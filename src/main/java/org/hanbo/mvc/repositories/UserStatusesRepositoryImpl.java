package org.hanbo.mvc.repositories;

import java.util.List;

import org.hanbo.mvc.entities.UserStatus;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@SuppressWarnings("unchecked")
public class UserStatusesRepositoryImpl
   implements UserStatusesRepository
{
   @Autowired
   private SessionFactory _sessionFactory;
   
   @Override
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   public void saveUserStatus(UserStatus userStatusToSave)
   {
      Session session = _sessionFactory.getCurrentSession();
      session.saveOrUpdate(userStatusToSave);
   }
   
   @Override
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   public long getViewableUserStatusesCount()
   {
      Session session = _sessionFactory.getCurrentSession();
      Query query = session.createQuery(
         "select count(userStatus) from UserStatus userStatus where "
         + "userStatus.viewable = true");
      List<Long> foundObjs = query.list();
      if (foundObjs != null && foundObjs.size() > 0)
      {
         return foundObjs.get(0);
      }
      
      return 0L;
   }
   
   @Override
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   public List<UserStatus> getViewableUserStatuses(int pageIdx, int maxItemsCount)
   {
      Session session = _sessionFactory.getCurrentSession();
      Query query = session.createQuery(
         "select userStatus from UserStatus userStatus where "
         + "userStatus.viewable = true")
         .setMaxResults(maxItemsCount)
         .setFirstResult(pageIdx * maxItemsCount);
      List<UserStatus> foundObjs = query.list();
      if (foundObjs != null && foundObjs.size() > 0)
      {
         loadUserStatus(foundObjs);
      }
      
      return foundObjs;
   }
   
   @Override
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   public long getAllUserStatusesCount(String ownerId)
   {
      Session session = _sessionFactory.getCurrentSession();
      Query query = session.createQuery(
         "select count(userStatus) from UserStatus userStatus where "
         + "userStatus.owner.id = :ownerId")
         .setParameter("ownerId", ownerId)
         .setMaxResults(1);
      List<Long> foundObjs = query.list();
      if (foundObjs != null && foundObjs.size() > 0)
      {
         return foundObjs.get(0);
      }
      
      return 0L;
   }
   
   @Override
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   public List<UserStatus> getAllUserStatuses(String ownerId, int pageIdx, int maxItemsCount)
   {
      Session session = _sessionFactory.getCurrentSession();
      Query query = session.createQuery(
         "select userStatus from UserStatus userStatus where "
         + "userStatus.owner.id = :ownerId")
         .setParameter("ownerId", ownerId)
         .setFirstResult(pageIdx * maxItemsCount)
         .setMaxResults(maxItemsCount);
      
      List<UserStatus> foundObjs = query.list();
      if (foundObjs != null && foundObjs.size() > 0)
      {
         loadUserStatus(foundObjs);
      }
      
      return foundObjs;
   }
   
   @Override
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   public UserStatus getUserStatus(String userId, String userUpdateId)
   {
      Session session = _sessionFactory.getCurrentSession();
      Query query = session.createQuery(
         "select userStatus from UserStatus userStatus where "
         + "userStatus.owner.id = :ownerId and userStatus.id = :userUpdateId")
         .setParameter("ownerId", userId)
         .setParameter("userUpdateId", userUpdateId)
         .setMaxResults(1);
      
      List<UserStatus> foundObjs = query.list();
      if (foundObjs != null && foundObjs.size() > 0)
      {
         return foundObjs.get(0);
      }
      
      return null;
   }
   
   private void loadUserStatus(List<UserStatus> foundObjs)
   {
      if (foundObjs != null && foundObjs.size() > 0)
      {
         for (UserStatus statusToLoad : foundObjs)
         {
            if (statusToLoad!= null && statusToLoad.getOwner() != null)
            {
               statusToLoad.getOwner().getId();
               statusToLoad.getOwner().getUserName();
               
               if (statusToLoad.getOwner().getUserProfile() != null)
               {
                  statusToLoad.getOwner().getUserProfile().getId();
                  statusToLoad.getOwner().getUserProfile().getFirstName();
                  statusToLoad.getOwner().getUserProfile().getLastName();
                  
                  if (statusToLoad.getOwner().getUserProfile().getUserIcon() != null)
                  {
                     statusToLoad.getOwner().getUserProfile().getUserIcon().getId();
                  }
               }
            }
         }
      }
   }
}

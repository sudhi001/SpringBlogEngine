package org.hanbo.mvc.repositories;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hanbo.mvc.entities.LoginUser;
import org.hanbo.mvc.entities.UserProfile;
import org.hanbo.mvc.entities.UserRole;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Repository
@SuppressWarnings("unchecked")
public class UsersRepositoryImpl implements UsersRepository
{
   @Autowired
   private SessionFactory _sessionFactory;
   
   @Override
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   public void  addUser(LoginUser userToSave, Set<UserRole> userRolesToSave)
   {
      Session session = _sessionFactory.getCurrentSession();
      session.saveOrUpdate(userToSave);
      
      Date dateNow = new Date();
      for (UserRole userRole : userRolesToSave)
      {
         userRole.setAssociatedUser(userToSave);
         userRole.setCreateDate(dateNow);
         session.saveOrUpdate(userRole);
      }
   }

   @Override
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   public boolean isUserExists(String userName)
   {
      Session session = _sessionFactory.getCurrentSession();
      List<Long> userMatches = session.createQuery(
         "select count(user) from LoginUser user where user.username = :userName"
      ).setParameter("userName", userName).list();

      if (userMatches.size() > 0)
      {
        long userCount = userMatches.get(0);
        return userCount >= 1L;
      }
      
      return false;      
   }
   
   @Override
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   public LoginUser getUser(String userName)
   {
      LoginUser retVal = getUserQuery("user.username", "userName", userName);
      loadUserRoles(retVal);
      
      return retVal;
   }

   @Override
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   public LoginUser getUserById(String userId)
   {
      LoginUser retVal = getUserQuery("user.id", "userId", userId);
      loadUserRoles(retVal);
      
      return retVal;
   }
   
   @Override
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   public void saveUserProfile(UserProfile userProfile)
   {
      Session session = _sessionFactory.getCurrentSession();
      session.saveOrUpdate(userProfile);
   }
   
   private LoginUser getUserQuery(String queryParam, String paramName, String queryVal)
   {
      String hqlQuery = String.format(
         "select user from LoginUser user where %s = :%s", queryParam, paramName
      );
      
      Session session = _sessionFactory.getCurrentSession();
      List<LoginUser> userMatches = session.createQuery(hqlQuery)
         .setParameter(paramName, queryVal).list();

      if (userMatches.size() > 0)
      {
        return userMatches.get(0);
      }
      
      return null;      
   }
   
   private void loadUserRoles(LoginUser userLoad)
   {
      if (userLoad != null)
      {
         Set<UserRole> userRoles = userLoad.getUserRoles();
         
         for(UserRole userRole : userRoles)
         {
            userRole.getCreateDate();
            userRole.getId();
            userRole.getRoleName();
         }
      }
   }
}

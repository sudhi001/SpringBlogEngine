package org.hanbo.mvc.repositories;

import java.util.List;

import org.hanbo.mvc.entities.FileResource;
import org.hanbo.mvc.entities.Resource;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("unchecked")
@Repository
public class ResourcesRepositoryImpl implements ResourcesRepository
{
   @Autowired
   private SessionFactory _sessionFactory;

   @Override
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   public void saveResource(Resource resToSave)
   {
      Session session = _sessionFactory.getCurrentSession();
      session.saveOrUpdate(resToSave);
   }

   @Override
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   public List<Resource> getResourcesByOwnerId(
      String ownerId, int pageIdx,
      int itemsCount
   )
   {
      Session session = _sessionFactory.getCurrentSession();
      
      Query objQuery = session.createQuery(
         "select resource from Resource "
         + "resource where resource.owner.id = :ownerId "
         + "order by resource.updateDate desc")
         .setParameter("ownerId", ownerId)
         .setMaxResults(itemsCount)
         .setFirstResult(pageIdx * itemsCount);
      
      List<Resource> retList =  objQuery.list();
      
      for (Resource res : retList)
      {
         res.getOwner().getId();
         res.getOwner().getUserName();
      }
      
      return retList;
   }
   
   @Override
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   public long getResourceCountByOwnerId(String ownerId)
   {
      Session session = _sessionFactory.getCurrentSession();
      
      Query objQuery = session.createQuery(
         "select count(resource) from Resource "
         + "resource where resource.owner.id = :ownerId")
         .setParameter("ownerId", ownerId);
      
      List<Long> retList =  objQuery.list();
      
      if (retList.size() > 0)
      {
         return retList.get(0);
      }
      
      return 0L;
   }

   @Override
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   public Resource getResourceByOwner(String resourceId, String ownerId)
   {
      Session session = _sessionFactory.getCurrentSession();
      
      Query objQuery = session.createQuery(
         "select resource from Resource resource "
         + "where resource.owner.id = :ownerId and resource.id = :resourceId")
         .setParameter("resourceId", resourceId)
         .setParameter("ownerId", ownerId).setMaxResults(1);
      
      List<Resource> objList = objQuery.list();
      if (objList.size() > 0)
      {
         return objList.get(0);
      }
      
      return null;
   }
   
   @Override
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   public void deleteResource(Resource resource)
   {
      Session session = _sessionFactory.getCurrentSession();
      session.delete(resource);
   }
   
   @Override
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   public List<Resource> getResourcesByTypeAndOwnerId(
      String ownerId, String resourceType, int pageIdx, int itemsCount)
   {
      Session session = _sessionFactory.getCurrentSession();

      Query objQuery = session.createQuery(
         "select resource from Resource resource"
         + " where resource.owner.id = :ownerId"
         + " and resource.resType = :resourceType"
         + " order by resource.updateDate desc")
         .setParameter("ownerId", ownerId)
         .setParameter("resourceType", resourceType)
         .setFirstResult(pageIdx * itemsCount)
         .setMaxResults(itemsCount);

      List<Resource> objList = objQuery.list();
      return objList;
   }
   
   @Override
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   public List<FileResource> getImageResourcesByOwnerId(
      String ownerId, int pageIdx, int itemsCount)
   {
      Session session = _sessionFactory.getCurrentSession();

      Query objQuery = session.createQuery(
         "select resource from FileResource resource"
         + " where resource.owner.id = :ownerId"
         + " and resource.subResourceType = :subType"
         + " order by resource.updateDate desc")
         .setParameter("ownerId", ownerId)
         .setParameter("subType", "image")
         .setFirstResult(pageIdx * itemsCount)
         .setMaxResults(itemsCount);

      List<FileResource> objList = objQuery.list();
      return objList;
   }
}

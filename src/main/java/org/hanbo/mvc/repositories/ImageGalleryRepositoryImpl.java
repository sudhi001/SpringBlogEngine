package org.hanbo.mvc.repositories;

import java.util.List;

import org.hanbo.mvc.entities.Gallery;
import org.hanbo.mvc.entities.Image;
import org.hibernate.Hibernate;
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
public class ImageGalleryRepositoryImpl
   implements ImageGalleryRepository
{
   @Autowired
   private SessionFactory _sessionFactory;

   @Override
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   public void saveImage(Image image)
   {
      Session session = _sessionFactory.getCurrentSession();
      session.saveOrUpdate(image);
   }
   
   @Override
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   public long getUserImagesCount(String ownerId)
   {
      Session session = _sessionFactory.getCurrentSession();
      
      Query query = session.createQuery(
         "select count(image) from Image image"
         + " where image.owner.id = :ownerId"
      ).setParameter("ownerId", ownerId)
      .setMaxResults(1);
      
      List<Long> foundObjs =  query.list();
      if (foundObjs.size() > 0)
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
   public List<Image> getUserImages(String ownerId, int pageIdx, int itemsCount)
   {
      Session session = _sessionFactory.getCurrentSession();
      
      Query query = session.createQuery(
         "select image from Image image where image.owner.id = :ownerId"
         + " order by image.uploadDate desc"
      ).setParameter("ownerId", ownerId)
      .setMaxResults(itemsCount)
      .setFirstResult(pageIdx * itemsCount);
      
      List<Image> foundObjs =  query.list();
      return foundObjs;
   }

   @Override
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   public long getUserGalleriesCount(String ownerId)
   {
      Session session = _sessionFactory.getCurrentSession();
      
      Query query = session.createQuery(
         "select count(gallery) from Gallery gallery"
         + " where gallery.owner.id = :ownerId"
      ).setParameter("ownerId", ownerId)
      .setMaxResults(1);
      
      List<Long> foundObjs =  query.list();
      if (foundObjs.size() > 0)
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
   public List<Gallery> getUserGalleries(String ownerId, int pageIdx, int itemsCount) {
      Session session = _sessionFactory.getCurrentSession();
      
      Query query = session.createQuery(
         "select gallery from Gallery gallery where gallery.owner.id = :ownerId"
         + " order by gallery.createDate desc"
      ).setParameter("ownerId", ownerId)
      .setMaxResults(itemsCount)
      .setFirstResult(pageIdx * itemsCount);
      
      List<Gallery> foundObjs =  query.list();
      if (foundObjs.size() > 0)
      {
         for (Gallery gallery : foundObjs)
         {
            gallery.getOwner().getId();
            gallery.getOwner().getUserName();
            
            if (gallery.getAssociatedImages().size() > 0)
            {
               for (Image image : gallery.getAssociatedImages())
               {
                  image.getId();
               }
            }
         }
      }
      return foundObjs;
   }
   
   @Override
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   public void addGallery(Gallery gallery)
   {
      Session session = _sessionFactory.getCurrentSession();
      session.saveOrUpdate(gallery);
   }
}

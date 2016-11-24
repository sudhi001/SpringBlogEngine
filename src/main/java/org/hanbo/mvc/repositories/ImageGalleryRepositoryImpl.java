package org.hanbo.mvc.repositories;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.hanbo.mvc.entities.Gallery;
import org.hanbo.mvc.entities.Image;
import org.hanbo.mvc.entities.ViewableGallery;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
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
   public void saveImage(Image image, String galleryId, String ownerId)
   {
      Session session = _sessionFactory.getCurrentSession();
      
      session.saveOrUpdate(image);
      
      Gallery associatedGallery = getUserGallery(session, galleryId, ownerId);
      if (associatedGallery != null)
      {
         associatedGallery.setUpdateDate(image.getUploadDate());
         associatedGallery.getAssociatedImages().add(image);
      }
      
      session.update(associatedGallery);
   }
   
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
   public void saveImages(List<Image> imagesToSave, String galleryId, String ownerId, Date updateDate)
   {
      Session session = _sessionFactory.getCurrentSession();
      
      Gallery associatedGallery = getUserGallery(session, galleryId, ownerId);
      if (associatedGallery != null)
      {
         for (Image img : imagesToSave)
         {
            if (img != null)
            {
               session.saveOrUpdate(img);
               associatedGallery.getAssociatedImages().add(img);
            }
         }
         
         associatedGallery.setUpdateDate(updateDate);
         session.update(associatedGallery);         
      }
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
   public void saveGallery(Gallery gallery)
   {
      Session session = _sessionFactory.getCurrentSession();
      session.saveOrUpdate(gallery);
   }
   
   @Override
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   public Gallery getUserGallery(String ownerId, String galleryId)
   {
      Session session = _sessionFactory.getCurrentSession();
      return getUserGallery(session, galleryId, ownerId);
   }
   
   @Override
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   public void setGalleryVisibility(String ownerId, String galleryId, boolean showGallery)
   {
      Session session = _sessionFactory.getCurrentSession();
      
      Query query = session.createQuery(
         "select gallery from Gallery gallery where gallery.owner.id = :ownerId and gallery.id = :galleryId"
         + " order by gallery.createDate desc"
      ).setParameter("ownerId", ownerId)
      .setParameter("galleryId", galleryId)
      .setMaxResults(1)
      .setFirstResult(0);
      
      List<Gallery> foundObjs =  query.list();
      if (foundObjs.size() > 0)
      {
         Gallery gallery = foundObjs.get(0);
                  
         gallery.setVisible(showGallery);
         gallery.setUpdateDate(new Date());
         
         session.update(gallery);
      }
   }
   
   @Override
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   public void enableGallery(String ownerId, String galleryId, boolean enableGallery)
   {
      Session session = _sessionFactory.getCurrentSession();
      
      Query query = session.createQuery(
         "select gallery from Gallery gallery where gallery.owner.id = :ownerId and gallery.id = :galleryId"
         + " order by gallery.createDate desc"
      ).setParameter("ownerId", ownerId)
      .setParameter("galleryId", galleryId)
      .setMaxResults(1)
      .setFirstResult(0);
      
      List<Gallery> foundObjs =  query.list();
      if (foundObjs.size() > 0)
      {
         Gallery gallery = foundObjs.get(0);
         
         gallery.setActive(enableGallery);
         gallery.setUpdateDate(new Date());
         
         session.update(gallery);
      }
   }
   
   private Gallery getUserGallery(Session session, String galleryId, String ownerId)
   {
      Query query = session.createQuery(
         "select gallery from Gallery gallery where gallery.owner.id = :ownerId and gallery.id = :galleryId"
         + " order by gallery.createDate desc"
      ).setParameter("ownerId", ownerId)
      .setParameter("galleryId", galleryId)
      .setMaxResults(1)
      .setFirstResult(0);
      
      List<Gallery> foundObjs =  query.list();
      if (foundObjs.size() > 0)
      {
         Gallery gallery = foundObjs.get(0);
         gallery.getOwner().getId();
         gallery.getOwner().getUserName();
         return gallery;
      }
      
      return null;
   }

   @Override
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   public long getGalleryImagesCount(String ownerId, String galleryId)
   {
      Session session = this._sessionFactory.getCurrentSession();
      Query query = session.createQuery(
         "select count(image) from Gallery gallery join gallery.galleryImages image"
         + " where gallery.id = :galleryId and gallery.owner.id = :ownerId order by image.uploadDate desc"
      ).setParameter("ownerId", ownerId)
      .setParameter("galleryId", galleryId)
      .setMaxResults(1)
      .setFirstResult(0);
      
      List<Long> imagesCount = query.list();
      if (imagesCount != null && imagesCount.size() > 0)
      {
         return imagesCount.get(0);
      }
      
      return 0L;
   }
   
   @Override
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   public List<Image> getGalleryImages(String ownerId, String galleryId, int pageIdx, int itemsCount)
   {
      Session session = this._sessionFactory.getCurrentSession();
      Query query = session.createQuery(
         "select image from Gallery gallery join gallery.galleryImages image"
         + " where gallery.id = :galleryId and gallery.owner.id = :ownerId order by image.uploadDate desc"
      ).setParameter("ownerId", ownerId)
      .setParameter("galleryId", galleryId)
      .setMaxResults(itemsCount)
      .setFirstResult(pageIdx * itemsCount);
      
      List<Image> foundImages = query.list();
      if (foundImages != null && foundImages.size() > 0)
      {
         for (Image img : foundImages)
         {
            img.getOwner().getId();
            img.getOwner().getUserName();
         }
         return foundImages;
      }
      
      return new ArrayList<Image>();
   }
   
   @Override
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   public Image getUserImage(String imageId, String ownerId)
   {
      Session session = this._sessionFactory.getCurrentSession();
      Query query = session.createQuery(
         "select image from Image image where image.id = :imageId "
         + "and image.owner.id = :ownerId"
      ).setParameter("ownerId", ownerId)
       .setParameter("imageId", imageId)
       .setMaxResults(1)
       .setFirstResult(0);

      List<Image> foundImages = query.list();
      if (foundImages != null && foundImages.size() > 0)
      {
         Image retImg = foundImages.get(0);
         retImg.getOwner().getId();
         retImg.getOwner().getUserName();
         
         return retImg;
      }
      
      return null;
   }

   @Override
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   public Image getImage(String imageId)
   {
      Session session = this._sessionFactory.getCurrentSession();
      Query query = session.createQuery(
         "select image from Image image where image.id = :imageId"
      ).setParameter("imageId", imageId)
       .setMaxResults(1)
       .setFirstResult(0);

      List<Image> imagesFound = query.list();
      if (imagesFound.size() > 0)
      {
         return imagesFound.get(0);
      }
      
      return null;
   }
   
   @Override
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   public List<Image> findUserImages(String ownerId, String[] searchWords, int pageIdx, int resultsCount)
   {
      try
      {
         List<Image> retVals = new ArrayList<Image>();
         if (searchWords == null || searchWords.length <= 0)
         {
            return retVals;
         }
         
         Session session = this._sessionFactory.getCurrentSession();
         
         FullTextSession fullTextSession = Search.getFullTextSession(session);
         
         QueryBuilder qb = fullTextSession.getSearchFactory()
            .buildQueryBuilder().forEntity(Image.class).get();
         
         org.apache.lucene.search.BooleanQuery finalQuery = new BooleanQuery();
         for (String keyword : searchWords)
         {
            org.apache.lucene.search.Query query = qb
               .keyword().onFields("imageTitle", "imageKeywords")
               .matching(keyword)
               .createQuery();
            finalQuery.add(query, Occur.SHOULD);
         }
         
         FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery(finalQuery);
         fullTextQuery.setMaxResults(resultsCount);
         fullTextQuery.setFirstResult(pageIdx * resultsCount);
         
         retVals = fullTextQuery.list();
 
         return retVals;
      }
      catch(Exception e)
      {
        throw new RuntimeException(e); 
      }
   }
   
   @Override
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   public List<Image> allUserImages(String ownerId, int pageIdx, int resultsCount)
   {
      try
      {
         Session session = this._sessionFactory.getCurrentSession();
         Query query = session.createQuery(
            "select image from Image image where image.owner.id = :ownerId order by image.uploadDate desc"
         ).setParameter("ownerId", ownerId)
          .setMaxResults(resultsCount)
          .setFirstResult(pageIdx * resultsCount);
         
         List<Image> retVal = query.list();
         return retVal;
      }
      catch(Exception e)
      {
         throw new RuntimeException(e); 
      }
   }
   
   @Override
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   public List<ViewableGallery> getViewableGalleries(int pageIdx, int itemsPerPage, int maxSampleImagesCount)
   {
      Session session = this._sessionFactory.getCurrentSession();
      
      Query query = session.createQuery("select gallery from Gallery gallery where gallery.visible = true and gallery.active = true order by gallery.updateDate desc")
         .setFirstResult(pageIdx * itemsPerPage)
         .setMaxResults(itemsPerPage);
      List<Gallery> foundObjs = query.list();
      
      List<ViewableGallery> retVals = new ArrayList<ViewableGallery>();
      if (foundObjs != null && foundObjs.size() > 0)
      {
         for (Gallery gallery : foundObjs)
         {
            if (gallery != null)
            {
               if (gallery.getOwner() != null)
               {
                  gallery.getOwner().getId();
                  gallery.getOwner().getUserName();
               }
               
               String galleryId = gallery.getId();
               
               ViewableGallery galleryToAdd = new ViewableGallery();
               galleryToAdd.setGallery(gallery);
               
               List<Image> sampleImages = 
               getViewableGalleryImageSamples(session, galleryId, maxSampleImagesCount);
               
               if (sampleImages != null && sampleImages.size() > 0)
               {
                  if (galleryToAdd.getSampleImages() == null)
                  {
                     galleryToAdd.setSampleImages(new ArrayList<Image>());
                  }
                  
                  galleryToAdd.getSampleImages().addAll(sampleImages);
               }
               
               retVals.add(galleryToAdd);
            }
         }
      }
      
      return retVals;
   }
   
   @Override
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   public long getViewableGalleriesCount()
   {
      Session session = this._sessionFactory.getCurrentSession();
      
      Query query = session.createQuery("select count(gallery) from Gallery gallery where gallery.visible = true and gallery.active = true")
         .setFirstResult(0)
         .setMaxResults(1);
      List<Long> foundObjs = query.list();
      if (foundObjs != null && foundObjs.size() > 0)
      {
         return foundObjs.get(0);
      }

      return 0L;
   }
   
   private List<Image> getViewableGalleryImageSamples(Session session, String galleryId, int sampleImagesCount)
   {
      List<Image> retVals = null;
      if (session != null && !StringUtils.isEmpty(galleryId) && sampleImagesCount > 0)
      {
         Query query = session.createQuery("select image from Gallery gallery join gallery.galleryImages image"
            + " where gallery.id = :galleryId and image.active = true order by image.uploadDate desc")
            .setParameter("galleryId", galleryId)
            .setFirstResult(0)
            .setMaxResults(sampleImagesCount);
         
         retVals = query.list();
         if (retVals != null && retVals.size() > 0)
         {
            for (Image image : retVals)
            {
               if (image != null && image.getOwner() != null)
               {
                  image.getOwner().getId();
                  image.getOwner().getUserName();
               }
            }
            
            return retVals;
         }
      }
      
      retVals = new ArrayList<Image>();
      return retVals;
   }
}

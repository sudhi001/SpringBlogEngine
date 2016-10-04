package org.hanbo.mvc.repositories;

import java.util.Date;
import java.util.List;

import org.hanbo.mvc.entities.Gallery;
import org.hanbo.mvc.entities.Image;

public interface ImageGalleryRepository
{
   void saveImage(Image image, String galleryId, String ownerId);

   void saveImage(Image image);
   
   void saveImages(List<Image> imagesToSave, String galleryId, String ownerId, Date updateDate);

   long getUserImagesCount(String ownerId);
   
   List<Image> getUserImages(String ownerId, int pageIdx, int itemsCount);

   long getUserGalleriesCount(String ownerId);
   
   List<Gallery> getUserGalleries(String ownerId, int pageIdx, int itemsCount);
   
   void saveGallery(Gallery gallery);
   
   Gallery getUserGallery(String ownerId, String galleryId);

   long getGalleryImagesCount(String ownerId, String galleryId);
   
   List<Image> getGalleryImages(String ownerId, String galleryId, int pageIdx, int itemsCount);
   
   void setGalleryVisibility(String ownerId, String galleryId, boolean showGallery);

   void enableGallery(String ownerId, String galleryId, boolean enableGallery);
   
   Image getUserImage(String imageId, String ownerId);
   
   Image getImage(String imageId);
   
   List<Image> findUserImages(String ownerId, String[] searchWords, int pageIdx, int resultsCount);

   List<Image> allUserImages(String ownerId, int pageIdx, int resultsCount);
}

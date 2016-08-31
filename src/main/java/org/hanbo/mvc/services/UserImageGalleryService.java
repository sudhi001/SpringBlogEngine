package org.hanbo.mvc.services;

import java.io.OutputStream;

import org.hanbo.mvc.models.GalleryDisplayPageDataModel;
import org.hanbo.mvc.models.GalleryImagesPageDisplayDataModel;
import org.springframework.web.multipart.MultipartFile;

public interface UserImageGalleryService
{
   GalleryDisplayPageDataModel getUserGalleries(String ownerId, int pageIdx);

   GalleryImagesPageDisplayDataModel getUserGalleryImages(String ownerId, String galleryId, int pageIdx);
   
   void uploadImage(String userId, String galleryId, String imageTitle,
      String imageKeywords, MultipartFile imageToUpload);
      
   boolean downloadImage(String imageId, String type,
      OutputStream outputStream) throws Exception;
   
   void addGallery(String ownerId, String galleryTitle,
      String galleryKeywords, String galleryDesc);

   void setGalleryVisibility(String ownerId, String galleryId, boolean showGallery);

   void enableGallery(String ownerId, String galleryId, boolean enableGallery);
}

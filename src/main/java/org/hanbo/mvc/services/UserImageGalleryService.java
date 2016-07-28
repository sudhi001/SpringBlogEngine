package org.hanbo.mvc.services;

import java.io.OutputStream;

import org.hanbo.mvc.models.GalleryDisplayPageDataModel;
import org.hanbo.mvc.models.ImageDisplayPageDataModel;
import org.springframework.web.multipart.MultipartFile;

public interface UserImageGalleryService
{
   ImageDisplayPageDataModel getUserImages(String ownerId, int pageIdx);

   GalleryDisplayPageDataModel getUserGalleries(String ownerId, int pageIdx);

   void uploadImage(String userId, String galleryId, String imageTitle,
      String imageKeywords, MultipartFile imageToUpload);
   
   boolean imageSnapshotAvailable(String imageId);
   
   boolean downloadImage(String imageId, String type,
      OutputStream outputStream) throws Exception;
   
   void addGallery(String ownerId, String galleryTitle,
      String galleryKeywords, String galleryDesc);
}

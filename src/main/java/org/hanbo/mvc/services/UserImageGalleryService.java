package org.hanbo.mvc.services;

import java.io.OutputStream;

import org.hanbo.mvc.models.ImageDisplayPageDataModel;
import org.springframework.web.multipart.MultipartFile;

public interface UserImageGalleryService
{
   ImageDisplayPageDataModel getUserImages(String ownerId, int pageIdx);
   
   void uploadImage(String userId, String imageTitle, String imageKeywords,
      MultipartFile imageToUpload, MultipartFile snapshotToUpload);
   
   boolean imageSnapshotAvailable(String imageId);
   
   boolean downloadImage(String imageId, String type,
      OutputStream outputStream) throws Exception;
}
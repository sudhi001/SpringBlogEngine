package org.hanbo.mvc.services;

import org.hanbo.mvc.models.ImageDisplayPageDataModel;
import org.springframework.web.multipart.MultipartFile;

public interface UserImageGalleryService
{
   ImageDisplayPageDataModel getUserImages(String ownerId, int pageIdx);
   
   void uploadImage(String userId, String imageTitle, String imageKeywords,
      MultipartFile imageToUpload, MultipartFile snapshotToUpload);
}

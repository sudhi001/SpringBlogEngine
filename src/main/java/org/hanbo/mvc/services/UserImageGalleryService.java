package org.hanbo.mvc.services;

import java.io.OutputStream;
import java.util.List;

import org.hanbo.mvc.models.GalleryDisplayPageDataModel;
import org.hanbo.mvc.models.GalleryImagesPageDisplayDataModel;
import org.hanbo.mvc.models.ImageDisplayDetail;
import org.hanbo.mvc.models.ImageSizeDataModel;
import org.hanbo.mvc.models.ViewableGalleriesPageDataModel;
import org.hanbo.mvc.models.ViewableGalleryDisplayDetail;
import org.hanbo.mvc.models.json.SearchUserPhotoResponse;
import org.springframework.web.multipart.MultipartFile;

public interface UserImageGalleryService
{
   GalleryDisplayPageDataModel getUserGalleries(String ownerId, int pageIdx);

   GalleryImagesPageDisplayDataModel getUserGalleryImages(String ownerId, String galleryId, int pageIdx);
   
   void uploadImage(String userId, String galleryId, String imageTitle,
      String imageKeywords, MultipartFile imageToUpload, boolean imageNotSafeForWork);
      
   void uploadImages(String userId, String galleryId, MultipartFile[] imagesToUpload);

   boolean downloadImage(String imageId, String type,
      OutputStream outputStream) throws Exception;
   
   void addGallery(String ownerId, String galleryTitle,
      String galleryKeywords, String galleryDesc);

   void setGalleryVisibility(String ownerId, String galleryId, boolean showGallery);

   void enableGallery(String ownerId, String galleryId, boolean enableGallery);
   
   ImageDisplayDetail getUserImageDetail(String imageId, String ownerId);
   
   void editImageDetails(String userId, String imageId, String imageTitle,
      String imageKeywords, boolean imageActive, boolean imageNotSafeForWork);

   void editGalleryDetails(String userId, String galleryId, String galleryTitle,
      String galleryKeywords, String galleryDesc, boolean galleryActive, boolean galleryVisible);
   
   ImageSizeDataModel getImageDimension(String imageId);
   
   List<SearchUserPhotoResponse> findUserPhotos(String ownerId, String searchWords, int pageIdx);

   List<SearchUserPhotoResponse> allUserImagesJson(String ownerId, int pageIdx);
   
   ViewableGalleriesPageDataModel getViewableGalleries(int pageIdx);
   
   ViewableGalleryDisplayDetail getViewableGallery(String galleryId, int pageIdx);
}

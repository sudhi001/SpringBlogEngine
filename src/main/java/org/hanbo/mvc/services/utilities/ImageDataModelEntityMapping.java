package org.hanbo.mvc.services.utilities;

import java.util.ArrayList;
import java.util.List;

import org.hanbo.mvc.entities.Gallery;
import org.hanbo.mvc.entities.Image;
import org.hanbo.mvc.models.GalleryDisplayDetail;
import org.hanbo.mvc.models.ImageDisplayDetail;
import org.hanbo.mvc.models.json.SearchUserPhotoResponse;

public class ImageDataModelEntityMapping
{
   public static ImageDisplayDetail entityToImageDisplayDetail(Image img)
   {
      ImageDisplayDetail retVal = new  ImageDisplayDetail();
      
      retVal.setImageId(img.getId());
      retVal.setImageTitle(img.getImageTitle());
      retVal.setImageKeywords(img.getImageKeywords());
      retVal.setUpdloadDate(img.getUploadDate());
      retVal.setImageActive(img.isActive());
      retVal.setImageNotSafeForWork(img.isNotSafeForWork());
      retVal.setImageFilePath(img.getFilePath());
      retVal.setImageThumbFilePath(img.getThumbnailFilePath());
      retVal.setOwnerId(img.getOwner().getId());
      retVal.setOwnerUserName(img.getOwner().getUserName());
      retVal.setImageWidth(img.getFileSizeX());
      retVal.setImageHeight(img.getFileSizeY());
      retVal.setImageDisplayWidth(0);
      retVal.setImageDisplayHeight(0);
      
      return retVal;
   }
   
   public static List<ImageDisplayDetail>
      entitiesToImageDisplayDetailList(List<Image> imgs)
   {
      List<ImageDisplayDetail> retVal = new ArrayList<ImageDisplayDetail>();
      for (Image img : imgs)
      {
         ImageDisplayDetail imgDispDetail = entityToImageDisplayDetail(img);
         
         retVal.add(imgDispDetail);
      }
      
      return retVal;
   }
   
   public static GalleryDisplayDetail entityToGalleryDisplayDetail(Gallery gallery)
   {
      GalleryDisplayDetail retVal = new GalleryDisplayDetail();
      
      retVal.setGalleryId(gallery.getId());
      retVal.setGalleryDescription(gallery.getGalleryDescription());
      retVal.setCreateDate(gallery.getCreateDate());
      retVal.setUpdateDate(gallery.getUpdateDate());
      retVal.setGalleryKeywords(gallery.getGalleryKeywords());
      retVal.setGalleryTitle(gallery.getGalleryTitle());
      retVal.setGalleryActive(gallery.isActive());
      retVal.setGalleryVisible(gallery.isVisible());
      retVal.setOwnerId(gallery.getOwner().getId());
      retVal.setOwnerName(gallery.getOwner().getUserName());
      
      return retVal;
   }
   
   public static List<GalleryDisplayDetail>
      entitiesToGalleriesDisplayDetailList(List<Gallery> entities)
   {
      List<GalleryDisplayDetail> retVal = new ArrayList<GalleryDisplayDetail>();
      for (Gallery gal : entities)
      {
         GalleryDisplayDetail obj = entityToGalleryDisplayDetail(gal);
         retVal.add(obj);
      }
      
      return retVal;
   }
   
   public static List<SearchUserPhotoResponse> entitiesToSearchUserPhotoResponses(List<Image> userImagesFound)
   {
      List<SearchUserPhotoResponse> retVals = new ArrayList<SearchUserPhotoResponse>();
      
      if (userImagesFound != null && userImagesFound.size() > 0)
      {
         for (Image imageToConvert : userImagesFound)
         {
            SearchUserPhotoResponse response
               = entityToSearchUserPhotoResponse(imageToConvert);
            
            if (response != null)
            {
               retVals.add(response);
            }
         }
      }
      
      return retVals;
   }

   public static SearchUserPhotoResponse entityToSearchUserPhotoResponse(Image userImageFound)
   {
      if (userImageFound != null)
      {
         SearchUserPhotoResponse retVal = new SearchUserPhotoResponse();
         retVal.setImageId(userImageFound.getId());
         retVal.setImageSizeX(userImageFound.getFileSizeX());
         retVal.setImageSizeY(userImageFound.getFileSizeY());
         retVal.setImageTitle(userImageFound.getImageTitle());
         
         float wtohRatio = ((float)userImageFound.getFileSizeX()) / ((float)userImageFound.getFileSizeY());
         retVal.setWidthToHeightRatio(wtohRatio);
         
         return retVal;
      }
      
      return null;
   }
}

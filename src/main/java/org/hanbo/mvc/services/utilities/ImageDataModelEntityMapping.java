package org.hanbo.mvc.services.utilities;

import java.util.ArrayList;
import java.util.List;

import org.hanbo.mvc.entities.Gallery;
import org.hanbo.mvc.entities.Image;
import org.hanbo.mvc.entities.ViewableGallery;
import org.hanbo.mvc.models.GalleryDisplayDetail;
import org.hanbo.mvc.models.ImageDisplayDetail;
import org.hanbo.mvc.models.ViewableGalleryDisplayDetail;
import org.hanbo.mvc.models.json.SearchUserPhotoResponse;

public class ImageDataModelEntityMapping
{
   public static ImageDisplayDetail entityToImageDisplayDetail(Image img)
   {
      ImageDisplayDetail retVal = new  ImageDisplayDetail();
      
      retVal.setImageId(img.getId());
      retVal.setImageTitle(img.getImageTitle());
      retVal.setImageKeywords(img.getImageKeywords());
      retVal.setUploadDate(img.getUploadDate());
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
      
      if (img.getOwner() != null)
      {
         retVal.setOwnerId(img.getOwner().getId());
         retVal.setOwnerUserName(img.getOwner().getUserName());
         if (img.getOwner().getUserProfile() != null)
         {
            retVal.setOwnerProfileId(img.getOwner().getUserProfile().getId());
            retVal.setOwnerUserFullName(String.format("%s %s", img.getOwner().getUserProfile().getLastName(), img.getOwner().getUserProfile().getFirstName()));
            if (img.getOwner().getUserProfile().getUserIcon() != null)
            {
               retVal.setOwnerIconFileId(img.getOwner().getUserProfile().getUserIcon().getId());
            }
         }
      }
      
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
   
   public static ViewableGalleryDisplayDetail entityToGalleryDetail(ViewableGallery gallery)
   {
      if (gallery != null)
      {
         ViewableGalleryDisplayDetail retVal = new ViewableGalleryDisplayDetail();
         
         if (gallery.getGallery() != null)
         {
            retVal.setGalleryId(gallery.getGallery().getId());
            retVal.setGalleryDescription(gallery.getGallery().getGalleryDescription());
            retVal.setCreateDate(gallery.getGallery().getCreateDate());
            retVal.setUpdateDate(gallery.getGallery().getUpdateDate());
            retVal.setGalleryKeywords(gallery.getGallery().getGalleryKeywords());
            retVal.setGalleryTitle(gallery.getGallery().getGalleryTitle());
            retVal.setGalleryActive(gallery.getGallery().isActive());
            retVal.setGalleryVisible(gallery.getGallery().isVisible());
            if (gallery.getGallery().getOwner() != null)
            {
               retVal.setOwnerId(gallery.getGallery().getOwner().getId());
               retVal.setOwnerName(gallery.getGallery().getOwner().getUserName());
            }
            
            if (gallery.getGallery().getOwner().getUserProfile() != null)
            {
               retVal.setOwnerProfileId(gallery.getGallery().getOwner().getUserProfile().getId());
               retVal.setOwnerUserFullName(String.format("%s %s", gallery.getGallery().getOwner().getUserProfile().getFirstName(), gallery.getGallery().getOwner().getUserProfile().getLastName()));
               if (gallery.getGallery().getOwner().getUserProfile().getUserIcon() != null)
               {
                  retVal.setOwnerIconFileId(gallery.getGallery().getOwner().getUserProfile().getUserIcon().getId());
               }
            }
            
            if (gallery.getSampleImages() != null)
            {
               for (Image image : gallery.getSampleImages())
               {
                  if (image != null)
                  {
                     ImageDisplayDetail imageDD = new ImageDisplayDetail();
                     imageDD.setImageId(image.getId());
                     imageDD.setImageTitle(image.getImageTitle());
                     imageDD.setImageKeywords(image.getImageKeywords());
                     imageDD.setUploadDate(image.getUploadDate());
                     imageDD.setImageActive(image.isActive());
                     imageDD.setImageNotSafeForWork(image.isNotSafeForWork());
                     imageDD.setImageFilePath(image.getFilePath());
                     imageDD.setImageThumbFilePath(image.getThumbnailFilePath());
                     imageDD.setOwnerId(image.getOwner().getId());
                     imageDD.setOwnerUserName(image.getOwner().getUserName());
                     imageDD.setImageWidth(image.getFileSizeX());
                     imageDD.setImageHeight(image.getFileSizeY());
                     imageDD.setImageDisplayWidth(0);
                     imageDD.setImageDisplayHeight(0);
                     
                     if (image.getOwner() != null)
                     {
                        imageDD.setOwnerId(image.getOwner().getId());
                        imageDD.setOwnerUserName(image.getOwner().getUserName());
                        if (image.getOwner().getUserProfile() != null)
                        {
                           imageDD.setOwnerProfileId(image.getOwner().getUserProfile().getId());
                           imageDD.setOwnerUserFullName(String.format("%s %s", image.getOwner().getUserProfile().getLastName(), image.getOwner().getUserProfile().getFirstName()));
                           if (image.getOwner().getUserProfile().getUserIcon() != null)
                           {
                              imageDD.setOwnerIconFileId(image.getOwner().getUserProfile().getUserIcon().getId());
                           }
                        }
                     }
                     
                     if (retVal.getSampleImages() == null)
                     {
                        retVal.setSampleImages(new ArrayList<ImageDisplayDetail>());
                     }
                     
                     retVal.getSampleImages().add(imageDD);
                  }
               }
            }
            
            return retVal;
         }
      }
      
      return null;
   }
   
   public static List<ViewableGalleryDisplayDetail> entitiesToGalleryDetails(List<ViewableGallery> galleries)
   {
      List<ViewableGalleryDisplayDetail> retVals = new ArrayList<ViewableGalleryDisplayDetail>();
      if (galleries != null)
      {
         for (ViewableGallery gallery : galleries)
         {
            if (gallery != null)
            {
               ViewableGalleryDisplayDetail galleryDisplay = entityToGalleryDetail(gallery);
               if (galleryDisplay != null)
               {
                  retVals.add(galleryDisplay);
               }
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

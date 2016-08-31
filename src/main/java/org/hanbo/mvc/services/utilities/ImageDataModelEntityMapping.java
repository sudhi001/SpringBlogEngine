package org.hanbo.mvc.services.utilities;

import java.util.ArrayList;
import java.util.List;

import org.hanbo.mvc.entities.Gallery;
import org.hanbo.mvc.entities.Image;
import org.hanbo.mvc.models.GalleryDisplayDetail;
import org.hanbo.mvc.models.ImageDisplayDetail;

public class ImageDataModelEntityMapping
{
   public static ImageDisplayDetail entityToImageDisplayDetail(Image img)
   {
      ImageDisplayDetail retVal = new  ImageDisplayDetail();
      
      retVal.setImageId(img.getId());
      retVal.setUpdloadDate(img.getUploadDate());
      retVal.setImageActive(img.isActive());
      retVal.setImageFilePath(img.getFilePath());
      retVal.setImageThumbFilePath(img.getThumbnailFilePath()); 
      
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
      retVal.setGalleryDescription(gallery.getDescription());
      retVal.setCreateDate(gallery.getCreateDate());
      retVal.setGalleryKeywords(gallery.getKeywords());
      retVal.setGalleryTitle(gallery.getTitle());
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
}

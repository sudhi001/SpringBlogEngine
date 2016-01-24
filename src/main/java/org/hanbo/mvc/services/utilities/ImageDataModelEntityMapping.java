package org.hanbo.mvc.services.utilities;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hanbo.mvc.entities.Image;
import org.hanbo.mvc.models.ImageDisplayDetail;

public class ImageDataModelEntityMapping
{
   public static ImageDisplayDetail entityToImageDisplayDetail(Image img)
   {
      ImageDisplayDetail retVal = new  ImageDisplayDetail();
      
      retVal.setImageId(img.getId());
      retVal.setUpdloadDate(img.getUploadDate());
      retVal.setImageFilePath(img.getFilePath());
      
      if (StringUtils.isEmpty(img.getSnapshotFilePath())
         && !StringUtils.isEmpty(img.getThumbnailFilePath()))
      {
         retVal.setImageThumbFilePath(img.getThumbnailFilePath());         
      }
      else if (!StringUtils.isEmpty(img.getSnapshotFilePath())
         && StringUtils.isEmpty(img.getThumbnailFilePath()))
      {
         retVal.setImageThumbFilePath(img.getSnapshotFilePath());         
      }
      else {
         retVal.setImageThumbFilePath("");
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
   
   
}
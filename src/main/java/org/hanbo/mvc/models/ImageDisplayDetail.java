package org.hanbo.mvc.models;

import java.util.Date;

import org.hanbo.mvc.utilities.DateToString;

public class ImageDisplayDetail
{
   private String imageId;
   
   private String imageFilePath;
   
   private String imageThumbFilePath;
   
   private Date uploadDate;
   
   private boolean imageActive; 

   public String getImageId()
   {
      return imageId;
   }

   public void setImageId(String imageId)
   {
      this.imageId = imageId;
   }

   public String getImageFilePath()
   {
      return imageFilePath;
   }

   public void setImageFilePath(String imageFilePath)
   {
      this.imageFilePath = imageFilePath;
   }

   public String getImageThumbFilePath()
   {
      return imageThumbFilePath;
   }

   public void setImageThumbFilePath(String imageThumbFilePath)
   {
      this.imageThumbFilePath = imageThumbFilePath;
   }

   public Date getUpdloadDate()
   {
      return uploadDate;
   }
   
   public String getUpdloadDateString()
   {
      return
      DateToString.dateToString(uploadDate, "MM/dd/yyyy");
   }

   public void setUpdloadDate(Date uploadDate)
   {
      this.uploadDate = uploadDate;
   }

   public boolean isImageActive()
   {
      return imageActive;
   }

   public void setImageActive(boolean imageActive)
   {
      this.imageActive = imageActive;
   }
}

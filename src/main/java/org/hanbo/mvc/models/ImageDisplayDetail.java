package org.hanbo.mvc.models;

import java.util.Date;

import org.hanbo.mvc.utilities.DateToString;

public class ImageDisplayDetail
{
   private String imageId;
   
   private String imageFilePath;
   
   private String imageTitle;
   
   private String imageKeywords;
   
   private String imageThumbFilePath;
   
   private Date uploadDate;
   
   private boolean imageActive;
   
   private String ownerId;
   
   private String ownerUserName;

   public String getImageId()
   {
      return imageId;
   }

   public void setImageId(String imageId)
   {
      this.imageId = imageId;
   }
   
   public String getImageTitle() {
      return imageTitle;
   }

   public void setImageTitle(String imageTitle)
   {
      this.imageTitle = imageTitle;
   }

   public String getImageKeywords()
   {
      return imageKeywords;
   }

   public void setImageKeywords(String imageKeywords)
   {
      this.imageKeywords = imageKeywords;
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

   public String getOwnerId()
   {
      return ownerId;
   }

   public void setOwnerId(String ownerId)
   {
      this.ownerId = ownerId;
   }

   public String getOwnerUserName()
   {
      return ownerUserName;
   }

   public void setOwnerUserName(String ownerUserName)
   {
      this.ownerUserName = ownerUserName;
   }
}

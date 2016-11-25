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
   
   private int imageWidth;
   
   private int imageHeight;
   
   private int imageDisplayWidth;
   
   private int imageDisplayHeight;
   
   private boolean imageActive;
   
   private boolean imageNotSafeForWork;
   
   private String ownerId;
   
   private String ownerUserName;

   private String ownerProfileId;
   
   private String ownerUserFullName;
   
   private String ownerIconFileId;

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
   
   public boolean isImageNotSafeForWork()
   {
      return imageNotSafeForWork;
   }

   public void setImageNotSafeForWork(boolean notSafeForWork)
   {
      this.imageNotSafeForWork = notSafeForWork;
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

   public int getImageWidth()
   {
      return imageWidth;
   }

   public void setImageWidth(int imageWidth)
   {
      this.imageWidth = imageWidth;
   }

   public int getImageHeight()
   {
      return imageHeight;
   }

   public void setImageHeight(int imageHeight)
   {
      this.imageHeight = imageHeight;
   }

   public int getImageDisplayWidth()
   {
      return imageDisplayWidth;
   }

   public void setImageDisplayWidth(int imageDisplayWidth)
   {
      this.imageDisplayWidth = imageDisplayWidth;
   }

   public int getImageDisplayHeight()
   {
      return imageDisplayHeight;
   }

   public void setImageDisplayHeight(int imageDisplayHeight)
   {
      this.imageDisplayHeight = imageDisplayHeight;
   }

   public String getOwnerProfileId()
   {
      return ownerProfileId;
   }

   public void setOwnerProfileId(String ownerProfileId)
   {
      this.ownerProfileId = ownerProfileId;
   }

   public String getOwnerUserFullName()
   {
      return ownerUserFullName;
   }

   public void setOwnerUserFullName(String ownerUserFullName)
   {
      this.ownerUserFullName = ownerUserFullName;
   }

   public String getOwnerIconFileId()
   {
      return ownerIconFileId;
   }

   public void setOwnerIconFileId(String ownerIconFileId)
   {
      this.ownerIconFileId = ownerIconFileId;
   }
}

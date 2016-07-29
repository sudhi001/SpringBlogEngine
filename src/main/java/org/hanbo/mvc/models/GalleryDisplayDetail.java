package org.hanbo.mvc.models;

import java.util.Date;

import org.hanbo.mvc.utilities.DateToString;

public class GalleryDisplayDetail
{
   private String galleryId;
   
   private String galleryTitle;
   
   private String galleryDescription;
   
   private String galleryKeywords;

   private boolean galleryActive;
   
   private boolean galleryVisible;
   
   private Date createDate;
   
   private String ownerName;
   
   private String ownerId;

   public String getGalleryId()
   {
      return galleryId;
   }

   public void setGalleryId(String galleryId)
   {
      this.galleryId = galleryId;
   }

   public String getGalleryTitle()
   {
      return galleryTitle;
   }

   public void setGalleryTitle(String galleryTitle)
   {
      this.galleryTitle = galleryTitle;
   }

   public String getGalleryDescription()
   {
      return galleryDescription;
   }

   public void setGalleryDescription(String galleryDescription)
   {
      this.galleryDescription = galleryDescription;
   }

   public String getGalleryKeywords()
   {
      return galleryKeywords;
   }

   public void setGalleryKeywords(String galleryKeywords)
   {
      this.galleryKeywords = galleryKeywords;
   }

   public Date getCreateDate()
   {
      return createDate;
   }

   public String getCreateDateString()
   {
      return DateToString.dateStringForDisplay(createDate);
   }

   public void setCreateDate(Date createDate)
   {
      this.createDate = createDate;
   }

   public String getOwnerName()
   {
      return ownerName;
   }

   public void setOwnerName(String ownerName)
   {
      this.ownerName = ownerName;
   }

   public String getOwnerId()
   {
      return ownerId;
   }

   public void setOwnerId(String ownerId)
   {
      this.ownerId = ownerId;
   }

   public boolean isGalleryVisible()
   {
      return galleryVisible;
   }

   public void setGalleryVisible(boolean galleryVisible)
   {
      this.galleryVisible = galleryVisible;
   }

   public boolean isGalleryActive()
   {
      return galleryActive;
   }

   public void setGalleryActive(boolean galleryActive)
   {
      this.galleryActive = galleryActive;
   }
}

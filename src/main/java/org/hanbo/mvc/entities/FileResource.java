package org.hanbo.mvc.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="fileresource")
@PrimaryKeyJoinColumn(name="id")
public class FileResource extends Resource
{
   @Column(name = "subtype", nullable = false, length = 16)
   private String subResourceType;
   
   @Column(name = "filename", nullable = false)
   private String resFileName;
   
   @Column(name = "imgwidth", nullable = false)
   private int imageWidth = 0;

   @Column(name = "imgheight", nullable = false)
   private int imageHeight = 0;

   public String getSubResourceType()
   {
      return subResourceType;
   }

   public void setSubResourceType(String subResourceType)
   {
      this.subResourceType = subResourceType;
   }

   public String getResourceFileName()
   {
      return resFileName;
   }

   public void setResourceFileName(String resFileName)
   {
      this.resFileName = resFileName;
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

   public void setImageHeight(int imageHeighht)
   {
      this.imageHeight = imageHeighht;
   }
}

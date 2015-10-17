package org.hanbo.mvc.models.json;

import org.apache.commons.lang.StringUtils;

public class ImageResourceJsonResponse
{
   private String resourceId;
   private String resourceName;
   private String ownerId;
   private String formattedResourceValue;
   private int width;
   private int height;
   private float widthToHeightRatio;
   
   public String getResourceId()
   {
      return resourceId;
   }
   
   public void setResourceId(String resourceId)
   {
      this.resourceId = resourceId;
   }

   public String getResourceName()
   {
      return resourceName;
   }

   public void setResourceName(String resourceName)
   {
      this.resourceName = resourceName;
   }

   public String getFormattedResourceValue()
   {
      return formattedResourceValue;
   }

   public void setFormattedResourceValue(String formattedResourceValue)
   {
      this.formattedResourceValue = formattedResourceValue;
   }

   public int getWidth()
   {
      return width;
   }

   public void setWidth(int width)
   {
      this.width = width;
   }

   public int getHeight()
   {
      return height;
   }

   public void setHeight(int height)
   {
      this.height = height;
   }
   
   public boolean isValid()
   {
      return !StringUtils.isEmpty(this.resourceId)
         && !StringUtils.isEmpty(this.resourceName)
         && !StringUtils.isEmpty(this.formattedResourceValue)
         && this.width > 0 && this.height > 0;
   }

   public float getWidthToHeightRatio()
   {
      return widthToHeightRatio;
   }

   public void setWidthToHeightRatio(float widthToHeightRatio)
   {
      this.widthToHeightRatio = widthToHeightRatio;
   }

   public String getOwnerId()
   {
      return ownerId;
   }

   public void setOwnerId(String ownerId)
   {
      this.ownerId = ownerId;
   }
}

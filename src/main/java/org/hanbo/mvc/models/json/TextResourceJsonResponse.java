package org.hanbo.mvc.models.json;

import org.springframework.util.StringUtils;

public class TextResourceJsonResponse
{
   private String resourceId;
   private String resourceName;
   private String subType;
   private String resourceValue;
   private String formattedResourceValue;
   
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

   public String getSubType()
   {
      return subType;
   }

   public void setSubType(String subType)
   {
      this.subType = subType;
   }

   public String getResourceValue()
   {
      return resourceValue;
   }

   public void setResourceValue(String resourceValue)
   {
      this.resourceValue = resourceValue;
   }
   
   public boolean isValid()
   {
      return (!StringUtils.isEmpty(this.resourceId)
         && !StringUtils.isEmpty(this.resourceName)
         && !StringUtils.isEmpty(this.resourceValue)
         && !StringUtils.isEmpty(this.resourceValue));
   }

   public String getFormattedResourceValue()
   {
      return formattedResourceValue;
   }

   public void setFormattedResourceValue(String formattedResourceValue)
   {
      this.formattedResourceValue = formattedResourceValue;
   }
}

package org.hanbo.mvc.models;

import java.util.Date;

import org.hanbo.mvc.utilities.DateToString;

public class ResourceListItemDataModel
{
   private String resourceId;
   private String resourceName;
   private String resourceType;
   private String resourceSubType;
   private Date createDate;
   private Date updateDate;
   
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

   public String getResourceType()
   {
      return resourceType;
   }

   public void setResourceType(String resourceType)
   {
      this.resourceType = resourceType;
   }

   public String getResourceSubType()
   {
      return resourceSubType;
   }

   public void setResourceSubType(String resourceSubType)
   {
      this.resourceSubType = resourceSubType;
   }

   public Date getCreateDate()
   {
      return createDate;
   }

   public String getCreateDateAsString()
   {
      return DateToString.dateStringForDisplay(createDate);
   }
   
   public void setCreateDate(Date createDate)
   {
      this.createDate = createDate;
   }

   public Date getUpdateDate()
   {
      return updateDate;
   }

   public String getUpdateDateAsString()
   {
      return DateToString.dateStringForDisplay(updateDate);
   }
   
   public void setUpdateDate(Date updateDate)
   {
      this.updateDate = updateDate;
   }  
}

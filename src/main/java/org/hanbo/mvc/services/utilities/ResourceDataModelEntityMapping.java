package org.hanbo.mvc.services.utilities;

import java.util.ArrayList;
import java.util.List;

import org.hanbo.mvc.entities.FileResource;
import org.hanbo.mvc.entities.Resource;
import org.hanbo.mvc.entities.TextResource;
import org.hanbo.mvc.models.ResourceListItemDataModel;

public class ResourceDataModelEntityMapping
{
   public static ResourceListItemDataModel listItemFromEntity(Resource resource)
   {
      ResourceListItemDataModel retVal = new ResourceListItemDataModel();
      
      retVal.setResourceId(resource.getId());
      retVal.setResourceName(resource.getName());
      retVal.setResourceType(resource.getResourceType());
      
      if (resource.getResourceType().equals("text"))
      {
         retVal.setResourceSubType(((TextResource)resource).getSubResourceType());         
      }
      else
      {
         retVal.setResourceSubType(((FileResource)resource).getSubResourceType());                  
      }
      retVal.setCreateDate(resource.getCreateDate());
      retVal.setUpdateDate(resource.getUpdateDate());
      
      return retVal;
   }

   public static List<ResourceListItemDataModel> listItemsFromEntities(List<? extends Resource> resources)
   {
      List<ResourceListItemDataModel> retList = new ArrayList<ResourceListItemDataModel>();
      
      for(Resource res : resources)
      {
         ResourceListItemDataModel item = listItemFromEntity(res);
         retList.add(item);
      }
      
      return retList;
   }
}

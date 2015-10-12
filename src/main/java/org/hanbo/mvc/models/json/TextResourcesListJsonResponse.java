package org.hanbo.mvc.models.json;

import java.util.ArrayList;
import java.util.List;

import org.hanbo.mvc.models.ResourceListItemDataModel;

public class TextResourcesListJsonResponse
{
   private String ownerId;
   private int pageIdx;
   private List<ResourceListItemDataModel> resourceList;
   
   public TextResourcesListJsonResponse()
   {
      setResourceList(new ArrayList<ResourceListItemDataModel>());
   }

   public String getOwnerId()
   {
      return ownerId;
   }

   public void setOwnerId(String ownerId)
   {
      this.ownerId = ownerId;
   }
   
   public int getPageIdx()
   {
      return pageIdx;
   }

   public void setPageIdx(int pageIdx)
   {
      this.pageIdx = pageIdx;
   }
   
   public List<ResourceListItemDataModel> getResourceList()
   {
      return resourceList;
   }

   public void setResourceList(List<ResourceListItemDataModel> resourceList)
   {
      this.resourceList = resourceList;
   }
   
   public boolean isValid()
   {
      return this.resourceList != null && this.resourceList.size() > 0;
   }
}

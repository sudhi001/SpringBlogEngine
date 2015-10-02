package org.hanbo.mvc.models;

import java.util.ArrayList;
import java.util.List;

public class ResourceListPageDataModel
   extends ItemListPageDataModel
{
   private List<ResourceListItemDataModel> listOfResources;
   
   public ResourceListPageDataModel()
   {
      listOfResources = new ArrayList<ResourceListItemDataModel>();
   }

   public List<ResourceListItemDataModel> getListOfResources()
   {
      return listOfResources;
   }

   public void setListOfResources(List<ResourceListItemDataModel> listOfResources)
   {
      this.listOfResources = listOfResources;
   }
}

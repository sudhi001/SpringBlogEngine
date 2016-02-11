package org.hanbo.mvc.models;

import java.util.List;
import java.util.ArrayList;

public class GalleryDisplayPageDataModel
   extends ItemListPageDataModel
{
   private List<GalleryDisplayDetail> listItems;

   public GalleryDisplayPageDataModel()
   {
      listItems = new ArrayList<GalleryDisplayDetail>();
   }
   
   public List<GalleryDisplayDetail> getListItems()
   {
      return listItems;
   }

   public void setListItems(List<GalleryDisplayDetail> listItems)
   {
      this.listItems = listItems;
   }
}

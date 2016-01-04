package org.hanbo.mvc.models;

import java.util.ArrayList;
import java.util.List;

public class ImageDisplayPageDataModel
   extends ItemListPageDataModel
{
   private List<ImageDisplayDetail> listItems;
   
   public ImageDisplayPageDataModel()
   {
      setListItems(new ArrayList<ImageDisplayDetail>());
   }

   public List<ImageDisplayDetail> getListItems()
   {
      return listItems;
   }

   public void setListItems(List<ImageDisplayDetail> listItems)
   {
      this.listItems = listItems;
   }
}

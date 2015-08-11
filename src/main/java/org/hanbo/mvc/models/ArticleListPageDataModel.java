package org.hanbo.mvc.models;

import java.util.ArrayList;
import java.util.List;

public class ArticleListPageDataModel
   extends ItemListPageDataModel
{
   private List<ArticleListDataItem> listItems;
   
   public ArticleListPageDataModel()
   {
      setListItems(new ArrayList<ArticleListDataItem>());
   }

   public List<ArticleListDataItem> getListItems()
   {
      return listItems;
   }

   public void setListItems(List<ArticleListDataItem> listItems)
   {
      this.listItems = listItems;
   }
}

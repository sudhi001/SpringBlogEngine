package org.hanbo.mvc.models;

import java.util.ArrayList;
import java.util.List;

public class ArticleListPageDataModel
   extends ItemListPageDataModel
{
   private List<SimplifiedArticleDataModel> listItems;
   
   public ArticleListPageDataModel()
   {
      setListItems(new ArrayList<SimplifiedArticleDataModel>());
   }

   public List<SimplifiedArticleDataModel> getListItems()
   {
      return listItems;
   }

   public void setListItems(List<SimplifiedArticleDataModel> listItems)
   {
      this.listItems = listItems;
   }
}

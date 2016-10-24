package org.hanbo.mvc.models.json;

import java.util.ArrayList;
import java.util.List;

import org.hanbo.mvc.models.ResourceListItemDataModel;

public class ArticleIconsJsonDataModel
{
   private int currentPageIdx;
   
   private List<ResourceListItemDataModel> articleIconList;

   public ArticleIconsJsonDataModel()
   {
      currentPageIdx = 0;
      articleIconList = new ArrayList<ResourceListItemDataModel>();
   }
   
   public int getCurrentPageIdx()
   {
      return currentPageIdx;
   }
   
   public void setCurrentPageIdx(int currentPageIdx)
   {
      this.currentPageIdx = currentPageIdx;
   }

   public void setArticleIconList(List<ResourceListItemDataModel> articleIconList)
   {
      this.articleIconList = articleIconList;
   }
   
   public List<ResourceListItemDataModel> getArticleIconList()
   {
      return articleIconList;
   }
}

package org.hanbo.mvc.models;

public class ArticleDataModel extends SimplifiedArticleDataModel
{
   private String articleContent;

   public ArticleDataModel()
   {      
   }
   
   public String getArticleContent()
   {
      return articleContent;
   }
   
   public void setArticleContent(String articleContent)
   {
      this.articleContent = articleContent;
   }
}

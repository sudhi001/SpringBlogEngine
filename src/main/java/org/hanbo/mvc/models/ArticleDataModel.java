package org.hanbo.mvc.models;

public class ArticleDataModel extends SimplifiedArticleDataModel
{
   private boolean previewMode;
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

   public boolean isPreviewMode()
   {
      return previewMode;
   }

   public void setPreviewMode(boolean previewMode)
   {
      this.previewMode = previewMode;
   }
}

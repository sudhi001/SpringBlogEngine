package org.hanbo.mvc.models;

public class PermaLinkDataModel
{
   private String linkId;
   private String linkPath;
   private boolean pageReplacement;
   private String articleId;
   private String articleTitle;   
   private String authorId;
   private String authorName;
   
   public String getLinkId()
   {
      return linkId;
   }
   
   public void setLinkId(String linkId)
   {
      this.linkId = linkId;
   }

   public String getLinkPath()
   {
      return linkPath;
   }

   public void setLinkPath(String linkPath)
   {
      this.linkPath = linkPath;
   }
   
   public boolean isPageReplacement()
   {
      return pageReplacement;
   }

   public void setPageReplacement(boolean pageReplacement)
   {
      this.pageReplacement = pageReplacement;
   }

   public String getArticleId()
   {
      return articleId;
   }

   public void setArticleId(String articleId)
   {
      this.articleId = articleId;
   }

   public String getAuthorId()
   {
      return authorId;
   }

   public void setAuthorId(String authorId)
   {
      this.authorId = authorId;
   }

   public String getAuthorName()
   {
      return authorName;
   }

   public void setAuthorName(String authorName)
   {
      this.authorName = authorName;
   }

   public String getArticleTitle()
   {
      return articleTitle;
   }

   public void setArticleTitle(String articleTitle)
   {
      this.articleTitle = articleTitle;
   }
}

package org.hanbo.mvc.models.json;

public class NewPermaLinkJsonRequest
{
   private String articleId;
   private String authorId;
   private String permaLinkPath;
   private boolean pageReplacement;
   
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
   
   public String getPermaLinkPath()
   {
      return permaLinkPath;
   }
   
   public void setPermaLinkPath(String permaLinkPath)
   {
      this.permaLinkPath = permaLinkPath;
   }
   
   public boolean isPageReplacement()
   {
      return pageReplacement;
   }
   
   public void setPageReplacement(boolean pageReplacement)
   {
      this.pageReplacement = pageReplacement;
   }
}

package org.hanbo.mvc.models.json;

public class NewPermaLinkJsonResponse
{
   private String articleId;
   private String permaLinkId;
   private String operationStatus;
   
   public String getArticleId()
   {
      return articleId;
   }
   
   public void setArticleId(String articleId)
   {
      this.articleId = articleId;
   }
   
   public String getPermaLinkId()
   {
      return permaLinkId;
   }
   
   public void setPermaLinkId(String permaLinkId)
   {
      this.permaLinkId = permaLinkId;
   }
   
   public String getOperationStatus()
   {
      return operationStatus;
   }
   
   public void setOperationStatus(String operationStatus)
   {
      this.operationStatus = operationStatus;
   }
}

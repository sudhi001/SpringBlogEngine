package org.hanbo.mvc.models.json;

public class UserCommentActionInputDataModel
{
   private String articleId;
   
   private String commentId;

   public String getArticleId()
   {
      return articleId;
   }

   public void setArticleId(String articleId)
   {
      this.articleId = articleId;
   }

   public String getCommentId()
   {
      return commentId;
   }

   public void setCommentId(String commentId)
   {
      this.commentId = commentId;
   }
}

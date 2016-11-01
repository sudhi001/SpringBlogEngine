package org.hanbo.mvc.models;

import java.util.List;

public class ArticleCommentDataModel extends VisitorCommentDataModel
{
   private String articleId;
   
   private String commentUserId;
   
   private List<ArticleCommentDataModel> childComments;

   public String getArticleId()
   {
      return articleId;
   }

   public void setArticleId(String articleId)
   {
      this.articleId = articleId;
   }

   public String getCommentUserId()
   {
      return commentUserId;
   }

   public void setCommentUserId(String commentUserId)
   {
      this.commentUserId = commentUserId;
   }

   public List<ArticleCommentDataModel> getChildComments()
   {
      return childComments;
   }

   public void setChildComments(List<ArticleCommentDataModel> childComments)
   {
      this.childComments = childComments;
   }
   
   public void validateDataModel()
   {
      
   }
}

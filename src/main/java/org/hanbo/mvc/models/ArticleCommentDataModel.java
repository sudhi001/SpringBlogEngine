package org.hanbo.mvc.models;

import java.util.ArrayList;
import java.util.List;

import org.hanbo.mvc.exceptions.WebAppException;
import org.springframework.util.StringUtils;

public class ArticleCommentDataModel extends VisitorCommentDataModel
{
   private String articleId;
   
   private String commentUserId;
   
   private List<ArticleCommentDataModel> childComments;

   public ArticleCommentDataModel()
   {
      super();
      childComments = new ArrayList<ArticleCommentDataModel>();
   }
   
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
      if (StringUtils.isEmpty(this.articleId))
      {
         throw new WebAppException("Associated article id is null or empty.",
            WebAppException.ErrorType.DATA);
      }
      
      if (StringUtils.isEmpty(this.commentUserId))
      {
         super.validateCommenterInfoNotEmpty();
      }
      else
      {
         super.validateCommenterInfo();
      }
      
      super.validateCommentContent();
   }
}

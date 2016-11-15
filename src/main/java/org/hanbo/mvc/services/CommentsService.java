package org.hanbo.mvc.services;

import java.util.List;

import org.hanbo.mvc.models.ArticleCommentDataModel;
import org.hanbo.mvc.models.UserArticleCommentsPageDataModel;

public interface CommentsService
{
   void addArticleComment(ArticleCommentDataModel commentToSave);
   
   ArticleCommentDataModel loadArticleComment(String articleId, String commentId);
   
   UserArticleCommentsPageDataModel getUnapprovedArticleComments(int pageIdx);

   List<ArticleCommentDataModel> getArticleComments(String articleId, int pageIdx);
   
   List<ArticleCommentDataModel> getViewableArticleComments(String articleId);

   boolean approveComment(String articleId, String commentId);

   boolean deleteComment(String articleId, String commentId);
}

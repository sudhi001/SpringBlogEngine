package org.hanbo.mvc.services;

import java.util.List;

import org.hanbo.mvc.models.ArticleCommentDataModel;
import org.hanbo.mvc.models.UserArticleCommentsPageDataModel;
import org.hanbo.mvc.models.UserCommentsPageDataModel;
import org.hanbo.mvc.models.json.CommentJsonDataModel;

public interface CommentsService
{
   void addArticleComment(ArticleCommentDataModel commentToSave);
   
   ArticleCommentDataModel loadArticleComment(String articleId, String commentId);
   
   UserArticleCommentsPageDataModel getUnapprovedArticleComments(int pageIdx);

   UserCommentsPageDataModel getUnapprovedComments(int pageIdx);
   
   List<ArticleCommentDataModel> getArticleComments(String articleId, int pageIdx);
   
   List<ArticleCommentDataModel> getViewableArticleComments(String articleId);

   CommentJsonDataModel loadComment(String commentId);
   
   boolean approveComment(String commentId);

   boolean deleteComment(String commentId);
}

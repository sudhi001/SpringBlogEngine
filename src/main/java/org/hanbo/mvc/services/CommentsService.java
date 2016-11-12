package org.hanbo.mvc.services;

import java.util.List;

import org.hanbo.mvc.models.ArticleCommentDataModel;

public interface CommentsService
{
   void addArticleComment(ArticleCommentDataModel commentToSave);
   
   ArticleCommentDataModel loadArticleComment(String articleId, String commentId);
   
   List<ArticleCommentDataModel> getArticleComments(String articleId, int pageIdx);
   
   List<ArticleCommentDataModel> getViewableArticleComments(String articleId);
}

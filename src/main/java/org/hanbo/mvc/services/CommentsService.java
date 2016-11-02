package org.hanbo.mvc.services;

import org.hanbo.mvc.models.ArticleCommentDataModel;

public interface CommentsService
{
   void addArticleComment(ArticleCommentDataModel commentToSave);
}

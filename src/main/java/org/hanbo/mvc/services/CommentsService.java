package org.hanbo.mvc.services;

public interface CommentsService
{
   void addArticleComment(String articleId, String commentUserId,
      String commentAuthor, String commentAuthorEmail, String commentTitle,
      String commentContent);
}

package org.hanbo.mvc.repositories;

import java.util.List;

import org.hanbo.mvc.entities.VisitorComment;
import org.hibernate.Session;

public interface CommentsRepository
{
   void saveArticleComment(String articleId, String commentOwnerId,
      String parentCommentid, VisitorComment commentToSave);
   
   List<VisitorComment> loadArticleComments(String articleId, int maxResultsCount);
   
   void deleteArticleComment(String articleId, String commentId);   
   
   void deleteArticleComments(String articleId);
   
   void deleteArticleComments(Session session, String articleId);
}

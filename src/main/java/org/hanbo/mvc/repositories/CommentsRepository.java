package org.hanbo.mvc.repositories;

import java.util.List;

import org.hanbo.mvc.entities.VisitorComment;
import org.hibernate.Session;

public interface CommentsRepository
{
   void saveArticleComment(String articleId, String commentOwnerId,
      String parentCommentid, VisitorComment commentToSave);
   
   List<VisitorComment> loadArticleComments(String articleId, int pageIdx, int itemsCount);

   List<VisitorComment> loadArticleComments(Session session, String queryString, String articleId, int pageIdx, int maxResultsCount);

   List<VisitorComment> loadArticleViewableComments(String articleId, int maxResultsCount);
   
   long getUnapprovedArticleCommentsCount();
   
   List<VisitorComment> getUnapprovedArticleComments(int pageIdx, int maxItemsCount);
   
   VisitorComment loadArticleComment(String articleId, String commentId);
   
   void deleteArticleComment(String articleId, String commentId);   
   
   void deleteArticleComments(String articleId);
   
   void deleteArticleComments(Session session, String articleId);
   
   void unassociateArticleComments(Session session, String articleId);
   
   boolean approveComment(String commentId);

   boolean deleteComment(String commentId);

   VisitorComment loadComment(String commentId);
}

package org.hanbo.mvc.repositories;

import java.util.List;

import org.hanbo.mvc.entities.VisitorComment;
import org.hibernate.Session;

public interface CommentsRepository
{
   void saveArticleComment(String articleId, String commentOwnerId,
      String parentCommentId, VisitorComment commentToSave);
   
   void saveImageComment(String imageId, String commentOwnerId,
      String parentCommentId, VisitorComment commentToSave);

   List<VisitorComment> loadArticleComments(String articleId, int pageIdx, int itemsCount);

   List<VisitorComment> loadComments(Session session, String queryString, String refObjIdName,
      String refObjId, int pageIdx, int maxResultsCount);

   List<VisitorComment> loadArticleViewableComments(String articleId, int maxResultsCount);
   
   List<VisitorComment> loadImageViewableComments(String imageId, int maxResultsCount);

   long getUnapprovedCommentsCount();
   
   List<VisitorComment> getUnapprovedComments(int pageIdx, int maxItemsCount);
   
   VisitorComment loadArticleComment(String articleId, String commentId);
   
   VisitorComment loadImageComment(String imageId, String commentId);

   void deleteArticleComment(String articleId, String commentId);   
   
   void deleteArticleComments(String articleId);
   
   void deleteArticleComments(Session session, String articleId);
   
   void unassociateArticleComments(Session session, String articleId);
   
   boolean approveComment(String commentId);

   boolean deleteComment(String commentId);

   VisitorComment loadComment(String commentId);
   
   long getArticleViewableCommentsCount(String articleId);

   long getImageViewableCommentsCount(String imageId);
   
   long getUserStatusViewableCommentsCount(String userStatusId);
   
   long getUserStatusViewableCommentsCount(Session session, String userStatusId);
}

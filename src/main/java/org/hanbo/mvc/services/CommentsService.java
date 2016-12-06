package org.hanbo.mvc.services;

import java.util.List;

import org.hanbo.mvc.models.CommentRefObjectType;
import org.hanbo.mvc.models.UserCommentsPageDataModel;
import org.hanbo.mvc.models.VisitorCommentDataModel;
import org.hanbo.mvc.models.json.CommentJsonDataModel;

public interface CommentsService
{
   void addArticleComment(VisitorCommentDataModel commentToSave);
   
   void addImageComment(VisitorCommentDataModel comment);
   
   VisitorCommentDataModel loadArticleComment(String articleId, String commentId);

   VisitorCommentDataModel loadComment(String refObjectId, String commentId, String refObjectType);
   
   UserCommentsPageDataModel getUnapprovedComments(int pageIdx);
   
   List<VisitorCommentDataModel> getArticleComments(String articleId, int pageIdx);
   
   List<VisitorCommentDataModel> getViewableArticleComments(String articleId);
   
   List<VisitorCommentDataModel> getViewableImageComments(String imageId);
   
   int getViewableCommentsCount(String refOjectId, CommentRefObjectType objectType);

   CommentJsonDataModel loadComment(String commentId);
   
   boolean approveComment(String commentId);

   boolean deleteComment(String commentId);
}

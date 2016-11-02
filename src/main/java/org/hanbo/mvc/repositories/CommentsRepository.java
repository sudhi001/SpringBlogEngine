package org.hanbo.mvc.repositories;

import org.hanbo.mvc.entities.VisitorComment;

public interface CommentsRepository
{
   void saveArticleComment(String articleId, String commentOwnerId,
      String parentCommentid, VisitorComment commentToSave);
}

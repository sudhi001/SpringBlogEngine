package org.hanbo.mvc.repositories;

import org.hanbo.mvc.entities.VisitorComment;

public interface CommentsRepository
{
   void saveComment(VisitorComment commentToSave);
}

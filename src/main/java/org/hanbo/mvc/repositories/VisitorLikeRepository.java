package org.hanbo.mvc.repositories;

import org.hanbo.mvc.entities.VisitorLike;

public interface VisitorLikeRepository
{
   void saveVisitorLike(VisitorLike likeToSave);
   
   long getArticleVisitorLikesCount(String refObjectId);
   
   long getImageVisitorLikesCount(String refObjectId);
}

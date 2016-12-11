package org.hanbo.mvc.repositories;

import org.hanbo.mvc.entities.VisitorLike;
import org.hibernate.Session;

public interface VisitorLikeRepository
{
   void addVisitorLikeToArticle(String articleId, boolean likeIt, String sourceIp);
   
   void addVisitorLikeToImage(String imageId, boolean likeIt, String sourceIp);
   
   void saveVisitorLike(VisitorLike likeToSave);
   
   long getArticleVisitorLikesCount(String refObjectId);

   long getArticleVisitorDislikesCount(String refObjectId);
   
   long getImageVisitorLikesCount(String refObjectId);

   long getImageVisitorDislikesCount(String refObjectId);

   long getUserStatusVisitorLikesCount(String refObjectId);

   long getUserStatsVisitorDislikesCount(String refObjectId);

   long getUserStatusVisitorLikesCount(Session session, String refObjectId);

   long getUserStatusVisitorDislikesCount(Session session, String refObjectId);
}

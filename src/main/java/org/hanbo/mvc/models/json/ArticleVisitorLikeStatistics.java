package org.hanbo.mvc.models.json;

public class ArticleVisitorLikeStatistics
{
   private String articleId;
   
   private long likeCount;
   
   private long dislikeCount;

   public String getArticleId()
   {
      return articleId;
   }

   public void setArticleId(String articleId)
   {
      this.articleId = articleId;
   }

   public long getLikeCount()
   {
      return likeCount;
   }

   public void setLikeCount(long likeCount)
   {
      this.likeCount = likeCount;
   }

   public long getDislikeCount()
   {
      return dislikeCount;
   }

   public void setDislikeCount(long dislikeCount)
   {
      this.dislikeCount = dislikeCount;
   }
}

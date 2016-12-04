package org.hanbo.mvc.repositories;

import java.util.List;
import org.hanbo.mvc.entities.Article;
import org.hanbo.mvc.entities.ArticleIcon;
import org.hibernate.Session;

public interface ArticlesRepository
{
   void saveArticle(Article article);
   
   Article getArticleById(String articleId);
   
   Article getArticleById(
      String articleId, String authorId
   );
   
   long getAllArticlesCountsByUserId(
      String authorId
   );

   List<Article> getAllArticlesByUserId(
      String authorId, int pageIdx,
      int pageItemCount, boolean sortDsc
   );
   
   String getArticleOwnerId(String articleId);
   
   void deleteArticle(String articleId);
   
   void publishArticle(String articleId,
      boolean articleToPublish);
   
   long getViewableArticlesCount(String articleType);
   
   List<Article> getViewableArticles(
      String articleType, int pageIdx, int itemsCount);
   
   Article findArticleById(String articleId);
   
   Article findArticleById(Session session, String articleId);
   
   void saveArticleIcon(ArticleIcon articleIcon);
   
   void saveArticleIcon(String articleId, String iconFileid);
   
   void deleteArticleIcon(String articleId);
}
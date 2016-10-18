package org.hanbo.mvc.repositories;

import java.util.Date;
import java.util.List;

import org.hanbo.mvc.entities.Article;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@SuppressWarnings("unchecked")
public class ArticlesRepositoryImpl implements ArticlesRepository
{
   @Autowired
   private SessionFactory _sessionFactory;
   
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   @Override
   public void saveArticle(Article article)
   {
      Session session = _sessionFactory.getCurrentSession();
      session.saveOrUpdate(article);
   }

   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   @Override
   public Article getReportById(String articleId)
   {
      Session session = _sessionFactory.getCurrentSession();
      Query objQuery = session.createQuery("select article from Article article "
            + "where article.id = :articleId")
        .setMaxResults(1)
        .setFirstResult(0)
        .setParameter("articleId", articleId);
      
      List<Article> foundObjs = objQuery.list();
      return getFirstArticle(foundObjs);
   }

   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   @Override
   public Article getReportById(String articleId, String authorId)
   {
      Session session = _sessionFactory.getCurrentSession();
      Query objQuery = session.createQuery("select article from Article article "
            + "where article.id = :articleId and article.author.id = :authorId")
         .setMaxResults(1)
         .setFirstResult(0)
         .setParameter("articleId", articleId)
         .setParameter("authorId", authorId);
      
      List<Article> foundObjs = objQuery.list();
      return getFirstArticle(foundObjs);
   }
   
   private Article getFirstArticle(List<Article> foundObjs)
   {
      if (foundObjs.size() > 0)
      {
         Article retVal = foundObjs.get(0);
         retVal.getAuthor().getId();
         retVal.getAuthor().getUserName();
         
         loadArticleMetadata(retVal);
         return retVal;
      }

      return null;
   }

   @Override
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   public long getAllArticlesCountsByUserId(
      String authorId
   )
   {
      Session session = _sessionFactory.getCurrentSession();
      
      StringBuilder querySb = new StringBuilder();
      querySb.append("Select count(article) from Article article");
      querySb.append(" where article.author.id = :authorId");
      
      Query objQuery = session.createQuery(querySb.toString())
            .setMaxResults(1)
            .setFirstResult(0)
            .setParameter("authorId", authorId);

      List<Long> objList = objQuery.list();
      if (objList.size() > 0)
      {
         return objList.get(0);
      }
      
      return 0L;
   }
   
   @Override
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   public List<Article> getAllArticlesByUserId(
      String authorId, int pageIdx,
      int pageItemCount, boolean sortDsc
   )
   {
      Session session = _sessionFactory.getCurrentSession();
      
      StringBuilder querySb = new StringBuilder();
      querySb.append("Select article from Article article");
      querySb.append(" where article.author.id = :authorId");
      if (sortDsc)
      {
         querySb.append(" order by article.updateDate desc");
      }
      else
      {
         querySb.append(" order by article.updateDate asc");
      }
      
      int pageItemStart = pageIdx *pageItemCount;      
      Query objQuery = session.createQuery(querySb.toString())
         .setMaxResults(pageItemCount)
         .setFirstResult(pageItemStart)
         .setParameter("authorId", authorId);
      
      List<Article> articlesRet = objQuery.list();
      for (Article article : articlesRet)
      {
         article.getAuthor().getUserName();
         article.getAuthor().getId();
         
         loadArticleMetadata(article);
      }

      return articlesRet;
   }
   
   @Override
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   public String getArticleOwnerId(String articleId)
   {
      Session session = _sessionFactory.getCurrentSession();
      Query query = session.createQuery(
         "select article.author.id from Article article where article.id = :articleId"
      )
         .setParameter("articleId", articleId)
         .setMaxResults(1);
      List<String> objsFound = query.list();
      if (objsFound.size() > 0)
      {
         return objsFound.get(0);
      }
      
      return "";
   }
   
   @Override
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   public void deleteArticle(String articleId)
   {
      Session session = _sessionFactory.getCurrentSession();

      Query query = session.createQuery(
         "delete from Article where id = :articleId"
      )
         .setParameter("articleId", articleId);
      
      query.executeUpdate();
   }
   
   @Override
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   public void publishArticle(String articleId,
      boolean articleToPublish)
   {
      Session session = _sessionFactory.getCurrentSession();
      Query query = session.createQuery(
         "update Article set published = :articleToPublish, updateDate = :updateDate where id = :articleId"
      )
         .setParameter("articleId", articleId)
         .setParameter("articleToPublish", articleToPublish)
         .setParameter("updateDate", new Date());
      
      query.executeUpdate();
   }

   @Override
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   public long getViewableArticlesCount(String articleType)
   {
      Session session = _sessionFactory.getCurrentSession();
      Query query = session.createQuery(
         "select count(article) from Article article where article.articleType = :articleType and article.published = true"
      )
         .setParameter("articleType", articleType)
         .setMaxResults(1)
         .setFirstResult(0);
      
      List<Long> objsCount = query.list();
      if (objsCount.size() > 0)
      {
         return objsCount.get(0);
      }
      
      return 0L;
   }

   @Override
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   public List<Article> getViewableArticles(
      String articleType, int pageIdx, int itemsCount)
   {
      int firstResult = pageIdx * itemsCount;
      
      Session session = _sessionFactory.getCurrentSession();
      Query query = session.createQuery(
         "select article from Article article where article.articleType = :articleType "
         + "and article.published = true order by article.updateDate desc"
      )
         .setParameter("articleType", articleType)
         .setMaxResults(itemsCount)
         .setFirstResult(firstResult);
      
      List<Article> objsList = query.list();
      
      if (objsList.size() > 0)
      {
         for (Article article : objsList)
         {
            loadArticleMetadata(article);
         }
      }
      
      return objsList;
   }
   
   @Override
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   public Article findArticleById(String articleId)
   {
      Session session = _sessionFactory.getCurrentSession();
      return internalFindArticleById(session, articleId);
   }
   
   static Article internalFindArticleById(Session session, String articleId)
   {
      Query query = session.createQuery(
         "select article from Article article where article.id = :articleId"
      )
         .setParameter("articleId", articleId);
      
      List<Article> objList =  query.list();
      if (objList.size() > 0)
      {
         Article article = objList.get(0);
         loadArticleMetadata(article);
         
         return article;
      }
      
      return null;
   }
   
   static void loadArticleMetadata(Article article)
   {
      if (article != null && article.getAuthor() != null)
      {
         article.getAuthor().getId();
         article.getAuthor().getUserName();
         
         if (article.getAuthor().getUserProfile() != null)
         {
            article.getAuthor().getUserProfile().getFirstName();
            article.getAuthor().getUserProfile().getLastName();

            if (article.getAuthor().getUserProfile().getUserIcon() != null)
            {
               article.getAuthor().getUserProfile().getUserIcon().getId();
            }
         }
      }
   }
}

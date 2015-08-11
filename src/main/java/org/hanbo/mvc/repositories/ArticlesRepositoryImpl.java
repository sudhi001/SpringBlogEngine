package org.hanbo.mvc.repositories;

import java.util.List;

import org.hanbo.mvc.entities.Article;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@SuppressWarnings("unchecked")
public class ArticlesRepositoryImpl implements ArticlesRepository
{
   @Autowired
   private SessionFactory _sessionFactory;
   
   @Transactional
   @Override
   public void saveArticle(Article article)
   {
      Session session = _sessionFactory.getCurrentSession();
      session.saveOrUpdate(article);
   }

   @Transactional
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

   @Transactional
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
         
         return retVal;
      }

      return null;
   }
}

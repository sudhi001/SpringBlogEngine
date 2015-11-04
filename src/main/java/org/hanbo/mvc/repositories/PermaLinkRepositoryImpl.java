package org.hanbo.mvc.repositories;

import java.util.List;

import org.hanbo.mvc.entities.Article;
import org.hanbo.mvc.entities.PermaLink;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@SuppressWarnings("unchecked")
public class PermaLinkRepositoryImpl implements PermaLinkRepository
{
   @Autowired
   private SessionFactory _sessionFactory;
   
   @Override
   @Transactional
   public void savePermaLink(PermaLink linkToSave)
   {
      Session session = _sessionFactory.getCurrentSession();
      session.saveOrUpdate(linkToSave);
   }
   
   @Override
   @Transactional
   public PermaLink getPermaLinkByArticle(String articleId)
   {
      Session session = _sessionFactory.getCurrentSession();
      
      Query query =
      session.createQuery("select permaLink from PermaLink permaLink "
            + "where permaLink.article.id = :articleId")
         .setParameter("articleId", articleId);

      List<PermaLink> objList = query.list();
      if (objList.size() > 0)
      {
         PermaLink retVal = objList.get(0);
         retVal.getAssociatedAuthor().getId();
         retVal.getAssociatedAuthor().getUserName();
         retVal.getAssociatedArticle().getId();
         
         return retVal;
      }
      
      return null;
   }
   
   @Override
   @Transactional
   public void deletePermaLinkByArticle(String articleId)
   {
      Session session = _sessionFactory.getCurrentSession();
      
      Query query =
      session.createQuery("delete from PermaLink "
            + "where article.id = :articleId")
         .setParameter("articleId", articleId);
      query.executeUpdate();
   }
   
   @Override
   @Transactional
   public Article getArticleByPermaLink(String permaLinkValue)
   {
      Session session = _sessionFactory.getCurrentSession();
      
      Query query =
      session.createQuery("select permaLink.article from PermaLink permaLink "
            + "where permaLink.path = :permaLinkValue")
         .setParameter("permaLinkValue", permaLinkValue);

      List<Article> objList = query.list();
      if (objList.size() > 0)
      {
         Article retVal = objList.get(0);
         retVal.getAuthor().getUserName();
         retVal.getAuthor().getId();
         
         if (retVal.getArticleIcon() != null)
         {
            retVal.getArticleIcon().getId();
            retVal.getArticleIcon().getIconResource().getId();
         }
         
         return retVal;
      }
      
      return null;
   }
}

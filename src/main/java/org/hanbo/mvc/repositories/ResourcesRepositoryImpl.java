package org.hanbo.mvc.repositories;

import java.util.List;

import org.hanbo.mvc.entities.Article;
import org.hanbo.mvc.entities.ArticleIcon;
import org.hanbo.mvc.entities.FileResource;
import org.hanbo.mvc.entities.Resource;
import org.hanbo.mvc.entities.TextResource;
import org.hanbo.mvc.exceptions.WebAppException;
import org.hanbo.mvc.utilities.IdUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("unchecked")
@Repository
public class ResourcesRepositoryImpl implements ResourcesRepository
{
   @Autowired
   private SessionFactory _sessionFactory;

   @Override
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   public void saveResource(Resource resToSave)
   {
      Session session = _sessionFactory.getCurrentSession();
      session.saveOrUpdate(resToSave);
   }

   @Override
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   public List<Resource> getResourcesByOwnerId(
      String ownerId, int pageIdx,
      int itemsCount
   )
   {
      Session session = _sessionFactory.getCurrentSession();
      
      Query objQuery = session.createQuery(
         "select resource from Resource "
         + "resource where resource.owner.id = :ownerId "
         + "order by resource.updateDate desc")
         .setParameter("ownerId", ownerId)
         .setMaxResults(itemsCount)
         .setFirstResult(pageIdx * itemsCount);
      
      List<Resource> retList =  objQuery.list();
      
      for (Resource res : retList)
      {
         res.getOwner().getId();
         res.getOwner().getUserName();
      }
      
      return retList;
   }
   
   @Override
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   public long getResourceCountByOwnerId(String ownerId)
   {
      Session session = _sessionFactory.getCurrentSession();
      
      Query objQuery = session.createQuery(
         "select count(resource) from Resource "
         + "resource where resource.owner.id = :ownerId")
         .setParameter("ownerId", ownerId);
      
      List<Long> retList =  objQuery.list();
      
      if (retList.size() > 0)
      {
         return retList.get(0);
      }
      
      return 0L;
   }

   @Override
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   public Resource getResourceByOwner(String resourceId, String ownerId)
   {
      Session session = _sessionFactory.getCurrentSession();
      
      Query objQuery = session.createQuery(
         "select resource from Resource resource "
         + "where resource.owner.id = :ownerId and resource.id = :resourceId")
         .setParameter("resourceId", resourceId)
         .setParameter("ownerId", ownerId).setMaxResults(1);
      
      List<Resource> objList = objQuery.list();
      if (objList.size() > 0)
      {
         return objList.get(0);
      }
      
      return null;
   }
   
   @Override
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   public void deleteResource(Resource resource)
   {
      Session session = _sessionFactory.getCurrentSession();
      
      deleteArticleIconByResId(session, resource.getId());
      
      session.delete(resource);
   }
   
   @Override
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   public List<Resource> getResourcesByTypeAndOwnerId(
      String ownerId, String resourceType, int pageIdx, int itemsCount)
   {
      Session session = _sessionFactory.getCurrentSession();

      Query objQuery = session.createQuery(
         "select resource from Resource resource"
         + " where resource.owner.id = :ownerId"
         + " and resource.resType = :resourceType"
         + " order by resource.updateDate desc")
         .setParameter("ownerId", ownerId)
         .setParameter("resourceType", resourceType)
         .setFirstResult(pageIdx * itemsCount)
         .setMaxResults(itemsCount);

      List<Resource> objList = objQuery.list();
      return objList;
   }
   
   @Override
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   public List<FileResource> getImageResourcesByOwnerId(
      String ownerId, int pageIdx, int itemsCount)
   {
      Session session = _sessionFactory.getCurrentSession();

      Query objQuery = session.createQuery(
         "select resource from FileResource resource"
         + " where resource.owner.id = :ownerId"
         + " and resource.subResourceType = :subType"
         + " order by resource.updateDate desc")
         .setParameter("ownerId", ownerId)
         .setParameter("subType", "image")
         .setFirstResult(pageIdx * itemsCount)
         .setMaxResults(itemsCount);

      List<FileResource> objList = objQuery.list();
      return objList;
   }

   @Override
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   public Resource getArticleIcon(String articleId)
   {
      Session session = _sessionFactory.getCurrentSession();
      
      Query objQuery = session.createQuery(
            "select articleIcon.iconResource from ArticleIcon articleIcon"
            + " where articleIcon.article.id = :articleId")
            .setParameter("articleId", articleId)
            .setMaxResults(1);
      
      List<Resource> objList = objQuery.list();
      if (objList.size() > 0)
      {
         return objList.get(0);
      }
      
      return null;
   }
   
   @Override
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   public List<FileResource> getIconResources(
      String ownerId, int pageIdx, int itemsCount)
   {
      Session session = _sessionFactory.getCurrentSession();
      
      Query objQuery = session.createQuery(
            "select fileResource from FileResource fileResource"
            + " where fileResource.owner.id = :ownerId"
            + " and fileResource.imageWidth = fileResource.imageHeight")
            .setParameter("ownerId", ownerId)
            .setFirstResult(pageIdx * itemsCount)
            .setMaxResults(itemsCount);
      
      List<FileResource> objList = objQuery.list();
      return objList;
   }
   
   @Override
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   public void setIconToArticle(String articleId, String resourceId)
   {
      Session session = _sessionFactory.getCurrentSession();

      Article article = 
      ArticlesRepositoryImpl.internalFindArticleById(session, articleId);
      if (article == null)
      {
         throw new WebAppException(
            String.format("Unable to find article with id [%s]", articleId),
            WebAppException.ErrorType.DATA);
      }

      FileResource res =  getFileResourceById(session, resourceId);
      if (res == null)
      {
         throw new WebAppException(
            String.format("Unable to find file resource with id [%s]", resourceId),
            WebAppException.ErrorType.DATA);         
      }
      
      Query objQuery = session.createQuery(
            "select articleIcon from ArticleIcon articleIcon"
            + " where articleIcon.article.id = :articleId")
            .setParameter("articleId", articleId)
            .setMaxResults(1);
      
      List<ArticleIcon> retObjs = objQuery.list();
      if (retObjs.size() > 0)
      {
         ArticleIcon articleIcon = retObjs.get(0);
         articleIcon.setIconResource(res);
         
         session.saveOrUpdate(articleIcon);
      }
      else
      {
         ArticleIcon articleIcon = new ArticleIcon();
         articleIcon.setId(IdUtil.generateUuid());
         articleIcon.setArticle(article);
         articleIcon.setIconResource(res);
         session.saveOrUpdate(articleIcon);
      }
   }
   
   @Override
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   public boolean deleteIconToArticle(String articleId)
   {
      Session session = _sessionFactory.getCurrentSession();
      Query objQuery = session.createQuery(
            "select articleIcon from ArticleIcon articleIcon"
            + " where articleIcon.article.id = :articleId")
            .setParameter("articleId", articleId)
            .setMaxResults(1);
      
      List<ArticleIcon> retObjs = objQuery.list();
      if (retObjs.size() > 0)
      {
         ArticleIcon articleIcon = retObjs.get(0);
         session.delete(articleIcon);
         return true;
      }
      
      return false;
   }
   
   @Override
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   public boolean isArticleIconAlreadyExist(String resourceId)
   {
      Session session = _sessionFactory.getCurrentSession();
      Query objQuery = session.createQuery(
            "select articleIcon from ArticleIcon articleIcon"
            + " where articleIcon.iconResource.id = :resourceId")
            .setParameter("resourceId", resourceId)
            .setMaxResults(1);
      
      List<ArticleIcon> retObjs = objQuery.list();
      return retObjs.size() > 0;
   }
   
   protected static FileResource getFileResourceById(Session session, String resourceId)
   {
      return (FileResource)getResourceById(session, resourceId);
   }
   
   protected static TextResource getTextResourceById(Session session, String resourceId)
   {      
      return (TextResource)getResourceById(session, resourceId);
   }
   
   private static Resource getResourceById(Session session, String resourceId)
   {
      Query objQuery = session.createQuery(
         "select resource from Resource resource where resource.id = :resourceId")
         .setParameter("resourceId", resourceId)
         .setMaxResults(1);
      
      List<Resource> objsFound =objQuery.list();
      if (objsFound.size() > 0)
      {
         return objsFound.get(0);
      }
      
      return null;
   }
   
   private static void deleteArticleIcon(Session session, String fieldName, String paramName, String idVal)
   {
      StringBuilder sb = new StringBuilder();
      sb.append("delete from ArticleIcon articleIcon");
      sb.append(String.format(" where %s = :%s", fieldName, paramName));
      
      Query delQuery = session.createQuery(sb.toString())
         .setParameter(paramName, idVal);
      
      delQuery.executeUpdate();
   }
   
   static void deleteArticleIconByResId(Session session, String resourceId)
   {
      deleteArticleIcon(
         session, "iconResource.id", "resourceId", resourceId);
   }
   
   static void deleteArticleIconByArticleId(Session session, String articleId)
   {
      deleteArticleIcon(
         session, "article.id", "articleId", articleId);
   }
}

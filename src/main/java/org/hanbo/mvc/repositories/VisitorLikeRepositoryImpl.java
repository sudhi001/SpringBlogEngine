package org.hanbo.mvc.repositories;

import java.util.Date;
import java.util.List;

import org.hanbo.mvc.entities.Article;
import org.hanbo.mvc.entities.VisitorLike;
import org.hanbo.mvc.utilities.IdUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang.StringUtils;

@SuppressWarnings("unchecked")
@Repository
public class VisitorLikeRepositoryImpl
   implements VisitorLikeRepository
{
   @Autowired
   private SessionFactory _sessionFactory;
   
   @Autowired
   private ArticlesRepository _articleRepo;

   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   @Override
   public void saveVisitorLike(VisitorLike likeToSave)
   {
      Session session = _sessionFactory.getCurrentSession();
      session.saveOrUpdate(likeToSave);
   }
   
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   @Override
   public void addVisitorLikeToArticle(String articleId, boolean likeIt, String sourceIp)
   {
      Session session = _sessionFactory.getCurrentSession();
      Query query = session.createQuery("select visitorLike from VisitorLike visitorLike "
            + "where visitorLike.relatedArticle.id = :articleId and visitorLike.sourceip = :sourceIp")
         .setParameter("articleId", articleId)
         .setParameter("sourceIp", sourceIp)
         .setMaxResults(1);
      List<VisitorLike> foundObjs = query.list();
      
      Date dateNow = new Date();
      if (foundObjs != null && foundObjs.size() > 0)
      {
         VisitorLike visitorLike = foundObjs.get(0);
         
         if (visitorLike != null)
         {
            if (likeIt)
            {
               int currentLikeCount = visitorLike.getLikeCount();
               visitorLike.setLikeCount(currentLikeCount + 1);
            }
            else
            {
               int currentDislikeCount = visitorLike.getDislikeCount();
               visitorLike.setDislikeCount(currentDislikeCount + 1);
            }
            
            visitorLike.setUpdateDate(dateNow);
            session.update(visitorLike);
         }
      }
      else
      {
         Article articleToLike = _articleRepo.getArticleById(articleId);
         
         if (articleToLike != null)
         {
            VisitorLike visitorLike = new VisitorLike();
            visitorLike.setId(IdUtil.generateUuid());
            visitorLike.setRelatedArticle(articleToLike);
            visitorLike.setSourceIp(sourceIp);
            visitorLike.setUpdateDate(dateNow);
            visitorLike.setCreateDate(dateNow);
            if (likeIt)
            {
               int currentLikeCount = visitorLike.getLikeCount();
               visitorLike.setLikeCount(currentLikeCount + 1);
            }
            else
            {
               int currentDislikeCount = visitorLike.getDislikeCount();
               visitorLike.setDislikeCount(currentDislikeCount + 1);
            }
            
            session.save(visitorLike);
         }
      }
   }

   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   @Override
   public long getArticleVisitorLikesCount(String refObjectId)
   {
      Session session = _sessionFactory.getCurrentSession();
      return getRefObectVisitorLikesCount(session, "select count(unique visitorLike.id) "
         + "from VisitorLike visitorLike where visitorLike.relatedArticle.id = :articleId",
         "articleId", refObjectId);
   }
   
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   @Override
   public long getImageVisitorLikesCount(String refObjectId)
   {
      Session session = _sessionFactory.getCurrentSession();
      return getRefObectVisitorLikesCount(session, "select count(unique visitorLike.id) "
         + "from VisitorLike visitorLike where visitorLike.relatedImage.id = :imageId",
         "imageId", refObjectId);
   }
   
   private long getRefObectVisitorLikesCount(Session session, String queryStrVal,
      String refObjectParam, String refObjId)
   {
      if (session != null
         && !StringUtils.isEmpty(queryStrVal)
         && !StringUtils.isEmpty(refObjectParam)
         && !StringUtils.isEmpty(refObjId))
      {
         Query query = session.createQuery(queryStrVal)
            .setParameter(refObjectParam, refObjId)
            .setMaxResults(1);
         List<Long> foundObj = query.list();
         if (foundObj != null && foundObj.size() > 0)
         {
            return foundObj.get(0);
         }
      }
      
      return 0L;
   }
}

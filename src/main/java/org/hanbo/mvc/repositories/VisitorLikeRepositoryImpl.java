package org.hanbo.mvc.repositories;

import java.util.List;

import org.hanbo.mvc.entities.VisitorLike;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@SuppressWarnings("unchecked")
@Repository
public class VisitorLikeRepositoryImpl
   implements VisitorLikeRepository
{
   @Autowired
   private SessionFactory _sessionFactory;

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

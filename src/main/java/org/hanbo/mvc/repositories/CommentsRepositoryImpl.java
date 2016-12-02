package org.hanbo.mvc.repositories;

import java.util.ArrayList;
import java.util.List;

import org.hanbo.mvc.entities.Article;
import org.hanbo.mvc.entities.Image;
import org.hanbo.mvc.entities.LoginUser;
import org.hanbo.mvc.entities.VisitorComment;
import org.hanbo.mvc.exceptions.WebAppException;
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
public class CommentsRepositoryImpl
   implements CommentsRepository
{
   @Autowired
   private SessionFactory _sessionFactory;

   @Autowired
   private ArticlesRepository _articleRepo;
   
   @Autowired
   private ImageGalleryRepository _imageRepo;
   
   @Autowired
   private UsersRepository _userRepo;
   
   @Override
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   public void saveArticleComment(String articleId, String commentOwnerId,
      String parentCommentId, VisitorComment commentToSave)
   {
      Session session = _sessionFactory.getCurrentSession();
      
      Article commentArticle = 
         _articleRepo.findArticleById(session, articleId);
      
      if (commentArticle != null)
      {
         commentToSave.setRelatedArticle(commentArticle);
      }
      else
      {
         throw new WebAppException("Article to comment is not found.", WebAppException.ErrorType.DATA);
      }
      
      saveComment(session, commentToSave, commentOwnerId, parentCommentId);
   }
   
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   @Override
   public void saveImageComment(String imageId, String commentOwnerId,
      String parentCommentId, VisitorComment commentToSave)
   {
      Session session = _sessionFactory.getCurrentSession();
      
      Image commentImage = 
         _imageRepo.getImage(session, imageId);
      if (commentImage != null)
      {
         commentToSave.setRelatedImage(commentImage);
      }
      else
      {
         throw new WebAppException("Article to comment is not found.", WebAppException.ErrorType.DATA);
      }
      
      saveComment(session, commentToSave, commentOwnerId, parentCommentId);
   }

   
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   @Override
   public List<VisitorComment> loadArticleComments(String articleId, int pageIdx, int maxResultsCount)
   {
      Session session = _sessionFactory.getCurrentSession();
      
      return loadComments(session, "select visitorComment from VisitorComment visitorComment "
         + "where visitorComment.relatedArticle.id = :articleId order by visitorComment.updateDate desc", "articleId", articleId, pageIdx, maxResultsCount);
   }
   
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   @Override
   public List<VisitorComment> loadArticleViewableComments(String articleId, int maxResultsCount)
   {
      Session session = _sessionFactory.getCurrentSession();
      
      return loadComments(session, "select visitorComment from VisitorComment visitorComment where visitorComment.relatedArticle.id = :articleId "
         + "and visitorComment.commentPrivate = false and visitorComment.commentApproved = true order by visitorComment.updateDate desc", "articleId", articleId, 0, maxResultsCount);
   }
   
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   @Override
   public List<VisitorComment> loadImageViewableComments(String imageId, int maxResultsCount)
   {
      Session session = _sessionFactory.getCurrentSession();
      
      List<VisitorComment> retVals = 
         loadComments(session, "select visitorComment from VisitorComment visitorComment where visitorComment.relatedImage.id = :imageId "
            + "and visitorComment.commentPrivate = false and visitorComment.commentApproved = true order by visitorComment.updateDate desc", "imageId", imageId, 0, maxResultsCount);
      System.out.println("image comments count: " + retVals.size());
      
      return retVals;
   }
      
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   @Override
   public VisitorComment loadArticleComment(String articleId, String commentId)
   {
      Session session = _sessionFactory.getCurrentSession();
      
      Query query = session.createQuery("select visitorComment from VisitorComment visitorComment where visitorComment.id = :commentId and relatedArticle.id = :articleId")
         .setParameter("articleId", articleId)
         .setParameter("commentId", commentId)
         .setFirstResult(0)
         .setMaxResults(1);
      List<VisitorComment> foundObjs =  query.list();
      if (foundObjs != null && foundObjs.size() > 0)
      {
         VisitorComment retVal = foundObjs.get(0);
         preloadVisitorComment(retVal);
         
         return retVal;
      }
      
      return null;
   }
   
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   @Override
   public VisitorComment loadImageComment(String imageId, String commentId)
   {
      Session session = _sessionFactory.getCurrentSession();
      
      Query query = session.createQuery("select visitorComment from VisitorComment visitorComment where visitorComment.id = :commentId and relatedImage.id = :imageId")
         .setParameter("imageId", imageId)
         .setParameter("commentId", commentId)
         .setFirstResult(0)
         .setMaxResults(1);
      List<VisitorComment> foundObjs =  query.list();
      if (foundObjs != null && foundObjs.size() > 0)
      {
         VisitorComment retVal = foundObjs.get(0);
         preloadVisitorComment(retVal);
         
         return retVal;
      }
      
      return null;
   }
   
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   @Override
   public void deleteArticleComment(String articleId, String commentId)
   {
      Session session = _sessionFactory.getCurrentSession();

      Query query = 
      session.createQuery("delete from VisitorComment where id = :commentId and relatedArticle.id = :articleId")
         .setParameter("articleId", articleId)
         .setParameter("commentId", commentId);
      query.executeUpdate();
   }
   
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   @Override
   public void deleteArticleComments(String articleId)
   {
      Session session = _sessionFactory.getCurrentSession();
      deleteArticleComments(session, articleId);
   }
   
   @Override
   public List<VisitorComment> loadComments(
         Session session, String queryString, String refObjIdName,
         String refObjId, int pageIdx, int maxResultsCount)
   {
      List<VisitorComment> retVals = new ArrayList<VisitorComment>();
      if (session != null)
      {
         Query query = 
            session.createQuery(queryString)
               .setParameter(refObjIdName, refObjId)
               .setFirstResult(pageIdx * maxResultsCount)
               .setMaxResults(maxResultsCount);
         
         retVals = query.list();
         if (retVals != null && retVals.size() > 0)
         {
            for (VisitorComment comment : retVals)
            {
               preloadVisitorComment(comment);
            }
         }
      }
      
      return retVals;
   }
   
   @Override
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   public long getUnapprovedCommentsCount()
   {
      Session session = _sessionFactory.getCurrentSession();

      Query query = 
      session.createQuery("select count(visitorComment) from VisitorComment visitorComment where visitorComment.commentApproved = false")
         .setFirstResult(0)
         .setMaxResults(1);
      List<Long> foundObjs = query.list();
      
      if (foundObjs != null && foundObjs.size() > 0)
      {
         Long itemsCount = foundObjs.get(0);
         
         return itemsCount;
      }
      
      return 0L;
   }
   
   @Override
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   public List<VisitorComment> getUnapprovedComments(int pageIdx, int maxItemsCount)
   {
      List<VisitorComment> retVals = new ArrayList<VisitorComment>();
      
      Session session = _sessionFactory.getCurrentSession();

      Query query = 
      session.createQuery("select visitorComment from VisitorComment visitorComment where visitorComment.commentApproved = false order by visitorComment.updateDate desc")
         .setFirstResult(pageIdx * maxItemsCount)
         .setMaxResults(maxItemsCount);
      List<VisitorComment> foundObjs = query.list();
      
      if (foundObjs != null && foundObjs.size() > 0)
      {
         for (VisitorComment comment : foundObjs)
         {
            preloadVisitorComment(comment);
         }
         
         retVals = foundObjs;
      }
      
      return retVals;
   }
   
   @Override
   public void deleteArticleComments(Session session, String articleId)
   {
      if (session != null)
      {
         Query query = 
         session.createQuery("delete from VisitorComment where relatedArticle.id = :articleId")
            .setParameter("articleId", articleId);
         query.executeUpdate();
      }
   }
   
   @Override
   public void unassociateArticleComments(Session session, String articleId)
   {
      if (session != null)
      {
         Query query =
            session.createQuery("update VisitorComment set parentComment = null where relatedArticle.id = :articleId")
               .setParameter("articleId", articleId);
         query.executeUpdate();
      }
   }
   
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   @Override
   public boolean approveComment(String commentId)
   {
      boolean retVal = false;
      Session session = this._sessionFactory.getCurrentSession();
      if (session != null)
      {
         if (!StringUtils.isEmpty(commentId))
         {
            Query query =
               session.createQuery("update VisitorComment set commentApproved = true where id = :commentId")
                  .setParameter("commentId", commentId);
            int resultsCount = query.executeUpdate();
            retVal = resultsCount > 0;
         }
      }
      
      return retVal;
   }
   
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   @Override
   public boolean deleteComment(String commentId)
   {
      boolean retVal = false;
      Session session = this._sessionFactory.getCurrentSession();
      if (session != null)
      {
         if (!StringUtils.isEmpty(commentId))
         {
            Query query =
               session.createQuery("delete VisitorComment where id = :commentId")
                  .setParameter("commentId", commentId);
            int resultsCount = query.executeUpdate();
            retVal = resultsCount > 0;
         }
      }
      
      return retVal;
   }
   
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   @Override
   public VisitorComment loadComment(String commentId)
   {
      Session session = this._sessionFactory.getCurrentSession();
      if (session != null)
      {
         VisitorComment retVal = getCommentById(session, commentId);
         if (retVal != null)
         {
            if (retVal.getOwner() != null)
            {
               retVal.getOwner().getUserName();
               retVal.getOwner().getUserEmail();
            }
            
            return retVal;
         }
      }
      
      return null;
   }
   
   private VisitorComment getCommentById(Session session, String commentId)
   {
      Query query = session.createQuery("select comment from VisitorComment comment where comment.id = :commentId")
         .setParameter("commentId", commentId)
         .setFirstResult(0)
         .setMaxResults(1);
      List<VisitorComment> foundComments = query.list();
      if (foundComments != null && foundComments.size() > 0)
      {
         return foundComments.get(0);
      }
      
      return null;
   }
   
   private void preloadVisitorComment(VisitorComment comment)
   {
      if (comment != null)
      {
         if (comment.getOwner() != null)
         {
            comment.getOwner().getId();
            comment.getOwner().getUserName();
            
            if (comment.getOwner().getUserProfile() != null)
            {
               comment.getOwner().getUserProfile().getId();
               comment.getOwner().getUserProfile().getFirstName();
               comment.getOwner().getUserProfile().getLastName();
               
               if (comment.getOwner().getUserProfile().getUserIcon() != null)
               {
                  comment.getOwner().getUserProfile().getUserIcon().getId();
               }
            }
         }

         if (comment.getParentComment() != null)
         {
            comment.getParentComment().getId();
         }
         
         if (comment.getRelatedArticle() != null)
         {
            comment.getRelatedArticle().getId();
            comment.getRelatedArticle().getArticleTitle();
         }
         
         if (comment.getRelatedImage() != null)
         {
            comment.getRelatedImage().getId();
            comment.getRelatedImage().getImageTitle();
         }
         
         // XXX add more data extraction if needed.
      }
   }
   
   private void saveComment(Session session, VisitorComment commentToSave, String commentOwnerId, String parentCommentId)
   {
      if (session != null && commentToSave != null)
      {
         if (!StringUtils.isEmpty(commentOwnerId))
         {
            LoginUser commentOwner = _userRepo.getUserById(session, commentOwnerId);
            if (commentOwner != null)
            {
               commentToSave.setOwner(commentOwner);
               commentToSave.setCommenter("");
               commentToSave.setCommenterEmail("");
            }
            else
            {
               throw new WebAppException("Comment owner is not found.", WebAppException.ErrorType.DATA);
            }
         }
         
         if (!StringUtils.isEmpty(parentCommentId))
         {
            VisitorComment parentComment = this.getCommentById(session, parentCommentId);
            if (parentComment != null)
            {
               commentToSave.setParentComment(parentComment);
            }
            else
            {
               throw new WebAppException("Parent comment is not found.", WebAppException.ErrorType.DATA);
            }
         }
       
         session.saveOrUpdate(commentToSave);
      }
   }
}

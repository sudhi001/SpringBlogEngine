package org.hanbo.mvc.repositories;

import java.util.ArrayList;
import java.util.List;

import org.hanbo.mvc.entities.Article;
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
   private UsersRepository _userRepo;
   
   @Override
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   public void saveArticleComment(String articleId, String commentOwnerId,
      String parentCommentid, VisitorComment commentToSave)
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
      
      if (!StringUtils.isEmpty(parentCommentid))
      {
         VisitorComment parentComment = this.getCommentById(session, parentCommentid);
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
   
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   @Override
   public List<VisitorComment> loadArticleComments(String articleId, int pageIdx, int maxResultsCount)
   {
      Session session = _sessionFactory.getCurrentSession();
      
      return loadArticleComments(session, "select visitorComment from VisitorComment visitorComment "
         + "where visitorComment.relatedArticle.id = :articleId order by visitorComment.updateDate desc", articleId, pageIdx, maxResultsCount);
   }
   
   @Transactional(
      propagation = Propagation.REQUIRED,
      isolation = Isolation.READ_COMMITTED
   )
   @Override
   public List<VisitorComment> loadArticleViewableComments(String articleId, int maxResultsCount)
   {
      Session session = _sessionFactory.getCurrentSession();
      
      return loadArticleComments(session, "select visitorComment from VisitorComment visitorComment where visitorComment.relatedArticle.id = :articleId "
         + "and visitorComment.commentPrivate = false and visitorComment.commentApproved = true order by visitorComment.updateDate desc", articleId, 0, maxResultsCount);
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
   public List<VisitorComment> loadArticleComments(Session session, String queryString, String articleId, int pageIdx, int maxResultsCount)
   {
      List<VisitorComment> retVals = new ArrayList<VisitorComment>();
      if (session != null)
      {
         Query query = 
            session.createQuery(queryString)
               .setParameter("articleId", articleId)
               .setFirstResult(pageIdx * maxResultsCount)
               .setMaxResults(maxResultsCount);
         
         retVals = query.list();
         if (retVals != null && retVals.size() > 0)
         {
            for (VisitorComment comment : retVals)
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
}

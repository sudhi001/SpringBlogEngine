package org.hanbo.mvc.repositories;

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

package org.hanbo.mvc.repositories;

import org.hanbo.mvc.entities.VisitorComment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unchecked")
@Repository
public class CommentsRepositoryImpl
   implements CommentsRepository
{
   @Autowired
   private SessionFactory _sessionFactory;
   
   @Override
   public void saveComment(VisitorComment commentToSave)
   {
      Session session = _sessionFactory.getCurrentSession();
      session.saveOrUpdate(commentToSave);
   }
}

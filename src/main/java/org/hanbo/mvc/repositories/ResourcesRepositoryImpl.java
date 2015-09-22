package org.hanbo.mvc.repositories;

import org.hanbo.mvc.entities.Resource;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
}

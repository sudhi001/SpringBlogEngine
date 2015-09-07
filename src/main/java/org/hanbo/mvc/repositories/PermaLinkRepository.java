package org.hanbo.mvc.repositories;

import org.hanbo.mvc.entities.PermaLink;

public interface PermaLinkRepository
{
   void savePermaLink(PermaLink linkToSave);
   
   PermaLink getPermaLinkByArticle(String articleId);
}

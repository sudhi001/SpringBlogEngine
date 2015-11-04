package org.hanbo.mvc.services;

import org.hanbo.mvc.models.ArticleDataModel;
import org.hanbo.mvc.models.json.GetPermaLinkJsonResponse;
import org.hanbo.mvc.models.json.NewPermaLinkJsonRequest;

public interface PermaLinkService
{
   String setPermaLink(NewPermaLinkJsonRequest permaLinkJsonReq);
   
   GetPermaLinkJsonResponse getPermaLink(String articleId);
   
   void deletePermaLink(String articleId, String authorId);
   
   ArticleDataModel findArticleByPermaLink(String permaLinkValue);
}

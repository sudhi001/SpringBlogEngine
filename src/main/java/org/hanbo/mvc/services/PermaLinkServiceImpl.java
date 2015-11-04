package org.hanbo.mvc.services;

import org.apache.commons.lang.StringUtils;
import org.hanbo.mvc.entities.Article;
import org.hanbo.mvc.entities.LoginUser;
import org.hanbo.mvc.entities.PermaLink;
import org.hanbo.mvc.exceptions.WebAppException;
import org.hanbo.mvc.models.ArticleDataModel;
import org.hanbo.mvc.models.json.GetPermaLinkJsonResponse;
import org.hanbo.mvc.models.json.NewPermaLinkJsonRequest;
import org.hanbo.mvc.repositories.ArticlesRepository;
import org.hanbo.mvc.repositories.PermaLinkRepository;
import org.hanbo.mvc.repositories.UsersRepository;
import org.hanbo.mvc.services.utilities.ArticleDataModelEntityMapping;
import org.hanbo.mvc.utilities.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermaLinkServiceImpl implements PermaLinkService
{
   @Autowired
   private ArticlesRepository _articleRepo;
   
   @Autowired
   private UsersRepository _userRepo;
   
   @Autowired
   private PermaLinkRepository _permaLinkRepository;
   
   @Override
   public String setPermaLink(
      NewPermaLinkJsonRequest permaLinkReq)
   {
      validateDataModel(permaLinkReq);
      
      LoginUser userToLink =
      _userRepo.getUserById(permaLinkReq.getAuthorId());
      if (userToLink == null)
      {
         throw new WebAppException(
            String.format("Cannot find user with id [%s]", permaLinkReq.getAuthorId()),
            WebAppException.ErrorType.SECURITY);
      }
      
      PermaLink permaLink = 
      _permaLinkRepository.getPermaLinkByArticle(permaLinkReq.getArticleId());
      
      if (permaLink == null)
      {
         return createNewPermaLink(permaLinkReq, userToLink);
      }
      else
      {
         return updateExistingPermaLink(permaLink, permaLinkReq);
      }
   }
   
   @Override
   public GetPermaLinkJsonResponse getPermaLink(String articleId)
   {
      PermaLink permaLink = 
      _permaLinkRepository.getPermaLinkByArticle(articleId);
      
      GetPermaLinkJsonResponse response = new GetPermaLinkJsonResponse();
      if (permaLink != null)
      {
         response.setArticleId(permaLink.getAssociatedArticle().getId());
         response.setAuthorId(permaLink.getAssociatedAuthor().getId());
         response.setPageReplacement(permaLink.isPageReplacement());
         response.setPermaLinkPath(permaLink.getPath());
         response.setValid(true);
      }
      
      return response;
   }
   
   @Override
   public void deletePermaLink(String articleId, String authorId)
   {
      Article articleToModify = this._articleRepo.getReportById(articleId, authorId);
      if (articleToModify == null)
      {
         throw new WebAppException(
            String.format("Cannot find article with id [%s]", articleId),
            WebAppException.ErrorType.DATA);
      }
      
      articleToModify.setPermaLinks(null);
      
      this._permaLinkRepository.deletePermaLinkByArticle(articleId);
      
      this._articleRepo.saveArticle(articleToModify);
   }
   
   @Override
   public ArticleDataModel findArticleByPermaLink(String permaLinkValue)
   {
      Article article = 
         _permaLinkRepository.getArticleByPermaLink(permaLinkValue);
      
      if (article == null)
         return null;
      
      return ArticleDataModelEntityMapping.toDataModel(article);
   }
   
   private String createNewPermaLink(
      NewPermaLinkJsonRequest permaLinkReq, LoginUser userToLink)
   {
      PermaLink linkToSave = new PermaLink();
      linkToSave.setId(IdUtil.generateUuid());
      linkToSave.setPageReplacement(permaLinkReq.isPageReplacement());
      linkToSave.setPath(permaLinkReq.getPermaLinkPath());
      linkToSave.setPageReplacement(permaLinkReq.isPageReplacement());
      
      Article articleToLink = 
      _articleRepo.findArticleById(permaLinkReq.getArticleId());
      
      if (articleToLink == null)
      {
         throw new WebAppException(
            String.format("Cannot find article with id [%s]",
               permaLinkReq.getArticleId()),
            WebAppException.ErrorType.DATA);
      }
      
      linkToSave.setAssociatedArticle(articleToLink);
      linkToSave.setAssociatedAuthor(userToLink);
    
      _permaLinkRepository.savePermaLink(linkToSave);
      
      return linkToSave.getId();
   }
   
   private String updateExistingPermaLink(
      PermaLink linkToUpdate, NewPermaLinkJsonRequest permaLinkReq)
   {
      String articleId = 
      linkToUpdate.getAssociatedArticle().getId();
      
      if (!permaLinkReq.getArticleId().equals(articleId))
      {
         throw new WebAppException(
            "Article id mismatch for updating perma link",
            WebAppException.ErrorType.SECURITY);
      }
      
      linkToUpdate.setPath(permaLinkReq.getPermaLinkPath());
      linkToUpdate.setPageReplacement(permaLinkReq.isPageReplacement());

      _permaLinkRepository.savePermaLink(linkToUpdate);
      
      return linkToUpdate.getId();
   }
   
   private void validateDataModel(
      NewPermaLinkJsonRequest permaLinkReq)
   {
      if (StringUtils.isEmpty(permaLinkReq.getArticleId()))
      {
         throw new WebAppException("Permalink's articleId cannot be empty", WebAppException.ErrorType.DATA);
      }

      if (StringUtils.isEmpty(permaLinkReq.getAuthorId()))
      {
         throw new WebAppException("Permalink's authorId cannot be empty", WebAppException.ErrorType.DATA);
      }

      if (StringUtils.isEmpty(permaLinkReq.getPermaLinkPath()))
      {
         throw new WebAppException("Permalink's path value cannot be empty", WebAppException.ErrorType.DATA);
      }

      if (permaLinkReq.getPermaLinkPath().length() > 128)
      {
         throw new WebAppException("Permalink's path value cannot have more than 128 characters", WebAppException.ErrorType.DATA);
      }
   }
}

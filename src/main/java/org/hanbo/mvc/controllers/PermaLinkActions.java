package org.hanbo.mvc.controllers;

import javax.servlet.http.HttpServletRequest;

import org.hanbo.mvc.controllers.utilities.ActionsUtil;
import org.hanbo.mvc.models.ArticleDataModel;
import org.hanbo.mvc.models.PageMetadata;
import org.hanbo.mvc.models.UserPrincipalDataModel;
import org.hanbo.mvc.models.json.GetPermaLinkJsonResponse;
import org.hanbo.mvc.models.json.NewPermaLinkJsonRequest;
import org.hanbo.mvc.models.json.NewPermaLinkJsonResponse;
import org.hanbo.mvc.services.PermaLinkService;
import org.hanbo.mvc.utilities.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.apache.log4j.*;

@Controller
public class PermaLinkActions
{
   private static Logger _logger = LogManager.getLogger(PermaLinkActions.class);
   
   @Autowired
   private ActionsUtil _util;
   
   @Autowired
   private PermaLinkService _permaLinkService;
   
   @RequestMapping(value="/permalink/{linkValue}",
      method=RequestMethod.GET)
   @ResponseBody
   public ModelAndView viewPermaLink(
      @PathVariable("linkValue")
      String linkValue
   )
   {
      ArticleDataModel articleDataModel =
         _permaLinkService.findArticleByPermaLink(linkValue);
      if (articleDataModel == null)
      {
         return _util.createErorrPageViewModel("Article not found",
            String.format("Unable to find article via PermaLink [%s]", linkValue));
      }
      
      if (!articleDataModel.isArticlePublished())
      {
         return _util.createErorrPageViewModel("Article is not published",
            String.format("Article with permalink [%s] is not"
               + " yet published. Cannot be viewed publicly.",
            linkValue));
      }
      
      if (articleDataModel.getArticleType().equals("post"))
      {
         return createArticleViewPage(
            articleDataModel.getArticleTitle(), "viewPost",
            articleDataModel);
      }
      else
      {
         return createArticleViewPage(
            articleDataModel.getArticleTitle(), "viewPost",
            articleDataModel);
      }
   }

   
   @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
   @RequestMapping(value="/admin/blog/setPermaLink",
      method=RequestMethod.POST,
      produces = MediaType.APPLICATION_JSON_VALUE)
   @ResponseBody
   public ResponseEntity<String> setPermaLink(
      HttpServletRequest request
   )
   {      
      String reqBody = JsonUtil.readHttpRequestBody(request);
      _logger.debug("JSON request: " + reqBody);
      
      if (!StringUtils.isEmpty(reqBody))
      {
         NewPermaLinkJsonRequest permaLinkReq
            = JsonUtil.convertJsonToObject(reqBody, NewPermaLinkJsonRequest.class);
      
         _logger.debug("article Id: " + permaLinkReq.getArticleId());
         _logger.debug("author Id: " + permaLinkReq.getAuthorId());
         _logger.debug("Perma Link Path: " + permaLinkReq.getPermaLinkPath());
         _logger.debug("Page Replacement: " + permaLinkReq.isPageReplacement());

         UserPrincipalDataModel loginUser = this._util.getLoginUser();
         if (loginUser == null)
         {
            return newPermaLinkJsonResponse(
               HttpStatus.UNAUTHORIZED, permaLinkReq.getArticleId(),
               "N/A", "User is not logged in to do this");
         }

         String userId = loginUser.getUserId();
         if (!permaLinkReq.getAuthorId().equals(userId))
         {
            return newPermaLinkJsonResponse(
               HttpStatus.UNAUTHORIZED, permaLinkReq.getArticleId(),
               "N/A", "Article owner and current user mismatch");
         }

         try
         {
            String linkId = _permaLinkService.setPermaLink(permaLinkReq);
            
            return newPermaLinkJsonResponse(
               HttpStatus.OK, permaLinkReq.getArticleId(),
               linkId, "Perma Link has been created");
         }
         catch(Exception e)
         {
            return newPermaLinkJsonResponse(
               HttpStatus.BAD_REQUEST, permaLinkReq.getArticleId(),
               "N/A", "Exception occurred");
         }
      }

      return newPermaLinkJsonResponse(
         HttpStatus.BAD_REQUEST, "N/A", "N/A", "Exception occurred");
   }

   @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
   @RequestMapping(value="/admin/blog/getPermaLink",
      method=RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
   @ResponseBody
   public ResponseEntity<String> getPermaLink(
      @RequestParam("articleId")
      String articleId
   )
   {
      GetPermaLinkJsonResponse response =
      _permaLinkService.getPermaLink(articleId);
      
      if (response.isValid())
      {
         return new ResponseEntity<String>(
            JsonUtil.convertObjectToJson(response),
            HttpStatus.OK
         );
      }
      
      return new ResponseEntity<String>(
         JsonUtil.convertObjectToJson(response),
         HttpStatus.NOT_ACCEPTABLE
      );
   }
   
   @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
   @RequestMapping(value="/admin/blog/deletePermaLink",
      method=RequestMethod.DELETE,
      produces = MediaType.APPLICATION_JSON_VALUE)
   @ResponseBody
   public ResponseEntity<String> deletePermaLink(
      @RequestParam("articleId")
      String articleId
   )
   {
      UserPrincipalDataModel loginUser = this._util.getLoginUser();
      if (loginUser == null)
      {
         return new ResponseEntity<String>(
            HttpStatus.UNAUTHORIZED
         );
      }
      
      _permaLinkService.deletePermaLink(articleId, loginUser.getUserId());

      return new ResponseEntity<String>(
         HttpStatus.OK
      );
   }
   
   private ResponseEntity<String> newPermaLinkJsonResponse(
      HttpStatus statusCode, String articleId,
      String permaLinkId, String statusMsg)
   {
      NewPermaLinkJsonResponse response =
         new NewPermaLinkJsonResponse();
      
      response.setArticleId(articleId);
      response.setPermaLinkId(permaLinkId);
      response.setOperationStatus(statusMsg);
      
      return new ResponseEntity<String>(
         JsonUtil.convertObjectToJson(response),
         statusCode
      );
   }
   
   private ModelAndView createArticleViewPage(
      String pageTitle, String pageTemplate,
      ArticleDataModel articleDataModel)
   {
      PageMetadata pageMetadata
         = _util.creatPageMetadata(pageTitle);
      ModelAndView retVal
         = _util.getDefaultModelAndView(
              pageTemplate, pageMetadata
           );
      retVal.addObject("articleModel", articleDataModel);
      
      return retVal;
   }
}

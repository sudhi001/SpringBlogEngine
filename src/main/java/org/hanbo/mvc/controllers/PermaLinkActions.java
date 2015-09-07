package org.hanbo.mvc.controllers;

import javax.servlet.http.HttpServletRequest;

import org.hanbo.mvc.controllers.utilities.ActionsUtil;
import org.hanbo.mvc.models.UserPrincipalDataModel;
import org.hanbo.mvc.models.json.NewPermaLinkJsonRequest;
import org.hanbo.mvc.models.json.NewPermaLinkJsonResponse;
import org.hanbo.mvc.services.ArticleService;
import org.hanbo.mvc.services.PermaLinkService;
import org.hanbo.mvc.utilities.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.log4j.*;

import com.google.gson.Gson;

@Controller
public class PermaLinkActions
{
   private static Logger _logger = LogManager.getLogger(PermaLinkActions.class);
   
   @Autowired
   private ActionsUtil _util;
   
   //@Autowired
   //private ArticleService _articleService;
   
   @Autowired
   private PermaLinkService _permaLinkService;
   
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
            return permaLinkJsonResponse(
               HttpStatus.UNAUTHORIZED, permaLinkReq.getArticleId(),
               "N/A", "User is not logged in to do this");
         }

         String userId = loginUser.getUserId();
         if (!permaLinkReq.getAuthorId().equals(userId))
         {
            return permaLinkJsonResponse(
               HttpStatus.UNAUTHORIZED, permaLinkReq.getArticleId(),
               "N/A", "Article owner and current user mismatch");
         }

         try
         {
            String linkId = _permaLinkService.setPermaLink(permaLinkReq);
            
            return permaLinkJsonResponse(
               HttpStatus.OK, permaLinkReq.getArticleId(),
               linkId, "Perma Link has been created");
         }
         catch(Exception e)
         {
            return permaLinkJsonResponse(
               HttpStatus.BAD_REQUEST, permaLinkReq.getArticleId(),
               "N/A", "Exception occurred");
         }
      }

      return permaLinkJsonResponse(
         HttpStatus.BAD_REQUEST, "N/A", "N/A", "Exception occurred");
   }
   
   
   private ResponseEntity<String> permaLinkJsonResponse(
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
}

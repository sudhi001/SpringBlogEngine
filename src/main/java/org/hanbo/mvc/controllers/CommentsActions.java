package org.hanbo.mvc.controllers;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.hanbo.mvc.controllers.utilities.ActionsUtil;
import org.hanbo.mvc.models.ArticleCommentDataModel;
import org.hanbo.mvc.models.CommentInputDataModel;
import org.hanbo.mvc.models.UserPrincipalDataModel;
import org.hanbo.mvc.services.CommentsService;
import org.hanbo.mvc.utilities.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CommentsActions
{
   @Autowired
   private ActionsUtil _util;
   
   @Autowired
   private CommentsService _commentService;
   
   @RequestMapping(value = "/public/comments/addArticleComment",
      method=RequestMethod.POST,
      produces = MediaType.APPLICATION_JSON_VALUE)
   @ResponseBody
   public ResponseEntity<String> addArticleComment(
      @RequestBody
      String commentJsonObject,      
      HttpServletRequest request
   )
   {
      String loggedInUserId = null;
      UserPrincipalDataModel loginUser = this._util.getLoginUser();
      if (loginUser != null)
      {
         loggedInUserId = loginUser.getUserId();
      }
      
      try
      {
         if (!StringUtils.isEmpty(commentJsonObject))
         {
            System.out.println(commentJsonObject);
            
            CommentInputDataModel commentReceived = 
            JsonUtil.convertJsonToObject(commentJsonObject, CommentInputDataModel.class);
            
            if (commentReceived != null)
            {
               ArticleCommentDataModel commentToAdd
                  = new ArticleCommentDataModel();
               commentToAdd.setArticleId(commentReceived.getOriginId());
               commentToAdd.setParentCommentId(commentReceived.getParentCommentId());
               commentToAdd.setCommentContent(commentReceived.getCommentContent());
               commentToAdd.setCommentTitle(commentReceived.getCommentTitle());
               commentToAdd.setCommenterName(commentReceived.getCommenterName());
               commentToAdd.setCommenterEmail(commentReceived.getCommenterEmail());
               commentToAdd.setCommentApproved(false);
               commentToAdd.setCommentPrivate(commentReceived.isCommentPrivate());
               
               String ipAddress = request.getHeader("X-FORWARDED-FOR");
               if (ipAddress == null) {
                     ipAddress = request.getRemoteAddr();
               }
               commentToAdd.setCommentSourceIp(ipAddress);
               
               if (!StringUtils.isEmpty(loggedInUserId))
               {
                  commentToAdd.setCommentUserId(loggedInUserId);
               }
               
               final HttpHeaders httpHeaders= new HttpHeaders();
               httpHeaders.setContentType(MediaType.APPLICATION_JSON);
               _commentService.addArticleComment(commentToAdd);

               return new ResponseEntity<String>(JsonUtil.simpleErrorMessage("No Error, Success."), httpHeaders, HttpStatus.OK);
            }
            else
            {
               String respJsonVal = JsonUtil.simpleErrorMessage("Comment content is empty.");
               return new ResponseEntity<String>(respJsonVal, HttpStatus.BAD_REQUEST);
            }
         }
         else
         {
            String respJsonVal = JsonUtil.simpleErrorMessage("Comment content is empty.");
            return new ResponseEntity<String>(respJsonVal, HttpStatus.BAD_REQUEST);
         }
      }
      catch(Exception ex)
      {
         ex.printStackTrace();
         String respJsonVal = JsonUtil.simpleErrorMessage("Unknown exception occurred.");
         return new ResponseEntity<String>(respJsonVal, HttpStatus.INTERNAL_SERVER_ERROR);
      }
   }
   
   @RequestMapping(value = "/public/comments/loadComment",
      method=RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
   @ResponseBody
   public ResponseEntity<String> loadComment(
      @RequestParam("articleId")
      String articleId,      
      @RequestParam("commentId")
      String commentId
   )
   {
      if (!StringUtils.isEmpty(articleId) && !StringUtils.isEmpty(commentId))
      {
         ArticleCommentDataModel articleComment
            = this._commentService.loadArticleComment(articleId, commentId);
         
         if (articleComment != null)
         {
            String jsonResp = JsonUtil.convertObjectToJson(articleComment);
            
            final HttpHeaders httpHeaders= new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            
            return new ResponseEntity<String>(jsonResp, HttpStatus.OK);
         }
      }
      
      String respJsonVal = JsonUtil.simpleErrorMessage("Article Id is null or empty, or comment Id is null or empty.");
      return new ResponseEntity<String>(respJsonVal, HttpStatus.INTERNAL_SERVER_ERROR);
   }

}

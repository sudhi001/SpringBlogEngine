package org.hanbo.mvc.controllers;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.hanbo.mvc.controllers.utilities.ActionsUtil;
import org.hanbo.mvc.exceptions.WebAppException;
import org.hanbo.mvc.models.ArticleCommentDataModel;
import org.hanbo.mvc.models.CommentInputDataModel;
import org.hanbo.mvc.models.PageMetadata;
import org.hanbo.mvc.models.UserArticleCommentsPageDataModel;
import org.hanbo.mvc.models.UserPrincipalDataModel;
import org.hanbo.mvc.models.json.UserCommentActionInputDataModel;
import org.hanbo.mvc.services.CommentsService;
import org.hanbo.mvc.utilities.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
   
   @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
   @RequestMapping(value="/admin/comments/{pageIdx}",
      method=RequestMethod.GET)
   public ModelAndView allUserArticleComments(
      @PathVariable("pageIdx")
      int pageIdx
   )
   {
      UserPrincipalDataModel loginUser = this._util.getLoginUser();
      if (loginUser == null)
      {
         throw new WebAppException("User is not logged in", WebAppException.ErrorType.SECURITY);
      }
      
      UserArticleCommentsPageDataModel articleCommentsPageDataModel
         = _commentService.getUnapprovedArticleComments(pageIdx);
      
      PageMetadata pageMetadata
         = _util.creatPageMetadata("Unapproved Comments");
      ModelAndView retVal
         = _util.getDefaultModelAndView(
              "userCommentsList", pageMetadata);
      retVal.addObject("unapprovedArticleComments", articleCommentsPageDataModel);
      
      return retVal;
   }
   
   @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
   @RequestMapping(value="/admin/comments/approveComment",
      method=RequestMethod.PUT,
      produces = MediaType.APPLICATION_JSON_VALUE)
   @ResponseBody
   public ResponseEntity<String> approveComment(
      @RequestBody
      String approveRequest
   )
   {
      System.out.println(approveRequest);
      
      final HttpHeaders httpHeaders= new HttpHeaders();
      httpHeaders.setContentType(MediaType.APPLICATION_JSON);
      
      UserCommentActionInputDataModel commentInputParam = 
      JsonUtil.convertJsonToObject(approveRequest, UserCommentActionInputDataModel.class);      
      if (commentInputParam == null)
      {
         return new ResponseEntity<String>(JsonUtil.simpleErrorMessage("Input request is empty."), httpHeaders, HttpStatus.BAD_REQUEST);
      }
      
      UserPrincipalDataModel loginUser = this._util.getLoginUser();
      if (loginUser == null)
      {
         throw new WebAppException("User is not logged in", WebAppException.ErrorType.SECURITY);
      }

      try
      {
         if (_commentService.approveComment(commentInputParam.getArticleId(), commentInputParam.getCommentId()))
         {
            return new ResponseEntity<String>(JsonUtil.simpleErrorMessage("No Error, Success."), httpHeaders, HttpStatus.OK);            
         }
         else
         {
            return new ResponseEntity<String>(JsonUtil.simpleErrorMessage("Processing failed."), httpHeaders, HttpStatus.BAD_REQUEST);                        
         }
      }
      catch(Exception e)
      {
         return new ResponseEntity<String>(JsonUtil.simpleErrorMessage("Unknown error occurred"), httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
      }
   }
   
   @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
   @RequestMapping(value="/admin/comments/deleteComment",
      method=RequestMethod.DELETE,
      produces = MediaType.APPLICATION_JSON_VALUE)
   @ResponseBody
   public ResponseEntity<String> deleteComment(
      @RequestBody
      String deleteRequest
   )
   {
      System.out.println(deleteRequest);
      
      final HttpHeaders httpHeaders= new HttpHeaders();
      httpHeaders.setContentType(MediaType.APPLICATION_JSON);

      if (StringUtils.isEmpty(deleteRequest))
      {
         return new ResponseEntity<String>(JsonUtil.simpleErrorMessage("Input request is empty."), httpHeaders, HttpStatus.BAD_REQUEST);
      }
      
      UserCommentActionInputDataModel commentInputParam = 
      JsonUtil.convertJsonToObject(deleteRequest, UserCommentActionInputDataModel.class);      
      if (commentInputParam == null)
      {
         return new ResponseEntity<String>(JsonUtil.simpleErrorMessage("Input request is empty."), httpHeaders, HttpStatus.BAD_REQUEST);
      }
      
      UserPrincipalDataModel loginUser = this._util.getLoginUser();
      if (loginUser == null)
      {
         throw new WebAppException("User is not logged in", WebAppException.ErrorType.SECURITY);
      }

      try
      {
         if (_commentService.deleteComment(commentInputParam.getArticleId(), commentInputParam.getCommentId()))
         {
            return new ResponseEntity<String>(JsonUtil.simpleErrorMessage("No Error, Success."), httpHeaders, HttpStatus.OK);
         }
         else
         {
            return new ResponseEntity<String>(JsonUtil.simpleErrorMessage("Processing failed."), httpHeaders, HttpStatus.BAD_REQUEST);
         }
      }
      catch(Exception e)
      {
        e.printStackTrace();
         return new ResponseEntity<String>(JsonUtil.simpleErrorMessage("Unknown error occurred"), httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
      }
   }
}

package org.hanbo.mvc.controllers;

import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.hanbo.mvc.controllers.utilities.ActionsUtil;
import org.hanbo.mvc.models.PageMetadata;
import org.hanbo.mvc.models.ResourceListPageDataModel;
import org.hanbo.mvc.models.UserPrincipalDataModel;
import org.hanbo.mvc.models.json.IconResourcesListJsonResponse;
import org.hanbo.mvc.models.json.ImageResourceJsonResponse;
import org.hanbo.mvc.models.json.TextResourceJsonResponse;
import org.hanbo.mvc.models.json.ResourcesListJsonResponse;
import org.hanbo.mvc.services.ResourceService;
import org.hanbo.mvc.utilities.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.*;

@Controller
public class ResourcesActions
{
   private static Logger _log = LogManager.getLogger(ResourcesActions.class);
   
   @Autowired
   private ActionsUtil _util;
   
   @Autowired
   private ResourceService _resourceService;
   
   @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
   @RequestMapping(value="/admin/resources/allMyResources", method=RequestMethod.GET)
   public ModelAndView allMyResources(
      @RequestParam(value="pageIdx", required=false)
      Integer pageIdx
   )
   {
      if (pageIdx == null)
      {
         pageIdx = new Integer(0);
      }
      
      if (pageIdx < 0)
      {
         return _util.createErorrPageViewModel(
            "Invalid Page Index",
            "The list of posts should have page idx >= 0."
         );
      }
      
      UserPrincipalDataModel loginUser = this._util.getLoginUser();
      if (loginUser == null)
      {
         return _util.createErorrPageViewModel("User Authorization Failure", "User cannot be found.");
      }

      ResourceListPageDataModel resListPage =
         this._resourceService.getOwnerResourcesPage(
            loginUser.getUserId(), pageIdx);
      
      PageMetadata pageMetadata
         = _util.creatPageMetadata("List all my resources");
      ModelAndView retVal
         = _util.getDefaultModelAndView(
            "siteResourcesList", pageMetadata
         );
      retVal.addObject("resourceListPageModel", resListPage);
      
      return retVal;
   }
   
   @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
   @RequestMapping(value="/admin/resources/addUpdateTextResource", method=RequestMethod.POST)
   public ModelAndView addUpdateTextResource(
      @RequestParam(value="resourceId", required=false)
      String resourceId,
      @RequestParam(value="resourceName")
      String resourceName,
      @RequestParam(value="resourceSubType")
      String resourceSubType,
      @RequestParam(value="resourceValue")
      String resourceValue
   )
   {
      UserPrincipalDataModel loginUser = this._util.getLoginUser();
      if (loginUser == null)
      {
         return _util.createErorrPageViewModel(
            "User Authorization Failure", "User cannot be found."
         );
      }
      
      if (!StringUtils.isEmpty(resourceId))
      {
         _resourceService.updateTextResource(
            loginUser.getUserId(), resourceId, resourceName, resourceSubType, resourceValue);
      }
      else
      {
         _resourceService.saveTextResource(
            loginUser.getUserId(), resourceName, resourceSubType, resourceValue);
      }
      
      return _util.createRedirectPageView(
         "redirect:/admin/resources/allMyResources"
      );
   }
      
   @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
   @RequestMapping(value="/admin/resources/addNewFileResource", method=RequestMethod.POST)
   public ModelAndView addNewFileResource(
       @RequestParam("resourceName")
       String resourceName,
       @RequestParam("resourceSubType")
       String resourceSubType,
       @RequestParam("fileToUpload")
       MultipartFile fileToUpload
   )
   {
      UserPrincipalDataModel loginUser = this._util.getLoginUser();
      if (loginUser == null)
      {
         return _util.createErorrPageViewModel(
            "User Authorization Failure", "User cannot be found."
         );
      }
      
      _log.info("ContentType: " + fileToUpload.getContentType());
      _log.info("Name: " + fileToUpload.getName());
      _log.info("OriginalName: " + fileToUpload.getOriginalFilename());
      _log.info("Size: " + fileToUpload.getSize());
      
      _resourceService.saveResourceFile(
         loginUser.getUserId(),
         resourceName,
         resourceSubType, fileToUpload
      );
      
      return _util.createRedirectPageView(
         "redirect:/admin/resources/allMyResources"
      );
   }
   
   @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
   @RequestMapping(value="/admin/resources/deleteResource",
      method=RequestMethod.DELETE,
      produces = MediaType.APPLICATION_JSON_VALUE)
   @ResponseBody
   public ResponseEntity<String> deleteResource(
      @RequestParam("resourceId")
      String resourceId
   )
   {
      UserPrincipalDataModel loginUser = this._util.getLoginUser();
      if (loginUser == null)
      {
         return new ResponseEntity<String>(
            JsonUtil.simpleErrorMessage("User not found"),
            HttpStatus.UNAUTHORIZED
         );
      }
      
      String userId = loginUser.getUserId();
      
      this._resourceService.deleteResource(resourceId, userId);
      
      return new ResponseEntity<String>(HttpStatus.OK);
   }
   
   @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
   @RequestMapping(value="/admin/resources/getTextResource",
      method=RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
   @ResponseBody
   public ResponseEntity<String> getTextResource(
      @RequestParam("resourceId")
      String resourceId
   )
   {
      UserPrincipalDataModel loginUser = this._util.getLoginUser();
      if (loginUser == null)
      {
         return new ResponseEntity<String>(
            JsonUtil.simpleErrorMessage("User not found"),
            HttpStatus.UNAUTHORIZED
         );
      }
      
      String userId = loginUser.getUserId();
      
      TextResourceJsonResponse jsonResp = 
      this._resourceService.getTextResource(resourceId, userId);
      if (jsonResp != null && jsonResp.isValid())
      {
         return new ResponseEntity<String>(
            JsonUtil.convertObjectToJson(jsonResp), HttpStatus.OK
         );
      }
      
      return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
   }

   @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
   @RequestMapping(value="/admin/resources/getFormattedTextResource",
      method=RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
   @ResponseBody
   public ResponseEntity<String> getFormattedTextResource(
      @RequestParam("resourceId")
      String resourceId
   )
   {
      UserPrincipalDataModel loginUser = this._util.getLoginUser();
      if (loginUser == null)
      {
         return new ResponseEntity<String>(
            JsonUtil.simpleErrorMessage("User not found"),
            HttpStatus.UNAUTHORIZED
         );
      }
      
      String userId = loginUser.getUserId();
      
      TextResourceJsonResponse jsonResp = 
      this._resourceService.getFormattedTextResource(resourceId, userId);
      if (jsonResp != null && jsonResp.isValid())
      {
         return new ResponseEntity<String>(
            JsonUtil.convertObjectToJson(jsonResp), HttpStatus.OK
         );
      }
      
      return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
   }
   
   @RequestMapping(value="/admin/resources/getFormattedImageResource",
      method=RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
   @ResponseBody
   public ResponseEntity<String> getFormattedImageResource(
      @RequestParam("resourceId")
      String resourceId
   )
   {
      UserPrincipalDataModel loginUser = this._util.getLoginUser();
      if (loginUser == null)
      {
         return new ResponseEntity<String>(
            JsonUtil.simpleErrorMessage("User not found"),
            HttpStatus.UNAUTHORIZED
         );
      }
      
      String userId = loginUser.getUserId();
      
      ImageResourceJsonResponse jsonResp = 
      this._resourceService.getFormattedImageResource(resourceId, userId);
      if (jsonResp != null && jsonResp.isValid())
      {
         return new ResponseEntity<String>(
            JsonUtil.convertObjectToJson(jsonResp), HttpStatus.OK
         );
      }
      
      return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
   }
   
   @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
   @RequestMapping(value="/admin/resources/getTextResourcesList",
      method=RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
   @ResponseBody
   public ResponseEntity<String> getTextResourcesList(
      @RequestParam(value="pageIdx", required=false)
      Integer pageIdx
   )
   {
      if (pageIdx == null)
      {
         pageIdx = new Integer(0);
      }
      
      if (pageIdx < 0)
      {
         return new ResponseEntity<String>(
            JsonUtil.simpleErrorMessage("pageIdx invalid"), HttpStatus.NOT_ACCEPTABLE
         );
      }
      
      UserPrincipalDataModel loginUser = this._util.getLoginUser();
      if (loginUser == null)
      {
         return new ResponseEntity<String>(
            JsonUtil.simpleErrorMessage("User not found"),
            HttpStatus.UNAUTHORIZED
         );
      }
      
      String userId = loginUser.getUserId();
      
      ResourcesListJsonResponse jsonResp = 
      this._resourceService.getTextResourcesList(userId, pageIdx);
      if (jsonResp != null && jsonResp.isValid())
      {
         return new ResponseEntity<String>(
            JsonUtil.convertObjectToJson(jsonResp), HttpStatus.OK
         );
      }
      
      return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
   }
   
   
   @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
   @RequestMapping(value="/admin/resources/getImageResourcesList",
      method=RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
   @ResponseBody
   public ResponseEntity<String> getImageResourcesList(
      @RequestParam(value="pageIdx", required=false)
      Integer pageIdx
   )
   {
      if (pageIdx == null)
      {
         pageIdx = new Integer(0);
      }
      
      if (pageIdx < 0)
      {
         return new ResponseEntity<String>(
            JsonUtil.simpleErrorMessage("pageIdx invalid"), HttpStatus.NOT_ACCEPTABLE
         );
      }
      
      UserPrincipalDataModel loginUser = this._util.getLoginUser();
      if (loginUser == null)
      {
         return new ResponseEntity<String>(
            JsonUtil.simpleErrorMessage("User not found"),
            HttpStatus.UNAUTHORIZED
         );
      }
      
      String userId = loginUser.getUserId();
      
      ResourcesListJsonResponse jsonResp = 
      this._resourceService.getImageResourcesList(userId, pageIdx);
      if (jsonResp != null && jsonResp.isValid())
      {
         return new ResponseEntity<String>(
            JsonUtil.convertObjectToJson(jsonResp), HttpStatus.OK
         );
      }
      
      return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
   }

   @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
   @RequestMapping(value="/secure/imgresource/{resourceId}", method=RequestMethod.GET)
   public void downloadResourceSecure(
      @PathVariable("resourceId")
      String resourceId,
      HttpServletResponse response
   )
   {
      downloadImage(resourceId, response);
   }

   @RequestMapping(value="/public/imgresource/{resourceId}", method=RequestMethod.GET)
   public void downloadViewableImage(
      @PathVariable("resourceId")
      String resourceId,
      HttpServletResponse response
   )
   {
      downloadImage(resourceId, response);
   }

   @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
   @RequestMapping(value="/admin/resources/getArticleIcon", method=RequestMethod.GET)
   public ResponseEntity<String> getArticleIcon(
      @RequestParam("articleId")
      String articleId
   )
   {
      String iconUrl = 
      this._resourceService.getArticleIconUrl(articleId);
      
      if (StringUtils.isEmpty(iconUrl))
      {
         return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
      }
      
      return new ResponseEntity<String>(iconUrl, HttpStatus.OK);
   }
   
   @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
   @RequestMapping(value="/admin/resources/setArticleIcon", method=RequestMethod.POST)
   public ResponseEntity<String> setArticleIcon(
      @RequestParam("articleId")
      String articleId,
      @RequestParam("resourceId")
      String resourceId
   )
   {
      UserPrincipalDataModel loginUser = this._util.getLoginUser();
      if (loginUser == null)
      {
         return new ResponseEntity<String>(
            JsonUtil.simpleErrorMessage("User not found"),
            HttpStatus.UNAUTHORIZED
         );
      }
      
      boolean retVal
         = this._resourceService.setArticleIcon(
            articleId, resourceId
         );
      if (retVal)
      {
         String iconUrl = 
         this._resourceService.getArticleIconUrl(articleId);
         return new ResponseEntity<String>(iconUrl, HttpStatus.OK);
      }
      
      return new ResponseEntity<String>(HttpStatus.NOT_MODIFIED);
   }

   @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
   @RequestMapping(value="/admin/resources/removeArticleIcon", method=RequestMethod.DELETE)
   public ResponseEntity<String> removeArticleIcon(
      @RequestParam("articleId")
      String articleId
   )
   {
      boolean retVal
         = this._resourceService.removeArticleIcon(
            articleId
         );
      if (retVal)
      {
         return new ResponseEntity<String>(HttpStatus.OK);
      }
      
      return new ResponseEntity<String>(HttpStatus.NOT_MODIFIED);
   }
   
   
   @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
   @RequestMapping(value="/admin/resources/getIconResourcesList",
      method=RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<String> getIconResourcesList(
      @RequestParam("pageIdx")
      Integer pageIdx
   )
   {
      if (pageIdx == null)
      {
         pageIdx = new Integer(0);
      }
      
      if (pageIdx < 0)
      {
         return new ResponseEntity<String>(
            JsonUtil.simpleErrorMessage("pageIdx invalid"), HttpStatus.NOT_ACCEPTABLE
         );
      }
      
      UserPrincipalDataModel loginUser = this._util.getLoginUser();
      if (loginUser == null)
      {
         return new ResponseEntity<String>(
            JsonUtil.simpleErrorMessage("User not found"),
            HttpStatus.UNAUTHORIZED
         );
      }
      
      IconResourcesListJsonResponse respObj = 
      this._resourceService.getIconResourcesList(
         loginUser.getUserId(), pageIdx);
      
      if (!respObj.isValid())
      {
         return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
      }
      
      return new ResponseEntity<String>(
         JsonUtil.convertObjectToJson(respObj), HttpStatus.OK);
   }
 
   private void downloadImage(String resourceId, HttpServletResponse response)
   {
      try
      {
         OutputStream s = response.getOutputStream();
         
         boolean retVal = this._resourceService.downloadResource(resourceId, "image", s);
         
         response.setStatus(retVal? 200 : 404);
      }
      catch(Exception e)
      {
         response.setStatus(404);
      }
   }   
}

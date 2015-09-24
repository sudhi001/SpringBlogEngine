package org.hanbo.mvc.controllers;

import org.hanbo.mvc.controllers.utilities.ActionsUtil;
import org.hanbo.mvc.models.PageMetadata;
import org.hanbo.mvc.models.UserPrincipalDataModel;
import org.hanbo.mvc.services.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
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
         pageIdx = new Integer(1);
      }
      
      if (pageIdx <= 0)
      {
         return _util.createErorrPageViewModel(
            "Invalid Page Index",
            "The list of posts should have page idx >= 1."
         );
      }

      pageIdx -= 1;
      
      UserPrincipalDataModel loginUser = this._util.getLoginUser();
      if (loginUser == null)
      {
         return _util.createErorrPageViewModel("User Authorization Failure", "User cannot be found.");
      }

      // XXX fix here
      
      PageMetadata pageMetadata
         = _util.creatPageMetadata("List all my resources");
      ModelAndView retVal
         = _util.getDefaultModelAndView(
            "siteResourcesList", pageMetadata
         );
      // XXX fix here
      //retVal.addObject("articleListPageModel", articleListPage);
      
      return retVal;
   }
   
   @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
   @RequestMapping(value="/admin/resources/addNewTextResource", method=RequestMethod.POST)
   public ModelAndView addNewTextResource(
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
      
      _resourceService.saveTextResource(
          loginUser.getUserId(), resourceName, resourceSubType, resourceValue);
      
      return _util.createRedirectPageView(
         "redirect:/admin/resources/allMyResources?pageIdx=1"
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
         "redirect:/admin/resources/allMyResources?pageIdx=1"
      );
   }
}

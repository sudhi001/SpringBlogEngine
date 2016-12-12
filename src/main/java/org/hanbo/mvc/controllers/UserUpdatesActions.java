package org.hanbo.mvc.controllers;

import org.apache.commons.lang.StringUtils;
import org.hanbo.mvc.controllers.utilities.ActionsUtil;
import org.hanbo.mvc.models.PageMetadata;
import org.hanbo.mvc.models.UserPrincipalDataModel;
import org.hanbo.mvc.models.UserUpdatesPageDataModel;
import org.hanbo.mvc.services.UserStatusesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserUpdatesActions
{
   @Autowired
   private ActionsUtil _util;
   
   @Autowired
   private UserStatusesService userStatusesService;

   @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
   @RequestMapping(value = "/admin/updates/allMyUpdates/{pageIdx}",
      method=RequestMethod.GET)
   public ModelAndView allMyUpdates(
      @PathVariable("pageIdx")
      int pageIdx
   )
   {
      UserPrincipalDataModel loginUser = this._util.getLoginUser();
      if (loginUser == null)
      {
         return _util.createErorrPageViewModel("User Authorization Failure", "User cannot be found.");
      }
      
      String adminUserId = loginUser.getUserId();
      if (!StringUtils.isEmpty(adminUserId))
      {
         if (userStatusesService != null)
         {
            UserUpdatesPageDataModel pageModel = 
            userStatusesService.getAllUserUpdates(adminUserId, pageIdx);
            
            PageMetadata pageMetadata
               = _util.creatPageMetadata("Manage My Updates");
            ModelAndView retVal
               = _util.getDefaultModelAndView(
                  "adminUserUpdates", pageMetadata
               );
            retVal.addObject("adminUserUpdates", pageModel);
            return retVal;
         }
         else
         {
            return _util.createErorrPageViewModel(
               "Error occurred", "Injected user update service is not available.");
         }
      }
      else
      {
         return _util.createErorrPageViewModel(
            "Error occurred", "Invalid user detected.");
      }
   }
   
   @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
   @RequestMapping(value = "/admin/updates/addNewUpdate",
      method=RequestMethod.GET)
   public ModelAndView addNewUpdate()
   {
      PageMetadata pageMetadata
         = _util.creatPageMetadata("Add New Update");
      ModelAndView retVal
         = _util.getDefaultModelAndView(
            "editUserUpdate", pageMetadata
         );
      return retVal;
   }
   
   @RequestMapping(value = "/public/updates/allUpdates/{pageIdx}",
      method=RequestMethod.GET)
   public ModelAndView allMyViewableUpdates(
      @PathVariable("pageIdx")
      int pageIdx
   )
   {
      if (userStatusesService != null)
      {
         UserUpdatesPageDataModel pageModel
            = userStatusesService.getViewableUserUpdates(pageIdx);
         
         PageMetadata pageMetadata
            = _util.creatPageMetadata("My Updates");
         ModelAndView retVal
            = _util.getDefaultModelAndView(
               "userUpdates", pageMetadata
            );
         retVal.addObject("userUpdates", pageModel);
         return retVal;
      }
      else
      {
         return _util.createErorrPageViewModel(
            "Error occurred", "The injected service is NULL.");
      }
   }
}

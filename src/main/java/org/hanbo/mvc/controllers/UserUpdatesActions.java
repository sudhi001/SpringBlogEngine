package org.hanbo.mvc.controllers;

import org.hanbo.mvc.controllers.utilities.ActionsUtil;
import org.hanbo.mvc.models.PageMetadata;
import org.hanbo.mvc.models.UserPrincipalDataModel;
import org.hanbo.mvc.services.UserStatusesService;
import org.springframework.beans.factory.annotation.Autowired;
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
   
   @RequestMapping(value = "/public/updates/allUpdates/{pageIdx}",
      method=RequestMethod.GET)
   public ModelAndView allMyUpdates(
      @PathVariable("pageIdx")
      int pageIdx
   )
   {
      if (userStatusesService != null)
      {
         userStatusesService.getViewableUserUpdates(pageIdx);
         
         PageMetadata pageMetadata
            = _util.creatPageMetadata("My Updates");
         ModelAndView retVal
            = _util.getDefaultModelAndView(
               "userUpdates", pageMetadata
            );
         return retVal;
      }
      else
      {
         return _util.createErorrPageViewModel(
            "Error occurred", "The injected service is NULL.");
      }
   }
}

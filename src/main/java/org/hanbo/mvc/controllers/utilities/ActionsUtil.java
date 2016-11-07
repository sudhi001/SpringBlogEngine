package org.hanbo.mvc.controllers.utilities;

import org.hanbo.mvc.exceptions.WebAppException;
import org.hanbo.mvc.models.PageMetadata;
import org.hanbo.mvc.models.UserPrincipalDataModel;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

@Component
public class ActionsUtil
{
   public PageMetadata creatPageMetadata(String pageTitle)
   {
      PageMetadata retVal = new PageMetadata();
      retVal.setPageTitle(pageTitle);
      return retVal;
   }
   
   public ModelAndView getDefaultModelAndView(
       String viewName, PageMetadata pageMetadata
   )
   {
      ModelAndView retVal = new ModelAndView();
      retVal.setViewName(viewName);
      retVal.addObject("pageMetadata", pageMetadata);
      return retVal;
   }
   
   public UserPrincipalDataModel getLoginUser()
   {
      Authentication authentication
         = SecurityContextHolder.getContext().getAuthentication();
      Object userP = authentication.getPrincipal();
      UserPrincipalDataModel userPrincipal = null;
      if (userP != null && userP instanceof UserPrincipalDataModel)
      {
         userPrincipal
            = (UserPrincipalDataModel)userP;
      }
      
      return userPrincipal;
   }
   
   public ModelAndView createErorrPageViewModel(
      String errorPageTitle, String errorSummary
   )
   {
      PageMetadata pageMetadata
         = this.creatPageMetadata("Error Page");
      ModelAndView retVal
         = this.getDefaultModelAndView(
            "error", pageMetadata
         );
      retVal.addObject("errorTitle", errorPageTitle);
      retVal.addObject("errorSummary", errorSummary);
      
      return retVal;
   }
   
   public ModelAndView createRedirectPageView(String redirectUrl)
   {
      ModelAndView retVal = new ModelAndView();
      retVal.setViewName(redirectUrl);
      
      return retVal;
   }
}

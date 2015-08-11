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
      UserPrincipalDataModel userPrincipal
         = (UserPrincipalDataModel)authentication.getPrincipal();
      
      if (userPrincipal == null)
      {
         throw new WebAppException(
            "No user is logged in.",
            WebAppException.ErrorType.SECURITY);
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
            "errorPage", pageMetadata
         );
      retVal.addObject("errorTitle", errorPageTitle);
      retVal.addObject("errorSummary", errorSummary);
      
      return retVal;
   }
}

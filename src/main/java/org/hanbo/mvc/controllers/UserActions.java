package org.hanbo.mvc.controllers;

import org.hanbo.mvc.controllers.utilities.ActionsUtil;
import org.hanbo.mvc.models.PageMetadata;
import org.hanbo.mvc.models.UserInfoDataModel;
import org.hanbo.mvc.models.UserPrincipalDataModel;
import org.hanbo.mvc.models.UserProfileDataModel;
import org.hanbo.mvc.models.UserSignupDataModel;
import org.hanbo.mvc.services.LoginUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserActions
{
   @Autowired
   private LoginUserService _userService;
   
   @Autowired
   private ActionsUtil _actionUtil;
   
   @RequestMapping(value = "/signin", method = RequestMethod.GET)
   public ModelAndView login()
   {
      ModelAndView retVal = 
         _actionUtil.getDefaultModelAndView(
           "login", _actionUtil.creatPageMetadata("User Signin")
        );
      
      return retVal;
   }
      
   @RequestMapping(value = "/signup")
   public ModelAndView userSignup()
   {
      PageMetadata pageMetadata
         = _actionUtil.creatPageMetadata("login");
      pageMetadata.setPageTitle("User sign up");
      
      ModelAndView retVal = new ModelAndView();
      retVal.setViewName("userSignup");
      retVal.getModelMap().put("userSignupModel", new UserSignupDataModel());
      retVal.addObject("pageMetadata", pageMetadata);
      return retVal;
   }
   
   @RequestMapping(value = "/handleUserSignup")
   public ModelAndView handleUserSignup(
      @ModelAttribute("userSignupModel")
      UserSignupDataModel userToAdd
   )
   {
      _userService.validateUserInfoData(userToAdd);
      _userService.addUser(userToAdd);
      
      return createSignupSuccessPageModelAndView(
          userToAdd
      );
   }
   
   @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
   @RequestMapping(value = "/admin/editUserProfile")
   public ModelAndView editUserProfile()
   {
      UserPrincipalDataModel loginUser = this._actionUtil.getLoginUser();
      if (loginUser == null)
      {
         return _actionUtil.createErorrPageViewModel(
            "User Authorization Failure", "User cannot be found.");
      }
      
      UserInfoDataModel userInfo = _userService.getUserById(loginUser.getUserId());
      if (userInfo == null)
      {
         return _actionUtil.createErorrPageViewModel(
            "User cannot be found", "User cannot be found.");
      }
      UserProfileDataModel userProfileModel = new UserProfileDataModel();
      userProfileModel.setUserId(userInfo.getUserId());
      userProfileModel.setUserName(userInfo.getUserName());
      userProfileModel.setUserEmail(userInfo.getUserEmail());      
      
      // TODO
      
      PageMetadata pageMetadata
         = _actionUtil.creatPageMetadata("Create My User Profile");
      ModelAndView retVal
         = _actionUtil.getDefaultModelAndView(
              "editUserProfile", pageMetadata);
      retVal.addObject("editUserProfile", userProfileModel);
      
      return retVal;
   }
   
   @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
   @RequestMapping(value = "/admin/editUserProfile")
   public ModelAndView saveUserProfile(
      @ModelAttribute("editUserProfile")
      UserProfileDataModel userProfileToSave
   )
   {
      PageMetadata pageMetadata
         = _actionUtil.creatPageMetadata("Create My User Profile");
      ModelAndView retVal
         = _actionUtil.getDefaultModelAndView(
              "viewUserProfile", pageMetadata);
      retVal.addObject("userProfileToView", userProfileModel);
      
      return retVal;
   }
   
   private ModelAndView createSignupSuccessPageModelAndView(
      UserInfoDataModel userInfo
   )
   {
      PageMetadata pageMetadata = new PageMetadata();
      pageMetadata.setPageTitle("Sign-up successful");
      
      ModelAndView retVal = new ModelAndView();
      retVal.setViewName("signupStatus");
      retVal.addObject("pageMetadata", pageMetadata);
      retVal.addObject("userInfo", userInfo);
      
      return retVal;
   }
}

package org.hanbo.mvc.controllers;

import org.hanbo.mvc.controllers.utilities.ActionsUtil;
import org.hanbo.mvc.models.PageMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexActions
{
   @Autowired
   private ActionsUtil _util;
   
   @RequestMapping("/")
   public String base()
   {
      return "redirect:/index";
   }
   
   @RequestMapping("/index")
   public ModelAndView index()
   {
      PageMetadata pageMetadata
         = _util.creatPageMetadata("Home");
      ModelAndView retVal
         = _util.getDefaultModelAndView(
            "index", pageMetadata
         );
      
      return retVal;
   }
}

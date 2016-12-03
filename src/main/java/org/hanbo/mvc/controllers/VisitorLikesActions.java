package org.hanbo.mvc.controllers;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hanbo.mvc.controllers.utilities.ActionsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class VisitorLikesActions
{
   private static Logger _logger = LogManager.getLogger(BlogPostActions.class);
   
   @Autowired
   private ActionsUtil _util;
 
   @RequestMapping(value = "/likes/article/{articleId}", method=RequestMethod.GET)
   public ResponseEntity<String> addLikeToArticle(
      @PathVariable("articleId")
      String articleId,
      HttpServletRequest likeReq
   )
   {  
      return null;
   }
}

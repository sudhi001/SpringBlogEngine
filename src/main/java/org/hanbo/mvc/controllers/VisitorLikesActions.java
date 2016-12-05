package org.hanbo.mvc.controllers;

import javax.servlet.http.HttpServletRequest;

import org.hanbo.mvc.models.json.ArticleVisitorLikeStatistics;
//import org.apache.log4j.LogManager;
//import org.apache.log4j.Logger;
//import org.hanbo.mvc.controllers.utilities.ActionsUtil;
import org.hanbo.mvc.services.VisitorLikeService;
import org.hanbo.mvc.utilities.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class VisitorLikesActions
{
   //private static Logger _logger = LogManager.getLogger(BlogPostActions.class);
   
   @Autowired
   private VisitorLikeService _visitorLikeService;
   
   //@Autowired
   //private ActionsUtil _util;
 
   @RequestMapping(value = "/likes/article/{articleId}", method=RequestMethod.POST)
   public ResponseEntity<String> addLikeToArticle(
      @PathVariable("articleId")
      String articleId,
      @RequestParam("like")
      boolean visitorLike,
      HttpServletRequest likeReq
   )
   {
      System.out.println("Like: " + visitorLike);
      
      final HttpHeaders httpHeaders= new HttpHeaders();
      httpHeaders.setContentType(MediaType.APPLICATION_JSON);
      try
      {
         String ipAddress = likeReq.getHeader("X-FORWARDED-FOR");
         if (ipAddress == null) {
            ipAddress = likeReq.getRemoteAddr();
         }
         
         _visitorLikeService.addVisitorLikeToArticle(articleId, visitorLike, ipAddress);
         
         ArticleVisitorLikeStatistics articleLikeStatistics =
            _visitorLikeService.getArticleVisitorLikeStatistics(articleId);
         return new ResponseEntity<String>(JsonUtil.convertObjectToJson(articleLikeStatistics), httpHeaders, HttpStatus.OK);
      }
      catch(Exception ex)
      {
         ex.printStackTrace();
         String respJsonVal = JsonUtil.simpleErrorMessage("Unknown exception occurred.");
         return new ResponseEntity<String>(respJsonVal, HttpStatus.INTERNAL_SERVER_ERROR);
      }
   }
}

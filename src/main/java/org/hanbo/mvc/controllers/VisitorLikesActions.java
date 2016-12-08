package org.hanbo.mvc.controllers;

import javax.servlet.http.HttpServletRequest;

import org.hanbo.mvc.models.json.VisitorLikeStatistics;
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
   @Autowired
   private VisitorLikeService _visitorLikeService;
 
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
         
         VisitorLikeStatistics articleLikeStatistics =
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
   
   @RequestMapping(value = "/likes/article/{articleId}", method=RequestMethod.GET)
   public ResponseEntity<String> getLikesCountFromArticle(
      @PathVariable("articleId")
      String articleId
   )
   {
      final HttpHeaders httpHeaders= new HttpHeaders();
      httpHeaders.setContentType(MediaType.APPLICATION_JSON);
      try
      {
         VisitorLikeStatistics articleLikeStatistics =
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
   
   @RequestMapping(value = "/likes/image/{imageId}", method=RequestMethod.GET)
   public ResponseEntity<String> addLikeToImage(
      @PathVariable("imageId")
      String imageId
   )
   {
      final HttpHeaders httpHeaders= new HttpHeaders();
      httpHeaders.setContentType(MediaType.APPLICATION_JSON);
      try
      {
         VisitorLikeStatistics imageLikeStatistics =
            _visitorLikeService.getImageVisitorLikeStatistics(imageId);
         return new ResponseEntity<String>(JsonUtil.convertObjectToJson(imageLikeStatistics), httpHeaders, HttpStatus.OK);
      }
      catch(Exception ex)
      {
         ex.printStackTrace();
         String respJsonVal = JsonUtil.simpleErrorMessage("Unknown exception occurred.");
         return new ResponseEntity<String>(respJsonVal, HttpStatus.INTERNAL_SERVER_ERROR);
      }
   }
   
   @RequestMapping(value = "/likes/image/{imageId}", method=RequestMethod.POST)
   public ResponseEntity<String> addLikeToImage(
      @PathVariable("imageId")
      String imageId,
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
         
         _visitorLikeService.addVisitorLikeToImage(imageId, visitorLike, ipAddress);
         
         VisitorLikeStatistics imageLikeStatistics =
            _visitorLikeService.getImageVisitorLikeStatistics(imageId);
         return new ResponseEntity<String>(JsonUtil.convertObjectToJson(imageLikeStatistics), httpHeaders, HttpStatus.OK);
      }
      catch(Exception ex)
      {
         ex.printStackTrace();
         String respJsonVal = JsonUtil.simpleErrorMessage("Unknown exception occurred.");
         return new ResponseEntity<String>(respJsonVal, HttpStatus.INTERNAL_SERVER_ERROR);
      }
   }
}

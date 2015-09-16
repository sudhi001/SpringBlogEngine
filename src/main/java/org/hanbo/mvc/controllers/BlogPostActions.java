package org.hanbo.mvc.controllers;

import java.util.HashMap;
import java.util.Map;

import org.hanbo.mvc.controllers.utilities.ActionsUtil;
import org.hanbo.mvc.models.ArticleDataModel;
import org.hanbo.mvc.models.ArticleListPageDataModel;
import org.hanbo.mvc.models.PageMetadata;
import org.hanbo.mvc.models.UserPrincipalDataModel;
import org.hanbo.mvc.services.ArticleService;
import org.hanbo.mvc.services.PermaLinkService;
import org.hanbo.mvc.utilities.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BlogPostActions
{
   @Autowired
   private ActionsUtil _util;
   
   @Autowired
   private ArticleService _articleService;
   
   @Autowired
   private PermaLinkService _permaLinkService;
   
   @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
   @RequestMapping(value = "/admin/blog/newPost", method=RequestMethod.GET)
   public ModelAndView newPost()
   {  
      PageMetadata pageMetadata
         = _util.creatPageMetadata("New Post");
      ModelAndView retVal
         = _util.getDefaultModelAndView(
            "blogPostEdit", pageMetadata
         );
      retVal.addObject("newPostModel", new ArticleDataModel());
      retVal.addObject("actionName", "addNewPost");
      retVal.addObject("articleEditingTitle", "Create a New Post/Page");
      retVal.addObject("articleTypeList", createArticleTypeList());
      
      return retVal;
   }
   
   @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
   @RequestMapping(value = "/admin/blog/addNewPost", method=RequestMethod.POST, params="newPostSaveDraft")
   public ModelAndView addNewPost(
      @ModelAttribute("newPostModel")
      ArticleDataModel article
   )
   {
      UserPrincipalDataModel loginUser = this._util.getLoginUser();
      if (loginUser == null)
      {
         return _util.createErorrPageViewModel("User Authorization Failure", "User cannot be found.");
      }
      
      article.setAuthorId(loginUser.getUserId());
      article.setAuthorName(loginUser.getUserName());
      
      ArticleDataModel articleDataModel
         = this._articleService.saveArticle(article);
      
      PageMetadata pageMetadata
         = _util.creatPageMetadata("Preview Post");
      ModelAndView retVal
         = _util.getDefaultModelAndView(
            "previewPost", pageMetadata
         );
      retVal.addObject("articleModel", articleDataModel);
      
      return retVal;
   }
   
   @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
   @RequestMapping(value = "/admin/blog/previewPost/{articleId}", method=RequestMethod.GET)
   public ModelAndView previewPost(
      @PathVariable("articleId")
      String articleId
   )
   {
      UserPrincipalDataModel loginUser = this._util.getLoginUser();
      if (loginUser == null)
      {
         return _util.createErorrPageViewModel("User Authorization Failure", "User cannot be found.");
      }
      
      ArticleDataModel articleDataModel
         = this._articleService.getArticleById(articleId, loginUser.getUserId());

      PageMetadata pageMetadata
         = _util.creatPageMetadata("Preview Post");
      ModelAndView retVal
         = _util.getDefaultModelAndView(
            "previewPost", pageMetadata
         );
      retVal.addObject("articleModel", articleDataModel);
      
      return retVal;
   }
   
   @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
   @RequestMapping(value = "/admin/blog/editPost/{articleId}", method=RequestMethod.GET)
   public ModelAndView editPost(
      @PathVariable("articleId")
      String articleId
   )
   {
      UserPrincipalDataModel loginUser = this._util.getLoginUser();
      if (loginUser == null)
      {
         return _util.createErorrPageViewModel("User Authorization Failure", "User cannot be found.");
      }
      
      ArticleDataModel articleDataModel
         = this._articleService.getArticleById(articleId, loginUser.getUserId());
      
      PageMetadata pageMetadata
         = _util.creatPageMetadata("Preview Post");
      ModelAndView retVal
         = _util.getDefaultModelAndView(
            "blogPostEdit", pageMetadata
         );

      retVal.addObject("newPostModel", articleDataModel);
      retVal.addObject("actionName", "handleEditPost");
      retVal.addObject("articleEditingTitle", "Edit Post");
      retVal.addObject("articleTypeList", createArticleTypeList());
      
      return retVal;
   }

   @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
   @RequestMapping(value = "/admin/blog/handleEditPost", method=RequestMethod.POST)
   public ModelAndView handleEditPost(
      @ModelAttribute("newPostModel")
      ArticleDataModel article
   )
   {
      UserPrincipalDataModel loginUser = this._util.getLoginUser();
      if (loginUser == null)
      {
         return _util.createErorrPageViewModel("User Authorization Failure", "User cannot be found.");
      }
      
      article.setAuthorId(loginUser.getUserId());
      article.setAuthorName(loginUser.getUserName());
      
      ArticleDataModel articleDataModel
         = this._articleService.updateArticle(article);
      
      PageMetadata pageMetadata
         = _util.creatPageMetadata("Preview Post");
      ModelAndView retVal
         = _util.getDefaultModelAndView(
            "previewPost", pageMetadata
         );
      retVal.addObject("articleModel", articleDataModel);
      
      return retVal;
   }
      
   @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
   @RequestMapping(value = "/admin/blog/allMyPosts", method=RequestMethod.GET)
   public ModelAndView allMyPosts(
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

      ArticleListPageDataModel articleListPage
         = this._articleService.getUserArticleList(
            pageIdx, loginUser.getUserId()
         );
            
      PageMetadata pageMetadata
         = _util.creatPageMetadata("List all my posts");
      ModelAndView retVal
         = _util.getDefaultModelAndView(
            "authorPostsList", pageMetadata
         );
      retVal.addObject("articleListPageModel", articleListPage);
      
      return retVal;
   }
   
   @RequestMapping(value = "/blog/allPosts/{pageIdx}", method=RequestMethod.GET)
   public ModelAndView allBlogPosts(
      @PathVariable(value="pageIdx")
      int pageIdx
   )
   {
      if (pageIdx <= 0)
      {
         pageIdx = 1;
      }
      
      pageIdx -= 1;
      
      ArticleListPageDataModel articleListPage = 
      this._articleService.getAllViewablePosts(pageIdx);
      
      PageMetadata pageMetadata
         = _util.creatPageMetadata("Blog Posts");
      ModelAndView retVal
         = _util.getDefaultModelAndView(
            "blogPosts", pageMetadata
         );
      retVal.addObject("articleListPageModel", articleListPage);
      
      return retVal;
   }
   
   // +++ REST APIs
   
   @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
   @RequestMapping(value="/admin/blog/deletePost",
      method=RequestMethod.DELETE,
      produces = MediaType.APPLICATION_JSON_VALUE)
   @ResponseBody
   public ResponseEntity<String> deletePost(
      @RequestParam("articleId")
      String articleId
   )
   {
      UserPrincipalDataModel loginUser = this._util.getLoginUser();
      if (loginUser == null)
      {
         return new ResponseEntity<String>(
            JsonUtil.simpleErrorMessage("User not found"),
            HttpStatus.UNAUTHORIZED
         );
      }
      
      String userId = loginUser.getUserId();
      
      this._permaLinkService.deletePermaLink(articleId, userId);
      this._articleService.deleteOwnerArticle(
         articleId, userId
      );
      
      return new ResponseEntity<String>(HttpStatus.OK);
   }

   @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
   @RequestMapping(value="/admin/blog/publishPost",
      method=RequestMethod.PUT,
      produces = MediaType.APPLICATION_JSON_VALUE)
   @ResponseBody
   public ResponseEntity<String> publishPost(
      @RequestParam("articleId")
      String articleId,
      @RequestParam("toPublish")
      boolean toPublish
   )
   {
      UserPrincipalDataModel loginUser = this._util.getLoginUser();
      if (loginUser == null)
      {
         return new ResponseEntity<String>(
            JsonUtil.simpleErrorMessage("User not found"),
            HttpStatus.UNAUTHORIZED
         );
      }

      String userId = loginUser.getUserId();
      this._articleService.publishOwnerArticle(
         articleId, userId, toPublish
      );

      return new ResponseEntity<String>(HttpStatus.OK);
   }

   private Map<String, String> createArticleTypeList()
   {
      Map<String, String> retVal = new HashMap<String, String>();
      
      retVal.put("post", "Post");
      retVal.put("page", "Page");
      
      return retVal;
   }
}

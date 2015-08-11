package org.hanbo.mvc.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hanbo.mvc.controllers.utilities.ActionsUtil;
import org.hanbo.mvc.models.ArticleDataModel;
import org.hanbo.mvc.models.ArticleListDataItem;
import org.hanbo.mvc.models.PageMetadata;
import org.hanbo.mvc.models.UserPrincipalDataModel;
import org.hanbo.mvc.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BlogPostActions
{
   @Autowired
   private ActionsUtil _util;
   
   @Autowired
   private ArticleService _articleService;
   
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
   public ModelAndView publishNewPost(
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
   @RequestMapping(value = "/admin/blog/allPosts", method=RequestMethod.GET)
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

      /*List<ArticleListDataItem> articleList
         = this._articleService.getPostsAsListItems(
            loginUser.getUserId(), pageIdx
         );
      
      PageMetadata pageMetadata
         = _util.creatPageMetadata("Preview Post");
      ModelAndView retVal
         = _util.getDefaultModelAndView(
            "previewPost", pageMetadata
         );
      retVal.addObject("articleListModel", articleDataModel);
      */
      return null;
   }

   private Map<String, String> createArticleTypeList()
   {
      Map<String, String> retVal = new HashMap<String, String>();
      
      retVal.put("post", "Post");
      retVal.put("page", "Page");
      
      return retVal;
   }
}

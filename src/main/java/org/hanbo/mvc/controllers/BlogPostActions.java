package org.hanbo.mvc.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.hanbo.mvc.controllers.utilities.ActionsUtil;
import org.hanbo.mvc.exceptions.WebAppException;
import org.hanbo.mvc.models.ArticleDataModel;
import org.hanbo.mvc.models.ArticleListPageDataModel;
import org.hanbo.mvc.models.PageMetadata;
import org.hanbo.mvc.models.UserPrincipalDataModel;
import org.hanbo.mvc.models.VisitorCommentDataModel;
import org.hanbo.mvc.services.ArticleService;
import org.hanbo.mvc.services.CommentsService;
import org.hanbo.mvc.services.PermaLinkService;
import org.hanbo.mvc.utilities.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import org.apache.log4j.*;

@Controller
public class BlogPostActions
{
   private static Logger _logger = LogManager.getLogger(BlogPostActions.class);
   
   @Autowired
   private ActionsUtil _util;
   
   @Autowired
   private ArticleService _articleService;
   
   @Autowired
   private PermaLinkService _permaLinkService;
   
   @Autowired
   private CommentsService _commentsService;

   @ModelAttribute("newPostModel")
   public ArticleDataModel loadEmptyModelBean()
   {
      return new ArticleDataModel();
   }
   
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
      articleDataModel.setPreviewMode(true);
      
      return createModelViewForArticle(articleDataModel);
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
      
      return createPostViewModelView(
         loginUser.getUserId(), articleId, true);
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
      articleDataModel.setPreviewMode(true);
      
      return createModelViewForArticle(articleDataModel);
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
         pageIdx = new Integer(0);
      }
      
      if (pageIdx < 0)
      {
         return _util.createErorrPageViewModel(
            "Invalid Page Index",
            "The list of posts should have page idx >= 0."
         );
      }
            
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
   
   @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
   @RequestMapping(value = "/admin/image/postForBlog", method=RequestMethod.POST)
   public ModelAndView imagePostForBlog(
      @RequestParam("postImageId")
      String blogImageId,
      @RequestParam("blogTitle")
      String blogTitle,
      @RequestParam("blogKeywords")
      String blogKeywords,
      @RequestParam("blogContent")
      String blogContent,
      HttpServletRequest postReq
   )
   {
      UserPrincipalDataModel loginUser = this._util.getLoginUser();
      if (loginUser == null)
      {
         return _util.createErorrPageViewModel("User Authorization Failure", "User cannot be found.");
      }
      
      String basedUrlPath = postReq.getContextPath();
      String articleId = this._articleService.createBlogPostFromImage(loginUser.getUserId(),
         blogImageId, blogTitle, blogKeywords, blogContent, basedUrlPath);
      if (!StringUtils.isEmpty(articleId))
      {
         String toEditPostPage = String.format("redirect:/admin/blog/editPost/%s", articleId);
         return _util.createRedirectPageView(toEditPostPage);
      }
      else
      {
         return _util.createErorrPageViewModel("Failed with error", "Unable to create blog post from the image.");
      }
   }
   
   @RequestMapping(value = "/blog/allPosts/{pageIdx}", method=RequestMethod.GET)
   public ModelAndView allBlogPosts(
      @PathVariable(value="pageIdx")
      int pageIdx
   )
   {
      if (pageIdx <= 0)
      {
         pageIdx = 0;
      }
      
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

   @RequestMapping(value = "/blog/view/{articleId}", method=RequestMethod.GET)
   public ModelAndView blogView(
      @PathVariable("articleId")
      String articleId
   )
   {
      return viewArticle(articleId);
   }
   
   @RequestMapping(value = "/page/view/{articleId}", method=RequestMethod.GET)
   public ModelAndView pageView(
      @PathVariable("articleId")
      String articleId
   )
   {
      return viewArticle(articleId);
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
   
   @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
   @RequestMapping(value="/admin/blog/setArticleIcon",
      method=RequestMethod.POST,
      produces = MediaType.APPLICATION_JSON_VALUE)
   @ResponseBody
   public ResponseEntity<String> setArticleIcon(
      @RequestParam("articleId")
      String articleId,
      @RequestParam("articleIconId")
      String articleIconId
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
      
      try
      {
         this._articleService.setArticleIcon(articleId, articleIconId);
         return new ResponseEntity<String>(HttpStatus.OK);
      }
      catch(Exception e)
      {
         _logger.error(ExceptionUtils.getStackTrace(e));
         return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
   }

   @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
   @RequestMapping(value="/admin/blog/removeArticleIcon",
      method=RequestMethod.DELETE,
      produces = MediaType.APPLICATION_JSON_VALUE)
   @ResponseBody
   public ResponseEntity<String> removeArticleIcon(
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

      try
      {
         this._articleService.removeArticleIcon(articleId);
         return new ResponseEntity<String>(HttpStatus.OK);
      }
      catch(Exception e)
      {
         _logger.error(ExceptionUtils.getStackTrace(e));
         return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
      }

   }
   
   
   @ExceptionHandler(WebAppException.class)
   public ModelAndView handleException(WebAppException ex)
   {
      PageMetadata pageMetadata
         = _util.creatPageMetadata("Error Occurred");
      ModelAndView retVal
         = _util.getDefaultModelAndView(
              "error", pageMetadata
           );
      retVal.addObject("errorTitle", "Unexpected error occurred...");
      retVal.addObject("errorSummary",
         org.apache.commons.lang.exception.ExceptionUtils.getFullStackTrace(ex));
      
      return retVal;
   }

   private Map<String, String> createArticleTypeList()
   {
      Map<String, String> retVal = new HashMap<String, String>();
      
      retVal.put("post", "Post");
      retVal.put("page", "Page");
      
      return retVal;
   }
   
   // XXX not very good names
   private ModelAndView createPostViewModelView(
      String ownerId, String articleId, boolean previewMode)
   {
      ArticleDataModel articleDataModel
         = this._articleService.getArticleById(articleId, ownerId);
      articleDataModel.setPreviewMode(previewMode);
      return createModelViewForArticle(articleDataModel);
   }
   
   private ModelAndView createModelViewForArticle(
      ArticleDataModel articleDataModel)
   {
      String pageTitle;
      String pageTemplate;
      if (articleDataModel.getArticleType().equals("post"))
      {
         pageTitle = articleDataModel.isPreviewMode()?
            String.format("Preview Post - %s", articleDataModel.getArticleTitle())
            : articleDataModel.getArticleTitle();
         pageTemplate = "viewPost";
      }
      else
      {
         pageTitle = articleDataModel.isPreviewMode()?
            String.format("Preview Page - %s", articleDataModel.getArticleTitle())
            : articleDataModel.getArticleTitle();
         pageTemplate = "viewPage";         
      }
      
      PageMetadata pageMetadata
         = _util.creatPageMetadata(pageTitle);
      ModelAndView retVal
         = _util.getDefaultModelAndView(
              pageTemplate, pageMetadata
           );
      retVal.addObject("articleModel", articleDataModel);
      
      return retVal;
   }
   
   private ModelAndView viewArticle(String articleId)
   {
      ArticleDataModel articleDataModel
         = this._articleService.getArticleById(articleId);
      
      if (articleDataModel.isArticlePublished())
      {
         ModelAndView retVal = 
            createModelViewForArticle(articleDataModel);
         
         if (retVal != null)
         {
            List<VisitorCommentDataModel> allViewableComments
               = this._commentsService.getViewableArticleComments(articleId);
            
            if (allViewableComments != null && allViewableComments.size() > 0)
            {
               retVal.addObject("articleComments", allViewableComments);
            }
            else
            {
               retVal.addObject("articleComments", new ArrayList<VisitorCommentDataModel>());
            }
            
            return retVal;
         }
      }
      
      return _util.createErorrPageViewModel("Post is not published",
         String.format("This post with id [%s] has not been published.", articleId));
   }
}

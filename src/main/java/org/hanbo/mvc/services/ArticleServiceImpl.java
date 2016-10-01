package org.hanbo.mvc.services;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hanbo.mvc.entities.Article;
import org.hanbo.mvc.entities.LoginUser;
import org.hanbo.mvc.exceptions.WebAppException;
import org.hanbo.mvc.models.ArticleDataModel;
import org.hanbo.mvc.models.ArticleListPageDataModel;
import org.hanbo.mvc.models.ImageSizeDataModel;
import org.hanbo.mvc.models.ItemListPageDataModel;
import org.hanbo.mvc.models.SimplifiedArticleDataModel;
import org.hanbo.mvc.repositories.ArticlesRepository;
import org.hanbo.mvc.repositories.UsersRepository;
import org.hanbo.mvc.services.utilities.ArticleDataModelEntityMapping;
import org.hanbo.mvc.utilities.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import org.apache.log4j.*;

@Service
@PropertySource("classpath:/site.properties")
public class ArticleServiceImpl implements ArticleService
{
   private static Logger _logger = LogManager.getLogger(ArticleServiceImpl.class);
   
   @Autowired
   private UsersRepository userRepo;
   
   @Autowired
   private ArticlesRepository articlesRepo;
   
   @Autowired
   private Environment configValues;
   
   @Autowired
   private UserImageGalleryService userImageGalleryService;
   
   @Override
   public ArticleDataModel saveArticle(ArticleDataModel articleDataModel)
   {
      validateArticleDataModel(articleDataModel);
      
      Date dateNow = new Date();
      articleDataModel.setArticleCreateDate(dateNow);
      articleDataModel.setArticleUpdateDate(dateNow);
      
      LoginUser user = 
      userRepo.getUserById(articleDataModel.getAuthorId());
      if (user == null)
      {
         throw new WebAppException(
            String.format("User with id [%s] cannot be found.", articleDataModel.getAuthorId())
            , WebAppException.ErrorType.DATA);
      }
      
      Article article = ArticleDataModelEntityMapping.fromDataModel(articleDataModel);
      String articleId = IdUtil.generateUuid();
      article.setId(articleId);
      article.setAuthor(user);
      
      articlesRepo.saveArticle(article);
      
      articleDataModel.setArticleId(articleId);
      return articleDataModel;
   }
   
   @Override
   public ArticleDataModel getArticleById(String articleId)
   {
      Article article = articlesRepo.getReportById(articleId);
      
      if(article == null)
      {
         throw new WebAppException(
            String.format("Article with id [%s] cannot be found.", articleId)
            , WebAppException.ErrorType.DATA);
      }
      
      ArticleDataModel retVal = 
         ArticleDataModelEntityMapping.toDataModel(article);

      return retVal;
   }

   @Override
   public ArticleDataModel getArticleById(String articleId, String authorId)
   {
      Article article = articlesRepo.getReportById(articleId, authorId);
      
      if(article == null)
      {
         throw new WebAppException(
            String.format("Article with id [%s] cannot be found.", articleId)
            , WebAppException.ErrorType.DATA);
      }
      
      ArticleDataModel retVal = 
         ArticleDataModelEntityMapping.toDataModel(article);

      return retVal;
   }
   
   @Override
   public ArticleDataModel updateArticle(ArticleDataModel articleDataModel)
   {
      this.validateArticleDataModel(articleDataModel);
      
      String articleId = articleDataModel.getArticleId();
      String userId = articleDataModel.getAuthorId();
    
      validateArticleIds(articleId, userId);
      
      Article existingArticle
         = this.articlesRepo.getReportById(articleId, userId);
      
      Date dateNow = new Date();
      articleDataModel.setArticleUpdateDate(dateNow);
      articleDataModel.setArticleCreateDate(existingArticle.getCreateDate());

      ArticleDataModelEntityMapping.updateExistingEntity(articleDataModel, existingArticle);

      articlesRepo.saveArticle(existingArticle);
      
      return articleDataModel;
   }
   
   @Override
   public ArticleListPageDataModel getUserArticleList(
      int pageIdx,
      String authorId)
   {
      String itemsCount = configValues.getProperty("postEditsPerPage");
      
      int itemsCountVal = Integer.parseInt(itemsCount);
      
      List<Article> allArticles
         = this.articlesRepo.getAllArticlesByUserId(
            authorId, pageIdx, itemsCountVal, true
         );
      
      long allArticlesCount
         = this.articlesRepo.getAllArticlesCountsByUserId(authorId);
      
      List<SimplifiedArticleDataModel> allArticlesTransformed =
      ArticleDataModelEntityMapping.toDataListItems(allArticles);
      
      ArticleListPageDataModel listPageDataModel
         = new ArticleListPageDataModel();
      
      ItemListPageDataModel.<ArticleListPageDataModel>createPageModel(
         listPageDataModel, allArticlesTransformed.size(),
         (int)allArticlesCount, pageIdx, itemsCountVal);
      listPageDataModel.setListItems(allArticlesTransformed);

      return listPageDataModel;
   }
   
   @Override
   public void deleteOwnerArticle(String articleId, String ownerId)
   {
      validateArticleOwnership(articleId, ownerId);
      this.articlesRepo.deleteArticle(articleId);
   }
   
   @Override
   public void publishOwnerArticle(String articleId, String ownerId, boolean articleToPublish)
   {
      validateArticleOwnership(articleId, ownerId);
      this.articlesRepo.publishArticle(articleId, articleToPublish);      
   }
   
   @Override
   public ArticleListPageDataModel getAllViewablePosts(int pageIdx)
   {
      String itemsCount = this.configValues.getProperty("publicPostsPerPage");
      int itemsCountIntVal = Integer.parseInt(itemsCount);
      
      long totalPublicPostsCount = this.articlesRepo.getViewableArticlesCount("post");
      
      List<Article> allArticles
         = this.articlesRepo.getViewableArticles("post", pageIdx, itemsCountIntVal);
      
      List<SimplifiedArticleDataModel> allArticleDataModels= 
      ArticleDataModelEntityMapping.toDataListItems(allArticles);
      
      ArticleListPageDataModel retVal = new ArticleListPageDataModel();
      ItemListPageDataModel.createPageModel(retVal, allArticleDataModels.size(),
         (int)totalPublicPostsCount, pageIdx, itemsCountIntVal);
      retVal.setListItems(allArticleDataModels);
      
      return retVal;
   }
   
   public String createBlogPostFromImage(String ownerId, String imageId, String postTitle,
      String postKeywords, String postContent)
   {
      LoginUser user = 
      userRepo.getUserById(ownerId);
      if (user == null)
      {
         throw new WebAppException(
            String.format("User with id [%s] cannot be found.", ownerId)
            , WebAppException.ErrorType.DATA);
      }
      
      ImageSizeDataModel imageSizeInfo = 
         userImageGalleryService.getImageDimension(imageId);
      if (imageSizeInfo != null)
      {
         _logger.debug("imageSizeInfo is not null");
         StringBuilder sb = new StringBuilder();
         sb.append("<!-- Image size: ");
         sb.append(imageSizeInfo.getImageSizeX());
         sb.append("x");
         sb.append(imageSizeInfo.getImageSizeY());
         sb.append("; ratio: ");
         sb.append(String.format("%.2f", imageSizeInfo.getWidthToHeightRatio()));
         sb.append(" -->");
         sb.append(System.lineSeparator());
         sb.append("<p>");
         if (imageSizeInfo.getImageSizeX() > imageSizeInfo.getImageSizeY())
         {
            sb.append("<div class=\"row\">");
            sb.append("<div class=\"col-xs-12 thumbnail\">");
            sb.append("<img src=\"");
            sb.append("/public/image/");
            sb.append(imageId);
            sb.append("\" class=\"img-responsive\" width=\"100%\">");
            sb.append("</div>");
            sb.append("</div>");
         }
         else
         {
            sb.append("<div class=\"row\">");
            sb.append("<div class=\"col-xs-8 col-xs-offset-2\">");
            sb.append("<img src=\"");
            sb.append("/public/image/");
            sb.append(imageId);
            sb.append("\" class=\"img-responsive\" width=\"100%\">");
            sb.append("</div>");
            sb.append("</div>");
         }
         sb.append("</p>");
         sb.append(System.lineSeparator());
         sb.append("<p>");
         sb.append(postContent);
         sb.append("</p>");
       
         _logger.debug(sb.toString());
         
         Date dateNow = new Date();
         
         Article article = new Article();
         String articleId = IdUtil.generateUuid();
         article.setId(articleId);
         article.setAuthor(user);
         article.setArticleType("post");
         article.setArticleCategory("");
         article.setArticleKeywords(postKeywords);
         article.setArticleContent(sb.toString());
         article.setArticleSummary("");
         article.setArticleTitle(postTitle);
         article.setCreateDate(dateNow);
         article.setUpdateDate(dateNow);
         
         this.articlesRepo.saveArticle(article);
         
         _logger.debug(articleId);
         return articleId;
      }
      else
      {
         _logger.debug("imageSizeInfo is null");
      }
      
      return "";
   }

   private void validateArticleOwnership(String articleId, String ownerId)
   {
      String articleOwnerId = this.articlesRepo.getArticleOwnerId(articleId);
      
      if (StringUtils.isEmpty(articleOwnerId))
      {
         throw new WebAppException("Article owner id not found.", WebAppException.ErrorType.DATA);
      }
      
      if (!articleOwnerId.equals(ownerId))
      {
         throw new WebAppException("Article owner id mismatch.", WebAppException.ErrorType.DATA);
      }
   }
   
   private void validateArticleDataModel(ArticleDataModel articleDataModel)
   {
      if (StringUtils.isEmpty(articleDataModel.getArticleTitle()))
      {
         throw new WebAppException("Article title cannot be empty.", WebAppException.ErrorType.DATA);
      }
      
      if (articleDataModel.getArticleTitle().length() > 128)
      {
         throw new WebAppException("Article title has too many characters.", WebAppException.ErrorType.DATA);
      }
         
      if (articleDataModel.getArticleKeywords().length() > 128)
      {
         throw new WebAppException("Article keywords has too many characters.", WebAppException.ErrorType.DATA);
      }

      if (articleDataModel.getArticleCategory().length() > 64)
      {
         throw new WebAppException("Article category has too many characters.", WebAppException.ErrorType.DATA);
      }
      
      if (StringUtils.isEmpty(articleDataModel.getArticleType()))
      {
         throw new WebAppException("Article type cannot be empty.", WebAppException.ErrorType.DATA);
      }
      
      if (!articleDataModel.getArticleType().equals("post") && !articleDataModel.getArticleType().equals("page"))
      {
         throw new WebAppException("Article type is invalid.", WebAppException.ErrorType.DATA);
      }
      
      if (!StringUtils.isEmpty(articleDataModel.getArticleSummary())
            && articleDataModel.getArticleSummary().length() > 2048)
      {
         throw new WebAppException("Article summary has too many characters.", WebAppException.ErrorType.DATA);
      }
      
      if (StringUtils.isEmpty(articleDataModel.getAuthorId()))
      {
         throw new WebAppException("Author id cannot be empty.", WebAppException.ErrorType.DATA);
      }
   }
   
   private void validateArticleIds(String articleId, String ownerId)
   {
      if (StringUtils.isEmpty(articleId))
      {
         throw new WebAppException("Article id cannot be empty.", WebAppException.ErrorType.DATA);
      }
      
      if (StringUtils.isEmpty(ownerId))
      {
         throw new WebAppException("Article owner id cannot be empty.", WebAppException.ErrorType.DATA);
      }
   }
}

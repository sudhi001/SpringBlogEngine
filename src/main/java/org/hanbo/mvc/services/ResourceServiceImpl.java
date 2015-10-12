package org.hanbo.mvc.services;

import java.io.File;
import java.io.FilenameFilter;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hanbo.mvc.entities.FileResource;
import org.hanbo.mvc.entities.LoginUser;
import org.hanbo.mvc.entities.Resource;
import org.hanbo.mvc.entities.TextResource;
import org.hanbo.mvc.exceptions.WebAppException;
import org.hanbo.mvc.models.ItemListPageDataModel;
import org.hanbo.mvc.models.ResourceListItemDataModel;
import org.hanbo.mvc.models.ResourceListPageDataModel;
import org.hanbo.mvc.models.json.TextResourceJsonResponse;
import org.hanbo.mvc.models.json.TextResourcesListJsonResponse;
import org.hanbo.mvc.repositories.ResourcesRepository;
import org.hanbo.mvc.repositories.UsersRepository;
import org.hanbo.mvc.services.utilities.ResourceDataModelEntityMapping;
import org.hanbo.mvc.utilities.FileStreamUtil;
import org.hanbo.mvc.utilities.IdUtil;
import org.hanbo.mvc.utilities.ImageFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@PropertySource("classpath:/site.properties")
public class ResourceServiceImpl implements ResourceService
{
   @Autowired
   private Environment configValues;
   
   @Autowired
   private UsersRepository _usersRepo;
   
   @Autowired
   private ResourcesRepository _resRepo;
   
   @Override
   public void saveTextResource(
      String ownerId, String resourceName,
      String resourceSubType, String resourceValue
   )
   {
      validateTextResourceInputs(
         resourceName,
         resourceSubType,
         resourceValue
      );
      
      LoginUser resourceOwner = _usersRepo.getUserById(ownerId);
      if (resourceOwner == null)
      {
         throw new WebAppException(
            String.format("Unable to find user with id [%s]", ownerId),
            WebAppException.ErrorType.DATA);
      }
      
      TextResource textResource = new TextResource();
      textResource.setId(IdUtil.generateUuid());
      textResource.setName(resourceName);
      textResource.setResourceType("text");
      textResource.setResourceSize(resourceValue.length());
      textResource.setResourceValue(resourceValue);
      
      Date dateNow = new Date();
      textResource.setCreateDate(dateNow);
      textResource.setUpdateDate(dateNow);
      textResource.setSubResourceType(resourceSubType);
      textResource.setOwner(resourceOwner);
      
      _resRepo.saveResource(textResource);
   }
   
   @Override
   public void updateTextResource(String ownerId, String resourceId,
         String resourceName, String resourceSubType, String resourceValue)
   {
      // TODO Auto-generated method stub
      validateTextResourceInputs(
         resourceName,
         resourceSubType,
         resourceValue
      );
      
      LoginUser resourceOwner = _usersRepo.getUserById(ownerId);
      if (resourceOwner == null)
      {
         throw new WebAppException(
            String.format("Unable to find user with id [%s]", ownerId),
            WebAppException.ErrorType.DATA);
      }
      
      Resource res = _resRepo.getResourceByOwner(resourceId, ownerId);
      if (res.getResourceType().equals("text"))
      {
         TextResource tres  = (TextResource)res;
         tres.setName(resourceName);
         tres.setSubResourceType(resourceSubType);
         tres.setResourceValue(resourceValue);
         
         _resRepo.saveResource(tres);
      }
   }
   
   @Override
   public void saveResourceFile(
      String ownerId,
      String resourceName,
      String subType, MultipartFile fileToSave)
   {
      validateFileResourceInputs(
         resourceName,
         subType,
         fileToSave
      );
      
      LoginUser resourceOwner = _usersRepo.getUserById(ownerId);
      if (resourceOwner == null)
      {
         throw new WebAppException(
            String.format("Unable to find user with id [%s]", ownerId),
            WebAppException.ErrorType.DATA);
      }
      
      String resourceId = IdUtil.generateUuid();
      String resourcePath = resourcePath(subType.toUpperCase(), resourceId);
      
      String resourceFileNameOnly
         = resourceFileNameOnly(fileToSave.getOriginalFilename(), resourceId);
      
      String fullResourceFileName = resourcePath + resourceFileNameOnly;
      
      try
      {
         FileStreamUtil.saveFileToServer(
            fullResourceFileName, fileToSave.getInputStream()
         );
      }
      catch (Exception e)
      {
         throw new WebAppException(
            String.format("Exception happened trying to save the resource file [%s]", fileToSave.getOriginalFilename()),
            WebAppException.ErrorType.OPERATIONAL,
            e
         );
      }
      
      FileResource fileResource = new FileResource();
      fileResource.setId(resourceId);
      fileResource.setName(resourceName);
      fileResource.setResourceType("file");
      fileResource.setResourceFileName(fullResourceFileName);
      
      Date dateNow = new Date();
      fileResource.setCreateDate(dateNow);
      fileResource.setUpdateDate(dateNow);
      fileResource.setSubResourceType(subType);
      
      if (subType.equals("image"))
      {
         setImageFileDimension(fileResource);
      }
      
      fileResource.setOwner(resourceOwner);
      
      _resRepo.saveResource(fileResource);
   }

   @Override
   public List<ResourceListItemDataModel> getOwnerResources(
      String ownerId, int pageIdx)
   {
      String itemsCount = configValues.getProperty("ResourceListItemsPerPage");  
      int itemsCountVal = Integer.parseInt(itemsCount);
      
      List<Resource> allFoundResources = 
         this._resRepo.getResourcesByOwnerId(ownerId, pageIdx, itemsCountVal);
      
      List<ResourceListItemDataModel> retVal
         = ResourceDataModelEntityMapping.listItemsFromEntities(allFoundResources);
      
      return retVal;
   }
   
   @Override
   public ResourceListPageDataModel getOwnerResourcesPage(
      String ownerId,
      int pageIdx)
   {
      String itemsCount = configValues.getProperty("ResourceListItemsPerPage");  
      int itemsCountVal = Integer.parseInt(itemsCount);
      
      int resourceCount = (int)this._resRepo.getResourceCountByOwnerId(ownerId);
      
      List<ResourceListItemDataModel> listRes = getOwnerResources(ownerId, pageIdx);
      
      ResourceListPageDataModel retVal = new ResourceListPageDataModel();
      ItemListPageDataModel.createPageModel(retVal, listRes.size(), resourceCount, pageIdx, itemsCountVal);
      retVal.setListOfResources(listRes);
      
      return retVal;
   }
   
   @Override
   public boolean downloadResource(
      String resourceId,
      String resourceSubType, OutputStream outStream)
     throws Exception
   {
      String resourcePath = resourcePath(resourceSubType.toUpperCase(), resourceId);
      final String fileToSearch = resourceId + ".";
      
      File dirFile = new File(resourcePath);
      if (dirFile.exists())
      {
         File[] filesFound = dirFile.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
               return name.contains(fileToSearch);
            }
         });
         
         if (filesFound.length > 0)
         {
            FileStreamUtil.readFileToOutputStream(filesFound[0].getAbsolutePath(), outStream);
            return true;
         }
      }
      
      return false;
   }
   
   @Override
   public void deleteResource(String resourceId, String ownerId)
   {
      Resource resReturned = this._resRepo.getResourceByOwner(resourceId, ownerId);
      
      if (resReturned != null)
      {
         if(resReturned.getResourceType().equals("file"))
         {
            File file = new File(((FileResource)resReturned).getResourceFileName());
            
            if (file.exists())
            {
               file.delete();
            }
         }
         
         this._resRepo.deleteResource(resReturned);
      }
   }
   
   @Override
   public TextResourceJsonResponse getTextResource(
      String resourceId,
      String ownerId
   )
   {
      Resource resource = this._resRepo.getResourceByOwner(resourceId, ownerId);
      if (resource != null && resource.getResourceType().equals("text"))
      {
         TextResource textRes = (TextResource)resource;
         TextResourceJsonResponse retVal = new TextResourceJsonResponse();
         retVal.setResourceId(textRes.getId());
         retVal.setResourceName(textRes.getName());
         retVal.setSubType(textRes.getSubResourceType());
         retVal.setResourceValue(textRes.getResourceValue());
         
         return retVal;
      }
      
      return null;
   }
   
   @Override
   public TextResourceJsonResponse getFormattedTextResource(
      String resourceId,
      String ownerId
   )
   {
      TextResourceJsonResponse retVal = getTextResource(
         resourceId,
         ownerId
      );
      
      if (retVal != null)
      {
         String resVal = 
         createFormattedTextResourceValue(retVal);
         
         retVal.setFormattedResourceValue(resVal);
      }
      
      return retVal;
   }
   
   @Override
   public TextResourcesListJsonResponse getTextResourcesList(
      String ownerId,
      int pageIdx
   )
   {
      String itemsCount = configValues.getProperty("ResourceListItemsJsonPerPage");  
      int itemsCountVal = Integer.parseInt(itemsCount);
      
      List<Resource> allFoundResources = 
         this._resRepo.getResourcesByTypeAndOwnerId(ownerId, "text", pageIdx, itemsCountVal);
      
      List<ResourceListItemDataModel> resList
         = ResourceDataModelEntityMapping.listItemsFromEntities(allFoundResources);
      
      TextResourcesListJsonResponse retVal = new TextResourcesListJsonResponse();
      retVal.setOwnerId(ownerId);
      retVal.setPageIdx(pageIdx);
      retVal.setResourceList(resList);
      
      return retVal;
   }
   
   private void validateTextResourceInputs(
      String resourceName,
      String resourceSubType,
      String resourceValue
   )
   {
      if (StringUtils.isEmpty(resourceName))
      {
         throw new WebAppException(
            "Resource Name cannot be null or empty string", WebAppException.ErrorType.DATA
         );
      }
      
      if (StringUtils.isEmpty(resourceSubType))
      {
         throw new WebAppException(
            "Resource SubType cannot be null or empty string", WebAppException.ErrorType.DATA
         );
      }

      if (StringUtils.isEmpty(resourceValue))
      {
         throw new WebAppException(
            "Resource Value cannot be null or empty string", WebAppException.ErrorType.DATA
         );
      }

      if (resourceName.length() > 96)
      {
         throw new WebAppException(
            "Resource Name is too long, must be 96 chars or fewer.", WebAppException.ErrorType.DATA
         );
      }
      
      if (resourceSubType.length() > 16)
      {
         throw new WebAppException(
            "Resource SubType is too long, must be 16 chars or fewer.", WebAppException.ErrorType.DATA
         );
      }

      if (resourceValue.length() > 65535)
      {
         throw new WebAppException(
            "Resource Value is too long, must be 65535 chars or fewer", WebAppException.ErrorType.DATA
         );
      }
   }

   private void validateFileResourceInputs(
      String resourceName,
      String resourceSubType,
      MultipartFile fileToSave
   )
   {
      if (StringUtils.isEmpty(resourceName))
      {
         throw new WebAppException(
            "Resource Name cannot be null or empty string", WebAppException.ErrorType.DATA
         );
      }
      
      if (StringUtils.isEmpty(resourceSubType))
      {
         throw new WebAppException(
            "Resource SubType cannot be null or empty string", WebAppException.ErrorType.DATA
         );
      }

      if (StringUtils.isEmpty(fileToSave.getOriginalFilename()))
      {
         throw new WebAppException(
            "File to upload cannot be null or empty string", WebAppException.ErrorType.DATA
         );
      }

      if (resourceName.length() > 96)
      {
         throw new WebAppException(
            "Resource Name is too long, must be 96 chars or fewer.", WebAppException.ErrorType.DATA
         );
      }
      
      if (resourceSubType.length() > 16)
      {
         throw new WebAppException(
            "Resource SubType is too long, must be 16 chars or fewer.", WebAppException.ErrorType.DATA
         );
      }
   }

   
   private String resourcePath(String type, String resourceId)
   {
      StringBuilder sb = new StringBuilder();
      
      String resourceBasePath = configValues.getProperty("resBasePath");
      sb.append(resourceBasePath);
      sb.append("/");
      sb.append(type);
      sb.append("/");
      FileStreamUtil.directoryPathExists(sb.toString());
      
      char char1 = resourceId.charAt(0);
      char char2 = resourceId.charAt(1);
      sb.append(char1);
      sb.append("/");
      FileStreamUtil.directoryPathExists(sb.toString());
      
      sb.append(char2);
      sb.append("/");
      FileStreamUtil.directoryPathExists(sb.toString());
      
      return sb.toString();
   }
   
   public String resourceFileNameOnly(String origFileName, String resourceId)
   {
      int dotPos = origFileName.lastIndexOf(".");
      if (dotPos >= 0 && dotPos < origFileName.length())
      {
         StringBuilder sb = new StringBuilder();

         String fileExt = origFileName.substring(dotPos);
         sb.append(resourceId);
         sb.append(fileExt);
         
         return sb.toString();
      }
      
      throw new WebAppException(
         String.format("The original file name [%s] is invalid (no dot or file extension).", origFileName),
         WebAppException.ErrorType.FUNCTIONAL
      );
   }
   
   private void setImageFileDimension(FileResource fileResource)
   {
      try
      {
         ImageFileUtil imgFileUtil = new ImageFileUtil();
         
         imgFileUtil.setOriginalFile(fileResource.getResourceFileName());
         imgFileUtil.imageFileDimensions();
         
         fileResource.setImageWidth(imgFileUtil.getOriginalImageFileSizeX());
         fileResource.setImageHeight(imgFileUtil.getOriginalImageFileSizeY());
      }
      catch(Exception e)
      {
         throw new WebAppException("Unable to find the dimension of the image.",
            WebAppException.ErrorType.FUNCTIONAL, e);
      }
   }
   
   private String createFormattedTextResourceValue(TextResourceJsonResponse respObj)
   {
      StringBuilder sb = new StringBuilder();
      
      sb.append("<!-- resourceId = [");
      sb.append(respObj.getResourceId());
      sb.append("], subType=[");
      sb.append(respObj.getSubType());
      sb.append("] -->");
      sb.append(System.getProperty("line.separator"));
      
      if (respObj.getSubType().equals("text") || respObj.getSubType().equals("html"))
      {
         sb.append(respObj.getResourceValue());
         sb.append(System.getProperty("line.separator"));
      }
      else if (respObj.getSubType().equals("quote"))
      {
         sb.append("<blockquote><p>");
         sb.append(respObj.getResourceValue());
         sb.append("</p></blockquote>");
         sb.append(System.getProperty("line.separator"));
      }
      else if (respObj.getSubType().equals("snippet"))
      {
         sb.append("<pre>");
         sb.append(respObj.getResourceValue());
         sb.append("</pre>");
         sb.append(System.getProperty("line.separator"));
      }
      sb.append(System.getProperty("line.separator"));
      
      return sb.toString();
   }
}

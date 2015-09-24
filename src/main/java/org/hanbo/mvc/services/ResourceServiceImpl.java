package org.hanbo.mvc.services;

import java.io.InputStream;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.hanbo.mvc.entities.FileResource;
import org.hanbo.mvc.entities.LoginUser;
import org.hanbo.mvc.entities.TextResource;
import org.hanbo.mvc.exceptions.WebAppException;
import org.hanbo.mvc.repositories.ResourcesRepository;
import org.hanbo.mvc.repositories.UsersRepository;
import org.hanbo.mvc.utilities.FileStreamUtil;
import org.hanbo.mvc.utilities.IdUtil;
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
         
      }
      
      fileResource.setOwner(resourceOwner);
      
      _resRepo.saveResource(fileResource);
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
   
   /*@Override
   public String resourcePath(String type, String resourceId)
   {
      StringBuilder sb = new StringBuilder();
      
      String resourceBasePath = configValues.getProperty("resBasePath");
      sb.append(resourceBasePath);
      sb.append("/");
      sb.append(type);
      sb.append("/");
      sb.append("/");
      
      char char1 = resourceId.charAt(0);
      char char2 = resourceId.charAt(1);
      sb.append(char1);
      sb.append("/");
      sb.append(char2);
      sb.append("/");
      
      return sb.toString();
   }

   @Override
   public String resourceFileName(String type, String resourceId, String fileExt)
   {
      StringBuilder sb = new StringBuilder();
      
      String filePath = resourcePath(type, resourceId);
      
      sb.append(filePath);
      sb.append(resourceId);
      sb.append(".");
      sb.append(fileExt);
      
      return sb.toString();
   }
   
   @Override
   public void saveToFile(String fileName, InputStream uploadFileStream)
   {
      try
      {
         FileStreamUtil.saveFileToServer(fileName, uploadFileStream);
      }
      catch(Exception e)
      {
         throw new WebAppException(
            String.format("Unable to save to file [%s], exception message: [%s]", fileName, e.getMessage()),
            WebAppException.ErrorType.FUNCTIONAL,
            e
         );
      }
   }

   @Override
   public void saveResource() {
      // TODO Auto-generated method stub
      
   }*/
}

package org.hanbo.mvc.utilities;

import java.io.InputStream;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.IOUtils;
import com.google.gson.Gson;

public class JsonUtil
{
   public static String convertObjectToJson(Object obj)
   {
      Gson gson = new Gson();
      String retVal = gson.toJson(obj);
      
      return retVal;
   }
   
   public static <T> T convertJsonToObject(String jsonVal, Class<T> objType)
   {
      Gson gson = new Gson();
      T retVal = gson.fromJson(jsonVal, objType);
      
      return retVal;
   }
   
   public static String simpleErrorMessage(String errMsg)
   {
      String retVal = String.format(
         "{ 'ErrorMessage' : '%s' }", errMsg
      );
      
      return retVal;
   }
   
   public static String readHttpRequestBody(HttpServletRequest request)
   {
      InputStream inStrm = null;
      try
      {
         inStrm = request.getInputStream();
         return IOUtils.toString(inStrm);   
      }
      catch (Exception e)
      {
         return "";
      }
      finally
      {
         IOUtils.closeQuietly(inStrm);
      }
   }
   
}

package org.hanbo.mvc.models.json;

public class GenericValueResponse <T>
{
   private T responseValue;

   public T getResponseValue()
   {
      return responseValue;
   }

   public void setResponseValue(T responseValue)
   {
      this.responseValue = responseValue;
   }
}

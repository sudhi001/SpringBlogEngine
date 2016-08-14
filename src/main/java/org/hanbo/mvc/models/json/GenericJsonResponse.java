package org.hanbo.mvc.models.json;

public class GenericJsonResponse
{
   private boolean operationSuccess;
   
   private String statusMessage;

   public GenericJsonResponse()
   {
      
   }

   public GenericJsonResponse(boolean opSuccess, String statusMsg)
   {
      operationSuccess = opSuccess;
      statusMessage = statusMsg;
   }

   public boolean isOperationSuccess()
   {
      return operationSuccess;
   }

   public void setOperationSuccess(boolean operationSuccess)
   {
      this.operationSuccess = operationSuccess;
   }

   public String getStatusMessage()
   {
      return statusMessage;
   }

   public void setStatusMessage(String statusMessage)
   {
      this.statusMessage = statusMessage;
   }
}

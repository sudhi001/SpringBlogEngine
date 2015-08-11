package org.hanbo.mvc.exceptions;

public class WebAppException extends RuntimeException
{
   private static final long serialVersionUID = -4294791458605455595L;

   public enum ErrorType {
      SECURITY, DATA, FUNCTIONAL, OPERATIONAL 
   };
   
   private ErrorType _errorType;
   
   public WebAppException(String errMsg, ErrorType errorType)
   {
      super(errMsg);
      
      _errorType = errorType;
   }
   
   public WebAppException(String errMsg, ErrorType errorType, Exception e)
   {
      super(errMsg, e);
      
      _errorType = errorType;
   }

   public ErrorType getErrorType()
   {
      return _errorType;
   }

   public void setErrorType(ErrorType _errorType)
   {
      this._errorType = _errorType;
   }  
}

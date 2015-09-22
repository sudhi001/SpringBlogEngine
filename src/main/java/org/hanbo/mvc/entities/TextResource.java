package org.hanbo.mvc.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="textresource")
@PrimaryKeyJoinColumn(name="id")
public class TextResource extends Resource
{  
   @Column(name = "subtype", nullable = false, length = 16)
   private String subResourceType;
   
   @Column(name = "value", nullable = false)
   private String resValue;

   @Column(name = "length", nullable = false)
   private int resSize;
   
   public String getSubResourceType()
   {
      return subResourceType;
   }

   public void setSubResourceType(String subResourceType)
   {
      this.subResourceType = subResourceType;
   }

   public String getResourceValue()
   {
      return resValue;
   }

   public void setResourceValue(String resValue)
   {
      this.resValue = resValue;
   }

   public int getResourceSize()
   {
      return resSize;
   }

   public void setResourceSize(int resSize)
   {
      this.resSize = resSize;
   }
}

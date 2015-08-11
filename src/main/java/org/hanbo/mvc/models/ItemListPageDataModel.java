package org.hanbo.mvc.models;

public class ItemListPageDataModel
{
   private int beginIndex;
   private int endIndex;
   private boolean canGoBack;
   private boolean hasMoreElement;
   private int pageElementsCount;
   private int totalElementsCount;
   
   private int pageIdx;
   private int previousPageIdx;
   private int nextPageIdx;
   
   public int getBeginIndex()
   {
      return beginIndex;
   }
   
   public void setBeginIndex(int beginIndex)
   {
      this.beginIndex = beginIndex;
   }

   public int getEndIndex()
   {
      return endIndex;
   }

   public void setEndIndex(int endIndex)
   {
      this.endIndex = endIndex;
   }

   public boolean isCanGoBack()
   {
      return canGoBack;
   }

   public void setCanGoBack(boolean canGoBack)
   {
      this.canGoBack = canGoBack;
   }

   public boolean isHasMoreElement()
   {
      return hasMoreElement;
   }

   public void setHasMoreElement(boolean hasMoreElement)
   {
      this.hasMoreElement = hasMoreElement;
   }

   public int getPageElementsCount()
   {
      return pageElementsCount;
   }

   public void setPageElementsCount(int pageElemntsCount)
   {
      this.pageElementsCount = pageElemntsCount;
   }

   public int getTotalElementsCount()
   {
      return totalElementsCount;
   }

   public void setTotalElementsCount(int totalElementsCount)
   {
      this.totalElementsCount = totalElementsCount;
   }

   public int getPageIdx()
   {
      return pageIdx;
   }

   public void setPageIdx(int pageIdx)
   {
      this.pageIdx = pageIdx;
   }

   public int getPreviousPageIdx()
   {
      return previousPageIdx;
   }

   public void setPreviousPageIdx(int previousPageIdx)
   {
      this.previousPageIdx = previousPageIdx;
   }

   public int getNextPageIdx()
   {
      return nextPageIdx;
   }

   public void setNextPageIdx(int nextPageIdx)
   {
      this.nextPageIdx = nextPageIdx;
   }
   
   public boolean isDataModelEmpty()
   {
      return this.getBeginIndex() == 0 && this.getEndIndex() == 0
         && this.getNextPageIdx() == 0 && this.getPageElementsCount() == 0
         && this.getPreviousPageIdx() == 0 && this.getTotalElementsCount() == 0;
   }
   
   public static void createEmptyPageDataModel(
      ItemListPageDataModel objToSet
   )
   {
      objToSet.setBeginIndex(0);
      objToSet.setEndIndex(0);
      objToSet.setCanGoBack(false);
      objToSet.setHasMoreElement(false);
      objToSet.setNextPageIdx(0);
      objToSet.setPageElementsCount(0);
      objToSet.setPageIdx(0);
      objToSet.setPreviousPageIdx(0);
      objToSet.setTotalElementsCount(0);
   }
   
   public static void setCurrentPageModel(
      ItemListPageDataModel objToSet,
      int currPageIdx,
      int elemsPerPage,
      int actualElemsCount,
      int totalElemsCount
   )
   {
      int slideBeginIndex = currPageIdx * elemsPerPage + 1;
      int slideEndIndex = slideBeginIndex + actualElemsCount - 1;
      int allElementsToCurrPage = currPageIdx * elemsPerPage + actualElemsCount;

      objToSet.setBeginIndex(slideBeginIndex);
      objToSet.setEndIndex(slideEndIndex);
      objToSet.setCanGoBack(currPageIdx > 0);
      objToSet.setHasMoreElement(allElementsToCurrPage < totalElemsCount);
      objToSet.setNextPageIdx(currPageIdx + 1);
      objToSet.setPageElementsCount(elemsPerPage);
      objToSet.setPageIdx(currPageIdx + 1);
      objToSet.setPreviousPageIdx(currPageIdx - 1);
      objToSet.setTotalElementsCount(totalElemsCount);
   }
   
   public static <T extends ItemListPageDataModel> void createPageModel(
      T pageModel, int actualElemCountInList, int totalElemCount,
      int pageIdx, int elemsPerPage
   )
   {
      if (totalElemCount == 0)
      {
         createEmptyPageDataModel(pageModel);
         return;
      }
      
      if (actualElemCountInList == 0)
      {
         createEmptyPageDataModel(pageModel);
         return;
      }

      setCurrentPageModel(
         pageModel,
         pageIdx,
         elemsPerPage,
         actualElemCountInList,
         totalElemCount
      );
   }
}

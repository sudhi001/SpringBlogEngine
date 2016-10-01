<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 
<tiles:insertDefinition name="defaultTemplate">
  <tiles:putAttribute name="cssContent">
     <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/custom/index.css">
  </tiles:putAttribute>

  <tiles:putAttribute name="body">
    <h3>${articleEditingTitle}</h3>
    <hr>
    
    <div class="row">
      <div class="col-md-12">
        <form:form class="form-horizontal" id="newPostForm" role="form" modelAttribute="newPostModel" method="POST" action="${pageContext.request.contextPath}/admin/blog/${actionName}">
          <form:hidden path="articleId" id="articleId"/>
          <div class="form-group">
            <div class="col-md-2"></div>
            <label class="col-md-3 control-label">
              <form:checkbox path="articlePublished"/> Publish this article
            </label>
          </div>
          <div class="form-group">
            <label class="col-md-2 control-label">Title</label>
            <div class="col-md-10">
              <form:input path="articleTitle" class="form-control"/>
            </div>
          </div>
          <div class="form-group">
            <label class="col-md-2 control-label">Article Type</label>
            <div class="col-md-3">
              <form:select class="col-md-2 form-control" path="articleType" items="${articleTypeList}" />
            </div>
          </div>
          <div class="form-group">
            <label class="col-md-2 control-label">Category</label>
            <div class="col-md-5">
              <form:input path="articleCategory" class="form-control"/>
            </div>
          </div>
          <div class="form-group">
            <label class="col-md-2 control-label">Keywords</label>
            <div class="col-md-5">
              <form:input path="articleKeywords" class="form-control"/>
            </div>
          </div>
          <div class="form-group">
            <label class="col-md-2 control-label">Summary</label>
            <div class="col-md-10">
              <form:textarea path="articleSummary" class="form-control" rows="10"/>
            </div>
          </div>
          <div class="form-group">
            <label class="col-md-2 control-label">Content</label>
            <div class="col-md-10">
              <p><button type="button" class="btn btn-default" onclick="handleClickResDlg('addTextResDlg')">Insert Text Resource</button> 
              <button type="button" class="btn btn-default" onclick="handleClickResDlg('addImgResDlg')">Insert Image Resource</button>
              <button type="button" class="btn btn-default" onclick="handleClickResDlg('addPhotosDlg')">Insert My Photos</button>              
              </p>
              <form:textarea path="articleContent" id="articleContent" class="form-control" rows="18"/>
            </div>
          </div>

          <div class="form-group">
            <div class="col-md-6"></div>
            <div class="col-md-6">
              <input class="btn btn-default" name="newPostPreview" type="submit" value="Preview"/>
              <input class="btn btn-primary" name="newPostSaveDraft" type="submit" value="Save/Update"/>
              <input class="btn btn-danger" type="reset" value="Clear"/>
            </div>
          </div>
        </form:form>
      </div>
    </div>
    
    <div id="addTextResDlg" class="modal fade">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">Insert Text-Based Resource</h4>
          </div>
          <input type="hidden" id="textResPageIdx" value=""/>
          <div id="txtResDlgBody" class="modal-body">
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" onclick="handleClickTextResLoadMore()">Load More</button>
            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
          </div>
        </div>
      </div>
    </div>
    
    <div id="addImgResDlg" class="modal fade">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">Insert Image Resource</h4>
          </div>
          <input type="hidden" id="imgResPageIdx" value=""/>
          <div id="imgResDlgBody" class="modal-body">
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" onclick="handleClickImageResLoadMore()">Load More</button>
            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
          </div>
        </div>
      </div>
    </div>
    
    <div id="addPhotosDlg" class="modal fade">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">Insert My Photos</h4>
          </div>
          <div class="modal-body">
             <form id="searchMyPhotosForm" class="form-inline">
                <div class="form-group">
                   <input type="text" class="form-control" id="searchPhotoWords" name="searchPhotoWords" placeholder="Enter Keywords">
   	            </div>
   	            <button class="btn btn-default" onclick="handleClickFindPhotos()">Search</button>
             </form>
             <hr>
             <div id="photosList" class="row">
                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                   <div class="thumbnail gallery-image">
                      <img style="img-responsive" src="${pageContext.request.contextPath}/secure/image-thumb/f5a4641a-aace-4878-81f8-cfe70a366656" width="100%">
                   </div>
                </div>
                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                   <div class="thumbnail gallery-image">
                      <img style="img-responsive" src="${pageContext.request.contextPath}/secure/image-thumb/f5a4641a-aace-4878-81f8-cfe70a366656" width="100%">
                   </div>
                </div>
                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                   <div class="thumbnail gallery-image">
                      <img style="img-responsive" src="${pageContext.request.contextPath}/secure/image-thumb/f5a4641a-aace-4878-81f8-cfe70a366656" width="100%">
                   </div>
                </div>
                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                   <div class="thumbnail gallery-image">
                      <img style="img-responsive" src="${pageContext.request.contextPath}/secure/image-thumb/f5a4641a-aace-4878-81f8-cfe70a366656" width="100%">
                   </div>
                </div>
                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                   <div class="thumbnail gallery-image">
                      <img style="img-responsive" src="${pageContext.request.contextPath}/secure/image-thumb/f5a4641a-aace-4878-81f8-cfe70a366656" width="100%">
                   </div>
                </div>
                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                   <div class="thumbnail gallery-image">
                      <img style="img-responsive" src="${pageContext.request.contextPath}/secure/image-thumb/f5a4641a-aace-4878-81f8-cfe70a366656" width="100%">
                   </div>
                </div>
             </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" onclick="handleClickFindPhoto()">Find</button>
            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
          </div>
        </div>
      </div>
    </div>
  </tiles:putAttribute>
  
  <tiles:putAttribute name="javascriptContent">
    <script src="${pageContext.request.contextPath}/assets/js/jquery.min-1.11.1.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js"></script>
    <script type="text/javascript">
       var handleClickResDlg = function (dlgid)
       {
          dlgid = "#" + dlgid;  
          $(dlgid).modal("show");
       };
       
       $('#addTextResDlg').on('show.bs.modal', function (e)
       {
         var existingTable = $("#addTextResDlg #txtResTable");
         if (existingTable !== null)
         {
           existingTable.remove();
         }
         var tableToAdd = createEmptyTable('txtResTable');
         
         $.ajax({
             type: "GET",
             url: "${pageContext.request.contextPath}/admin/resources/getTextResourcesList?pageIdx=0",
             xhrFields: {
                withCredentials: true
             },
             success: function(data) {
                creatMoreTableRows(tableToAdd, data, "handleClickAddTextResource");
                if (data.resourceList.length > 0)
                {
                   $("#addTextResDlg #textResPageIdx").val(data.pageIdx);
                }
             },
             error: function() {
             }
          });
         
          $("#addTextResDlg #txtResDlgBody").append(tableToAdd);
       });

      $('#addImgResDlg').on('show.bs.modal', function (e)
      {
          var existingTable = $("#addImgResDlg #imgResTable");
          if (existingTable !== null)
          {
             existingTable.remove();
          }
          var tableToAdd = createEmptyTable('imgResTable');
           
          $.ajax({
             type: "GET",
             url: "${pageContext.request.contextPath}/admin/resources/getImageResourcesList?pageIdx=0",
             xhrFields: {
                withCredentials: true
             },
             success: function(data) {
                creatMoreTableRows(tableToAdd, data, "handleClickAddImageResource");
                if (data.resourceList.length > 0)
                {
                   $("#addImgResDlg #imgResPageIdx").val(data.pageIdx);
                }
             },
             error: function() {
             }
          });
           
          $("#addImgResDlg #imgResDlgBody").append(tableToAdd);
       });
       
       var createEmptyTable = function (tableId)
       {
           var headerTr = $("<tr></tr>");
           headerTr.append(tableHeaderColumn('Type', 'col-md-3'));
           headerTr.append(tableHeaderColumn('Subject', 'col-md-7'));
           headerTr.append(tableHeaderColumn('Action', 'col-md-2'));       
           
           var tableToAdd = $("<table></table>", {
              'id': tableId,
              'class': 'table table-hover',
           }).append(headerTr);
           
           return tableToAdd;
       };
       
       var tableHeaderColumn = function (colName, colStyle)
       {
         var headerTh = $("<th></th>", {
             'class': colStyle,
             'text': colName
         });
         return headerTh;
       };
       
       var creatMoreTableRows = function (table, jsonData, clickFunc)
       {          
          prepareMoreTableRow(table, jsonData, clickFunc); //"handleClickAddTextResource");
       };
       
       var prepareMoreTableRow = function(table, jsonData, clickFunc) {
          if (jsonData.resourceList.length > 0)
          {
             for (var i = 0; i < jsonData.resourceList.length; i++)
             {
                var tblRow = $("<tr></tr>");
                var td = $("<td></td>").text(jsonData.resourceList[i].resourceSubType);
                tblRow.append(td);
                td = $("<td></td>").text(jsonData.resourceList[i].resourceName);
                tblRow.append(td);
                 
                var btn = $("<button></button>",{
                   'class': 'btn btn-default',
                   'onclick': clickFunc + "('" + jsonData.resourceList[i].resourceId + "')"
                }).text("Add");
                td = $("<td></td>").append(btn);
                tblRow.append(td);
                
                table.append(tblRow);
             }
          }
       };
       
       var handleClickAddTextResource = function (resId) {
          $.ajax({
             type: "GET",
             url: "${pageContext.request.contextPath}/admin/resources/getFormattedTextResource?resourceId=" + resId,
             xhrFields: {
                withCredentials: true
             },
             success: function(data) {
                var textArea = $("#articleContent");
                var currentText = textArea.val();
                textArea.val(currentText + data.formattedResourceValue);
             },
             error: function() {
             }
          });
       };
       
       var handleClickAddImageResource = function (resId) {
           $.ajax({
              type: "GET",
              url: "${pageContext.request.contextPath}/admin/resources/getFormattedImageResource?resourceId=" + resId,
              xhrFields: {
                 withCredentials: true
              },
              success: function(data) {
                 var textArea = $("#articleContent");
                 var currentText = textArea.val();
                 textArea.val(currentText + data.formattedResourceValue);
              },
              error: function() {
              }
           });
        };
       
       var handleClickTextResLoadMore = function () {
           var currPageIdx = $("#addTextResDlg #textResPageIdx").val();
           var nextPageIdx = parseInt(currPageIdx) + 1;
           var jsonUrl = "${pageContext.request.contextPath}/admin/resources/getTextResourcesList?pageIdx=" + nextPageIdx;
           
           $.ajax({
              type: "GET",
              url: jsonUrl,
              xhrFields: {
                 withCredentials: true
              },
              success: function(data) {
                 var table = $("#addTextResDlg #txtResTable");
                 creatMoreTableRows(table, data, "handleClickAddTextResource");
                 if (data.resourceList.length > 0)
                 {
                    $("#addTextResDlg #textResPageIdx").val(data.pageIdx);
                 }
              },
              error: function() {
              }
           });
        };

        var handleClickImageResLoadMore = function () {
            var currPageIdx = $("#addImgResDlg #imgResPageIdx").val();
            var nextPageIdx = parseInt(currPageIdx) + 1;
            var jsonUrl = "${pageContext.request.contextPath}/admin/resources/getImageResourcesList?pageIdx=" + nextPageIdx;
            
            $.ajax({
               type: "GET",
               url: jsonUrl,
               xhrFields: {
                  withCredentials: true
               },
               success: function(data) {
                  var table = $("#addImgResDlg #imgResTable");
                  creatMoreTableRows(table, data, "handleClickAddImageResource");
                  if (data.resourceList.length > 0)
                  {
                     $("#addImgResDlg #imgResPageIdx").val(data.pageIdx);
                  }
               },
               error: function() {
               }
            });
         };
         
         $('#addPhotosDlg').on('show.bs.modal', function (e) {
            $("#addPhotosDlg #searchMyPhotosForm #searchPhotoWords").val("");
            $("#addPhotosDlg #photosList").empty();
         });
         
         var handleClickFindPhotos = function () {
        	var searchWords = $("#addPhotosDlg #searchMyPhotosForm #searchPhotoWords").val();
        	if (searchWords != null && searchWords.length > 0)
            {
               var jsonUrl = "${pageContext.request.contextPath}/admin/images/findImages";
                
               $.ajax({
            	  type: "POST",
            	  url: jsonUrl,
                  xhrFields: {
                     withCredentials: true
                  },
                  data: "searchWords=" + searchWords,
                  success: function(data) {
                     alert(JSON.stringify(data));   
                       
                     var divPhotosList = $("#addPhotosDlg #photosList");
                     if (divPhotosList)
                     {
                        
                     }
                  },
                  error: function() {
                	 alert("Error");
                  }
               });
            };
         }
         
    </script>
  </tiles:putAttribute>
</tiles:insertDefinition>
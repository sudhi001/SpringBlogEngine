<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 
<tiles:insertDefinition name="defaultTemplate">
  <tiles:putAttribute name="cssContent">
     <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/custom/index.css">
  </tiles:putAttribute>

  <tiles:putAttribute name="body">
    <h3>${articleEditingTitle}</h3>
    <hr>
    
    <form:form class="form-horizontal" id="newPostForm" role="form" modelAttribute="newPostModel" method="POST" action="${pageContext.request.contextPath}/admin/blog/${actionName}">
      <form:hidden path="articleId"/>
      <div class="form-group">
        <div class="col-sm-2"></div>
        <label class="col-sm-2 control-label">
          <form:checkbox path="articlePublished"/> Publish this article
        </label>
      </div>
      <div class="form-group">
        <label class="col-sm-2 control-label">Title</label>
        <div class="col-md-8">
          <form:input path="articleTitle" class="form-control"/>
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-2 control-label">Article Type</label>
        <div class="col-md-2">
          <form:select class="col-sm-2 form-control" path="articleType" items="${articleTypeList}" />
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-2 control-label">Category</label>
        <div class="col-md-5">
          <form:input path="articleCategory" class="form-control"/>
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-2 control-label">Keywords</label>
        <div class="col-md-5">
          <form:input path="articleKeywords" class="form-control"/>
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-2 control-label">Summary</label>
        <div class="col-md-8">
          <form:textarea path="articleSummary" class="form-control" rows="10"/>
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-2 control-label">Content</label>
        <div class="col-md-8">
          <p><button type="button" class="btn btn-default" onclick="handleClickResDlg('addTextResDlg')">Insert Text Resource</button> 
          <button type="button" class="btn btn-default" onclick="handleClickResDlg('addImgResDlg')">Insert Image Resource</button>
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
          <div class="modal-body">
            
          </div>
          <div class="modal-footer">
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
                creatMoreTableRows(tableToAdd, data);
                if (data.resourceList.length > 0)
                {
                   $("#addTextResDlg #textResPageIdx").val(data.pageIdx);
                }
             },
             error: function() {
             }
          });
    	  
          $("#addTextResDlg #txtResDlgBody").append(tableToAdd);
          //$("#addTextResDlg #txtResTable tr:last").after("<tr><td>Quote</td><td>sample subject</td><td>test</td></tr>");
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
    	   prepareMoreTableRow(table, jsonData, "handleClickAddTextResource");
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

    </script>
  </tiles:putAttribute>
</tiles:insertDefinition>
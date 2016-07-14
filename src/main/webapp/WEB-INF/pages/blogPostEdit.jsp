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
      <div class="col-md-9">
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
      <div id="rightSide" class="col-md-3" style="display:hidden;">
        <div class="panel panel-default">
          <div class="panel-body">
            <div style="padding-top: 10px; padding-bottom: 10px; text-align: center">
              <img id="iconImg" src="https://placeholdit.imgix.net/~text?txtsize=9&txt=240%C3%97240&w=240&h=240" width="200" height="200">
            </div>
            <div style="text-align: center"><button id="articleIconBtn" class="btn btn-default" onclick="handleClickSetArticleIconBtn()">Set Article Icon</button></div>
          </div>
        </div>
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
    
    <div id="setArticleIconDlg" class="modal fade">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">Set Article Icon</h4>
          </div>
          <input type="hidden" id="iconsPageIdx" value=""/>
          <div class="modal-body">
            <div class="row">
              <div class="col-md-4">
                <div style="text-align: center;">
                  <div class="thumbnail">
                    <input type="hidden" id="selectedIconResId" value=""/>
                    <img id="iconImg" src="https://placeholdit.imgix.net/~text?txtsize=9&txt=240%C3%97240&w=240&h=240" width="200" height="200">
                  </div>
                  <button class="btn btn-default" onclick="handleClickSubmitArticleIcon()">Set Icon</button>
                  <button class="btn btn-default" onclick="handleClickRemoveArticleIcon()">Remove</button>
                </div>
              </div>
              <div class="col-md-8">
                <div style="padding-left: 15px; padding-right: 15px; max-height: 240px; overflow-y: scroll;">
                  <ul id="iconResourcesList" class="list-group row">
                  </ul>
                </div>
              </div>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" onclick="handleClickArticleIconsLoadMore()">Load More</button>
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
       $(function() {
          var articleIdField = $("#newPostForm #articleId");
          if (articleIdField.val() === undefined
             || articleIdField.val().length == 0)
          {
             $("#articleIconBtn").prop("disabled", true);
          }
          else
          {
            var articleId = articleIdField.val();
            displayArticleIcon(articleId);
          }
       });
    
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
         
         var handleClickSetArticleIconBtn = function () {
            $("#setArticleIconDlg").modal("show"); 
         };
         
         $('#setArticleIconDlg').on('show.bs.modal', function (e) {
            $('#setArticleIconDlg #iconResourcesList').empty();
            $('#setArticleIconDlg #iconsPageIdx').val("0");
            
            $("#setArticleIconDlg #iconImg").attr(
               "src", "https://placeholdit.imgix.net/~text?txtsize=9&txt=240%C3%97240&w=240&h=240");
            
            var articleIdField = $("#newPostForm #articleId");
            if (articleIdField.val() !== undefined
               && articleIdField.val().length > 0)
            {
               var articleId = articleIdField.val();
               displayArticleIcon(articleId);
            }
            
            loadIconResources(0);
         });
         
         var handleClickArticleIconsLoadMore = function() {
            var currPageIdx = $("#setArticleIconDlg #iconsPageIdx").val();
             var nextPageIdx = parseInt(currPageIdx) + 1;
            
             loadIconResources(nextPageIdx);
         };
         
         var loadIconResources = function (nextPageIdx) {
             var jsonUrl = "${pageContext.request.contextPath}/admin/resources/getIconResourcesList?pageIdx=" + nextPageIdx;
             
             $.ajax({
                 type: "GET",
                 url: jsonUrl,
                 xhrFields: {
                    withCredentials: true
                 },
                 success: function(data) {
                     createMoreIconResULItems(data);
                    if (data.iconResourcesList.length > 0)
                    {
                       $("#setArticleIconDlg #iconsPageIdx").val(data.pageIdx);
                    }
                 }
              });
         };
         
         var createMoreIconResULItems = function (jsonData)
         {
            if (jsonData.iconResourcesList.length > 0)
            {
               var items = [];
               for (var i = 0; i < jsonData.iconResourcesList.length; i++)
               {
                  var liItem = $("<li></li>",{
                      "class": "list-group-item col-md-6",
                      "style": "border-style: none !important;"
                  });
                  var iconLink = $("<a href='#'></a>");
                  iconLink.attr("onclick", "handleClickSetIconForArticle('" + jsonData.iconResourcesList[i].resourceId + "')");
                  var iconImg = $("<img>");
                  iconImg.attr("src", jsonData.iconResourcesList[i].iconUrl);
                   iconImg.attr("width", "100%");
                   iconImg.attr("height", "100%");
                   
                  iconLink.append(iconImg);
                  liItem.append(iconLink);
                  
                  items.push(liItem);
               }
               
               $("#setArticleIconDlg #iconResourcesList").append(items);
            }
         };
         
         var handleClickSetIconForArticle = function(resId){
            $("#setArticleIconDlg #selectedIconResId").val(resId);
            $("#setArticleIconDlg #iconImg").attr(
               "src", "${pageContext.request.contextPath}/public/imgresource/"+resId);
         };
         
         var handleClickSubmitArticleIcon = function() {
           var articleId = $("#newPostForm #articleId").val();
           var resId = $("#setArticleIconDlg #selectedIconResId").val();
           
           $.ajax({
                type: "POST",
                url: "${pageContext.request.contextPath}/admin/resources/setArticleIcon?articleId=" + articleId + "&resourceId=" + resId,
                xhrFields: {
                   withCredentials: true
                },
                success: function(data) {
                   $("#iconImg").attr("src", data);
                },
                error: function() {
                    $("#iconImg").attr("src", "https://placeholdit.imgix.net/~text?txtsize=9&txt=240%C3%97240&w=240&h=240");
                    $("#setArticleIconDlg #iconImg").attr("src", "https://placeholdit.imgix.net/~text?txtsize=9&txt=240%C3%97240&w=240&h=240");
                },
                statusCode: {
                   304: function() {
                      $("#iconImg").attr("src", "https://placeholdit.imgix.net/~text?txtsize=9&txt=240%C3%97240&w=240&h=240");
                       $("#setArticleIconDlg #iconImg").attr("src", "https://placeholdit.imgix.net/~text?txtsize=9&txt=240%C3%97240&w=240&h=240");
                   }
                }
             });
           
         };

         var handleClickRemoveArticleIcon = function() {
             var articleId = $("#newPostForm #articleId").val();

             $.ajax({
                 type: "DELETE",
                 url: "${pageContext.request.contextPath}/admin/resources/removeArticleIcon?articleId=" + articleId,
                 xhrFields: {
                    withCredentials: true
                 },
                 success: function(data) {
                    $("#iconImg").attr("src", "https://placeholdit.imgix.net/~text?txtsize=9&txt=240%C3%97240&w=240&h=240");
                    
                    $("#setArticleIconDlg #iconImg").attr("src", "https://placeholdit.imgix.net/~text?txtsize=9&txt=240%C3%97240&w=240&h=240");
                 },
                 error: function() {
                 }
              });
          };
          
          var displayArticleIcon = function (articleId) {
              $.ajax({
                  type: "GET",
                  url: "${pageContext.request.contextPath}/admin/resources/getArticleIcon?articleId=" + articleId,
                  xhrFields: {
                     withCredentials: true
                  },
                  success: function(data) {
                     var iconImg = $("#iconImg");
                     iconImg.attr("src", data);
                     
                     iconImg = $("#setArticleIconDlg #iconImg");
                     iconImg.attr("src", data);
                  },
                  error: function() {
                  }
               });
          };

    </script>
  </tiles:putAttribute>
</tiles:insertDefinition>
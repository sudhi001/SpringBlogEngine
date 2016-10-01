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
         url: "/admin/resources/getTextResourcesList?pageIdx=0",
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
         url: "/admin/resources/getImageResourcesList?pageIdx=0",
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
         url: "/admin/resources/getFormattedTextResource?resourceId=" + resId,
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
          url: "/admin/resources/getFormattedImageResource?resourceId=" + resId,
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
       var jsonUrl = "/admin/resources/getTextResourcesList?pageIdx=" + nextPageIdx;
       
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
        var jsonUrl = "/admin/resources/getImageResourcesList?pageIdx=" + nextPageIdx;
        
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
           var jsonUrl = "/admin/images/findImages";
            
           $.ajax({
        	  type: "POST",
        	  url: jsonUrl,
              xhrFields: {
                 withCredentials: true
              },
              async:false,
              data: "searchWords=" + searchWords,
              success: function(data) {
                 //alert(JSON.stringify(data));   
                   
                 var divPhotosList = $("#addPhotosDlg #photosList");
                 if (divPhotosList && data && data.length > 0)
                 {
                    for (var i = 0; i < data.length; i++)
                    {
                       var img = $("<img>", {
                          "src": "/secure/image-thumb/" + data[i].imageId,
                          "width": "100%",
                          "style": "img-responsive"
                       });
                       var imgLink = $("<a></a>", {
                          "href": "#",
                          "onclick": "addPhotoToBlog('" + data[i].imageId + "', " + data[i].imageSizeX + ", " + data[i].imageSizeY + ", " + data[i].widthToHeightRatio + ")"
                       }).append(img);
                       var imgInnerDiv = $("<div></div>",{
                           "class": "thumbnail gallery-image",
                       }).append(imgLink);
                       var imgOuterDiv = $("<div></div>",{
                           'class': 'col-xs-12 col-sm-6 col-md-4 col-lg-4',
                       }).append(imgInnerDiv);
                       
                       $("#addPhotosDlg #photosList").append(imgInnerDiv);
                    }
                 }
              },
              error: function() {
              }
           });
        };
     }
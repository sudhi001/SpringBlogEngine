<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<tiles:insertDefinition name="defaultTemplate">
  <tiles:putAttribute name="cssContent">
     <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/custom/index.css">
  </tiles:putAttribute>

  <tiles:putAttribute name="body">
    <h3>All My Posts</h3>
    <hr>
    
    <div class="post-list">
      <div class="row">
        <div class="col-md-3">
          <c:choose>
            <c:when test="${articleListPageModel.totalElementsCount == 0}">
              <h4><span class="label label-info">${articleListPageModel.totalElementsCount} articles available</span></h4>
            </c:when>
            <c:otherwise>
              <h4><span class="label label-info">${articleListPageModel.totalElementsCount} articles and counting ...</span></h4>
            </c:otherwise>            
          </c:choose>
        </div>
        <div class="col-md-9 text-right">
          <p>
            <a href="${pageContext.request.contextPath}/admin/blog/newPost" class="btn btn-default">New Post</a>
          </p>
        </div>
      </div>

      <c:choose>
      <c:when test="${!articleListPageModel.isDataModelEmpty()}">
      
      <c:forEach items="${articleListPageModel.getListItems()}" var="postInfo">
         <div class="post-list">
            <div class="panel panel-default">
               <div class="panel-body">
                  <div class="row">
                     <div class="col-md-12">
                        <h4 class="media-heading">
                           <a href="${pageContext.request.contextPath}/admin/blog/previewPost/${postInfo.articleId}"><strong>${postInfo.articleTitle}</strong></a>
                        </h4>
                        <hr class="postlist-hr">
                        <div class="postlist-postsummary">
                           <div class="row">
                              <div class="col-xs-4 col-sm-2 col-md-2 col-lg-3 text-center">
                                 <div class="thumbnail gallery-image">
                                    <img src="${pageContext.request.contextPath}/assets/imgs/default-user.jpg" width="100%">
                                 </div>
                              </div>
                              <div class="col-xs-12 col-sm-10 col-md-10 col-lg-9">
                                 <c:if test="${postInfo.articleSummary != null && postInfo.articleSummary.length() > 0}">
                                 <p>
                                 ${postInfo.articleSummary}
                                 </p>
                                 </c:if>
                              </div>
                           </div>
                        </div>
                 
                        <div class="thumbnail postlist-bottom-sidebar">
                           <div class="row">
                              <div class="col-md-2 padding-updown">
                                 <c:choose>
                                    <c:when test="${postInfo.articleType.equals('post')}">
                                       <span class="glyphicon glyphicon-book"></span> Post
                                    </c:when>
                                    <c:otherwise>
                                       <span class="glyphicon glyphicon-file"></span> Page
                                    </c:otherwise>
                                 </c:choose>
                              </div>
                              <div class="col-md-3 padding-updown">
                                 <span class="glyphicon glyphicon-calendar"></span>
                                 ${postInfo.getArticleUpdateDateString()}
                              </div>
                              <div class="col-md-3 padding-updown">
                                 <span class="glyphicon glyphicon-user"></span>
                                 <a href="${pageContext.request.contextPath}/userProfile/${postInfo.authorId}">
                                    <c:choose>
                                       <c:when test="${postInfo.authorName != null && postInfo.authorName.length() > 0}">
                                       ${postInfo.authorName}
                                       </c:when>
                                       <c:otherwise>
                                       ${postInfo.authorUserName}
                                       </c:otherwise>
                                    </c:choose>
                                 </a>
                              </div>
                              <div class="col-md-4 text-right">
                                 <c:if test="${postInfo.articleType.equals('post')}">
                                 <button class="btn btn-success" onclick="clickEditArticleIconBtn('${postInfo.articleId}', '${pageContext.request.contextPath}')" data-toggle="tooltip" data-placement="top" title="Set/Edit Article Icon"><span class="glyphicon glyphicon-picture"></span></button>
                                 </c:if>
                                 <a href="${pageContext.request.contextPath}/admin/blog/editPost/${postInfo.articleId}" class="btn btn-primary" data-toggle="tooltip" data-placement="top" title="Edit Article"><span class="glyphicon glyphicon-pencil"></span></a>
                                 <button class="btn btn-danger" onclick="deletePostJson('${postInfo.articleId}')" data-toggle="tooltip" data-placement="top" title="Delete Article"><span class="glyphicon glyphicon-floppy-remove"></span></button>
                                 <c:choose>
                                    <c:when test="${!postInfo.articlePublished}">
                                       <button class="btn btn-success" onclick="publishPostJson('${postInfo.articleId}', 'true')" data-toggle="tooltip" data-placement="top" title="Publish"><span class="glyphicon glyphicon-file"></span></button>
                                    </c:when>
                                    <c:otherwise>
                                       <button class="btn btn-default" onclick="publishPostJson('${postInfo.articleId}', 'false')"  data-toggle="tooltip" data-placement="top" title="Unpublish/Draft"><span class="glyphicon glyphicon-file"></span></button>
                                    </c:otherwise>
                                 </c:choose>
                                 <button class="btn btn-default" onclick="setPermaLinkBtnClick('${postInfo.articleId}', '${postInfo.authorId}', '${postInfo.articleTitle}')"  data-toggle="tooltip" data-placement="top" title="Set/Edit PermaLink"><span class="glyphicon glyphicon-link"></span></button>
                              </div>
                           </div>
                        </div>
                     </div>
                  </div>
               </div>
            </div>
         </div>
      </c:forEach>
      

      <div class="row">
        <div class="md-col-12 text-right">
          <nav>
            <ul class="pagination">
              <c:if test="${articleListPageModel.canGoBack}">
              <li>
                <a href="${pageContext.request.contextPath}/admin/blog/allMyPosts?pageIdx=${articleListPageModel.previousPageIdx}" aria-label="Previous">
                  <span aria-hidden="true">&laquo;</span>
                </a>
              </li>
              </c:if>
              <c:if test="${articleListPageModel.hasMoreElement}">
              <li>
                <a href="${pageContext.request.contextPath}/admin/blog/allMyPosts?pageIdx=${articleListPageModel.nextPageIdx}" aria-label="Next">
                  <span aria-hidden="true">&raquo;</span>
                </a>
              </li>
              </c:if>
            </ul>
          </nav>
        </div>
      </div>

      </c:when>
      <c:otherwise>
        <p>No posts found</p>
      </c:otherwise>
    </c:choose>
    </div>
    
    <div class="modal fade" id="permaLinkDlg">
      <div class="modal-dialog">
        <div class="modal-content">
          <form id="permaLinkForm" method="post" action="${pageContext.request.contextPath}/admin/blog/setPermaLink">
            <input type="hidden" id="permaLinkArticleId" name="permaLinkArticleId" value="" />
            <input type="hidden" id="permaLinkAuthorId" name="permaLinkAuthorId" value="" />
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">Set Perma Link</h4>
          </div>
          <div class="modal-body">
            <div class="form-group">
               <label class="col-md-3">Article</label>
               <p class="col-md-9" id="articleTitle"></p>
            </div>
            <div class="form-group">
               <label for="permaLinkPath" class="col-md-3">Perma Link</label>
               <input type="text" class="col-md-9" id="permaLinkPath" name="permaLinkPath" placeholder=""/>
            </div>
            <p>
              <input type="checkbox" id="pageReplacement" name="pageReplacement"> Use as page Replacement
            </p>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-danger" onclick="deletePermaLinkClick()">Remove Link</button>
            <button type="button" class="btn btn-primary" onclick="submitPermaLinkClick()">Change Link</button>
            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
          </div>
          </form>
        </div>
      </div>
    </div>
    
    <div class="modal fade" id="addArticleIconDlg">
      <div class="modal-dialog">
        <div class="modal-content">
          
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">Edit Article Icon</h4>
          </div>
          <div class="modal-body">
            <div id="addArticleIconForm" style="height: 250px; max-height: 250px; overflow-y: scroll;">
              <input type="hidden" id="addIconArticleId" name="addIconArticleId" value="" />
              <div id="addArticleIconGallery" class="row">
              </div>
            </div>
            <div class="row margin-updown" id="addArticleIconError" style="display: none;">
               <div class="col-xs-12">
                  <div class="warning-block" id="addArticleIconErrorMsg">
                     test test
                  </div>
               </div>
            </div>
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
    <script src="${pageContext.request.contextPath}/assets/custom/js/addArticleIcon.js"></script>
    <script type="text/javascript">       
        var deletePostJson = function (articleId)
        {
           $.ajax({
              type: "DELETE",
              url: "${pageContext.request.contextPath}/admin/blog/deletePost?articleId=" + articleId,
              xhrFields: {
                 withCredentials: true
              },
              success: function(data) {
                 location.reload(true);
              },
              error: function() {
              }
           });
        };
        
        var publishPostJson = function (articleId, toPublish)
        {
           $.ajax({
              type: "PUT",
              url: "${pageContext.request.contextPath}/admin/blog/publishPost?articleId=" + articleId + "&" + "toPublish=" + toPublish,
              xhrFields: {
                 withCredentials: true
              },
              success: function(data) {
                 location.reload(true);
              },
              error: function() {
              }
           });
        };
        
        var setPermaLinkBtnClick = function (articleId, authorId, articleTitle)
        {
           $("#permaLinkDlg #permaLinkForm #permaLinkArticleId").val(articleId);
           $("#permaLinkDlg #permaLinkForm #permaLinkAuthorId").val(authorId);
           $("#permaLinkDlg #permaLinkForm #articleTitle").html(articleTitle);
           $("#permaLinkDlg #permaLinkForm #permaLinkPath").val("");
           $("#permaLinkDlg #permaLinkForm #pageReplacement").prop("checked", false);
           
           $("#permaLinkDlg").modal("show");
        };
        
        $('#permaLinkDlg').on('shown.bs.modal', function () {
        	var articleId = $("#permaLinkDlg #permaLinkForm #permaLinkArticleId").val();
        	$.get("${pageContext.request.contextPath}/admin/blog/getPermaLink?articleId=" + articleId,
               function (data) {
                $("#permaLinkDlg #permaLinkForm #permaLinkPath").val(data.permaLinkPath);
                if (data.pageReplacement)
                {
                   $("#permaLinkDlg #permaLinkForm #pageReplacement").prop("checked", true);
                }
            });
        });
        
        var submitPermaLinkClick = function ()
        {
           var pageReplacementVal = false;
           if ($("#permaLinkDlg #permaLinkForm #pageReplacement").is(":checked"))
           {
               pageReplacementVal = true;
           }
           
           var postData = {
              articleId: $("#permaLinkDlg #permaLinkForm #permaLinkArticleId").val(),
              authorId: $("#permaLinkDlg #permaLinkForm #permaLinkAuthorId").val(),
              permaLinkPath: $("#permaLinkDlg #permaLinkForm #permaLinkPath").val(),
              pageReplacement: pageReplacementVal 
           };

           $.post("${pageContext.request.contextPath}/admin/blog/setPermaLink",
        	  JSON.stringify(postData),
              function(data){
                 $("#permaLinkDlg").modal("hide");
              }).always(function (){
                 $("#permaLinkDlg").modal("hide");
              });
        };
          
        var deletePermaLinkClick = function ()
        {
           var articleId = $("#permaLinkDlg #permaLinkForm #permaLinkArticleId").val();
           var deletePermaLinkUrl
              = "${pageContext.request.contextPath}/admin/blog/deletePermaLink?articleId=" +articleId;
        	 
           $.ajax({
              url: deletePermaLinkUrl,
              type: "DELETE",
              success: function (result)
              {
                 $("#permaLinkDlg").modal("hide");
              }
           });
        };
        
    </script>
  </tiles:putAttribute>
</tiles:insertDefinition>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tiles:insertDefinition name="defaultTemplate">
  <tiles:putAttribute name="cssContent">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/jasny/css/jasny-bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/custom/index.css">
  </tiles:putAttribute>

  <tiles:putAttribute name="body">
    <h3>Recent Comments from Others</h3>
    <hr>

    <div class="row">
       <div class="col-md-8">
          <span class="label label-info">You have ${unapprovedArticleComments.getTotalElementsCount()} unapproved comments.</span>
       </div>
       <div class="col-md-4">
       </div>
    </div>
    <br/>
    <c:choose>
      <c:when test="${!unapprovedArticleComments.isDataModelEmpty()}">
      <table class="table table-hover">
        <tr>
          <th class="col-md-1"></th>
          <th class="col-md-4">Comment Title</th>
          <th class="col-md-3">Commenter</th>
          <th class="col-md-2">Last Modified</th>
          <th class="col-md-2">Actions</th>
        </tr>
        <c:forEach items="${unapprovedArticleComments.getCommentsList()}" var="commentItem">
          <tr>
            <td>
               <span class="glyphicon glyphicon-comment"></span>
            </td>
            <td>
               <a href="#" onclick="displayCommentDetail('${commentItem.articleId}', '${commentItem.commentId}', '${pageContext.request.contextPath}')">${commentItem.commentTitle}</a>
            </td>
            <td>
               <c:choose>
                  <c:when test="${commentItem.commentUserFullName != null && commentItem.commentUserFullName.length() > 0}">
                     ${commentItem.commentUserFullName}
                  </c:when>
                  <c:when test="${commentItem.commentUserName != null && commentItem.commentUserName.length() > 0}">
                     ${commentItem.commentUserName}
                  </c:when>
                  <c:when test="${commentItem.commenterName != null && commentItem.commenterName.length() > 0}">
                     ${commentItem.commenterName}
                  </c:when>
                  <c:otherwise>
                     Anonymous
                  </c:otherwise>
               </c:choose>
            </td>
            <td>
               ${commentItem.getCommentUpdateDateString()}
            </td>
            <td>
               <button class="btn btn-sm btn-success" onclick="approveComment()" data-toggle="tooltip" data-placement="top" title="Approve Comment"><span class="glyphicon glyphicon-ok-circle"></span></button>
               <button class="btn btn-sm btn-danger" onclick="approveComment()" data-toggle="tooltip" data-placement="top" title="Delete Comment"><span class="glyphicon glyphicon-remove-circle"></span></button>
            </td>
          </tr>
        </c:forEach>
      </table>

      </c:when>
      <c:otherwise>
        <div class="row">
          <div class="col-md-12">
            <p>No unapproved comments available.</p>
          </div>
        </div>
      </c:otherwise>
    </c:choose>
    
    <div id="commentDetailDlg" class="modal fade">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">Comment Detail</h4>
          </div>
          <div class="modal-body">
            <strong id="commentTitle">yak yak</strong>
            <p id="commentContent">yak yak</p>
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
    <script src="${pageContext.request.contextPath}/assets/custom/js/userComments.js"></script>
    
    <script type="text/javascript">
    </script>
  </tiles:putAttribute>
</tiles:insertDefinition>

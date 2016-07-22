<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
 
<tiles:insertDefinition name="defaultTemplate">
  <tiles:putAttribute name="cssContent">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/custom/index.css">
  </tiles:putAttribute>

  <tiles:putAttribute name="body">
     <div class="content-box">
        <p>This is the Index Page.</p>
     </div>
     
     <!-- 
     <div class="content-box">
       <div class="box-header with-border">
         <div class="user-block">
           <img class="img-circle" src="http://bootdey.com/img/Content/avatar/avatar1.png" alt="User Image">
           <span class="username"><a href="#">John Breakgrow jr.</a></span>
           <span class="description">Shared publicly - 7:30 PM Today</span>
         </div>
         <div class="box-tools">
           <button type="button" class="btn btn-box-tool" data-toggle="tooltip" title="" data-original-title="Mark as read">
           <i class="fa fa-circle-o"></i></button>
           <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
           <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
         </div>
       </div>

       <div class="box-body" style="display: block;">
         <img class="img-responsive pad" style="text-align: center;" src="resources/images/Hydrangeas.jpg" alt="Photo">
         <p>I took this photo this morning. What do you guys think?</p>
         <button type="button" class="btn btn-default btn-xs"><i class="fa fa-share"></i> Share</button>
         <button type="button" class="btn btn-default btn-xs"><i class="fa fa-thumbs-o-up"></i> Like</button>
         <span class="pull-right text-muted">127 likes - 3 comments</span>
       </div>

       <div class="box-footer box-comments" style="display: block;">
         <div class="box-comment">
           <img class="img-circle img-sm" src="http://bootdey.com/img/Content/avatar/avatar2.png" alt="User Image">
           <div class="comment-text">
             <span class="username">
               Maria Gonzales
               <span class="text-muted pull-right">8:03 PM Today</span>
             </span>
             It is a long established fact that a reader will be distracted
             by the readable content of a page when looking at its layout.
           </div>
         </div>

         <div class="box-comment">
           <img class="img-circle img-sm" src="http://bootdey.com/img/Content/avatar/avatar3.png" alt="User Image">
           <div class="comment-text">
             <span class="username">
               Luna Stark
               <span class="text-muted pull-right">8:03 PM Today</span>
             </span>
             It is a long established fact that a reader will be distracted
             by the readable content of a page when looking at its layout.
           </div>
         </div>
       </div>
       <div class="box-footer" style="display: block;">
         <form action="#" method="post">
           <img class="img-responsive img-circle img-sm" src="http://bootdey.com/img/Content/avatar/avatar1.png" alt="Alt Text">
           <div class="img-push">
             <input type="text" class="form-control input-sm" placeholder="Press enter to post comment">
           </div>
         </form>
       </div>
     </div>   -->
  </tiles:putAttribute>
  
  <tiles:putAttribute name="javascriptContent">
    <script src="assets/js/jquery.min-1.11.1.js"></script>
    <script src="assets/js/bootstrap.min.js"></script>
  </tiles:putAttribute>
</tiles:insertDefinition>
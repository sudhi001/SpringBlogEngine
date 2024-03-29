<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %> 

    <nav class="navbar navbar-default navbar-override" role="navigation">
      <div class="container-fluid">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <span class="navbar-brand">${pageMetadata.siteTitle}</span>
        </div>

        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
          <ul class="nav navbar-nav navbar-right">
            <li><a href="${pageContext.request.contextPath}/index">Home</a></li>
            <li><a href="${pageContext.request.contextPath}/public/updates/allUpdates/0">Updates</a></li>
            <li><a href="${pageContext.request.contextPath}/blog/allPosts/0">Blog</a></li>
            <li><a href="${pageContext.request.contextPath}/galleries/0">Galleries</a></li>
            <sec:authorize ifAnyGranted="ROLE_ANONYMOUS">
              <li><a href="${pageContext.request.contextPath}/signin">Log in</a></li>
            </sec:authorize>
            <sec:authorize ifAnyGranted="ROLE_ADMIN,ROLE_STAFF">
              <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">Admin<span class="caret"></span></a>
                <ul class="dropdown-menu" role="menu">
                  <li><a href="${pageContext.request.contextPath}/admin/viewUserProfile">My User Profile</a></li>
                  <li><a href="${pageContext.request.contextPath}/admin/blog/allMyPosts">My Posts</a></li>
                  <!-- <li><a href="${pageContext.request.contextPath}/admin/images/allMyImages">My Images</a></li> -->
                  <li><a href="${pageContext.request.contextPath}/admin/galleries/allMyGalleries/0">My Galleries</a></li>
                  <li><a href="${pageContext.request.contextPath}/admin/updates/allMyUpdates/0">My Updates</a></li>
                  <li><a href="${pageContext.request.contextPath}/admin/resources/allMyResources/0">My Resources</a></li>
                  <li><a href="${pageContext.request.contextPath}/admin/comments/0">Guest Comments</a></li>
                </ul>
              </li>
            </sec:authorize>
            <sec:authorize ifNotGranted="ROLE_ANONYMOUS">
              <li><a href="${pageContext.request.contextPath}/j_spring_security_logout">Log out</a></li>
            </sec:authorize>
          </ul>
        </div>
      </div>
    </nav>

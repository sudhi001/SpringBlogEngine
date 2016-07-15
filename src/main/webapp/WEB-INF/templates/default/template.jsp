<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>${pageMetadata.pageTitle}</title>
    
    <link href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/jasny/css/jasny-bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/lightbox/css/magnific-popup.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/custom/index.css">
</head>
<body>
    <div class="page">
      <tiles:insertAttribute name="header" />
      
      <div class="jumbotron-box">
	      <div class="container">
	          <div class="row">
	            <div class="col-xs-12">
	               <h1>Hello, world!</h1>
	               <p>This is a test banner subtitle</p>
	            </div>
	         </div>
	      </div>
      </div>
      
      <div class="container page-content">
          <tiles:insertAttribute name="body" />
      </div>
      <tiles:insertAttribute name="footer" />
      <tiles:insertAttribute name="javascriptContent" />
    </div>
</body>
</html>
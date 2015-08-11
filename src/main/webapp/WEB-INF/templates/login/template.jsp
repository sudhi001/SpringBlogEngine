<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html>
    <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>${pageMetadata.pageTitle}</title>
    
    <link href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/css/custom/signin.css" rel="stylesheet">
</head>
<body>
  
  <tiles:insertAttribute name="body" />
  <tiles:insertAttribute name="javascriptContent" />

</body>
</html>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tiles:insertDefinition name="defaultTemplate">
  <tiles:putAttribute name="cssContent">
     <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/custom/index.css">
  </tiles:putAttribute>

  <tiles:putAttribute name="body">
    <h3>${articleModel.articleTitle}</h3>
    <hr>
    <p>By ${articleModel.authorName}, Created on ${articleModel.getArticleCreateDateString()}; Last modified on ${articleModel.getArticleUpdateDateString()}</p>
    <br/>
    <c:out value="${articleModel.articleContent}" escapeXml = "false"/>
    <br/>
    <p>Keywords: ${articleModel.articleKeywords}</p>
    <p>Category: ${articleModel.articleCategory}</p>
    
  </tiles:putAttribute>
  
  <tiles:putAttribute name="javascriptContent">
    <script src="${pageContext.request.contextPath}/assets/js/jquery.min-1.11.1.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js"></script>
  </tiles:putAttribute>
</tiles:insertDefinition>
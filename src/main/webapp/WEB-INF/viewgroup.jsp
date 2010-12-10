<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<html>
  <head><title>View Groups</title></head>
  <body>
  <h2>Groups</h2>
    <c:if test="${empty groups}">
       <div><b>No Schedule Groups Found!</b></div>
    </c:if>
    <c:forEach var="group" items="${groups}">
        <div>
            <html:link action="/viewsetpoint" target="setpoint">
                <html:param name="grouppath" value="${group.path}"/>
                ${group.name}
            </html:link>
        </div>
    </c:forEach>
  </body>
</html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<html>
  <head><title>Setpoint</title></head>
  <body>
  <html:form action="/changesetpoint">
      <h1>${setpoint.groupName}</h1>
      <table>
          <tr>
              <td>Occupied</td>
              <td>Heating:</td>
              <td><html:text property="occHeat"/></td>
              <td style="padding-left:2em;">Cooling:</td>
              <td><html:text property="occCool"/></td>
          </tr>
          <tr>
              <td>Unoccupied</td>
              <td>Heating:</td>
              <td><html:text property="unoccHeat"/></td>
              <td style="padding-left:2em;">Cooling:</td>
              <td><html:text property="unoccCool"/></td>
          </tr>
      </table>
      <html:hidden property="groupPath"/>
      <html:submit>Apply</html:submit>
      <html:messages id="errors">
          <div>${errors}</div>
      </html:messages>
  </html:form>
  </body>
</html>
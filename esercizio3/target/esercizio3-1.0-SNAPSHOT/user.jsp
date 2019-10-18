<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <body>

    <%
        String isSubmit = request.getParameter("isSubmit") != null ? request.getParameter("isSubmit") : "0";
        if (isSubmit.equalsIgnoreCase("0")) { %>
    <form name="UserForm" method="post" action="user.jsp">
        Nome:<input type="text" name="nome"/> <br />
        Cognome:<input type="text" name="cognome"/><br />
        Eta:    <input type="text" name="eta"/><br />
        <input value="1" type="hidden" name="isSubmit">
        <input type="submit" value="ok"><br />
    </form>
        <% } else{ %>
            <table>
                <tr>
                    <td>
                        Nome
                    </td>
                    <td>
                        <%=request.getParameter("nome")%>
                    </td>
                </tr>
                <tr>
                    <td>
                        Cognome
                    </td>
                    <td>
                        <% out.println(request.getParameter("cognome")); %>
                    </td>
                </tr>
                <tr>
                    <td>
                        Eta
                    </td>
                    <td>
                        <% out.println(request.getParameter("eta")); %>
                    </td>
                </tr>
                <tr>
                    <td>
                        Note sono una pippa al sugo
                    </td>
                </tr>
            </table>

        <% } %>
    </body>
</html>
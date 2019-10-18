<%@ page import="javax.xml.soap.*" %>
<%@ page import="sun.awt.geom.AreaOp" %>
<%@ page import="javax.xml.transform.TransformerFactory" %>
<%@ page import="javax.xml.transform.stream.StreamResult" %>
<%@ page import="javax.xml.transform.dom.DOMSource" %>
<%@ page import="java.net.HttpURLConnection" %>
<%@ page import="java.net.URL" %>
<%@ page import="java.io.*" %>
<%@ page import="org.w3c.dom.Document" %>
<%@ page import="javax.xml.parsers.DocumentBuilderFactory" %>
<%@ page import="javax.xml.namespace.QName" %>
<%@ page import="javax.xml.transform.Transformer" %>
<%@ page import="javax.xml.transform.OutputKeys" %>
<%@ page import="javax.xml.ws.Service" %>
<%@ page import="org.apache.cxf.binding.soap.wsdl.extensions.SoapBody" %>
<%@ page import="org.w3c.dom.NodeList" %>
<%@ page import="org.w3c.dom.Node" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="com.ags.example.model.Versione" %>
<%@ page import="com.ags.example.service.MotornetService" %>
<%@ page import="com.ags.example.model.SearchTargaResponse" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <body>
        <%
            String isSubmit = request.getParameter("isSubmit") != null ? request.getParameter("isSubmit") : "0";
            MotornetService service= new MotornetService();
            if (isSubmit.equalsIgnoreCase("0")) {

             %>
            <form name="targaform" method="post" action="veicolo.jsp">
                Targa:<input type="text" name="targa"/> <br />
                <input value="1" type="hidden" name="isSubmit">
                <input type="submit" value="ok"><br />
            </form>
        <%}else if (isSubmit.equalsIgnoreCase("1")){
            try{
                String token=service.loginAction();
                String targa=request.getParameter("targa");
                SearchTargaResponse targaResult= new SearchTargaResponse();
                targaResult = service.searchTarga(token,targa);
                %>
                <form name="valutazioneform" method="post" action="veicolo.jsp">
                    <input type="text" name="fMarca"value="<%=targaResult.getMarca()%>" readonly></br>
                    <input type="text" name="fModello" value="<%=targaResult.getModello()%>" readonly></br>
                    <select>
                        <%
                        for(Versione versione : targaResult.getVersioni()){
                        %>
                            <% System.out.println(versione.getCodiceEurotax()+"---"+versione.getVersione()+"---"+versione.getCodiceMotornet());%>
                            <option value="<%=versione.getCodiceEurotax()%>"><%=versione.getVersione()%></option>
                        <%}%>
                    </select>
                    </br>
                    <input type="text" name="fDdimm" value="<%=targaResult.getDdimm()%>" readonly></br>
                    <input value="2" type="hidden" name="isSubmit">
                    <input type="submit" value="ok"><br />
                </form>
                <%
                }catch (Exception e){
                System.out.println("Errore");
                e.printStackTrace();
                }
        }
       else {
        %> <h1>GET-Valutazione</h1>
        <%}%>
    </body>
</html>

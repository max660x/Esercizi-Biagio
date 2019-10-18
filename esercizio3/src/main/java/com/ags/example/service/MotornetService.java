package com.ags.example.service;
import javax.xml.soap.*;
import com.ags.example.model.GetValutazioneResponse;
import com.ags.example.model.SearchTargaResponse;
import com.ags.example.model.Versione;
import sun.awt.geom.AreaOp;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.dom.DOMSource;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.*;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.namespace.QName;
import javax.xml.transform.Transformer;
import javax.xml.transform.OutputKeys;
import javax.xml.ws.Service;
import org.apache.cxf.binding.soap.wsdl.extensions.SoapBody;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import java.util.ArrayList;
import java.util.List;

public class MotornetService {
    private static final String username = "rcbnq4497";
    private static final String password=username;
    private static  URL url;
    static {
        try {
            url = new URL("https://webservice.motornet.it/server.php");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
    private HttpURLConnection connection =null;
    private OutputStream os = null;

    //login

    public String loginAction () throws Exception {
        SOAPFactory loginSOAPFactory = SOAPFactory.newInstance();
        Name loginBodyName =  loginSOAPFactory.createName("login","urn","urn:motornetwebservice");
        SOAPMessage loginMessage = MessageFactory.newInstance().createMessage();
        SOAPEnvelope loginEnvelope = loginMessage.getSOAPPart().getEnvelope();
        SOAPBody loginBody = loginEnvelope.getBody();
        SOAPBodyElement loginBodyElement = loginBody.addBodyElement(loginBodyName);
        loginBodyElement.addChildElement("username").addTextNode(username);
        loginBodyElement.addChildElement("password").addTextNode(password);
        connection= (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type","text/xml; charset=utf-8");
        connection.setDoOutput(true);
        os = connection.getOutputStream();
        loginMessage.writeTo(os);
        Document loginResult = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(connection.getInputStream());
        String token=loginResult.getElementsByTagName("token").item(0).getFirstChild().getNodeValue();
        os.flush();
        return token;
    }
    //searchtarga
    public SearchTargaResponse searchTarga(String token, String targa)throws Exception{
        List<Versione> versioniResult = null;
        SearchTargaResponse response = new SearchTargaResponse();
        SOAPFactory targaSOAPFactory = SOAPFactory.newInstance();
        Name targaBodyName = targaSOAPFactory.createName("searchTarga","urn","urn:motornetwebservice");
        SOAPMessage targaMessage = MessageFactory.newInstance().createMessage();
        SOAPEnvelope targaEnvelope = targaMessage.getSOAPPart().getEnvelope();
        SOAPBody targaBody = targaEnvelope.getBody();
        SOAPBodyElement targaBodyElement = targaBody.addBodyElement(targaBodyName);
        targaBodyElement.addChildElement("token").addTextNode(token);
        targaBodyElement.addChildElement("targa").addTextNode(targa);
        connection= (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type","text/xml; charset=utf-8");
        connection.setDoOutput(true);
        os = connection.getOutputStream();
        targaMessage.writeTo(os);
        os.flush();
        Document targaResult = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(connection.getInputStream());
        NodeList ndlmarca=targaResult.getElementsByTagName("marca").item(0).getFirstChild().getChildNodes();
        for (int i=0;i<ndlmarca.getLength();i++){
            if(ndlmarca.item(i).getNodeName().equalsIgnoreCase("nome")){
                String marca=ndlmarca.item(i).getFirstChild().getNodeValue();
                response.setMarca(marca);
            }
        }
        //String modello=targaResult.getElementsByTagName("modello").item(0).getFirstChild().getNodeValue();
        NodeList ndlmodello=targaResult.getElementsByTagName("modelli").item(0).getFirstChild().getChildNodes();
        for (int i=0;i<ndlmodello.getLength();i++){
            if(ndlmodello.item(i).getNodeName().equalsIgnoreCase("descrizione_modello")){
                String modello=ndlmodello.item(i).getFirstChild().getNodeValue();
                response.setModello(modello);
            }
        }
        String data_imm=targaResult.getElementsByTagName("data_imm").item(0).getFirstChild().getNodeValue();
        NodeList items = targaResult.getElementsByTagName("versioni").item(0).getChildNodes();
        versioniResult=new ArrayList<Versione>();
        for(int i=0; i<items.getLength(); i++){
            NodeList itemChilds = (NodeList) items.item(i);
            Versione vers = new Versione();
            for(int j=0; j<itemChilds.getLength(); j++){
                if(itemChilds.item(j).getNodeName().equalsIgnoreCase("codice_motornet")){
                    Node codmtrnet = itemChilds.item(j);
                    vers.setCodiceMotornet(codmtrnet.getFirstChild().getNodeValue());
                    //String htmlSelectVersioni
                }
                if(itemChilds.item(j).getNodeName().equalsIgnoreCase("codice_eurotax")){
                    Node codeutax = itemChilds.item(j);
                    vers.setCodiceEurotax(codeutax.getFirstChild().getNodeValue());
                    //String htmlSelectVersioni
                }
                if(itemChilds.item(j).getNodeName().equalsIgnoreCase("versione")){
                    Node versione = itemChilds.item(j);
                    vers.setVersione(versione.getFirstChild().getNodeValue());
                    //String htmlSelectVersioni
                }
            }
            versioniResult.add(i,vers);
        }

        response.setDdimm(data_imm);
        response.setVersioni(versioniResult);

        return response;
    }
    //valutazione


}

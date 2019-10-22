package com.ags.example;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import java.util.ArrayList;
import java.util.List;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.*;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilderFactory;
import com.ags.example.SearchTargaResponse;
import com.ags.example.Versione;
import javax.xml.soap.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stax.StAXResult;
import javax.xml.transform.stream.StreamResult;

public class MotornetService {private static final String username = "rcbnq4497";
    private static final String password=username;
    private static  URL url;
    static {
        try {
            url = new URL("http://rci-soa-sviluppo.it.grouperci.com:51180/xmlrciita/services?service=canaliVOIntegration");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
    private HttpURLConnection connection =null;
    private OutputStream os = null;

    //login

    public String loginAction () throws Exception {
        SOAPFactory loginSOAPFactory = SOAPFactory.newInstance();
        Name loginBodyName =  loginSOAPFactory.createName("canaliVOIntegration","can","can:CanaliVORequest");
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
    public SearchTargaResponse searchTarga(String brandId, String targa)throws Exception{
        List<Versione> versioniResult = null;
        SearchTargaResponse response = new SearchTargaResponse();
        SOAPFactory targaSOAPFactory = SOAPFactory.newInstance();
        Name targaBodyName =  targaSOAPFactory.createName("CanaliVORequest","can","https://www.rcibanque.com/xml/canali-vo");
        SOAPMessage targaMessage = MessageFactory.newInstance().createMessage();
        SOAPBody targaBody=targaMessage.getSOAPBody();
        SOAPBodyElement targaBodyElement = targaBody.addBodyElement(targaBodyName);
        targaBodyElement.addChildElement("brandId","can").addTextNode(brandId);
        targaBodyElement.addChildElement("plate","can").addTextNode(targa);


        TransformerFactory factory= TransformerFactory.newInstance();
        Transformer transformer=factory.newTransformer();
        Document doc=targaBody.extractContentAsDocument();//domsource
        StringWriter sw = new StringWriter();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.transform(new DOMSource(doc), new StreamResult(sw));
        String transresult=sw.toString();


        /*
        System.out.println(transresult);
        SOAPEnvelope targaEnvelope = targaMessage.getSOAPPart().getEnvelope();
        SOAPBody targaBody = targaEnvelope.getBody();
        SOAPBodyElement targaBodyElement = targaBody.addBodyElement(targaBodyName);
        targaBodyElement.addChildElement("token").addTextNode(token);
        targaBodyElement.addChildElement("targa").addTextNode(targa);
         */
        connection= (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type","text/xml; charset=utf-8");
        connection.setDoOutput(true);
        os = connection.getOutputStream();
        os.write(transresult.getBytes());
        System.out.println(os);
        os.flush();
        Document targaResult = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(connection.getInputStream());
        response.setMarca(targaResult.getElementsByTagName("ns2:makeDescription").item(0).getFirstChild().getNodeValue());
        response.setModello(targaResult.getElementsByTagName("ns2:modelDescription").item(0).getFirstChild().getNodeValue());
        response.setDataImmatricolazione(targaResult.getElementsByTagName("ns2:registrationDate").item(0).getFirstChild().getNodeValue());
        NodeList items = targaResult.getElementsByTagName("ns2:ModelVersion").item(0).getChildNodes();
        versioniResult=new ArrayList<Versione>();
        for(int i=0; i<items.getLength(); i++){
            Versione vers = new Versione();
            vers.setEurotaxCode(targaResult.getElementsByTagName("ns2:ModelVersion").item(0).getChildNodes().item(3).getFirstChild().getNodeValue());
            //String htmlSelectVersion
            //Node versione = itemChilds.item(j);
            vers.setDescription(targaResult.getElementsByTagName("ns2:ModelVersion").item(i).getChildNodes().item(1).getFirstChild().getNodeValue());
            //String htmlSelectVersioni
            versioniResult.add(i,vers);
        }
        response.setVersioni(versioniResult);

        return response;
    }
    //valutazione
    public ValutazioneResponse getValutazione(String targa,String eutaxCode, String ddim) throws Exception {
        ValutazioneResponse valutazione= new ValutazioneResponse();
        SOAPFactory valutazioneSOAPFactory = SOAPFactory.newInstance();
        Name valutazioneBodyName =  valutazioneSOAPFactory.createName("CanaliVORequest","can","https://www.rcibanque.com/xml/canali-vo");
        SOAPMessage valutazioneMessage = MessageFactory.newInstance().createMessage();
        SOAPBody valutazioneBody=valutazioneMessage.getSOAPBody();
        SOAPBodyElement valutazioneBodyElement = valutazioneBody.addBodyElement(valutazioneBodyName);
        valutazioneBodyElement.addChildElement("plate","can").addTextNode(targa);
        valutazioneBodyElement.addChildElement("registrationDate","can").addTextNode(ddim);
        valutazioneBodyElement.addChildElement("categoryVehicle","can").addTextNode("AU");
        valutazioneBodyElement.addChildElement("ModelVersion","can").addChildElement("eurotaxCode","can").addTextNode(eutaxCode);
        TransformerFactory factory= TransformerFactory.newInstance();
        Transformer transformer=factory.newTransformer();
        Document doc=valutazioneBody.extractContentAsDocument();//domsource
        StringWriter sw = new StringWriter();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.transform(new DOMSource(doc), new StreamResult(sw));
        String transresult=sw.toString();
        connection= (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type","text/xml; charset=utf-8");
        connection.setDoOutput(true);
        os = connection.getOutputStream();
        os.write(transresult.getBytes());
        System.out.println(os);
        os.flush();
        Document valutazioneResult = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(connection.getInputStream());
        valutazione.setQuotazione(valutazioneResult.getElementsByTagName("ns2:quotationVehicle").item(0).getFirstChild().getNodeValue());
        valutazione.setQuotazioneBlu(valutazioneResult.getElementsByTagName("ns2:quotationBluVehicle").item(0).getFirstChild().getNodeValue());

        return valutazione;
    }


}

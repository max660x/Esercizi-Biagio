package com.ags.example;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import com.ags.example.*;

@ManagedBean
public class Bean implements Serializable {
    // Variabili
    public String targa;
    public String brand;

    private String eutaxCode;
    public SearchTargaResponse searchTargaResponse;
    public ValutazioneResponse valutazioneResponse;
    private static final long serialVersionUID = 1L;

    //Metodi
    public void searchTarga() throws Exception {
        MotornetService service = new MotornetService();
        this.searchTargaResponse = service.searchTarga(this.brand, this.targa);
    }
    public void valutazione() throws Exception {
        MotornetService service= new MotornetService();
        this.valutazioneResponse= service.getValutazione(this.targa,this.eutaxCode,this.searchTargaResponse.getDataImmatricolazione());
    }
    //getter and setter

    public String getEutaxCode() {
        return eutaxCode;
    }

    public void setEutaxCode(String eutaxCode) {
        this.eutaxCode =eutaxCode;
    }

    public String getTarga() {
        return targa;
    }
    public void setTarga(String targa) {
        this.targa = targa;
    }
    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }

    public SearchTargaResponse getSearchTargaResponse() {
        return searchTargaResponse;
    }
    public ValutazioneResponse getValutazioneResponse(){
        return valutazioneResponse;
    }
}

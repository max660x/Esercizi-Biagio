package com.ags.example;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import com.ags.example.*;

@ManagedBean
@SessionScoped
public class Bean implements Serializable {
    private String tar;
    public MotornetService service;
    private String generated;
    private static final long serialVersionUID = 1L;
    public SearchTargaResponse response;
    public String getTar() {
        return tar;
    }
    public void setTar(String hw) {
        this.tar= tar;
    }

    public String genToken() throws Exception {
        generated=service.loginAction();
        return generated;
    }

    public SearchTargaResponse searchTarga (String targa) throws Exception {

        String secondgen= service.loginAction();
        response=service.searchTarga(secondgen,targa);
        return response;
    }



}

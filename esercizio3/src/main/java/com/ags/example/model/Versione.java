package com.ags.example.model;

public class Versione {
    private String codiceEurotax;
    private String codiceMotornet;
    private String versione;

    public String getCodiceMotornet() {
        return codiceMotornet;
    }

    public void setCodiceMotornet(String codiceMotornet) {
        this.codiceMotornet = codiceMotornet;
    }



    public String getCodiceEurotax() {
        return codiceEurotax;
    }

    public void setCodiceEurotax(String codiceEurotax) {
        this.codiceEurotax = codiceEurotax;
    }

    public String getVersione() {
        return versione;
    }

    public void setVersione(String versione) {
        this.versione = versione;
    }
}

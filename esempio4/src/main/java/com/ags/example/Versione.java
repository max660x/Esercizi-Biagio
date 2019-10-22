package com.ags.example;

public class Versione {
    private String eurotaxCode;
    private String codiceMotornet;
    private String description;

    public String getCodiceMotornet() {
        return codiceMotornet;
    }

    public void setCodiceMotornet(String codiceMotornet) {
        this.codiceMotornet = codiceMotornet;
    }

    public String getEurotaxCode() {
        return eurotaxCode;
    }

    public void setEurotaxCode(String eurotaxCode) {
        this.eurotaxCode = eurotaxCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

package com.ags.example;

import java.util.List;

public class SearchTargaResponse {
    private String marca;
    private List<Versione> versioni;

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModello() {
        return modello;
    }

    public void setModello(String modello) {
        this.modello = modello;
    }

    public String getDdimm() {
        return ddimm;
    }

    public void setDdimm(String ddimm) {
        this.ddimm = ddimm;
    }

    private String modello;
    private String ddimm;


    public List<Versione> getVersioni() {
        return versioni;
    }

    public void setVersioni(List<Versione> versioni) {
        this.versioni = versioni;
    }
}

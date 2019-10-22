package com.ags.example;

import java.util.List;

public class SearchTargaResponse {
    private String marca;
    private List<Versione> versioni;
    private String modello;
    private String dataImmatricolazione;

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

    public String getDataImmatricolazione() {
        return dataImmatricolazione;
    }

    public void setDataImmatricolazione(String dataImmatricolazione) {
        this.dataImmatricolazione =dataImmatricolazione;
    }

    public List<Versione> getVersioni() {
        return versioni;
    }

    public void setVersioni(List<Versione> versioni) {
        this.versioni = versioni;
    }
}

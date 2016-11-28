package com.dtech.smartpulsa;

import java.util.Arrays;
import java.util.List;

/**
 * Created by aris on 14/11/16.
 */

public class PredictNumber {

    public String typeNumber;
    public String kodeTransaksi;
    public String typeSimpati;
    List<String> jatim = Arrays.asList("081130", "081131", "081132", "081133",
            "081134", "081137", "081135", "081136", "081216", "081217", "081230", "081231",
            "081232", "081233", "081234", "081235", "081249", "081252", "081259", "081330",
            "081331", "081332", "081333", "081334", "081335", "081336", "081357", "081358",
            "081359", "082139", "082140", "082141", "082142", "082143", "085230", "085231",
            "085232", "085233", "085234", "085235", "085236", "085257", "085258", "085259",
            "085330", "085331", "085332", "085333", "085334", "085335", "085336", "082228",
            "082229", "082230", "082231", "082232", "082233", "082234", "082264", "082330",
            "082331", "082332", "082333", "082334", "082335", "082336", "082337", "082338");

    public void readNumber(String typeNumber) {
        String subed = typeNumber.substring(0, 4);
        switch (subed) {
            case "0895":
            case "0896":
                setTypeNumber("xl");
                setKodeTransaksi("xr");
                break;
            case "0811":
                readSimpati(typeNumber);
                break;
        }
    }


    public void readSimpati(String typeSimpati) {
        if (!jatim.contains(typeSimpati)) {
            setTypeNumber("Telkomsel");
            setKodeTransaksi("se");
        } else {
            setTypeNumber("Telkomsel");
            setKodeTransaksi("s");
        }
    }

    public String getTypeSimpati() {
        return typeSimpati;
    }

    public void setTypeSimpati(String typeSimpati) {
        this.typeSimpati = typeSimpati;
    }

    public String getTypeNumber() {
        return typeNumber;
    }

    public void setTypeNumber(String typeNumber) {
        this.typeNumber = typeNumber;
    }

    public String getKodeTransaksi() {
        return kodeTransaksi;
    }

    public void setKodeTransaksi(String kodeTransaksi) {
        this.kodeTransaksi = kodeTransaksi;
    }
}

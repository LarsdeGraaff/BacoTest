package ldg.bacotest.entities;

/**
 * Created by Lars on 5/10/2015.
 */
public class Speler {
    private String objectId;
    private String spelersVoornaam;
    private String spelersNaam;
    private String spelersFoto;
    private String positie;
    private String voet;
    private String leeftijd;
    private String spelerStats;

    public Speler() {
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getSpelersVoornaam() {
        return spelersVoornaam;
    }

    public void setSpelersVoornaam(String spelersVoornaam) {
        this.spelersVoornaam = spelersVoornaam;
    }

    public String getSpelersNaam() {
        return spelersNaam;
    }

    public void setSpelersNaam(String spelersNaam) {
        this.spelersNaam = spelersNaam;
    }

    public String getPositie() {
        return positie;
    }

    public void setPositie(String positie) {
        this.positie = positie;
    }

    public String getVoet() {
        return voet;
    }

    public void setVoet(String voet) {
        this.voet = voet;
    }

    public String getLeeftijd() {
        return leeftijd;
    }

    public void setLeeftijd(String leeftijd) {
        this.leeftijd = leeftijd;
    }

    public String getSpelersFoto() {
        return spelersFoto;
    }

    public void setSpelersFoto(String spelersFoto) {
        this.spelersFoto = spelersFoto;
    }

    public String getSpelerStats() {
        return spelerStats;
    }

    public void setSpelerStats(String spelerStats) {
        this.spelerStats = spelerStats;
    }
}

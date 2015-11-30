package ldg.bacotest.entities;

/**
 * Created by Lars on 8/10/2015.
 */
public class Berichten {
    private String objectId;
    private String titel;
    private String inleiding;
    private String bericht;
    private String userId;
    private int aantalReacties;

    public Berichten() {
    }


    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getInleiding() {
        return inleiding;
    }

    public void setInleiding(String inleiding) {
        this.inleiding = inleiding;
    }

    public String getBericht() {
        return bericht;
    }

    public void setBericht(String bericht) {
        this.bericht = bericht;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

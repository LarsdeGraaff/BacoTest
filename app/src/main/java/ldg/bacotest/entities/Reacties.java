package ldg.bacotest.entities;

/**
 * Created by Lars on 9/10/2015.
 */
public class Reacties {
    private String objectId;
    private String reactie;
    private String berichtId;
    private String userId;

    public Reacties() {
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getReactie() {
        return reactie;
    }

    public void setReactie(String reactie) {
        this.reactie = reactie;
    }

    public String getBerichtId() {
        return berichtId;
    }

    public void setBerichtId(String berichtId) {
        this.berichtId = berichtId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

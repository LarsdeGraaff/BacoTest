package ldg.bacotest.entities;

/**
 * Created by Lars on 21/01/2016.
 */
public class SpelerStats {
    private String objectId;
    private String thuisploeg;
    private String uitploeg;
    private String goals;
    private String assists;

    public SpelerStats() {
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getThuisploeg() {
        return thuisploeg;
    }

    public void setThuisploeg(String thuisploeg) {
        this.thuisploeg = thuisploeg;
    }

    public String getUitploeg() {
        return uitploeg;
    }

    public void setUitploeg(String uitploeg) {
        this.uitploeg = uitploeg;
    }

    public String getGoals() {
        return goals;
    }

    public void setGoals(String goals) {
        this.goals = goals;
    }

    public String getAssists() {
        return assists;
    }

    public void setAssists(String assists) {
        this.assists = assists;
    }
}

package ldg.bacotest.entities;

/**
 * Created by Lars on 21/01/2016.
 */
public class SpelerStats {
    private String objectId;
    private String tegenstander;
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

    public String getTegenstander() {
        return tegenstander;
    }

    public void setTegenstander(String tegenstander) {
        this.tegenstander = tegenstander;
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

package ldg.bacotest.entities;

/**
 * Created by Lars on 19/01/2016.
 */
public class MatchStats {

    private String objectId;
    private String naamSpeler;
    private String goals;
    private String assists;

    public MatchStats() {
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getNaamSpeler() {
        return naamSpeler;
    }

    public void setNaamSpeler(String naamSpeler) {
        this.naamSpeler = naamSpeler;
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

package ldg.bacotest.entities;

/**
 * Created by Lars on 7/10/2015.
 */
public class Statistieken {

    private String objectId;
    private String selecties;
    private String goals;
    private String assisten;

    public Statistieken() {
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getSelecties() {
        return selecties;
    }

    public void setSelecties(String selecties) {
        this.selecties = selecties;
    }

    public String getGoals() {
        return goals;
    }

    public void setGoals(String goals) {
        this.goals = goals;
    }

    public String getAssisten() {
        return assisten;
    }

    public void setAssisten(String assisten) {
        this.assisten = assisten;
    }

}



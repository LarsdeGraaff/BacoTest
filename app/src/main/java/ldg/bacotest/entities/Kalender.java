package ldg.bacotest.entities;

/**
 * Created by Lars on 13/10/2015.
 */
public class Kalender {
    private String objectId;
    private String thuisPloeg;
    private String uitPloeg;
    private String plaats;
    private String uur;

    public Kalender() {
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getThuisPloeg() {
        return thuisPloeg;
    }

    public void setThuisPloeg(String thuisPloeg) {
        this.thuisPloeg = thuisPloeg;
    }

    public String getUitPloeg() {
        return uitPloeg;
    }

    public void setUitPloeg(String uitPloeg) {
        this.uitPloeg = uitPloeg;
    }

    public String getPlaats() {
        return plaats;
    }

    public void setPlaats(String plaats) {
        this.plaats = plaats;
    }

    public String getUur() {
        return uur;
    }

    public void setUur(String uur) {
        this.uur = uur;
    }
}

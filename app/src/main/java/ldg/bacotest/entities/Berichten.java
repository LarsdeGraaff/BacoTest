package ldg.bacotest.entities;

import android.graphics.Bitmap;
import android.media.Image;

import com.parse.ParseFile;

import java.io.File;

/**
 * Created by Lars on 8/10/2015.
 */
public class Berichten {
    private String objectId;
    private String titel;
    private String inleiding;
    private String bericht;
    private String userId;
    private ParseFile foto;
    private int aantalReacties;
    private String timestamp;

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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public ParseFile getFoto() {
        return foto;
    }

    public void setFoto(ParseFile foto) {
        this.foto = foto;
    }


}

package ldg.bacotest;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;

/**
 * Created by Lars on 6/10/2015.
 */
public class BacoJuniorsApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "1QuEhhYIyNqj55c6x0SwZvh49r6dIZrQldG8OD1Y", "mlFdqHdYeTg2u0jlm3Ds4cahzVPvtEQy8s2w9HnY");
        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG);
        ParseInstallation.getCurrentInstallation().saveInBackground();

    }
}
package de.gemo.stunden;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.ViewGroup;
import de.gemo.stunden.units.DayHolder;
import de.gemo.stunden.units.undostates.UndoManager;
import de.gemo.stunden.utils.GUICreator;

public class MainActivity extends ActionBarActivity {

    private DayHolder dayHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // INIT GUIUtils
        GUICreator.initApp(this);
        GUICreator.initViewGroup(((ViewGroup) findViewById(R.id.LinearLayout)));
        ((ViewGroup) findViewById(R.id.ScrollView)).setBackgroundColor(GUICreator.DESIGN.getBackground());

        // initialize data
        this.dayHolder = new DayHolder(this);

        // show Month-View
        new CountDownTimer(250, 250) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                GUICreator.showMonthView();
            }
        }.start();
    }

    @Override
    public void onBackPressed() {
        UndoManager.goBack();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        // init GUIUtils
        GUICreator.initMenu(menu);

        return true;
    }

    public DayHolder getDayHolder() {
        return dayHolder;
    }

}

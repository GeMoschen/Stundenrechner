package de.gemo.stunden;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.ViewGroup;
import de.gemo.stunden.units.DayHolder;
import de.gemo.stunden.units.undostates.UndoManager;
import de.gemo.stunden.utils.GUIUtils;

public class MainActivity extends ActionBarActivity {

	public static int currentState = 0; // 0 : DAY
	private DayHolder dayHolder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// INIT GUIUtils
		GUIUtils.setApp(this);
		((ViewGroup) findViewById(R.id.ScrollView)).setBackgroundColor(GUIUtils.BACKGROUND);
		GUIUtils.setViewGroup((ViewGroup) findViewById(R.id.LinearLayout));

		// initialize data
		this.dayHolder = new DayHolder(this);

		// show Month-View
		new CountDownTimer(250, 250) {
			@Override
			public void onTick(long millisUntilFinished) {
			}

			@Override
			public void onFinish() {
				GUIUtils.showMonthView();
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
		GUIUtils.setMenu(menu);

		return true;
	}

	public DayHolder getDayHolder() {
		return dayHolder;
	}

}

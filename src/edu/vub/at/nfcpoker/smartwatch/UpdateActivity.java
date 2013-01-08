package edu.vub.at.nfcpoker.smartwatch;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class UpdateActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_update, menu);
		return true;
	}

	public void doit(View v) {
		Intent i = new Intent(WePokerWidgetExtension.UPDATE_ACTION);
		i.putExtra("probability", 0.95);
		sendBroadcast(i);
	}
	
 }
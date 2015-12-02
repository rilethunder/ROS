package com.cs123grpE.restaurantorderingsystem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class SelectAccount extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_account);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.select_account, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void onBackPressed() {
		// disables the back button
	}
	
	public void toAdmin (View v) {
		// go to the Admin account main screen
		Intent i = new Intent (this, Admin.class);
		startActivity(i);
		finish();
	}
	
	public void toKitchen (View v) {
		// go to the Kitchen account main screen
		Intent i = new Intent (this, Kitchen.class);
		startActivity(i);
		finish();
	}
	
	public void toCustomer (View v) {
		// go to the Customer account main screen
		Intent i = new Intent (this, Customer.class);
		startActivity(i);
		finish();
	}
}

package com.jon.demonstratesruntimepermission.ui.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.jon.demonstratesruntimepermission.R;
import com.jon.demonstratesruntimepermission.utils.LogUtils;

import java.text.MessageFormat;

public class MainActivity
		extends Activity {

	//==============================================
	//              Constants
	//==============================================

	private static final int REQUEST_CODE_PERMISSION_ACCESS_FINE_LOCATION = 1;

	//==============================================
	//              Fields
	//==============================================

	//ui
	private View     mGetLocationButton;
	private TextView mLocationTV;
	//
	LocationManager mLocationManager;


	//==============================================
	//              Listeners
	//==============================================

	LocationListener locationListener = new LocationListener() {
		@Override
		public void onLocationChanged(Location location) {
			LogUtils.LogDebug();

			String locationStr;

			if (location != null) {
				locationStr = MessageFormat.format(getString(R.string.mainActivity_locationTVFormat), location.getLatitude(), location.getLongitude());
			}
			else {
				locationStr = getString(R.string.mainActivity_locationTV_errorMessage);
			}

			mLocationTV.setText(locationStr);
		}

		//region irrelevant methods
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {}

		@Override
		public void onProviderEnabled(String provider) {}

		@Override
		public void onProviderDisabled(String provider) {}
		//endregion
	};

	//==============================================
	//              Activity Lifecycle Methods
	//==============================================

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		LogUtils.LogDebug();

		mGetLocationButton = findViewById(R.id.mainActivity_getLocation);
		mLocationTV = (TextView) findViewById(R.id.mainActivity_locationTV);

		mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		mGetLocationButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				LogUtils.LogDebug();

				getLocationCoordinates();
			}
		});

	}

	//==============================================
	//              Private Methods
	//==============================================

	public void getLocationCoordinates() {
		LogUtils.LogDebug();

		if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
				&& checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

			LogUtils.LogDebug("Don't got them permissions");

			if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
				LogUtils.LogDebug("should show request rationale");
				//prompt the user to please go and approve u
			}
			else {
				LogUtils.LogDebug("no showing rationale needed. Requeting permissions");
				requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
						REQUEST_CODE_PERMISSION_ACCESS_FINE_LOCATION);
			}
			return;
		}
		else {
			mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
		}

	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

		LogUtils.LogDebug();

		switch (requestCode) {

			case REQUEST_CODE_PERMISSION_ACCESS_FINE_LOCATION: {

				// If request is cancelled, the result arrays are empty.
				if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

					LogUtils.LogDebug("Permission granted");
					// permission was granted, yay! Do the
					// contacts-related task you need to do.
					if (mLocationManager != null && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

						mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
					}
				}
				else {

					LogUtils.LogDebug("Permission denied");
					// permission denied, boo! Disable the
					// functionality that depends on this permission.
					if (mLocationTV != null) {

						mLocationTV.setText(getString(R.string.permission_denied));
					}
				}
				return;
			}
		}
	}

	//==============================================
	//              Options Menu Methods
	//==============================================

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}

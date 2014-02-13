package com.rennakanote.VIS141Final;

import java.sql.Date;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class MyLocation extends Activity implements LocationListener  {
	  
	  private TextView latituteField;
	  private TextView longitudeField;
	  private TextView dateField;
	  private LocationManager locationManager;
	  private String provider;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		latituteField = (TextView) findViewById(R.id.latTextView);
	    longitudeField = (TextView) findViewById(R.id.lngTextView);
	    dateField = (TextView) findViewById(R.id.timeTextView);
	    
	    // Get the location manager
	    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

	    // Define the criteria how to select the location provide
	    Criteria criteria = new Criteria();
	    provider = locationManager.getBestProvider(criteria, false);
	    Location location = locationManager.getLastKnownLocation(provider);
	    // fetch current date
	    Date date = new Date(location.getTime());
	    java.text.DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());
	    
	    // Initialize the location fields
	    if (location != null) {
	      System.out.println("Provider " + provider + " has been selected.");
	      onLocationChanged(location);
	      	
	    } else {
	      latituteField.setText("Location not available");
	      longitudeField.setText("Location not available");
	      dateField.setText("Timelog not available");
	    }
	  }	    
	  // Request updates at startup
	  @Override
	  protected void onResume() {
	    super.onResume();
	    locationManager.requestLocationUpdates(provider, 400, 1, this);
	  }

	  // Remove the location listener updates when Activity is paused
	  @Override
	  protected void onPause() {
	    super.onPause();
	    locationManager.removeUpdates(this);
	  }

	  @Override
	  public void onLocationChanged(Location location) {
	    float lat = (float) (location.getLatitude());
	    float lng = (float) (location.getLongitude());
	    latituteField.setText(String.valueOf(lat));
	    longitudeField.setText(String.valueOf(lng));
	  }

	  @Override
	  public void onStatusChanged(String provider, int status, Bundle extras) {
	    // TODO Auto-generated method stub

	  }

	  @Override
	  public void onProviderEnabled(String provider) {
	    Toast.makeText(this, "Enabled new provider " + provider,
	        Toast.LENGTH_SHORT).show();

	  }

	  @Override
	  public void onProviderDisabled(String provider) {
	    Toast.makeText(this, "Disabled provider " + provider,
	        Toast.LENGTH_SHORT).show();
	  }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_location, menu);
		return true;
	}

}
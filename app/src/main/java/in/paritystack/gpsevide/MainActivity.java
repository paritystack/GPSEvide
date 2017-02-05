package in.paritystack.gpsevide;

import android.content.Context;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    LocationManager locationManager = null;
    GpsStatus.Listener listener = null;
    LocationListener locationListener = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView = (TextView) findViewById(R.id.log);
        textView.append("GPS status\n\n\n");
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        listener = new GpsStatus.Listener(){
            @Override
            public void onGpsStatusChanged(int event) {

                    textView.append("onGpsStatusChanged :" + event + "\n\n");
                    GpsStatus status = locationManager.getGpsStatus(null);

                    textView.append("Max satellites :" + status.getMaxSatellites() + "\n");
                    textView.append("Time to fix :" + status.getTimeToFirstFix() + "\n");

                    for (GpsSatellite satellite : status.getSatellites()) {
                        textView.append("Satellite\n----------\n" +
                                decodeSatellite(satellite) + "\n\n\n");
                    }

                }

        };
        locationManager.addGpsStatusListener(listener);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,2*60*1000,0,locationListener);

    }

    private String decodeSatellite(GpsSatellite satellite){
        String val = "";
        val = val.concat("Azimuth :"+ satellite.getAzimuth()+"\n");
        val = val.concat("Elevation "+ satellite.getElevation()+"\n");
        val = val.concat("Pnr :"+ satellite.getPrn()+"\n");
        val = val.concat("Snr :"+ satellite.getSnr()+"\n");
        val = val.concat("Almanac :"+ satellite.hasAlmanac()+"\n");
        val = val.concat("Ephemeris :"+ satellite.hasEphemeris()+"\n");
        return val;
    }
}

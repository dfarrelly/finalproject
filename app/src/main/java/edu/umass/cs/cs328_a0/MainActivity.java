package edu.umass.cs.cs328_a0;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener, LocationListener{

    SensorManager sensorManager;
    Sensor accelerometer;
    Sensor barometer;
    boolean running = false;
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;
    TextView txtLat;
    TextView txtFile;
    TextView txterror;
    String lat;
    int count = 0;
    String provider;
    protected String latitude,longitude;
    protected boolean gps_enabled,network_enabled;
    StringBuilder sb;
    PrintWriter pw;
    String csv;
    String output;
    double pressure = 0.0;
    double lati = 0.0;
    double longi = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtLat = (TextView) findViewById(R.id.location);
        txterror = (TextView) findViewById(R.id.error);
        txtFile = (TextView) findViewById(R.id.file_error);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        try {
//            csv = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
//            txterror.setText(csv);
//            writer = new CSVWriter(new FileWriter(csv));
//            List<String[]> data = new ArrayList<String[]>();
//            data.add(new String[] {"India", "New Delhi"});
//            data.add(new String[] {"United States", "Washington D.C"});
//            data.add(new String[] {"Germany", "Berlin"});
//            writer.writeAll(data);
            //writer.close();
            writeFileOnInternalStorage("kill me");

        }
        catch(Exception e)
        {
            txtFile.setText(e.toString());
        }
        try {
            //txterror.setText("no error");
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }
        catch(SecurityException e){
            //txterror.setText("yes error");
            txtLat.setText(e.toString());

        }
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        barometer = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
    }

    @Override
    public void onLocationChanged(Location location) {
        count++;
        txtLat = (TextView) findViewById(R.id.location);
        txtLat.setText(location.getLatitude() + "," + location.getLongitude());
        lati = location.getLatitude();
        longi = location.getLongitude();
        try {
            if(pressure > 0) {
                output = output + "\n" + location.getLatitude() + "," + location.getLongitude() + "," + pressure;
               // writeFileOnInternalStorage(output);
            }
        }
        catch(Exception e)
        {

            //txterror.setText(e.toString());
        }

    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude","disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude","enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude","status");
    }

    public void onSensorChanged(SensorEvent se)
    {


        System.out.println("wow");
        if(se.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
        {
            float x = se.values[0];
            float y = se.values[1];
            float z = se.values[2];
            TextView xbutton = (TextView)findViewById(R.id.accel_x);
            xbutton.setText("" + x);
            TextView ybutton = (TextView)findViewById(R.id.accel_y);
            ybutton.setText("" + y);
            TextView zbutton = (TextView)findViewById(R.id.accel_z);
            zbutton.setText("" + z);
            running = true;
        }
        if (se.sensor.getType() == Sensor.TYPE_PRESSURE) {//finish this here to display pressure data
            View texter = findViewById(R.id.file_error);
            TextView Vtexter = (TextView) texter;
            float[] answerhelp = se.values;
            Vtexter.setText(String.valueOf(answerhelp[0]));//find data being processed and print
            running = true;
            pressure = answerhelp[0];
            try {
                if(longi != 0.0 && lati != 0.0) {
                    output = output + "\n" + lati + "," + longi + "," + pressure;
                    writeFileOnInternalStorage(output);
                }
            }
            catch(Exception e)
            {

                //txterror.setText(e.toString());
            }
        }


    }

    public void onAccuracyChanged(Sensor s, int i)
    {

    }
//
//    private boolean isLocationEnabled(){
//        return locationManager.isProvidedEnabler(LocationManager.GPS_PROVIDER)||locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
//    }

    public void startStop(View view)
    {
        Toast toasty;
        if(!running) {
            running = true;
            sensorManager.registerListener(this, accelerometer, sensorManager.SENSOR_DELAY_NORMAL);
            sensorManager.registerListener(this,barometer,sensorManager.SENSOR_DELAY_FASTEST);
            toasty = Toast.makeText(this,R.string.wow, Toast.LENGTH_SHORT);
            toasty.show();

            //Button button = (Button)findViewById(R.id.button_stop);
            Button button = (Button) view;
            button.setText("Stop");
            readFileOnInternalStorage();
        }
        else
        {
            sensorManager.unregisterListener(this);

            toasty =Toast.makeText(this,R.string.woah, Toast.LENGTH_SHORT);
            toasty.show();
            Button button = (Button) view;
            button.setText("Start");
            running = false;

        }


    }

    public void onPause()
    {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    public void onResume()
    {
        super.onResume();
        if(running)
        {
            sensorManager.unregisterListener(this);
        }
    }

    public void writeFileOnInternalStorage(String message){
        try{

//            OutputStreamWriter outputWriter = new OutputStreamWriter(this.openFileOutput("data.txt", Context.MODE_PRIVATE));
//            outputWriter.write(message);
//            outputWriter.close();
            FileOutputStream fos= openFileOutput("mytext.txt", MODE_PRIVATE);
            fos.write(message.getBytes());
            fos.close();
            txterror.setText("hmmm");

        }catch (Exception e){
            txterror.setText(e.toString());
        }
    }
    public void readFileOnInternalStorage(){
        try{
            FileInputStream fis = openFileInput("mytext.txt");

            InputStreamReader inputStreamReader = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(inputStreamReader);
            StringBuffer sb = new StringBuffer();
            String lines;
            while( (lines = br.readLine())!=null )
            {
                sb.append(lines+"\n");
            }
            txterror.setText(sb.toString());

            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("message/rfc822");
            i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"zkiihne@gmail.com"});
            i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
            i.putExtra(Intent.EXTRA_TEXT   , sb.toString());

            startActivity(Intent.createChooser(i, "Send mail..."));


        }catch (Exception e){
            txterror.setText(e.toString());
        }
    }

}

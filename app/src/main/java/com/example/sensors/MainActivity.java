package com.example.sensors;

import android.hardware.Sensor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.sensors.fragments.ActivityRecognitionFragment;
import com.example.sensors.fragments.CompassFragment;
import com.example.sensors.fragments.MotionSensorFragment;
import com.example.sensors.fragments.SensorGraphFragment;
import com.example.sensors.fragments.SensorsListFragment;
import com.example.sensors.fragments.StepCounterFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Affiche la liste des capteurs par défaut au démarrage
        if (savedInstanceState == null) {
            openFragment(new SensorsListFragment());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_sensors) {
            openFragment(new SensorsListFragment());

        } else if (id == R.id.menu_temperature) {
            openFragment(SensorGraphFragment.newInstance(
                    Sensor.TYPE_AMBIENT_TEMPERATURE, "Température ambiante", "FIRST_VALUE"));

        } else if (id == R.id.menu_humidity) {
            openFragment(SensorGraphFragment.newInstance(
                    Sensor.TYPE_RELATIVE_HUMIDITY, "Humidité relative", "FIRST_VALUE"));

        } else if (id == R.id.menu_proximity) {
            openFragment(SensorGraphFragment.newInstance(
                    Sensor.TYPE_PROXIMITY, "Capteur de proximité", "FIRST_VALUE"));

        } else if (id == R.id.menu_magnetic) {
            openFragment(SensorGraphFragment.newInstance(
                    Sensor.TYPE_MAGNETIC_FIELD, "Champ magnétique", "MAGNITUDE"));

        } else if (id == R.id.menu_accelerometer) {
            openFragment(MotionSensorFragment.newInstance(
                    Sensor.TYPE_ACCELEROMETER, "Accéléromètre : x, y, z"));

        } else if (id == R.id.menu_gravity) {
            openFragment(MotionSensorFragment.newInstance(
                    Sensor.TYPE_GRAVITY, "Gravité : x, y, z"));

        } else if (id == R.id.menu_gyroscope) {
            openFragment(MotionSensorFragment.newInstance(
                    Sensor.TYPE_GYROSCOPE, "Gyroscope : rad/s"));

        } else if (id == R.id.menu_steps) {
            openFragment(new StepCounterFragment());

        } else if (id == R.id.menu_compass) {
            openFragment(new CompassFragment());

        } else if (id == R.id.menu_activity) {
            openFragment(new ActivityRecognitionFragment());
        }

        return true;
    }

    private void openFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}

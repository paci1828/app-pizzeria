package simone.pizzeria4.app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.Manifest;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ThemeManager.applyTheme(ThemeManager.getThemeMode(this));
        setContentView(R.layout.activity_main);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> maybeRequestNotificationPermissionAndNotify());
    }

    private void maybeRequestNotificationPermissionAndNotify() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS},
                        1001);
                return;
            }
        }
        if (PreferencesHelper.areNotificationsEnabled(this)) {
            NotificationHelper.notify(this, 1, "Ciao!", "Questa è una notifica di prova.");
        } else {
            Snackbar.make(findViewById(R.id.fab), "Notifiche disabilitate", Snackbar.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1001) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                NotificationHelper.notify(this, 1, "Ciao!", "Questa è una notifica di prova.");
            } else {
                Snackbar.make(findViewById(R.id.fab), "Permesso notifiche negato", Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.theme_auto) {
            ThemeManager.setThemeMode(this, ThemeManager.MODE_AUTO);
            return true;
        } else if (id == R.id.theme_light) {
            ThemeManager.setThemeMode(this, ThemeManager.MODE_LIGHT);
            return true;
        } else if (id == R.id.theme_dark) {
            ThemeManager.setThemeMode(this, ThemeManager.MODE_DARK);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
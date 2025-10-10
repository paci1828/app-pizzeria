package simone.pizzeria.app;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


public class MainActivity extends AppCompatActivity {

    private NavController navController;
    private AppBarConfiguration appBarConfiguration;
    private SharedPreferences preferences;
    private static final String PREFS_NAME = "ThemePrefs";
    private static final String KEY_THEME = "theme_mode";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Applica il tema salvato PRIMA di super.onCreate()
        preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int savedTheme = preferences.getInt(KEY_THEME, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        AppCompatDelegate.setDefaultNightMode(savedTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Recupera il NavController dal NavHostFragment
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();

        // Imposta la Toolbar come ActionBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        updateToolbarTextColor(toolbar);


        // Configura la navigazione con la Toolbar
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        // Floating Action Button: esempio di azione
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            new AlertDialog.Builder(this)
                    .setTitle("Contattaci")
                    .setMessage("Puoi scriverci a info@pizzeria.it 🍕")
                    .setPositiveButton("OK", null)
                    .show();
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Segna il tema attivo
        int currentTheme = preferences.getInt(KEY_THEME, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        MenuItem themeAuto = menu.findItem(R.id.theme_auto);
        MenuItem themeLight = menu.findItem(R.id.theme_light);
        MenuItem themeDark = menu.findItem(R.id.theme_dark);

        themeAuto.setChecked(currentTheme == AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        themeLight.setChecked(currentTheme == AppCompatDelegate.MODE_NIGHT_NO);
        themeDark.setChecked(currentTheme == AppCompatDelegate.MODE_NIGHT_YES);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            new AlertDialog.Builder(this)
                    .setTitle("Impostazioni")
                    .setMessage("Sezione impostazioni in arrivo! 🍕")
                    .setPositiveButton("OK", null)
                    .show();
            return true;
        } else if (id == R.id.theme_auto) {
            applyThemeMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
            item.setChecked(true);
            return true;
        } else if (id == R.id.theme_light) {
            applyThemeMode(AppCompatDelegate.MODE_NIGHT_NO);
            item.setChecked(true);
            return true;
        } else if (id == R.id.theme_dark) {
            applyThemeMode(AppCompatDelegate.MODE_NIGHT_YES);
            item.setChecked(true);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void applyThemeMode(int themeMode) {
      if (preferences == null) {
            preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        }
        preferences.edit().putInt(KEY_THEME, themeMode).apply();


        AppCompatDelegate.setDefaultNightMode(themeMode);

        new android.os.Handler().postDelayed(() -> {
            Toolbar toolbar = findViewById(R.id.toolbar);
            if (toolbar != null) {
                updateToolbarTextColor(toolbar);
            }
        }, 100);
    }

    // Gestisce il pulsante "indietro" (Up button)
    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void updateToolbarTextColor(Toolbar toolbar) {
      int nightMode = getResources().getConfiguration().uiMode &
                android.content.res.Configuration.UI_MODE_NIGHT_MASK;

        if (nightMode== Configuration.UI_MODE_NIGHT_YES) {
            // Testo bianco per toolbar
            toolbar.setPopupTheme(androidx.appcompat.R.style.ThemeOverlay_AppCompat_Dark);

        } else  {

            toolbar.setPopupTheme(androidx.appcompat.R.style.ThemeOverlay_AppCompat_Light);

        }
    }
}
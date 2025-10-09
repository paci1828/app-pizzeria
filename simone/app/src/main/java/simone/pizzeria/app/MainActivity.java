package simone.pizzeria.app;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


public class MainActivity extends AppCompatActivity {

    private NavController navController;
    private AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Abilita edge-to-edge per evitare sovrapposizioni
        WindowCompat.setDecorFitsSystemWindows(getWindow(),
                false);

        // Configura la status bar
        WindowInsetsControllerCompat windowInsetsController =
                WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());

        if (windowInsetsController != null) {
            // Usa icone scure sulla status bar se in modalità chiara
            windowInsetsController.setAppearanceLightStatusBars(false);
        }
        // Recupera il NavController dal NavHostFragment
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();

        // Imposta la Toolbar come ActionBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

    // Gestisce il pulsante "indietro" (Up button)
    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
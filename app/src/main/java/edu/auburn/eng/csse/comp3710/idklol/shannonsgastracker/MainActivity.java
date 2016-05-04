package edu.auburn.eng.csse.comp3710.idklol.shannonsgastracker;

import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements ServiceEntryListFragment.OnListFragmentInteractionListener,
        LogEntryCreationFragment.LogEntryCreationFragmentListener,
        GasDetailFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        displayListFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_new_gas_record:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.current_fragment, LogEntryCreationFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListFragmentInteraction(LogEntry item) {
        GasDetailFragment newFragment = GasDetailFragment.newInstance(item.getID());
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.current_fragment, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void displayListFragment() {
        ServiceEntryListFragment newFragment = ServiceEntryListFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.current_fragment, newFragment);
        transaction.commit();
    }

    @Override
    public void popBackStack() {
        getSupportFragmentManager().popBackStack();
    }
}

package edu.auburn.eng.csse.comp3710.idklol.shannonsgastracker;

import android.content.ContentProvider;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import edu.auburn.eng.csse.comp3710.idklol.shannonsgastracker.dummy.DummyContent;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LogEntryCreationFragmentListener} interface
 * to handle interaction events.
 * Use the {@link LogEntryCreationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LogEntryCreationFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private VehicleLog mVehicleLog = DummyContent.VEHICLE_LOG;

    private LogEntryCreationFragmentListener mListener;

    public LogEntryCreationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment LogEntryCreationFragment.
     */
    // TODO: Add parameter to reflect VehicleLog to which this entry will be added
    public static LogEntryCreationFragment newInstance() {
        LogEntryCreationFragment fragment = new LogEntryCreationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Enable display of custom app bar actions
        setHasOptionsMenu(true);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_log_entry_creation, container, false);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof LogEntryCreationFragmentListener) {
            mListener = (LogEntryCreationFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement LogEntryCreationFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.log_entry_creation, menu);

        if (mListener instanceof AppCompatActivity) {
            android.support.v7.app.ActionBar bar = ((AppCompatActivity)mListener).getSupportActionBar();
            if (bar != null) {
                bar.setDisplayHomeAsUpEnabled(true);
                bar.setTitle(R.string.log_entry_creation_title);
                bar.setDisplayShowTitleEnabled(true);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //TODO figure out if this is the best way to implement up button function
            case android.R.id.home:
                mListener.displayListFragment();
                break;
            case R.id.action_done:
                createNewGasEntry();
                mListener.displayListFragment();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void createNewGasEntry() {
        View view = getView();

        if (view != null) {
            String gallonString = ((EditText)(view.findViewById(R.id.input_gallons))).getText().toString();
            double gallonsValue = Double.parseDouble(gallonString);
            String priceString = ((EditText)(view.findViewById(R.id.input_price))).getText().toString();
            double priceValue = Double.parseDouble(priceString);
            String odometerString = ((EditText)(view.findViewById(R.id.input_odometer))).getText().toString();
            double odometerValue = Double.parseDouble(odometerString);

            GasEntry newEntry = new GasEntry();
            newEntry.setGallons(gallonsValue);
            newEntry.setPrice(priceValue);
            newEntry.setOdometer(odometerValue);

            mVehicleLog.addEntry(newEntry);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface LogEntryCreationFragmentListener {
        void displayListFragment();
    }
}
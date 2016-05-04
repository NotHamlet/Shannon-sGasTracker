package edu.auburn.eng.csse.comp3710.idklol.shannonsgastracker;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LogEntryCreationFragmentListener} interface
 * to handle interaction events.
 * Use the {@link LogEntryCreationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LogEntryCreationFragment extends Fragment {
    private static final int TYPE_GAS_ENTRY = 0;
    private static final int TYPE_SERVICE_ENTRY = 1;
    private static final int TYPE_LOG_ENTRY = 2;

    private VehicleLog mVehicleLog;

    private LogEntryCreationFragmentListener mListener;
    private Spinner mTypeSpinner;

    public LogEntryCreationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment LogEntryCreationFragment.
     */
    public static LogEntryCreationFragment newInstance() {
        LogEntryCreationFragment fragment = new LogEntryCreationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        VehicleLog log;
        try {
            log = (new XMLArchiver()).loadEntries(getContext());
        } catch (IOException e) {
            log = new VehicleLog();
            e.printStackTrace();
        }

        mVehicleLog = log;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Enable display of custom app bar actions
        setHasOptionsMenu(true);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_log_entry_creation, container, false);


        // Stock spinner with values
        Resources res = getResources();
        String[] spinnerOptions = {res.getString(R.string.spinner_label_gas_entry),
                res.getString(R.string.spinner_label_service_entry),
                res.getString(R.string.spinner_label_log_entry)};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(), R.layout.support_simple_spinner_dropdown_item, spinnerOptions);

        Spinner typeSpinner = (Spinner)view.findViewById(R.id.entry_type_spinner);
        typeSpinner.setAdapter(adapter);
        typeSpinner.setOnItemSelectedListener(new EntryTypeSpinnerListener());
        mTypeSpinner = typeSpinner;

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
                mListener.popBackStack();
                break;
            case R.id.action_done:
                createNewLogEntry();
                mListener.popBackStack();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void createNewLogEntry() {
        View view = getView();

        if (view != null) {
            // TODO: Add error checking and either instantiate with default values or flag required inputs
            LogEntry newEntry;
            switch (mTypeSpinner.getSelectedItemPosition()) {
                case TYPE_GAS_ENTRY:
                    GasEntry gasEntry = new GasEntry();
                    String gallonString = ((EditText) (view.findViewById(R.id.input_gallons))).getText().toString();
                    double gallonsValue = Double.parseDouble(gallonString);
                    gasEntry.setGallons(gallonsValue);

                    String priceString = ((EditText) (view.findViewById(R.id.input_price))).getText().toString();
                    double priceValue = Double.parseDouble(priceString);
                    gasEntry.setPrice(priceValue);

                    newEntry = gasEntry;
                    break;
                case TYPE_SERVICE_ENTRY:
                    ServiceEntry serviceEntry= new ServiceEntry();
                    String serviceTypeString = ((EditText) (view.findViewById(R.id.input_service_type))).getText().toString();
                    serviceEntry.setServiceType(serviceTypeString);

                    newEntry = serviceEntry;
                    break;
                default:
                    newEntry = new LogEntry();
            }

            String odometerString = ((EditText)(view.findViewById(R.id.input_odometer))).getText().toString();
            double odometerValue = Double.parseDouble(odometerString);
            newEntry.setOdometer(odometerValue);

            String noteString = ((EditText)(view.findViewById(R.id.input_note))).getText().toString();
            newEntry.setNote(noteString);

            mVehicleLog.addEntry(newEntry);
            try {
                (new XMLArchiver()).saveEntries(mVehicleLog, getContext());
            } catch (IOException e) {
                Log.e("XMLTest", "Error new Log Entry");
                e.printStackTrace();
            }

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
        void popBackStack();
    }


    private class EntryTypeSpinnerListener implements AdapterView.OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            View parentView = getView();
            if (parentView != null) {
                switch (position) {
                    case TYPE_GAS_ENTRY:
                        parentView.findViewById(R.id.input_gallons).setVisibility(View.VISIBLE);
                        parentView.findViewById(R.id.input_price).setVisibility(View.VISIBLE);
                        parentView.findViewById(R.id.input_service_type).setVisibility(View.GONE);
                        break;
                    case TYPE_SERVICE_ENTRY:
                        parentView.findViewById(R.id.input_gallons).setVisibility(View.GONE);
                        parentView.findViewById(R.id.input_price).setVisibility(View.GONE);
                        parentView.findViewById(R.id.input_service_type).setVisibility(View.VISIBLE);
                        break;
                    default:
                        parentView.findViewById(R.id.input_gallons).setVisibility(View.GONE);
                        parentView.findViewById(R.id.input_price).setVisibility(View.GONE);
                        parentView.findViewById(R.id.input_service_type).setVisibility(View.GONE);
                        break;
                }
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }

    }
}

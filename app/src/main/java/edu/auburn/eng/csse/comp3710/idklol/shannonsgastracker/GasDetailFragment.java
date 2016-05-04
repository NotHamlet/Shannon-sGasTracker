package edu.auburn.eng.csse.comp3710.idklol.shannonsgastracker;

import android.content.Context;
import android.net.Uri;
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
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GasDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GasDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GasDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_ENTRY_ID = "entry_id";

    // TODO: Rename and change types of parameters

    private OnFragmentInteractionListener mListener;
    private String mEntryID;
    private LogEntry mEntry;

    public GasDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param entryID Parameter 1.
     * @return A new instance of fragment GasDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GasDetailFragment newInstance(String entryID) {
        GasDetailFragment fragment = new GasDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ENTRY_ID, entryID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mEntryID = getArguments().getString(ARG_ENTRY_ID);
        }

        // Get the entry with the given ID and store it
        VehicleLog log;
        try {
            log = (new XMLArchiver()).loadEntries(getContext());
        } catch (IOException e) {
            log = new VehicleLog();
            e.printStackTrace();
        }

        mEntry = log.getEntryWithID(mEntryID);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Enable display of custom app bar actions
        setHasOptionsMenu(true);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        // TODO Populate views with data from mEntry
        populateView(view);

        return view;
    }

    private void populateView(View view) {
        if (mEntry == null) {
            return;
        }

        TextView odometerTextView = (TextView) view.findViewById(R.id.detailOdometer);
        TextView noteTextView = (TextView) view.findViewById(R.id.detailNote);

        odometerTextView.setText(Double.toString(mEntry.getOdometer()));
        noteTextView.setText(mEntry.getNote());

        if (mEntry instanceof GasEntry) {
            GasEntry gasEntry = (GasEntry)mEntry;
            TextView gallonsTextView = (TextView) view.findViewById(R.id.detailGallons);
            TextView priceTextView = (TextView) view.findViewById(R.id.detailPrice);

            gallonsTextView.setText(Double.toString(gasEntry.getGallons()));
            priceTextView.setText(Double.toString(gasEntry.getPrice()));
            view.findViewById(R.id.detailGallonsContainer).setVisibility(View.VISIBLE);
            view.findViewById(R.id.detailPriceContainer).setVisibility(View.VISIBLE);
            view.findViewById(R.id.detailServiceContainer).setVisibility(View.GONE);
        }
        else if (mEntry instanceof ServiceEntry) {
            ServiceEntry serviceEntry = (ServiceEntry)mEntry;
            TextView gallonsTextView = (TextView) view.findViewById(R.id.detailServiceType);
            gallonsTextView.setText(serviceEntry.getServiceType());
            view.findViewById(R.id.detailGallonsContainer).setVisibility(View.GONE);
            view.findViewById(R.id.detailPriceContainer).setVisibility(View.GONE);
            view.findViewById(R.id.detailServiceContainer).setVisibility(View.VISIBLE);
        }
        else {
            view.findViewById(R.id.detailGallonsContainer).setVisibility(View.GONE);
            view.findViewById(R.id.detailPriceContainer).setVisibility(View.GONE);
            view.findViewById(R.id.detailServiceContainer).setVisibility(View.GONE);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.log_entry_detail, menu);

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
            case R.id.action_delete:
                deleteCurrentEntry();
                mListener.popBackStack();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteCurrentEntry() {
        VehicleLog log;
        try {
            log = new XMLArchiver().loadEntries(getContext());
        } catch (IOException e) {
            log = new VehicleLog();
            e.printStackTrace();
        }

        log.deleteEntryWithID(mEntryID);
        try {
            new XMLArchiver().saveEntries(log, getContext());
        } catch (IOException e) {
            Log.e("XMLTest", "Error saving Log after removing LogEntry");
            e.printStackTrace();
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
    public interface OnFragmentInteractionListener {
        void popBackStack();
    }
}

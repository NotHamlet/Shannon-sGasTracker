package edu.auburn.eng.csse.comp3710.idklol.shannonsgastracker;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.auburn.eng.csse.comp3710.idklol.shannonsgastracker.ServiceEntryListFragment.OnListFragmentInteractionListener;
import edu.auburn.eng.csse.comp3710.idklol.shannonsgastracker.dummy.DummyContent.DummyItem;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ServiceEntryRecyclerViewAdapter extends RecyclerView.Adapter<ServiceEntryRecyclerViewAdapter.ViewHolder> {

    private static final int GAS_ENTRY_VIEWTYPE = 1;
    private final VehicleLog mVehicleLog;
    private final OnListFragmentInteractionListener mListener;

    public ServiceEntryRecyclerViewAdapter(VehicleLog vehicleLog, OnListFragmentInteractionListener listener) {
        mVehicleLog = vehicleLog;
        mListener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        if (mVehicleLog.get(position) instanceof  GasEntry) {
            return GAS_ENTRY_VIEWTYPE;
        }

        return super.getItemViewType(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case GAS_ENTRY_VIEWTYPE:
                view = LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.fragment_gasentry, parent, false);
                return new ViewHolder(view);
            default:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.fragment_serviceentry, parent, false);
                return new ViewHolder(view);

        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mVehicleLog.get(position);
        View holderView = holder.mView;
        int viewType = getItemViewType(position);
        Resources res = holder.mView.getContext().getResources();

        String odometerText;
        LogEntry entry = holder.mItem;
        switch (viewType) {
            case GAS_ENTRY_VIEWTYPE:
                GasEntry gasEntry = (GasEntry)(entry);

                odometerText = String.format(res.getString(R.string.list_item_odometer_label), gasEntry.getOdometer());
                ((TextView)(holderView.findViewById(R.id.odometer))).setText(String.valueOf(odometerText));

                String gallonsText = String.format(res.getString(R.string.list_item_gallons_label), gasEntry.getGallons());
                ((TextView)(holderView.findViewById(R.id.gallons))).setText(String.valueOf(gallonsText));

                String priceText = String.format(res.getString(R.string.list_item_gas_price_label), gasEntry.getPrice());
                ((TextView)(holderView.findViewById(R.id.price))).setText(String.valueOf(priceText));

                break;
            default:
                odometerText = String.format(res.getString(R.string.list_item_odometer_label), entry.getOdometer());
                ((TextView)(holderView.findViewById(R.id.odometer))).setText(String.valueOf(odometerText));

                String dateText = (new SimpleDateFormat("MM/dd/yyyy")).format(entry.getDate());
                ((TextView)(holderView.findViewById(R.id.date))).setText(String.valueOf(dateText));

                break;
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mVehicleLog.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public LogEntry mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mItem.toString() + "'";
        }
    }
}

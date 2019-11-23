package com.alastor.vehiclereport.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alastor.vehiclereport.R;
import com.alastor.vehiclereport.repository.roomdatabase.entity.Category;
import com.alastor.vehiclereport.repository.roomdatabase.entity.Report;

import java.util.ArrayList;
import java.util.List;

public class ReportsAdapter extends RecyclerView.Adapter<ReportsAdapter.ViewHolder> {

    private List<Report> mReports = new ArrayList<>();
    private OnReportListener mOnReportListener;

    public ReportsAdapter(OnReportListener onReportListener) {
        this.mOnReportListener = onReportListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_report, parent, false);
        return new ViewHolder(view, mOnReportListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Report report = mReports.get(position);
        holder.bindView(report);
    }

    @Override
    public int getItemCount() {
        return mReports.size();
    }

    public void setReports(List<Report> freshReports) {
        mReports.clear();
        mReports.addAll(freshReports);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mExecutionDate;
        private View mSeparator;
        private TextView mCost;

        private TextView mTitle;
        private TextView mDescription;

        private OnReportListener mOnReportListener;

        public ViewHolder(@NonNull View itemView, OnReportListener onReportListener) {
            super(itemView);
            mOnReportListener = onReportListener;

            mExecutionDate = itemView.findViewById(R.id.text_execution_date);
            mSeparator = itemView.findViewById(R.id.view_execution_date_cost_separator);
            mCost = itemView.findViewById(R.id.text_cost);
            mTitle = itemView.findViewById(R.id.text_title);
            mDescription = itemView.findViewById(R.id.text_description);
        }

        void bindView(Report report) {
            mExecutionDate.setText("" + report.getExecutionTimestamp());

            final float cost = report.getCost();
            if (cost > 0f) {
                mSeparator.setVisibility(View.VISIBLE);
                mCost.setVisibility(View.VISIBLE);
                mCost.setText("" + report.getCost());
            }

            mTitle.setText(report.getTitle());
            mDescription.setText(report.getDescription());

        }
    }

    public interface OnReportListener {
        void onReportClick(long reportId);
    }
}

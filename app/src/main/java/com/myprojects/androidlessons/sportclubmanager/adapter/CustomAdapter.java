package com.myprojects.androidlessons.sportclubmanager.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.myprojects.androidlessons.sportclubmanager.R;
import com.myprojects.androidlessons.sportclubmanager.model.Member;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ItemViewHolder> implements Filterable {

    private Context mContext;
    private List<Member> mMembers;
    private List<Member> mMembersFull;

    private LayoutInflater mInflater;
    private OnItemClick mListener;


    public CustomAdapter(Context context, List<Member> members) {
        mContext = context;
        mMembers = members;
        mMembersFull = new ArrayList<>(mMembers);
        mInflater = LayoutInflater.from(context);

    }

    public List<Member> getMemberList() {
        return mMembers;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layoutr, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {
        holder.setup(mMembers.get(position));

    }

    @Override
    public int getItemCount() {
        return mMembers.size();
    }

    @Override
    public Filter getFilter() {
        return memberFilter;
    }

    private Filter memberFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Member> filteredList = new ArrayList<>();
            if(constraint == null || constraint.length() == 0){
                filteredList.addAll(mMembersFull);
            }else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for(Member member: mMembersFull){
                    if(member.getMemberName().toLowerCase().contains(filterPattern) ||
                            member.getMemberSurname().toLowerCase().contains(filterPattern)){
                        filteredList.add(member);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mMembers.clear();
            mMembers.addAll((List)results.values);
            notifyDataSetChanged();

        }
    };

    public void setOnItemClickListener(CustomAdapter.OnItemClick listener) {
        mListener = listener;
    }

    public interface OnItemClick {
        void onClick(Member member);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_row_name) TextView txtName;
        @BindView(R.id.txt_row_surname) TextView txtSurname;
        @BindView(R.id.txt_row_payment) TextView txtPaymentDate;
        @BindView(R.id.card_view) CardView mCardView;

        ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick
        void onClick() {
            if (mListener != null) mListener.onClick(mMembers.get(getAdapterPosition()));
        }

        void setup(Member member) {
            txtName.setText(member.getMemberName());
            txtSurname.setText(member.getMemberSurname());
            txtPaymentDate.setText(member.getMemberPaymentDate());

            int validPayment = sortByValidPayment(member);

            int orange = Color.parseColor("#EF6C00");
            int green = Color.parseColor("#2E7D32");
            int red = Color.parseColor("#DD2C00");

            if (validPayment == 0) {
                txtPaymentDate.setTextColor(red);
            }
            if (validPayment == 2) {
                txtPaymentDate.setTextColor(green);
            }
            if (validPayment == 1) {
                txtPaymentDate.setTextColor(orange);
            }

        }

        int sortByValidPayment(Member member) {

            Locale currentLocale = mContext.getResources().getConfiguration().locale;
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", currentLocale);
            Calendar calendarAfterMont = Calendar.getInstance();
            Calendar calendarAfterMontMinus3days = Calendar.getInstance();
            Date today = new Date();

            String memberPayment = member.getMemberPaymentDate();

            int payment = 0;

            try {
                if (!memberPayment.equals("")) {
                    Date memberPaymentDate = dateFormat.parse(memberPayment);
                    calendarAfterMont.setTime(memberPaymentDate);
                    calendarAfterMontMinus3days.setTime(memberPaymentDate);

                    calendarAfterMont.add(Calendar.MONTH, 1);

                    calendarAfterMontMinus3days.add(Calendar.MONTH, 1);
                    calendarAfterMontMinus3days.add(Calendar.DATE, -3);

                    Date memberValidPaymentUntilDate = calendarAfterMont.getTime();
                    Date memberValidPaymentUntilDateMinus3days = calendarAfterMontMinus3days.getTime();

                    if (memberValidPaymentUntilDateMinus3days.before(today) && memberValidPaymentUntilDate.after(today)) {
                        return 1;
                    }
                    if (memberValidPaymentUntilDate.after(today)) {
                        return 2;
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return payment;
        }
    }
}

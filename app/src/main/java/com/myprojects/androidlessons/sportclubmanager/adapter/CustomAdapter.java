package com.myprojects.androidlessons.sportclubmanager.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.myprojects.androidlessons.sportclubmanager.R;
import com.myprojects.androidlessons.sportclubmanager.model.Member;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ItemViewHolder> {

    private Context mContext;
    private List<Member> mMembers;
    private LayoutInflater mInflater;
    private OnItemClick mListener;


    public CustomAdapter(Context context, List<Member> members) {
        mContext = context;
        mMembers = members;
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

    public void setOnItemClickListener(CustomAdapter.OnItemClick listener) {
        mListener = listener;
    }

    public interface OnItemClick {
        void onClick(Member member);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_row_name) TextView name;
        @BindView(R.id.txt_row_surname) TextView surname;
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
            name.setText(member.getMemberName());
            surname.setText(member.getMemberSurname());

            int validPayment = sortByValidPayment(member);

            int yellow = Color.parseColor("#FFF9C4");
            int green = Color.parseColor("#C8E6C9");
            int red = Color.parseColor("#FFCDD2");

            if (validPayment == 0) {
                mCardView.setCardBackgroundColor(red);
            }
            if (validPayment == 2) {
                mCardView.setCardBackgroundColor(green);
            }
            if (validPayment == 1) {
                mCardView.setCardBackgroundColor(yellow);
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

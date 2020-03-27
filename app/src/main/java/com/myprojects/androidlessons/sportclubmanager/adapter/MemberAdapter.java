package com.myprojects.androidlessons.sportclubmanager.adapter;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
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

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.ItemViewHolder> {

    private Context mContext;
    private List<Member> mMembers;
    private LayoutInflater mInflater;
    private OnItemClick mListener;

    public MemberAdapter(Context context, List<Member> members) {
        mContext = context;
        mMembers = members;
        mInflater = LayoutInflater.from(context);
    }

    public List<Member> getMemberList() {
        return mMembers;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(mInflater.inflate(R.layout.row_member, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.setup(mMembers.get(position));
    }

    @Override
    public int getItemCount() {
        return mMembers.size();
    }

    public void setOnItemClickListener(OnItemClick listener) {
        mListener = listener;
    }

    public interface OnItemClick {
        void onClick(Member member);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name) TextView mName;
        @BindView(R.id.tv_surname) TextView mSurname;
        @BindView(R.id.iv_photo) ImageView mImageView;


        ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick
        void onClick() {
            if (mListener != null) mListener.onClick(mMembers.get(getAdapterPosition()));
        }

        void setup(Member member) {
            mName.setText(member.getMemberName());
            mSurname.setText(member.getMemberSurname());

            try {
                boolean isValidPayment = isValidPayment(member);
                if(!isValidPayment){
                    mImageView.setImageResource(R.drawable.ic_error_red);
                }if(isValidPayment){
                    mImageView.setImageResource(R.drawable.ic_done);
                }



            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        boolean isValidPayment(Member member) throws ParseException {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.UK);
            Calendar calendar = Calendar.getInstance();
            Date today = new Date();


            if (!member.getMemberPaymentDate().equals("")) {

                String memberPaymentDateString = member.getMemberPaymentDate();
                Date memberPaymentDate = dateFormat.parse(memberPaymentDateString);
                calendar.setTime(memberPaymentDate);
                calendar.add(Calendar.MONTH, 1);
                Date memberValidPayment = calendar.getTime();

                return !memberValidPayment.before(today);
            }
            return !member.getMemberPaymentDate().equals("");
        }
    }
}

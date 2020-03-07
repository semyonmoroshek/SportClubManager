package com.myprojects.androidlessons.sportclubmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myprojects.androidlessons.sportclubmanager.R;
import com.myprojects.androidlessons.sportclubmanager.model.Member;

import java.util.List;

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

        @BindView(R.id.tv_name)
        TextView mName;

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

//            Glide.with(mContext).load(employee.getPhoto()).into(mPhoto);
        }
    }
}

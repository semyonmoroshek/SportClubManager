package com.myprojects.androidlessons.sportclubmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.myprojects.androidlessons.sportclubmanager.R;
import com.myprojects.androidlessons.sportclubmanager.model.Member;

import java.util.List;

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
    public ItemViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
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
        @BindView(R.id.txt_row_name)
        TextView name;
        @BindView(R.id.txt_row_surname)
        TextView surname;

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
        }

    }
}

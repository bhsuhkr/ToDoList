package com.example.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.ViewHolder>{

    private ArrayList<MainModel> mainModelArrayList;
    private Context context;
    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;

    public MainRecyclerAdapter(Context context,ArrayList<MainModel> mainModelArrayList) {
        this.context = context;
        this.mainModelArrayList = mainModelArrayList;
    }

    @Override
    public MainRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_main_adapter, viewGroup, false);
        return new MainRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MainRecyclerAdapter.ViewHolder viewHolder, int position) {
        final MainModel offersListModel = mainModelArrayList.get(position);
        viewHolder.rowMainText.setText(offersListModel.getOfferName());
        viewHolder.rowMainImage.setImageResource(offersListModel.getOfferIcon());
        viewHolder.rowMainParentLinearLayout.setTag(offersListModel);
    }

    @Override
    public int getItemCount() {
        return mainModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView rowMainImage;
        private TextView rowMainText;
        private LinearLayout rowMainParentLinearLayout;

        public ViewHolder(View view) {
            super(view);
            rowMainImage = view.findViewById(R.id.row_main_adapter_iv);
            rowMainText = view.findViewById(R.id.row_main_adapter_tv);
            rowMainParentLinearLayout =  view.findViewById(R.id.row_main_adapter_linear_layout);
            rowMainParentLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onRecyclerViewItemClickListener != null) {
                        onRecyclerViewItemClickListener.onItemClick(getAdapterPosition(), view);
                    }
                }
            });
        }
    }

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }
}
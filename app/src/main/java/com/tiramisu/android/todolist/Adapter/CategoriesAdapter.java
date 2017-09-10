
package com.tiramisu.android.todolist.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tiramisu.android.todolist.Model.Category;
import com.tiramisu.android.todolist.R;
import com.tiramisu.android.todolist.Tasks;

import java.util.List;


public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.MyView> {
    private List<Category> categoryList;
    public Context mContext1;


    public CategoriesAdapter(Context mContext, List<Category> categorylist) {
        this.categoryList = categorylist;
        this.mContext1 = mContext;
    }

    @Override
    public CategoriesAdapter.MyView onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(mContext1).inflate(R.layout.custom_categories, parent,false);

        return new MyView(itemView);
    }

    @Override
    public void onBindViewHolder(CategoriesAdapter.MyView holder, final int position) {

        // Log.d("wtf",categoryList.get(position).getCategoryName());

        holder.mTextView.setText(categoryList.get(position).getCategoryName());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext1,Tasks.class);


                Long cID=categoryList.get(position).getId();
                intent.putExtra("id",(cID));

                mContext1.startActivity(intent);


            }
        });
    }

    @Override
    public int getItemCount() {

        Log.d("hellosham",String.valueOf(categoryList.size()));
        return categoryList.size();

    }

    public static class MyView extends RecyclerView.ViewHolder {

        public TextView mTextView;
        public RelativeLayout mLinearLayout;
        public CardView cardView;

        public MyView(View itemView) {

            super(itemView);
            cardView=(CardView)itemView.findViewById(R.id.cardview);
            mTextView = (TextView) itemView.findViewById(R.id.text);
            mLinearLayout = (RelativeLayout) itemView.findViewById(R.id.ll);
        }
    }
}

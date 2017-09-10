package com.tiramisu.android.todolist.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tiramisu.android.todolist.R;

import java.util.ArrayList;

/**
 * Created by Siddhant on 8/11/2017.
 */

public class CategorySearchAdapter extends RecyclerView.Adapter<CategorySearchAdapter.MyViewHolder> {

    private ArrayList<String> list_item ;
    public Context mcontext;

    public CategorySearchAdapter(ArrayList<String> list, Context context){
        list_item =list;
        mcontext = context;
    }

    @Override
    public CategorySearchAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.list_item, null);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final CategorySearchAdapter.MyViewHolder viewHolder, final int position) {

        viewHolder.category_name.setText(list_item.get(position));
        viewHolder.category_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(mcontext, ""+list_item.get(position),Toast.LENGTH_LONG).show();

                EditText txtView = (EditText) ((Activity)mcontext).findViewById(R.id.search_category);
                txtView.setText(list_item.get(position));

            }
        });
    }



    @Override
    public int getItemCount() {
        return list_item.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView category_name;
        public RelativeLayout categrory_search_layout;

        public MyViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            category_name = (TextView) itemLayoutView.findViewById(R.id.category_name);
            categrory_search_layout = (RelativeLayout)itemLayoutView.findViewById(R.id.layout_search_category);


        }
    }

}

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

import com.tiramisu.android.todolist.Model.TaskSuggestionModel;
import com.tiramisu.android.todolist.R;

import java.util.ArrayList;


public class TaskSearchAdapter extends RecyclerView.Adapter<TaskSearchAdapter.MyView> {
    private ArrayList<TaskSuggestionModel> items ;
    Context mcontext;
    public TaskSearchAdapter(ArrayList<TaskSuggestionModel> list, Context context) {

        items=list;
        mcontext = context;
    }

    @Override
    public TaskSearchAdapter.MyView onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.items,parent,false);


        return new MyView(layoutView);

    }

    @Override
    public void onBindViewHolder(MyView holder, final int position) {
        holder.category_name.setText(items.get(position).getSuggestion());
        holder.category_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(mcontext, ""+items.get(position),Toast.LENGTH_LONG).show();

              //  EditText txt = (EditText) ((Activity)mcontext).findViewById(R.id.addtask);
               // txt.setText(items.get(position).getSuggestion());

            }
        });



    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyView extends RecyclerView.ViewHolder {
        RelativeLayout categrory_search_layout;
        public TextView category_name;
        public MyView(View itemView) {

            super(itemView);
            category_name = (TextView) itemView.findViewById(R.id.category_name);

            categrory_search_layout = (RelativeLayout)itemView.findViewById(R.id.layout_search_category);
        }
    }
}

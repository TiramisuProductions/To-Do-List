package com.tiramisu.android.todolist.Adapter;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.tiramisu.android.todolist.Model.GoalsModel;
import com.tiramisu.android.todolist.R;

import java.util.ArrayList;
import java.util.List;


public class GoalsAdapter extends RecyclerView.Adapter<GoalsAdapter.MyView> {
    public List<GoalsModel> goalsList=new ArrayList<>();
    public Activity activity;
    public DatabaseReference toDoRef,goalsRef;



    public GoalsAdapter(FragmentActivity activity, List<GoalsModel> goalsList) {
        this.activity=activity;
        this.goalsList=goalsList;

    }

    @Override
    public MyView onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView= LayoutInflater.from(activity).inflate(R.layout.custom_goals, parent,false);

        return new MyView(itemView);


    }

    @Override
    public void onBindViewHolder(MyView holder, int position) {

        GoalsModel goals=goalsList.get(position);

        holder.mText.setText(goals.getName());


    }

    @Override
    public int getItemCount() {
        Log.d("yoyo",""+goalsList.size());
        return goalsList.size();
    }

    public class MyView extends RecyclerView.ViewHolder {
        TextView mText;
        CardView cardView;


        public MyView(View itemView) {
            super(itemView);
            mText=(TextView)itemView.findViewById(R.id.goalsText);
            cardView=(CardView)itemView.findViewById(R.id.cardView);
        }
    }
}

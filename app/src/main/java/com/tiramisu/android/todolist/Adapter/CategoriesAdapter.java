
package com.tiramisu.android.todolist.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tiramisu.android.todolist.AllTasks;
import com.tiramisu.android.todolist.Model.CategoryModel;
import com.tiramisu.android.todolist.Model.StaticVar;
import com.tiramisu.android.todolist.R;
import com.tiramisu.android.todolist.Tasks;

import java.util.List;

import static android.support.design.R.styleable.RecyclerView;


public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.MyView> {
    private List<CategoryModel> categoryList;
    public Activity activity;
    DatabaseReference todoref,categoryref;


    public CategoriesAdapter(Activity activity, List<CategoryModel> categorylist) {
        this.categoryList = categorylist;
        this.activity = activity;
    }

    @Override
    public CategoriesAdapter.MyView onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(activity).inflate(R.layout.custom_categories, parent,false);

        return new MyView(itemView);
    }

    @Override
    public void onBindViewHolder(final CategoriesAdapter.MyView holder, final int position) {



        holder.mTextView.setText(categoryList.get(position).getName());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(categoryList.get(position).getId().equals("ALL"))
                {
                    Intent intent=new Intent(activity,AllTasks.class);

                    intent.putExtra("category_name",categoryList.get(position).getName());
                    intent.putExtra("category_id",categoryList.get(position).getId());
                    ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,holder.mTextView,"categoryname");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        activity.startActivity(intent,activityOptionsCompat.toBundle());
                    }
                    else{
                        activity.startActivity(intent);
                    }
                }
                else{
                    Intent intent=new Intent(activity,Tasks.class);
                    todoref = FirebaseDatabase.getInstance().getReference("Todo");


                    intent.putExtra("category_name",categoryList.get(position).getName());
                    intent.putExtra("category_id",categoryList.get(position).getId());
                    ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,holder.mTextView,"categoryname");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        activity.startActivity(intent,activityOptionsCompat.toBundle());
                    }
                    else{
                        activity.startActivity(intent);
                    }
                }











            }
        });
    }

   


    @Override
    public int getItemCount() {


        return categoryList.size();

    }

    public static class MyView extends RecyclerView.ViewHolder {

        public TextView mTextView;
        public RelativeLayout mLinearLayout;
        public CardView cardView;

        public MyView(View itemView) {

            super(itemView);
            cardView=(CardView)itemView.findViewById(R.id.cardview);
            mTextView = (TextView) itemView.findViewById(R.id.category_name);

            mLinearLayout = (RelativeLayout) itemView.findViewById(R.id.ll);
        }
    }
}

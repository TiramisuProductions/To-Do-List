
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
import com.tiramisu.android.todolist.TAGS;
import com.tiramisu.android.todolist.Tasks;

import java.util.List;

import static android.support.design.R.styleable.RecyclerView;


public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.MyView> {
    private List<CategoryModel> categoryList;
    public Activity activity;


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



       holder.mTextView.setText(categoryList.get(position).getCategoryName());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
Intent i = new Intent(activity,Tasks.class);
i.putExtra(TAGS.CATEGORYID,categoryList.get(position).getCategoryId());
i.putExtra(TAGS.CATEGORYNAME,categoryList.get(position).getCategoryName());
activity.startActivity(i);
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

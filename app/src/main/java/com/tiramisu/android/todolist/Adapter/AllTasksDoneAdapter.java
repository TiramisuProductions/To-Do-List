package com.tiramisu.android.todolist.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tiramisu.android.todolist.Model.AllTasksModel;
import com.tiramisu.android.todolist.Model.StaticVar;
import com.tiramisu.android.todolist.Model.TaskModel;
import com.tiramisu.android.todolist.R;
import com.tiramisu.android.todolist.Tasks;

import java.util.List;

/**
 * Created by Sarvesh Palav on 26-09-2017.
 */

public class AllTasksDoneAdapter extends RecyclerView.Adapter<AllTasksDoneAdapter.MyView> {
    private Context context;
    private List<AllTasksModel> list;
    private Tasks tasks;
    DatabaseReference todoref,categoryref;

    public AllTasksDoneAdapter(Context context , List<AllTasksModel> list) {
        this.list=list;
        this.context=context;
    }
    @Override
    public AllTasksDoneAdapter.MyView onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_task,parent,false);

        return new AllTasksDoneAdapter.MyView(layoutView);
    }

    @Override
    public void onBindViewHolder(final AllTasksDoneAdapter.MyView holder, final int position) {
        final AllTasksModel list1 =list.get(position);



        holder.checkBox.setChecked(true);
        holder.text.setText(list1.getTaskName());
        holder.text.setPaintFlags(holder.text.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                todoref = FirebaseDatabase.getInstance().getReference("Todo");
                categoryref = todoref.child(""+ StaticVar.UID+"/Categories");
                categoryref.child(list1.getCatId()).child("Tasks").child(list1.getTaskId()).removeValue();
                list.remove(position);
                notifyDataSetChanged();
            }
        });


        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(!isChecked)
                {
                    todoref = FirebaseDatabase.getInstance().getReference("Todo");
                    categoryref = todoref.child(""+StaticVar.UID+"/Categories");
                    Log.d("CategoryID",list1.getCatId());
                    categoryref.child(list1.getCatId()).child("Tasks").child(list1.getTaskId()).child("done").setValue("false");
                    notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.d("oneplusdone",""+list.size());
        return list.size();
    }

    public class MyView extends RecyclerView.ViewHolder {
        public CheckBox checkBox;
        public ImageView delete;
        public TextView text;

        public MyView(View itemView) {
            super(itemView);
            checkBox=(CheckBox)itemView.findViewById(R.id.checkbox);
            text=(TextView)itemView.findViewById(R.id.taskName);
            delete = (ImageView)itemView.findViewById(R.id.del);
        }
    }
}

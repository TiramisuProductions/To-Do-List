package com.tiramisu.android.todolist.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tiramisu.android.todolist.Model.StaticVar;
import com.tiramisu.android.todolist.Model.TaskModel;
import com.tiramisu.android.todolist.R;
import com.tiramisu.android.todolist.Tasks;

import java.util.List;

//Adapter for recycler view for task which are checked
public class TaskDoneAdapter extends RecyclerView.Adapter<TaskDoneAdapter.MyView> {
    private Context context;
    private List<TaskModel> list;
    private Tasks tasks;
    DatabaseReference todoref,categoryref;

    public TaskDoneAdapter(Context context , List<TaskModel> list) {
        this.list=list;
        this.context=context;
    }
    @Override
    public TaskDoneAdapter.MyView onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_task,parent,false);

        return new MyView(layoutView);
    }

    @Override
    public void onBindViewHolder(final TaskDoneAdapter.MyView holder, final int position) {
        final TaskModel list1 =list.get(position);
        holder.checkBox.setChecked(true);
        holder.text.setText(list1.getTaskName());
        holder.text.setPaintFlags(holder.text.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        //deletes the done task
        holder.delete.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        todoref = FirebaseDatabase.getInstance().getReference("Todo");
        categoryref = todoref.child(""+StaticVar.UID+"/Categories");
        categoryref.child(StaticVar.CATEGORY_ID).child("Tasks").child(list1.getId()).removeValue();
        list.remove(position);
        notifyDataSetChanged();
    }
});
        //Checks if task is checked or not
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(!isChecked)
                {
                    todoref = FirebaseDatabase.getInstance().getReference("Todo");
                    categoryref = todoref.child(""+StaticVar.UID+"/Categories");
                    categoryref.child(StaticVar.CATEGORY_ID).child("Tasks").child(list1.getId()).child("done").setValue("false");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.d("oneplus3",""+list.size());
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

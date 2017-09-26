package com.tiramisu.android.todolist.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tiramisu.android.todolist.Model.StaticVar;
import com.tiramisu.android.todolist.Model.TaskModel;
import com.tiramisu.android.todolist.R;
import com.tiramisu.android.todolist.Tasks;

import java.util.List;



public class TaskNotDoneAdapter extends RecyclerView.Adapter<TaskNotDoneAdapter.MyView> {

    private Context context;
    private  List<TaskModel> list;
    private Tasks tasks;
    private int counter;
    DatabaseReference todoref,categoryref;

    public TaskNotDoneAdapter(Context context , List<TaskModel> list) {
        this.context=context;
        this.list=list;
    }

    @Override
    public TaskNotDoneAdapter.MyView onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_task,parent,false);


        return new MyView(layoutView);
    }

    @Override
    public void onBindViewHolder(final TaskNotDoneAdapter.MyView holder, final int position) {
        final TaskModel list1 =list.get(position);
//        Log.d("name",list1.getText());







//

        holder.checkBox.setChecked(false);


        holder.text.setText(list1.getTaskName());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // notifyItemChanged(position);
                // notifyItemRangeChanged(position,list.size());
                todoref = FirebaseDatabase.getInstance().getReference("Todo");
                categoryref = todoref.child(""+StaticVar.UID+"/Categories");
                categoryref.child(StaticVar.CATEGORY_ID).child("Tasks").child(list1.getId()).removeValue();
                list.remove(position);
                notifyDataSetChanged();
            }
        });







        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {

                    todoref = FirebaseDatabase.getInstance().getReference("Todo");
                    categoryref = todoref.child(""+StaticVar.UID+"/Categories");
                    categoryref.child(StaticVar.CATEGORY_ID).child("Tasks").child(list1.getId()).child("done").setValue("true");



                }
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.d("oneplus",""+list.size());

        return list.size();
    }

    public class MyView extends RecyclerView.ViewHolder {
        public TextView text,dataID;
        public ImageView delete,dateTime;
        public RelativeLayout relativeLayout;
        public CheckBox checkBox;

        public MyView(View itemView) {
            super(itemView);
            this.setIsRecyclable(false);


            delete=(ImageView)itemView.findViewById(R.id.del);
            text=(TextView)itemView.findViewById(R.id.taskName);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkbox);
            relativeLayout=(RelativeLayout)itemView.findViewById(R.id.custom_ll);
        }

    }
}

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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tiramisu.android.todolist.Model.Task;
import com.tiramisu.android.todolist.R;
import com.tiramisu.android.todolist.Tasks;
import com.tiramisu.android.todolist.Model.WorldEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;



public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyView> {

    private Context context;
    private  List<Task> list;
    private Tasks tasks;
    private int counter;

    public TaskAdapter(Context context , List<Task> list) {
        this.context=context;
        this.list=list;
    }

    @Override
    public TaskAdapter.MyView onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_task,parent,false);


        return new MyView(layoutView);
    }

    @Override
    public void onBindViewHolder(final TaskAdapter.MyView holder,final int position) {
        final Task list1 =list.get(position);
//        Log.d("name",list1.getText());







//

        holder.checkBox.setChecked(false);


        holder.text.setText(list1.taskname);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("shammu position",String.valueOf(position));
                Task list1 =list.get(position);
                list.remove(position);
                notifyDataSetChanged();
                list1.delete();
                // notifyItemChanged(position);
                // notifyItemRangeChanged(position,list.size());
            }
        });







        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {

                    //list1.isselected=true;
                    Task task = Task.findById(Task.class, list1.getId());
                    task.setIsdone(true);
                    task.save();
                    holder.text.setPaintFlags(holder.text.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    list.remove(position);
                    notifyDataSetChanged();

                    Toast.makeText(context,""+list1.getId(),Toast.LENGTH_LONG).show();
                    EventBus.getDefault().post(new WorldEvent("Hello EventBus!"));

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
        public ImageButton delete,dateTime;
        public RelativeLayout relativeLayout;
        public CheckBox checkBox;

        public MyView(View itemView) {
            super(itemView);
            this.setIsRecyclable(false);


            delete=(ImageButton)itemView.findViewById(R.id.del);
            text=(TextView)itemView.findViewById(R.id.taskName);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkbox);
            relativeLayout=(RelativeLayout)itemView.findViewById(R.id.custom_ll);
        }

    }
}

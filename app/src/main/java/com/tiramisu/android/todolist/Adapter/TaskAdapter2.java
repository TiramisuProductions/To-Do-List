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
import android.widget.TextView;
import com.tiramisu.android.todolist.Model.Task;
import com.tiramisu.android.todolist.R;
import com.tiramisu.android.todolist.Tasks;
import com.tiramisu.android.todolist.Model.WorldEvent;
import org.greenrobot.eventbus.EventBus;
import java.util.List;

public class TaskAdapter2 extends RecyclerView.Adapter<TaskAdapter2.MyView> {
    private Context context;
    private List<Task> list;
    private Tasks tasks;

    public TaskAdapter2(Context context , List<Task> list) {
        this.list=list;
        this.context=context;
    }
    @Override
    public TaskAdapter2.MyView onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_task,parent,false);

        return new MyView(layoutView);
    }

    @Override
    public void onBindViewHolder(final TaskAdapter2.MyView holder, final int position) {
        final Task list1 =list.get(position);



        holder.checkBox.setChecked(true);
        holder.text.setText(list1.taskname);
        holder.text.setPaintFlags(holder.text.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(!isChecked)
                {
                    Task task = Task.findById(Task.class, list1.getId());
                    task.setIsdone(false);
                    task.save();
                    EventBus.getDefault().post(new WorldEvent("Hello EventBus!"));
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
        public TextView dataID;
        public TextView text;

        public MyView(View itemView) {
            super(itemView);
            checkBox=(CheckBox)itemView.findViewById(R.id.checkbox);
            text=(TextView)itemView.findViewById(R.id.taskName);
        }
    }
}

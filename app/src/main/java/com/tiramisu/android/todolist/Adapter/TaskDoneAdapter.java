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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tiramisu.android.todolist.Model.StaticVar;
import com.tiramisu.android.todolist.Model.TaskModel;
import com.tiramisu.android.todolist.R;
import com.tiramisu.android.todolist.TAGS;
import com.tiramisu.android.todolist.Tasks;

import java.util.List;

//Adapter for recycler view for task which are checked
public class TaskDoneAdapter extends RecyclerView.Adapter<TaskDoneAdapter.MyView> {
    private Context context;
    private List<TaskModel> list;
    private Tasks tasks;
    private FirebaseAuth firebaseAuth;
    private String categoryId;
    FirebaseFirestore db ;

    public TaskDoneAdapter(Context context , List<TaskModel> list,String categoryId) {
        this.list=list;
        this.context=context;
        this.categoryId = categoryId;
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

    }
    @Override
    public TaskDoneAdapter.MyView onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_task,parent,false);

        return new MyView(layoutView);
    }

    @Override
    public void onBindViewHolder(final TaskDoneAdapter.MyView holder, final int position) {
        final TaskModel task =list.get(position);
        holder.checkBox.setChecked(true);
        holder.text.setText(task.getTaskName());
        holder.text.setPaintFlags(holder.text.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        //deletes the done task
        holder.delete.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
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
                    DocumentReference ref = db.collection(TAGS.TODO).document(firebaseAuth.getUid()).collection(TAGS.CATEGORIES).document(categoryId).collection(TAGS.TASKS).document(task.getTaskId());
                    ref.update(TAGS.TASKDONE,false).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("done","done");
                        }});
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

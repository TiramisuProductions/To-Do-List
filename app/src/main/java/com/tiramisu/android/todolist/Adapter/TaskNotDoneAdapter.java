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


//Adapter for recycler view for task that are not checked
public class TaskNotDoneAdapter extends RecyclerView.Adapter<TaskNotDoneAdapter.MyView> {

    private Context context;
    private  List<TaskModel> list;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth;
    private String categoryId;

    public TaskNotDoneAdapter(Context context , List<TaskModel> list,String categoryId) {
        this.context=context;
        this.list=list;
        this.categoryId = categoryId;
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public TaskNotDoneAdapter.MyView onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_task,parent,false);


        return new MyView(layoutView);
    }

    @Override
    public void onBindViewHolder(final TaskNotDoneAdapter.MyView holder, final int position) {
        final TaskModel task =list.get(position);
        holder.checkBox.setChecked(false);
        holder.text.setText(task.getTaskName());

        //Delete button deletes the task
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.remove(position);
                notifyDataSetChanged();
            }
        });

        //Checks if task is checked or not
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    Log.d("check",task.getTaskId());
                    DocumentReference ref = db.collection(TAGS.TODO).document(firebaseAuth.getUid()).collection(TAGS.CATEGORIES).document(categoryId).collection(TAGS.TASKS).document(task.getTaskId());
                    ref.update(TAGS.TASKDONE,true).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                       Log.d("done","done");
                        }
                    });





                }
            }
        });
    }

    @Override
    public int getItemCount() {
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

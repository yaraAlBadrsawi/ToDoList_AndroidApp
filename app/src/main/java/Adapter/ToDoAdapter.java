package Adapter;

import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist_app.MainActivity;
import com.example.todolist_app.R;

import java.time.temporal.TemporalAccessor;
import java.util.List;

import Model.Task;
import Utils.DataBaseHandler;
import Utils.NewTask;
import Utils.TaskContract;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {
    private List<Task> task;
    private MainActivity activity;
    private DataBaseHandler dataBaseHandler;
    public ToDoAdapter(MainActivity mainActivity,DataBaseHandler dataBaseHandler)
    {
        this.activity=mainActivity;
        this.dataBaseHandler=dataBaseHandler;
    }

    @Override
    public ToDoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_tasks,parent,false));
    }

    @Override
    public void onBindViewHolder(ToDoAdapter.ViewHolder holder, int position) {
    dataBaseHandler.openDataBase();
    final Task item=task.get(position);
    holder.task.setText(item.getTask());
    holder.task.setChecked(toCheckStatus(item.getStatus()));
    holder.task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked)
            {
                dataBaseHandler.updateStatus(item.getId(),1);

            }else
                dataBaseHandler.updateStatus(item.getId(),0);
        }
    });
    }
    private boolean toCheckStatus(int status){return status!=0;}

    @Override
    public int getItemCount() {
        return task.size();
    }

    public Context getContext(){return this.activity;}

    public void setTasks(List<Task> todoList) {
        this.task = todoList;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        Task item = task.get(position);
        dataBaseHandler.deleteTask(item.getId());
        task.remove(position);
        notifyItemRemoved(position);
    }

    public void editItem(int position) {
        Task item = task.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt(TaskContract.TaskEntry.COL_ID, item.getId());
        bundle.putString(TaskContract.TaskEntry.COL_TASK, item.getTask());
        NewTask newTask = new NewTask();
        newTask.setArguments(bundle);
        newTask.show(activity.getSupportFragmentManager(),NewTask.TAG);
    }
     public class ViewHolder extends RecyclerView.ViewHolder
    {
        //TextView title;
        CheckBox task;
        //TextView date;
        public ViewHolder(View itemView) {
            super(itemView);
         //   title=itemView.findViewById(R.id.title);
            task=itemView.findViewById(R.id.task);
           // date=itemView.findViewById(R.id.date);

        }
    }
}


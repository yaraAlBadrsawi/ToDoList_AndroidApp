package com.example.todolist_app;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import Adapter.ToDoAdapter;
import Model.Task;
import Utils.DataBaseHandler;
import Utils.NewTask;

public class MainActivity extends AppCompatActivity implements DialogCloseListener{
    private RecyclerView recyclerView;
    private ToDoAdapter toDoAdapter;
    private List<Task> taskList;
    private FloatingActionButton actionButton;
    private DataBaseHandler dataBaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
     super.onCreate(savedInstanceState);
     setContentView(R.layout.activity_main);

    //    Objects.requireNonNull(getSupportActionBar()).hide();
        this.dataBaseHandler=new DataBaseHandler(this);
        this.dataBaseHandler.openDataBase();
        this.recyclerView = findViewById(R.id.recyclerView);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
       //this.taskList = new ArrayList<>();
        this.toDoAdapter=new ToDoAdapter(MainActivity.this,dataBaseHandler);
       //taskList.add(new Task("new TAsk add ",1));
        this.recyclerView.setAdapter(this.toDoAdapter);

        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new RecyclerItemTouchHelper(toDoAdapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
        actionButton = findViewById(R.id.action_add_task);
        taskList=dataBaseHandler.getAllTasks();
        Collections.reverse(taskList);
        this.toDoAdapter.setTasks(taskList);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewTask.newInstance().show(getSupportFragmentManager(),NewTask.TAG);

            }
        });
    }

    @Override
    public void handleDialogClose(DialogInterface dialogInterface) {
        taskList=dataBaseHandler.getAllTasks();
        Collections.reverse(taskList);
        toDoAdapter.setTasks(taskList);
        toDoAdapter.notifyDataSetChanged();
        //new ToDoAdapter();

    }

}
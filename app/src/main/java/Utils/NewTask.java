package Utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.todolist_app.DialogCloseListener;
import com.example.todolist_app.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Objects;

import Model.Task;

public class NewTask extends BottomSheetDialogFragment {
    public static final String TAG = "ActionBottomDialog";
    private TextView inputTask;
    private TextView new_task_text;
    private EditText dialog_titleTask_edit;
    private EditText dialog_newTask_edit;
    private Button dialog_saveTask_button;
    private Button dialog_cancelTask_button;

    private DataBaseHandler dataBaseHandler;

    public static NewTask newInstance()
    {
        return new NewTask();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_new_task, container, false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.ALPHA_CHANGED);
        getDialog().setCancelable(false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //inflate for view in dialog
        this.new_task_text = getView().findViewById(R.id.newTask);//add new task textView
        this.inputTask = getView().findViewById(R.id.input);// pleas add your task
        //this.dialog_titleTask_edit = getView().findViewById(R.id.title);// title edit text
        //this.dialog_newTask_edit = Objects.requireNonNull(getView()).findViewById(R.id.task);//task edit text
        dialog_newTask_edit = requireView().findViewById(R.id.task);
        this.dialog_saveTask_button = getView().findViewById(R.id.addButton);//add task button
        this.dialog_cancelTask_button = getView().findViewById(R.id.cancelButton);// cancel task button

        boolean isUpdate =false;
        final Bundle bundle = getArguments();
        if (bundle != null) {
            isUpdate = true;
            String task = bundle.getString("task");
            //code
            dialog_newTask_edit.setText(task);
            assert task != null;

            if (task.length() > 0) {
                dialog_newTask_edit.setTextColor(ContextCompat.getColor(requireContext(), R.color.brown));
            }
            dataBaseHandler = new DataBaseHandler(getActivity());
            dataBaseHandler.openDataBase();//open dataBase

           final boolean finalIsUpdate = isUpdate;
            dialog_saveTask_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickSave();

                    String text_task = dialog_newTask_edit.getText().toString();
                    if (finalIsUpdate) {
                        dataBaseHandler.updateTask(bundle.getInt("id"), text_task);
                    } else {
                        Task task1 = new Task();
                        task1.setTask(text_task);
                        task1.setStatus(0);
                        dataBaseHandler.insertTask(task1);
                    }
                    dismiss();
                }

                private void onClickSave() {
                    Toast.makeText(getActivity(),"clicked",Toast.LENGTH_LONG).show();
                }
            });

        }
    }
    @Override
    public void onDismiss(DialogInterface dialog) {
        Activity activity = getActivity();
        if (activity instanceof DialogCloseListener) {
            ((DialogCloseListener)activity).handleDialogClose(dialog);
        }}



}

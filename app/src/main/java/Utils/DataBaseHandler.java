package Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import Model.Task;

public class DataBaseHandler extends SQLiteOpenHelper {
    private SQLiteDatabase sqLiteDatabase;
    public DataBaseHandler(Context context) {
        super(context,TaskContract.DATABASE_NAME, null,TaskContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create database
        db.execSQL("CREATE TABLE "+TaskContract.TaskEntry.TABLE_NAME+" ( " +
                TaskContract.TaskEntry.COL_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TaskContract.TaskEntry.COL_TASK+ " TEXT NOT NULL," +
                TaskContract.TaskEntry.COL_STATUS+ " INTEGER );");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop the older table
        db.execSQL("DROP TABLE IF EXISTS "+ TaskContract.TaskEntry.TABLE_NAME);
        //Create table again
        onCreate(db);
    }

    public void openDataBase()
    {
        sqLiteDatabase=this.getWritableDatabase();//db=getWritable..
    }

    //to add task into database
    public void insertTask(Task task)
    {
        ContentValues contentValues=new ContentValues();//to add all task in it
        contentValues.put(TaskContract.TaskEntry.COL_TASK,task.getTask());
        contentValues.put(TaskContract.TaskEntry.COL_STATUS,task.getStatus());//,0
        //to execute sql query :INSERT
        sqLiteDatabase.insert(TaskContract.TaskEntry.TABLE_NAME,null,contentValues);

    }

    //TO get all task we add in list
    public List<Task> getAllTasks()
    {
        List<Task> taskList=new ArrayList<>();
        Cursor cursor=null;
        sqLiteDatabase.beginTransaction();
        try{
            //to execute Sql :SELECT* FROM TABLE_NAME;
            cursor=sqLiteDatabase.query(TaskContract.TaskEntry.TABLE_NAME,null,null,
                    null,null,null,null,null);
            if (cursor!=null)
            {
                if (cursor.moveToFirst())
                { //looping through all rows adding to list
                    do {
                        Task task = new Task();
                        task.setId(cursor.getInt(cursor.getColumnIndex(TaskContract.TaskEntry.COL_ID)));
                        task.setTask(cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK)));
                        task.setStatus(cursor.getInt(cursor.getColumnIndex(TaskContract.TaskEntry.COL_STATUS)));
                        taskList.add(task);//adding all task to list
                    }while (cursor.moveToNext());
                }
            }
        }finally {
            sqLiteDatabase.endTransaction();
         assert cursor != null;
            //close cursor
            cursor.close();
        }
        //return task List
        return taskList;
    }
    //update the status
    public void updateStatus(int id ,int status)
    {
        ContentValues contentValues=new ContentValues();
        contentValues.put(TaskContract.TaskEntry.COL_STATUS,status);
        sqLiteDatabase.update(TaskContract.TaskEntry.TABLE_NAME,contentValues,
                TaskContract.TaskEntry.COL_ID+"=?",
                new String[]{String.valueOf(id)});
    }
    public void updateTask(int id ,String task)
    {
        ContentValues contentValues=new ContentValues();
        contentValues.put(TaskContract.TaskEntry.COL_TASK,task);
        sqLiteDatabase.update(TaskContract.TaskEntry.TABLE_NAME,contentValues,
                TaskContract.TaskEntry.COL_ID+"=?",
                new String[]{String.valueOf(id)});
    }
    public void deleteTask(int id){
        sqLiteDatabase.delete(TaskContract.TaskEntry.TABLE_NAME,
                TaskContract.TaskEntry.COL_ID + "= ?",
                new String[] {String.valueOf(id)});
    }
}

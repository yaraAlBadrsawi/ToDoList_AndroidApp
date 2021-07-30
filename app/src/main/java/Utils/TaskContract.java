package Utils;

public class TaskContract {
    public static final String DATABASE_NAME="TO_DO_LIST.DB";
    public static final int DATABASE_VERSION=2;

    public class TaskEntry {
        public static final String TABLE_NAME = "TASKS";
        public static final String COL_ID = "id";
        public static final String COL_TASK = "task";
        public static final String COL_STATUS = "status";
    }

}

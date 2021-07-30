package Model;

public class Task {
    public String task;
    public int id,status;

  /*  public Task() {
    }
*/
  /*  public Task(String task, int status) {
        task = task;
        this.status = status;
    }*/

    /*public Task(int id, int status, String task) {
        this.id = id;
        this.status = status;
        task = task;
    }*/

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        task = task;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}

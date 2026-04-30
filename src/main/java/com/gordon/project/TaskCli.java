package com.gordon.project;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

interface Command{
    public void execute() throws IOException;
}

@Author(name = "GordonChao", country = "Taiwan")
class addTask implements Command{
    String[] args;
    Path jsonFile;
    String content;
    private TaskManagement manager;
    public addTask(String[] args, TaskManagement manager, Path jsonFile, String content){ this.args = args; this.manager = manager; this.jsonFile = jsonFile; this.content = content;}
    @Override
    public void execute() throws IOException{
        String desc = args[1];
        
        int nextInt = manager.getNextInt();

        Mapper mapper = new Mapper(nextInt, desc);
        String result = mapper.save();

        Task task = new Task(result, jsonFile, content);

        task.addTasks();
        System.out.println(String.format("Output: Task added successfully (ID: %d)", nextInt));
    }
}
class updateTask implements Command{
    String[] args;
    private TaskManagement manager;
    public updateTask(String[] args, TaskManagement manager){ this.args = args; this.manager = manager;}
    @Override
    public void execute() throws IOException, NumberFormatException {
        int taskId = Integer.parseInt(args[1]);
        String newTask = args[2];
        manager.updateTask(taskId, newTask);
    }
}
//mark-todo <id>
class trackStatus implements Command{
    String action;
    String[] args;
    TaskManagement manager;
    public trackStatus(String[] args, String action, TaskManagement manager){
        this.action = action;
        this.args = args;
        this.manager = manager;
    }
    @Override public void execute() throws IOException{
        int id = Integer.parseInt(args[1]);
        manager.trackStatus(action, id);
    }
}
class ListallTasks implements Command{
    String[] args;
    TaskManagement manager;
    public ListallTasks(String[] args, TaskManagement manager){
        this.args = args;
        this.manager = manager;
    }
    @Override public void execute(){
        manager.listAllTasks(args);
    }
}
class ListbyStatus implements Command {
    String[] args;
    TaskManagement manager;
    public ListbyStatus(String[] args, TaskManagement manager){
        this.args = args;
        this.manager = manager;
    }
    @Override public void execute(){
        manager.listAllTasks(args);
    }
}
class deleteTask implements Command{
    String[] args;
    TaskManagement manager;
    public deleteTask(String[] args, TaskManagement manager){
        this.args = args;
        this.manager = manager;
    }
    @Override public void execute() throws IOException{
        int taskId = Integer.parseInt(args[1]);
        manager.deleteTask(taskId);
    }
}
public class TaskCli{
    public static void main(String[] args) throws IOException {
        //add, update, delete...
        if (args.length == 0) { return; }
        
        Path jsonFile = Path.of("Tracker.json");
        
        String action = args[0];
        String content = new String(Files.readAllBytes(jsonFile));
        
        TaskManagement manager = new TaskManagement(jsonFile, content);

        Map<String, Command> map = new HashMap<>();
        map.put("add", new addTask(args, manager, jsonFile, content));
        map.put("update", new updateTask(args, manager));
        map.put("mark-todo", new trackStatus(args, action, manager));
        map.put("mark-in-progress", new trackStatus(args, action, manager));
        map.put("mark-done", new trackStatus(args, action, manager));
        map.put("delete", new deleteTask(args, manager));
        map.put("list", (args.length == 1 && args[0] == "list") ? new ListallTasks(args, manager) : new ListbyStatus(args, manager));

        taskHelper helper = new taskHelper(args);
        Boolean check = helper.check();

        if(!check){
            return;
        }

        Command command = map.get(action);
        if(command==null){
            System.out.println("Unrecognized keyword: " + action);
            return;
        }
        command.execute();

    }
}
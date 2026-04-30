package com.gordon.project;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
class Task{
    String jsonString;
    Path jsonFile;
    String content;
    Task(String jsonString, Path jsonFile, String content) throws IOException{
        this.jsonString = jsonString;
        this.jsonFile = jsonFile;
        this.content = content;
    }
    //[{}, {}, ...]
    public void addTasks() throws IOException{
        content = content.trim();
        String newLine = System.lineSeparator();
        if(content == null || content.trim().isEmpty()){
            content = "[" + newLine + jsonString + newLine + "]";
        }
        else if(content.endsWith("]")){
            content = content.substring(0, content.length() - 1);

            if(content.equals("[")){
                content = "[" + newLine + jsonString + newLine + "]";
            }else{
                content += "," + newLine + jsonString + newLine + "]";
            }
        }
        Files.write(jsonFile, content.getBytes());
    }
}
public class TaskManagement {
    Path jsonFile;
    String content;
    public TaskManagement(Path jsonFile, String content){
        this.jsonFile = jsonFile;
        this.content = content;
    }
    
    public int getNextInt() throws IOException{
        if(content == null || !Files.exists(jsonFile)){
            return 1;
        }
        try{
            Matcher m = Pattern.compile("\"id\":\\s*(\\d+)").matcher(content);
            int maxId = 0;
            while(m.find()){
                int currentId = Integer.parseInt(m.group(1));
                if(currentId > maxId){
                    maxId = currentId;
                }
            }
            return maxId + 1;
        }catch(Exception e){
            e.printStackTrace();
            return 1;
        }
    }
    public void updateTask(int taskId, String newTask) throws IOException, NumberFormatException{
        //id not present -> throws Exception
        //read -> modify -> write back
        String regex = "(\"id\":\\s*" + taskId + ".*?\"description\":\\s*\")([^\"]*)(.*?\"updatedAt\":\\s*\")([^\"]*)(\")";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(content);
        if(m.find()){
            String updatedContent = m.replaceFirst("$1" + newTask + "$3" + java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "$5");
            Files.write(jsonFile, updatedContent.getBytes());
            System.out.println("Task updated successfully.");
        }else{
            System.out.println("Failed to update: Task with id " + taskId + " not found.");
        }
    }
    public void trackStatus(String mark, int id) throws IOException{
        Matcher m = Pattern.compile("(\"id\":\\s*" + id + ")(.*?\"status\":\\s*\")([^\"]*)(.*?\"updatedAt\":\\s*\")([^\"]*)(\")").matcher(content);
        String targetStatus = "";
        if(mark.equals("mark-todo")) { targetStatus = "todo"; }
        else if(mark.equals("mark-in-progress")) { targetStatus = "in-progress"; }
        else if (mark.equals("mark-done")) { targetStatus = "done"; }
        
        if(m.find()){
            String newContent = m.replaceFirst("$1$2" + targetStatus + "$4" + java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "$6");
            Files.write(jsonFile, newContent.getBytes());
            System.out.println("Status updated to: " + targetStatus);
        }else{
            System.out.println("Task ID not found.");
        }
    }
    public void listAllTasks(String[] args){
        String mark = (args.length == 1 && args[0].equals("list")) ? "list" : args[1];
        //read content and print all tasks
        // Use Pattern.DOTALL to allow . to match newlines
        Matcher m = Pattern.compile("\"id\":\\s*(\\d+).*?\"description\":\\s*\"([^\"]*)\".*?\"status\":\\s*\"([^\"]*)\"", Pattern.DOTALL).matcher(content);
        while(m.find()){
            if(mark.equals("list")){
                System.out.printf("ID: %s | Description: %s | Status: %s%n", m.group(1), m.group(2), m.group(3));
            }
            else if(m.group(3).equals(mark)){
                System.out.printf("ID: %s | Description: %s | Status: %s%n", m.group(1), m.group(2), m.group(3));
            }
        }
    }
    public void deleteTask(int taskId) throws IOException{
        String newLine = System.lineSeparator();

        // Remove the task object with leading/trailing whitespace
        String regex = ",\\s*\\{\\s*\"id\":\\s*" + taskId + "[^}]*\\}";
        String newContent = content.replaceAll(regex, "");
        
        // If no match (head case), try without leading comma
        if(newContent.equals(content)) {
            regex = "\\{\\s*\"id\":\\s*" + taskId + "[^}]*\\}\\s*,";
            newContent = content.replaceAll(regex, "");
        }
        
        // Clean up: remove double commas
        newContent = newContent.replaceAll(",\\s*,", ",");
        
        // Clean up: remove opening bracket with comma
        newContent = newContent.replaceAll("\\[\\s*,", "[");
        
        // Clean up: remove comma before closing bracket
        newContent = newContent.replaceAll(",\\s*\\]", "]");
        
        // Remove empty lines: split by newline, filter out lines with only whitespace, rejoin
        String[] lines = newContent.split(newLine);
        StringBuilder sb = new StringBuilder();
        for(String line : lines) {
            if(!line.trim().isEmpty()) {
                sb.append(line).append(newLine);
            }
        }
        newContent = sb.toString();
        
        newContent = newContent.trim();
        Files.write(jsonFile, newContent.getBytes());
        System.out.println("Task deleted successfully.");
    }
}
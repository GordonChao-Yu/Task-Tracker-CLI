package com.gordon.project;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
class Mapper{
    String desc;
    int id;
    String status;
    String createAt;
    String updateAt;
    LocalDateTime dateTime = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public Mapper(int id, String desc){
        this.id = id;
        this.status = "todo";
        this.desc = desc;
        this.createAt = formatter.format(dateTime);
        this.updateAt = formatter.format(dateTime);
        
    }
    public String getDesc(){
        return desc;
    }
    public int getId(){
        return id;
    }
    public String getStatus(){
        return status;
    }
    public String createAt(){
        return createAt;
    }
    public String updateAt(){
        return updateAt;
    }
    public String toJson(){
        return String.format(
        "{\"id\": %d, \"description\": \"%s\", \"status\": \"%s\", \"createdAt\": \"%s\", \"updatedAt\": \"%s\"}",
        getId(), getDesc(), getStatus(), createAt(), updateAt()
        );
    }
    public String save(){
        //save to json file
        return toJson();
    }
}


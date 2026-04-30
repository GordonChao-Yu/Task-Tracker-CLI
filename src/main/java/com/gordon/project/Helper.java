package com.gordon.project;

class taskHelper{
    String[] args;
    public taskHelper(String[] args){
        this.args = args;
    }
    Boolean check(){
        if (args.length == 0) {
            System.out.println("No command provided.");
            return false;
        }
        String action = args[0];
        
        switch (action) {
            case "add":
                if (args.length < 2) {
                    System.out.println("Usage: add <description>");
                    return false;
                }
                break;
            case "update":
                if (args.length < 3) {
                    System.out.println("Usage: update <id> <description>");
                    return false;
                }
                break;
            case "delete":
                if (args.length < 2) {
                    System.out.println("Usage: delete <id>");
                    return false;
                }
                break;
            case "mark-done":
                if (args.length < 2) {
                    System.out.println("Usage: mark-done <id>");
                    return false;
                }
                break;
            case "mark-todo":
                if (args.length < 2) {
                    System.out.println("Usage: mark-todo <id>");
                    return false;
                }
                break;
            case "mark-in-progress":
                if (args.length < 2) {
                    System.out.println("Usage: mark-in-progress <id>");
                    return false;
                }
                break;
            case "list":
                break;
            default:
                System.out.print("Invalid command. Please use one of the following: add, update, delete, mark-done, mark-todo, mark-in-progress, list");
                return false;
        }
        return true;
    }
}


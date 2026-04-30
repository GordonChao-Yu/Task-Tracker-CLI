# Task-Tracker-CLI
This is a simple command-line interface (CLI) application for managing tasks. You can add, update, delete, mark, and list tasks directly from the terminal.
## Features
- **Add a Task**: Add a new task with a description.
- **Update a Task**: Update the description of an existing task.
- **Delete a Task**: Remove a task by its ID.
- **Mark a Task**: Mark a task as "in progress", "done" or "todo."
- **List Tasks**: List all tasks or filter them by status (e.g. todo, in-progress, done).
## Installation
1. Clone the repository:
   ```
   git clone https://github.com/GordonChao-Yu/Task-Tracker-CLI
   cd Task-Tracker-CLI
   ```
2. Compile the source code:
   ```
   javac -d bin src/main/java/com/gordon/project/*.java
   ```
3. Run the application:
   ```
   java -cp bin com.gordon.project.TaskCli <command> [argument]
   ```
## Usage
```
# Adding a new task
java -cp bin com.gordon.project.TaskCli add "Buy groceries"
# Output: Task added successfully (ID: 1)

# Updating a task
java -cp bin com.gordon.project.TaskCli update 1 "Buy groceries and cook dinner"
# Output: Task updated successfully (ID: 1)

# Deleting a task
java -cp bin com.gordon.project.TaskCli delete 1
# Output: Task deleted successfully (ID: 1)

# Marking a task as in progress
java -cp bin com.gordon.project.TaskCli mark-in-progress 1
# Output: Task marked as in progress (ID: 1)

# Marking a task as done
java -cp bin com.gordon.project.TaskCli mark-done 1
# Output: Task marked as done (ID: 1)

# Listing all tasks
java -cp bin com.gordon.project.TaskCli list
# Output: List of all tasks

# Listing tasks by status
java -cp bin com.gordon.project.TaskCli list todo
java -cp bin com.gordon.project.TaskCli list in-progress
java -cp bin com.gordon.project.TaskCli list done
```
https://roadmap.sh/projects/task-tracker

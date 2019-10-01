package com.TaskList;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Main {

    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static ArrayList<Task> taskList = new ArrayList<>();
    private static boolean flag = true; // flag for interaction with user


    public static void main(String[] args) throws Exception {

        System.out.println("==============================================================================================================================");
        System.out.println("===============================================================================================================");
        System.out.println("================================================================================================");
        System.out.println("Welcome to task list console application!\n" +
                "Here you can note and create all tasks you need, edit and delete them.\n" +
                "Set your owm goals, accomplish them and be productive.\n" +
                "My name is Aleksandr, I can't waste my life and always want to be useful.\n" +
                "I developed this one, so I hope you'll enjoy it. Let's begin!");
        System.out.println("================================================================================================");
        System.out.println("===============================================================================================================");
        System.out.println("==============================================================================================================================");

        Task task = new Task();

        while (flag) {

            boolean utterAnotherFlag = true;
            boolean anotherFlag;

            System.out.println("\nWhat do you want to do (enter the number) ?");
            System.out.println("1 - look over the task list;\n2 - create new task;\n3 - edit the task;\n4 - delete the task;\n5 - quit the program.");
            while (utterAnotherFlag) {
                anotherFlag = true;
                try {
                    int answer = Integer.parseInt(reader.readLine());
                    while (anotherFlag) {
                        switch (answer) {
                            case 1:
                                if (taskList.size() != 0) {
                                    task.printTasks();
                                    flag = true;
                                } else {
                                    create(task);
                                    flag = true;
                                }
                                anotherFlag = false;
                                break;
                            case 2:
                                task.createTask();
                                anotherFlag = false;
                                flag = true;
                                break;
                            case 3:
                                task.editTask();
                                anotherFlag = false;
                                flag = true;
                                break;
                            case 4:
                                if (taskList.size() != 0) {
                                    task.deleteTask();
                                    flag = true;
                                } else {
                                    create(task);
                                    flag = true;
                                }
                                anotherFlag = false;
                                break;
                            case 5:
                                System.out.println("Thank you for coming, see you later!");
                                anotherFlag = false;
                                flag = false;
                                break;
                            default:
                                task.printError();
                                anotherFlag = false;
                                flag = true;
                                break;
                        }
                    } // while anotherFlag
                    utterAnotherFlag = false;
                } catch (NumberFormatException e) {
                    System.out.println("Press only 1 - 5 number please.");
                }
            }
        } // while flag
    } // main()

    /*----------------------------------------------------------------------------------------------------------------*/

    /**
     * If user's task list is empty, the method suggests create a new task or leave the program.
     * */

    public static void create(Task task) throws Exception {

        flag = true;
        boolean anotherFlag;

        System.out.println("Your task list is empty. Want to create new task (1 - yes, 0 - no) ?");
        while (flag) {
            try {
                anotherFlag = true;
                int answer2 = Integer.parseInt(reader.readLine());
                while (anotherFlag) {
                    if (answer2 == 1) {
                        task.createTask();
                        anotherFlag = false;
                        flag = false;
                    } else if (answer2 == 0) {
                        System.out.println("Thank you for coming, see you later!");
                        anotherFlag = false;
                        flag = false;
                    } else {
                        task.printError();
                        anotherFlag = false;
                    }
                }
            } catch(NumberFormatException e){
                System.out.println("Press 1 or 0 only please.");
            }
        }
    } // create

    /*====================================================================================================================*/

    /**
     * Class for all actions and tasks' describes.
     * */

    public static class Task {

        String text;
        String date;
        String mark; // mark for ticking tasks as important
        static int taskCount = 0; // value of tasks

        public Task() {
        }

        public Task(String text, String date, String mark) {
            this.text = text;
            this.date = date;
            this.mark = mark;
        }

        /*------------------------------------------------------------------------------------------------------------*/

        /**
         * Methods printing the text performance of any task according to the its mark.
         * */

        public void getTask(int count, String text, String date) {
            System.out.println(count + ". " + text + ". Finish to " + date + ".");
        }

        public void getImportantTask(int count, String text, String date, String mark) {
            System.out.println(count + ". " + text + ". " + "(!" + mark + "!). Finish to " + date + ".");
        }

        /*------------------------------------------------------------------------------------------------------------*/

        /**
         * Printing of the input mistakes.
         * */

        public void printError() {
            System.out.println("Input error. Try again.");
        }

        /*------------------------------------------------------------------------------------------------------------*/

        /**
         * Method returns text performance of date for screen output.
         * */

        public String printDate() throws Exception {
            boolean flag = true;

            while (flag) {
                try {
                    String x = reader.readLine();
                    SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
                    Date y = s.parse(x);
                    SimpleDateFormat z = new SimpleDateFormat("MMMMMMMMM dd, yyyy", Locale.ENGLISH);
                    date = z.format(y).toUpperCase();
                    flag = false;
                } catch (ParseException e) {
                    System.out.println("Invalid date format. Write the correct date format (write if as [yyyy-MM-dd]) .");
                }
            }
            return date;

        }

        /*------------------------------------------------------------------------------------------------------------*/

        /**
         * Printing of all tasks in the task list for a now moment.
         * */

        public void printTasks() {
            int count = 0;

            for (Task task : taskList) {
                if (task.mark.equals("")) {
                    getTask(++count, task.text, task.date);
                } else {
                    getImportantTask(++count, task.text, task.date, task.mark);
                }
            }
        } // printTasks()

        /*------------------------------------------------------------------------------------------------------------*/

        /**
         * Creating the new Task with all necessary parameters.
         * */

        public void createTask() throws Exception {

            mark = "Important/urgently";
            boolean flag = true;
            boolean anotherFlag;

            System.out.println("Write the text of the task you have to do:");
            text = reader.readLine();

            System.out.println("Write the date you want to perform it (write if as [yyyy-MM-dd] format) :");
            date = printDate();

            System.out.println("Should it to be marked as important or urgently task (1 - yes, 0 - no) ?");
            while (flag) {
                try {
                    anotherFlag = true;
                    int answer = Integer.parseInt(reader.readLine());
                    while (anotherFlag) {
                        if (answer == 1) {
                            anotherFlag = false;
                            flag = false;
                        } else if (answer == 0) {
                            mark = "";
                            anotherFlag = false;
                            flag = false;
                        } else {
                            printError();
                            anotherFlag = false;
                        }
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Press 1 or 0 only please.");
                }
            }

            Task task = new Task(text, date, mark);
            taskList.add(task);

            taskCount++;
            System.out.println("The task has been created:");
            if (task.mark.equals("")) {
                getTask(taskCount, text, date);
            } else {
                getImportantTask(taskCount, text, date, mark);
            }
        } // createTask()

        /*------------------------------------------------------------------------------------------------------------*/

        /**
         * Editing the task with text, date, text and date, ticking task as important or leaving the editor mode.
         * */

        public void editTask() throws Exception {

            boolean flag = true;
            boolean anotherFlag = true;
            boolean utterAnotherFlag = true;

            System.out.println("What task would you like to edit (enter the number of task) ?");
            printTasks();
            while (utterAnotherFlag) {
                try {
                    int answer = Integer.parseInt(reader.readLine());
                    answer--;
                    while (flag) {
                        if (answer >= 0 && answer < taskList.size()) {
                            Task t = taskList.get(answer);
                            System.out.println("What part of task would you like to edit (1 - text, 2 - date, 3 - text and date, 4 - mark as important; 0 - nothing) ?");
                            if (t.mark.equals("")) {
                                getTask(taskCount, t.text, t.date);
                            } else {
                                getImportantTask(taskCount, t.text, t.date, t.mark);
                            }
                            int answer2 = Integer.parseInt(reader.readLine());
                            while (anotherFlag) {
                                switch (answer2) {
                                    case 1:
                                        System.out.println("Write the text:");
                                        t.text = reader.readLine();
                                        anotherFlag = false;
                                        break;
                                    case 2:
                                        System.out.println("Write the date:");
                                        t.date = printDate();
                                        anotherFlag = false;
                                        break;
                                    case 3:
                                        System.out.println("Write the text:");
                                        t.text = reader.readLine();
                                        System.out.println("Write the date:");
                                        t.date = printDate();
                                        anotherFlag = false;
                                        break;
                                    case 4:
                                        if (t.mark.equals("")) {
                                            t.mark = "Important/urgently";
                                        } else {
                                            System.out.println("You can't mark already marked task.");
                                        }
                                        anotherFlag = false;
                                        break;
                                    case 0:
                                        anotherFlag = false;
                                        break;
                                    default:
                                        printError();
                                        break;
                                }
                            }
                            System.out.println("The task has been edited.");
                            flag = false;
                            utterAnotherFlag = false;
                        } else {
                            printError();
                        }
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Press number of existing task only please.");
                }
            }
        } // editTask()

        /*------------------------------------------------------------------------------------------------------------*/

        /**
         * Deleting any of tasks.
         * */

        public void deleteTask() throws Exception {

            boolean flag;
            boolean anotherFlag = true;


            System.out.println("What task would you like to delete (enter the number of task) ?\n");
            printTasks();
            while (anotherFlag) {
                flag = true;
                try {
                    int answer = Integer.parseInt(reader.readLine());
                    answer--;
                    while (flag) {
                        if (answer >= 0 && answer < taskList.size()) {
                            Task s = taskList.get(answer);
                            taskList.remove(s);
                            System.out.println("The task under the number of " + ++answer + " has been deleted.");
                            taskCount--;
                            flag = false;
                            anotherFlag = false;
                        } else {
                            printError();
                            flag = false;
                        }
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Press number of existing task only please.");
                }
            }
        } // deleteTask()
    }
}

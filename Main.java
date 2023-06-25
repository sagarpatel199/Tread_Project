import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Main {
    static ArrayList<Group> groupList = new ArrayList<>();
    public static long time = System.currentTimeMillis();
    public static final int numStudent = 20;
    public static final int numGroups = 3;
    public static final int numHouses = 2;
    public static Semaphore houseLock = new Semaphore(numHouses);

    public static boolean emptyClass;
    public static Semaphore mutex = new Semaphore(1);
    public static Semaphore rainbow = new Semaphore(0);

    public static Students[] students = new Students[numStudent];

    static int arrayCount = 0;



    Object mutualExclusion = new Object();


    public static void main(String[] args) throws InterruptedException {
        Boolean LectureOn;

        emptyClass = true;

        Teacher teacher = new Teacher();
        teacher.start();




        //create a list of students
        for(int i = 0; i< students.length; i++) {

            Students student = new Students(teacher, i);
            students[i] = student;
        }
        for (int i = 0; i < students.length; i++){
                students[i].start();

            }
        for(int i=1; i<=numGroups; i++) {
            groupList.add(new Group(i));
        }
        rainbow.acquire();


        //Thread.sleep(5000);
//        System.out.println("["+(System.currentTimeMillis()- time)+"] " + "Group 1 has: " + Group.group1.size());
//        System.out.println("["+(System.currentTimeMillis()- time)+"] " + "Group 2 has: " + Group.group2.size());
//        System.out.println("["+(System.currentTimeMillis()- time)+"] " + "Group 3 has: " + Group.group3.size());
        Group.selectHouse(groupList);

        for(int i = 1; i <= Group.groups.size(); i++){
            houseLock.acquire();
            for(int j = 0; j < Group.groups.get(i - 1).size(); j++){
                if( i == 1){
                    Students.group1Lock.release();
                }
                if(i == 2){
                    Students.group2Lock.release();
                }
                if(i == 3){
                    Students.group3Lock.release();
                }
            }
            Thread.sleep(2000);


            houseLock.release();

        }
//        System.out.println("["+(System.currentTimeMillis()- time)+"] " + "This is the average of group1: "+Group.groupAverage(Group.group1));
//        System.out.println("["+(System.currentTimeMillis()- time)+"] " + "This is the average of group2: " + Group.groupAverage(Group.group2));
//        System.out.println("["+(System.currentTimeMillis()- time)+"] " + "This is the average of group3: "+ Group.groupAverage(Group.group3));

        groupList.get(0).groupAve = Group.groupAverage(Group.group1);
        groupList.get(1).groupAve = Group.groupAverage(Group.group2);
        groupList.get(2).groupAve = Group.groupAverage(Group.group3);
        groupList.get(0).groupID = Group.group1;
        groupList.get(1).groupID = Group.group2;
        groupList.get(2).groupID = Group.group3;


            Group winner = Group.highestAverage(groupList).get(0);
            System.out.println("Group " +winner.brick +" is the WINNER with an average of "+ winner.groupAve + ".");

        for(int i = 0; i<Group.groups.size(); i++){
            if(Group.groups.get(i) == Group.winner1st){ // winning group released first
                Students.totalLock.release();
            }else{
                Students.totalLock.acquire();   // rest will be released after
            }

        }
        System.out.println("All students have been released.");









        //house.start();
//        Random rand = new Random();
//        for(int i =0; i<numStudent; i++){
//            Students student = students[rand.nextInt(1, 3)];
//            System.out.println(student);
//        }




    }
}
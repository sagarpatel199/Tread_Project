import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.Semaphore;


public class Students extends Thread{
    volatile static int groupId;

    volatile int candy;
    public static int counter = 0;
    public static int BB = 0;

    public static int CC = -1;


    public  static Semaphore totalLock = new Semaphore(0);

    public  static Semaphore group1Lock = new Semaphore(0);
    public  static Semaphore group2Lock = new Semaphore(0);
    public  static Semaphore group3Lock = new Semaphore(0);

    public static Semaphore studentLock = new Semaphore(0);

    static Random random = new Random();
    static int id;
    public static ArrayList<Integer> candies = new ArrayList<Integer>();
    static Students totalCandies;
    Teacher teacher;




    Students(Teacher teach,int StudID){
        teacher =teach;
        id = StudID;
        setName("Student" + StudID);

    }

    public void addToArray(Students students, int candy){
        candies.add(candy);
        totalCandies = students;
        setName("Student " + totalCandies);
    }

    public void msg(String m) {
        System.out.println("["+(System.currentTimeMillis()- Main.time)+"] "+getName()+": "+m);
    }

    //simulates travel of students
    public synchronized void commute() throws InterruptedException {
        Thread.sleep((long) (Math.random()*2000));
        msg(" Student is Excited");
    }
    //simulates student hesitation

    public synchronized int  generateNum() {
        groupId = random.nextInt(1,4);
        return groupId;
    }

    public int trickOrTreat(){
        candy = random.nextInt(1, 10);
        return candy;
    }


    @Override
    public synchronized void run(){


        try {
            commute();

            Main.mutex.acquire();  //P(mutex)
            counter ++;            //waiting for students, forming group
            Main.mutex.release();  //V(mutex)
            if(counter == Main.numStudent){//checking if group is formed.
                Teacher.teacherLock.release();
                studentLock.acquire();
            }else {
                studentLock.acquire();  //P(teacherLock)
            }
            msg("got a bag of candy.");




            Main.mutex.acquire();  //P(mutex)
            BB ++;            //waiting for students, forming group
            Main.mutex.release();  //V(mutex)
            if(BB == Main.numStudent){//checking if group is formed.

                    studentLock.release();
            }else {
                studentLock.acquire();  //P(teacherLock)
            }
            studentLock.release();


//            generateNum();  // get the groups for students



//            Main.mutex.acquire();  //P(mutex)
//            CC ++;            //waiting for students, forming group
//            Main.mutex.release();  //V(mutex)
//            if(CC == Main.numStudent){//checking if group is formed.
//                studentLock.release();
//
//                Main.rainbow.release();
//            }else {
//                studentLock.acquire();  //P(teacherLock)
//            }
//             studentLock.release();

            Main.mutex.acquire();
            generateNum();
            msg("is in group:" + groupId);
            if (groupId == 1) {
                Group.group1.add(this);
                Main.mutex.release();
                Main.rainbow.release();
                group1Lock.acquire();
            } else if (groupId == 2) {
                Group.group2.add(this);
                Main.mutex.release();
                Main.rainbow.release();
                group2Lock.acquire();
            } else if (groupId == 3) {
                Group.group3.add(this);
                Main.mutex.release();
                Main.rainbow.release();
                group3Lock.acquire();
            }


            trickOrTreat();
            msg(" got " + candy + " candies.");
            addToArray(this, candy);
//                studentLock.acquire();


            totalLock.release();

//            House.houseLock.release();
            //Teacher.teacherLock.release(); // V(teacherLock)
            } catch (InterruptedException e) {
            throw new RuntimeException(e);

        }

    }
}

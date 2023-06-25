import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Group {

    static Random random = new Random();
    static volatile int houseNum;
    static ArrayList<Students> groupID;
    volatile int brick;

    volatile double groupAve;

    public static ArrayList<Students> group1 = new ArrayList<>();
    public static ArrayList<Students> group2 = new ArrayList<>();
    public static ArrayList<Students> group3 = new ArrayList<>();

    public static ArrayList<ArrayList> groups = new ArrayList<>() {
        {
            add(group1);
            add(group2);
            add(group3);
        }

    };

    public static ArrayList<Students> winner1st;


//    public static int getBrick() {
//        return brick;
//    }
//
//    public void setBrick(int brick) {
//        Group.brick = brick;
//    }

    //    Group(int id, ArrayList<Students> group,int house){
//        houseNum = house;
//        brick = id;
//        groupID = group;
//    }
    Group(int id){
        groupID = new ArrayList<>();
        this.brick= id;
        this.groupAve = 0;

    }

    public static int houseGen(){
        houseNum = random.nextInt(1, 4);
        return houseNum;
    }

//    public void setName(String, int s){
//        return
//    }





    public static void selectHouse(ArrayList<Group> group){
        int house;
        for(int i = 0; i < group.size(); i++){
            house = houseGen();
            Main.groupList.get(i).houseNum = house;
            System.out.println("["+(System.currentTimeMillis()- Main.time)+"] " + "Group " + (i+1) + " selected House: " + house);
        }

    }
//    public static void printWinner(){
//        if(groupAverage(Group.group1)>Group.groupAverage(Group.group2) &&
//                Group.groupAverage(Group.group1) > Group.groupAverage(Group.group3)){
//            System.out.println("Group 1 is the winner.");
//        } else if (groupAverage(Group.group2)>Group.groupAverage(Group.group1) &&
//                Group.groupAverage(Group.group2) > Group.groupAverage(Group.group3)) {
//            System.out.println("Group 2 is the winner.");
//        } else if (groupAverage(Group.group3)>Group.groupAverage(Group.group1) &&
//                Group.groupAverage(Group.group3) > Group.groupAverage(Group.group2)) {
//            System.out.println("Group 3 is the winner.");
//        }
//    }



    public static double groupAverage(ArrayList<Students> group){
        double sumTotal =0;
        for(int i =0; i<group.size(); i++){
            sumTotal += group.get(i).candy;

        }
        double average = sumTotal/(double)group.size();
        return average;
    }



    public static ArrayList<Group> highestAverage(ArrayList<Group> list) {

        double maxAvg = 0;
        int maxAvgGroup = 0;
        ArrayList<Group> newList = new ArrayList<>();
        for(int i=0; i< list.size(); i++){
            double avg = list.get(i).groupAve;
            System.out.println("The Average of Group " + (i+1) + " is: " + avg);

            if(avg > maxAvg) {
                newList.add(0,list.get(i));
                maxAvg = avg;

            }else{
                newList.add(list.get(i));
            }
        }
        System.out.println("Max " + maxAvg);
        return newList;
    }



}

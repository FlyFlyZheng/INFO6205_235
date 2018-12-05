import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sun.rmi.runtime.Log;

import javax.swing.*;
import java.rmi.ConnectIOException;
import java.util.*;


public class GA {
    private static final Logger logger = LogManager.getLogger(Main.class);

    Point[][] map;
    private ArrayList<Robot> robotList;
    private ArrayList<Robot> sonRobotList;
    public Robot bestRobot = null;
    public int bestScore = Integer.MIN_VALUE;
    public int bestInheritance;
    Random random=new Random();
    Environment env;

    public GA(){
    }

    public ArrayList<Robot> getRobotList() {
        return robotList;
    }

    public ArrayList<Robot> init(){
        logger.info("In GA.init method, set up envirnoment");
        env = new Environment();
        map = env.getMap();
        logger.info("In GA.init method, init the orgin robot list");
        robotList = new ArrayList<>();
        initRobotList(robotList,Config.POPULATION);
        return robotList;
    }

    public void initRobotList(ArrayList<Robot> robotList,int polulation){
        Random random = new Random();

        for(int i=0; i<polulation; i++){
            Robot robot = new Robot(Config.STEP_NUMBER, map);
            int[] steps = robot.getSteps();
            for(int j=0; j< Config.STEP_NUMBER; j++){
                int currentStep = random.nextInt(7);
                steps[j] = currentStep;
            }
            //set Score
            robot.calScore();
            robotList.add(robot);
        }
        logger.info("For the first generate of robots, calculate their fitness");
        calFitness(robotList);
        //System.out.println("Init Robot List finished. Robot Number:"+Config.POPULATION+" STEP Number: "+Config.STEP_NUMBER);
        logger.info("For the first generate of robots, calculate the Avg Score of them");

        float ga=GetAvgScore(this.robotList);
        logger.info("The Avg Score is :"+ga);
    }
    public float GetAvgScore(ArrayList<Robot> rlist){
        float total=0;
        for(Robot r:rlist){
            total+=r.calScore();
        }
        // System.out.println("Avg Socre is "+total/Config.POPULATION);
        return total/Config.POPULATION;
    }

    public void calFitness(ArrayList<Robot> robotList) {
        if (robotList == null) {
            return;
        }
        //get plus number
        Collections.sort(robotList);


        int plus = 0;
        if (robotList.get(0).getScore() < 0) {
            plus = 0 - robotList.get(0).getScore();
        }

        int[] plusScores = new int[robotList.size()];
        int sum = 0;


        for (int i = 0; i < robotList.size(); i++) {
            plusScores[i] = robotList.get(i).getScore()+plus;
            sum += plusScores[i];
        }


        float a=0;
        for (int i = 0; i < robotList.size(); i++) {
            float fitness = (float) plusScores[i] / sum;
            robotList.get(i).setFitness(fitness);
//            System.out.println("Socre is"+  robotList.get(i).getScore());
//            System.out.println("Fitness is"+  robotList.get(i).getFitness());

            a=a+robotList.get(i).getFitness();
        }

        // Print(a);

    }

    public void evlove(int x){

        logger.info("Begin to evlove for "+x+" generaion");
        for(int i=0;i<x;i++){

            if(GetAvgScore(this.getRobotList())<Config.STEP_NUMBER*9) {
                oneGeneration(i);
            }else {
                return;
            }
        }
    }

    public void oneGeneration(int x){
        logger.info("The Avg Score of the "+x+ " generation is "+ GetAvgScore(robotList));
        GetAvgScore(robotList);
        this.sonRobotList = new ArrayList<>(Config.POPULATION);

        //generate sonRobotList;
        this.hybridization();

        mutationOnce();
        this.robotList=this.updateRobotList();

        calFitness(robotList);


        Collections.sort(robotList);
        Collections.reverse(robotList);
        this.bestRobot=robotList.get(0);



        Collections.sort(robotList);
        Collections.reverse(robotList);
        if(this.bestScore<robotList.get(0).getScore()){
            logger.info("Update Best");
            bestRobot=robotList.get(0);
            bestScore=robotList.get(0).getScore();
            bestInheritance=x;
            logger.info(x+" Is the bestInheritance Currently");
        }



    }
    public void hybridization(){

        while(sonRobotList.size()<=1000){
            oneHybridization();
        }
        Collections.sort(sonRobotList);

    }

    public void oneHybridization(){
        //Print("Size of the son list"+sonRobotList.size());
        Robot father= this.getParent();
        Robot mother= this.getParent();

        while(mother.equals(father)){
            father= this.getParent();
        }

        Robot[] twoSon=crossOver(father,mother);


        sonRobotList.add(twoSon[0]);
        sonRobotList.add(twoSon[1]);

    }

    public Robot getParent(){

        Collections.shuffle(robotList);
        float a= (float) 0.999;
        float percent=random.nextFloat();
        while(percent>a){
            percent=random.nextFloat();
        }
        float totalOfFitness=0;

        int i =0;
        for(Robot r:robotList){
            totalOfFitness+=r.getFitness();
            i++;

            if(totalOfFitness-percent>0){
                return r;
            }
        }
        Print("Can not find a parent ?");
        return null;


    }


    public Robot[] crossOver(Robot father, Robot mother){
        if(father == null || mother == null || father.equals(mother)){
            logger.error("In crossOvre method, the father or mother is null!");
            return null;
        }



        //Get Cross Start Index
        Random random = new Random();
        int crossStartIndex = random.nextInt(Config.STEP_NUMBER);

        int crossEndIndex = random.nextInt(Config.STEP_NUMBER);
        while(crossEndIndex == crossStartIndex){
            crossEndIndex = random.nextInt(Config.STEP_NUMBER);
        }
        if(crossEndIndex < crossStartIndex){
            int temp = crossEndIndex;
            crossEndIndex = crossStartIndex;
            crossStartIndex = temp;
        }

        //swap crossStartIndex -> crossEndIndex
        Robot[] children = new Robot[2];
        Robot firstchild = father.clone();
        Robot secondchild = mother.clone();
        for(int i=crossStartIndex; i<=crossEndIndex; i++){
            int temp = firstchild.getSteps()[i];
            firstchild.getSteps()[i] = secondchild.getSteps()[i];
            secondchild.getSteps()[i] = temp;
        }
        firstchild.calScore();
        secondchild.calScore();
        children[0] = firstchild;
        children[1] = secondchild;

        return children;
    }



    public ArrayList<Robot> updateRobotList(){
        ArrayList<Robot> bigList= new ArrayList<>(Config.POPULATION*2);
        ArrayList<Robot> resList= new ArrayList<>(Config.POPULATION*2);

        bigList.addAll(sonRobotList);
        bigList.addAll(robotList);
        Collections.sort(bigList);
        Collections.reverse(bigList);

        resList.add(bigList.get(0));
        //Choose some entity of first half to not be added
        Set<Integer> fountset= new HashSet<>();
        while(fountset.size()<51){
            fountset.add(random.nextInt(1000));
        }

        for(int i=0;i<1000;i++){
            if(!fountset.contains(i)){
                resList.add(bigList.get(i));
            }
            else{
                continue;
            }
        }


        while(resList.size()<1000){
            resList.add(bigList.get(1000+random.nextInt(1000)));
        }

        return  resList;

    }
    //mutation Once
    public void mutationOnce(){
        Random random = new Random();
        double percent = random.nextDouble();
        for(Robot robot: this.sonRobotList){
            if(percent < Config.VARIABLE_PERCENT){
                this.mutation(robot);
            }
            percent = random.nextDouble();
        }
    }

    private void mutation(Robot robot){
        Random random = new Random();
        int muationIndex = random.nextInt(Config.STEP_NUMBER);
        int mutationValue = random.nextInt(7);
        robot.getSteps()[muationIndex] = mutationValue;
    }



    private static void Print(String s){
        System.out.println(s);
    }
    private static void Print(Float s){
        System.out.println(s);
    }
    private static void Print(int s){
        System.out.println(s);
    }
    private static void Print(Robot r){
        for(int i:r.getSteps()){
            System.out.print(i+"->");
        }
    }

}





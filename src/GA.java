import javax.swing.*;
import java.rmi.ConnectIOException;
import java.util.*;

public class GA {

    Point[][] map;

    private ArrayList<Robot> robotList;
    private ArrayList<Robot> sonRobotList;
    public Robot bestRobot = null;
    public int bestScore;
    public int bestInheritance;


    public GA(){
    }



    public void init(){
        Environment env = new Environment();
        map = env.getMap();

        robotList = new ArrayList<>();
        initRobotList(robotList);

    }

    private void initRobotList(ArrayList<Robot> robotList){
        Random random = new Random();

        for(int i=0; i<Config.POPULATION; i++){
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
        calFitness(robotList);
        System.out.println("Init Robot List finished. Robot Number:"+Config.POPULATION+" STEP Number: "+Config.STEP_NUMBER);
        GetAvgScore();
    }
    public float GetAvgScore(){
        int total=0;
        for(Robot r:robotList){
            total+=r.calScore();
        }
        System.out.print("Avg Socre is "+total/Config.POPULATION);
        return total/Config.POPULATION;
    }



    //Fitness
    private void calFitness(ArrayList<Robot> robotList){
        if(robotList == null){
            return;
        }

        //get plus number
        Collections.sort(robotList);
        int plus = 0;
        if(robotList.get(0).getScore() < 0){
            plus = 0-robotList.get(0).getScore();
        }

        int[] plusScores = new int[robotList.size()];
        int sum = 0;
        for(int i=0; i<robotList.size(); i++){
            plusScores[i] = robotList.get(i).getScore() + plus;
            sum+=plusScores[i];
        }

        for(int i=0; i<robotList.size(); i++){
            double fitness = plusScores[i]/sum;
            robotList.get(i).setFitness(fitness);
        }
    }

    public void evlove(){
        System.out.println("Begin to evlove: ");
        for(int i=0; i<Config.maxNumberOfGenerations; i++){
            System.out.println("Genetic No: "+(i+1));
            this.oneGenetic(i+1);
        }
    }

    public void oneGenetic(int geneticNumber){
        this.sonRobotList = new ArrayList<>();
        //generate sonRobotList;
        this.hybridization();
        //mutation;
        this.mutationOnce();
        //update robot list
        this.updateRobotList();
        //update best robot
        this.updateBestRobot(geneticNumber);

    }

    private void hybridization(){
        System.out.println("begin hybridization: ");
        Random random = new Random();
        for(int i=0; i<Config.POPULATION/2+1; i++){
            System.out.println("The times of Hybridization is :"+ i);
            this.oneHybridization();
        }
    }




    //generate next generation to sonRobotList
    private void oneHybridization(){
        //get Parent
        Robot father = this.getParent();
        Robot mother = this.getParent();
        Robot best = this.getBestParent();

        while(father == null || father.equals(best)){
            father = this.getParent();
        }
        int cnt=0;
        while(mother == null || father.equals(mother) || mother.equals(best)){
            if(cnt > Config.POPULATION/2){
                break;
            }
            cnt ++;
            mother = this.getParent();
        }

        //crossOver
        Robot[] children = crossOver(father, mother);
        if(children != null){
            this.sonRobotList.add(children[0]);
            this.sonRobotList.add(children[1]);
        }

    }

    //轮盘赌选择Parent
    private Robot getParent(){

        Collections.shuffle(robotList);

        Random random = new Random();
        double selectedPercent = random.nextDouble();
        double distributionPercent = 0.0;
        for(int i=0; i<robotList.size(); i++){
            distributionPercent += this.robotList.get(i).getFitness();
            if(distributionPercent > selectedPercent){
                return this.robotList.get(i);
            }
        }
        return null;
    }

    private Robot getBestParent(){
        if(this.bestRobot == null){
            Robot bestParent = this.robotList.get(0).clone();
            for(int i=0; i<this.robotList.size(); i++){
                if(this.robotList.get(i).getScore() > bestParent.getScore()){
                    bestParent = this.robotList.get(i);
                }
            }
            return bestParent;
        }else{
            return this.bestRobot.clone();
        }
    }

    private Robot[] crossOver(Robot father, Robot mother){
        if(father == null || mother == null || father.equals(mother)){
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
        Robot fatherClone = father.clone();
        Robot motherClone = mother.clone();
        for(int i=crossStartIndex; i<=crossEndIndex; i++){
            int temp = fatherClone.getSteps()[i];
            fatherClone.getSteps()[i] = motherClone.getSteps()[i];
            motherClone.getSteps()[i] = temp;
        }
        children[0] = fatherClone;
        children[1] = motherClone;
        return children;
    }

    //mutation Once
    private void mutationOnce(){
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

    private void updateRobotList(){

        if(sonRobotList == null){
            return;
        }

        //update sonRobotList detailed info
        for(Robot robot: sonRobotList){
            robot.calScore();
        }
        this.calFitness(sonRobotList);

        //generate next geneation
        List<Robot> allRobots = new ArrayList<>();
        allRobots.addAll(robotList);
        allRobots.addAll(sonRobotList);
        Collections.sort(allRobots);

        List<Robot> bestRobots = new ArrayList<>();
        for(int i=0; i<Config.POPULATION; i++){
            bestRobots.add(allRobots.get(i).clone());
        }

        Collections.shuffle(bestRobots);
        for(int i=0; i<Config.POPULATION; i++){
            this.robotList.set(i, bestRobots.get(i));
        }
        this.calFitness(this.robotList);

    }

    private void updateBestRobot(int geneticNumber){
        boolean isUpdate = false;
        for(int i=0; i<this.robotList.size(); i++){
            if(this.robotList.get(i).getScore() > bestScore){
                this.bestRobot = this.robotList.get(i).clone();
                this.bestScore = this.robotList.get(i).getScore();
                this.bestInheritance = geneticNumber;
                isUpdate = true;
            }
        }

        if(isUpdate){
            System.out.println("Best Solution: "
                + " \n genetic:" +  this.bestInheritance
                + " \n Score:" + this.bestScore
                + " \n steps:" + this.bestRobot.printSteps());
        }
    }



}

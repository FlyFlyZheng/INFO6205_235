import javax.swing.*;
import java.rmi.ConnectIOException;
import java.util.ArrayList;
import java.util.Random;

public class GA {

    Point[][] map;

    private ArrayList<Robot> robotList;
    private ArrayList<Robot> sonRobotList;

    public GA(){
        init();
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


}

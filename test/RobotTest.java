import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

public class RobotTest {

    private ArrayList<Robot> rlist= new ArrayList<>();
    private Point[][] map;
    private  Random random = new Random();

    @org.junit.Before
    public void setUp() throws Exception {

        Environment env = new Environment();
        map = env.getMap();

        for(int i=0;i<5;i++) {
            rlist.add(new Robot(Config.STEP_NUMBER, map));
            int[] steps = rlist.get(i).getSteps();
            for (int j = 0; j < Config.STEP_NUMBER; j++) {
                int currentStep = random.nextInt(7);
                steps[j] = currentStep;
            }

        }
    }
    @Test
    public void TestSteps(){

        assertTrue(rlist.get(0).getSteps().length==Config.STEP_NUMBER);
        assertTrue(rlist.get(1).getSteps().length==Config.STEP_NUMBER);
        assertTrue(rlist.get(2).getSteps().length==Config.STEP_NUMBER);

        int number = random.nextInt(50);
        assertTrue(rlist.get(0).getSteps()[number]!=rlist.get(1).getSteps()[number]||rlist.get(1).getSteps()[number]!=rlist.get(3).getSteps()[number]||rlist.get(2).getSteps()[number]!=rlist.get(3).getSteps()[number]);

    }


    @Test
    public void TestCompare(){

        for(Robot r: rlist) r.calScore();
        Collections.sort(rlist);

        assertTrue(rlist.get(0).getScore()<rlist.get(1).getScore());
        assertTrue(rlist.get(1).getScore()<rlist.get(2).getScore());
    }
}
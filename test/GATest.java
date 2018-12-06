import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.*;

public class GATest {

    GA ga;

    @org.junit.Before
    public void setUp() throws Exception {
        ga= new GA();
        ga.init();
    }
    @Test
    public void init() {
        assertTrue(ga.map.length==Config.LENGTH);
        assertTrue(ga.map[1].length==Config.LENGTH);

        assertTrue(ga.env.cupNum==Config.CUP_NUM);

    }

    @Test
    public void initRobotList() {
        assertTrue(ga.getRobotList().size()==Config.POPULATION);
    }

    @Test
    public void getAvgScore() {
        assertTrue(ga.GetAvgScore(ga.getRobotList())<Config.STEP_NUMBER*10);
        assertTrue(ga.GetAvgScore(ga.getRobotList())>0-Config.STEP_NUMBER*5);
        float initScore=ga.GetAvgScore(ga.getRobotList());
        ga.evlove(10);
        assertTrue(ga.GetAvgScore(ga.getRobotList())<Config.STEP_NUMBER*10);
        assertTrue(ga.GetAvgScore(ga.getRobotList())>0-Config.STEP_NUMBER*5);
        float tenScore=ga.GetAvgScore(ga.getRobotList());
        ga.evlove(100);
        assertTrue(ga.GetAvgScore(ga.getRobotList())<Config.STEP_NUMBER*10);
        assertTrue(ga.GetAvgScore(ga.getRobotList())>0-Config.STEP_NUMBER*5);
        float hundrandScore=ga.GetAvgScore(ga.getRobotList());

        assertTrue(initScore<tenScore);
        assertTrue(tenScore<hundrandScore);
    }

    @Test
    public void calFitness(){
        ga.calFitness(ga.getRobotList());
        float total = 0;
        for(Robot r: ga.getRobotList()){
            total+=r.getFitness();
        }
        assertTrue(total>(float)(0.999));
        assertTrue(total<(float)(1.001));
    }
    @Test
    public void evlove() {
        float first =ga.GetAvgScore(ga.getRobotList());
        ga.evlove(300);
        float second =ga.GetAvgScore(ga.getRobotList());
        ga.evlove(300);
        float third =ga.GetAvgScore(ga.getRobotList());
        ga.evlove(300);
        float fourth =ga.GetAvgScore(ga.getRobotList());
        //For this test, in most of times it would passed, indicate that thhe speed of evlove is slowing down after multiple generations
        assertTrue(second-first>third-second);
        assertTrue(third-second>fourth-third);
    }

    @Test
    public void oneGeneration() {


    }


    @Test
    public void oneHybridization() {

    }

    @Test
    public void getParent() {

        int parentSocreSum=0;
        for(int i=0;i<=500;i++) {
            parentSocreSum+=ga.getParent().calScore();
        }
        float avgParent= parentSocreSum/ga.getRobotList().size();
        assertTrue(avgParent>ga.GetAvgScore(ga.getRobotList()));

    }
    @Test
    public void mutationTest(){

    }

    @Test
    public void crossOver(){
        System.out.println("Number of RobotList is "+ ga.getRobotList().size());

        Robot father = ga.getParent();
        Robot mother = ga.getParent();
        Robot[] child =ga.crossOver(father,mother);

        assertTrue(!father.getSteps().equals(mother.getSteps()));
        assertTrue(!child[0].getSteps().equals(child[1].getSteps()));
        assertTrue(!child[0].getSteps().equals(father.getSteps()));
        assertTrue(!child[0].getSteps().equals(mother.getSteps()));
        assertTrue(!child[1].getSteps().equals(father.getSteps()));
        assertTrue(!child[1].getSteps().equals(mother.getSteps()));

    }
}
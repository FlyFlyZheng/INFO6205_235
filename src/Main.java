import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);


    public static void main(String[] args){
        GA ga = new GA();





        //init environment
        ga.init();


        Print(ga.getRobotList().get(12).getScore());
        Print(ga.getRobotList().get(121).getScore());
        //ga.calFitness(ga.getRobotList());

        ga.evlove(10000);


        StringBuilder stringBuilder= new StringBuilder();

        for(int i: ga.bestRobot.getSteps()) {
            stringBuilder.append(i);
            stringBuilder.append("->");
        }

        logger.info("After Evolove");
        logger.info("Best entity appeared in Genetic"+ ga.bestInheritance);
        logger.info("The best Score is "+ga.bestScore);
        logger.info("The best track is "+ stringBuilder);

        System.out.println("After Evolove: "
                + " \n genetic:" +  ga.bestInheritance
                + " \n Score:" + ga.bestScore);

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
}

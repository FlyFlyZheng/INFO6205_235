public class Main {

    public static void main(String[] args){
        GA ga = new GA();

        //init environment
        ga.init();

        //evolove
        ga.evlove();

        System.out.println("After Evolove: "
                + " \n genetic:" +  ga.bestInheritance
                + " \n Score:" + ga.bestScore
                + " \n steps:" + ga.bestRobot.printSteps());


    }
}

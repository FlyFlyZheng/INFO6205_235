public class Main {

    public static void main(String[] args){
        GA ga = new GA();

        //init environment
        ga.init();

        Print(ga.getRobotList().get(12).getScore());
        Print(ga.getRobotList().get(121).getScore());
        //ga.calFitness(ga.getRobotList());

        ga.evlove(1000);

        System.out.println("After Evolove: "
                + " \n genetic:" +  ga.bestInheritance
                + " \n Score:" + ga.bestScore);
        //    + " \n steps:" + ga.bestRobot.printSteps());

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

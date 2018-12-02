public class Robot {

    public static final int GO_UP = 0;
    public static final int GO_DOWN = 1;
    public static final int GO_LEFT = 2;
    public static final int GO_RIGHT = 3;
    public static final int GO_RANDOM = 4;
    public static final int PICK = 5;
    public static final int DO_NOTHING = 6;

    //Environment
    Point[][] map;

    //Genotype
    private int[] steps;

    //Phenotype
    private int score;

    public Robot(int stepNum, Point[][] map){
        steps = new int[stepNum];
        this.map = map;
    }

    //Expression: mapping Genotype to Phenotype
    public int calScore(){
        //TODO
        return -1;
    }

    //is wall: return false;
    //in bound: return true;
    private boolean inBound(int x, int y){
        return false;
    }


}

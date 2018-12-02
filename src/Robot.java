import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Robot implements Comparable<Robot>{

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

    // all fitness of a generation plus to be 1
    private double fitness;

    public Robot(int stepNum, Point[][] map){
        steps = new int[stepNum];
        this.map = map;
    }

    public Robot clone(){
        Robot clone = new Robot(this.steps.length, this.map);
        for(int i=0; i<this.steps.length; i++){
            clone.steps[i] = this.steps[i];
        }
        clone.score = this.score;
        clone.fitness = this.fitness;
        return clone;
    }

    //Expression: mapping Genotype to Phenotype
    public int calScore(){
        //TODO
        int result = 0;
        int CurrentX=0;
        int CurrentY=0;
        int Nums=0;

        while(Nums<steps.length){

            int i= steps[Nums];

            //System.out.print("X position is: "+CurrentX);
            //System.out.print("Y position is: "+CurrentX);
            switch (i){
                case GO_UP:
                    if(inBound(CurrentX,CurrentY+1))
                    {
                        CurrentY++;
                    }else{
                        result -=5;
                    }
                    break;
                case GO_DOWN:
                    if(inBound(CurrentX,CurrentY-1))
                    {
                        CurrentY--;
                    }else{
                        result -=5;
                    }
                    break;
                case GO_LEFT:
                    if(inBound(CurrentX-1,CurrentY))
                    {
                        CurrentX--;
                    }else{
                        result -=5;
                    }
                    break;
                case GO_RIGHT:
                    if(inBound(CurrentX+1,CurrentY))
                    {
                        CurrentX++;
                    }else{
                        result -=5;
                    }
                    break;
                case GO_RANDOM:
                    int random = new Random().nextInt(4);
                    if(random==0){
                        if(inBound(CurrentX,CurrentY+1))
                        {
                            CurrentY++;
                        }else{
                            result -=5;
                        }

                    }else if(random==1){
                        if(inBound(CurrentX,CurrentY-1))
                        {
                            CurrentY--;
                        }else{
                            result -=5;
                        }

                    }else if(random==2){
                        if(inBound(CurrentX-1,CurrentY))
                        {
                            CurrentX--;
                        }else{
                            result -=5;
                        }

                    }else {
                        if(inBound(CurrentX+1,CurrentY))
                        {
                            CurrentX++;
                        }else{
                            result -=5;
                        }

                    }
                    break;
                case PICK:
                    if(map[CurrentX][CurrentY].getStatus()==Point.IS_EMPLY){
                        result-=1;
                    }else{
                        result+=10;
                    }
                    break;
                case DO_NOTHING:
                    break;
            }
            Nums++;
        }
        score =result;

        return result;
    }




    //is wall: return false;
    //in bound: return true;
    private boolean inBound(int x, int y){
        if(x<0 || x>9 || y<0 || y>9){
            return false;
        }
        return true;
    }

    public int[] getSteps(){
        return this.steps;
    }

    public int getScore(){
        return this.score;
    }

    public void setFitness(double fitness){
        this.fitness = fitness;
    }

    public double getFitness() {
         return this.fitness;
    }

    public String printSteps(){
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        for(int i=0; i<this.steps.length; i++){
            sb.append(this.steps[i] + "->");
        }
        sb.append("\n");
        return sb.toString();
    }

    @Override
    public int compareTo(Robot o) {
        return sign(this.score - o.score);
    }

    public int sign(int x){
        return (x>0) ? 1 : ((x==0) ? 0: -1);
    }
}

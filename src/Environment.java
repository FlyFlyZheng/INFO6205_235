import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

public class Environment {
    private static final Logger logger = LogManager.getLogger(Main.class);

    private Point[][] map;

    int len= Config.LENGTH;

    public Environment(){
        logger.info("Enviroment init");
        generateMap();
       // printMap();
    }

    public Point[][] getMap(){
        return map;
    }

    public void generateMap(){

        logger.info("Genereate a map of size 10*10");
        map = new Point[len][len];

        for(int i=0;i<len;i++){
            for(int j=0;j<len;j++){
                map[i][j] = new Point(i,j);
            }
        }

        //set wall
//        for(int i=0;i<len;i++){
//            map[0][i].setStatus(Point.IS_WALL);
//            map[len-1][i].setStatus(Point.IS_WALL);
//            map[i][0].setStatus(Point.IS_WALL);
//            map[i][len-1].setStatus(Point.IS_WALL);
//        }

        //set random cup
        Random random= new Random();

        logger.info("For the map, set servel cups in random posistion of the map ");
        while(calculateCupNumber(map)< Config.CUP_NUM) {
            int randomX = random.nextInt(len);
            int randomY = random.nextInt(len);
            map[randomX][randomY].setStatus(Point.CUP);

        }
    }



    public int calculateCupNumber(Point[][] map){

        int res=0;
        for(int i=0;i<len;i++){
            for(int j=0;j<len;j++){
                if(map[i][j].getStatus()==Point.CUP){
                    res++;
                }
            }
        }
        return res;

    }

    public void printMap(){

        logger.info("Draw the image of the map, 0 to be empty and 1 to be withcup");
        if(map == null){
            System.out.println("map is not generated");
            return;
        }
        System.out.print("--------------Map----------------\n");
        int len = Config.LENGTH;
        for(int i=0; i<len; i++){
            for(int j=0; j<len; j++){
                System.out.print(map[i][j].getStatus()+" ");
            }
            System.out.print("\n");
        }
        System.out.print("---------------------------------\n");
    }


}


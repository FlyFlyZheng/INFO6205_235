import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

public class Config {
    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(Main.class);
    static int LENGTH = 10;
    static int CUP_NUM = 30;


    static int STEP_NUMBER = 200;
    static int POPULATION = 1000;

    static int maxNumberOfGenerations = 10000;
    static double VARIABLE_PERCENT=0.1;

}

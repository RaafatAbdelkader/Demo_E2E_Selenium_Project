package basePg;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

import java.io.File;

public class MyLogger {
    private static Logger logger= LogManager.getLogger();
    private static String dirPath=System.getProperty("user.dir")+"//reports";
    private static synchronized void  startLog(String dirPath,String classname,String tcName){
        File dir=new File(dirPath);
        int count=0;
        if (dir.exists()){
            for (File file:dir.listFiles()) {
                if(file.isFile()&&file.getName().contains(tcName)&&file.getName().endsWith(".log"))
                    count++;
            }
        }
        String logFileName=classname.split("Test\\.")[1]+"-"+tcName;
        if (count>0)
            logFileName=logFileName+"-"+count;
        ThreadContext.put("logFilename",logFileName);
    }

    public static void startTC(String classname,String tcName){
        startLog(dirPath,classname,tcName);
        info("=========== TC: "+tcName+" started: ===========");
    }
    public static void testFailed(String tcName){
        error("===========TC: "+tcName+" failed  ===========");
    }
    public static void testPassed(String tcName){
        info("=========== TC: "+tcName+" passed  ===========");
    }
    public static void testSkipped(String tcName){
        info("=========== TC: "+tcName+" Skipped  ===========");
    }
    public static void trace (String msg){
        logger.trace(msg);
    }
    public static void debug (String msg){
        logger.debug(msg);
    }
    public static void info (String msg){
        logger.info(msg);
    }
    public static void warn (String msg){
        logger.warn(msg);
    }
    public static void error (String msg){
        logger.error(msg);
    }
    public static void fatal (String msg){
        logger.fatal(msg);
    }
}

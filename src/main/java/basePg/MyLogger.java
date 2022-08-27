package basePg;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

import java.io.File;

public class MyLogger {
    private static Logger logger= LogManager.getLogger();
    static String dirPath=System.getProperty("user.dir")+"//reports";

    public static synchronized void  startLog(String dirPath,String classname,String tcName){
        File dir=new File(dirPath);
//        int count=0;
//        if (dir.exists()){
//            for (File file:dir.listFiles()) {
//                if(file.isFile()&&file.getName().contains(tcName)&&file.getName().endsWith(".log"))
//                    count++;
//            }
//        }
        String logFileName=classname+"-"+tcName;
//        if (count>0)
//            logFileName=classname+"-"+tcName+"-"+count;


        ThreadContext.put("logFilename",logFileName);
    }

    public static void startTC(String classname,String tcName){
        startLog(dirPath,classname,tcName);
        info("======================= TC: "+tcName+" started: =======================");
    }
    public static void endTC(String tcName){
        info("======================= TC: "+tcName+" finished: =======================");
    }
    public static void testFailed(String tcName){
        error("======================= TC: "+tcName+" failed:  =======================");
    }


    public static void error (String msg){
        logger.error(msg);
    }
    public static void info (String msg){
        logger.info(msg);
    }

}

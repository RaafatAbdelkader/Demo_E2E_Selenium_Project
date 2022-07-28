package base;
import java.sql.*;

public class DBReader {
    private PropReader propReader=new PropReader();
    private String db_username=propReader.getProp("db_username");
    private String db_psw=propReader.getProp("db_psw");
    private String db_host=propReader.getProp("db_host");
    private String db_Port=propReader.getProp("db_Port");
    private String db_Name=propReader.getProp("db_Name");
    private String db_URL="jdbc:sqlserver://"+db_host+":"+db_Port+";databaseName="+db_Name+";trustServerCertificate=true";

    public String query="SELECT username,password,status FROM loginData WHERE status ='valid'";

    public void setQuery(String query) {
        this.query = query;
    }

    public Connection getDBConnection (){
        Connection conn = null;
        try {
            conn=DriverManager.getConnection(db_URL,db_username,db_psw);
            if ((conn==null))
                System.out.println("can not connect to Database");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public ResultSet executeSqlQuery (String sqlQuery){
        Connection conn;
        Statement statement;
        ResultSet resultSet = null;
        try {
            conn=DriverManager.getConnection(db_URL,db_username,db_psw);
           if (!(conn ==null)){
               statement= conn.createStatement();
               resultSet= statement.executeQuery(sqlQuery);
           }else {
               System.out.println("can not connect to Database");
           }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public Object[][] getLoginData(Boolean validData, int numOfExpectedLoginData) throws SQLException {
        if (!(validData)) {
            query= query.replace("valid", "invalid");
        }
        ResultSet result =executeSqlQuery(query);
        Object[][]loginData=new Object[numOfExpectedLoginData][3];
        int valueAdd=0;
        for (int i = 0; i < numOfExpectedLoginData && result.next(); i++) {
            loginData[i][0]=result.getString("username");
            loginData[i][1]=result.getString("password");
            loginData[i][2]=result.getString("status");
            valueAdd++;
        }
        if (valueAdd<numOfExpectedLoginData){
            System.out.println("the expected number of data was not found in DB");
            return null;
        }else {
            return loginData;
        }

    }
}

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import java.io.*;
import java.sql.*;
import java.util.*;

public class DormAssignment {
    private Connection conn;
    private Statement stmt;

    public static void main(String[] args) {
        DormAssignment dormAssignment = new DormAssignment();
        dormAssignment.ex1_2();
        dormAssignment.ex1_3();
        dormAssignment.ex1_4();
        dormAssignment.ex1_5();
        dormAssignment.ex1_6();
    }

    public void ex1_2() {
        connectDB();
        try {
            long start = GregorianCalendar.getInstance().getTimeInMillis();
            //创建`dorm_building`表
            stmt.execute("DROP TABLE IF EXISTS `dorm_building`;");
            stmt.execute(" CREATE TABLE `dorm_building`(`dbid` VARCHAR(10) NOT NULL PRIMARY KEY , `fee` DOUBLE  , `phone` CHAR (8));");
            //创建`student`表
            stmt.execute("DROP TABLE IF EXISTS `student`");
            stmt.execute("CREATE TABLE `student`(`sid` VARCHAR (20) NOT NULL PRIMARY KEY , `name` VARCHAR (20) NOT NULL , `sex` BOOL NOT NULL , `department` VARCHAR (20) NOT NULL ,  `campus` VARCHAR(10) NOT NULL, `dbid` VARCHAR (10));");
            long end = GregorianCalendar.getInstance().getTimeInMillis();
            System.out.println("ex1_2 took " + (end - start) + "ms.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDBConn();
    }

    public void ex1_3() {
        connectDB();
        try {
            //连接分配方案.xls文件
            Workbook workbook = Workbook.getWorkbook(new File("C:\\Users\\NJ\\IdeaProjects\\Database\\src\\main\\resources\\分配方案.xls"));
            Sheet sheet = workbook.getSheet(0);
            //连接电话.txt文件
            BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\NJ\\IdeaProjects\\Database\\src\\main\\resources\\电话.txt"));

            //创建插入表student数据的sql语句
            StringBuilder values = new StringBuilder();
            for (int i = 1; i < 3775; i++) {
                values.append("(");
                values.append("'" + sheet.getCell(1, i).getContents() + "',");
                values.append("'" + sheet.getCell(2, i).getContents() + "',");
                values.append((sheet.getCell(3, i).getContents().equals("男") ? false : true) + ",");
                values.append("'" + sheet.getCell(0, i).getContents() + "',");
                values.append("'" + sheet.getCell(4, i).getContents() + "',");
                values.append("'" + sheet.getCell(5, i).getContents() + "'");
                values.append(")");
                if (i != 3774){
                    values.append(",");
                }
            }
            String sql1 = "insert into `student`(`sid`,`name`,`sex`,`department`,`campus`,`dbid`) values" + values + ";";

            //创建插入表dorm_building数据的sql语句
            values = new StringBuilder();
            Map<String, String> map1 = new HashMap<>();
            for (int i = 1; i < 3775; i++) {
                map1.put(sheet.getCell(5, i).getContents(), sheet.getCell(6 ,i).getContents());
            }
            Map<String, String> map2 = new HashMap<>();
            String line = br.readLine();
            while((line = br.readLine()) != null){
                map2.put(line.split(";")[0], line.split(";")[1]);
            }
            Set<String> keys = map2.keySet();
            Iterator<String> keyIterator = keys.iterator();
            while (keyIterator.hasNext()){
                String key = keyIterator.next();
                values.append("(");
                values.append("'" + key + "',");
                values.append(map1.get(key) + ",");
                values.append("'" + map2.get(key) + "'");
                values.append(")");
                if (keyIterator.hasNext()) values.append(",");
            }
            String sql2 = "insert into `dorm_building`(`dbid`,`fee`,`phone`) values" + values + ";";

            //执行sql语句
            long start = GregorianCalendar.getInstance().getTimeInMillis();
            stmt.execute(sql1);
            stmt.execute(sql2);
            long end = GregorianCalendar.getInstance().getTimeInMillis();
            System.out.println("ex1_3 took " + (end - start) + "ms.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDBConn();
    }

    public void ex1_4() {
        connectDB();
        try {
            long start = GregorianCalendar.getInstance().getTimeInMillis();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT ss.department " +
                    "FROM student ss " +
                    "WHERE ss.dbid IN (SELECT DISTINCT s.dbid FROM student s WHERE s.name='王小星');");
            long end = GregorianCalendar.getInstance().getTimeInMillis();
            System.out.println("ex1_4 took " + (end - start) + "ms.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDBConn();
    }

    public void ex1_5() {
        connectDB();
        try {
            long start = GregorianCalendar.getInstance().getTimeInMillis();
            stmt.execute("UPDATE dorm_building SET fee=1200 WHERE dbid='陶园1舍';");
            long end = GregorianCalendar.getInstance().getTimeInMillis();
            System.out.println("ex1_5 took " + (end - start) + "ms.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDBConn();
    }

    public void ex1_6() {
        connectDB();
        try {
            long start = GregorianCalendar.getInstance().getTimeInMillis();
            ResultSet rs1 = stmt.executeQuery("SELECT DISTINCT dbid FROM student WHERE department='软件学院' AND sex=TRUE ;");
            rs1.next();
            String dbid1 = rs1.getString("dbid");
            ResultSet rs2 = stmt.executeQuery("SELECT DISTINCT dbid FROM student WHERE department='软件学院' AND sex=FALSE ;");
            rs2.next();
            String dbid2 = rs2.getString("dbid");
            stmt.execute("UPDATE student SET dbid='" + dbid2 + "' WHERE department='软件学院' AND sex=TRUE;");
            stmt.execute("UPDATE student SET dbid='" + dbid1 + "' WHERE department='软件学院' AND sex=FALSE;");
            long end = GregorianCalendar.getInstance().getTimeInMillis();
            System.out.println("ex1_6 took " + (end - start) + "ms.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDBConn();
    }

    private void connectDB() {
        try {
            //加载驱动程序
            Class.forName("com.mysql.jdbc.Driver");
            //获取数据库连接
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/exercise2", "root", "52013147758.+");
            //创建语句对象
            stmt = conn.createStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void closeDBConn() {
        try {
            if (stmt != null) {
                stmt.close();
                stmt = null;
            }
            if (conn != null) {
                conn.close();
                conn = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

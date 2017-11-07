import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import java.io.*;
import java.nio.Buffer;
import java.sql.*;
import java.util.GregorianCalendar;

public class DormAssignment {
    private Connection conn;
    private Statement stmt;

    public static void main(String[] args) {
        DormAssignment dormAssignment = new DormAssignment();
        dormAssignment.ex1_2();
        dormAssignment.ex1_3();
    }

    public void ex1_2() {
        connectDB();
        try {
            long start = GregorianCalendar.getInstance().getTimeInMillis();
            //创建`dorm_building`表
            stmt.execute("DROP TABLE IF EXISTS `dorm_building`;");
            stmt.execute(" CREATE TABLE `dorm_building`(`dbid` VARCHAR(10) NOT NULL PRIMARY KEY , `fee` DOUBLE , `campus` VARCHAR(10) NOT NULL , `phone` CHAR (8));");
            //创建`student`表
            stmt.execute("DROP TABLE IF EXISTS `student`");
            stmt.execute("CREATE TABLE `student`(`sid` VARCHAR (20) NOT NULL PRIMARY KEY , `name` VARCHAR (20) NOT NULL , `department` VARCHAR (20) NOT NULL , `dbid` VARCHAR (10));");
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
            String path = "C:\\Users\\NJ\\IdeaProjects\\Database\\src\\main\\resources\\分配方案.xls";
            Workbook workbook = Workbook.getWorkbook(new File(path));
            Sheet sheet = workbook.getSheet(0);
            StringBuilder values = new StringBuilder();
            for (int i = 1; i < 3775; i++) {
                values.append("(");
                values.append(sheet.getCell(1, i).getContents() + ",");
                values.append(sheet.getCell(2, i).getContents() + ",");
                values.append(sheet.getCell(0, i).getContents() + ",");
                values.append(sheet.getCell(5, i).getContents());
                values.append(")");
                if (i != 3774){
                    values.append(",");
                }
            }
            String sql = "insert into `student`(`sid`,`name`,`department`,`dbid`) values" + values;
            System.out.println(sql);

            long start = GregorianCalendar.getInstance().getTimeInMillis();
            long end = GregorianCalendar.getInstance().getTimeInMillis();
            System.out.println("ex1_2 took " + (end - start) + "ms.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
        closeDBConn();
    }

    public void ex1_4() {
        connectDB();
        try {
            long start = GregorianCalendar.getInstance().getTimeInMillis();
            //创建`dorm_building`表
            stmt.execute("DROP TABLE IF EXISTS `dorm_building`;");
            stmt.execute(" CREATE TABLE `dorm_building`(`dbid` VARCHAR(10) NOT NULL PRIMARY KEY , `fee` DOUBLE , `sex` BOOL , `campus` VARCHAR(10) NOT NULL , `phone` CHAR (8));");
            //创建`student`表
            stmt.execute("DROP TABLE IF EXISTS `student`");
            stmt.execute("CREATE TABLE `student`(`sid` VARCHAR (20) NOT NULL PRIMARY KEY , `name` VARCHAR (20) NOT NULL , `department` VARCHAR (20) NOT NULL , `dbid` VARCHAR (10));");
            long end = GregorianCalendar.getInstance().getTimeInMillis();
            System.out.println("ex1_2 took " + (end - start) + "ms.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDBConn();
    }

    public void ex1_5() {
        connectDB();
        try {
            long start = GregorianCalendar.getInstance().getTimeInMillis();
            //创建`dorm_building`表
            stmt.execute("DROP TABLE IF EXISTS `dorm_building`;");
            stmt.execute(" CREATE TABLE `dorm_building`(`dbid` VARCHAR(10) NOT NULL PRIMARY KEY , `fee` DOUBLE , `sex` BOOL , `campus` VARCHAR(10) NOT NULL , `phone` CHAR (8));");
            //创建`student`表
            stmt.execute("DROP TABLE IF EXISTS `student`");
            stmt.execute("CREATE TABLE `student`(`sid` VARCHAR (20) NOT NULL PRIMARY KEY , `name` VARCHAR (20) NOT NULL , `department` VARCHAR (20) NOT NULL , `dbid` VARCHAR (10));");
            long end = GregorianCalendar.getInstance().getTimeInMillis();
            System.out.println("ex1_2 took " + (end - start) + "ms.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDBConn();
    }

    public void ex1_6() {
        connectDB();
        try {
            long start = GregorianCalendar.getInstance().getTimeInMillis();
            //创建`dorm_building`表
            stmt.execute("DROP TABLE IF EXISTS `dorm_building`;");
            stmt.execute(" CREATE TABLE `dorm_building`(`dbid` VARCHAR(10) NOT NULL PRIMARY KEY , `fee` DOUBLE , `sex` BOOL , `campus` VARCHAR(10) NOT NULL , `phone` CHAR (8));");
            //创建`student`表
            stmt.execute("DROP TABLE IF EXISTS `student`");
            stmt.execute("CREATE TABLE `student`(`sid` VARCHAR (20) NOT NULL PRIMARY KEY , `name` VARCHAR (20) NOT NULL , `department` VARCHAR (20) NOT NULL , `dbid` VARCHAR (10));");
            long end = GregorianCalendar.getInstance().getTimeInMillis();
            System.out.println("ex1_2 took " + (end - start) + "ms.");
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

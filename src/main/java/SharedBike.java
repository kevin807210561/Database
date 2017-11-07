import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.GregorianCalendar;

public class SharedBike {
    private Connection conn;
    private Statement stmt;

    public static void main(String[] args){
        SharedBike sharedBike = new SharedBike();
        sharedBike.ex2_1_3();
    }

    public void ex2_1_3() {
        connectDB();
        try {
            BufferedReader recordBr = new BufferedReader(new FileReader("C:\\Users\\NJ\\IdeaProjects\\Database\\src\\main\\resources\\record.txt"));
            BufferedReader bikeBr = new BufferedReader(new FileReader("C:\\Users\\NJ\\IdeaProjects\\Database\\src\\main\\resources\\bike.txt"));
            BufferedReader userBr = new BufferedReader(new FileReader("C:\\Users\\NJ\\IdeaProjects\\Database\\src\\main\\resources\\user.txt"));

            //生成插入user表数据的sql语句
            StringBuilder values = new StringBuilder();
            String line = userBr.readLine();
            while(line != null){
                String[] data = line.split(";");
                values.append("(");
                values.append(data[0] + ",");
                values.append("'" + data[1] + "',");
                values.append("'" + data[2] + "',");
                values.append(data[3]);
                values.append(")");
                if ((line = userBr.readLine()) != null) values.append(",");
            }
            String sql1 = "insert ignore into `user`(`uid`,`name`,`phone`,`balance`) values" + values + ";";

            //生成插入bike表数据的sql语句
            values = new StringBuilder();
            line = bikeBr.readLine();
            while(line != null){
                values.append("(");
                values.append(line);
                values.append(")");
                if ((line = bikeBr.readLine()) != null) values.append(",");
            }
            String sql2 = "insert ignore into `bike`(`bid`) values" + values + ";";

            //生成插入record表数据的sql语句
            values = new StringBuilder();
            line = recordBr.readLine();
            while(line != null){
                String[] data = line.split(";");
                values.append("(");
                values.append(data[0] + ",");
                values.append(data[1] + "',");
                values.append("'" + data[2] + "',");
                values.append("'" + data[3] + "',");
                values.append("'" + data[4] + "',");
                values.append("'" + data[5] + "',");
                values.append("'" + data[2] + "',");
                values.append(")");
                if ((line = userBr.readLine()) != null) values.append(",");
            }
            String sql3 = "insert into `record`(`uid`,`bid`,`from`,`start_time`,`to`,`end_time`,`cost`) values" + values + ";";

            long start = GregorianCalendar.getInstance().getTimeInMillis();
            //创建user表
            stmt.execute("DROP TABLE IF EXISTS `user`;");
            stmt.execute("CREATE TABLE `user`(`uid` INT NOT NULL AUTO_INCREMENT PRIMARY KEY , `name` VARCHAR (15) NOT NULL , `phone` CHAR (11) NOT NULL , `balance` DOUBLE NOT NULL DEFAULT 0, `address` VARCHAR (30));");
            stmt.execute(sql1);
            //创建bike表
            stmt.execute("DROP TABLE IF EXISTS `bike`;");
            stmt.execute("CREATE TABLE `bike`(`bid` INT NOT NULL AUTO_INCREMENT PRIMARY KEY );");
            stmt.execute(sql2);
            //创建record表
            stmt.execute("DROP TABLE IF EXISTS `record`;");
            stmt.execute("CREATE TABLE `record`(`uid` INT NOT NULL , `bid` INT NOT NULL , `from` VARCHAR (30) NOT NULL , `start_time` DATETIME NOT NULL , `to` VARCHAR (30) NOT NULL , `end_time` DATETIME NOT NULL , `cost` DOUBLE NOT NULL );");
            long end = GregorianCalendar.getInstance().getTimeInMillis();
            System.out.println("ex1_6 took " + (end - start) + "ms.");
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
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

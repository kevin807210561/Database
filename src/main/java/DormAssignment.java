import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DormAssignment {
    private Connection conn;
    private Statement stmt;

    public void ex1_2(){
        connectDB();
        try {
            stmt.execute("CREATE TABLE `dorm_building`(`id` VARCHAR(10) PRIMARY KEY , `fee` DOUBLE , `sex` , );");
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
            if (stmt != null){
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

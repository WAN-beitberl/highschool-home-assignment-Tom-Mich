import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class CreateTables {

    public static void ReadFriendShipTable(String jdbcURL, String username, String password)
    {

        String csvFilePath = "highschool_friendships.csv";

        int other_friend_id;
        int friend_id;

        Connection connection = null;

        try {

            connection = DriverManager.getConnection(jdbcURL, username, password);
            connection.setAutoCommit(false);

            String sql = "INSERT INTO highschool_friendships (friend_id, other_friend_id) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePath));
            String lineText = null;

            lineReader.readLine(); // skip header line

            while ((lineText = lineReader.readLine()) != null) {
                String[] data = lineText.split(",");    // Change variables and data type

                // It should be noted that the value id is auto incremented
                // If exists only one value or the second value is empty
                if(data.length < 2 || data[1].equals(""))
                {
                    friend_id = 0; // fix
                }
                else
                {
                    friend_id = Integer.parseInt(data[1]);
                }

                if(data.length < 3)
                {
                    other_friend_id = 0;
                }
                else
                {
                    other_friend_id = Integer.parseInt(data[2]);
                }

                statement.setInt(1, friend_id);
                statement.setInt(2, other_friend_id);

                statement.addBatch();

                statement.executeBatch();
            }

            lineReader.close();
            connection.commit();
            connection.close();

        } catch (IOException ex) {
            System.err.println(ex);
        } catch (SQLException ex) {
            ex.printStackTrace();

            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void ReadHighSchoolTable(String jdbcURL, String username, String password)
    {

        String csvFilePath = "highschool.csv";  //change

        String first_name, last_name;
        String email;
        String gender;
        String ip_address;
        String has_car, car_color;
        double grade_avg;
        int cm_height;
        int age;
        int grade;
        int identification_card;

        Connection connection = null;

        try {

            connection = DriverManager.getConnection(jdbcURL, username, password);
            connection.setAutoCommit(false);

            String sql =  "INSERT INTO highschool " +
                    "(first_name, last_name, email,gender, ip_address, cm_height, age, has_car, car_color, grade, grade_avg, identification_card) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(sql);

            BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePath));
            String lineText = null;

            int count = 0;

            // Skip first line - the column names
            lineReader.readLine();

            while ((lineText = lineReader.readLine()) != null) {
                String[] data = lineText.split(",");

                first_name = data[1];
                last_name = data[2];
                email = data[3];
                gender = data[4];
                ip_address = data[5];
                cm_height = Integer.parseInt(data[6]);
                age = Integer.parseInt(data[7]);
                has_car = data[8];
                car_color = data[9];
                grade = Integer.parseInt(data[10]);
                grade_avg = Double.parseDouble(data[11]);
                identification_card = Integer.parseInt(data[12]);

                statement.setString(1, first_name);
                statement.setString(2, last_name);
                statement.setString(3, email);
                statement.setString(4, gender);
                statement.setString(5, ip_address);
                statement.setInt(6, cm_height);
                statement.setInt(7, age);

                if (!car_color.equals(null))
                    statement.setString(8, "true");
                else
                    statement.setString(8, "false");

                if (has_car.equals("FALSE")){
                    statement.setNull(9, Types.VARCHAR);
                }
                else
                {
                    statement.setString(9, car_color);
                }

                statement.setInt(10, grade);
                statement.setDouble(11, grade_avg);
                statement.setInt(12, identification_card);

                statement.addBatch();
                statement.executeBatch();
            }

            lineReader.close();
            connection.commit();
            connection.close();

        } catch (IOException ex) {
            System.err.println(ex);
        } catch (SQLException ex) {
            ex.printStackTrace();

            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}

import java.sql.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String jdbcURL = "jdbc:mysql://localhost:3306/s_one";
        String username = "root";
        String password = "Bvcswbf2lol572?";
        //String filePath = "highschool.csv";
        Menu(jdbcURL, username, password);

    }

    // Option 1
    public static void RetSchoolAvg(String jdbcURL, String username, String password)
    {

        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection connection = null;
        String query = "SELECT AVG(grade_avg) FROM highschool";

        try {
            connection = DriverManager.getConnection(jdbcURL, username, password);
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            double avg = resultSet.getDouble("AVG(grade_avg)");
            System.out.println("The High School Average Grade is "+avg);
        }
        catch (SQLException e)
        {
            System.out.println(e);
        }
    }

    // Option 2
    public static void BoysAvg(String jdbcURL, String username, String password)
    {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT AVG(grade_avg) FROM highschool WHERE gender = \"Male\"";

        try {
            connection = DriverManager.getConnection(jdbcURL, username, password); // Establish connection
            statement = connection.prepareStatement(query);     // Create an SQL query using a String
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                double avg = resultSet.getDouble("AVG(grade_avg)");
                System.out.println("The average grades for men is = "+avg);
            }
        }
        catch (SQLException e)
        {
            System.out.println(e);
        }
    }

    // Option 3
    public static void GirlsAvg(String jdbcURL, String username, String password)
    {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT AVG(grade_avg) FROM highschool WHERE gender = \"Female\"";

        try {
            connection = DriverManager.getConnection(jdbcURL, username, password); // Establish connection
            statement = connection.prepareStatement(query);     // Create an SQL query using a String
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                double avg = resultSet.getDouble("AVG(grade_avg)");
                System.out.println("The average grades for women is = "+avg);
            }
        }
        catch (SQLException e)
        {
            System.out.println(e);
        }
    }

    // Option 4
    public static void TallPurpleCarEnjoyers(String jdbcURL, String username, String password)
    {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection connection = null;
        // Should I make it exclusive for male students?
        String query = "SELECT AVG(cm_height) FROM highschool WHERE cm_height > 199 GROUP BY car_color HAVING car_color = \"Purple\"";

        try {
            connection = DriverManager.getConnection(jdbcURL, username, password);
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            double avg = resultSet.getDouble("AVG(cm_height)");
            System.out.println("The Average height for students 2m tall and above that have a purple car is: " + avg);
        }
        catch (SQLException e){
            System.out.println(e);
        }
    }

    // Option 5
    public static void GetFriends(String jdbcURL, String username, String password)
    {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please Enter Student id:\t");
        int id = scanner.nextInt();

        int friends = 0;

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(jdbcURL, username, password);
            // SQL command data stored in String datatype
            String select = "select friend_id, other_friend_id from highschool_friendships where friend_id = "+id+" or other_friend_id = "+id;

            PreparedStatement statement = connection.prepareStatement(select);
            ResultSet resultSet = statement.executeQuery();

            // Condition check
            while (resultSet.next()) {
                int friend_id = resultSet.getInt("friend_id");
                int other_id = resultSet.getInt("other_friend_id");

                if(id == friend_id && friend_id != 0 && other_id != id)
                    friends = other_id;
                else if (id != 0 && id != id)
                    friends = id;

                if(friends != 0)
                {
                    System.out.println(friends);
                    String select2 = "select friend_id, other_friend_id from highschool_friendships " +
                            "where friend_id = " + friends + " or other_friend_id = " + friends;
                    PreparedStatement innerP = connection.prepareStatement(select2);
                    ResultSet innerRes = innerP.executeQuery();

                    // Condition check
                    while (innerRes.next()) {
                        int id3 = innerRes.getInt("friend_id");
                        int id4 = innerRes.getInt("other_friend_id");
                        if(id3 == friends && id4 != 0 && id4 != id) {
                            System.out.println(id4);
                        }
                        else if(id3 != 0 && id3 != id && id3 != friends) System.out.println(id3);
                    }
                }
            }
        }
        catch (SQLException e){
            System.out.println(e);
        }
    }

    // option 6
    public static void PopularityPercent(String jdbcURL, String username, String password)
    {
        int popular = 0;
        int unpopular = 0;
        int normal = 0;
        int[] friendships = new int[1001];

        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection connection = null;
        String query = "SELECT friend_id, other_friend_id FROM highschool_friendships WHERE friend_id != NULL AND other_friend_id != NULL";

        try {
            connection = DriverManager.getConnection(jdbcURL, username, password);
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            int friend1 = resultSet.getInt("friend_id");
            int friend2 = resultSet.getInt("other_friend_id");

            friendships[friend1]++;
            friendships[friend2]++;

            while (resultSet.next())
            {
                friend1 = resultSet.getInt("friend_id");
                friend2 = resultSet.getInt("other_friend_id");

                friendships[friend1]++;
                friendships[friend2]++;
            }

            for(int i = 1; i <1001; i++)
            {
                if(friendships[i] == 0)
                    unpopular++;
                else if(friendships[i] == 1)
                    normal++;
                else
                    popular++;
            }

            // Divide by 10 instead of dividing by 1000 and multiplying by 100 for percent
            System.out.println("Percent of popular students: " + (float)(popular/10));
            System.out.println("Percent of unpopular students: " + (float)(unpopular/10));
            System.out.println("Percent of normal students: " + (float)(normal/10));

        }
        catch (SQLException e)
        {
            System.out.println(e);
        }

    }

    // Option 7
    public static void SpecificStudentAvg(String jdbcURL, String username, String password)
    {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please Enter Student id:\t");
        int id = scanner.nextInt();

        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection connection = null;
        String select = "SELECT identification_card, grade_avg FROM highschool WHERE identification_card = " + id;


        try {
            connection = DriverManager.getConnection(jdbcURL, username, password);
            statement = connection.prepareStatement(select);
            resultSet = statement.executeQuery();

            double avg = resultSet.getDouble("grade_avg");
            System.out.println(avg);
        }
        catch (SQLException e){
            System.out.println(e);
        }
    }

    public static void Menu(String jdbcURL, String username, String password)
    {
        String bless_Sima = "Hello Sima! Good day to you!";
        Scanner scanner = new Scanner(System.in);

        int options;

        System.out.println(bless_Sima);



        while(true)
        {
            System.out.println("Please insert 1-8 options");
            options = scanner.nextInt();

            switch(options)
            {
                case 1:
                    RetSchoolAvg(jdbcURL, username, password);
                    break;

                case 2:
                    BoysAvg(jdbcURL, username, password);
                    break;
                case 3:
                    GirlsAvg(jdbcURL, username, password);
                    break;

                case 4:
                    TallPurpleCarEnjoyers(jdbcURL, username, password);
                    break;

                case 5:
                    GetFriends(jdbcURL, username, password);
                    break;

                case 6:
                    PopularityPercent(jdbcURL, username, password);
                    break;

                case 7:
                    SpecificStudentAvg(jdbcURL, username, password);
                    break;

                case 8:
                    System.exit(0);
                default:
                    System.out.println("Wrong input");
                    break;
            }

        }
    }



}
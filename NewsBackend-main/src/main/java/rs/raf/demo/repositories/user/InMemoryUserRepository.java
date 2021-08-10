package rs.raf.demo.repositories.user;

import org.apache.commons.codec.digest.DigestUtils;
import rs.raf.demo.entities.User;
import rs.raf.demo.repositories.MySqlAbstractRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class InMemoryUserRepository extends MySqlAbstractRepository implements UserRepository {




    @Override
    public User findUser(String email) {
        User user = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM users as u where email = ?");
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {

                String name = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String pass = resultSet.getString("password");

                user = new User(email,name,lastName,pass);
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return user;
    }


    @Override
    public List<User> allUser() {

        List<User> userList = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from users");
            while (resultSet.next()) {

                User user = new User(resultSet.getString("email"), resultSet.getString("first_name"),
                        resultSet.getString("last_name"), resultSet.getString("password"));
                user.setStatus(resultSet.getInt("status"));
                user.setType(resultSet.getInt("type"));
                userList.add(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return userList;
    }

    @Override
    public User addUser(User user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            String[] generatedColumns={"email"};
            preparedStatement = connection.prepareStatement("select * from users as u where u.email = ? ");
            preparedStatement.setString(1, user.getEmail());
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                preparedStatement = connection.prepareStatement("INSERT INTO users (email,first_name, last_name,password," +
                        "type, status) VALUES(?,?,?,?,?,?)", generatedColumns);
                preparedStatement.setString(1, user.getEmail());
                preparedStatement.setString(2, user.getFirst_name());
                preparedStatement.setString(3, user.getLast_name());
                preparedStatement.setString(4, user.getPassword());
                preparedStatement.setInt(5, user.getType());
                preparedStatement.setInt(6, user.getStatus());
                preparedStatement.executeUpdate();
                resultSet = preparedStatement.getGeneratedKeys();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return user;
    }

    @Override
    public Integer userActivity(String email) {
        User user = null;
        int status  = -1;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            String[] generatedColumns={"status"};
            preparedStatement = connection.prepareStatement("select * from users where email = ? ");
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
//            System.out.println(resultSet.next());
            if (resultSet.next()) {
                 status = resultSet.getInt("status");
                System.out.println(status);
                preparedStatement = connection.prepareStatement("update users set status = ? where email = ? ");
                if (status == 0) {
                    status = 1;
                } else {
                    status = 0;
                }
                preparedStatement.setInt(1, status);
                preparedStatement.setString(2, email);
                preparedStatement.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return status;
    }

    @Override
    public User updateUser(User user, String email) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();


            if (!(email.equals(user.getEmail()))) {
                preparedStatement = connection.prepareStatement("select * from users where email = ? ");
                preparedStatement.setString(1, user.getEmail());
                resultSet = preparedStatement.executeQuery();

            }
            if(resultSet == null || !resultSet.next() || email.equals(user.getEmail())) {

                preparedStatement = connection.prepareStatement("update users as u set u.email = ?, u.first_name = ?" +
                        ", u.last_name = ?, u.type = ? where email = ?");
                preparedStatement.setString(1, user.getEmail());
                preparedStatement.setString(2, user.getFirst_name());
                preparedStatement.setString(3, user.getLast_name());
                preparedStatement.setInt(4, user.getType());
                preparedStatement.setString(5, email);
                preparedStatement.executeUpdate();


            }
            preparedStatement.close();
            connection.close();


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            if (resultSet != null) {
                this.closeResultSet(resultSet);
            }
            this.closeConnection(connection);
        }

        return user;
    }

}
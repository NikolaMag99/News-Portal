package rs.raf.demo.repositories.komentar;

import rs.raf.demo.entities.Komentar;
import rs.raf.demo.entities.Tag;
import rs.raf.demo.entities.User;
import rs.raf.demo.entities.Vesti;
import rs.raf.demo.repositories.MySqlAbstractRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlCommentRepository extends MySqlAbstractRepository implements CommentRepository {



    @Override
    public Komentar addComment(Komentar komentar) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();
            java.util.Date date = new java.util.Date();
            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement("INSERT INTO komentar (author, content, createdAt, vest) VALUES(?,?,?,?)", generatedColumns);
            java.sql.Date sqlDate = new java.sql.Date(System.currentTimeMillis());
            preparedStatement.setString(1, komentar.getAuthor());
            preparedStatement.setString(2, komentar.getContent());
            preparedStatement.setDate(3, sqlDate);
            preparedStatement.setInt(4, komentar.getVest().getId());

            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                komentar.setId(resultSet.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return komentar;
    }

    @Override
    public List<Komentar> allComments() {
        List<Komentar> commentList = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSetNews = null;
        try {
            connection = this.newConnection();

            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from komentar order by createdAt desc");
            while (resultSet.next()) {

                Komentar koementar = new Komentar(resultSet.getInt("id"), resultSet.getString("author"),resultSet.getString("content"),
                        resultSet.getDate("createdAt"));

                preparedStatement = connection.prepareStatement("select * from vest where id = ?");
                preparedStatement.setInt(1, resultSet.getInt("vest"));
                resultSetNews = preparedStatement.executeQuery();
                while (resultSetNews.next()){
                    Vesti vesti = new Vesti(resultSetNews.getInt("id"), resultSetNews.getString("title"), resultSetNews.getString("content"), resultSetNews.getDate("createdAt"));
                    vesti.setVisits(resultSetNews.getInt("visits"));


                    synchronized (this) {
                        koementar.setVest(vesti);
                    }
                }
                commentList.add(koementar);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return commentList;
    }

    @Override
    public Komentar findComment(Integer id) {
        Komentar komentar = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ResultSet resultSetNews = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM komentar where id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {

                String author = resultSet.getString("author");
                String content = resultSet.getString("content");
                Date createdAt = resultSet.getDate("createdAt");

                komentar = new Komentar(id, author, content,createdAt);
                preparedStatement = connection.prepareStatement("select * from vest where id = ?");
                preparedStatement.setInt(1, resultSet.getInt("vest"));
                resultSetNews = preparedStatement.executeQuery();
                while (resultSetNews.next()){
                    Vesti vesti = new Vesti(resultSetNews.getInt("id"), resultSetNews.getString("title"), resultSetNews.getString("content"), resultSetNews.getDate("createdAt"));
                    vesti.setVisits(resultSetNews.getInt("visits"));


                    synchronized (this) {
                        komentar.setVest(vesti);
                    }
                }
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

        return komentar;
    }

    @Override
    public void deleteComment(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("DELETE FROM komentar where id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
    }
}

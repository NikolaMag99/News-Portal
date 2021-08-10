package rs.raf.demo.repositories.vesti;

import rs.raf.demo.entities.*;
import rs.raf.demo.repositories.MySqlAbstractRepository;
import rs.raf.demo.repositories.TagRepository.TagRepository;
import rs.raf.demo.repositories.komentar.CommentRepository;

import javax.inject.Inject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlVestiRepository extends MySqlAbstractRepository implements VestiRepository {

//    @Inject
//    private VestiRepository vestiRepository;
    @Inject
    private TagRepository  tagRepository;

    @Inject
    private CommentRepository commentRepository;

    @Override
    public Vesti addNews(Vesti vesti) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ResultSet resultSetCategory = null;


        try {
            connection = this.newConnection();

            String[] generatedColumns = {"id"};


            preparedStatement = connection.prepareStatement("select * from kategorija as k where name equals ", generatedColumns);

            preparedStatement = connection.prepareStatement("INSERT INTO vest (title, content, createdAt, visits, author, kategorija) VALUES(?, ?,?, ?,?,?)", generatedColumns);
            java.sql.Date sqlDate = new java.sql.Date(System.currentTimeMillis());
            preparedStatement.setString(1, vesti.getTitle());
            preparedStatement.setString(2, vesti.getContent());
            preparedStatement.setDate(3, sqlDate);
            preparedStatement.setInt(4, 0);
            preparedStatement.setString(5, vesti.getAuthor().getEmail());
            preparedStatement.setString(6, vesti.getKategorija().getName());

            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                vesti.setId(resultSet.getInt(1));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            if (resultSet != null) {
                this.closeResultSet(resultSet);
            }
            this.closeConnection(connection);
        }

        return vesti;
    }

    @Override
    public Vesti updateNews(Vesti vesti) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ResultSet resultSetCategory = null;

        try {
            connection = this.newConnection();


                preparedStatement = connection.prepareStatement("update vest as v set v.title = ?, v.content = ?, v.author = ?, v.kategorija = ?  where v.id = ?");
                preparedStatement.setString(1, vesti.getTitle());
                preparedStatement.setString(2, vesti.getContent());
                preparedStatement.setString(3, vesti.getAuthor().getEmail());
                preparedStatement.setString(4, vesti.getKategorija().getName());
                preparedStatement.setInt(5, vesti.getId());
                preparedStatement.executeUpdate();

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

        return vesti;
    }

    @Override
    public List<Vesti> allNews() {
        List<Vesti> vestiList = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;

        ResultSet resultSet = null;
        ResultSet resultSetUser = null;
        ResultSet resultSetCategory = null;

        PreparedStatement preparedStatement = null;
        int i = 0;

        try {
            connection = this.newConnection();

            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from vest order by createdAt desc");

            while (i < 10 && resultSet.next()) {
            i++;
                Vesti vesti = new Vesti(resultSet.getInt("id"), resultSet.getString("title"), resultSet.getString("content"), resultSet.getDate("createdAt"));
                vesti.setVisits(resultSet.getInt("visits"));

                preparedStatement = connection.prepareStatement("select * from users where email = ?");
                preparedStatement.setString(1, resultSet.getString("author"));
                resultSetUser = preparedStatement.executeQuery();

                while (resultSetUser.next()) {
                    User user = new User(resultSetUser.getString("email"), resultSetUser.getString("first_name"), resultSetUser.getString("last_name"), resultSetUser.getString("password"));
                    user.setStatus(resultSetUser.getInt("status"));
                    user.setType(resultSetUser.getInt("type"));

                    synchronized (this) {
                        vesti.setAuthor(user);
                    }
                }

                preparedStatement = connection.prepareStatement("select * from kategorija where name = ?");
                preparedStatement.setString(1, resultSet.getString("kategorija"));
                resultSetCategory = preparedStatement.executeQuery();

                while (resultSetCategory.next()) {
                    Kategorija category = new Kategorija(resultSetCategory.getString("name"), resultSetCategory.getString("description"));

                    synchronized (this) {
                        vesti.setKategorija(category);
                    }
                }
                vestiList.add(vesti);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return vestiList;
    }

    @Override
    public List<Vesti> allNewsByVisits() {
        List<Vesti> vestiList = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;

        ResultSet resultSet = null;
        ResultSet resultSetUser = null;
        ResultSet resultSetCategory = null;
        int i = 0;

        PreparedStatement preparedStatement = null;

        try {
            connection = this.newConnection();

            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from vest WHERE createdAt BETWEEN NOW() - INTERVAL 30 DAY AND NOW() order by visits desc");

            while (i < 10 && resultSet.next()){
                i++;
                Vesti vesti = new Vesti(resultSet.getInt("id"), resultSet.getString("title"), resultSet.getString("content"), resultSet.getDate("createdAt"));
                vesti.setVisits(resultSet.getInt("visits"));

                preparedStatement = connection.prepareStatement("select * from users where email = ?");
                preparedStatement.setString(1, resultSet.getString("author"));
                resultSetUser = preparedStatement.executeQuery();

                while (resultSetUser.next()){
                    User user = new User(resultSetUser.getString("email"), resultSetUser.getString("first_name"), resultSetUser.getString("last_name"), resultSetUser.getString("password"));
                    user.setStatus(resultSetUser.getInt("status"));
                    user.setType(resultSetUser.getInt("type"));

                    synchronized (this) {
                        vesti.setAuthor(user);
                    }
                }

                preparedStatement = connection.prepareStatement("select * from kategorija where name = ?");
                preparedStatement.setString(1, resultSet.getString("kategorija"));
                resultSetCategory = preparedStatement.executeQuery();

                while (resultSetCategory.next()){
                    Kategorija category = new Kategorija(resultSetCategory.getString("name"), resultSetCategory.getString("description"));

                    synchronized (this) {
                        vesti.setKategorija(category);
                    }
                }

                vestiList.add(vesti);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return vestiList;
    }


    //     resultSet = statement.executeQuery("select * from vest order by visits desc ");
    @Override
    public Vesti findNews(Integer id) {
        Vesti vesti = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ResultSet resultSetUser = null;
        ResultSet resultSetCategory = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM vest where id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                String title = resultSet.getString("title");
                String content = resultSet.getString("content");
                Date createdAt = resultSet.getDate("createdAt");
                Integer visits = resultSet.getInt("visits");
                visits++;
                preparedStatement = connection.prepareStatement("update vest as v set v.visits = ? where v.id = ?");
                preparedStatement.setInt(1, visits);
                preparedStatement.setInt(2, id);
                preparedStatement.executeUpdate();
                vesti = new Vesti(id, title, content, createdAt,visits);


                preparedStatement = connection.prepareStatement("select * from users where email = ?");
                preparedStatement.setString(1, resultSet.getString("author"));
                resultSetUser = preparedStatement.executeQuery();

                while (resultSetUser.next()){
                    User user = new User(resultSetUser.getString("email"), resultSetUser.getString("first_name"), resultSetUser.getString("last_name"), resultSetUser.getString("password"));
                    user.setStatus(resultSetUser.getInt("status"));
                    user.setType(resultSetUser.getInt("type"));

                    synchronized (this) {
                        vesti.setAuthor(user);
                    }
                }

                preparedStatement = connection.prepareStatement("select * from kategorija where name = ?");
                preparedStatement.setString(1, resultSet.getString("kategorija"));
                resultSetCategory = preparedStatement.executeQuery();

                while (resultSetCategory.next()){
                    Kategorija category = new Kategorija(resultSetCategory.getString("name"), resultSetCategory.getString("description"));

                    synchronized (this) {
                        vesti.setKategorija(category);
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

        return vesti;
    }

    @Override
    public void deleteNews(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("DELETE FROM vest where id = ?");
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

    @Override
    public List<Vesti> allByCategory(String name) {
        List<Vesti> vestiList = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;

        ResultSet resultSet = null;
        ResultSet resultSetAutor = null;
        ResultSet resultSetKategorija = null;

        PreparedStatement preparedStatement = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("select * from vest where kategorija like ? order by createdAt desc");
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Vesti vesti = new Vesti(resultSet.getInt("id"), resultSet.getString("title"), resultSet.getString("content"), resultSet.getDate("createdAt"));
                vesti.setVisits(resultSet.getInt("visits"));

                preparedStatement = connection.prepareStatement("select * from users where email = ?");
                preparedStatement.setString(1, resultSet.getString("author"));
                resultSetAutor = preparedStatement.executeQuery();
                while (resultSetAutor.next()){
                    User user = new User(resultSetAutor.getString("email"), resultSetAutor.getString("first_name"), resultSetAutor.getString("last_name"), resultSetAutor.getString("password"));
                    user.setStatus(resultSetAutor.getInt("status"));
                    user.setType(resultSetAutor.getInt("type"));

                    synchronized (this) {
                        vesti.setAuthor(user);
                    }
                }

                preparedStatement = connection.prepareStatement("select * from kategorija where name = ?");
                preparedStatement.setString(1, resultSet.getString("kategorija"));
                resultSetKategorija = preparedStatement.executeQuery();
                while (resultSetKategorija.next()) {
                    String ime = resultSetKategorija.getString("name");
                    String description = resultSetKategorija.getString("description");

                    Kategorija kategorija = new Kategorija(ime, description);

                    synchronized (this) {
                        vesti.setKategorija(kategorija);
                    }
                }
                vestiList.add(vesti);
            }

            resultSet.close();
            resultSetAutor.close();
            resultSetKategorija.close();
            preparedStatement.close();
            connection.close();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
            this.closeResultSet(resultSetAutor);
            this.closeResultSet(resultSetKategorija);
        }

        return vestiList;
    }

    @Override
    public List<Vesti> allByTag(Integer id) {
        List<Vesti> vestiList = new ArrayList<>();
//        List<Vesti> vestiList = new ArrayList<>();
        Vesti vesti = null;

        Connection connection = null;
        Statement statement = null;

        ResultSet resultSet = null;
        ResultSet resultSet1 = null;
        ResultSet resultSetUser = null;
        ResultSet resultSetCategory = null;

        PreparedStatement preparedStatement = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("select * from tag_vesti where idTag = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
//
            while (resultSet.next()) {

                Integer idVesti = resultSet.getInt("idVest");
                System.out.println(resultSet.getInt("idVest"));
                System.out.println("ispisiiisii");
//                System.out.println();


//                statement = connection.createStatement();
                preparedStatement = connection.prepareStatement("SELECT * FROM vest where id = ? ");
                preparedStatement.setInt(1, idVesti);
                resultSet1 = preparedStatement.executeQuery();
                if(resultSet1.next()) {
                    String title = resultSet1.getString("title");
                    String content = resultSet1.getString("content");
                    Date createdAt = resultSet1.getDate("createdAt");
                    Integer visits = resultSet1.getInt("visits");
                    vesti = new Vesti(idVesti, title, content, createdAt,visits);



                    preparedStatement = connection.prepareStatement("select * from users where email = ?");
                    preparedStatement.setString(1, resultSet1.getString("author"));
                    resultSetUser = preparedStatement.executeQuery();

                    while (resultSetUser.next()){
                        User user = new User(resultSetUser.getString("email"), resultSetUser.getString("first_name"), resultSetUser.getString("last_name"), resultSetUser.getString("password"));
                        user.setStatus(resultSetUser.getInt("status"));
                        user.setType(resultSetUser.getInt("type"));

                        synchronized (this) {
                            vesti.setAuthor(user);
                        }
                    }

                    preparedStatement = connection.prepareStatement("select * from kategorija where name = ?");
                    preparedStatement.setString(1, resultSet1.getString("kategorija"));
                    resultSetCategory = preparedStatement.executeQuery();

                    while (resultSetCategory.next()){
                        Kategorija category = new Kategorija(resultSetCategory.getString("name"), resultSetCategory.getString("description"));

                        synchronized (this) {
                            vesti.setKategorija(category);
                        }
                    }

                }

                vestiList.add(vesti);
                System.out.println("DA L SI USOO");
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return vestiList;
    }


    @Override
    public List<Tag> allTagByNews(Integer id) {
        List<Tag> tagList = new ArrayList<>();

        Connection connection = null;
        ResultSet resultSet = null;

        PreparedStatement preparedStatement = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("select * from tag_vesti where idVest = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Tag tag = tagRepository.findTagById(resultSet.getInt("idTag"));
                tagList.add(tag);
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);

        }

        return tagList;
    }

    @Override
    public List<Komentar> allCommentsByNews(Integer id) {
        List<Komentar> tagList = new ArrayList<>();

        Connection connection = null;
        ResultSet resultSet = null;

        PreparedStatement preparedStatement = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("select * from komentar where vest = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Komentar komentar = commentRepository.findComment(resultSet.getInt("id"));
                tagList.add(komentar);
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);

        }

        return tagList;
    }

}

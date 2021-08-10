package rs.raf.demo.repositories.category;

import rs.raf.demo.entities.Kategorija;
import rs.raf.demo.entities.Vesti;
import rs.raf.demo.repositories.MySqlAbstractRepository;
import rs.raf.demo.repositories.vesti.VestiRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlCategoryRepository extends MySqlAbstractRepository implements CategoryRepository {

    @Override
    public Kategorija addCategory(Kategorija kategorija) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();

            String[] generatedColumns={"name"};
            preparedStatement = connection.prepareStatement("select * from kategorija as k where k.name = ? ");
            preparedStatement.setString(1, kategorija.getName());
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {

                preparedStatement = connection.prepareStatement("INSERT INTO kategorija (name, description) VALUES (?,?)", generatedColumns);
                preparedStatement.setString(1, kategorija.getName());
                preparedStatement.setString(2, kategorija.getDescription());

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

        return kategorija;
    }

    @Override
    public List<Kategorija> allCategory() {
        List<Kategorija> kategorijaList = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from kategorija");
            while (resultSet.next()) {
                Kategorija kategorija = new Kategorija(resultSet.getString("name"), resultSet.getString("description"));
                kategorijaList.add(kategorija);
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return kategorijaList;
    }

    @Override
    public Kategorija findCategory(String name) {
        Kategorija kategorija = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM kategorija as k where k.name = ?");
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                String description = resultSet.getString("description");
                kategorija = new Kategorija(name, description);
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

        return kategorija;
    }

    @Override
    public void deleteCategory(String name) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("DELETE FROM kategorija where name = ?");
            preparedStatement.setString(1, name);
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
    public Kategorija updateCategory(Kategorija kategorija, String name) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();


            if (!(name.equals(kategorija.getName()))) {
                preparedStatement = connection.prepareStatement("select * from kategorija where name = ? ");
                preparedStatement.setString(1, kategorija.getName());
                resultSet = preparedStatement.executeQuery();
            }
            if(resultSet == null || !resultSet.next() || name.equals(kategorija.getName())) {
                preparedStatement = connection.prepareStatement("update kategorija as k set k.name = ?, k.description = ? where k.name = ?");
                preparedStatement.setString(1, kategorija.getName());
                preparedStatement.setString(2, kategorija.getDescription());
                preparedStatement.setString(3, name);
                preparedStatement.executeUpdate();


                preparedStatement.close();
                connection.close();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                this.closeResultSet(resultSet);
            }
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }

        return kategorija;
    }


}

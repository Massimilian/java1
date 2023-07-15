package testArea;

import java.sql.*;

public class SQLTest {
    public static void main(String[] args) {
        Connection connection = null;
        String url = "jdbc:postgresql://127.0.0.1:5432/worklist"; // создаём URL
        String name = "postgres"; // имя пользователя
        String password = "1357"; // пароль (указывали при создании базы данных
        try {
            Class.forName("org.postgresql.Driver"); // активизируем драйвер
            connection = DriverManager.getConnection(url, name, password); // активизируем соединение
            PreparedStatement statement = null;
            statement = connection.prepareStatement( "INSERT INTO test (words) VALUES ('1')");
            ResultSet rs = statement.executeQuery(); // выполняем подготовленный запрос и получаем данные от сервера (строки с id 1 и 2)
//            while (rs.next()) {
//                System.out.println(rs.getInt("id") + " - " + rs.getString("name"));
//            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}

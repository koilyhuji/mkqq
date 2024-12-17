package mkqq.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mkqq.DTO.UserDTO;

public class DBUtils {
    public static <T> ObservableList<T> getAll(String tablename, Class<T> type){
        ObservableList<T> typedList = FXCollections.observableArrayList();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        StringBuilder prequery = new StringBuilder("SELECT * FROM " + tablename);
        String query = prequery.toString();

        try {
            conn = DBSingleton.getInstance().getConnection();
            stmt = conn.prepareStatement(query);
            //stmt.setString(1,tablename);
            rs = stmt.executeQuery();
            Field[] fields = type.getDeclaredFields();
            while (rs.next()) {
                T entity = type.getDeclaredConstructor().newInstance();
                for (Field field : fields) {
                    field.setAccessible(true);
                    String columnName = field.getName().toLowerCase();
                    field.set(entity, rs.getObject(columnName));
                }

                if (type.isInstance(entity)) {
                    typedList.add(type.cast(entity));

                }
            }
        } catch (SQLException | IllegalAccessException | InstantiationException | NoSuchMethodException |
                 InvocationTargetException e) {
            e.printStackTrace();
        }
        try {
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return typedList;
    }

}

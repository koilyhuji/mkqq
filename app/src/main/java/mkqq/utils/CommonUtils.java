package mkqq.utils;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class CommonUtils {
    public static String encodePassword(String password) {
        try {
            // Create an instance of the MessageDigest class with MD5 algorithm
            MessageDigest md = MessageDigest.getInstance("MD5");

            // Update the digest with the password bytes
            md.update(password.getBytes());

            // Generate the hash (MD5 hash in byte array form)
            byte[] byteData = md.digest();

            // Convert the byte array into a hexadecimal string
            StringBuilder sb = new StringBuilder();
            for (byte b : byteData) {
                sb.append(String.format("%02x", b));  // Convert each byte to its hexadecimal representation
            }

            // Return the MD5 hash as a hexadecimal string
            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;  // Return null in case of an error
        }
    }
    public static <T> List<T> ReadExcel(File file, Class<T> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        List<T> objectList = new ArrayList<>();
        try {
            Constructor<T> constructor = clazz.getConstructor();
            T obj = constructor.newInstance();
            Field objecField[] = obj.getClass().getFields();
            FileInputStream fis = new FileInputStream(file);
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                Row row = sheet.getRow(i);

                if (row == null) {
                    continue;
                }
                for (int j = 0 ; j< objecField.length; j++) {
                    field.setAccessible(true);
                    field.set(obj,row.getCell(j).getCellValue());
                }



            }

            objectList.add(obj);
            workbook.close();
            fis.close();

        } catch (Exception ex) {
            ex.printStackTrace();

        }
        return  objectList;
    }
     
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DbAccess;

import Config.SysVar;
import Model.Db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Zito
 */
public class ManageConnection {

    private static String connectionString(String driver) {
        StringBuilder sb = new StringBuilder();

        Db db = Db.getInfoDB();
        if (db != null) {
            if (driver.equalsIgnoreCase(SysVar.driver_MSSQL)) {
                sb.append("jdbc:sqlserver://");
            } else {
                sb.append("jdbc:jtds:sqlserver://");
            }
            sb.append(db.getServer())
                    .append(":").append(db.getPort());
            if (driver.equalsIgnoreCase(SysVar.driver_MSSQL)) {
                sb.append(";DatabaseName=").append(db.getDatabase());
            } else {
                sb.append("/").append(db.getDatabase());
            }
            if (!db.getInstance().isEmpty()) {
                sb.append(";instance=").append(db.getInstance());
            }
            sb.append(";User=").append(db.getUsername())
                    .append(";Password=").append(db.getPassword());
        }
        return sb.toString();

    }

    public static Connection getConnection(String driver) {
        try {
            Class.forName(driver);
            return DriverManager.getConnection(connectionString(driver));
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ManageConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ManageConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}

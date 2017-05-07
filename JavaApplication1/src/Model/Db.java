/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Config.SysVar;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Zito
 */
public class Db {

    String server;
    String port;
    String database;
    String username;
    String password;
    String instance;

    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static Db getInfoDB() {
        Properties p = new Properties();
        Db db = null;
        File file = new File(SysVar.file_DbConfig);
        //System.out.println(file.getAbsolutePath());
        if (file.exists()) {
            try (
                    FileInputStream fis = new FileInputStream(file);) {
                p.load(fis);
                db = new Db();
                db.setDatabase(p.getProperty("database"));
                db.setInstance(p.getProperty("instance"));
                db.setPort(p.getProperty("port"));
                db.setPassword(p.getProperty("password"));
                db.setServer(p.getProperty("server"));
                db.setUsername(p.getProperty("username"));

            } catch (Exception ex) {
                db = null;
                //System.out.println("DB null!");
            }
        }
        return db;
    }

    public static int SetInfoDB(Db db) {
        Properties p = new Properties();
        try (
                FileOutputStream file = new FileOutputStream(SysVar.file_DbConfig);) {
            p.setProperty("server", db.getServer());
            p.setProperty("port", db.getPort());
            p.setProperty("database", db.getDatabase());
            p.setProperty("instance", db.getInstance());
            p.setProperty("username", db.getUsername());
            p.setProperty("password", db.getPassword());
            p.store(file, "Nguyen Duc Tung");

        } catch (IOException ex) {
            return 2;
        }
        return 1;
    }

}

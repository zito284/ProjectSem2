/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Helpers.SqlHelper;
import java.sql.ResultSet;
import java.util.HashMap;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Zito
 */
public class Copies {

    public String Cop_Id;
    public String Book_ISBN;
    public String Cop_No;
    public boolean Cop_Status;
    public boolean Cop_isDeleted;

    public Copies() {

    }

    public static DefaultTableModel getCopyListByISBN(String ISBN) {
        return SqlHelper.getDefaultTableModel("Copies_getFreeCopiesList", ISBN);
    }

    public static Copies getLastestIsFree(String ISBN, HashMap Cop_Id) {
        Copies cop = null;
        ResultSet rs = null;
        try {
            rs = SqlHelper.getResultSet("Copies_getLastestIsFree", ISBN, Cop_Id);
            if (rs.next()) {
                cop = new Copies();
                cop.Book_ISBN = rs.getString("Book_ISBN");
                cop.Cop_Id = rs.getString("Cop_Id");
                cop.Cop_Status = rs.getBoolean("Cop_Status");
                cop.Cop_No = rs.getString("Cop_No");

            }
        } catch (Exception e) {
            SqlHelper.closeConnection(rs);
            cop = null;
        }
        return cop;
    }

    public static int Copies_Insert(Copies obj) {
        return SqlHelper.executeNonQuery("Copies_Insert", obj.Book_ISBN, obj.Cop_No);
    }

    public static int Copies_DeleteCopiesByCopyId(String Cop_Id) {
        return SqlHelper.executeNonQuery("Copies_DeleteCopiesByCopyNo", Cop_Id);
    }
}

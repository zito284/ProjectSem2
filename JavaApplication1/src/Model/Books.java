/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Helpers.SqlHelper;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Zito
 */
public class Books {

    public String Book_ISBN;
    public String Book_Title;
    public String Book_Publisher;
    public String Book_Author;
    public float Book_Price;
    public String Book_Content;
    public String Cat_Id;
    public String Book_Language;
    public String Book_ImageFile;
    public String Book_CreateDate;
    public boolean Book_isDeleted;
    public String Cat_Name;
    public int Book_Count;

    public Books() {

    }

    public static Books getByISBN(String ISBN) {
        Books obj = null;
        ResultSet rs = null;
        try {
            rs = SqlHelper.getResultSet("Books_getByISBN", ISBN);
            if (rs.next()) {
                obj = new Books();
                obj.Book_ISBN = rs.getString("Book_ISBN");
                obj.Book_Title = rs.getString("Book_Title");
                obj.Book_Publisher = rs.getString("Book_Publisher");
                obj.Book_Author = rs.getString("Book_Author");
                obj.Book_Price = rs.getFloat("Book_Price");
                obj.Book_Content = rs.getString("Book_Content");
                obj.Cat_Name = rs.getString("Cat_Name");
                obj.Book_Language = rs.getString("Book_Language");
                obj.Book_ImageFile = rs.getString("Book_ImageFile");
                obj.Book_CreateDate = rs.getString("Book_CreateDate");
                obj.Book_isDeleted = rs.getBoolean("Book_isDeleted");
                obj.Book_Count = rs.getInt("Book_Count");
            }
        } catch (Exception e) {
            SqlHelper.closeConnection(rs);
            obj = null;
        }
        return obj;
    }
    
    public static Books getByISBNFree(String ISBN) {
        Books obj = null;
        ResultSet rs = null;
        try {
            rs = SqlHelper.getResultSet("Books_getByISBNFree", ISBN);
            if (rs.next()) {
                obj = new Books();
                obj.Book_ISBN = rs.getString("Book_ISBN");
                obj.Book_Title = rs.getString("Book_Title");
                obj.Book_Publisher = rs.getString("Book_Publisher");
                obj.Book_Author = rs.getString("Book_Author");
                obj.Book_Price = rs.getFloat("Book_Price");
                obj.Book_Content = rs.getString("Book_Content");
                obj.Cat_Name = rs.getString("Cat_Name");
                obj.Book_Language = rs.getString("Book_Language");
                obj.Book_ImageFile = rs.getString("Book_ImageFile");
                obj.Book_CreateDate = rs.getString("Book_CreateDate");
                obj.Book_isDeleted = rs.getBoolean("Book_isDeleted");
                obj.Book_Count = rs.getInt("Book_Count");
            }
        } catch (Exception e) {
            e.printStackTrace();
            SqlHelper.closeConnection(rs);
            obj = null;
        }
        return obj;
    }

    public static int Books_Insert(Books obj) {
        return SqlHelper.executeNonQuery("Books_Insert",
                obj.Book_ISBN,
                obj.Book_Title,
                obj.Book_Publisher,
                obj.Book_Author,
                obj.Book_Price,
                obj.Book_Content,
                obj.Cat_Id,
                obj.Book_Language,
                obj.Book_ImageFile,
                obj.Book_isDeleted);
    }

    public static int Books_GetNumberOfBooksInCategory(String catname) {
        return SqlHelper.getResultSetRows("Books_getBookListByCatename", catname);
    }

    public static DefaultTableModel Books_getBookListByCatename(String catname) {
        DefaultTableModel tbl = SqlHelper.getDefaultTableModel("Books_getBookListByCatename", catname);
        return tbl;
    }

    public static int Books_DeleteBookByISBN(String bookisbn) {
        return SqlHelper.executeNonQuery("Books_DeleteBookByISBN", bookisbn);
    }

    public static int Books_Update(Books obj, String bookisbn) {
        return SqlHelper.executeNonQuery("Books_Update", bookisbn, obj.Book_Title, obj.Book_Publisher,
                obj.Book_Author,
                obj.Book_Price,
                obj.Book_Content,
                obj.Cat_Id,
                obj.Book_Language,
                obj.Book_ImageFile,
                obj.Book_isDeleted);
    }

    public static DefaultTableModel Books_searchBook(String title,
            String publisher,
            String author,
            boolean isdelete) {
        DefaultTableModel tbl = SqlHelper.getDefaultTableModel("Books_searchBook",
                title,
                publisher,
                author,
                isdelete);
        return tbl;
    }
}

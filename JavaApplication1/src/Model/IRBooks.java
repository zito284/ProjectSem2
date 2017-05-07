/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Helpers.SqlHelper;
import java.util.HashMap;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Zito
 */
public class IRBooks {

    public Books book;
    public String IRBook_Id;
    public String Mem_Id;

    public String Mem_FullName;
    public String IRBook_DueDate;
    public String IRBook_CreateDate;
    public int IRBook_LateDay;
    public Copies copy;

    public IRBooks() {

    }

    public static DefaultTableModel getListBookByFilter(Books b) {
        DefaultTableModel tblM = SqlHelper.getDefaultTableModel("Books_getListBookByFilter",
                b.Book_ISBN,
                b.Book_Title,
                b.Book_Author);
        return tblM;
    }

    public static DefaultTableModel getListBookNotReturnByMemberNo(String Mem_No) {
        DefaultTableModel tblM = SqlHelper.getDefaultTableModel("IRBooks_getListBookNotReturnByMemberNo", Mem_No);
        return tblM;
    }

    public static DefaultTableModel getListBookNotReturn(String Mem_No) {
        DefaultTableModel tblM = SqlHelper.getDefaultTableModel("IRBooks_getListBookNotReturn", Mem_No);
        return tblM;
    }

    public static DefaultTableModel getListBookByMemNo(String Mem_No) {
        DefaultTableModel tblM = SqlHelper.getDefaultTableModel("IRBooks_getListBookByMemNo", Mem_No);
        return tblM;
    }

    public static DefaultTableModel ListByMemberNo(String Mem_No) {
        DefaultTableModel tblM = SqlHelper.getDefaultTableModel("Fines_ListByMemberNo", Mem_No);
        return tblM;
    }

    public static int IssueBook(HashMap Cop_No, String Mem_Id) {
        return SqlHelper.executeNonQuery("IRBooks_IssueBook", Cop_No, Mem_Id);
    }

    public static int ReturnBook(HashMap IRDetail) {
        return SqlHelper.executeNonQuery("IRBooks_ReturnBook", IRDetail);
    }

    public static int PaidFine(String IRBookDetail_Id, float Fine_ammount) {
        return SqlHelper.executeNonQuery("Fines_Paid", IRBookDetail_Id, Fine_ammount);
    }
}

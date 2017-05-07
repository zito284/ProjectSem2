package Model;

import Helpers.SqlHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import org.openide.util.Exceptions;

public class Categories {

    public String Cat_Id;
    public String Cat_Name;
    public boolean Cat_isDelete;
    public String Cat_Description;
    public static Categories objCate;

    public Categories() {

    }

    public Categories(String Cat_Name, boolean Cat_isDelete, String Cat_Description) {
        this.Cat_Id = Cat_Id;
        this.Cat_Name = Cat_Name;
        this.Cat_isDelete = Cat_isDelete;
        this.Cat_Description = Cat_Description;
    }

    public static DefaultTableModel Categories_getCategoryListWithBookNumber() {
        DefaultTableModel tbl = SqlHelper.getDefaultTableModel("Categories_getCategoryListWithBookNumber");
        return tbl;
    }

    public static int Categories_Insert(Categories obj) {
        return SqlHelper.executeNonQuery("Categories_Insert", obj.Cat_Name, obj.Cat_isDelete, obj.Cat_Description);
    }

    public static Categories Categories_getCategoryByCateId(String cateid) {
        ResultSet rs;
        objCate = new Categories();
        try {
            rs = SqlHelper.getResultSet("Categories_getCategoryByCateId", cateid);
            if (rs.next()) {
                objCate.Cat_Id = cateid;
                objCate.Cat_Name = rs.getString("Cat_Name");
                objCate.Cat_isDelete = rs.getBoolean("Cat_isDelete");
                objCate.Cat_Description = rs.getString("Cat_Description");
            }
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
        return objCate;
    }

    public static int Categories_Update(Categories obj, String cateid) {
        return SqlHelper.executeNonQuery("Categories_Update", cateid,
                obj.Cat_Name, obj.Cat_isDelete, obj.Cat_Description);
    }

    public static int Categories_Lock(String cateid) {
        return SqlHelper.executeNonQuery("Categories_Lock", cateid);
    }

    public static DefaultTableModel Categories_getCategoryList() {
        DefaultTableModel tbl = SqlHelper.getDefaultTableModel("Categories_getCategoryList");
        return tbl;
    }

    public static DefaultComboBoxModel Categories_getCategoryCombobox() {
        DefaultComboBoxModel cbo = SqlHelper.getDefaultComboBoxModel("Categories_getCategoryList");
        return cbo;
    }

    public static Categories Categories_getCategoryByCateName(String catename) {
        ResultSet rs;
        objCate = new Categories();
        try {
            rs = SqlHelper.getResultSet("Categories_getCategoryByCateName", catename);
            if (rs.next()) {
                objCate.Cat_Name = catename;
                objCate.Cat_Id = rs.getString("Cat_Id");
                objCate.Cat_Description = rs.getString("Cat_Description");
                objCate.Cat_isDelete = rs.getBoolean("Cat_isDelete");
            }
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
        return objCate;
    }
}

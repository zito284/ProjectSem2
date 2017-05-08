/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helpers;

import DbAccess.ManageConnection;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Zito
 */
public class SqlHelper {

    public static final String myConnectionDriver = Config.SysVar.driver_MSSQL;

    public static DefaultComboBoxModel getDefaultComboBoxModel(String cmdText) {
        DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
        ResultSet rs = null;
        try {
            rs = getResultSet(cmdText);
            while (rs.next()) {
                dcbm.addElement(rs.getString(1));
            }
            return dcbm;
        } catch (Exception e) {
            return null;
        } finally {
            closeConnection(rs);
        }
    }

    public static DefaultComboBoxModel getDefaultComboBoxModel(String cmdText, Object... cmdParams) {
        DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
        ResultSet rs = null;
        try {
            rs = getResultSet(cmdText, cmdParams);
            while (rs.next()) {
                dcbm.addElement(rs.getString(1));
            }
            return dcbm;
        } catch (Exception e) {
            return null;
        } finally {
            closeConnection(rs);
        }
    }

    public static DefaultTableModel getDefaultTableModel(String cmdText) {
        ResultSet rs = getResultSet(cmdText);
        DefaultTableModel tm = getDefaultTableModel(rs);
        closeConnection(rs);
        return tm;
    }

    public static DefaultTableModel getDefaultTableModel(String cmdText, Object... cmdParams) {
        ResultSet rs = getResultSet(cmdText, cmdParams);
        DefaultTableModel tm = getDefaultTableModel(rs);
        closeConnection(rs);
        return tm;
    }

    public static int executeNonQuery(String cmdText) {
        CallableStatement stmt = getCallableStatement(cmdText);
        if (stmt == null) {
            return -2;
        }
        int i;
        try {
            stmt.executeUpdate();
            i = stmt.getInt(1);
        } catch (SQLException ex) {
            Logger.getLogger(SqlHelper.class.getName()).log(Level.SEVERE, null,
                    ex);
            i = -1;
        } finally {
            closeConnection(stmt);
        }
        return i;
    }

    public static int executeNonQuery(String cmdText, Object... cmdParams) {
        CallableStatement stmt = getCallableStatement(cmdText, cmdParams);
        if (stmt == null) {
            return -2;
        }
        int i;
        try {
            stmt.executeUpdate();
            i = stmt.getInt(1);
        } catch (SQLException ex) {
            Logger.getLogger(SqlHelper.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            i = -1;
        } finally {
            closeConnection(stmt);
        }
        return i;
    }

//use for get row numbers
    public static int getResultSetRows(String cmdText, Object... cmdParams) {
        cmdText = getQueryStore(cmdText, cmdParams);
        PreparedStatement pstmt = getPreparedStatement(cmdText, cmdParams);
        if (pstmt == null) {
            return 0;
        }
        try {
            ResultSet rs = pstmt.executeQuery();
            int count = 0;
            while (rs.next()) {
                count++;
            }
            pstmt.close();
            return count;
        } catch (SQLException ex) {
            Logger.getLogger(SqlHelper.class.getName()).log(Level.SEVERE, null, ex);
            closeConnection(pstmt);
        }
        return 0;
    }

    public static ResultSet getResultSet(String cmdText) {
        cmdText = getQueryStore(cmdText);
        PreparedStatement pstmt = getPreparedStatement(cmdText);
        if (pstmt == null) {
            return null;
        }
        try {
            return pstmt.executeQuery();

        } catch (SQLException ex) {
            Logger.getLogger(SqlHelper.class.getName()).log(Level.SEVERE, null, ex);
            closeConnection(pstmt);
        }
        return null;
    }

    public static ResultSet getResultSet(String cmdText, Object... cmdParams) {
        cmdText = getQueryStore(cmdText, cmdParams);
        PreparedStatement pstmt = getPreparedStatement(cmdText, cmdParams);
        if (pstmt == null) {
            return null;
        }
        try {
            return pstmt.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(SqlHelper.class.getName()).log(Level.SEVERE, null, ex);
            closeConnection(pstmt);
        }
        return null;
    }

    public static Object execScalar(String cmdText) {
        ResultSet rs = getResultSet(cmdText);
        Object obj = getScalar(rs);
        closeConnection(rs);
        return obj;
    }

    public static Object execScalar(String cmdText, Object... cmdParams) {
        ResultSet rs = getResultSet(cmdText, cmdParams);
        Object obj = getScalar(rs);
        closeConnection(rs);
        return obj;
    }

    private static Object getScalar(ResultSet rs) {
        if (rs == null) {
            return null;
        }
        Object obj = null;
        try {
            if (rs.next()) {
                obj = rs.getObject(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SqlHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return obj;
    }

    private static String getQueryStore(String cmdText, Object... cmdParams) {
        StringBuilder strSql = new StringBuilder();
        strSql.append("{call ").append(cmdText);
        int t = 0;
        if (cmdParams.length > 0) {
            addParam(strSql, cmdParams);
            strSql.append(")");
        }
        strSql.append("}");
        return strSql.toString();
    }

    private static String getQueryStoreReturn(String cmdText, Object... cmdParams) {
        StringBuilder strSql = new StringBuilder();
        strSql.append("{? = call ").append(cmdText);
        int t = 0;
        if (cmdParams != null && cmdParams.length > 0) {
            addParam(strSql, cmdParams);
            strSql.append(")");
        }
        strSql.append("}");
        return strSql.toString();
    }

    private static PreparedStatement getPreparedStatement(String cmdText) {
        Connection conn = ManageConnection.getConnection(myConnectionDriver);
        if (conn == null) {
            return null;
        }
        try {
            return conn.prepareStatement(cmdText, ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

        } catch (SQLException ex) {
            Logger.getLogger(SqlHelper.class.getName()).log(Level.SEVERE, null, ex);
            close(conn);
        }
        return null;
    }

    private static PreparedStatement getPreparedStatement(String cmdText, Object... cmdParams) {
        Connection conn = ManageConnection.getConnection(myConnectionDriver);
        if (conn == null) {
            return null;
        }
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(cmdText,
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            return pstmt;
        } catch (SQLException ex) {
            Logger.getLogger(SqlHelper.class.getName()).log(Level.SEVERE, null, ex);
            close(pstmt);
        }
        return null;
    }

    private static CallableStatement getCallableStatement(String cmdText, Object... cmdParams) {

        CallableStatement cstmt = null;
        try {
            Connection conn = ManageConnection.getConnection(myConnectionDriver);
            cmdText = getQueryStoreReturn(cmdText, cmdParams);

            cstmt = conn.prepareCall(cmdText);
            cstmt.registerOutParameter(1, java.sql.Types.INTEGER);

            return cstmt;
        } catch (Exception ex) {
            Logger.getLogger(SqlHelper.class.getName()).log(Level.SEVERE, null, ex);
            close(cstmt);
        }
        return null;

    }

    private static void addParam(StringBuilder strSql, Object... cmdParams) {
        int t = 0;
        for (Object obj : cmdParams) {
            if (t == 0) {
                strSql.append("(");
            } else {
                strSql.append(",");
            }

            if (obj instanceof String) {
                strSql.append("'").append(String.valueOf(obj)).append("'");
            } else if (obj instanceof Integer) {
                strSql.append((Integer) obj);
            } else if (obj instanceof Boolean) {
                strSql.append((Boolean) obj ? 1 : 0);
            } else if (obj instanceof Float) {
                strSql.append((Float) obj);
            } else if (obj instanceof HashMap) {
                strSql.append(StringHelper.getStringByMap((HashMap) obj));
            }
            t++;
        }
    }

    private static DefaultTableModel getDefaultTableModel(ResultSet rs) {
        if (rs == null) {
            return null;
        }
        ResultSetMetaData rsm;
        String[] columnNames = null;
        Object[][] data = null;
        DefaultTableModel model;
        try {

            rsm = rs.getMetaData();
            columnNames = new String[rsm.getColumnCount()];
            for (int i = 0; i < rsm.getColumnCount(); i++) {
                columnNames[i] = rsm.getColumnName(i + 1);
            }
            int row = 0;
            int colum = 0;
            int columCount = rsm.getColumnCount();
            rs.last();
            int rowCount = rs.getRow();
            rs.beforeFirst();
            data = new Object[rowCount][columCount];
            while (rs.next()) {
                for (colum = 0; colum < rsm.getColumnCount(); colum++) {
                    data[row][colum] = rs.getObject(colum + 1);
                }
                row++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SqlHelper.class.getName()).log(Level.SEVERE, null,
                    ex);
            return null;
        }
        model = new DefaultTableModel(data, columnNames) {
            @SuppressWarnings("unchecked")
            @Override
            public Class getColumnClass(int c) {

                if (dataVector.isEmpty() == false && getValueAt(0, c) != null) {
                    return getValueAt(0, c).getClass();
                } else {
                    return Object.class;
                }
            }
        };
        return model;
    }

    private static void close(Object obj) {
        if (obj == null) {
            return;
        }
        try {
            if (obj instanceof Statement) {
                ((Statement) obj).close();
            } else if (obj instanceof PreparedStatement) {
                ((PreparedStatement) obj).close();
            } else if (obj instanceof ResultSet) {
                ((ResultSet) obj).close();
            } else if (obj instanceof Connection) {
                ((Connection) obj).close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(SqlHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void closeConnection(Object obj) {
        if (obj == null) {
            return;
        }
        try {
            if (obj instanceof Statement) {
                ((Statement) obj).getConnection().close();
            } else if (obj instanceof PreparedStatement) {
                ((PreparedStatement) obj).getConnection().close();
            } else if (obj instanceof ResultSet) {
                ((ResultSet) obj).getStatement().getConnection().close();
            } else if (obj instanceof Connection) {
                ((Connection) obj).close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(SqlHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

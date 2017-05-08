package form.member;

import ExSwing.ClPanelTransparent;
import Helpers.Fine;
import Helpers.UIHelper;
import Model.IRBooks;
import Helpers.MessageHandle;
import static java.awt.Image.SCALE_SMOOTH;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.openide.util.Exceptions;

public class Members extends javax.swing.JFrame {

    DefaultTableModel tableModel;
    Vector row;

    public Members() {
        initComponents();
        lblFileName.setVisible(false);
        jLabel7.setVisible(false);
        txtID.setVisible(false);
        tblFine.setDefaultEditor(Object.class, null);
        tblIssued.setDefaultEditor(Object.class, null);
        tblMemList.setDefaultEditor(Object.class, null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        UIHelper.bindBackground(pnlBackground);
        this.setTitle("Member Manage");
        start();

    }

    public void start() {
        setNormalMode();
        getList();
    }

    private void getList() {
        tblMemList.setModel(Model.Members.Mems_getMemberList());
        tblMemList.removeColumn(tblMemList.getColumnModel().getColumn(0));
    }

    public void setNormalMode() {
        txtID.setEnabled(false);
        txtNo.setEnabled(false);
        btnChange.setEnabled(false);
        txtFirstname.setEnabled(false);
        txtLastname.setEnabled(false);
        txtPhone.setEnabled(false);
        txtAddress.setEnabled(false);
        txtDepartment.setEnabled(false);
        lblRegdate.setEnabled(false);
        cbStatus.setEnabled(false);

        txtID.setEditable(false);
        txtNo.setEditable(false);
        txtFirstname.setEditable(false);
        txtLastname.setEditable(false);
        txtPhone.setEditable(false);
        txtDepartment.setEditable(false);
        txtAddress.setEditable(false);
        cbStatus.setEditable(false);

        lblFileName.setText(null);
        txtID.setText(null);
        txtNo.setText(null);
        txtFirstname.setText(null);
        txtLastname.setText(null);
        txtPhone.setText(null);
        txtDepartment.setText(null);
        txtAddress.setText(null);
        lblRegdate.setText(null);

        lblMemAvatar.setIcon(null);
        btnDelete.setEnabled(false);
        btnChange.setEnabled(false);
        btnUpdate.setEnabled(false);

        btnSaveUpdate.setVisible(false);
        btnSaveAdd.setEnabled(false);
        btnSaveAdd.setVisible(true);
        btnCancel.setEnabled(false);

        tblMemList.clearSelection();
    }

    public void setSelectedMode() {
        txtID.setEnabled(false);
        txtNo.setEnabled(false);
        btnChange.setEnabled(false);
        txtFirstname.setEnabled(true);
        txtLastname.setEnabled(true);
        txtPhone.setEnabled(true);
        txtDepartment.setEnabled(true);
        txtAddress.setEnabled(true);
        lblRegdate.setEnabled(true);
        cbStatus.setEnabled(true);

        txtID.setEditable(false);
        txtNo.setEditable(false);
        txtFirstname.setEditable(false);
        txtLastname.setEditable(false);
        txtPhone.setEditable(false);
        txtDepartment.setEditable(false);
        txtAddress.setEditable(false);
        cbStatus.setEditable(false);
        cbStatus.setEnabled(false);

        btnDelete.setEnabled(true);
        btnChange.setEnabled(false);
        btnUpdate.setEnabled(true);

        btnSaveUpdate.setVisible(false);
        btnSaveAdd.setVisible(true);
        btnSaveAdd.setEnabled(false);
        btnCancel.setEnabled(false);
    }

    public void setUpdateMode() {
        txtID.setEnabled(false);
        txtNo.setEnabled(false);
        btnChange.setEnabled(true);
        txtFirstname.setEnabled(true);
        txtLastname.setEnabled(true);
        txtPhone.setEnabled(true);
        txtDepartment.setEnabled(true);
        txtAddress.setEnabled(true);
        lblRegdate.setEnabled(true);
        cbStatus.setEnabled(true);

        txtID.setEditable(false);
        txtNo.setEditable(true);
        txtFirstname.setEditable(true);
        txtLastname.setEditable(true);
        txtPhone.setEditable(true);
        txtDepartment.setEditable(true);
        txtAddress.setEditable(true);
        cbStatus.setEditable(true);

        btnDelete.setEnabled(false);
        btnChange.setEnabled(true);
        btnUpdate.setEnabled(false);

        btnSaveUpdate.setVisible(true);
        btnSaveAdd.setVisible(false);
        btnCancel.setEnabled(true);
    }

    public void setAddNewMode() {
        txtID.setEnabled(false);
        txtNo.setEnabled(false);
        btnChange.setEnabled(true);
        txtFirstname.setEnabled(true);
        txtLastname.setEnabled(true);
        txtPhone.setEnabled(true);
        txtDepartment.setEnabled(true);
        txtAddress.setEnabled(true);
        lblRegdate.setEnabled(true);
        cbStatus.setEnabled(true);

        txtID.setEditable(false);
        txtNo.setEditable(true);
        txtFirstname.setEditable(true);
        txtLastname.setEditable(true);
        txtPhone.setEditable(true);
        txtDepartment.setEditable(true);
        txtAddress.setEditable(true);
        cbStatus.setEnabled(false);

        txtID.setText("Auto Generate");
        txtNo.setText("Auto Generate");
        txtFirstname.setText(null);
        txtLastname.setText(null);
        txtPhone.setText(null);
        txtDepartment.setText(null);
        txtAddress.setText(null);
        lblRegdate.setText(null);

        lblMemAvatar.setIcon(null);
        btnDelete.setEnabled(false);
        btnChange.setEnabled(true);
        btnUpdate.setEnabled(false);

        btnSaveUpdate.setVisible(false);
        btnSaveAdd.setVisible(true);
        btnSaveAdd.setEnabled(true);
        btnCancel.setEnabled(true);

        tblMemList.clearSelection();
    }

    private static void copyFile(File source, File dest) throws IOException {
        InputStream input = null;
        OutputStream output = null;
        try {
            input = new FileInputStream(source);
            output = new FileOutputStream(dest);
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buf)) > 0) {
                output.write(buf, 0, bytesRead);
            }
        } finally {
            input.close();
            output.close();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton5 = new javax.swing.JButton();
        pnlBackground = new javax.swing.JPanel();
        jPanel3 = new ClPanelTransparent();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtSearchNo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtSearchName = new javax.swing.JTextField();
        lblFileName = new javax.swing.JLabel();
        pnlUpdate = new ClPanelTransparent();
        pnlProfile = new javax.swing.JPanel();
        txtID = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtFirstname = new javax.swing.JTextField();
        txtLastname = new javax.swing.JTextField();
        txtPhone = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        cbStatus = new javax.swing.JComboBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAddress = new javax.swing.JTextArea();
        jLabel16 = new javax.swing.JLabel();
        txtDepartment = new javax.swing.JTextField();
        btnSaveAdd = new javax.swing.JButton();
        btnSaveUpdate = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        lblRegdate = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtNo = new javax.swing.JTextField();
        lblMemAvatar = new javax.swing.JLabel();
        btnChange = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jPanel4 = new ClPanelTransparent();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblMemList = new javax.swing.JTable();
        btnAdd = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblFine = new javax.swing.JTable();
        lblTotalFine = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblIssued = new javax.swing.JTable();

        jButton5.setText("jButton5");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Members Managment");
        setMinimumSize(new java.awt.Dimension(1110, 620));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(java.awt.Color.yellow);
        jLabel1.setText("Search Member");

        jLabel6.setText("No:");

        txtSearchNo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchNoKeyReleased(evt);
            }
        });

        jLabel2.setText("Name:");

        txtSearchName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchNameKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(txtSearchNo, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(txtSearchName, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(lblFileName)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtSearchNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(txtSearchName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(3, 3, 3))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(lblFileName)
                        .addContainerGap())))
        );

        jLabel7.setText("ID");

        jLabel8.setText("First Name");

        jLabel9.setText("Last Name");

        jLabel10.setText("Phone");

        jLabel11.setText("Department");

        jLabel14.setText("Date");

        jLabel15.setText("Status");

        cbStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Active", "Inactive" }));

        txtAddress.setColumns(20);
        txtAddress.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        txtAddress.setLineWrap(true);
        txtAddress.setRows(5);
        txtAddress.setText("\n");
        txtAddress.setWrapStyleWord(true);
        txtAddress.setAutoscrolls(false);
        jScrollPane2.setViewportView(txtAddress);

        jLabel16.setText("Address");

        btnSaveAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Save.png"))); // NOI18N
        btnSaveAdd.setText("Save");
        btnSaveAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveAddActionPerformed(evt);
            }
        });

        btnSaveUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Save.png"))); // NOI18N
        btnSaveUpdate.setText("Save");
        btnSaveUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveUpdateActionPerformed(evt);
            }
        });

        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Cancel.png"))); // NOI18N
        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        jLabel3.setText("No");

        javax.swing.GroupLayout pnlProfileLayout = new javax.swing.GroupLayout(pnlProfile);
        pnlProfile.setLayout(pnlProfileLayout);
        pnlProfileLayout.setHorizontalGroup(
            pnlProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlProfileLayout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addComponent(btnSaveUpdate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSaveAdd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCancel)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(pnlProfileLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel8)
                        .addComponent(jLabel9)
                        .addComponent(jLabel10)
                        .addComponent(jLabel11)
                        .addComponent(jLabel16))
                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(pnlProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbStatus, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtPhone)
                    .addComponent(txtLastname)
                    .addComponent(txtDepartment)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                    .addComponent(lblRegdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtFirstname))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(pnlProfileLayout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(pnlProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel3))
                .addGap(24, 24, 24)
                .addGroup(pnlProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtID)
                    .addComponent(txtNo))
                .addContainerGap())
        );
        pnlProfileLayout.setVerticalGroup(
            pnlProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlProfileLayout.createSequentialGroup()
                .addGroup(pnlProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFirstname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtLastname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtDepartment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel14)
                    .addComponent(lblRegdate, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSaveAdd)
                    .addComponent(btnSaveUpdate)
                    .addComponent(btnCancel))
                .addContainerGap(57, Short.MAX_VALUE))
        );

        lblMemAvatar.setPreferredSize(new java.awt.Dimension(140, 140));

        btnChange.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Picture.png"))); // NOI18N
        btnChange.setText("Change");
        btnChange.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChangeActionPerformed(evt);
            }
        });

        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Update.png"))); // NOI18N
        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Delete.png"))); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlUpdateLayout = new javax.swing.GroupLayout(pnlUpdate);
        pnlUpdate.setLayout(pnlUpdateLayout);
        pnlUpdateLayout.setHorizontalGroup(
            pnlUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlUpdateLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMemAvatar, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
                    .addComponent(btnChange, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(pnlProfile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        pnlUpdateLayout.setVerticalGroup(
            pnlUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlUpdateLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(lblMemAvatar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnChange)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUpdate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDelete)
                .addGap(91, 91, 91))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlUpdateLayout.createSequentialGroup()
                .addContainerGap(37, Short.MAX_VALUE)
                .addComponent(pnlProfile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tblMemList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "First Name", "Last Name", "Phone", "Department", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblMemList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMemListMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblMemList);
        if (tblMemList.getColumnModel().getColumnCount() > 0) {
            tblMemList.getColumnModel().getColumn(0).setResizable(false);
            tblMemList.getColumnModel().getColumn(0).setPreferredWidth(100);
            tblMemList.getColumnModel().getColumn(1).setResizable(false);
            tblMemList.getColumnModel().getColumn(1).setPreferredWidth(20);
            tblMemList.getColumnModel().getColumn(2).setResizable(false);
            tblMemList.getColumnModel().getColumn(2).setPreferredWidth(80);
            tblMemList.getColumnModel().getColumn(3).setResizable(false);
            tblMemList.getColumnModel().getColumn(3).setPreferredWidth(150);
            tblMemList.getColumnModel().getColumn(4).setResizable(false);
            tblMemList.getColumnModel().getColumn(4).setPreferredWidth(20);
        }

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 677, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Add.png"))); // NOI18N
        btnAdd.setText("New Member");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel19.setForeground(java.awt.Color.yellow);
        jLabel19.setText("Member List");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAdd)))
                .addGap(20, 20, 20))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(btnAdd))
                .addGap(8, 8, 8)
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(22, 22, 22))
        );

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel20.setForeground(java.awt.Color.yellow);
        jLabel20.setText("Fine history");

        tblFine.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblFine);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 661, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 11, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        lblTotalFine.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTotalFine.setForeground(new java.awt.Color(255, 0, 0));
        lblTotalFine.setText("Total: $");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblTotalFine)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(lblTotalFine))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel21.setForeground(java.awt.Color.yellow);
        jLabel21.setText("Book issued");

        tblIssued.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(tblIssued);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 709, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel21)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlBackgroundLayout = new javax.swing.GroupLayout(pnlBackground);
        pnlBackground.setLayout(pnlBackgroundLayout);
        pnlBackgroundLayout.setHorizontalGroup(
            pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlBackgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlBackgroundLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(pnlUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        pnlBackgroundLayout.setVerticalGroup(
            pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBackgroundLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 458, Short.MAX_VALUE)
                .addGroup(pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlBackgroundLayout.createSequentialGroup()
                    .addContainerGap(55, Short.MAX_VALUE)
                    .addGroup(pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(pnlUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap(219, Short.MAX_VALUE)))
        );

        getContentPane().add(pnlBackground, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        setAddNewMode();
        txtFirstname.requestFocus();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        setSelectedMode();
        setUpdateMode();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnSaveAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveAddActionPerformed
        Model.Members obj;
        obj = new Model.Members();

        obj.Mem_FirstName = txtFirstname.getText();
        obj.Mem_LastName = txtLastname.getText();
        obj.Mem_Phone = txtPhone.getText();
        obj.Mem_Address = txtAddress.getText();
        obj.Mem_Dep = txtDepartment.getText();

        if (txtFirstname.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "First Name cannot be NULL");
            txtFirstname.requestFocus();
            return;
        } else if (txtFirstname.getText().length() > 30) {
            JOptionPane.showMessageDialog(null, "First Name cannot be longer than 30 chars");
            txtFirstname.requestFocus();
            return;
        }
        if (txtLastname.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Last Name cannot be NULL");
            txtLastname.requestFocus();
            return;
        } else if (txtLastname.getText().length() > 50) {
            JOptionPane.showMessageDialog(null, "Last Name cannot be longer than 50 chars");
            txtLastname.requestFocus();
            return;
        }
        if (txtDepartment.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Department cannot be NULL");
            txtDepartment.requestFocus();
            return;
        } else if (txtDepartment.getText().length() > 50) {
            JOptionPane.showMessageDialog(null, "Department cannot be longer than 50 chars");
            txtDepartment.requestFocus();
            return;
        }
        if (cbStatus.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(null, "Please choose a Status");
            cbStatus.requestFocus();
            return;
        }
        if ((String) cbStatus.getSelectedItem() == "Active") {
            obj.Mem_Status = true;
        } else {
            obj.Mem_Status = false;
        }
        if (txtAddress.getText().isEmpty()) {
            obj.Mem_Address = "";
        }
        if (lblMemAvatar.getIcon() == null) {
            obj.Mem_ImageFile = "imgMem/MemNoAvatar.png";
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String newfilename = sdf.format(Calendar.getInstance().getTime());
            File labelicon = new File(lblFileName.getText());
            File desfile = new File("imgMem\\" + newfilename + "_" + labelicon.getName());
            try {
                copyFile(labelicon, desfile);
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
            obj.Mem_ImageFile = "imgMem/" + desfile.getName();
        }
        int rt = Model.Members.Members_Insert(obj);
        if (rt == 1) {
            setAddNewMode();
            getList();
        } else if (rt == 0) {
            txtNo.requestFocus();
        } else if (rt == 3) {
            txtDepartment.requestFocus();
        }
        MessageHandle.showMessage(MessageHandle.Obj_Member, MessageHandle.Action_insert, rt);
    }//GEN-LAST:event_btnSaveAddActionPerformed

    private void btnChangeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChangeActionPerformed

        JFileChooser fc = new JFileChooser();
        fc.showOpenDialog(null);
        File sourcefile = fc.getSelectedFile();
        ImageIcon newIcon = new ImageIcon(sourcefile.getPath());
        lblMemAvatar.setIcon(new ImageIcon(newIcon.getImage().getScaledInstance(lblMemAvatar.getWidth(), lblMemAvatar.getHeight(), SCALE_SMOOTH)));
        lblFileName.setText(sourcefile.getPath());
    }//GEN-LAST:event_btnChangeActionPerformed

    private void tblMemListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMemListMouseClicked
        setSelectedMode();
        int line = tblMemList.getSelectedRow();
        DefaultTableModel tbm = new DefaultTableModel();
        tbm = (DefaultTableModel) tblMemList.getModel();
        String memId = (String) tbm.getValueAt(line, 0);
        Model.Members mem = Model.Members.Members_getMemberByMemberId(memId);
        DefaultTableModel tbm2 = IRBooks.ListByMemberNo(mem.Mem_No);
        tblFine.setModel(tbm2);
        UIHelper.hideColumnOfTable(tblFine, 0);
        DefaultTableModel tbm3 = IRBooks.getListBookByMemNo(mem.Mem_No);
        tblIssued.setModel(tbm3);
        UIHelper.hideColumnOfTable(tblIssued, 0);
        lblTotalFine.setText("Total: $" + Fine.calculateTotal(tblFine, 8));
        txtID.setText(mem.Mem_Id);
        txtNo.setText(mem.Mem_No);
        txtFirstname.setText(mem.Mem_FirstName);
        txtLastname.setText(mem.Mem_LastName);
        txtPhone.setText(mem.Mem_Phone);
        txtDepartment.setText(mem.Mem_Dep);
        txtAddress.setText(mem.Mem_Address);
        lblRegdate.setText(mem.Mem_CreateDate);
        if (mem.Mem_Status == false) {
            cbStatus.setSelectedItem("Inactive");
        } else {
            cbStatus.setSelectedItem("Active");
        }
        ImageIcon icon = new ImageIcon(mem.Mem_ImageFile);
        lblMemAvatar.setIcon(new ImageIcon(icon.getImage().getScaledInstance(lblMemAvatar.getWidth(), lblMemAvatar.getHeight(), SCALE_SMOOTH)));
    }//GEN-LAST:event_tblMemListMouseClicked

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        setNormalMode();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnSaveUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveUpdateActionPerformed
        Model.Members obj;
        obj = new Model.Members();
        String memid = txtID.getText();
        obj.Mem_FirstName = txtFirstname.getText();
        obj.Mem_LastName = txtLastname.getText();
        obj.Mem_Phone = txtPhone.getText();
        obj.Mem_Address = txtAddress.getText();
        obj.Mem_Dep = txtDepartment.getText();

        if (lblFileName.getText() == null) {
            obj.Mem_ImageFile = lblMemAvatar.getIcon().toString();
        } else {
            if (lblMemAvatar.getIcon() == null) {
                obj.Mem_ImageFile = "imgMem/MemNoAvatar.png";
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                String newfilename = sdf.format(Calendar.getInstance().getTime());
                File labelicon = new File(lblFileName.getText());
                File desfile = new File("imgMem/" + newfilename + "_" + labelicon.getName());
                try {
                    copyFile(labelicon, desfile);
                } catch (IOException ex) {
                    Exceptions.printStackTrace(ex);
                }
                obj.Mem_ImageFile = "imgMem/" + desfile.getName();
            }
        }

        if (cbStatus.getSelectedItem() == "Active") {
            obj.Mem_Status = true;
        } else if (cbStatus.getSelectedItem() == "Inactive") {
            obj.Mem_Status = false;
        }
        if (txtFirstname.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "First Name cannot be NULL");
            txtFirstname.requestFocus();
            return;
        } else if (txtFirstname.getText().length() > 30) {
            JOptionPane.showMessageDialog(null, "First Name cannot be longer 30 chars");
            txtFirstname.requestFocus();
            return;
        }

        if (txtLastname.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Last Name cannot be NULL");
            txtLastname.requestFocus();
            return;
        } else if (txtLastname.getText().length() > 50) {
            JOptionPane.showMessageDialog(null, "Last Name cannot be longer 50 chars");
            txtLastname.requestFocus();
            return;
        }

        if (txtDepartment.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Department cannot be NULL");
            txtDepartment.requestFocus();
            return;
        } else if (txtDepartment.getText().length() > 50) {
            JOptionPane.showMessageDialog(null, "Department cannot be longer 50 chars");
            txtDepartment.requestFocus();
            return;
        }
        if (cbStatus.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(null, "Please choose a Status");
            cbStatus.requestFocus();
            return;
        }
        obj.Mem_Status = (String) cbStatus.getSelectedItem() == "Active";

        if (txtAddress.getText().isEmpty()) {
            obj.Mem_Address = "";
        }
        int rt = Model.Members.Members_Update(obj, memid);
        if (rt == 1) {
            setNormalMode();
            getList();
        } else if (rt == 0) {
            txtNo.requestFocus();
        } else if (rt == 3) {
            txtDepartment.requestFocus();
        }
        MessageHandle.showMessage(MessageHandle.Obj_Member, MessageHandle.Action_update, rt);

    }//GEN-LAST:event_btnSaveUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        String Mem_Id = txtID.getText();
        int del = Model.Members.Members_Lock(Mem_Id);
        MessageHandle.showMessage(MessageHandle.Obj_Member, MessageHandle.Action_delete, del);
        getList();

    }//GEN-LAST:event_btnDeleteActionPerformed

    private void txtSearchNoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchNoKeyReleased
        String MemNo = txtSearchNo.getText();
        String Name = txtSearchName.getText();
        tblMemList.setModel(Model.Members.getAllMemByFilter(MemNo, Name));
        tblMemList.removeColumn(tblMemList.getColumnModel().getColumn(0));
    }//GEN-LAST:event_txtSearchNoKeyReleased

    private void txtSearchNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchNameKeyReleased
        String MemNo = txtSearchNo.getText();
        String Name = txtSearchName.getText();
        tblMemList.setModel(Model.Members.getAllMemByFilter(MemNo, Name));
        tblMemList.removeColumn(tblMemList.getColumnModel().getColumn(0));
    }//GEN-LAST:event_txtSearchNameKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnChange;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnSaveAdd;
    private javax.swing.JButton btnSaveUpdate;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox cbStatus;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblFileName;
    private javax.swing.JLabel lblMemAvatar;
    private javax.swing.JLabel lblRegdate;
    private javax.swing.JLabel lblTotalFine;
    private javax.swing.JPanel pnlBackground;
    private javax.swing.JPanel pnlProfile;
    private javax.swing.JPanel pnlUpdate;
    private javax.swing.JTable tblFine;
    private javax.swing.JTable tblIssued;
    private javax.swing.JTable tblMemList;
    private javax.swing.JTextArea txtAddress;
    private javax.swing.JTextField txtDepartment;
    private javax.swing.JTextField txtFirstname;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtLastname;
    private javax.swing.JTextField txtNo;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtSearchName;
    private javax.swing.JTextField txtSearchNo;
    // End of variables declaration//GEN-END:variables
}

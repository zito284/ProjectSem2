/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form.ir;

import Config.SysVar;
import ExSwing.*;
import Helpers.SqlHelper;
import Helpers.UIHelper;
import Model.Books;
import Model.Copies;
import Model.IRBooks;
import Model.Members;
import Helpers.MessageHandle;
import form.member.MemberSearch;
import java.awt.Dimension;
import static java.awt.Image.SCALE_SMOOTH;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JWindow;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Zito
 */
public class IssueManagement extends javax.swing.JFrame {

    // <editor-fold defaultstate="collapsed" desc="Declare variable">
    private Members selectedMember;

    private IRBooks ir;
    int countSTT = 1;
    int countAllow = 0;
    private HashMap<String, String> Cop_IdList;
    private HashMap<String, String> Cop_IdList_Issued;
    private JWindow window;
    private boolean isPopUp = false;
    public String Member_No = "";
    private JTable tblIssued;
    private static String issue_col[] = {"No", "ISBN", "Title", "Category", "Copy No"};
    //private static String copies_col[] = {"ISBN","Title","Author"};
    private static String issued_col[] = {"No", "CreateDate", "DueDate", "ISBN", "Title", "Cop No"};

    // </editor-fold>     
    public IssueManagement() {
        initComponents();
        UIHelper.bindBackground(pnlIssue);

        ir = new IRBooks();
        Cop_IdList = new HashMap<>(20);
        Cop_IdList_Issued = new HashMap<>(20);
        initForm();
        initMember();
        initCategory();
        initTblIssuing();
        initTblCopies();
        initPopUpWindow();

        //loadBook("");
    }

    private void loadBook(String ISBN) {
        Books book = Books.getByISBNFree(ISBN);
        lblAuthor.setText(book.Book_Author);
        lblCategory.setText(book.Cat_Name);
        lblLanguage.setText(book.Book_Language);
        lblPublisher.setText(book.Book_Publisher);
        lblTitle.setText(book.Book_Title);
        lblISBN.setText(book.Book_ISBN);
        lblFree.setText(String.valueOf(book.Book_Count));

        //load image book
        ImageIcon icon = new ImageIcon(book.Book_ImageFile);
        lblImgBook.setIcon(new ImageIcon(icon.getImage().getScaledInstance(lblImgBook.getWidth(), lblImgBook.getHeight(), SCALE_SMOOTH)));
        lblImgBook.setBounds(0, 0, 140, 140);
    }

    private void loadMember() {
        String mem_No = txtMemNo.getText();
        Members mem = Members.getIRCountInformation(mem_No);

        if (mem != null) {
            selectedMember = mem;
            lblMemberNo.setText(mem.Mem_No);
            lblFullname.setText(mem.Mem_FirstName + " " + mem.Mem_LastName);
            lblPhone.setText(mem.Mem_Phone);
            lblMemberDep.setText(mem.Mem_Dep);
            lblStatusMem.setText(mem.Mem_Status ? "Active" : "Inactive");
            lblRegisterDate.setText(mem.Mem_CreateDate);
            lblNumberBookIssued.setText(String.valueOf(mem.Mem_CountIssued));
            //load image member
            ImageIcon icon = new ImageIcon(mem.Mem_ImageFile);
            lblImgMember.setIcon(icon);
            lblImgMember.setIcon(new ImageIcon(icon.getImage().getScaledInstance(lblImgMember.getWidth(), lblImgMember.getHeight(), SCALE_SMOOTH)));
            lblImgMember.setBounds(0, 0, 140, 140);
            //rebind data tbl issued
            initPopUpWindow();
            countAllow = mem.Mem_CountIssued + tblIssuing.getRowCount();
            if (countAllow > 20) {
                ((DefaultTableModel) tblIssuing.getModel()).setNumRows(0);
            }
        } else {
            MessageHandle.showError("Can not find Member with No: " + mem_No);
        }

    }

    // <editor-fold defaultstate="collapsed" desc="Process function in Form">
    private void openPopUp(JLabel lbl) {
        Point p = lbl.getLocationOnScreen();
        p.y += lbl.getHeight();
        p.x -= 520;
        window.setLocation(p);
        window.setVisible(true);
    }

    private void closePopUp() {
        window.setVisible(false);
    }

    public void setDataPopUp(String memberNo) {
        this.Member_No = memberNo;
    }

    public String getDataPopUp() {
        return Member_No;
    }

    private void setCountSttOnTblIssuing() {
        for (int i = 0; i < tblIssuing.getRowCount(); i++) {
            tblIssuing.setValueAt(String.valueOf(i + 1), i, 0);
        }
    }

    private void bindTblIssue(IRBooks irb) {
        DefaultTableModel tblM = (DefaultTableModel) tblIssuing.getModel();
        tblM.addRow(new Object[]{
            countSTT++,
             irb.book.Book_ISBN,
             irb.book.Book_Title,
             irb.book.Cat_Name,
             irb.copy.Cop_No});
        tblIssuing.setModel(tblM);
        Cop_IdList.put(irb.copy.Cop_Id, irb.copy.Cop_Id);
    }

    // </editor-fold>   
    // <editor-fold defaultstate="collapsed" desc="Init Component when load form">
    private void initMember() {
        lblMemberNo.setText("");
        lblFullname.setText("");
        lblPhone.setText("");
        //lblMemberDepartment.setText("");
        lblRegisterDate.setText("");
        lblStatusMem.setText("");
        lblNumberBookIssued.setText("");
        //load image member
        lblImgMember.setIcon(new ImageIcon(IssueManagement.class
                .getResource(SysVar.image_member_defaut)));
        lblImgMember.setBounds(0, 0, 140, 140);
    }

    private void initTblIssuing() {
        DefaultTableModel tblM = new DefaultTableModel(issue_col, 0);
        tblIssuing.setModel(tblM);
    }

    private void initTblCopies() {
        DefaultTableModel tblM = SqlHelper.getDefaultTableModel("Books_getBookListByCatename", "");
        tblCopies.setModel(tblM);
        tblCopies.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void initPopUpWindow() {

        window = new JWindow(this);
        tblIssued = new JTable();
        if (selectedMember != null) {
            tblIssued.setModel(IRBooks.getListBookNotReturnByMemberNo(selectedMember.Mem_No));//));
        } else {
            DefaultTableModel tbm = new DefaultTableModel(issued_col, 0);
            tblIssued.setModel(tbm);//));
        }
        tblIssued.setPreferredSize(new Dimension(750, 100));
        UIHelper.setColumnWidth(tblIssued, new int[]{50, 120, 120, 170, 170, 120});
        window.add(new JScrollPane(tblIssued));
        window.setEnabled(false);

    }

    private void initCategory() {
//        cbCategory.setModel(Categories.Categories_getCategoryCombobox());
    }

    private void initForm() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        btSearchMem.setIcon(new ImageIcon(IssueManagement.class
                .getResource("/image/Explore.png")));
        btReset.setIcon(new ImageIcon(IssueManagement.class
                .getResource("/image/CloseForm.png")));
        btIssue.setIcon(new ImageIcon(IssueManagement.class
                .getResource("/image/issue.png")));
        tblCopies.getTableHeader().setReorderingAllowed(false);
        tblIssuing.getTableHeader().setReorderingAllowed(false);
        lblBookIssuedEvent.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                if (window != null) {
                    openPopUp(lblBookIssuedEvent);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (window != null) {
                    closePopUp();
                }
            }
        });
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(IssueManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(IssueManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(IssueManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(IssueManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
    // </editor-fold>       

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlIssue = new ClPanelTransparent();
        jPanel1 = new ClPanelTransparent();
        jPanel3 = new ClPanelTransparent();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtMemNo = new javax.swing.JTextField();
        btSearchMem = new ClButtonTransparan("Search");
        jPanel6 = new ClPanelTransparent();
        jPanel8 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtISBN = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtTitle = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtAuthor = new javax.swing.JTextField();
        jPanel10 = new ClPanelTransparent();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblIssuing = new javax.swing.JTable(){
            public boolean isCellEditable(int row,int column){
                return false;
            };
        };
        jPanel11 = new ClPanelTransparent();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblCopies = new javax.swing.JTable(){

            public boolean isCellEditable(int row,int column){
                return false;
            };
        };
        btRemove = new ClButtonTransparan("Issue");
        btAdd = new ClButtonTransparan("Issue");
        jPanel2 = new ClPanelTransparent();
        jPanel4 = new ClPanelTransparent();
        jPanel9 = new javax.swing.JPanel();
        pnlImgMember = new javax.swing.JPanel();
        lblImgMember = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblFullname = new javax.swing.JLabel();
        lblPhone = new javax.swing.JLabel();
        lblRegisterDate = new javax.swing.JLabel();
        lblMemberDep = new javax.swing.JLabel();
        lblBookIssuedEvent = new javax.swing.JLabel();
        lblNumberBookIssued = new javax.swing.JLabel();
        JLabel1111 = new javax.swing.JLabel();
        lblMemberNo = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        lblStatusMem = new javax.swing.JLabel();
        jPanel7 = new ClPanelTransparent();
        jPanel13 = new javax.swing.JPanel();
        pnlImgBook = new javax.swing.JPanel();
        lblImgBook = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblAuthor = new javax.swing.JLabel();
        lblPublisher = new javax.swing.JLabel();
        lblCategory = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblTitle = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lblISBN = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        lblLanguage = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        lblFree = new javax.swing.JLabel();
        jPanel12 = new ClPanelTransparent();
        btIssue = new ClButtonTransparan("Issue");
        btReset = new ClButtonTransparan("Reset");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, org.openide.util.NbBundle.getMessage(IssueManagement.class, "IssueManagement.jPanel3.border.title_1"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), java.awt.Color.yellow)); // NOI18N
        jPanel3.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel1.setText(org.openide.util.NbBundle.getMessage(IssueManagement.class, "IssueManagement.jLabel1.text_1")); // NOI18N

        txtMemNo.setText(org.openide.util.NbBundle.getMessage(IssueManagement.class, "IssueManagement.txtMemNo.text_1")); // NOI18N
        txtMemNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMemNoActionPerformed(evt);
            }
        });

        btSearchMem.setForeground(java.awt.Color.darkGray);
        btSearchMem.setText(org.openide.util.NbBundle.getMessage(IssueManagement.class, "IssueManagement.btSearchMem.text_1")); // NOI18N
        btSearchMem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSearchMemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(txtMemNo, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btSearchMem)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtMemNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btSearchMem))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel5, java.awt.BorderLayout.CENTER);

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, org.openide.util.NbBundle.getMessage(IssueManagement.class, "IssueManagement.jPanel6.border.title_1"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), java.awt.Color.yellow)); // NOI18N
        jPanel6.setLayout(new java.awt.BorderLayout());

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel6.setText(org.openide.util.NbBundle.getMessage(IssueManagement.class, "IssueManagement.jLabel6.text_1")); // NOI18N

        txtISBN.setText(org.openide.util.NbBundle.getMessage(IssueManagement.class, "IssueManagement.txtISBN.text_1")); // NOI18N
        txtISBN.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtISBNKeyReleased(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel14.setText(org.openide.util.NbBundle.getMessage(IssueManagement.class, "IssueManagement.jLabel14.text_1")); // NOI18N

        txtTitle.setText(org.openide.util.NbBundle.getMessage(IssueManagement.class, "IssueManagement.txtTitle.text_1")); // NOI18N
        txtTitle.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTitleKeyReleased(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel15.setText(org.openide.util.NbBundle.getMessage(IssueManagement.class, "IssueManagement.jLabel15.text_1")); // NOI18N

        txtAuthor.setText(org.openide.util.NbBundle.getMessage(IssueManagement.class, "IssueManagement.txtAuthor.text_1")); // NOI18N
        txtAuthor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAuthorKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(txtISBN, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtAuthor, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(46, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel14)
                        .addComponent(txtAuthor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(txtISBN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel15)
                        .addComponent(txtTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.add(jPanel8, java.awt.BorderLayout.CENTER);

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, org.openide.util.NbBundle.getMessage(IssueManagement.class, "IssueManagement.jPanel10.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), java.awt.Color.yellow)); // NOI18N

        jScrollPane2.setHorizontalScrollBar(null);

        tblIssuing.setModel(new javax.swing.table.DefaultTableModel(
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
        tblIssuing.setPreferredSize(new java.awt.Dimension(300, 150));
        tblIssuing.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblIssuing.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblIssuingMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblIssuing);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
        );

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(null, org.openide.util.NbBundle.getMessage(IssueManagement.class, "IssueManagement.jPanel11.border.title_1"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), java.awt.Color.yellow)); // NOI18N

        tblCopies.setModel(new javax.swing.table.DefaultTableModel(
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
        tblCopies.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblCopies.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCopiesMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblCopies);

        btRemove.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btRemove.setForeground(new java.awt.Color(255, 0, 0));
        btRemove.setText(org.openide.util.NbBundle.getMessage(IssueManagement.class, "IssueManagement.btRemove.text")); // NOI18N
        btRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRemoveActionPerformed(evt);
            }
        });

        btAdd.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btAdd.setForeground(new java.awt.Color(255, 0, 0));
        btAdd.setText(org.openide.util.NbBundle.getMessage(IssueManagement.class, "IssueManagement.btAdd.text")); // NOI18N
        btAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAddActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(213, 213, 213)
                .addComponent(btRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(250, 250, 250))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(993, 993, 993))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, org.openide.util.NbBundle.getMessage(IssueManagement.class, "IssueManagement.jPanel4.border.title_1"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), java.awt.Color.yellow)); // NOI18N
        jPanel4.setPreferredSize(new java.awt.Dimension(250, 176));
        jPanel4.setLayout(new java.awt.BorderLayout());

        pnlImgMember.setPreferredSize(new java.awt.Dimension(140, 140));

        lblImgMember.setText(org.openide.util.NbBundle.getMessage(IssueManagement.class, "IssueManagement.lblImgMember.text_1")); // NOI18N
        lblImgMember.setAlignmentX(0.5F);

        javax.swing.GroupLayout pnlImgMemberLayout = new javax.swing.GroupLayout(pnlImgMember);
        pnlImgMember.setLayout(pnlImgMemberLayout);
        pnlImgMemberLayout.setHorizontalGroup(
            pnlImgMemberLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlImgMemberLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblImgMember)
                .addContainerGap(123, Short.MAX_VALUE))
        );
        pnlImgMemberLayout.setVerticalGroup(
            pnlImgMemberLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlImgMemberLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblImgMember)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel2.setText(org.openide.util.NbBundle.getMessage(IssueManagement.class, "IssueManagement.jLabel2.text_1")); // NOI18N

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel3.setText(org.openide.util.NbBundle.getMessage(IssueManagement.class, "IssueManagement.jLabel3.text_1")); // NOI18N

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel4.setText(org.openide.util.NbBundle.getMessage(IssueManagement.class, "IssueManagement.jLabel4.text_1")); // NOI18N

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel5.setText(org.openide.util.NbBundle.getMessage(IssueManagement.class, "IssueManagement.jLabel5.text_1")); // NOI18N

        lblPhone.setPreferredSize(new java.awt.Dimension(40, 15));

        lblBookIssuedEvent.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        lblBookIssuedEvent.setText(org.openide.util.NbBundle.getMessage(IssueManagement.class, "IssueManagement.lblBookIssuedEvent.text_1")); // NOI18N

        lblNumberBookIssued.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        JLabel1111.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        JLabel1111.setText(org.openide.util.NbBundle.getMessage(IssueManagement.class, "IssueManagement.JLabel1111.text_1")); // NOI18N

        jLabel12.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel12.setText(org.openide.util.NbBundle.getMessage(IssueManagement.class, "IssueManagement.jLabel12.text_1")); // NOI18N

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addComponent(pnlImgMember, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 64, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblStatusMem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(JLabel1111, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblMemberNo, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(lblMemberDep, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblRegisterDate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblPhone, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblFullname, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(lblBookIssuedEvent, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(lblNumberBookIssued, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(21, 21, 21))))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(pnlImgMember, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(JLabel1111)
                            .addComponent(lblMemberNo))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(lblFullname))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(lblPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(lblRegisterDate))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(lblMemberDep))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(lblStatusMem))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblBookIssuedEvent)
                            .addComponent(lblNumberBookIssued))
                        .addGap(18, 18, 18))))
        );

        jPanel4.add(jPanel9, java.awt.BorderLayout.CENTER);

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, org.openide.util.NbBundle.getMessage(IssueManagement.class, "IssueManagement.jPanel7.border.title_1"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), java.awt.Color.yellow)); // NOI18N
        jPanel7.setPreferredSize(new java.awt.Dimension(430, 215));
        jPanel7.setLayout(new java.awt.BorderLayout());

        pnlImgBook.setPreferredSize(new java.awt.Dimension(140, 140));

        lblImgBook.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblImgBook.setText(org.openide.util.NbBundle.getMessage(IssueManagement.class, "IssueManagement.lblImgBook.text_1")); // NOI18N

        javax.swing.GroupLayout pnlImgBookLayout = new javax.swing.GroupLayout(pnlImgBook);
        pnlImgBook.setLayout(pnlImgBookLayout);
        pnlImgBookLayout.setHorizontalGroup(
            pnlImgBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlImgBookLayout.createSequentialGroup()
                .addComponent(lblImgBook, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnlImgBookLayout.setVerticalGroup(
            pnlImgBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlImgBookLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblImgBook, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel9.setText(org.openide.util.NbBundle.getMessage(IssueManagement.class, "IssueManagement.jLabel9.text_1")); // NOI18N

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel8.setText(org.openide.util.NbBundle.getMessage(IssueManagement.class, "IssueManagement.jLabel8.text_1")); // NOI18N

        lblAuthor.setText(org.openide.util.NbBundle.getMessage(IssueManagement.class, "IssueManagement.lblAuthor.text_1")); // NOI18N

        lblPublisher.setText(org.openide.util.NbBundle.getMessage(IssueManagement.class, "IssueManagement.lblPublisher.text_1")); // NOI18N

        lblCategory.setText(org.openide.util.NbBundle.getMessage(IssueManagement.class, "IssueManagement.lblCategory.text_1")); // NOI18N

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel7.setText(org.openide.util.NbBundle.getMessage(IssueManagement.class, "IssueManagement.jLabel7.text_1")); // NOI18N

        lblTitle.setText(org.openide.util.NbBundle.getMessage(IssueManagement.class, "IssueManagement.lblTitle.text_1")); // NOI18N

        jLabel11.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel11.setText(org.openide.util.NbBundle.getMessage(IssueManagement.class, "IssueManagement.jLabel11.text_1")); // NOI18N

        lblISBN.setText(org.openide.util.NbBundle.getMessage(IssueManagement.class, "IssueManagement.lblISBN.text_1")); // NOI18N

        jLabel13.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel13.setText(org.openide.util.NbBundle.getMessage(IssueManagement.class, "IssueManagement.jLabel13.text_1")); // NOI18N

        lblLanguage.setText(org.openide.util.NbBundle.getMessage(IssueManagement.class, "IssueManagement.lblLanguage.text_1")); // NOI18N

        jLabel10.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel10.setText(org.openide.util.NbBundle.getMessage(IssueManagement.class, "IssueManagement.jLabel10.text_1")); // NOI18N

        jLabel16.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel16.setText(org.openide.util.NbBundle.getMessage(IssueManagement.class, "IssueManagement.jLabel16.text")); // NOI18N

        lblFree.setText(org.openide.util.NbBundle.getMessage(IssueManagement.class, "IssueManagement.lblFree.text")); // NOI18N

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(159, 159, 159)
                .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblFree)
                .addGap(107, 107, 107))
            .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel13Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(pnlImgBook, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(lblISBN, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblCategory, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblPublisher, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblAuthor, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblLanguage, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblTitle, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap(199, Short.MAX_VALUE)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(lblFree))
                .addContainerGap())
            .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel13Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel13Layout.createSequentialGroup()
                            .addComponent(pnlImgBook, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, Short.MAX_VALUE))
                        .addGroup(jPanel13Layout.createSequentialGroup()
                            .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblISBN))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel7)
                                .addComponent(lblTitle))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel11)
                                .addComponent(lblCategory))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblPublisher)
                                .addComponent(jLabel8))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel9)
                                .addComponent(lblAuthor))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel10)
                                .addComponent(lblLanguage))))
                    .addContainerGap()))
        );

        jPanel7.add(jPanel13, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, 455, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        btIssue.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btIssue.setForeground(java.awt.Color.yellow);
        btIssue.setText(org.openide.util.NbBundle.getMessage(IssueManagement.class, "IssueManagement.btIssue.text_1")); // NOI18N
        btIssue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btIssueActionPerformed(evt);
            }
        });

        btReset.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btReset.setForeground(java.awt.Color.yellow);
        btReset.setText(org.openide.util.NbBundle.getMessage(IssueManagement.class, "IssueManagement.btReset.text_1")); // NOI18N
        btReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btResetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btIssue)
                .addGap(80, 80, 80)
                .addComponent(btReset)
                .addGap(100, 100, 100))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btReset)
                .addComponent(btIssue))
        );

        javax.swing.GroupLayout pnlIssueLayout = new javax.swing.GroupLayout(pnlIssue);
        pnlIssue.setLayout(pnlIssueLayout);
        pnlIssueLayout.setHorizontalGroup(
            pnlIssueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlIssueLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlIssueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlIssueLayout.setVerticalGroup(
            pnlIssueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlIssueLayout.createSequentialGroup()
                .addGroup(pnlIssueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlIssueLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlIssueLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 558, Short.MAX_VALUE)))
                .addContainerGap())
        );

        getContentPane().add(pnlIssue, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // <editor-fold defaultstate="collapsed" desc="Event in Form">
    private void txtMemNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMemNoActionPerformed
        // TODO add your handling code here:
        loadMember();
    }//GEN-LAST:event_txtMemNoActionPerformed

    private void tblCopiesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCopiesMouseClicked
        // TODO add your handling code here:
        int index = tblCopies.getSelectedRow();
        boolean status = index != -1;

        if (evt.getClickCount() == 2) {
            if (status) {
                if (countAllow < 20) {
                    //get ISBN on table
                    String ISBN = String.valueOf(tblCopies.getModel().getValueAt(index, 0));
                    ir.book = Books.getByISBN(ISBN);
                    ir.copy = Copies.getLastestIsFree(ir.book.Book_ISBN, Cop_IdList);
                    if (ir.copy != null) {
                        bindTblIssue(ir);
                        setCountSttOnTblIssuing();
                        ++countAllow;
                        validate();
                    } else {
                        MessageHandle.showError("Not exists Copy");
                    }
                } else {
                    MessageHandle.showError("Not Allow total issue more than 5 book");
                }
            }
        } else if (evt.getClickCount() == 1) {
            if (status) {
                String ISBN = String.valueOf(tblCopies.getModel().getValueAt(index, 0));
                loadBook(ISBN);
            }
            // 
        }
    }//GEN-LAST:event_tblCopiesMouseClicked

    private void tblIssuingMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblIssuingMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            JTable target = (JTable) evt.getSource();
            int index = target.getSelectedRow();
            boolean status = index != -1;
            if (status) {
                --countAllow;
                String copNo = String.valueOf(tblIssuing.getModel().getValueAt(index, 4));
                Cop_IdList.remove(copNo);
                removeRow(index);
                setCountSttOnTblIssuing();
            }
        }
    }//GEN-LAST:event_tblIssuingMouseClicked
    private void removeRow(int index) {
        DefaultTableModel tblM = (DefaultTableModel) tblIssuing.getModel();
        tblM.removeRow(tblCopies.convertRowIndexToModel(index));
        tblIssuing.setModel(tblM);
    }
    private void btSearchMemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSearchMemActionPerformed
        // TODO add your handling code here:
        MemberSearch memberSearchBox = new MemberSearch(this, true);
        memberSearchBox.setVisible(true);
        txtMemNo.setText(memberSearchBox.getPopUpData());
        loadMember();
    }//GEN-LAST:event_btSearchMemActionPerformed

    private void btIssueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btIssueActionPerformed
        // TODO add your handling code here:
        boolean flag = true;
        int result = -1;
        if (tblIssuing.getRowCount() > 0) {
            if (selectedMember != null) {
                String memId = selectedMember.Mem_Id;
                for (int i = 0; i < tblIssuing.getRowCount(); i++) {
                    //string cop no in tblissuing
                    String copNo = String.valueOf(tblIssuing.getModel().getValueAt(i, 4));
                    Cop_IdList_Issued.put(copNo, copNo);
                }
                // StringHelper.getStringByMap(Cop_IdList_Issued)
                result = IRBooks.IssueBook(Cop_IdList_Issued, memId);
                if (result == 1) {
                    ((DefaultTableModel) tblIssuing.getModel()).setNumRows(0);
                    loadMember();
                    Cop_IdList_Issued.clear();
                    Cop_IdList.clear();
                }
                MessageHandle.showMessage(MessageHandle.Obj_Book, MessageHandle.Action_issue, result);
            } else {
                MessageHandle.showError("Please choose Member to continue!!");
            }
        } else {
            MessageHandle.showError("Please choose Copy from Book to continue!!");
        }

        //  else

    }//GEN-LAST:event_btIssueActionPerformed

    private void btResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btResetActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btResetActionPerformed

    private void txtISBNKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtISBNKeyReleased

        String isbn = txtISBN.getText();
        String title = txtTitle.getText();
        String author = txtAuthor.getText();
        DefaultTableModel tblM = SqlHelper.getDefaultTableModel("Books_getListBookByFilter", isbn, title, author);
        tblCopies.setModel(tblM);
    }//GEN-LAST:event_txtISBNKeyReleased

    private void txtTitleKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTitleKeyReleased
        String isbn = txtISBN.getText();
        String title = txtTitle.getText();
        String author = txtAuthor.getText();
        DefaultTableModel tblM = SqlHelper.getDefaultTableModel("Books_getListBookByFilter", isbn, title, author);
        tblCopies.setModel(tblM);
    }//GEN-LAST:event_txtTitleKeyReleased

    private void txtAuthorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAuthorKeyReleased
        String isbn = txtISBN.getText();
        String title = txtTitle.getText();
        String author = txtAuthor.getText();
        DefaultTableModel tblM = SqlHelper.getDefaultTableModel("Books_getListBookByFilter", isbn, title, author);
        tblCopies.setModel(tblM);
    }//GEN-LAST:event_txtAuthorKeyReleased

    private void btRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRemoveActionPerformed
        int index = tblIssuing.getSelectedRow();
        boolean status = index != -1;
        if (status) {
            --countAllow;
            String copNo = String.valueOf(tblIssuing.getModel().getValueAt(index, 4));
            Cop_IdList.remove(copNo);
            removeRow(index);
            setCountSttOnTblIssuing();
        }
    }//GEN-LAST:event_btRemoveActionPerformed

    private void btAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAddActionPerformed
        // TODO add your handling code here:
        int index=tblCopies.getSelectedRow();
        if (index!=-1)
        {
                if (countAllow < 20) {
                    //get ISBN on table
                    String ISBN = String.valueOf(tblCopies.getModel().getValueAt(index, 0));
                    ir.book = Books.getByISBN(ISBN);
                    ir.copy = Copies.getLastestIsFree(ir.book.Book_ISBN, Cop_IdList);
                    if (ir.copy != null) {
                        bindTblIssue(ir);
                        setCountSttOnTblIssuing();
                        ++countAllow;
                        validate();
                    } else {
                        MessageHandle.showError("Not exists Copy");
                    }
                } else {
                    MessageHandle.showError("Not Allow total issue more than 20 book");
                }
        }
    }//GEN-LAST:event_btAddActionPerformed
    // </editor-fold>


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel JLabel1111;
    private javax.swing.JButton btAdd;
    private javax.swing.JButton btIssue;
    private javax.swing.JButton btRemove;
    private javax.swing.JButton btReset;
    private javax.swing.JButton btSearchMem;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblAuthor;
    private javax.swing.JLabel lblBookIssuedEvent;
    private javax.swing.JLabel lblCategory;
    private javax.swing.JLabel lblFree;
    private javax.swing.JLabel lblFullname;
    private javax.swing.JLabel lblISBN;
    private javax.swing.JLabel lblImgBook;
    private javax.swing.JLabel lblImgMember;
    private javax.swing.JLabel lblLanguage;
    private javax.swing.JLabel lblMemberDep;
    private javax.swing.JLabel lblMemberNo;
    private javax.swing.JLabel lblNumberBookIssued;
    private javax.swing.JLabel lblPhone;
    private javax.swing.JLabel lblPublisher;
    private javax.swing.JLabel lblRegisterDate;
    private javax.swing.JLabel lblStatusMem;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel pnlImgBook;
    private javax.swing.JPanel pnlImgMember;
    private javax.swing.JPanel pnlIssue;
    private javax.swing.JTable tblCopies;
    private javax.swing.JTable tblIssuing;
    private javax.swing.JTextField txtAuthor;
    private javax.swing.JTextField txtISBN;
    private javax.swing.JTextField txtMemNo;
    private javax.swing.JTextField txtTitle;
    // End of variables declaration//GEN-END:variables
}

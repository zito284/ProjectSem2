/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form.main;

import ExSwing.*;
import Helpers.UIHelper;
import form.book.BookList;
import form.category.Category;
import form.ir.IssueManagement;
import form.ir.ReturnManagement;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.Timer;

/**
 *
 * @author Zito
 */
public class Main extends javax.swing.JFrame {

    /**
     * Creates new form Main
     */
    private String[] imageFile={"home.png","member_management.png","book_management.png","ir_management.png","fine_management.png"};
    SimpleDateFormat simpleDate = new SimpleDateFormat("EEE, dd/MM/yyyy");
    SimpleDateFormat simpleTime = new SimpleDateFormat("HH:mm:ss");
    private String[] strDays = new String[] { "Sun", "Mon", "Tue", "Wed", "Thu","Fri", "Sat" };

    public Main() {
        initComponents();
        setDateTime();
        initForm();        
    }
        private void initForm(){
        setLocationRelativeTo(null);
        setIconImage(Toolkit.getDefaultToolkit().getImage(
				Main.class.getResource("/image/main.png")));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     
        setContentPane(contentpane);
        contentpane.setBackground(Color.BLACK);
        
        //set panel Home        
        pnlHome.setLayout(null);
        jTabbedPane1.setBounds(5, 5, 809, 620);
        
        lblWelcome.setFont(new Font("Droid Sans", Font.BOLD, 36));
        lblWelcome.setForeground(new Color(255, 255, 255));
        lblWelcome.setBounds(200, 261, 573, 63);       
     //background wall
        
        UIHelper.bindBackground(contentpane,"/image/background.png");
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        contentpane = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        pnlHome = new ClGlossyPanel();
        lblWelcome = new LabelGradient("");
        bgHome = new javax.swing.JLabel();
        pnlMember = new ClGlossyPanel();
        iconMem1 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        pnlBook = new ClGlossyPanel();
        iconMem3 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        iconMem4 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel4 = new ClPanelTransparent();
        jLabel32 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        pnlIR = new ClGlossyPanel();
        iconMem7 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        iconMem8 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        sidePanel = new ClPanelTransparent();
        iconMem13 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        lblTime = new javax.swing.JLabel();
        lblDate = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle(org.openide.util.NbBundle.getMessage(Main.class, "Main.title")); // NOI18N
        setBounds(new java.awt.Rectangle(100, 5, 1166, 736));

        contentpane.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));

        jTabbedPane1.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(1240, 720));
        jTabbedPane1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTabbedPane1FocusGained(evt);
            }
        });

        lblWelcome.setText(org.openide.util.NbBundle.getMessage(Main.class, "Main.lblWelcome.text")); // NOI18N
        lblWelcome.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        bgHome.setText(org.openide.util.NbBundle.getMessage(Main.class, "Main.bgHome.text")); // NOI18N

        javax.swing.GroupLayout pnlHomeLayout = new javax.swing.GroupLayout(pnlHome);
        pnlHome.setLayout(pnlHomeLayout);
        pnlHomeLayout.setHorizontalGroup(
            pnlHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHomeLayout.createSequentialGroup()
                .addGroup(pnlHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bgHome)
                    .addGroup(pnlHomeLayout.createSequentialGroup()
                        .addGap(191, 191, 191)
                        .addComponent(lblWelcome)))
                .addContainerGap(589, Short.MAX_VALUE))
        );
        pnlHomeLayout.setVerticalGroup(
            pnlHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHomeLayout.createSequentialGroup()
                .addComponent(bgHome)
                .addGap(283, 283, 283)
                .addComponent(lblWelcome)
                .addContainerGap())
        );

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(Main.class, "Main.pnlHome.TabConstraints.tabTitle"), pnlHome); // NOI18N

        iconMem1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconMem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/member.png"))); // NOI18N
        iconMem1.setText(org.openide.util.NbBundle.getMessage(Main.class, "Main.iconMem1.text")); // NOI18N
        iconMem1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        iconMem1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        iconMem1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iconMem1MouseClicked(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Times New Roman", 3, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText(org.openide.util.NbBundle.getMessage(Main.class, "Main.jLabel1.text")); // NOI18N

        javax.swing.GroupLayout pnlMemberLayout = new javax.swing.GroupLayout(pnlMember);
        pnlMember.setLayout(pnlMemberLayout);
        pnlMemberLayout.setHorizontalGroup(
            pnlMemberLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMemberLayout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addGroup(pnlMemberLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(iconMem1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(689, Short.MAX_VALUE))
        );
        pnlMemberLayout.setVerticalGroup(
            pnlMemberLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMemberLayout.createSequentialGroup()
                .addGap(110, 110, 110)
                .addComponent(iconMem1, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addContainerGap(359, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(Main.class, "Main.pnlMember.TabConstraints.tabTitle"), pnlMember); // NOI18N

        iconMem3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconMem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/jm-category.png"))); // NOI18N
        iconMem3.setText(org.openide.util.NbBundle.getMessage(Main.class, "Main.iconMem3.text")); // NOI18N
        iconMem3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        iconMem3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        iconMem3.setMaximumSize(new java.awt.Dimension(400, 400));
        iconMem3.setMinimumSize(new java.awt.Dimension(400, 400));
        iconMem3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iconMem3MouseClicked(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Times New Roman", 3, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText(org.openide.util.NbBundle.getMessage(Main.class, "Main.jLabel3.text")); // NOI18N

        iconMem4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconMem4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/book.png"))); // NOI18N
        iconMem4.setText(org.openide.util.NbBundle.getMessage(Main.class, "Main.iconMem4.text")); // NOI18N
        iconMem4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        iconMem4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        iconMem4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iconMem4MouseClicked(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Times New Roman", 3, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText(org.openide.util.NbBundle.getMessage(Main.class, "Main.jLabel4.text")); // NOI18N

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 255, 255));
        jLabel32.setText(org.openide.util.NbBundle.getMessage(Main.class, "Main.jLabel32.text")); // NOI18N

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 255, 255));
        jLabel34.setText(org.openide.util.NbBundle.getMessage(Main.class, "Main.jLabel34.text")); // NOI18N

        jLabel39.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel39.setForeground(java.awt.Color.yellow);
        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel39.setText(org.openide.util.NbBundle.getMessage(Main.class, "Main.jLabel39.text")); // NOI18N
        jLabel39.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jLabel40.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(102, 204, 255));
        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel40.setText(org.openide.util.NbBundle.getMessage(Main.class, "Main.jLabel40.text")); // NOI18N

        jLabel41.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel41.setForeground(java.awt.Color.yellow);
        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel41.setText(org.openide.util.NbBundle.getMessage(Main.class, "Main.jLabel41.text")); // NOI18N
        jLabel41.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel32)
                    .addComponent(jLabel34))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel39, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel41, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE))
                .addGap(20, 20, 20))
            .addComponent(jLabel40, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel40)
                .addGap(50, 50, 50)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(jLabel39))
                .addGap(25, 25, 25)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(jLabel41))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlBookLayout = new javax.swing.GroupLayout(pnlBook);
        pnlBook.setLayout(pnlBookLayout);
        pnlBookLayout.setHorizontalGroup(
            pnlBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBookLayout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addGroup(pnlBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(iconMem4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(70, 70, 70)
                .addGroup(pnlBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                    .addComponent(iconMem3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(276, 276, 276)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlBookLayout.setVerticalGroup(
            pnlBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBookLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(pnlBookLayout.createSequentialGroup()
                .addGap(110, 110, 110)
                .addGroup(pnlBookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlBookLayout.createSequentialGroup()
                        .addComponent(iconMem4, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4))
                    .addGroup(pnlBookLayout.createSequentialGroup()
                        .addComponent(iconMem3, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)))
                .addContainerGap(359, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(Main.class, "Main.pnlBook.TabConstraints.tabTitle"), pnlBook); // NOI18N

        pnlIR.setFont(new java.awt.Font("Dialog", 3, 12)); // NOI18N

        iconMem7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconMem7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/issue_MENU.png"))); // NOI18N
        iconMem7.setText(org.openide.util.NbBundle.getMessage(Main.class, "Main.iconMem7.text")); // NOI18N
        iconMem7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        iconMem7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        iconMem7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iconMem7MouseClicked(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Times New Roman", 3, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText(org.openide.util.NbBundle.getMessage(Main.class, "Main.jLabel7.text")); // NOI18N

        iconMem8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconMem8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/return_Menu.png"))); // NOI18N
        iconMem8.setText(org.openide.util.NbBundle.getMessage(Main.class, "Main.iconMem8.text")); // NOI18N
        iconMem8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        iconMem8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        iconMem8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iconMem8MouseClicked(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Times New Roman", 3, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText(org.openide.util.NbBundle.getMessage(Main.class, "Main.jLabel8.text")); // NOI18N

        javax.swing.GroupLayout pnlIRLayout = new javax.swing.GroupLayout(pnlIR);
        pnlIR.setLayout(pnlIRLayout);
        pnlIRLayout.setHorizontalGroup(
            pnlIRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlIRLayout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addGroup(pnlIRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(iconMem7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(70, 70, 70)
                .addGroup(pnlIRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(iconMem8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(492, 492, 492))
        );
        pnlIRLayout.setVerticalGroup(
            pnlIRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlIRLayout.createSequentialGroup()
                .addGap(110, 110, 110)
                .addGroup(pnlIRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlIRLayout.createSequentialGroup()
                        .addComponent(iconMem7, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7))
                    .addGroup(pnlIRLayout.createSequentialGroup()
                        .addComponent(iconMem8, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)))
                .addContainerGap(359, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab(org.openide.util.NbBundle.getMessage(Main.class, "Main.pnlIR.TabConstraints.tabTitle"), pnlIR); // NOI18N

        iconMem13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconMem13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/exit (2).png"))); // NOI18N
        iconMem13.setText(org.openide.util.NbBundle.getMessage(Main.class, "Main.iconMem13.text")); // NOI18N
        iconMem13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        iconMem13.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        iconMem13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iconMem13MouseClicked(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Times New Roman", 3, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText(org.openide.util.NbBundle.getMessage(Main.class, "Main.jLabel16.text")); // NOI18N

        javax.swing.GroupLayout sidePanelLayout = new javax.swing.GroupLayout(sidePanel);
        sidePanel.setLayout(sidePanelLayout);
        sidePanelLayout.setHorizontalGroup(
            sidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sidePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(sidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                    .addComponent(iconMem13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        sidePanelLayout.setVerticalGroup(
            sidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sidePanelLayout.createSequentialGroup()
                .addGap(212, 212, 212)
                .addComponent(iconMem13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel16)
                .addGap(18, 18, 18))
        );

        lblTime.setFont(new java.awt.Font("Century Gothic", 0, 30)); // NOI18N
        lblTime.setForeground(java.awt.Color.yellow);
        lblTime.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTime.setText(org.openide.util.NbBundle.getMessage(Main.class, "Main.lblTime.text")); // NOI18N

        lblDate.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        lblDate.setForeground(java.awt.Color.yellow);
        lblDate.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDate.setText(org.openide.util.NbBundle.getMessage(Main.class, "Main.lblDate.text")); // NOI18N

        javax.swing.GroupLayout contentpaneLayout = new javax.swing.GroupLayout(contentpane);
        contentpane.setLayout(contentpaneLayout);
        contentpaneLayout.setHorizontalGroup(
            contentpaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentpaneLayout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 903, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(contentpaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(contentpaneLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(contentpaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(sidePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(contentpaneLayout.createSequentialGroup()
                                .addComponent(lblTime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(8, 8, 8))))
                    .addGroup(contentpaneLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblDate, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        contentpaneLayout.setVerticalGroup(
            contentpaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentpaneLayout.createSequentialGroup()
                .addGroup(contentpaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, contentpaneLayout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(lblTime, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblDate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(sidePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 655, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 10, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(contentpane, javax.swing.GroupLayout.PREFERRED_SIZE, 1088, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(contentpane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void iconMem13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iconMem13MouseClicked
        // TODO add your handling code here:
        this.dispose();
        Login login = new Login();
                login.setVisible(true);
    }//GEN-LAST:event_iconMem13MouseClicked

    private void jTabbedPane1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTabbedPane1FocusGained

    }//GEN-LAST:event_jTabbedPane1FocusGained

    private void iconMem8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iconMem8MouseClicked
        // TODO add your handling code here:
        new ReturnManagement().setVisible(true);
    }//GEN-LAST:event_iconMem8MouseClicked

    private void iconMem7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iconMem7MouseClicked
        // TODO add your handling code here:
        new IssueManagement().setVisible(true);
    }//GEN-LAST:event_iconMem7MouseClicked

    private void iconMem4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iconMem4MouseClicked

        new BookList().setVisible(true);
    }//GEN-LAST:event_iconMem4MouseClicked

    private void iconMem3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iconMem3MouseClicked
        // TODO add your handling code here:
        new Category().setVisible(true);
    }//GEN-LAST:event_iconMem3MouseClicked

    private void iconMem1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iconMem1MouseClicked
        new form.member.Members().setVisible(true);
    }//GEN-LAST:event_iconMem1MouseClicked
    public void setDateTime() {
        
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                Calendar now = Calendar.getInstance();
                Date cur = now.getTime();             
                lblDate.setText(simpleDate.format(cur));
                lblTime.setText(simpleTime.format(cur));
            }
        };
		new Timer(1000, taskPerformer).start();
	}
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bgHome;
    private javax.swing.JPanel contentpane;
    private javax.swing.JLabel iconMem1;
    private javax.swing.JLabel iconMem13;
    private javax.swing.JLabel iconMem3;
    private javax.swing.JLabel iconMem4;
    private javax.swing.JLabel iconMem7;
    private javax.swing.JLabel iconMem8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblDate;
    private javax.swing.JLabel lblTime;
    private javax.swing.JLabel lblWelcome;
    private javax.swing.JPanel pnlBook;
    private javax.swing.JPanel pnlHome;
    private javax.swing.JPanel pnlIR;
    private javax.swing.JPanel pnlMember;
    private javax.swing.JPanel sidePanel;
    // End of variables declaration//GEN-END:variables
}

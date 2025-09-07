package com.mycompany.demo_frontend.view;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.optionalusertools.DateVetoPolicy;
import com.mycompany.demo_frontend.controller.NhanVienController;
import java.time.LocalDate;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;

public class PanelNhanVien extends JPanel {
    private final NhanVienController controller;
    public PanelNhanVien() {
        initComponents();
        dtpNgaySinh.getSettings().setVetoPolicy(new DateVetoPolicy() {
            @Override
            public boolean isDateAllowed(LocalDate date) {
                if (date == null) {
                    return false;
                }
                int currentYear = LocalDate.now().getYear();
                return (currentYear - date.getYear()) >= 18;
            }
        });
        this.controller = new NhanVienController(this);
        controller.loadTableData();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        panelInfor = new javax.swing.JPanel();
        TenDG = new javax.swing.JLabel();
        txtTenNV = new javax.swing.JTextField();
        SoDienThoai = new javax.swing.JLabel();
        txtSdt = new javax.swing.JTextField();
        Email = new javax.swing.JLabel();
        DiaChi = new javax.swing.JLabel();
        txtQueQuan = new javax.swing.JTextField();
        dtpNgaySinh = new com.github.lgooddatepicker.components.DatePicker();
        radNam = new javax.swing.JRadioButton();
        radNu = new javax.swing.JRadioButton();
        SoDienThoai1 = new javax.swing.JLabel();
        txtUserName = new javax.swing.JTextField();
        txtPass = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        radQL = new javax.swing.JRadioButton();
        radNV = new javax.swing.JRadioButton();
        jPanel1 = new javax.swing.JPanel();
        btnNhapDuLieu = new javax.swing.JButton();
        btnXuatDuLieu = new javax.swing.JButton();
        panelButton = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        TimKiem = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        cbTimKiem = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblNhanVien = new javax.swing.JTable();

        setMaximumSize(new java.awt.Dimension(1120, 666));
        setMinimumSize(new java.awt.Dimension(1120, 666));
        setPreferredSize(new java.awt.Dimension(1120, 666));

        panelInfor.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        TenDG.setText("Tên nhân viên");
        TenDG.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        txtTenNV.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtTenNV.setPreferredSize(new java.awt.Dimension(64, 25));

        SoDienThoai.setText("Số điện thoại");
        SoDienThoai.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        txtSdt.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtSdt.setPreferredSize(new java.awt.Dimension(64, 25));

        Email.setText("Ngày sinh");
        Email.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        DiaChi.setText("Quê quán");
        DiaChi.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        txtQueQuan.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtQueQuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtQueQuanActionPerformed(evt);
            }
        });

        buttonGroup1.add(radNam);
        radNam.setSelected(true);
        radNam.setText("Nam");
        radNam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radNamActionPerformed(evt);
            }
        });

        buttonGroup1.add(radNu);
        radNu.setText("Nữ");

        SoDienThoai1.setText("Giới tính");
        SoDienThoai1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        jLabel1.setText("Tài Khoản");
        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        jLabel2.setText("Mật khẩu");
        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        jLabel4.setText("Role");

        buttonGroup2.add(radQL);
        radQL.setText("QUAN_LY");

        buttonGroup2.add(radNV);
        radNV.setText("NHAN_VIEN");

        javax.swing.GroupLayout panelInforLayout = new javax.swing.GroupLayout(panelInfor);
        panelInfor.setLayout(panelInforLayout);
        panelInforLayout.setHorizontalGroup(
            panelInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInforLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(panelInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(panelInforLayout.createSequentialGroup()
                            .addComponent(TenDG)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txtTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panelInforLayout.createSequentialGroup()
                            .addGroup(panelInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(SoDienThoai)
                                .addComponent(SoDienThoai1))
                            .addGap(16, 16, 16)
                            .addGroup(panelInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(panelInforLayout.createSequentialGroup()
                                    .addComponent(radNam)
                                    .addGap(18, 18, 18)
                                    .addComponent(radNu))
                                .addComponent(txtSdt, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(panelInforLayout.createSequentialGroup()
                                    .addComponent(radQL)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(radNV)))))
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelInforLayout.createSequentialGroup()
                        .addComponent(Email)
                        .addGap(18, 18, 18)
                        .addComponent(dtpNgaySinh, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE))
                    .addGroup(panelInforLayout.createSequentialGroup()
                        .addGroup(panelInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(DiaChi)
                            .addGroup(panelInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel2)
                                .addComponent(jLabel1)))
                        .addGap(18, 18, 18)
                        .addGroup(panelInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtPass)
                            .addComponent(txtUserName)
                            .addComponent(txtQueQuan, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE))))
                .addGap(30, 30, 30))
        );
        panelInforLayout.setVerticalGroup(
            panelInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInforLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(panelInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TenDG)
                    .addComponent(txtTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Email)
                    .addComponent(dtpNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(SoDienThoai)
                        .addComponent(txtSdt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(DiaChi)
                    .addComponent(txtQueQuan, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(radNu)
                    .addComponent(radNam)
                    .addComponent(SoDienThoai1)
                    .addComponent(txtUserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(radQL)
                    .addComponent(radNV))
                .addContainerGap(8, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        btnNhapDuLieu.setText("Nhập Dữ liệu");
        btnNhapDuLieu.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnNhapDuLieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNhapDuLieuActionPerformed(evt);
            }
        });

        btnXuatDuLieu.setText("Xuất Dữ liệu");
        btnXuatDuLieu.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnXuatDuLieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatDuLieuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(63, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnNhapDuLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXuatDuLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(55, 55, 55))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(btnNhapDuLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnXuatDuLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelButton.setBorder(javax.swing.BorderFactory.createTitledBorder("Chức năng"));
        panelButton.setPreferredSize(new java.awt.Dimension(1120, 86));

        btnThem.setText("Thêm");
        btnThem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setText("Sửa");
        btnSua.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setText("Xóa");
        btnXoa.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        TimKiem.setText("Tìm Kiếm:");
        TimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemActionPerformed(evt);
            }
        });

        cbTimKiem.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mã nhân viên", "Tên nhân viên" }));
        cbTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        javax.swing.GroupLayout panelButtonLayout = new javax.swing.GroupLayout(panelButton);
        panelButton.setLayout(panelButtonLayout);
        panelButtonLayout.setHorizontalGroup(
            panelButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelButtonLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(107, 107, 107)
                .addComponent(TimKiem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbTimKiem, 0, 159, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelButtonLayout.setVerticalGroup(
            panelButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelButtonLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(TimKiem)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(0, 153, 153));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        tblNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã nhân viên", "Tên nhân viên", "Số điện thoại", "Ngày sinh", "Giới tính", "Role", "Quê quán", "Tài khoản", "Mật khẩu"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNhanVien.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tblNhanVien.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblNhanVien.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblNhanVien.setShowGrid(true);
        tblNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNhanVienMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblNhanVien);
        if (tblNhanVien.getColumnModel().getColumnCount() > 0) {
            tblNhanVien.getColumnModel().getColumn(0).setPreferredWidth(5);
            tblNhanVien.getColumnModel().getColumn(1).setPreferredWidth(150);
            tblNhanVien.getColumnModel().getColumn(2).setPreferredWidth(150);
            tblNhanVien.getColumnModel().getColumn(3).setPreferredWidth(200);
            tblNhanVien.getColumnModel().getColumn(6).setPreferredWidth(200);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelButton, javax.swing.GroupLayout.DEFAULT_SIZE, 1108, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelInfor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelInfor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtQueQuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtQueQuanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtQueQuanActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed

    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed

    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed

    }//GEN-LAST:event_btnXoaActionPerformed

    private void tblNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhanVienMouseClicked

    }//GEN-LAST:event_tblNhanVienMouseClicked

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemActionPerformed

    }//GEN-LAST:event_txtTimKiemActionPerformed

    private void btnNhapDuLieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNhapDuLieuActionPerformed

    }//GEN-LAST:event_btnNhapDuLieuActionPerformed

    private void btnXuatDuLieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatDuLieuActionPerformed

    }//GEN-LAST:event_btnXuatDuLieuActionPerformed

    private void radNamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radNamActionPerformed
    }//GEN-LAST:event_radNamActionPerformed

    // Getter cho các component
    public JButton getBtnThem(){
        return btnThem;
    }
    public JButton getBtnSua(){
        return btnSua;
    }
    public JButton getBtnXoa(){
        return btnXoa;
    }
    public JButton getBtnNhap(){
        return btnNhapDuLieu;
    }
    public JButton getBtnXuat(){
        return btnXuatDuLieu;
    }
    public JTextField getTxtTenNV(){
        return txtTenNV;
    }
    public JTextField getTxtSdt(){
        return txtSdt;
    }
    public JTextField getTxtQueQuan(){
        return txtQueQuan;
    }
    public JTextField getTxtTaiKhoan(){
        return txtUserName;
    }
    public JTextField getTxtMatKhau(){
        return txtPass;
    }
    public JTextField getTxtTimKiem(){
        return txtTimKiem;
    }
    public DatePicker getDTPNgaySinh(){
        return dtpNgaySinh;
    }
    public JComboBox getCBTimKiem(){
        return cbTimKiem;
    }
    public JTable getTblNhanVien(){
        return tblNhanVien;
    }
    public JRadioButton getRadNam(){
        return radNam;
    }
    public JRadioButton getRadNu(){
        return radNu;
    }
    public JRadioButton getRadQL(){
        return radQL;
    }
    public JRadioButton getRadNV(){
        return radNV;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel DiaChi;
    private javax.swing.JLabel Email;
    private javax.swing.JLabel SoDienThoai;
    private javax.swing.JLabel SoDienThoai1;
    private javax.swing.JLabel TenDG;
    private javax.swing.JLabel TimKiem;
    private javax.swing.JButton btnNhapDuLieu;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXuatDuLieu;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox<String> cbTimKiem;
    private com.github.lgooddatepicker.components.DatePicker dtpNgaySinh;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelButton;
    private javax.swing.JPanel panelInfor;
    private javax.swing.JRadioButton radNV;
    private javax.swing.JRadioButton radNam;
    private javax.swing.JRadioButton radNu;
    private javax.swing.JRadioButton radQL;
    private javax.swing.JTable tblNhanVien;
    private javax.swing.JTextField txtPass;
    private javax.swing.JTextField txtQueQuan;
    private javax.swing.JTextField txtSdt;
    private javax.swing.JTextField txtTenNV;
    private javax.swing.JTextField txtTimKiem;
    private javax.swing.JTextField txtUserName;
    // End of variables declaration//GEN-END:variables
}

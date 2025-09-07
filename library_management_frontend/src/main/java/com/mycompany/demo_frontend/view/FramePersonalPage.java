package com.mycompany.demo_frontend.view;

import com.mycompany.demo_frontend.controller.PersonalPageController;
import com.mycompany.demo_frontend.dto.NhanVienDTO;
import javax.swing.JButton;

public class FramePersonalPage extends javax.swing.JFrame {
    private final PersonalPageController controller;
    public FramePersonalPage(NhanVienDTO nhanVien) {
        initComponents();
        this.controller = new PersonalPageController(this, nhanVien);
        controller.initPPHandlers();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        lblMaNV.setText("Mã nhân viên: " + String.valueOf(nhanVien.getManv()));
        lblTenNV.setText("Tên nhân viên: " + nhanVien.getTennv());
        lblSdt.setText("Số điện thoại: " + nhanVien.getSdt());
        lblNgaySinh.setText("Ngày sinh: " + String.valueOf(nhanVien.getNgaysinh()));
        lblQueQuan.setText("Quê quán: " + nhanVien.getQuequan());
        lblGioiTinh.setText("Giới tính: " + nhanVien.getGioitinh());
        lblRole.setText("Chức vụ: " + nhanVien.getRole());
    }
    
    public JButton getBtnAccountSetup(){
        return btnAccountSetup;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        lblTenNV = new javax.swing.JLabel();
        lblMaNV = new javax.swing.JLabel();
        lblSdt = new javax.swing.JLabel();
        lblNgaySinh = new javax.swing.JLabel();
        lblQueQuan = new javax.swing.JLabel();
        lblGioiTinh = new javax.swing.JLabel();
        lblRole = new javax.swing.JLabel();
        btnAccountSetup = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 153, 153));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("TRANG CÁ NHÂN");

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin cá nhân", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N

        lblTenNV.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTenNV.setText("Họ và tên:");

        lblMaNV.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMaNV.setText("Mã nhân viên:");

        lblSdt.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblSdt.setText("Số điện thoại:");

        lblNgaySinh.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblNgaySinh.setText("Ngày sinh:");

        lblQueQuan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblQueQuan.setText("Quê quán:");

        lblGioiTinh.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblGioiTinh.setText("Giới tính:");

        lblRole.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblRole.setText("Chức vụ:");

        btnAccountSetup.setText("Thiết lập tài khoản");
        btnAccountSetup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAccountSetupActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMaNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTenNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblSdt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblNgaySinh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblRole, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblGioiTinh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblQueQuan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnAccountSetup, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblMaNV)
                .addGap(18, 18, 18)
                .addComponent(lblTenNV)
                .addGap(18, 18, 18)
                .addComponent(lblSdt)
                .addGap(18, 18, 18)
                .addComponent(lblNgaySinh)
                .addGap(18, 18, 18)
                .addComponent(lblQueQuan)
                .addGap(18, 18, 18)
                .addComponent(lblGioiTinh)
                .addGap(18, 18, 18)
                .addComponent(lblRole)
                .addGap(18, 18, 18)
                .addComponent(btnAccountSetup, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 212, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAccountSetupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAccountSetupActionPerformed

    }//GEN-LAST:event_btnAccountSetupActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAccountSetup;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblGioiTinh;
    private javax.swing.JLabel lblMaNV;
    private javax.swing.JLabel lblNgaySinh;
    private javax.swing.JLabel lblQueQuan;
    private javax.swing.JLabel lblRole;
    private javax.swing.JLabel lblSdt;
    private javax.swing.JLabel lblTenNV;
    // End of variables declaration//GEN-END:variables
}

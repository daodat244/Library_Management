package com.mycompany.demo_frontend.view;

import com.mycompany.demo_frontend.controller.SachController;
import java.nio.charset.StandardCharsets;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PanelSach extends javax.swing.JPanel {

    public PanelSach() {
        initComponents();
        loadInitialData();

};
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelInfor = new javax.swing.JPanel();
        TenSach = new javax.swing.JLabel();
        txtTenSach = new javax.swing.JTextField();
        NXB = new javax.swing.JLabel();
        NamXuatBan = new javax.swing.JLabel();
        txtNamXB = new javax.swing.JTextField();
        cbNXB = new javax.swing.JComboBox<>();
        TacGia = new javax.swing.JLabel();
        cbTacGia = new javax.swing.JComboBox<>();
        TheLoai = new javax.swing.JLabel();
        cbTheLoai = new javax.swing.JComboBox<>();
        SoTrang = new javax.swing.JLabel();
        txtSoTrang = new javax.swing.JTextField();
        txtSoLuong = new javax.swing.JTextField();
        NamXuatBan1 = new javax.swing.JLabel();
        MaSach = new javax.swing.JLabel();
        txtMaSach = new javax.swing.JTextField();
        panelButton = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        TimKiem = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        cbTimKiem = new javax.swing.JComboBox<>();
        panelTable = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableSach = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        btnNhapDuLieu = new javax.swing.JButton();
        btnXuatDuLieu = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(1120, 666));
        setMinimumSize(new java.awt.Dimension(1120, 666));
        setPreferredSize(new java.awt.Dimension(1120, 666));

        panelInfor.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        TenSach.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        TenSach.setText("Tên Sách");

        txtTenSach.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtTenSach.setPreferredSize(new java.awt.Dimension(64, 25));

        NXB.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        NXB.setText("NXB");

        NamXuatBan.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        NamXuatBan.setText("Năm XB");

        txtNamXB.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtNamXB.setPreferredSize(new java.awt.Dimension(64, 25));

        cbNXB.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        TacGia.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        TacGia.setText("Tác Giả");

        cbTacGia.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        cbTacGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbTacGiaActionPerformed(evt);
            }
        });

        TheLoai.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        TheLoai.setText("Thể loại");

        cbTheLoai.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        SoTrang.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        SoTrang.setText("Số trang");

        txtSoTrang.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtSoTrang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSoTrangActionPerformed(evt);
            }
        });

        txtSoLuong.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        NamXuatBan1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        NamXuatBan1.setText("Số lượng");

        MaSach.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        MaSach.setText("Mã sách");

        txtMaSach.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtMaSach.setPreferredSize(new java.awt.Dimension(64, 25));

        javax.swing.GroupLayout panelInforLayout = new javax.swing.GroupLayout(panelInfor);
        panelInfor.setLayout(panelInforLayout);
        panelInforLayout.setHorizontalGroup(
            panelInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInforLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(panelInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelInforLayout.createSequentialGroup()
                        .addComponent(NamXuatBan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtNamXB, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelInforLayout.createSequentialGroup()
                        .addComponent(NamXuatBan1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelInforLayout.createSequentialGroup()
                        .addComponent(MaSach)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtMaSach, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(114, 114, 114)
                .addGroup(panelInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelInforLayout.createSequentialGroup()
                        .addComponent(TenSach)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTenSach, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelInforLayout.createSequentialGroup()
                        .addGroup(panelInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelInforLayout.createSequentialGroup()
                                .addComponent(SoTrang)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtSoTrang)
                                .addGap(34, 34, 34))
                            .addGroup(panelInforLayout.createSequentialGroup()
                                .addComponent(TacGia)
                                .addGap(18, 18, 18)
                                .addComponent(cbTacGia, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(81, 81, 81)))
                .addGap(52, 52, 52)
                .addGroup(panelInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelInforLayout.createSequentialGroup()
                        .addComponent(NXB)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbNXB, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelInforLayout.createSequentialGroup()
                        .addComponent(TheLoai)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbTheLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panelInforLayout.setVerticalGroup(
            panelInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelInforLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelInforLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(panelInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(NamXuatBan)
                            .addComponent(txtNamXB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(NamXuatBan1)))
                    .addGroup(panelInforLayout.createSequentialGroup()
                        .addGroup(panelInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelInforLayout.createSequentialGroup()
                                .addGroup(panelInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(TenSach)
                                    .addComponent(txtTenSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(MaSach)
                                    .addComponent(txtMaSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(panelInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(TacGia)
                                    .addComponent(cbTacGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(panelInforLayout.createSequentialGroup()
                                .addGap(0, 5, Short.MAX_VALUE)
                                .addGroup(panelInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(NXB)
                                    .addComponent(cbNXB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(TheLoai)
                                    .addComponent(cbTheLoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(SoTrang)
                            .addComponent(txtSoTrang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        panelButton.setBorder(javax.swing.BorderFactory.createTitledBorder("Chức năng"));
        panelButton.setPreferredSize(new java.awt.Dimension(1120, 86));

        btnThem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        TimKiem.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        TimKiem.setText("Tìm Kiếm:");

        txtTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemActionPerformed(evt);
            }
        });

        cbTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cbTimKiem.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mã sách", "Tên sách", "Tác giả", "NXB", "Thể loại" }));

        javax.swing.GroupLayout panelButtonLayout = new javax.swing.GroupLayout(panelButton);
        panelButton.setLayout(panelButtonLayout);
        panelButtonLayout.setHorizontalGroup(
            panelButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelButtonLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(103, 103, 103)
                .addComponent(TimKiem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbTimKiem, 0, 144, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );
        panelButtonLayout.setVerticalGroup(
            panelButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelButtonLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(panelButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(TimKiem))
                    .addGroup(panelButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        tableSach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã sách", "Tên sách", "Tác giả", "NXB", "Thể loại", "Năm XB", "Số trang", "Số lượng"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableSach.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tableSach.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tableSach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableSachMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableSach);
        if (tableSach.getColumnModel().getColumnCount() > 0) {
            tableSach.getColumnModel().getColumn(0).setResizable(false);
            tableSach.getColumnModel().getColumn(0).setPreferredWidth(10);
            tableSach.getColumnModel().getColumn(1).setResizable(false);
            tableSach.getColumnModel().getColumn(1).setPreferredWidth(160);
            tableSach.getColumnModel().getColumn(2).setResizable(false);
            tableSach.getColumnModel().getColumn(2).setPreferredWidth(80);
            tableSach.getColumnModel().getColumn(3).setResizable(false);
            tableSach.getColumnModel().getColumn(4).setResizable(false);
            tableSach.getColumnModel().getColumn(4).setPreferredWidth(30);
            tableSach.getColumnModel().getColumn(5).setResizable(false);
            tableSach.getColumnModel().getColumn(5).setPreferredWidth(20);
            tableSach.getColumnModel().getColumn(6).setResizable(false);
            tableSach.getColumnModel().getColumn(6).setPreferredWidth(20);
            tableSach.getColumnModel().getColumn(7).setResizable(false);
            tableSach.getColumnModel().getColumn(7).setPreferredWidth(20);
        }

        javax.swing.GroupLayout panelTableLayout = new javax.swing.GroupLayout(panelTable);
        panelTable.setLayout(panelTableLayout);
        panelTableLayout.setHorizontalGroup(
            panelTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        panelTableLayout.setVerticalGroup(
            panelTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTableLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 414, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        btnNhapDuLieu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnNhapDuLieu.setText("Nhập dữ liệu");
        btnNhapDuLieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNhapDuLieuActionPerformed(evt);
            }
        });

        btnXuatDuLieu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnXuatDuLieu.setText("Xuất dữ liệu");
        btnXuatDuLieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatDuLieuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnXuatDuLieu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNhapDuLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(btnNhapDuLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnXuatDuLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelInfor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panelButton, javax.swing.GroupLayout.DEFAULT_SIZE, 1107, Short.MAX_VALUE))
                .addContainerGap(7, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelInfor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelTable, javax.swing.GroupLayout.PREFERRED_SIZE, 429, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    public JButton getBtnThem() {
        return btnThem;
    }

    public JButton getBtnSua() {
        return btnSua;
    }

    public JButton getBtnXoa() {
        return btnXoa;
    }

    public JButton getBtnNhapDuLieu() {
        return btnNhapDuLieu;
    }

    public JButton getBtnXuatDuLieu() {
        return btnXuatDuLieu;
    }

    public JComboBox<String> getCbNXB() {
        return cbNXB;
    }

    public JComboBox<String> getCbTacGia() {
        return cbTacGia;
    }

    public JComboBox<String> getCbTheLoai() {
        return cbTheLoai;
    }

    public JComboBox<String> getCbTimKiem() {
        return cbTimKiem;
    }

    public JTextField getTxtMaSach() {
        return txtMaSach;
    }

    public JTextField getTxtTenSach() {
        return txtTenSach;
    }

    public JTextField getTxtNamXB() {
        return txtNamXB;
    }

    public JTextField getTxtSoTrang() {
        return txtSoTrang;
    }

    public JTextField getTxtSoLuong() {
        return txtSoLuong;
    }

    public JTextField getTxtTimKiem() {
        return txtTimKiem;
    }

    public JTable getTableSach() {
        return tableSach;
    }

    private void loadInitialData() {
        SachController controller = new SachController(this);
    }

    private void cbTacGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbTacGiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbTacGiaActionPerformed

    private void txtSoTrangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSoTrangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSoTrangActionPerformed

    private void tableSachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableSachMouseClicked
        
    }//GEN-LAST:event_tableSachMouseClicked

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed

    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed

    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed

    }//GEN-LAST:event_btnXoaActionPerformed

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemActionPerformed

    }//GEN-LAST:event_txtTimKiemActionPerformed

    private void btnXuatDuLieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatDuLieuActionPerformed
        
    }//GEN-LAST:event_btnXuatDuLieuActionPerformed

    private void btnNhapDuLieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNhapDuLieuActionPerformed

    }//GEN-LAST:event_btnNhapDuLieuActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel MaSach;
    private javax.swing.JLabel NXB;
    private javax.swing.JLabel NamXuatBan;
    private javax.swing.JLabel NamXuatBan1;
    private javax.swing.JLabel SoTrang;
    private javax.swing.JLabel TacGia;
    private javax.swing.JLabel TenSach;
    private javax.swing.JLabel TheLoai;
    private javax.swing.JLabel TimKiem;
    private javax.swing.JButton btnNhapDuLieu;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXuatDuLieu;
    private javax.swing.JComboBox<String> cbNXB;
    private javax.swing.JComboBox<String> cbTacGia;
    private javax.swing.JComboBox<String> cbTheLoai;
    private javax.swing.JComboBox<String> cbTimKiem;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelButton;
    private javax.swing.JPanel panelInfor;
    private javax.swing.JPanel panelTable;
    private javax.swing.JTable tableSach;
    private javax.swing.JTextField txtMaSach;
    private javax.swing.JTextField txtNamXB;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtSoTrang;
    private javax.swing.JTextField txtTenSach;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}

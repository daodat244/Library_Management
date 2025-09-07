
package com.mycompany.demo_frontend.view;
import com.mycompany.demo_frontend.auth.AuthStorage;
import com.mycompany.demo_frontend.controller.PhieuMuonController;
import com.mycompany.demo_frontend.controller.PhieuTraController;
import com.mycompany.demo_frontend.controller.SachController;
import com.mycompany.demo_frontend.dto.NhanVienDTO;
import com.mycompany.demo_frontend.service.PhieuMuonService;
import com.mycompany.demo_frontend.service.PhieuTraService;
import com.mycompany.demo_frontend.service.SachService;
import java.awt.CardLayout;
import javax.swing.JOptionPane;

public class Menu extends javax.swing.JFrame {
    
    private ButtonColorHandler buttonColorHandler;
    private NhanVienDTO currentNhanVien;
    private PhieuMuonController phieuMuonController;
    private PhieuTraController phieuTraController;
    private SachController sachController;

    public Menu(NhanVienDTO nhanVien) {
        initComponents();
        // Lưu lại nhân viên để sử dụng ở các sự kiện
        this.currentNhanVien = nhanVien;

        if (nhanVien != null) {
            lblTenNV.setText("Xin chào: " + nhanVien.getTennv());
        } else {
            lblTenNV.setText("Xin chào: [Không xác định]");
        }
        
        lblTenNV.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblTenNV.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                openPersonalPage();
            }
        });

        
        PanelSach panelKhoSach = new PanelSach();
        PanelTacGia panelTacGia = new PanelTacGia();
        PanelNhaXuatBan panelNhaXuatBan = new PanelNhaXuatBan();
        PanelTheLoai panelTheLoai = new PanelTheLoai();
        PanelPhieuMuon panelPhieuMuon = new PanelPhieuMuon();
        PanelPhieuTra panelPhieuTra = new PanelPhieuTra();
        PanelDocGia panelDocGia = new PanelDocGia();
        PanelNhanVien panelNhanVien = new PanelNhanVien();
        PanelThongKe panelThongKe = new PanelThongKe();
        PhieuMuonService phieuMuonService = new PhieuMuonService();
        SachService sachService = new SachService();
        phieuMuonController = new PhieuMuonController(panelPhieuMuon, phieuMuonService, sachService);
        phieuTraController = new PhieuTraController(panelPhieuTra, new PhieuTraService(), sachService);
        sachController = new SachController(panelKhoSach);
        
        
        panelContent.add(panelNhaXuatBan, "nhaXuatBan");
        panelContent.add(panelKhoSach, "sach");
        panelContent.add(panelTacGia, "tacGia");
        panelContent.add(panelTheLoai, "theLoai");
        panelContent.add(panelPhieuMuon, "phieuMuon");
        panelContent.add(panelPhieuTra, "phieuTra");
        panelContent.add(panelDocGia, "docGia");
        panelContent.add(panelNhanVien, "nhanVien");
        panelContent.add(panelThongKe, "thongKe");

        
        panelContent.setVisible(false); // <== Thêm dòng này để ẩn ban đầu
        
        buttonColorHandler = new ButtonColorHandler(
            btnSach, btnPhieuMuon, btnDocGia, btnNhanVien, btnTacGia,
            btnNhaXuatBan, btnTaiKhoan, btnSuKien, btnMuonPhong, btnTheLoai,    
            btnThongKe, btnPhieuTra
        );
    }
    
    private void openPersonalPage() {
        FramePersonalPage frame = new FramePersonalPage(currentNhanVien);
        frame.setLocationRelativeTo(this); // Căn giữa frame mới
        frame.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        MenuLayout = new javax.swing.JPanel();
        btnNhanVien = new javax.swing.JButton();
        btnSach = new javax.swing.JButton();
        btnPhieuMuon = new javax.swing.JButton();
        btnDocGia = new javax.swing.JButton();
        btnThongKe = new javax.swing.JButton();
        btnTaiKhoan = new javax.swing.JButton();
        btnNhaXuatBan = new javax.swing.JButton();
        btnTacGia = new javax.swing.JButton();
        btnSuKien = new javax.swing.JButton();
        btnMuonPhong = new javax.swing.JButton();
        btnTheLoai = new javax.swing.JButton();
        btnPhieuTra = new javax.swing.JButton();
        panelLogo = new javax.swing.JPanel();
        lblHome = new javax.swing.JLabel();
        btnLogOut = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        lblTenNV = new javax.swing.JLabel();
        panelContent = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1366, 768));
        setResizable(false);
        setSize(new java.awt.Dimension(1366, 768));

        MenuLayout.setPreferredSize(new java.awt.Dimension(230, 768));

        btnNhanVien.setBackground(new java.awt.Color(42, 71, 89));
        btnNhanVien.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        btnNhanVien.setForeground(new java.awt.Color(238, 238, 238));
        btnNhanVien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/librarian.png"))); // NOI18N
        btnNhanVien.setText("DS NHÂN VIÊN");
        btnNhanVien.setBorder(null);
        btnNhanVien.setFocusPainted(false);
        btnNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNhanVienActionPerformed(evt);
            }
        });

        btnSach.setBackground(new java.awt.Color(42, 71, 89));
        btnSach.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        btnSach.setForeground(new java.awt.Color(238, 238, 238));
        btnSach.setIcon(new javax.swing.ImageIcon(getClass().getResource("/book.png"))); // NOI18N
        btnSach.setText("KHO SÁCH");
        btnSach.setBorder(null);
        btnSach.setBorderPainted(false);
        btnSach.setFocusPainted(false);
        btnSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSachActionPerformed(evt);
            }
        });

        btnPhieuMuon.setBackground(new java.awt.Color(42, 71, 89));
        btnPhieuMuon.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        btnPhieuMuon.setForeground(new java.awt.Color(238, 238, 238));
        btnPhieuMuon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/phieuMuon.png"))); // NOI18N
        btnPhieuMuon.setText("PHIẾU MƯỢN");
        btnPhieuMuon.setBorder(null);
        btnPhieuMuon.setFocusPainted(false);
        btnPhieuMuon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPhieuMuonActionPerformed(evt);
            }
        });

        btnDocGia.setBackground(new java.awt.Color(42, 71, 89));
        btnDocGia.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        btnDocGia.setForeground(new java.awt.Color(238, 238, 238));
        btnDocGia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/student.png"))); // NOI18N
        btnDocGia.setText("DS SINH VIÊN");
        btnDocGia.setBorder(null);
        btnDocGia.setFocusPainted(false);
        btnDocGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDocGiaActionPerformed(evt);
            }
        });

        btnThongKe.setBackground(new java.awt.Color(42, 71, 89));
        btnThongKe.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        btnThongKe.setForeground(new java.awt.Color(238, 238, 238));
        btnThongKe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/thongKe.png"))); // NOI18N
        btnThongKe.setText("THỐNG KÊ");
        btnThongKe.setBorder(null);
        btnThongKe.setFocusPainted(false);
        btnThongKe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThongKeActionPerformed(evt);
            }
        });

        btnTaiKhoan.setBackground(new java.awt.Color(42, 71, 89));
        btnTaiKhoan.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        btnTaiKhoan.setForeground(new java.awt.Color(238, 238, 238));
        btnTaiKhoan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/account.png"))); // NOI18N
        btnTaiKhoan.setText("TÀI KHOẢN");
        btnTaiKhoan.setBorder(null);
        btnTaiKhoan.setFocusPainted(false);
        btnTaiKhoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaiKhoanActionPerformed(evt);
            }
        });

        btnNhaXuatBan.setBackground(new java.awt.Color(42, 71, 89));
        btnNhaXuatBan.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        btnNhaXuatBan.setForeground(new java.awt.Color(238, 238, 238));
        btnNhaXuatBan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/nxb.png"))); // NOI18N
        btnNhaXuatBan.setText("NHÀ XUẤT BẢN");
        btnNhaXuatBan.setBorder(null);
        btnNhaXuatBan.setFocusPainted(false);
        btnNhaXuatBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNhaXuatBanActionPerformed(evt);
            }
        });

        btnTacGia.setBackground(new java.awt.Color(42, 71, 89));
        btnTacGia.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        btnTacGia.setForeground(new java.awt.Color(238, 238, 238));
        btnTacGia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/author.png"))); // NOI18N
        btnTacGia.setText("TÁC GIẢ");
        btnTacGia.setBorder(null);
        btnTacGia.setFocusPainted(false);
        btnTacGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTacGiaActionPerformed(evt);
            }
        });

        btnSuKien.setBackground(new java.awt.Color(42, 71, 89));
        btnSuKien.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        btnSuKien.setForeground(new java.awt.Color(238, 238, 238));
        btnSuKien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/event.png"))); // NOI18N
        btnSuKien.setText("SỰ KIỆN");
        btnSuKien.setBorder(null);
        btnSuKien.setFocusPainted(false);
        btnSuKien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuKienActionPerformed(evt);
            }
        });

        btnMuonPhong.setBackground(new java.awt.Color(42, 71, 89));
        btnMuonPhong.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        btnMuonPhong.setForeground(new java.awt.Color(238, 238, 238));
        btnMuonPhong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/room.png"))); // NOI18N
        btnMuonPhong.setText("MƯỢN PHÒNG HỌC");
        btnMuonPhong.setBorder(null);
        btnMuonPhong.setFocusPainted(false);
        btnMuonPhong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMuonPhongActionPerformed(evt);
            }
        });

        btnTheLoai.setBackground(new java.awt.Color(42, 71, 89));
        btnTheLoai.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        btnTheLoai.setForeground(new java.awt.Color(238, 238, 238));
        btnTheLoai.setIcon(new javax.swing.ImageIcon(getClass().getResource("/category.png"))); // NOI18N
        btnTheLoai.setText("THỂ LOẠI SÁCH");
        btnTheLoai.setBorder(null);
        btnTheLoai.setFocusPainted(false);
        btnTheLoai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTheLoaiActionPerformed(evt);
            }
        });

        btnPhieuTra.setBackground(new java.awt.Color(42, 71, 89));
        btnPhieuTra.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        btnPhieuTra.setForeground(new java.awt.Color(238, 238, 238));
        btnPhieuTra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/phieuTra.png"))); // NOI18N
        btnPhieuTra.setText("PHIẾU TRẢ");
        btnPhieuTra.setBorder(null);
        btnPhieuTra.setFocusPainted(false);
        btnPhieuTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPhieuTraActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout MenuLayoutLayout = new javax.swing.GroupLayout(MenuLayout);
        MenuLayout.setLayout(MenuLayoutLayout);
        MenuLayoutLayout.setHorizontalGroup(
            MenuLayoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnPhieuTra, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnSach, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnTheLoai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnThongKe, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnSuKien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnMuonPhong, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)
            .addComponent(btnNhaXuatBan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnTaiKhoan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnTacGia, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnPhieuMuon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnDocGia, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        MenuLayoutLayout.setVerticalGroup(
            MenuLayoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MenuLayoutLayout.createSequentialGroup()
                .addComponent(btnSach, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPhieuMuon, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDocGia, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTacGia, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNhaXuatBan, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSuKien, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnMuonPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTheLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnThongKe, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPhieuTra, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        panelLogo.setBackground(new java.awt.Color(42, 71, 89));
        panelLogo.setPreferredSize(new java.awt.Dimension(1366, 60));

        lblHome.setBackground(new java.awt.Color(42, 71, 89));
        lblHome.setFont(new java.awt.Font("Segoe UI", 1, 26)); // NOI18N
        lblHome.setForeground(new java.awt.Color(238, 238, 238));
        lblHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/library2.png"))); // NOI18N
        lblHome.setText("QUẢN LÝ THƯ VIỆN");

        btnLogOut.setBackground(new java.awt.Color(42, 71, 89));
        btnLogOut.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnLogOut.setForeground(new java.awt.Color(102, 153, 255));
        btnLogOut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/out2.png"))); // NOI18N
        btnLogOut.setBorder(null);
        btnLogOut.setFocusPainted(false);
        btnLogOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogOutActionPerformed(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menuIcon.png"))); // NOI18N

        lblTenNV.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTenNV.setForeground(new java.awt.Color(255, 255, 255));
        lblTenNV.setText("Xin chào: ");

        javax.swing.GroupLayout panelLogoLayout = new javax.swing.GroupLayout(panelLogo);
        panelLogo.setLayout(panelLogoLayout);
        panelLogoLayout.setHorizontalGroup(
            panelLogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLogoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 482, Short.MAX_VALUE)
                .addComponent(lblHome)
                .addGap(212, 212, 212)
                .addComponent(lblTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLogOut, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelLogoLayout.setVerticalGroup(
            panelLogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLogoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelLogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLogOut, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addGroup(panelLogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(lblHome, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
                .addComponent(lblTenNV))
        );

        panelContent.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        panelContent.setMaximumSize(new java.awt.Dimension(1120, 666));
        panelContent.setMinimumSize(new java.awt.Dimension(1120, 666));
        panelContent.setPreferredSize(new java.awt.Dimension(1120, 666));
        panelContent.setVerifyInputWhenFocusTarget(false);
        panelContent.setLayout(new java.awt.CardLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(MenuLayout, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelContent, javax.swing.GroupLayout.DEFAULT_SIZE, 1139, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(panelLogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(MenuLayout, javax.swing.GroupLayout.DEFAULT_SIZE, 702, Short.MAX_VALUE)
                    .addComponent(panelContent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLogOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogOutActionPerformed
        // Xoá token nếu cần
        AuthStorage.clear();

        // Mở lại login
        Login login = new Login();
        login.setVisible(true);

        // Đóng menu
        this.setVisible(false); // hoặc this.setVisible(false);
    }//GEN-LAST:event_btnLogOutActionPerformed

    private void btnTheLoaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTheLoaiActionPerformed
        CardLayout cl = (CardLayout)(panelContent.getLayout());
        panelContent.setVisible(true);
        cl.show(panelContent, "theLoai");
        buttonColorHandler.changeButtonColor(btnTheLoai);
    }//GEN-LAST:event_btnTheLoaiActionPerformed

    private void btnMuonPhongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMuonPhongActionPerformed

    }//GEN-LAST:event_btnMuonPhongActionPerformed

    private void btnSuKienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuKienActionPerformed

    }//GEN-LAST:event_btnSuKienActionPerformed

    private void btnTacGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTacGiaActionPerformed
        CardLayout cl = (CardLayout)(panelContent.getLayout());
        panelContent.setVisible(true);
        cl.show(panelContent, "tacGia");
        buttonColorHandler.changeButtonColor(btnTacGia);
    }//GEN-LAST:event_btnTacGiaActionPerformed

    private void btnNhaXuatBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNhaXuatBanActionPerformed
        CardLayout cl = (CardLayout)(panelContent.getLayout());
        panelContent.setVisible(true);
        cl.show(panelContent, "nhaXuatBan");
        buttonColorHandler.changeButtonColor(btnNhaXuatBan);
    }//GEN-LAST:event_btnNhaXuatBanActionPerformed

    private void btnTaiKhoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaiKhoanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTaiKhoanActionPerformed

    private void btnThongKeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThongKeActionPerformed
        CardLayout cl = (CardLayout)(panelContent.getLayout());
        panelContent.setVisible(true);
        cl.show(panelContent, "thongKe");
        buttonColorHandler.changeButtonColor(btnThongKe);
    }//GEN-LAST:event_btnThongKeActionPerformed

    private void btnDocGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDocGiaActionPerformed
        // TODO add your handling code here:
        CardLayout cl = (CardLayout)(panelContent.getLayout());
        panelContent.setVisible(true);
        cl.show(panelContent, "docGia");
        buttonColorHandler.changeButtonColor(btnDocGia);
    }//GEN-LAST:event_btnDocGiaActionPerformed

    private void btnPhieuMuonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPhieuMuonActionPerformed
        CardLayout cl = (CardLayout)(panelContent.getLayout());
        panelContent.setVisible(true);
        cl.show(panelContent, "phieuMuon");
        buttonColorHandler.changeButtonColor(btnPhieuMuon);
        phieuMuonController.loadTablePhieuMuon();
        phieuMuonController.loadTableTimSach();
    }//GEN-LAST:event_btnPhieuMuonActionPerformed

    private void btnSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSachActionPerformed
        CardLayout cl = (CardLayout)(panelContent.getLayout());
        panelContent.setVisible(true);
        cl.show(panelContent, "sach");
        buttonColorHandler.changeButtonColor(btnSach);
        sachController.loadTableData();
        sachController.clearFields();
    }//GEN-LAST:event_btnSachActionPerformed

    private void btnNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNhanVienActionPerformed
    if (!"QUAN_LY".equalsIgnoreCase(currentNhanVien.getRole())) {
        JOptionPane.showMessageDialog(this, 
            "Bạn không có quyền truy cập vào chức năng này!", 
            "Thông báo", 
            JOptionPane.WARNING_MESSAGE);
        return;
    }
    CardLayout cl = (CardLayout)(panelContent.getLayout());
    panelContent.setVisible(true);
    cl.show(panelContent, "nhanVien");
    buttonColorHandler.changeButtonColor(btnNhanVien);
    }//GEN-LAST:event_btnNhanVienActionPerformed

    private void btnPhieuTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPhieuTraActionPerformed
        CardLayout cl = (CardLayout)(panelContent.getLayout());
        panelContent.setVisible(true);
        cl.show(panelContent, "phieuTra");
        buttonColorHandler.changeButtonColor(btnPhieuTra);
        phieuTraController.loadPhieuMuonChuaTra();
        phieuTraController.loadPhieuTra();
        phieuTraController.clearDetailPanel();
    }//GEN-LAST:event_btnPhieuTraActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel MenuLayout;
    private javax.swing.JButton btnDocGia;
    private javax.swing.JButton btnLogOut;
    private javax.swing.JButton btnMuonPhong;
    private javax.swing.JButton btnNhaXuatBan;
    private javax.swing.JButton btnNhanVien;
    private javax.swing.JButton btnPhieuMuon;
    private javax.swing.JButton btnPhieuTra;
    private javax.swing.JButton btnSach;
    private javax.swing.JButton btnSuKien;
    private javax.swing.JButton btnTacGia;
    private javax.swing.JButton btnTaiKhoan;
    private javax.swing.JButton btnTheLoai;
    private javax.swing.JButton btnThongKe;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lblHome;
    private javax.swing.JLabel lblTenNV;
    private javax.swing.JPanel panelContent;
    private javax.swing.JPanel panelLogo;
    // End of variables declaration//GEN-END:variables
}

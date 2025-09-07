package com.mycompany.demo_frontend.controller;

import com.mycompany.demo_frontend.dto.NhanVienDTO;
import com.mycompany.demo_frontend.service.NhanVienService;
import com.mycompany.demo_frontend.view.FrameAccountSetup;
import com.mycompany.demo_frontend.view.FramePersonalPage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class PersonalPageController {
    private final NhanVienService nhanVienService = new NhanVienService();
    private FramePersonalPage viewPersonalPage;
    private FrameAccountSetup viewAccountSetup;
    private NhanVienDTO nhanVien;

    // Constructor cho từng frame
    public PersonalPageController(FramePersonalPage viewPP, NhanVienDTO nhanVien) {
        this.viewPersonalPage = viewPP;
        this.nhanVien = nhanVien;
    }

    public PersonalPageController(FrameAccountSetup viewAS, NhanVienDTO nhanVien) {
        this.viewAccountSetup = viewAS;
        this.nhanVien = nhanVien;
    }

    // Sự kiện cho trang cá nhân
    public void initPPHandlers() {
        viewPersonalPage.getBtnAccountSetup().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Mở FrameAccountSetup với nhanVien này
                FrameAccountSetup setupFrame = new FrameAccountSetup(nhanVien);
                setupFrame.setLocationRelativeTo(viewPersonalPage);
                setupFrame.setVisible(true);
            }
        });
    }

    // Sự kiện cho Account Setup
    public void initASHandlers() {
        viewAccountSetup.getBtnConfirm().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Xử lý đổi mật khẩu ở đây!
                // Gợi ý: Lấy thông tin từ viewAccountSetup, gọi service đổi mật khẩu.
            }
        });
    }
    private void handleChangePassword() {
        String oldPass = new String(viewAccountSetup.getTxtOldPass().getPassword()).trim();
        String newPass = new String(viewAccountSetup.getTxtNewPass().getPassword()).trim();
        String confirmPass = new String(viewAccountSetup.getTxtConfirmPass().getPassword()).trim();

        if (oldPass.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()) {
            JOptionPane.showMessageDialog(viewAccountSetup, "Không được để trống bất kỳ trường nào!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!newPass.equals(confirmPass)) {
            JOptionPane.showMessageDialog(viewAccountSetup, "Mật khẩu mới và nhập lại không khớp!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Kiểm tra độ mạnh mật khẩu nếu muốn...
        // Gọi service đổi mật khẩu:
//        boolean success = nhanVienService.changePassword(nhanVien.getUsername(), oldPass, newPass);
//        if (success) {
//            JOptionPane.showMessageDialog(viewPersonalPage, "Đổi mật khẩu thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
//        } else {
//            JOptionPane.showMessageDialog(viewPersonalPage, "Mật khẩu cũ không đúng hoặc có lỗi xảy ra!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//        }
    }

}

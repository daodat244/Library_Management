package com.mycompany.demo_frontend.controller;

import com.mycompany.demo_frontend.service.LoginService;
import com.mycompany.demo_frontend.dto.LoginResponse;
import com.mycompany.demo_frontend.auth.AuthStorage;
import com.mycompany.demo_frontend.dto.NhanVienDTO;
import com.mycompany.demo_frontend.service.NhanVienService;
import com.mycompany.demo_frontend.view.Login;
import com.mycompany.demo_frontend.view.Menu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController {
    private final Login loginPanel;
    private final LoginService loginService;

    public LoginController(Login loginPanel) {
        this.loginPanel = loginPanel;
        this.loginService = new LoginService();
        initController();
    }

    private void initController() {
        loginPanel.getBtnLogin().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doLogin();
            }
        });
    }

    private void doLogin() {
        String username = loginPanel.getTxtUser().getText().trim();
        String password = new String(loginPanel.getTxtPass().getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(loginPanel, "Vui lòng nhập đầy đủ thông tin");
            return;
        }

        LoginResponse response = loginService.login(username, password);
        if (response != null) {
            AuthStorage.saveTokens(response.getAccessToken(), response.getRefreshToken());
            NhanVienDTO nhanVien = new NhanVienService().getCurrentNhanVien(response.getAccessToken());
            AuthStorage.setCurrentNhanVien(nhanVien);
            System.out.println("Access Token: " + AuthStorage.getAccessToken());
            JOptionPane.showMessageDialog(loginPanel, "Đăng nhập thành công");

            // ✅ Mở Menu và truyền thông tin nhân viên
            Menu menu = new Menu(nhanVien);
            menu.setVisible(true);
            loginPanel.dispose();
        } else {
            JOptionPane.showMessageDialog(loginPanel, "Đăng nhập thất bại. Kiểm tra lại thông tin.");
        }
    }


}

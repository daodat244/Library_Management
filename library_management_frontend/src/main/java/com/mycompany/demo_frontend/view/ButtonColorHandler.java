package com.mycompany.demo_frontend.view;

import java.awt.Color;
import javax.swing.JButton;

public class ButtonColorHandler {
    private final Color defaultColor = new Color(42, 71, 89); 
    private final Color selectedColor = new Color(227, 238, 178); 
    private final Color defaultForegroundColor = new Color(238, 238, 238); 
    private final Color selectedForegroundColor = Color.BLACK;
    private final JButton[] buttons; 

    // Constructor nhận danh sách các button
    public ButtonColorHandler(JButton... buttons) {
        this.buttons = buttons;
        setDefaultButtonColors();
    }

    // Hàm thay đổi màu của button được chọn
    public void changeButtonColor(JButton selectedButton) {
        // Đặt lại màu mặc định cho tất cả các button
        for (JButton button : buttons) {
            button.setBackground(defaultColor);
            button.setForeground(defaultForegroundColor);
        }

        // Đổi màu cho button được chọn
        selectedButton.setBackground(selectedColor);
        selectedButton.setForeground(selectedForegroundColor);
    }

    // Hàm đặt màu mặc định cho các button
    private void setDefaultButtonColors() {
        for (JButton button : buttons) {
            button.setBackground(defaultColor);
            button.setForeground(defaultForegroundColor);
        }
    }

}

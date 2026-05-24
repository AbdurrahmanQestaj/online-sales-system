package org.example.onlinesalessystem.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.onlinesalessystem.models.User;
import org.example.onlinesalessystem.models.dto.LoginDto;
import org.example.onlinesalessystem.services.LoginService;
import org.example.onlinesalessystem.utils.SessionManager;

public class LoginController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    private final LoginService loginService;

    public LoginController() {
        this.loginService = new LoginService();
    }

    @FXML
    private void handleLogin() {
        try {
            LoginDto loginDto = new LoginDto(
                    usernameField.getText(),
                    passwordField.getText()
            );

            User user = loginService.login(loginDto);

            SessionManager.login(user);

            goToHome();

        } catch (Exception e) {
            messageLabel.setText(e.getMessage());
        }
    }

    @FXML
    private void goToSignup() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/views/signup-view.fxml")
            );

            Stage stage = (Stage) usernameField.getScene().getWindow();
            Scene scene = new Scene(fxmlLoader.load(), 600, 450);

            stage.setScene(scene);
        } catch (Exception e) {
            messageLabel.setText("Gabim gjate hapjes se Signup.");
        }
    }

    private void goToHome() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/views/home-view.fxml")
        );

        Stage stage = (Stage) usernameField.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 800, 500);

        stage.setScene(scene);
    }
}

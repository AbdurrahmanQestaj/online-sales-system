package org.example.onlinesalessystem.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.onlinesalessystem.models.dto.SignupDto;
import org.example.onlinesalessystem.services.SignupService;

public class SignupController {
    @FXML
    private TextField fullNameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Label messageLabel;

    private final SignupService signupService;

    public SignupController() {
        this.signupService = new SignupService();
    }

    @FXML
    private void handleSignup() {
        try {
            SignupDto signupDto = new SignupDto(
                    fullNameField.getText(),
                    emailField.getText(),
                    usernameField.getText(),
                    passwordField.getText(),
                    confirmPasswordField.getText()
            );

            signupService.signup(signupDto);

            messageLabel.setText("Regjistrimi u krye me sukses!");
            messageLabel.setStyle("-fx-text-fill: green;");

            clearFields();

        } catch (Exception e) {
            messageLabel.setText(e.getMessage());
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    private void goToLogin() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/views/login-view.fxml")
            );

            Stage stage = (Stage) usernameField.getScene().getWindow();
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);

            stage.setScene(scene);
        } catch (Exception e) {
            messageLabel.setText("Gabim gjate kthimit ne Login.");
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }

    private void clearFields() {
        fullNameField.clear();
        emailField.clear();
        usernameField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
    }
}
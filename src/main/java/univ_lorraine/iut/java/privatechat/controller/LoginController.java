package univ_lorraine.iut.java.privatechat.controller;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.EventObject;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import univ_lorraine.iut.java.privatechat.App;
import javafx.fxml.FXMLLoader;

import static univ_lorraine.iut.java.privatechat.App.loadFXML;

public class LoginController {
    @FXML
    private PasswordField login;

    @FXML
    private TextField user;

    @FXML
    private Text errorLabel;

    public LoginController() throws IOException {
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        return bytesToHex(digest);
    }
    @FXML
    private void login() throws IOException, NoSuchAlgorithmException {
        String password = login.getText();
        String username = user.getText();
        System.out.println("MDP : " + password);
        System.out.println("Login : " + username);

        // Vérification PASSWORD et USERNAME NON NULL

        if (username == "") {
            errorLabel.setText("Veuillez saisir votre username !");
        } else if (password == "") errorLabel.setText("Veuillez saisir votre mot de passe !");
        else {
            String filename = "pseudo.pwd";
            File file = new File(filename);
            if (file.exists()) {
                System.out.println("Le fichier " + filename + " existe.");

                // Récupère le contenu et on le compare avec la saisie
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line = reader.readLine();
                String storedLogin = null;
                String storedPassword = null;
                while (line != null) {
                    String[] parts = line.split("=");
                    if (parts.length == 2) {
                        String key = parts[0].trim();
                        String value = parts[1].trim();
                        if (key.equals("login")) {
                            storedLogin = value;
                        } else if (key.equals("password")) {
                            storedPassword = value;
                        }
                    }
                    line = reader.readLine();
                }

                reader.close();

                if (username.equals(storedLogin) && hashPassword(password).equals(storedPassword)) {
                    System.out.println("username et mot de passe corrects.");
                    App.setRoot("chat");

                } else {
                    System.out.println("Login ou mot de passe incorrects.");
                    //Message d'erreur qui dit que les identifiants sont incorrects
                    errorLabel.setText("username ou mot de passe incorrect");

                }

            } else {
                System.out.println("Le fichier " + filename + " n'existe pas.");
                // On créer le fichier + on ajoute le login+password hashé dans le fichier.
                boolean created = file.createNewFile();
                if (created) {
                    System.out.println("Le fichier " + filename + " a été créé.");
                    FileWriter writer = new FileWriter(file);
                    writer.write("login=" + username + "\n");
                    writer.write("password =" +hashPassword(password) + "\n");
                    writer.close();
                    App.setRoot("chat");
                } else {
                    System.out.println("Impossible de créer le fichier " + filename + ".");
                }


            }
        }

    }
}


package univ_lorraine.iut.java.privatechat.controller;

import java.io.*;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import univ_lorraine.iut.java.privatechat.App;


public class ChatController {
    @FXML
    private TextField nvcontact;

    @FXML
    private ListView liste;
@FXML
private TextField champRecherche;
    @FXML
    private Button btnAffCont;
    @FXML
    private TextField port;
    @FXML
    private TextField adIP;
    @FXML
    private Button lanceRecherche;
    private static final String FICHIER_LISTE = "listecontacts.txt";

    @FXML
    private void logout() throws IOException {
        App.setRoot("login");
    }

    @FXML
    private void ajouter() throws IOException {
        String ajoutNouveau = nvcontact.getText();
        String adresseIp = adIP.getText();
        String port2 = port.getText();
        liste.getItems().add(ajoutNouveau);
        //On enregistre ces données dans un fichier qui sera ouvert lors de la connexion réussie
        FileWriter fileWriter = new FileWriter("listecontacts.txt", true); // true pour ajouter au fichier existant
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.println(ajoutNouveau);
        printWriter.close();
        nvcontact.clear();
        adIP.clear();
        port.clear();


    }

   @FXML
    private void afficherContacts() throws IOException {
        // Charger les éléments de la liste à partir du fichier
       File fichier = new File(FICHIER_LISTE);
       if (fichier.exists()) {
           BufferedReader reader = new BufferedReader(new FileReader(FICHIER_LISTE));
           String ligne;
           while ((ligne = reader.readLine()) != null) {
               liste.getItems().add(ligne);
           }
           reader.close();
       }
       btnAffCont.setDisable(true);
   }
    @FXML
    private void rechercher() {

        String recherche = champRecherche.getText().trim().toLowerCase();
        liste.getItems().clear();

// Charger les éléments de la liste à partir du fichier
        File fichier = new File(FICHIER_LISTE);
        if (fichier.exists()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(FICHIER_LISTE));
                String ligne;
                while ((ligne = reader.readLine()) != null) {
                    if (ligne.toLowerCase().contains(recherche)) {
                        liste.getItems().add(ligne);
                    }
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        champRecherche.clear();
    }

}










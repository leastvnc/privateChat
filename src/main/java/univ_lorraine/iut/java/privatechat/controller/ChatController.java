package univ_lorraine.iut.java.privatechat.controller;

import java.io.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import univ_lorraine.iut.java.privatechat.App;
import univ_lorraine.iut.java.privatechat.model.Contact;

public class ChatController {
    @FXML
    private TextField usr;

    @FXML
    private ListView liste;
@FXML
private TextField champRecherche;
    @FXML
    private Button btnAffCont;
    @FXML
    private TextField porttxt;
    @FXML
    private TextField adIP;
    @FXML
    private TextField message;
    @FXML
    private ListView ListeMsg;

    private static final String FICHIER_LISTE = "listecontacts.txt";

    @FXML
    private void logout() throws IOException {
        App.setRoot("login");
    }

    //Fonction ajouter qui nous permet d'ajouter un contact dans la liste et donc dans le fichier "listecontacts.txt" :
    @FXML
    private void ajouter() throws IOException {
        //On prend la valeur des TextFields
        String pseudo = usr.getText();
        String adressIp = adIP.getText();
        String port2 = porttxt.getText();

        //On ajoute ces valeurs à la classe contact
        Contact contact = new Contact(pseudo, adressIp, port2);

        //On ajoute contact à la liste des contacts nommée "liste"
        liste.getItems().add(contact);

        //On enregistre ces données dans un fichier qui sera ouvert lorsque la connexion est réussie
        FileWriter fileWriter = new FileWriter("listecontacts.txt", true);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.println(contact);
        printWriter.close();

        //On créer un fichier nommé "pseudo(adip,port).txt" qui va enregistrer les conversations l'utilisateur séléctionné.
        String fichierContact = pseudo + ".txt";
        File nouveauFichierMessages = new File(fichierContact);
        if (!nouveauFichierMessages.exists()) {
            nouveauFichierMessages.createNewFile();
            PrintWriter ecrire = new PrintWriter(new FileWriter(nouveauFichierMessages));
        }
        //On vide les TextFields
        usr.clear();
        adIP.clear();
        porttxt.clear();
    }

    //Fonction ajouterMessage qui nous permet d'enregistrer un message saisi dans le TextField dans un fichier "contact".txt
    @FXML
    private void ajouterMessage() throws IOException {
        String messageNv = message.getText();
        String contactSelectionne = (String) liste.getSelectionModel().getSelectedItem();

        // Vérifier si un contact a été sélectionné
        if (contactSelectionne != null && !contactSelectionne.isEmpty()) {

            // Vérifier si le fichier existe pour le contact sélectionné
            File fichierContact = new File(contactSelectionne + ".txt");
            if (!fichierContact.exists()) {
                fichierContact.createNewFile();
            }

            // Écrire le message dans le fichier
            FileWriter fileWriter = new FileWriter(fichierContact, true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(messageNv);
            printWriter.close();
            afficherMessages(fichierContact);
        }
        }

        //Fonction afficher message qui va afficher le fichier des messages enregistrés dans le TextArea.
    //Mais le fichier s'affiche seulement après qu'on ait envoyé un message à cette personne.
    private boolean listeAffichee = false;
    @FXML
    private void afficherMessages(File fichierContact) throws IOException {

        if (fichierContact.exists() && !listeAffichee) {
            BufferedReader reader = new BufferedReader(new FileReader(fichierContact));
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                ListeMsg.getItems().add(ligne);
            }
            reader.close();
            listeAffichee = true;
        }
        message.clear();
    }
//Fonction afficherContacts va afficher les contacts enregistrés dans le fichier : "listecontacts.txt" ( affichable à l'aide du bouton).
   @FXML
    private void afficherContacts() throws IOException {
       File fichier = new File(FICHIER_LISTE); //on défini le fichier
       if (fichier.exists()) {
           BufferedReader reader = new BufferedReader(new FileReader(FICHIER_LISTE));
           String ligne;
           while ((ligne = reader.readLine()) != null) {
               liste.getItems().add(ligne);
           }
           reader.close();
       }
       btnAffCont.setDisable(true);//On fait en sorte que le bouton soit éxecutable qu'une seule fois
   }

   //Fonction rechercher qui nous permet de rechercher un contact avec son pseudo, pour réafficher la liste des contacts il suffit de vider le TextField et cliquer sur le bouton recherche.
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










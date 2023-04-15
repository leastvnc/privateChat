package ClientServeur;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class client {

    public static void main(String[] args) throws IOException {
        Socket clientSocket = null;
        try {
            // Nous nous contectons au serveur sur le port 12345
            clientSocket = new Socket("localhost",12345);
        } catch (UnknownHostException e) {
            System.err.println("Le serveur inconnu : " + e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Oh une erreur et survenue lors de la connexion au serveur : " + e.getMessage());
            System.exit(1);
        }

        // Nous retrouvons ici les flux d'entrée
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        // Nous retrouvons ici les flux de sortie
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

        // Lecture de l'entrée utilisateur
        BufferedReader inputUtilisateur = new BufferedReader(new InputStreamReader(System.in));
        String messageUtilisateur = null;
        // Boucle while permettant l'envoi de messages au serveur
        while ((messageUtilisateur = inputUtilisateur.readLine()) != null) {
            out.println(messageUtilisateur);
            System.out.println("Message envoyé : " + messageUtilisateur);
            // Nous receptionnons ici la réponse émise par le serveur
            System.out.println("Réponse du serveur : " + in.readLine());
        }

        // Nous procédons à la fermeture des flux et du socket client
        out.close();
        in.close();
        clientSocket.close();
    }
}
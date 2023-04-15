package ClientServeur;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Serveur {

    public static void main(String[] args) throws IOException {
        ServerSocket serveurSocket = null;
        try {
            // Nous créons un socket serveur sur le port 1234
            serveurSocket = new ServerSocket(12345);
        } catch (IOException e) {
            System.err.println("Erreur lors de la création du socket serveur : " + e.getMessage());
            System.exit(1);
        }

        Socket clientSocket = null;
        try {
            System.out.println("En attente de connexion...");
            // Nous attendons ici la connexion d'un client
            clientSocket = serveurSocket.accept();
            System.out.println("Connexion établie avec le client " + clientSocket.getInetAddress());
        } catch (IOException e) {
            System.err.println("Erreur lors de l'acceptation de la connexion : " + e.getMessage());
            System.exit(1);
        }

        // Nous retrouvons ici les flux d'entrée
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        // Nous retrouvons ici les flux de sortie
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

        // Boucle while en attente du message du client
        String messageClient = null;
        while ((messageClient = in.readLine()) != null) {
            System.out.println("Message reçu : " + messageClient);
            // Réponse reçu du client
            out.println("Message reçu : " + messageClient);
        }


        // Nous procédons à la fermeture des flux et du socket
        out.close();
        in.close();
        clientSocket.close();

        // Nous procédons à la fermeture des flux et du socket serveur
        serveurSocket.close();
    }
}

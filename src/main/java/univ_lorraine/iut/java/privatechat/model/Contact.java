package univ_lorraine.iut.java.privatechat.model;

public class Contact {
        private String pseudo;
        private String adresseIp;
        private String port;

        public Contact(String pseudo, String adresseIp, String port) {
            this.pseudo = pseudo;
            this.adresseIp = adresseIp;
            this.port = port;
        }

        public String getPseudo() {
            return pseudo;
        }

        public String getAdresseIp() {
            return adresseIp;
        }

        public String getPort() {
            return port;
        }

        @Override
        public String toString() {
            return pseudo + "( " + adresseIp +", "+ port +" )" ;
        }
    }


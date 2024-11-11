import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class GaraCavalli {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Inserire la lunghezza del percorso in metri: ");
        int percorsoTotale = scanner.nextInt(); //Legge la lunghezza del percorso
        scanner.nextLine(); //Pulisce il buffer

        System.out.print("Inserire il numero di cavalli in gara: ");
        int numeroCavalli = scanner.nextInt(); //Legge il numero di cavalli in gara
        scanner.nextLine(); //Pulisce il buffer

        List<Cavallo> cavalli = new ArrayList<>(); //Crea una lista per memorizzare i cavalli

        for (int i = 0; i < numeroCavalli; i++) { //Ciclo per ogni cavallo
            System.out.print("Inserire il nome del cavallo " + (i + 1) + ": ");
            String nome = scanner.nextLine();

            System.out.print("Inserire la velocità del cavallo (metri al secondo): ");
            int velocita = scanner.nextInt(); //Legge la velocità del cavallo
            scanner.nextLine(); //Pulisce il buffer

            cavalli.add(new Cavallo(nome, percorsoTotale, velocita)); //Aggiunge il cavallo alla lista
        }

        System.out.println("\nLa gara inizia!\n");

        for (Cavallo cavallo : cavalli) { //Avvia tutti i thread dei cavalli
            cavallo.start(); //Avvia il thread per ogni cavallo
        }


        for (Cavallo cavallo : cavalli) { //Attende che tutti i cavalli finiscano
            try {
                cavallo.join(); //Attende la fine di ciascun cavallo
            } catch (InterruptedException e) {
                e.printStackTrace(); //Gestisce eccezioni di interruzione
            }
        }

        List<Cavallo> classifica = Cavallo.getClassifica(); //Ottiene la classifica ordinata dei cavalli
        System.out.println("\nClassifica dei primi 3 cavalli:");
        for (int i = 0; i < Math.min(3, classifica.size()); i++) { //Mostra i primi 3 cavalli
            System.out.println((i + 1) + ". " + classifica.get(i).getNome()); //Stampa posizione e nome
        }

        System.out.print("Inserire il nome del file in cui salvare i risultati: ");
        String nomeFile = scanner.nextLine(); //Legge il nome del file per salvare i risultati

        try (FileWriter writer = new FileWriter(nomeFile, true)) { //Apre il file in modalità "append"
            writer.write("Risultati della gara:\n"); //Scrive l'intestazione nel file
            for (int i = 0; i < Math.min(3, classifica.size()); i++) { //Scrive i primi 3 classificati
                writer.write((i + 1) + ". " + classifica.get(i).getNome() + "\n"); //Scrive posizione e nome
            }
            writer.write("\n");
        } catch (IOException e) { //Gestione dell'eccezione in caso di errore di scrittura
            System.out.println("Riscontrato errore durante la scrittura nel file.");
            e.printStackTrace();
        }

        System.out.println("\nGara terminata! I risultati sono stati salvati nel file: " + nomeFile);
        scanner.close();
    }
}


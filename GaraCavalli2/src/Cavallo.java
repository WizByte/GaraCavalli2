import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

class Cavallo extends Thread {
    private String nome;
    private int percorsoTotale;
    private int velocita;
    private int metriPercorsi = 0;
    private boolean infortunato = false;
    private Random random = new Random();

    private static List<Cavallo> classifica = new CopyOnWriteArrayList<>(); //Classifica dei cavalli che tagliano il traguardo

    public Cavallo(String nome, int percorsoTotale, int velocita) {
        this.nome = nome;
        this.percorsoTotale = percorsoTotale;
        this.velocita = velocita;
    }

    @Override
    public void run() {
        while (metriPercorsi < percorsoTotale && !infortunato) { //Continua finché il cavallo non termina o si infortuna

            if (random.nextInt(100) < 5) { //5% di probabilità di infortunio ad ogni iterazione
                infortunato = true; //Imposta lo stato di infortunio a vero
                System.out.println(nome + " si è infortunato e non può continuare la gara.");
                return;
            }

            metriPercorsi += velocita; //Aggiunge i metri percorsi in base alla velocità

            if (metriPercorsi > percorsoTotale) { //Se i merti percorsi superano la distanza totale
                metriPercorsi = percorsoTotale; //Allora limita i metri percorsi al percorso totale
            }

            System.out.println(nome + " ha percorso " + metriPercorsi + " metri.");

            try {
                Thread.sleep(500); //Pausa di 5millesimi di secondo
            } catch (InterruptedException e) { //Gestione eccezione di interruzione
                e.printStackTrace();
            }
        }

        if (!infortunato) { //Se il cavallo non si è infortunato
            System.out.println(nome + " ha tagliato il traguardo!");
            classifica.add(this); //Aggiunge il cavallo alla classifica
        }
    }

    public String getNome() { //Metodo per ottenere il nome del cavallo
        return nome;
    }

    public static List<Cavallo> getClassifica() { //Metodo per ottenere la classifica finale
        classifica.sort(Comparator.comparingInt(Cavallo::getPercorsoTotale).reversed()); //Ordina i cavalli per percorso completato
        return classifica; //Ritorna la classifica ordinata
    }

    public int getPercorsoTotale() { //Metodo per ottenere il percorso totale
        return percorsoTotale;
    }
}

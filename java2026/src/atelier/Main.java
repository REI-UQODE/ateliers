package atelier;

import java.util.Random;

public class Main{
    // 10x20 blocs pour l'écran + 4 pour le tetramino qui pourrait se retrouver à l'extérieur
    private static Bloc[] blocs = new Bloc[124];
    private static byte blocsEnJeu = 0; // Nombre de blocs en jeu
    private static Tetramino tetramino; // Tetramino du jeu. Réutilisé pour éviter des réalocations de mémoire.
    private static int score = 0; // Score actuel
    private static final int SCORE_LIGNE = 100; // Score par ligne remplie

    public static Random random = new Random();

    private static long tempsDernièreMiseàJour = System.currentTimeMillis();
    private static long tempsDernierTomber = System.currentTimeMillis();

    private static Contrôles contrôles;

    public static void main(String[] args){
        initialisation();
        boucle();
        destruction();
    }

    private static void initialisation(){
        throw new UnsupportedOperationException();
    }

    private static void boucle(){
        throw new UnsupportedOperationException();
    }

    private static boolean estTetraminoAuSol(){
        throw new UnsupportedOperationException();
    }

    private static void réinitialiserTetramino(){
        throw new UnsupportedOperationException();
    }

    private static void effectuerContrôles(){
        throw new UnsupportedOperationException();
    }

    private static boolean estPartieTerminée(){
        throw new UnsupportedOperationException();
    }

    private static void destruction(){}
}
package solution;

import java.util.Random;

import solution.Contrôles.Action;
import solution.Tetramino.Config;

public class Main{
    // 10x20 blocs pour l'écran + 4 pour le tetramino qui pourrait se retrouver à l'extérieur
    private static Bloc[] blocs = new Bloc[124];
    private static byte blocsEnJeu = 0; // Nombre de blocs en jeu
    private static Config prochaineConfig = Config.CARRÉ; // Prochaine configuration du tetramino
    private static Tetramino tetramino; // Tetramino du jeu. Réutilisé pour éviter des réalocations de mémoire.
    private static int score = 0; // Score actuel
    private static final int SCORE_LIGNE = 100; // Score par ligne remplie

    private static Config[] enumValeurs = Config.values(); // Liste des valeurs de Config pour choisir aléatoirement parmis elles
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
        // Initialiser les blocs
        for (int i = 0; i < blocs.length; i++) {
            blocs[i] = new Bloc();
        }

        tetramino = new Tetramino();
        tetramino.réinitialiser(new Bloc[]{blocs[0],blocs[1],blocs[2],blocs[3]}, enumValeurs[random.nextInt(enumValeurs.length)]);
        blocsEnJeu = 4;

        // Contrôles
        contrôles = new Contrôles();

        Peintre.initialiser(blocs);
        Peintre.changerConfig(prochaineConfig);
    }
    private static void boucle(){
        while(true){
            // Mettre à jour l'écran toutes les 100ms.
            if(System.currentTimeMillis() - tempsDernièreMiseàJour >= 100){
                effectuerContrôles();

                if(estTetraminoAuSol()){
                    if(estPartieTerminée()){
                        break;
                    }
                    réinitialiserTetramino(); // Réutiliser le tetramino
                    trouverLignesPleines(); // Effacer les lignes pleines
                }else{
                    // Faire tomber le tetramino toutes les 500ms
                    if(System.currentTimeMillis() - tempsDernierTomber >= 500){
                        tetramino.déplacer((byte) 0, (byte) 1);
                        tempsDernierTomber = System.currentTimeMillis();
                    }
                }

                Peintre.dessinerBlocs();
                tempsDernièreMiseàJour = System.currentTimeMillis();
            }
        }
    }

    private static boolean estTetraminoAuSol(){
        boolean estAuSol = false;
        // Vérifier si le Tetramino a touché un bloc
        byte i = 0;
        extérieur:
        for (Bloc tetraBloc : tetramino.blocs) {
            i = 0;
            for (Bloc bloc : blocs) {
                // Ignorer les blocs non en jeu
                if (bloc.x == -1 && bloc.y == -1){
                    continue;
                }
                // Ignorer les blocs qui forment le tetramino
                if(bloc == tetramino.blocs[0] || bloc == tetramino.blocs[1] || bloc == tetramino.blocs[2] || bloc == tetramino.blocs[3]){
                    continue;
                }

                i += 1;
                if (tetraBloc.x == bloc.x && tetraBloc.y + 1 == bloc.y){
                    estAuSol = true;
                    break extérieur;
                }
                if(i >= blocsEnJeu-4 ){
                    break;
                }
            }
        }

        if (!estAuSol){
            // Vérifier si le Tetramino a touché le sol
            for (Bloc bloc : tetramino.blocs) {
                if (bloc.y == 19){
                    estAuSol = true;
                    break;
                }
            }
        }

        return estAuSol;
    }

    private static void réinitialiserTetramino(){
        // Trouver 4 blocs inutilisés
        byte i = 0;
        for (Bloc bloc : blocs) {
            if (bloc.x == -1 && bloc.y == -1){
                tetramino.blocs[i] = bloc; // Réutiliser la liste du tetramino
                i++;
            }
            if (i == 4){
                break;
            }
        }

        tetramino.réinitialiser(tetramino.blocs, prochaineConfig);
        blocsEnJeu += 4;

        // Changer la prochaine configuration du tetramino
        prochaineConfig = enumValeurs[random.nextInt(enumValeurs.length)];
        Peintre.changerConfig(prochaineConfig);
    }

    private static void trouverLignesPleines(){
        // La stratégie ici est de compter le nombre de blocs présents sur une ligne
        //  et si ce nombre atteint 10 (la longueur de la ligne), elle est pleine.

        // Compter les blocs
        byte[] remplissageLigne = new byte[20]; // Initialisés à 0
        for (Bloc bloc : blocs) {
            if(bloc.y < 0 || bloc.y >= 20){
                continue;
            }
            remplissageLigne[bloc.y] += 1;
        }

        // Effacer les lignes pleines
        for (int i = 0; i < remplissageLigne.length; i++) {
            if (remplissageLigne[i] < 10){
                continue;
            }

            score += SCORE_LIGNE; // Augmenter le score

            for (Bloc bloc : blocs) {
                if (bloc.y == i){
                    // Désactiver les blocs sur la ligne pleine
                    bloc.x = -1;
                    bloc.y = -1;
                }else if (bloc.y < i){
                    // Faire baisser tous les blocs au-dessus de la ligne
                    bloc.y++;
                }
            }
        }
    }

    private static void effectuerContrôles(){
        contrôles.requestFocus(); // Remettre la JFrame en focus

        if (contrôles.actions.get(Action.BOUGE_DROITE)){
            tetramino.déplacer((byte) 1, (byte) 0);
        }

        if (contrôles.actions.get(Action.BOUGE_GAUCHE)){
            tetramino.déplacer((byte) -1, (byte) 0);
        }

        if (contrôles.actions.get(Action.TOMBE)){
            tetramino.déplacer((byte) 0, (byte) 1);
            tempsDernierTomber = System.currentTimeMillis();
        }

        if (contrôles.actions.get(Action.TOURNE)){
            tetramino.tourner();
        }
    }

    private static boolean estPartieTerminée(){
        // Cette fonction n'est appelée que si un tetramino touche le sol.
        // Dans cet état, si un bloc se trouve au-dessus du haut de l'écran,
        //  la partie est terminée.
        for (Bloc bloc : tetramino.blocs) {
            if (bloc.y < 0){
                return true;
            }
        }
        return false;
    }

    private static void destruction(){}
}
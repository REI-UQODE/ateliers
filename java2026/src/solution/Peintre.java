package solution;

import java.util.HashMap;

import solution.Tetramino.Config;

public class Peintre {
    private static Bloc[] blocs; // Référence

    private static final char ESC = 0x1B;
    // Les positions du curseur dans le terminal commencent à 1
    private static final String ÉCRAN_BASE = 
"""
=== === === === ===  /==
 |  |=   |  |_/  |   \\=\\
 |  ===  |  | \\ ===  ==/
+--------------------+ +---------+
| . . . . . . . . . .| | . . . . |
| . . . . . . . . . .| | . . . . |
| . . . . . . . . . .| | . . . . |
| . . . . . . . . . .| | . . . . |
| . . . . . . . . . .| +---------+
| . . . . . . . . . .|
| . . . . . . . . . .| Score :
| . . . . . . . . . .| 0 pts
| . . . . . . . . . .|
| . . . . . . . . . .|
| . . . . . . . . . .|
| . . . . . . . . . .|
| . . . . . . . . . .|
| . . . . . . . . . .|
| . . . . . . . . . .|
| . . . . . . . . . .|
| . . . . . . . . . .|
| . . . . . . . . . .|
| . . . . . . . . . .|
| . . . . . . . . . .|
+--------------------+
""";
    private static final byte[] POS_JEU = {2,5};
    private static final byte[] POS_PROCHAIN = {27,8};
    private static final byte[] POS_SCORE = {24,12};

    private static Tetramino prochainTetramino;

    public static void initialiser(Bloc[] blocs){
        Peintre.blocs = blocs;
        prochainTetramino = new Tetramino();
        prochainTetramino.réinitialiser(new Bloc[]{new Bloc(), new Bloc(), new Bloc(), new Bloc()}, Config.CARRÉ);
        effacerÉcran();
        System.out.println(ÉCRAN_BASE);
    }

    public static void changerConfig(Config prochaineConfig){
        // Effacer le tetramino
        for (Bloc bloc : prochainTetramino.blocs) {
            déplacerCurseur((byte)(bloc.x*2 + POS_PROCHAIN[0] - 4*2), (byte)(bloc.y + POS_PROCHAIN[1]));
            System.out.print(" .");
        }
        // Modifier le tetramino
        prochainTetramino.réinitialiser(prochainTetramino.blocs, prochaineConfig);
        // Dessiner le tetramino
        for (Bloc bloc : prochainTetramino.blocs) {
            déplacerCurseur((byte)(bloc.x*2 + POS_PROCHAIN[0] - 4*2), (byte)(bloc.y + POS_PROCHAIN[1]));
            System.out.print(coul(prochainTetramino.couleur[0], prochainTetramino.couleur[1], prochainTetramino.couleur[2])+"▓▒"+ESC+"[0m");
        }
    }

    public static void changerScore(int score){
        déplacerCurseur(POS_SCORE[0], POS_SCORE[1]);
        System.out.print(score+" pts");
    }

    public static void dessinerBlocs(){
        // Effacer les aciennes positions
        for (Bloc bloc : blocs) {
            // Ignorer les blocs n'ayant pas bougé.
            if (bloc.x == bloc.x_prec && bloc.y == bloc.y_prec){
                continue;
            }

            if (bloc.x_prec >= 0 && bloc.x_prec < 10 && bloc.y_prec >= 0 && bloc.y_prec < 20){
                déplacerCurseur((byte)(bloc.x_prec*2 + POS_JEU[0]), (byte)(bloc.y_prec + POS_JEU[1]));
                System.out.print(" .");
            }
        }
        // Dessiner les nouvelles positions
        for (Bloc bloc : blocs) {
            // Ignorer les blocs n'ayant pas bougé
            if (bloc.x == bloc.x_prec && bloc.y == bloc.y_prec){
                continue;
            }

            bloc.x_prec = bloc.x;
            bloc.y_prec = bloc.y;
            if (bloc.x >= 0 && bloc.x < 10 && bloc.y >= 0 && bloc.y < 20){
                déplacerCurseur((byte)(bloc.x*2 + POS_JEU[0]), (byte)(bloc.y + POS_JEU[1]));
                System.out.print(coul(bloc.r,bloc.g,bloc.b)+"▓▒"+ESC+"[0m");
            }
        }
    }

    private static void effacerÉcran(){
        System.out.print(ESC+"[2J"+ESC+"[1;1H");
    }
    private static void déplacerCurseur(byte x, byte y){
        System.out.print(ESC+"["+y+";"+x+"H");
    }

    private static String coul(byte r, byte g, byte b){
        // Les `byte` sont signé [-128;127], pour les interpréter comme non-signés [0;255] : 
        // On initie une opération (ici `&`), ceci les convertis en `int` pour effectuer l'opération
        //  `Bbbbbbbb -> BBBBBBBB BBBBBBBB BBBBBBBB Bbbbbbbb`
        // Si `B == 1`, ceci les convertiras en nombre négatifs. Afin d'empêcher cela : `b & 0xFF` : 
        //  `  BBBBBBBB BBBBBBBB BBBBBBBB Bbbbbbbb`
        //  `& 00000000 00000000 00000000 11111111`
        //  `= 00000000 00000000 00000000 Bbbbbbbb`
        // Ce qui revient à insérer les bits du `byte` dans un `int`, l'interprétant ainsi en `u8`.
        return ESC+"[38;2;"+(r & 0xFF)+";"+(g & 0xFF)+";"+(b & 0xFF)+"m";
    }
}

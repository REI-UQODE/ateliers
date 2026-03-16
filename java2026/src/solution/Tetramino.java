package solution;

public class Tetramino {
    public enum Config{
        LIGNE,
        L_GAUCHE,
        L_DROITE,
        S_GAUCHE,
        S_DROITE,
        T,
        CARRÉ
    }

    public Bloc[] blocs = new Bloc[4]; // Référence
    public Config config = Config.LIGNE;
    public byte[] couleur = new byte[3]; // À interpréter comme non-signés.

    public Tetramino(){}
    public void réinitialiser(Bloc[] blocs, Config config){
        this.blocs = blocs;
        this.config = config;
        Main.random.nextBytes(this.couleur);

        for (Bloc bloc : blocs) {
            bloc.r = this.couleur[0];
            bloc.g = this.couleur[1];
            bloc.b = this.couleur[2];
        }

        switch (config) {
            case LIGNE:{
                this.blocs[0].x = 4; this.blocs[0].y = -3;
                this.blocs[1].x = 4; this.blocs[1].y = -2;
                this.blocs[2].x = 4; this.blocs[2].y = -1;
                this.blocs[3].x = 4; this.blocs[3].y =  0;
                break;
            }
            case L_GAUCHE:{
                this.blocs[0].x = 5; this.blocs[0].y =  0;
                this.blocs[1].x = 5; this.blocs[1].y = -1;
                this.blocs[2].x = 5; this.blocs[2].y = -2;
                this.blocs[3].x = 4; this.blocs[3].y = -2;
                break;
            }
            case L_DROITE:{
                this.blocs[0].x = 4; this.blocs[0].y =  0;
                this.blocs[1].x = 4; this.blocs[1].y = -1;
                this.blocs[2].x = 4; this.blocs[2].y = -2;
                this.blocs[3].x = 5; this.blocs[3].y = -2;
                break;
            }
            case S_GAUCHE:{
                this.blocs[0].x = 5; this.blocs[0].y =  0;
                this.blocs[1].x = 5; this.blocs[1].y = -1;
                this.blocs[2].x = 4; this.blocs[2].y = -1;
                this.blocs[3].x = 4; this.blocs[3].y = -2;
                break;
            }
            case S_DROITE:{
                this.blocs[0].x = 4; this.blocs[0].y =  0;
                this.blocs[1].x = 4; this.blocs[1].y = -1;
                this.blocs[2].x = 5; this.blocs[2].y = -1;
                this.blocs[3].x = 5; this.blocs[3].y = -2;
                break;
            }
            case T:{
                this.blocs[0].x = 3; this.blocs[0].y = -1;
                this.blocs[1].x = 4; this.blocs[1].y = -1;
                this.blocs[2].x = 5; this.blocs[2].y = -1;
                this.blocs[3].x = 4; this.blocs[3].y =  0;
                break;
            }
            case CARRÉ:{
                this.blocs[0].x = 4; this.blocs[0].y = -1;
                this.blocs[1].x = 5; this.blocs[1].y = -1;
                this.blocs[2].x = 4; this.blocs[2].y =  0;
                this.blocs[3].x = 5; this.blocs[3].y =  0;
                break;
            }
        }
    }

    public void déplacer(byte dx, byte dy){
        for (Bloc bloc : blocs) {
            // Les blocs apparaîssent au-dessus de l'écran, il n'est donc pas nécessaire de vérifier la borne supérieur.
            if (bloc.x+dx > 9 || bloc.x+dx < 0 || bloc.y+dy > 19){
                return;
            }
        }

        for (Bloc bloc : blocs) {
            bloc.x += dx;
            bloc.y += dy;
        }
    }

    public void tourner(){
        // Effectuer une rotation de 90° anti-horaire avec une multiplication matricielle
        //  autour du 2ème bloc du tetramino
        // | cos(90) sin(90)||x| = | 0 1||x| = | y|
        // |-sin(90) cos(90)||y| = |-1 0||y| = |-x|
        byte x_rel;
        byte y_rel;
        for (Bloc bloc : blocs) {
            if (bloc == blocs[1]){
                continue;
            }

            x_rel = (byte)(bloc.x - blocs[1].x);
            y_rel = (byte)(bloc.y - blocs[1].y);

            bloc.x = (byte)( y_rel + blocs[1].x);
            bloc.y = (byte)(-x_rel + blocs[1].y);
        }
    }
}

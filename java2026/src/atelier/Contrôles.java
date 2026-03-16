package atelier;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

// On utilises une JFrame Swing vide qui est en focus pour attraper les touches du clavier.
public class Contrôles extends JFrame implements KeyListener {
    public enum Action{
        BOUGE_DROITE,
        BOUGE_GAUCHE,
        TOMBE,
        TOURNE
    }

    public Contrôles(){
        this.addKeyListener(this);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.requestFocus();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        throw new UnsupportedOperationException();
    }

    // Inutilisé
    @Override
    public void keyTyped(KeyEvent e) {}
    
}

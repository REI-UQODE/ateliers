package solution;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

// On utilises une JFrame Swing vide qui est en focus pour attraper les touches du clavier.
public class Contrôles extends JFrame implements KeyListener {
    public enum Action{
        BOUGE_DROITE,
        BOUGE_GAUCHE,
        TOMBE,
        TOURNE
    }

    public HashMap<Action,Boolean> actions = new HashMap<>(Map.of(
        Action.BOUGE_DROITE, false,
        Action.BOUGE_GAUCHE, false,
        Action.TOMBE, false,
        Action.TOURNE, false
    ));

    public Contrôles(){
        this.addKeyListener(this);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.requestFocus();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                this.actions.put(Action.BOUGE_GAUCHE, true);
                break;
            case KeyEvent.VK_RIGHT:
                this.actions.put(Action.BOUGE_DROITE, true);
                break;
            case KeyEvent.VK_UP:
                this.actions.put(Action.TOURNE, true);
                break;
            case KeyEvent.VK_DOWN:
                this.actions.put(Action.TOMBE, true);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                this.actions.put(Action.BOUGE_GAUCHE, false);
                break;
            case KeyEvent.VK_RIGHT:
                this.actions.put(Action.BOUGE_DROITE, false);
                break;
            case KeyEvent.VK_UP:
                this.actions.put(Action.TOURNE, false);
                break;
            case KeyEvent.VK_DOWN:
                this.actions.put(Action.TOMBE, false);
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }
    
}

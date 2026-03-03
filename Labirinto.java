package gamej;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

// La classe principale che estende JFrame per creare la finestra
public class Labirinto extends JFrame {

    // Grandezza di ogni blocco del labirinto in pixel
    private static final int DIMENSIONE_BLOCCO = 40;
    
    // Matrice dei dati del labirinto:
    // 1 = Muro (Nero)
    // 0 = Strada (Bianco)
    // 9 = Uscita (Oro)
    private int[][] mappa = {
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1},
        {1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1},
        {1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1},
        {1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1},
        {1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1},
        {1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 9, 1},
    };

    // Posizione del giocatore (Riga, Colonna)
    private int playerY = 1;
    private int playerX = 1;

    // Pannello interno dove disegneremo la grafica
    private class PannelloGioco extends JPanel {
        
        public PannelloGioco() {
            int altezzaMap = mappa.length * DIMENSIONE_BLOCCO;
            int larghezzaMap = mappa[0].length * DIMENSIONE_BLOCCO;
            setPreferredSize(new Dimension(larghezzaMap, altezzaMap));
            setBackground(Color.GRAY);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g); 

            for (int y = 0; y < mappa.length; y++) {
                for (int x = 0; x < mappa[y].length; x++) {
                    
                    switch (mappa[y][x]) {
                        case 1: g.setColor(Color.BLACK); break;    
                        case 0: g.setColor(Color.WHITE); break;    
                        case 9: g.setColor(new Color(255, 215, 0)); break; 
                    }
                    
                    g.fillRect(x * DIMENSIONE_BLOCCO, y * DIMENSIONE_BLOCCO, DIMENSIONE_BLOCCO, DIMENSIONE_BLOCCO);
                    g.setColor(Color.LIGHT_GRAY);
                    g.drawRect(x * DIMENSIONE_BLOCCO, y * DIMENSIONE_BLOCCO, DIMENSIONE_BLOCCO, DIMENSIONE_BLOCCO);
                }
            }

            // Giocatore (Cerchio Rosso)
            g.setColor(Color.RED);
            g.fillOval(playerX * DIMENSIONE_BLOCCO + 5, playerY * DIMENSIONE_BLOCCO + 5, DIMENSIONE_BLOCCO - 10, DIMENSIONE_BLOCCO - 10);
        }
    }

    public Labirinto() {
        setTitle("Labirinto");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        PannelloGioco pannello = new PannelloGioco();
        add(pannello);
        pack();
        setLocationRelativeTo(null);

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                int nuovoY = playerY;
                int nuovoX = playerX;

                if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) nuovoY--;
                if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) nuovoY++;
                if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) nuovoX--;
                if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) nuovoX++;

                // Controllo collisioni
                if (mappa[nuovoY][nuovoX] != 1) {
                    playerY = nuovoY;
                    playerX = nuovoX;
                }

                repaint();

                // Controllo Uscita
                if (mappa[playerY][playerX] == 9) {
                    System.out.println("VITTORIA! Sei uscito dal labirinto!");
                    // Chiusura pulita del gioco
                    System.exit(0);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> new Labirinto());
    }
}
package kevinduarte;

import kevinduarte.vista.MainFrame;
import javax.swing.SwingUtilities;

public class KevinDuarte {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
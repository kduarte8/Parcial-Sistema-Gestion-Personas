package kevinduarte.vista;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Sistema de Gestión de Personas - Kevin Steven Duarte Vera");
        setSize(950, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(25, 42, 86));
        header.setPreferredSize(new Dimension(0, 70));
        JLabel titulo = new JLabel("  Sistema de Gestión de Personas", SwingConstants.LEFT);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(Color.WHITE);
        JLabel subtitulo = new JLabel("Kevin Steven Duarte Vera  ", SwingConstants.RIGHT);
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitulo.setForeground(new Color(180, 200, 255));
        header.add(titulo, BorderLayout.WEST);
        header.add(subtitulo, BorderLayout.EAST);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabs.setBackground(new Color(240, 244, 255));
        tabs.addTab("  Gestión Personas  ", new PersonaPanel());
        tabs.addTab("  Reporte VIEW  ", new ReportePanel());

        add(header, BorderLayout.NORTH);
        add(tabs, BorderLayout.CENTER);
    }
}
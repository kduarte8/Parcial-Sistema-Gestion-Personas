package kevinduarte.vista;

import kevinduarte.dao.PersonaDAO;
import kevinduarte.modelo.Persona;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class ReportePanel extends JPanel {

    private static final Color AZUL_OSCURO = new Color(25, 42, 86);
    private static final Color VERDE = new Color(39, 174, 96);

    private JTable tabla;
    private DefaultTableModel modelo;
    private JLabel lblTotal;
    private PersonaDAO dao = new PersonaDAO();

    private void cargarReporte() {
        modelo.setRowCount(0);
        List<Persona> lista = dao.listarReporte();
        for (Persona p : lista) {
            modelo.addRow(new Object[]{
                p.getId(), p.getNombre(), p.getApellidos(),
                p.getEdad(), p.getCedula(), ""
            });
        }
        lblTotal.setText("Total registros: " + lista.size());
    }

    public ReportePanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 247, 252));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(AZUL_OSCURO);
        headerPanel.setPreferredSize(new Dimension(0, 60));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel titulo = new JLabel("Reporte General de Personas");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titulo.setForeground(Color.WHITE);

        lblTotal = new JLabel("Total registros: 0");
        lblTotal.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblTotal.setForeground(new Color(180, 200, 255));

        JButton btnRefrescar = new JButton("Actualizar Reporte");
        btnRefrescar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnRefrescar.setBackground(VERDE);
        btnRefrescar.setForeground(Color.WHITE);
        btnRefrescar.setBorderPainted(false);
        btnRefrescar.setFocusPainted(false);
        btnRefrescar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRefrescar.setPreferredSize(new Dimension(160, 35));
        
        JButton btnExportar = new JButton("Exportar CSV");
        btnExportar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnExportar.setBackground(new Color(41, 128, 185));
        btnExportar.setForeground(Color.WHITE);
        btnExportar.setBorderPainted(false);
        btnExportar.setFocusPainted(false);
        btnExportar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnExportar.setPreferredSize(new Dimension(130, 35));

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        rightPanel.setBackground(AZUL_OSCURO);
        rightPanel.add(lblTotal);
        rightPanel.add(btnExportar);
        rightPanel.add(btnRefrescar);

        headerPanel.add(titulo, BorderLayout.WEST);
        headerPanel.add(rightPanel, BorderLayout.EAST);

        JPanel infoBadge = new JPanel(new FlowLayout(FlowLayout.LEFT));
        infoBadge.setBackground(new Color(245, 247, 252));
        JLabel badge = new JLabel("  Datos consultados desde VIEW: VW_REPORTE_PERSONAS  ");
        badge.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        badge.setForeground(new Color(100, 120, 180));
        badge.setBackground(new Color(225, 232, 255));
        badge.setOpaque(true);
        badge.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        infoBadge.add(badge);

        modelo = new DefaultTableModel(
            new String[]{"ID", "Nombre", "Apellidos", "Edad", "Cedula", "Fecha"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(modelo);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tabla.setRowHeight(30);
        tabla.setSelectionBackground(new Color(210, 225, 255));
        tabla.setGridColor(new Color(220, 225, 240));

        JTableHeader header = tabla.getTableHeader();
        header.setBackground(AZUL_OSCURO);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setPreferredSize(new Dimension(0, 38));

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(200, 210, 235)));

        JPanel topArea = new JPanel(new BorderLayout());
        topArea.setBackground(new Color(245, 247, 252));
        topArea.add(headerPanel, BorderLayout.NORTH);
        topArea.add(infoBadge, BorderLayout.SOUTH);

        add(topArea, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        
        btnExportar.addActionListener(e -> {
    try {
        JFileChooser fc = new JFileChooser();
        fc.setSelectedFile(new java.io.File("reporte_personas.csv"));
        if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            java.io.FileWriter fw = new java.io.FileWriter(fc.getSelectedFile());
            fw.write("ID,Nombre,Apellidos,Edad,Cedula\n");
            for (int i = 0; i < modelo.getRowCount(); i++) {
                fw.write(modelo.getValueAt(i,0) + "," +
                         modelo.getValueAt(i,1) + "," +
                         modelo.getValueAt(i,2) + "," +
                         modelo.getValueAt(i,3) + "," +
                         modelo.getValueAt(i,4) + "\n");
            }
            fw.close();
            JOptionPane.showMessageDialog(this, "CSV exportado correctamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error al exportar: " + ex.getMessage());
    }
});

        btnRefrescar.addActionListener(e -> cargarReporte());
        cargarReporte();
    }
}
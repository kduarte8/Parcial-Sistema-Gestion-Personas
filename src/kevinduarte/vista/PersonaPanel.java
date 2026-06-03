package kevinduarte.vista;

import kevinduarte.dao.PersonaDAO;
import kevinduarte.modelo.Persona;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class PersonaPanel extends JPanel {

    private static final Color AZUL_OSCURO = new Color(25, 42, 86);
    private static final Color AZUL_MEDIO = new Color(52, 100, 200);
    private static final Color VERDE = new Color(39, 174, 96);
    private static final Color ROJO = new Color(192, 57, 43);
    private static final Color GRIS_FONDO = new Color(245, 247, 252);

    private JTextField txtNombre, txtApellidos, txtEdad, txtCedula, txtFecha, txtId;
    private JTable tabla;
    private DefaultTableModel modelo;
    private PersonaDAO dao = new PersonaDAO();

    private JTextField crearCampo() {
        JTextField tf = new JTextField();
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tf.setPreferredSize(new Dimension(250, 30));
        tf.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(190, 200, 230)),
            BorderFactory.createEmptyBorder(3, 8, 3, 8)
        ));
        return tf;
    }

    private JButton crearBoton(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setPreferredSize(new Dimension(120, 35));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void cargarTabla() {
        modelo.setRowCount(0);
        List<Persona> lista = dao.listar();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (Persona p : lista) {
            modelo.addRow(new Object[]{
                p.getId(), p.getNombre(), p.getApellidos(),
                p.getEdad(), p.getCedula(),
                p.getFecha() != null ? sdf.format(p.getFecha()) : ""
            });
        }
    }

    private void limpiar() {
        txtId.setText(""); txtNombre.setText(""); txtApellidos.setText("");
        txtEdad.setText(""); txtCedula.setText(""); txtFecha.setText("dd/MM/yyyy");
        tabla.clearSelection();
    }

    private void mostrarExito(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Exito", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public PersonaPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(GRIS_FONDO);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBackground(Color.WHITE);
        panelForm.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 210, 235), 1),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtId = crearCampo(); txtId.setEditable(false);
        txtId.setBackground(new Color(235, 235, 235));
        txtNombre = crearCampo();
        txtApellidos = crearCampo();
        txtEdad = crearCampo();
        txtCedula = crearCampo();
        txtFecha = crearCampo(); txtFecha.setText("dd/MM/yyyy");

        String[] labels = {"ID (auto):", "Nombre:", "Apellidos:", "Edad:", "Cedula:", "Fecha (dd/MM/yyyy):"};
        JTextField[] campos = {txtId, txtNombre, txtApellidos, txtEdad, txtCedula, txtFecha};

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0; gbc.gridy = i; gbc.weightx = 0.2;
            JLabel lbl = new JLabel(labels[i]);
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
            lbl.setForeground(AZUL_OSCURO);
            panelForm.add(lbl, gbc);
            gbc.gridx = 1; gbc.weightx = 0.8;
            panelForm.add(campos[i], gbc);
        }

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBotones.setBackground(Color.WHITE);

        JButton btnGuardar = crearBoton("Guardar", VERDE);
        JButton btnActualizar = crearBoton("Actualizar", AZUL_MEDIO);
        JButton btnEliminar = crearBoton("Eliminar", ROJO);
        JButton btnLimpiar = crearBoton("Limpiar", new Color(120, 120, 120));

        panelBotones.add(btnGuardar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(GRIS_FONDO);
        topPanel.add(panelForm, BorderLayout.CENTER);
        topPanel.add(panelBotones, BorderLayout.SOUTH);

        modelo = new DefaultTableModel(new String[]{"ID","Nombre","Apellidos","Edad","Cedula","Fecha"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(modelo);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tabla.setRowHeight(28);
        tabla.setSelectionBackground(new Color(210, 225, 255));
        tabla.setGridColor(new Color(220, 225, 240));
        tabla.setShowVerticalLines(true);

        JTableHeader header = tabla.getTableHeader();
        header.setBackground(AZUL_OSCURO);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));
        header.setPreferredSize(new Dimension(0, 35));

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(200, 210, 235)));

        add(topPanel, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        cargarTabla();

        btnGuardar.addActionListener(e -> {
            try {
                Persona p = new Persona();
                p.setNombre(txtNombre.getText().trim());
                p.setApellidos(txtApellidos.getText().trim());
                p.setEdad(Integer.parseInt(txtEdad.getText().trim()));
                p.setCedula(txtCedula.getText().trim());
                p.setFecha(new SimpleDateFormat("dd/MM/yyyy").parse(txtFecha.getText().trim()));
                if (dao.insertar(p)) {
                    mostrarExito("Persona guardada correctamente.");
                    cargarTabla(); limpiar();
                }
            } catch (Exception ex) {
                mostrarError("Error: " + ex.getMessage());
            }
        });

        btnActualizar.addActionListener(e -> {
            if (txtId.getText().isEmpty()) { mostrarError("Seleccione un registro."); return; }
            try {
                Persona p = new Persona();
                p.setId(Integer.parseInt(txtId.getText()));
                p.setNombre(txtNombre.getText().trim());
                p.setApellidos(txtApellidos.getText().trim());
                p.setEdad(Integer.parseInt(txtEdad.getText().trim()));
                p.setCedula(txtCedula.getText().trim());
                p.setFecha(new SimpleDateFormat("dd/MM/yyyy").parse(txtFecha.getText().trim()));
                if (dao.actualizar(p)) {
                    mostrarExito("Persona actualizada correctamente.");
                    cargarTabla(); limpiar();
                }
            } catch (Exception ex) {
                mostrarError("Error: " + ex.getMessage());
            }
        });

        btnEliminar.addActionListener(e -> {
            if (txtId.getText().isEmpty()) { mostrarError("Seleccione un registro."); return; }
            int confirm = JOptionPane.showConfirmDialog(this,
                "Esta seguro de eliminar este registro?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (dao.eliminar(Integer.parseInt(txtId.getText()))) {
                    mostrarExito("Persona eliminada correctamente.");
                    cargarTabla(); limpiar();
                }
            }
        });

        btnLimpiar.addActionListener(e -> limpiar());

        tabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int fila = tabla.getSelectedRow();
                if (fila >= 0) {
                    txtId.setText(modelo.getValueAt(fila, 0).toString());
                    txtNombre.setText(modelo.getValueAt(fila, 1).toString());
                    txtApellidos.setText(modelo.getValueAt(fila, 2).toString());
                    txtEdad.setText(modelo.getValueAt(fila, 3).toString());
                    txtCedula.setText(modelo.getValueAt(fila, 4).toString());
                    txtFecha.setText(modelo.getValueAt(fila, 5).toString());
                }
            }
        });
    }
}
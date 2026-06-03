package kevinduarte.dao;

import kevinduarte.modelo.Persona;
import kevinduarte.util.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonaDAO {

    public boolean insertar(Persona p) {
        String sql = "INSERT INTO PERSONAS (ID, NOMBRE, APELLIDOS, EDAD, CEDULA, FECHA) VALUES (SEQ_PERSONAS.NEXTVAL, ?, ?, ?, ?, ?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getApellidos());
            ps.setInt(3, p.getEdad());
            ps.setString(4, p.getCedula());
            ps.setDate(5, new java.sql.Date(p.getFecha().getTime()));
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error insertar: " + e.getMessage());
            return false;
        }
    }

    public List<Persona> listar() {
        List<Persona> lista = new ArrayList<>();
        String sql = "SELECT * FROM PERSONAS";
        try (Connection con = Conexion.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Persona p = new Persona();
                p.setId(rs.getInt("ID"));
                p.setNombre(rs.getString("NOMBRE"));
                p.setApellidos(rs.getString("APELLIDOS"));
                p.setEdad(rs.getInt("EDAD"));
                p.setCedula(rs.getString("CEDULA"));
                p.setFecha(rs.getDate("FECHA"));
                lista.add(p);
            }
        } catch (Exception e) {
            System.out.println("Error listar: " + e.getMessage());
        }
        return lista;
    }

    public boolean actualizar(Persona p) {
        String sql = "UPDATE PERSONAS SET NOMBRE=?, APELLIDOS=?, EDAD=?, CEDULA=?, FECHA=? WHERE ID=?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getApellidos());
            ps.setInt(3, p.getEdad());
            ps.setString(4, p.getCedula());
            ps.setDate(5, new java.sql.Date(p.getFecha().getTime()));
            ps.setInt(6, p.getId());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error actualizar: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM PERSONAS WHERE ID=?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error eliminar: " + e.getMessage());
            return false;
        }
    }

    public List<Persona> listarReporte() {
        List<Persona> lista = new ArrayList<>();
        String sql = "SELECT * FROM VW_REPORTE_PERSONAS";
        try (Connection con = Conexion.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Persona p = new Persona();
                p.setId(rs.getInt("ID"));
                p.setNombre(rs.getString("NOMBRE"));
                p.setApellidos(rs.getString("APELLIDOS"));
                p.setEdad(rs.getInt("EDAD"));
                p.setCedula(rs.getString("CEDULA"));
                lista.add(p);
            }
        } catch (Exception e) {
            System.out.println("Error reporte: " + e.getMessage());
        }
        return lista;
    }
}
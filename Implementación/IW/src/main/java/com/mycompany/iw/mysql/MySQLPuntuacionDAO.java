/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.iw.mysql;
        

import com.mycompany.iw.Puntuacion;
import com.mycompany.iw.Valoracion;
import com.mycompany.iw.daos.DAOException;
import com.mycompany.iw.daos.PuntuacionDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author LuisAneri
 */


public class MySQLPuntuacionDAO implements PuntuacionDAO{

    
    final String INSERT = "INSERT INTO puntuaciones(puntuacion, comentario, idPista, idJugador) VALUES (?,?,?,?)"; 
    final String UPDATE = "UPDATE puntuaciones SET puntuacion = ?, comentario = ?, idPista = ?, idJugador = ? WHERE idPuntuacion = ?";
    final String DELETE = "DELETE FROM puntuaciones WHERE idPuntuacion = ?";
    final String GETALL = "SELECT idPuntuacion, puntuacion, comentario, idPista, idJugador FROM puntuaciones";
    final String GETONE = "SELECT idPuntuacion, puntuacion, comentario, idPista, idJugador FROM puntuaciones WHERE idPuntuacion = ?";
    final String GETALLPISTA = "SELECT idPuntuacion, puntuacion, comentario, idPista, idJugador FROM puntuaciones WHERE idPista = ? ";
    
    private Connection conn;
    
    public MySQLPuntuacionDAO(Connection conn) {
        
        this.conn = conn;
        
    }
    
    @Override
    public void insertar(Puntuacion j) throws DAOException{

        PreparedStatement stat = null;
        try{
            
            stat = conn.prepareStatement(INSERT);
           
            
            stat = conn.prepareStatement(INSERT);
            stat.setLong(1, j.getPuntuacion());
            stat.setString(2, j.getComentario());
            stat.setLong(3, j.getPista());
            stat.setLong(4, j.getIdJugador());
            
            if(stat.executeUpdate() == 0){
                throw new DAOException("Puede que no se haya guardado.");
            }
            
        } catch(SQLException ex){
            throw new DAOException("Error en SQL", ex);
        } finally{
            if (stat !=  null){
                
                try{
                    stat.close();
                }catch(SQLException ex){
                    throw new DAOException("Error en SQL", ex);
                }
            }
        }
        
    }

    @Override
    public void modificar(Puntuacion j) throws DAOException{

        PreparedStatement stat = null;
        
        try{
            
            stat = conn.prepareStatement(UPDATE);
            
           
            stat.setLong(1, j.getPuntuacion());
            stat.setString(2, j.getComentario());
            stat.setLong(3, j.getPista());
            stat.setLong(4, j.getIdJugador());
            stat.setLong(5, j.getId());
            
            if(stat.executeUpdate() == 0){
                throw new DAOException("Puede que no se haya guardado.");
            }
            
        } catch(SQLException ex){
            throw new DAOException("Error en SQL", ex);
        } finally{
            if (stat !=  null){
                
                try{
                    stat.close();
                }catch(SQLException ex){
                    throw new DAOException("Error en SQL", ex);
                }
            }
        }

    }

    @Override
    public void eliminar(Puntuacion j) throws DAOException{

        PreparedStatement stat = null;
        try{
            
            stat = conn.prepareStatement(DELETE);
            stat.setLong(1, j.getId());

            if(stat.executeUpdate() == 0){
                throw new DAOException("Puede que no se haya guardado.");
            }
            
        } catch(SQLException ex){
            throw new DAOException("Error en SQL", ex);
        } finally{
            if (stat !=  null){
                
                try{
                    stat.close();
                }catch(SQLException ex){
                    throw new DAOException("Error en SQL", ex);
                }
            }
        }

    }

    private Puntuacion convertir(ResultSet rs) throws SQLException{
        
        
        Long idJugador = rs.getLong("idJugador");
        Long pista = rs.getLong("idPista");
        int puntuacion = rs.getInt("puntuacion");
        String comentario = rs.getString("comentario");
        
        Puntuacion j = new Puntuacion( puntuacion,  comentario,  pista,  idJugador);
        j.setId(rs.getLong("idPuntuacion"));
        
        return j;
        
    }
    
    
    @Override
    public List<Puntuacion> obtenerTodos() throws DAOException{

        PreparedStatement stat = null;
        ResultSet rs = null;
        List<Puntuacion> puntuaciones = new ArrayList<>();

        try{

            stat = conn.prepareStatement(GETALL);
            rs = stat.executeQuery();
            while(rs.next()){

                puntuaciones.add(convertir(rs));

            }

        }catch(SQLException ex){
             throw new DAOException("Error en SQL", ex);
        }finally{

            if(rs != null){

                try{
                    rs.close();
                }catch(SQLException ex){
                    new DAOException("Error en SQL, ex");
                }

            }
            if(stat != null){

                try{
                    stat.close();
                }catch(SQLException ex){
                    new DAOException("Error en SQL, ex");
                }

            }
        }

         return puntuaciones;
    

    }

    @Override
    public Puntuacion obtener(Long id) throws DAOException{

        PreparedStatement stat = null;
        ResultSet rs = null;
        Puntuacion j;

        try{

            stat = conn.prepareStatement(GETONE);
            stat.setLong(1, id);
            rs = stat.executeQuery();
            if(rs.next()){

                j = convertir(rs);

            }else{
                throw new DAOException("No se ha encontrado ese registro.");
            }

        }catch(SQLException ex){
             throw new DAOException("Error en SQL", ex);
        }finally{

            if(rs != null){

                try{
                    rs.close();
                }catch(SQLException ex){
                    new DAOException("Error en SQL, ex");
                }

            }
            if(stat != null){

                try{
                    stat.close();
                }catch(SQLException ex){
                    new DAOException("Error en SQL, ex");
                }

            }
        }

         return j;


    }
    
    public List<Puntuacion> obtenerPuntaucionesPista(Long id) throws DAOException{

        PreparedStatement stat = null;
        ResultSet rs = null;
        List<Puntuacion> puntuaciones = new ArrayList<>();

        try{

            stat = conn.prepareStatement(GETALLPISTA);
            stat.setLong(1, id);
            rs = stat.executeQuery();
            while(rs.next()){

                puntuaciones.add(convertir(rs));

            }

        }catch(SQLException ex){
             throw new DAOException("Error en SQL", ex);
        }finally{

            if(rs != null){

                try{
                    rs.close();
                }catch(SQLException ex){
                    new DAOException("Error en SQL, ex");
                }

            }
            if(stat != null){

                try{
                    stat.close();
                }catch(SQLException ex){
                    new DAOException("Error en SQL, ex");
                }

            }
        }

         return puntuaciones;
    

    }
    
     String GETPUNTUACIONES = "SELECT idPuntuacion, idPista, idJugador, puntuacion, comentario FROM puntuaciones WHERE idPista = ?";

    public List<Puntuacion> getPuntuacionesPista(Long id) throws DAOException{

        PreparedStatement stat = null;
        ResultSet rs = null;
        List<Puntuacion> puntuaciones = new ArrayList<>();

        try{

            stat = conn.prepareStatement(GETPUNTUACIONES);
            stat.setLong(1, id);
            rs = stat.executeQuery();
            while(rs.next()){

                puntuaciones.add(convertir(rs));

            }

        }catch(SQLException ex){
             throw new DAOException("Error en SQL", ex);
        }finally{

            if(rs != null){

                try{
                    rs.close();
                }catch(SQLException ex){
                    new DAOException("Error en SQL, ex");
                }

            }
            if(stat != null){

                try{
                    stat.close();
                }catch(SQLException ex){
                    new DAOException("Error en SQL, ex");
                }

            }
        }

         return puntuaciones;
    
    }
    
    public float calcularPuntuacion(List<Puntuacion> p){
        
        float puntuacionMedia = 0;
        
        for (Puntuacion puntuacion : p)
        {
            puntuacionMedia = puntuacionMedia + puntuacion.getPuntuacion();
        }
        
        puntuacionMedia = puntuacionMedia /p.size();
        
        return puntuacionMedia;
    }
    
    
}

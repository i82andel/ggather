/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.iw.mysql;

import com.mycompany.iw.Jugador;
import com.mycompany.iw.daos.DAOException;
import com.mycompany.iw.daos.JugadorDao;
import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author LuisAneri
 */

public class MySQLJugadorDAO implements JugadorDao{
    
    
    /*
    *   ------------------------------------
    *   SENTENCIAS PARA LAS TABLAS DE JUGADOR
    *   ------------------------------------_
    */
    
    final String INSERT = "INSERT INTO jugadores( usuario, nombre, email, apellidos, fechaNacimiento, contraseņa,telefono, valoracionMedia) VALUES (?,?,?,?,?,?,?,?)"; 
    final String UPDATE = "UPDATE jugadores SET usuario = ? , nombre = ?, email = ?, apellidos = ?, fechaNacimiento = ?, contraseņa = ?,telefono = ? WHERE email = ?";
    final String DELETE = "DELETE FROM jugadores WHERE idJugador = ?";
    final String GETALL = "SELECT idJugador, usuario, nombre, email, apellidos, fechaNacimiento, contraseņa,telefono, valoracionMedia FROM jugadores";
    final String GETONE = "SELECT * FROM jugadores WHERE idJugador = ?";
    final String BUSCARPOREMAIL = "SELECT * FROM jugadores WHERE email = ?";
    
    
    private Connection conn;
    
    public MySQLJugadorDAO(Connection conn) {
        
        this.conn = conn;
        
    }
    

    /*
    *   ----------------------------------------------------------------
    *   FUNCIONES BASICAS DE DAO JUGADOR INSERTAR, BORRAR, OBTENER, ETC
    *   ----------------------------------------------------------------
    */
    
    
    @Override
    public void insertar(Jugador j) throws DAOException {
        
        PreparedStatement stat = null;
        
        
        try{
            
            stat = conn.prepareStatement(INSERT);   
            
            stat.setString(1, j.getUsuario());
            stat.setString(2, j.getNombre());
            stat.setString(3, j.getEmail());
            stat.setString(4, j.getApellidos());
            stat.setDate(5, new Date(j.getFechaNacimiento().getTime()));
            stat.setString(6, j.getContraseņa());
            stat.setFloat(7, j.getValoracionMedia()); //Esto abria que modificarlopor que al crear un usuario no posee valoracion
            stat.setLong(8, j.getTelefono());
            
            
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
            }if(stat != null){
               
               try{
                   stat.close();
               }catch(SQLException ex){
                   new DAOException("Error en SQL", ex);
               }
               
           }
        }
    }

    
    @Override
    public void modificar(Jugador j) throws DAOException{

         PreparedStatement stat = null;
        
        try{
            
            stat = conn.prepareStatement(UPDATE);
            stat.setString(1, j.getUsuario());
            stat.setString(2, j.getNombre());
            stat.setString(3, j.getEmail());
            stat.setString(4, j.getApellidos());
            stat.setDate(5, new Date(j.getFechaNacimiento().getTime()));
            stat.setString(6, j.getContraseņa());
            stat.setLong(7, j.getTelefono());
            stat.setString(8, j.getEmail());
            
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
    public void eliminar(Jugador j) throws DAOException{
        
        
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

    
    private Jugador convertir(ResultSet rs) throws SQLException{
        
        String usuario = rs.getString("usuario");
        String nombre = rs.getString("nombre");
        String apellidos = rs.getString("apellidos");
        String contraseņa = rs.getString("contraseņa");
        String email = rs.getString("email");
        Date fechaNacimiento = rs.getDate("fechaNacimiento");
        float valoracionMedia = rs.getFloat("valoracionMedia");
        int telefono = rs.getInt("telefono");
        
        Jugador j = new Jugador(usuario, nombre, apellidos, email, telefono, contraseņa, fechaNacimiento, valoracionMedia);
        j.setId(rs.getLong("idJugador"));
        
        return j;
        
    }
    
    @Override
    public List<Jugador> obtenerTodos() throws DAOException{

       PreparedStatement stat = null;
       ResultSet rs = null;
       List<Jugador> jugadores = new ArrayList<>();
       
       try{
           
           stat = conn.prepareStatement(GETALL);
           rs = stat.executeQuery();
           while(rs.next()){
               
               jugadores.add(convertir(rs));
               
           }
           
       }catch(SQLException ex){
            throw new DAOException("Error en SQL", ex);
       }finally{
           
           if(rs != null){
               
               try{
                   rs.close();
               }catch(SQLException ex){
                   new DAOException("Error en SQL", ex);
               }
               
           }
           if(stat != null){
               
               try{
                   stat.close();
               }catch(SQLException ex){
                   new DAOException("Error en SQL", ex);
               }
               
           }
       }
       
        return jugadores;
    }


    @Override
    public Jugador obtener(Long id) throws DAOException{
        
       PreparedStatement stat = null;
       ResultSet rs = null;
       Jugador j;
       
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
                   new DAOException("Error en SQL", ex);
               }
               
           }
           if(stat != null){
               
               try{
                   stat.close();
               }catch(SQLException ex){
                   new DAOException("Error en SQL", ex);
               }
               
           }
       }
       
        return j;
    }
    
    
    
    /*
    *   ------------------------------------
    *   SENTENCIAS PARA LAS TABLAS DE AMIGOS
    *   ------------------------------------
    */

    
    final String GETREQUEST = "SELECT j.idJugador, j.usuario, j.nombre, j.email, j.apellidos, j.fechaNacimiento, j.contraseņa, j.telefono FROM  jugadores j, amigos a "
                               + "WHERE j.idJugador = a.idAmigo2 and a.idAmigo1 = ?";
    final String GETRPENDIENTES = "SELECT j.idJugador, j.usuario, j.nombre, j.email, j.apellidos, j.fechaNacimiento, j.contraseņa, j.telefono FROM jugadores j, amigos a"
                               + "WHERE j.idJugador = a.idAmigo1 and a.idAmigo2 = ?";
    final String GETUPLAAMIGO = "SELECT j.idJugador, j.usuario, j.nombre, j.email, j.apellidos, j.fechaNacimiento, j.contraseņa, j.telefono FROM jugaodres j, amigos a "
                               + "WHERE j.idJugador and = a.idAmigo2 a.idAmigo1 = ? and a.idAmigo2 = ?";
    final String INSERTAMIGO = "INSERT INTO amigos(idAmigo1, idAmigo2) VALUES(?,?)";
    final String DELETEAMIGO = "DELETE FROM amigos WHERE idAmigo1 = ? and idAmigo2 = ?";
    
    
    /*
    *   ----------------------------------------------------------
    *   Funciones que se encargan de las relaciones entre jugadores
    *   ----------------------------------------------------------
    */
    
    /* Funcion basica para obtener una lista con tus solicitudes*/
    public List<Jugador> obtenerSolicitudesAmistad(Long idJugador) throws DAOException{

       PreparedStatement stat = null;
       ResultSet rs = null;
       List<Jugador> jugadores = new ArrayList<>();
       
       try{
           
           stat = conn.prepareStatement(GETREQUEST);
           stat.setLong(1, idJugador);
           rs = stat.executeQuery();
           while(rs.next()){
               
               jugadores.add(convertir(rs));
               
           }
           
       }catch(SQLException ex){
            throw new DAOException("Error en SQL", ex);
       }finally{
           
           if(rs != null){
               
               try{
                   rs.close();
               }catch(SQLException ex){
                   new DAOException("Error en SQL", ex);
               }
               
           }
           if(stat != null){
               
               try{
                   stat.close();
               }catch(SQLException ex){
                   new DAOException("Error en SQL", ex);
               }
               
           }
       }
       
        return jugadores;
    }
    
    /* Funcion basica para obtener una lista con tus solicitudes pendientes*/
    public List<Jugador> obtenerSolicitudesPendientes(Long idJugador) throws DAOException{

       PreparedStatement stat = null;
       ResultSet rs = null;
       List<Jugador> jugadores = new ArrayList<>();
       
       try{
           
           stat = conn.prepareStatement(GETRPENDIENTES);
           stat.setLong(1, idJugador);
           rs = stat.executeQuery();
           while(rs.next()){
               
               jugadores.add(convertir(rs));
               
           }
           
       }catch(SQLException ex){
            throw new DAOException("Error en SQL", ex);
       }finally{
           
           if(rs != null){
               
               try{
                   rs.close();
               }catch(SQLException ex){
                   new DAOException("Error en SQL", ex);
               }
               
           }
           if(stat != null){
               
               try{
                   stat.close();
               }catch(SQLException ex){
                   new DAOException("Error en SQL", ex);
               }
               
           }
       }
       
        return jugadores;
    }
    
    /* Funcion basica para obtener una lista con tus amigos*/
    public List<Jugador> obtenerAmigos(Long idJugador) throws DAOException{

       PreparedStatement stat = null, stat2 = null;
       ResultSet rs = null, rs2 = null;
       List<Jugador> jugadores = new ArrayList<>();
       
       try{
           
            stat = conn.prepareStatement(GETREQUEST);
            
            stat.setLong(1, idJugador);
            rs = stat.executeQuery();
            while(rs.next()){

                stat2 = conn.prepareStatement(GETUPLAAMIGO);
                stat2.setLong(2, idJugador);
                stat2.setLong(1, convertir(rs).getId());
                rs2 = stat.executeQuery();
                
                if(rs2.next()){
                    jugadores.add(convertir(rs));
                }
                
            }
            
            
       }catch(SQLException ex){
            throw new DAOException("Error en SQL", ex);
       }finally{
           
           if(rs != null){
               
               try{
                   rs.close();
               }catch(SQLException ex){
                   new DAOException("Error en SQL", ex);
               }
               
           }
           if(stat != null){
               
               try{
                   stat.close();
               }catch(SQLException ex){
                   new DAOException("Error en SQL", ex);
               }
               
           }
       }
       
        return jugadores;
    }
    
    /* Funcion basica para insertar una relacion*/
    public void insertarAmigo(Jugador j, Long id) throws DAOException {
        
        PreparedStatement stat = null;
        
        
        try{
            
            stat = conn.prepareStatement(INSERTAMIGO);

            stat.setLong(1, j.getId());
            stat.setLong(2, id);
            
            
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
            }if(stat != null){
               
               try{
                   stat.close();
               }catch(SQLException ex){
                   new DAOException("Error en SQL", ex);
               }
               
           }
        }
    }
    
    /* Funcion basica para borrar una relacion*/
    public void eliminarAmigo(Jugador j, Long id) throws DAOException{
        
        
        PreparedStatement stat = null;
        
        try{
            
            stat = conn.prepareStatement(DELETEAMIGO);
            stat.setLong(1, j.getId());
            stat.setLong(2, id);

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
    
    
    
    /*
    *   ----------------------------------------------------------
    *   Funciones que se encargan de las relaciones entre jugadores
    *   ----------------------------------------------------------
    */
    
    final String GETONEBYUSER = "SELECT * FROM jugadores WHERE usuario like '%?%'";
    final String GETONEBYEMAIL = "SELECT * FROM jugadores WHERE email like '%?%'";
    
    public List<Jugador> obtenerUsuario(String usuario) throws DAOException{
        
       
       PreparedStatement stat = null;
       ResultSet rs = null;
       List<Jugador> jugadores = new ArrayList<>();
       
       try{
           
           stat = conn.prepareStatement(GETONEBYUSER);
           stat.setString(1, usuario);
           rs = stat.executeQuery();
           while(rs.next()){
               
               jugadores.add(convertir(rs));
               
           }
           
       }catch(SQLException ex){
            throw new DAOException("Error en SQL", ex);
       }finally{
           
           if(rs != null){
               
               try{
                   rs.close();
               }catch(SQLException ex){
                   new DAOException("Error en SQL", ex);
               }
               
           }
           if(stat != null){
               
               try{
                   stat.close();
               }catch(SQLException ex){
                   new DAOException("Error en SQL", ex);
               }
               
           }
       }
       
        return jugadores;
    }
    
    
    
    public List<Jugador> obtenerEmail(String usuario) throws DAOException{
        
       
       PreparedStatement stat = null;
       ResultSet rs = null;
       List<Jugador> jugadores = new ArrayList<>();
       
       try{
           
           stat = conn.prepareStatement(GETONEBYEMAIL);
           stat.setString(1, usuario);
           rs = stat.executeQuery();
           while(rs.next()){
               
               jugadores.add(convertir(rs));
               
           }
           
       }catch(SQLException ex){
            throw new DAOException("Error en SQL", ex);
       }finally{
           
           if(rs != null){
               
               try{
                   rs.close();
               }catch(SQLException ex){
                   new DAOException("Error en SQL", ex);
               }
               
           }
           if(stat != null){
               
               try{
                   stat.close();
               }catch(SQLException ex){
                   new DAOException("Error en SQL", ex);
               }
               
           }
       }
       
        return jugadores;
    }
     
    final String GETJUGADORESP = "SELECT j.idJugador, j.usuario, j.nombre, j.email, j.apellidos, j.fechaNacimiento, j.contraseņa, j.telefono FROM jugaodres j, partido_jugador pj "
                               + "WHERE pj.idJugador = p.idJugador and pj.idPartido = ?";
    
    
    public List<Jugador> obtenerJugadoresPartido(Long idPartido) throws DAOException{

       PreparedStatement stat = null;
       List<Jugador> jugadores = new ArrayList<>();
       ResultSet rs = null;
       
       try{
           
            stat = conn.prepareStatement(GETJUGADORESP);
            stat.setLong(1, idPartido);
            rs = stat.executeQuery();
            while(rs.next()){
                
                jugadores.add(convertir(rs));
               
            }
            
            
       }catch(SQLException ex){
            throw new DAOException("Error en SQL", ex);
       }finally{
           
           if(rs != null){
               
               try{
                   rs.close();
               }catch(SQLException ex){
                   new DAOException("Error en SQL", ex);
               }
               
           }
           if(stat != null){
               
               try{
                   stat.close();
               }catch(SQLException ex){
                   new DAOException("Error en SQL", ex);
               }
               
           }
       }
       
        return jugadores;
    }
    

    public Jugador buscarJugadorPorEmail(String email) throws DAOException{
        
        PreparedStatement stat = null;
        ResultSet rs = null;
        Jugador j;
        try{
            
            stat = conn.prepareStatement(BUSCARPOREMAIL);
            stat.setString(1, email);
            rs = stat.executeQuery();
            if(rs.next()){
                
                j = convertir(rs);
            }else{
            	
                j = null;
            }
        }catch(SQLException ex){
             throw new DAOException("Error en SQL", ex);
        }finally{
            
            if(rs != null){
                
                try{
                    rs.close();
                }catch(SQLException ex){
                    new DAOException("Error en SQL", ex);
                }
                
            }
            if(stat != null){
                
                try{
                    stat.close();
                }catch(SQLException ex){
                    new DAOException("Error en SQL", ex);
                }
                
            }
        }
        
         return j;
     }
    
    
    final String UPDATEFOTO = "UPDATE jugadores SET fotoPerfil = ?  WHERE idJugador = ?";
    final String GETFOTO = "SELECT fotoPerfil FROM jugadores WHERE idJugador = ?";
    
    
    public void modificarFotoPerfil(String foto, Long idj) throws DAOException {
        
        PreparedStatement stat = null;
        
        
        try{
            
            stat = conn.prepareStatement(UPDATEFOTO);   
            
            stat.setString(1, foto);
            stat.setLong(2, idj);
            
            
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
            }if(stat != null){
               
               try{
                   stat.close();
               }catch(SQLException ex){
                   new DAOException("Error en SQL", ex);
               }
               
           }
        }
    }
   
    public String obtenerFotoPerfil(Long idJ) throws DAOException {  
        PreparedStatement stat = null;
        ResultSet rs = null;
        String j;
        
        try{
            
            stat = conn.prepareStatement(GETFOTO);
            stat.setLong(1, idJ);
            rs = stat.executeQuery();
            if(rs.next()){
                
                j = rs.getString("fotoPerfil");
                
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
                    new DAOException("Error en SQL", ex);
                }
                
            }
            if(stat != null){
                
                try{
                    stat.close();
                }catch(SQLException ex){
                    new DAOException("Error en SQL", ex);
                }
                
            }
        }
        
         return j;
    }
    
    
    final String GETNIVELES = "SELECT idDeporte, nivel FROM niveles WHERE idJugador = ?";
    
    public Map<Long, Long> getNiveles(Long idJ)throws DAOException{
    	
    	Map<Long, Long> myMap = new HashMap<Long, Long>();
    	PreparedStatement stat = null;
        ResultSet rs = null;
        String j;
        
        try{
            
            stat = conn.prepareStatement(GETFOTO);
            stat.setLong(1, idJ);
            rs = stat.executeQuery();
            while(rs.next()){
                
            	myMap.put(rs.getLong("idDeporte"), rs.getLong("nivel"));
                
            }
            
        }catch(SQLException ex){
             throw new DAOException("Error en SQL", ex);
        }finally{
            
            if(rs != null){
                
                try{
                    rs.close();
                }catch(SQLException ex){
                    new DAOException("Error en SQL", ex);
                }
                
            }
            if(stat != null){
                
                try{
                    stat.close();
                }catch(SQLException ex){
                    new DAOException("Error en SQL", ex);
                }
                
            }
        }
        
         return myMap;
    	
    }


}
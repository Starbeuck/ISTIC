package fr.acceis.forum.dao;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class DAO<T> {
  protected Connection connect = null;
   
  public DAO(Connection conn){
    this.connect = conn;
  }
   
  /**
  * Méthode de création
  * @param obj
  * @return boolean 
 * @throws SQLException 
 * @throws IllegalAccessException 
 * @throws InstantiationException 
 * @throws Exception 
  */
  public abstract boolean create(T obj) throws SQLException, InstantiationException, IllegalAccessException, Exception;

  /**
  * Méthode pour effacer
  * @param obj
  * @return boolean 
  */
  public abstract boolean delete(T obj);

  /**
  * Méthode de mise à jour
  * @param obj
  * @return boolean
  */
  public abstract boolean update(T obj);

  /**
  * Méthode de recherche des informations
  * @param id
  * @return T
 * @throws IllegalAccessException 
 * @throws InstantiationException 
 * @throws SQLException 
  */
  public abstract T find(T obj) throws SQLException, InstantiationException, IllegalAccessException;
  
  public abstract int findbyID(T obj) throws SQLException, InstantiationException, IllegalAccessException;
  
  public abstract T findByName(String name) throws SQLException, InstantiationException, IllegalAccessException; 
}
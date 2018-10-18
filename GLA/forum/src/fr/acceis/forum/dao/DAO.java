package fr.acceis.forum.dao;

import java.sql.Connection;
import java.sql.SQLException;

// TODO: Auto-generated Javadoc
/**
 * The Class DAO.
 *
 * @author solenn
 * @param <T> the generic type
 */
public abstract class DAO<T> {
  
  /** The connect. */
  protected Connection connect = null;
   
  /**
   * Instantiates a new dao.
   *
   * @param conn the conn
   */
  public DAO(Connection conn){
    this.connect = conn;
  }
   
  /**
   * Méthode de création.
   *
   * @param obj the obj
   * @return boolean
   * @throws SQLException the SQL exception
   * @throws InstantiationException the instantiation exception
   * @throws IllegalAccessException the illegal access exception
   * @throws Exception the exception
   */
  public abstract boolean create(T obj) throws SQLException, InstantiationException, IllegalAccessException, Exception;

  /**
   * Méthode pour effacer.
   *
   * @param obj the obj
   * @return boolean
   * @throws SQLException the SQL exception
   */
  public abstract boolean delete(T obj) throws SQLException;

  /**
   * Méthode de mise à jour.
   *
   * @param obj the obj
   * @return boolean
   * @throws SQLException the SQL exception
   */
  public abstract boolean update(T obj) throws SQLException;

  /**
   * Méthode de recherche des informations.
   *
   * @param obj the obj
   * @return T
   * @throws SQLException the SQL exception
   * @throws InstantiationException the instantiation exception
   * @throws IllegalAccessException the illegal access exception
   */
  public abstract T find(T obj) throws SQLException, InstantiationException, IllegalAccessException;
  
  /**
   * Findby ID.
   *
   * @param obj the obj
   * @return the int
   * @throws SQLException the SQL exception
   * @throws InstantiationException the instantiation exception
   * @throws IllegalAccessException the illegal access exception
   */
  public abstract int findbyID(T obj) throws SQLException, InstantiationException, IllegalAccessException;
  
  /**
   * Find by name.
   *
   * @param name the name
   * @return the t
   * @throws SQLException the SQL exception
   * @throws InstantiationException the instantiation exception
   * @throws IllegalAccessException the illegal access exception
   */
  public abstract T findByName(String name) throws SQLException, InstantiationException, IllegalAccessException; 
}
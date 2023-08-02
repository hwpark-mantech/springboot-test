package kr.co.mantech.jungwon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
public class DBManager { 
	/**
	 * Logger for this class
	 */
		
	private static final Logger logger = LoggerFactory.getLogger(DBManager.class);

    public static Connection getConnection() throws Exception
    {
    	logger.debug("getConnection");
      return getConnection("datasource");
      
    }
    public static Connection getConnection(String dsName) throws Exception
    {
      Context initCtx = null;    
      Context envCtx = null; 
      Connection conn = null;
      DataSource ds = null;  
		if (logger.isDebugEnabled()) {
			logger.debug("getConnection(String) - {}", "========START GET CONNECTION ============="); //$NON-NLS-1$ //$NON-NLS-2$
		}
 
      try { initCtx = new InitialContext();
			if (logger.isDebugEnabled()) {
				logger.debug("getConnection(String) - {}", dsName); //$NON-NLS-1$
				 
			} 
			
		String was_type=System.getProperty("WAS_TYPE");
		logger.debug("getConnection(String) - WAS_TYPE{}", was_type);
		if (was_type != null && was_type.equals("JEUS"))
		{
			//�냼�뒪 �닔�젙 (�젣�슦�뒪�씪�븣)
			logger.debug("getConnection(String) - WAS_TYPE{}  JEUS", was_type);
			ds = (DataSource)initCtx.lookup(dsName);
		}
		else
		{
			logger.debug("getConnection(String) - WAS_TYPE{}  OTHERS", was_type);
			ds = (DataSource)initCtx.lookup("java:/comp/env/"+dsName);
		} 

		
        conn = ds.getConnection();
      } catch (Exception ex) {
			logger.error("getConnection(String)", ex); //$NON-NLS-1$

      } finally {
        try {
          if (envCtx != null) {
            envCtx.close();
          }
          if (initCtx != null)
            initCtx.close();
        }
        catch (NamingException ex) {
				logger.error("getConnection(String)", ex); //$NON-NLS-1$
        }
      }

		if (logger.isDebugEnabled()) {
			logger.debug("getConnection(String) - {}", "========END GET CONNECTION ============="); //$NON-NLS-1$ //$NON-NLS-2$
		}
      return conn;
    }

    public static void close(Connection con) throws Exception
    {
		if (logger.isDebugEnabled()) {
			logger.debug("close(Connection) - {}", "========START CLOSE CONNECTION ============="); //$NON-NLS-1$ //$NON-NLS-2$
		}
		if (logger.isDebugEnabled()) {
			logger.debug("close(Connection) - {}", "con" + con); //$NON-NLS-1$ //$NON-NLS-2$
		}
      if ( con != null ) try { con.close(); } catch(Exception e) {}
		if (logger.isDebugEnabled()) {
			logger.debug("close(Connection) - {}", "========END CLOSE CONNECTION ============="); //$NON-NLS-1$ //$NON-NLS-2$
		}

    }

}

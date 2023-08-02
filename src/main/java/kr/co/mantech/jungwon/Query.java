package kr.co.mantech.jungwon;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HandshakeCompletedListener;
import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class Query {
	
	private static final Logger logger = LoggerFactory.getLogger(Query.class);
	
	public  static final String queryRepository = "/" + "resources"+File.separator+"query";
	
	public  StringBuffer fileQueryString = new StringBuffer();
	public  StringBuffer queryString     = new StringBuffer();
	
	public  String       columnsName;	
	public  String       fileName   ;
	public  String       orgFileName;
	public  String       orgFileName2;
	  
	 
	ArrayList<String> arrayOfBindName = new ArrayList();
	
	@Autowired ServletContext servletContext; //servletConext �쓽議댁꽦 二쇱엯
	
	// query�뙆�씪�쓣 �떎�떆 �씫怨� 珥덇린�솕 �븳�떎.
	public Query(String queryName)
	{ 
		fileName   = queryRepository+File.separator+queryName+".sql";
		orgFileName= queryRepository+File.separator+queryName+".org.sql";
		
		logger.info("fileName={}", fileName);
		logger.info("orgFileName={}", orgFileName);
		
		
	}  
	public void initQuery(String fileName,String orgFileName)
	{
	
		File ifile = new File(servletContext.getRealPath("/")+orgFileName);
		File ofile = new File(servletContext.getRealPath("/")+fileName);
		if (logger.isDebugEnabled()) {
			logger.debug("init() - {}", ofile); //$NON-NLS-1$
		}
		try {
			FileOutputStream fo = new FileOutputStream(ofile);
			FileInputStream fi = new FileInputStream(ifile);
            
            int data = 0;
            while ((data = fi.read()) != -1) {
               fo.write(data);
            }
            fo.close();
            fi.close();
            
            this.readQuery();
			
		} catch (FileNotFoundException e) {
			logger.error("init()", e);
		} catch (IOException e) {
			logger.error("init()", e);
		}			
	}

	public synchronized void readQuery() // queryString �룞湲고솕
	{
		fileQueryString.setLength(0);
		queryString.setLength(0);
		InputStream is = servletContext.getResourceAsStream(fileName);
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		String s;
		try {
			while ((s=br.readLine())!=null)
			{
				fileQueryString.append(s);
				//fileQueryString.append(System.lineSeparator());
				fileQueryString.append(System.getProperty("line.separator")); 
				
			}
		 
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (logger.isDebugEnabled()) {
			logger.info("readQuery() - String fileQueryString={}", queryString); //$NON-NLS-1$
		}
		// �뙆�씪�쓣 �씪怨� �뙣�꽩�쑝濡� �뙆�씪誘명꽣瑜� 鍮쇱삤怨�, ? �쑝濡� 臾몄옄瑜� 援먯껜�븳�떎.
		this.parseQuery();
	}
	public synchronized void parseQuery() // queryString �룞湲고솕
	{
		//Pattern pattern = Pattern.compile("#[a-zA-Z_0-9]*#");
		String s_pattern=":[a-zA-Z_0-9]*";
		Pattern pattern = Pattern.compile(s_pattern);
		Matcher matcher = pattern.matcher(fileQueryString.toString());
		
		//arrayOfString 珥덇린�솕
		arrayOfBindName.clear();
				
		//諛붿씤�뱶 蹂��닔 �꽔湲�//
		while (matcher.find())
		{				
			arrayOfBindName.add(matcher.group());			
		}
		//諛붿씤�뱶 蹂��닔�뿉 ? �쑝濡� 援먯껜
		queryString.append(matcher.replaceAll("?"));

	}	
 
	public void setStringAsName(String name, String value, String type, PreparedStatement ps) throws SQLException
	{
		String [] arrayString= arrayOfBindName.toArray(new String[]{});
	
		logger.debug("setStringAsName(String, String, String, PreparedStatement) - String[] arrayString=" + arrayOfBindName); //$NON-NLS-1$					
		for(int i=0; i<arrayString.length;i++) 
		{
			
			
			
			logger.debug("setStringAsName(String, String, String, PreparedStatement) - arrayString[{}] = {} ,name = {} , value = {} , type = {}",
					arrayString[i],i,name,value,type);					

			//�뙣�꽩�뿉 留욌떎硫� 諛붿씤�뱶 蹂��닔 �뀑�똿�븿.
			//if (((String) arrayString[i]).equals("#"+name+"#"))
			if (((String) arrayString[i]).equals(":"+name))
			{				
				if (type.equals("String"))
				{
					logger.debug("setStringAsName(String, String, String, PreparedStatement) - setString: i = {} name= {}, value= {}, type= {}",i,name,value,type);				
					ps.setString(i+1, value);
				}
				else if (type.equals("int"))
				{
					logger.debug("setStringAsName(String, String, String, PreparedStatement) - setInt: i = {} name= {}, value= {}, type= {}",i,name,value,type);									
					ps.setInt(i+1,Integer.parseInt(value));
				}
				else if (type.equals("Date"))
				{
					logger.debug("setStringAsName(String, String, String, PreparedStatement) - setDate: i = {} name= {}, value= {}, type= {}",i,name,value,type);									
					ps.setDate(i+1, Date.valueOf(value));					
				}
				else if (type.equals("double"))
				{
					logger.debug("setStringAsName(String, String, String, PreparedStatement) - setDouble: i = {} name= {}, value= {}, type= {}",i,name,value,type);									
					ps.setDouble(i+1, Double.parseDouble(value));					
				}
				else if (type.equals("BigDecimal"))
				{
					logger.debug("setStringAsName(String, String, String, PreparedStatement) - setBigDecimal: i = {} name= {}, value= {}, type= {}",i,name,value,type);
					ps.setBigDecimal(i+1, BigDecimal.valueOf(Double.parseDouble(value)));										
				}				
			}
			
		}
	}
	
	// textarea�뿉 �엳�뒗 �궡�슜�쓽 荑쇰━瑜� �뙆�씪�뿉 ���젙�븳�떎.
	public void saveQuery(String queryString)
	{
		File ofile = new File(servletContext.getRealPath("/")+fileName);
		FileWriter fw = null;
		
		try {
			
			fw = new FileWriter(ofile, false) ;
			fw.write(queryString);
			
			//write Flush
			fw.flush();
			this.readQuery();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();		
				
		}		
		finally {
			if(fw != null) try{fw.close();}catch(IOException e){}
		}
	}	
	
	public LinkedHashMap<String,String> excuteQuery(StringBuffer sql,String dataSource,List<String> names , List<String> values, List<String> type ) throws Exception
	{
		   
		   ResultSet  rs=null;
	       Connection con=null;
	       PreparedStatement ps=null;   
	       // ArrayList results = new ArrayList();
	       LinkedHashMap<String,String> results = new LinkedHashMap<String,String>();
	       	       
	       long begin=0;
	       long end=0;	       
	       
	       
		   try
		   {

			   logger.debug("excuteQuery(DataSource) - {}", dataSource); //$NON-NLS-1$
			   logger.debug("excuteQuery(sql) - {}", sql); //$NON-NLS-1$
			   logger.debug("excuteQuery(values) - {}", values); //$NON-NLS-1$
			   logger.debug("excuteQuery(type) - {}", type); //$NON-NLS-1$
			   
			   con=DBManager.getConnection(dataSource);
			   ps = con.prepareStatement(sql.toString());
			   
			   
		    
		       if (values!=null && !values.isEmpty())
		       {
		    	   for (int i=0;i<values.size();i++)
		    	   {
		    		   logger.debug("excuteQuery(String, name,value) - name= {} type= {} " + "type="+type.get(i), names.get(i),values.get(i));
		    		   setStringAsName(names.get(i),values.get(i),type.get(i),ps); 
		    	   }
			   }			   
		       if (logger.isDebugEnabled()) 
		    	   
			   {		       
		    	   begin=System.currentTimeMillis();
			   }
		       rs=ps.executeQuery(); 

		       StringBuffer stb_rsmd=new StringBuffer();
		       ResultSetMetaData rsmd = rs.getMetaData();
		       int columnCnt = rsmd.getColumnCount() ;

		       for(int i=1;i<=columnCnt;i++)
		       {
		              stb_rsmd.append(rsmd.getColumnName(i)+"\t");
		       }
		       columnsName=stb_rsmd.toString();
		       
		       
		       //�뵒踰꾧렇 紐⑤뱶�씪�븣留� resultSet�쓣 泥섎━�븳�떎.
		       if (logger.isDebugEnabled())
		       {
		    	   StringBuffer stb=new StringBuffer();
			       while(rs.next()) 
			       {		    	    
			    	   // Row     
			    	   		    	   		    	   
			            for(int i=1;i<=columnCnt;i++)
			            {
	
						if (logger.isDebugEnabled()) {
							logger.debug("excuteQuery(StringBuffer, String) - {}", rs.getString(i)); //$NON-NLS-1$ //$NON-NLS-2$
						}
			            stb.append(rs.getString(i));
			            stb.append("\t");
	
			            }
			            // �뵒踰꾧렇 紐⑤뱶�씪�븣�뒗 寃곌낵媛믪쓣 李띻퀬, �쟾�넚�븯吏�留�, �븘�땺�븣�뒗 李띿� �븡怨� Success留� 李띿쓬
											
						stb.append("\n");	
			       }
			       logger.debug("excuteQuery(StringBuffer, String) - {}", stb); //$NON-NLS-1$
			       results.put("queryResult", stb.toString());
		       }
		       
		       if (logger.isDebugEnabled()) 
			   {
				   end=System.currentTimeMillis();
				   long etime=end-begin;
				   logger.debug("excuteQuery(StringBuffer, String) - >>>>>>>>> QUERY TIME - {} ms <<<<<<<<<<<" , etime);
				   results.put("etime", etime +"(ms)");

			   }

				if (logger.isInfoEnabled()) {
					logger.info("excuteQuery(StringBuffer, String) - Success"); //$NON-NLS-1$					
					results.put("resultCode", "Success");
					
				}
        
		   }
		   catch ( Exception e ) 
		   {			   				 
			   logger.error("excuteQuery(StringBuffer, String)", e);			   
			   // �뿉�윭�뿉 ���븳 result瑜� �뿉�윭瑜� �뀑�똿�븯�뿬 蹂대궡以�
			   throw new Exception(e);
			   
		   } 
		   finally 
		   {
	           if ( rs != null ) try { rs.close();} catch(Exception e){}
	           if ( ps != null ) try { ps.close(); } catch(Exception e) {}
	           if ( con != null ) try { con.close(); } catch(Exception e) {}		                   
		   }		   
		   return results;
	}		
	

}

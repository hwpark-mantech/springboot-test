package kr.co.mantech.jungwon;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Handles requests for the application home page.
 */
@Controller  
public class Query1 extends Query
{
	/** 
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(Query1.class);
	 
	public  static final String queryName="Query1";
	public Query1()
	{
		// 荑쇰━ �씠由� �뀑�똿 //
		super(queryName);  
		
	}
	

	//init �샇異� 
	@RequestMapping(value = "/query/"+queryName+"/init",  method = {RequestMethod.GET, RequestMethod.POST})
	public String init(
			Locale locale, Model model)					    
	{
				
		initQuery(fileName,orgFileName);			
		model.addAttribute("query",  fileQueryString);
		model.addAttribute("queryName", queryName);
  	    model.addAttribute("bindCount",0);				
		return "/query/"+queryName;
	}	
	
	//save �샇異�
	@RequestMapping(value = "/query/"+queryName+"/save",  method = {RequestMethod.GET, RequestMethod.POST})
	public String save(
			Locale locale, Model model,
			@RequestParam(value="querytextarea", required=false) String querytextarea,					
		    @RequestParam(value="save", required=false) String save) 
	{
		saveQuery(querytextarea);
		model.addAttribute("query",  fileQueryString);
		model.addAttribute("queryName", queryName);
		return "/query/"+queryName; 
	}
	
	//湲곕낯�솕硫� �샇異�	
	@RequestMapping(value = "/query/"+queryName,  method = {RequestMethod.GET, RequestMethod.POST})
	public String gibon(Locale locale, Model model, HttpServletResponse response,
			@RequestParam(value="init", required=false) String init,
		    @RequestParam(value="save", required=false) String save,
            @RequestParam(value="excute", required=false) String excute, 
            @RequestParam(value="v_name", required=false)  List<String>  v_name,     // 諛붿씤�뱶 蹂��닔 泥섎━
            @RequestParam(value="v_value", required=false) List<String>  v_value,    // 諛붿씤�뱶 蹂��닔 泥섎━
            @RequestParam(value="v_type", required=false)  List<String>  v_type,     // 諛붿씤�뱶 蹂��닔 泥섎━
	        @RequestParam(value="dataSource", required=false) String dataSource)
	{
		
		LinkedHashMap<String, String> results=null;

     	// Read Query
		if(init==null &  save==null & excute==null)		
		{		
			this.readQuery();
		}

	    else if(excute!=null && excute.equals("excute"))
	    {
	    	try
	    	{
	    		results = excuteQuery(this.queryString, dataSource,v_name, v_value,v_type);
	    		model.addAttribute("queryResult", results.get("queryResult"));
	    		model.addAttribute("resultCode", results.get("resultCode"));
	    	}
	    	catch(Exception e)
	    	{
	    		//�뿉�윭 諛쒖깮�떆 500�쑝濡� �쓳�떟
	    		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				// �뿉�윭�뿉 ���븳 result瑜� �뿉�윭瑜� �뀑�똿�븯�뿬 蹂대궡以�
				StringWriter sw= new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				model.addAttribute("queryResult",sw.toString());
				
	    	}
	    	
	    	if(queryString.length()==0)
	    	{
	    		this.readQuery();
	    	}
	    	 	
			if (logger.isDebugEnabled() && results !=null) {
				logger.debug("gibon(Locale, Model, String, String, String, String) - queryString={}, columnsName={}", queryString, columnsName); //$NON-NLS-1$
				
				model.addAttribute("columnsName",columnsName);				
				model.addAttribute("etime",      results.get("etime"));				
			}						
	    }
		model.addAttribute("query", fileQueryString);	
		model.addAttribute("queryName", queryName);
		
		if (logger.isDebugEnabled())
		{
			logger.debug("gibon(Locale, Model, String, String, String, List<String>, String) - values=" + v_value); //$NON-NLS-1$
		}
		
		if (v_name!=null && !v_name.isEmpty())
		{
			model.addAttribute("bindCount",v_name.size());
			model.addAttribute("v_name",v_name);
			model.addAttribute("v_type",v_type);
			model.addAttribute("v_value",v_value);
		}
		else
		{
			model.addAttribute("bindCount",0);
		}
		
		return "/query/"+queryName;
	}	 
}

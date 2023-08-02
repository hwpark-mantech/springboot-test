package kr.co.mantech.jungwon;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;
import java.io.*;
import javax.naming.*;
import java.sql.*;
import javax.sql.*;


import javax.servlet.ServletContext;

import org.slf4j.Logger; 
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Handles requests for the application home page.
 */
@Controller 
public class Root {
	 
	private static final Logger logger = LoggerFactory.getLogger(Root.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */ 
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
				
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		//String stringFormat = "Start";
		 
		model.addAttribute("serverTime", formattedDate );
		
		return "root";
	}
	@RequestMapping(value = "/jsp/session-info", method = RequestMethod.GET)
	public String sessioninfo(Locale locale, Model model) {
		

		return "/jsp/session-info";
	}
	
	@RequestMapping(value = "/jsp/sleep", method = RequestMethod.GET)
	public String sleep(Locale locale, Model model,
			@RequestParam(value="second", required=false) String second) 
	{
		
		if (second == null )
		{
			second="1"; 
		}
		long start = System.currentTimeMillis();	
		try {
			Thread.sleep(Integer.parseInt(second)*1000);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int cnt=0;
		int count=5;
		while (true)
		{
			
		 //   double a=Math.random();
		  //  double b=Math.random();
		        double a=ThreadLocalRandom.current().nextDouble();
		        double b=ThreadLocalRandom.current().nextDouble();
		   
		   System.out.println(a);
		   System.out.println(b);
		        double c=a*b*a;

		        if (cnt==count)
		                        break;
		        cnt++;
		}

		long end = System.currentTimeMillis();
		long gap = (end-start);		
		
		model.addAttribute("second", second );
		model.addAttribute("gap", gap );

		return "/jsp/sleep";
	}
		
	

	
}

package org.gusmp.wbfmserver.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.gusmp.wbfmserver.entity.ICvs;
import org.gusmp.wbfmserver.enumeration.Theme;
import org.gusmp.wbfmserver.service.CollocationService;
import org.gusmp.wbfmserver.service.PhrasalVerbService;
import org.gusmp.wbfmserver.service.UpdateService;
import org.gusmp.wbfmserver.service.WordFormationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class MainController {

	@Autowired
	private UpdateService updateService;
	
	@Autowired
	private PhrasalVerbService phrasalVerbService;
	
	@Autowired
	private WordFormationService wordFormationService;

	@Autowired
	private CollocationService collocationService;
	
	@RequestMapping(value = {"/", "/{theme}"})
	public String mainView(@PathVariable Optional<Theme> theme, Model model) {
		

		if (theme.isPresent() == false) {
			model.addAttribute("themeUrl", "resources/js/webix/" + Theme.AIR.getCssFile());
			model.addAttribute("currentTheme",Theme.AIR.name());
		}
		else {
			switch(theme.get()) {
				case AIR:
				case AIRCOMPACT:
				case CLOUDS:
				case COMPACT:
				case CONTRAST:
				case FLAT:
				case GLAMOUR:
				case LIGHT:
				case METRO:
				case TERRACE:
				case TOUCH:
				case WEB:
					model.addAttribute("themeUrl", "resources/js/webix/" + theme.get().getCssFile());
					model.addAttribute("currentTheme",theme.get().name());
					break;
				default:
					model.addAttribute("themeUrl", "resources/js/webix/" + Theme.WEBIX.getCssFile() );
					model.addAttribute("currentTheme",Theme.WEBIX.name());
			}
		}

		
		return "mainView";
	}
	
	// http://localhost:8081/WBFMServer/processFolder
	@RequestMapping("/processFolder")
	@ResponseBody
	public String processFolder() {
		
		try {
				return updateService.updateDabase();	
		} catch(Exception exc) {
			return null;
		}
	}
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	
	private Date getDate(String date) {
	
		if (date == null) return null;
		try {
			return this.sdf.parse(date);
		} catch (ParseException e) {
			return null;
		}
	}
	
	private void generateCsv(List<ICvs> list, String fileName, HttpServletResponse response) throws Exception  {
		
		response.setHeader("Content-Disposition", "attachment; filename="+fileName);
		response.setHeader("Pragma", "no-cache");
	    response.setHeader("Cache-Control", "no-cache");
	    
	    if (list.size() > 0) {
	    	for(ICvs item : list) {
	    		
	    		if (list.indexOf(item) == (list.size()-1)) { 
	    			response.getOutputStream().write( (item.toCsv()).getBytes());
	    		} else {
	    			response.getOutputStream().write( (item.toCsv()+"\n").getBytes());
	    		}
	    	}
	    } else {
	    	response.getOutputStream().write("".getBytes());
	    }
		
	}
	
	// http://localhost:8081/WBFMServer/getpv
	@RequestMapping(value={"/getpv", "/getpv/{date}"})
	public void getPhrasalVerb(@PathVariable(required=false) String date,HttpServletResponse response) throws Exception {
		
		generateCsv(new ArrayList<ICvs>(phrasalVerbService.findAll(getDate(date))),"pv.txt", response);
	}
	
	@RequestMapping(value={"/getwf","/getwf/{date}"})
	public void getWordFormation(@PathVariable(required=false) String date, HttpServletResponse response) throws Exception {
		
		generateCsv(new ArrayList<ICvs>(wordFormationService.findAll(getDate(date))),"wf.txt", response);
	}
	
	@RequestMapping(value={"/getco", "/getco/{date}"})
	public void getCollocations(@PathVariable(required=false) String date, HttpServletResponse response) throws Exception {
		
		generateCsv(new ArrayList<ICvs>(collocationService.findAll(getDate(date))),"co.txt", response);
	}
}

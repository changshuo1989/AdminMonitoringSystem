package com.hrs.changshuo.demo.controller.env;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hrs.changshuo.demo.db.DataSourceHolder;
import com.hrs.changshuo.demo.domain.admin.IEnvironmentDAO;
import com.hrs.changshuo.demo.domain.env.IPatientDAO;
import com.hrs.changshuo.demo.domain.env.Patient;



@Controller
public class PatientController {
	@Autowired
	IPatientDAO patDAO;
	@Autowired
	IEnvironmentDAO envDAO;
	
	
	
	@RequestMapping("/set/patient/{environment}/{name}")
	@ResponseBody
	public String setPatient(@PathVariable(value="environment") String environment, @PathVariable(value="name") String name){
		String res="";
		try{
			DataSourceHolder.setDataSource(environment);
			
			Patient pat=new Patient();
			pat.setName(name);
			pat.setAge(18);
			pat.setEnvId(4);
			patDAO.save(pat);
			res="new patient: "+name+" inserted!";
		}
		catch(Exception e){
			res=e.getMessage();
		}
		return res;
	}
}

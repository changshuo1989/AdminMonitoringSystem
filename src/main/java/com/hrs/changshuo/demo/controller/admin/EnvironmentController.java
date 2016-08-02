package com.hrs.changshuo.demo.controller.admin;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hrs.changshuo.demo.domain.admin.Environment;
import com.hrs.changshuo.demo.domain.admin.IEnvironmentDAO;

@Controller
public class EnvironmentController {
	
	@Autowired
	IEnvironmentDAO envDAO;
	
	@RequestMapping("/")
	@ResponseBody
	public String onStart(){
		System.out.println("================request accepted===============");
		return "This Application starts sucessfully!";
	}
	
	
	@RequestMapping("/get/environments")
	@ResponseBody
	public String getEnvironments(){
		String res="";
		try{
			Iterator<Environment> envs=envDAO.findAll().iterator();
			while(envs.hasNext()){
				res=res+envs.next().getName()+", ";
			}
		}
		catch(Exception e){
			res=e.getMessage();
		}
		return res;
	}
	
	@RequestMapping("/set/environment/{name}")
	@ResponseBody
	public String setEnvironment(@PathVariable String name){
		String res="";
		try{
			Environment env=new Environment();
			env.setName(name);
			env.setLastUpdated(new Timestamp(new Date().getTime()));
			envDAO.save(env);
			res="new environment: "+name+" inserted!";
		}
		catch(Exception e){
			res=e.getMessage();
		}
		return res;
	}
}

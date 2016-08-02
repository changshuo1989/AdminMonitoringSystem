package com.hrs.changshuo.demo.domain.env;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPatientDAO extends CrudRepository<Patient, Integer>{
	
}

package com.hrs.changshuo.demo.domain.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEnvironmentDAO extends JpaRepository<Environment, Integer>{
	public Environment findOneByName(String name);
}

package com.example.demo.repo;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.Entity.User;

public interface UserRepo extends JpaRepository<User, Integer>{

	int countByUsername(String username);

	User findByUsername(String username);
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM user_task WHERE task_id = ?",nativeQuery = true)
	public void deletefromtable(int task);

}

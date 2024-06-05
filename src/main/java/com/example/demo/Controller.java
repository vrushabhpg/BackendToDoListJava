package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entity.Task;
import com.example.demo.Entity.User;
import com.example.demo.repo.TaskRepo;
import com.example.demo.repo.UserRepo;

@RestController
@CrossOrigin
public class Controller {

	@Autowired
	TaskRepo taskrepo;
	@Autowired
	UserRepo userrepo;

	// @RequestMapping("reg/{name}/{username}/{password}")
	// public int register(@PathVariable String name,@PathVariable String
	// username,@PathVariable String password)
	// {
	// User u=new User(0,name,username,password, null);
	// userrepo.save(u);
	// return 1;
	//
	// }

	@RequestMapping("login/{username}/{password}")
	public int login(@PathVariable String username, @PathVariable String password) {
		if (userrepo.countByUsername(username) != 1)
			return -1;
		User user = userrepo.findByUsername(username);
		if (!password.equals(user.getPassword()))
			return -2;

		return 1;
	}

	@RequestMapping("userdata/{username}")
	public User userdata(@PathVariable String username) {
		return userrepo.findByUsername(username);
	}

	@RequestMapping("addTask/{userid}/{work}")
	public Task addtask(@PathVariable int userid, @PathVariable String work) {
		Task task = new Task(0, work, 0);
		task = taskrepo.save(task);

		User user = userrepo.findById(userid).get();
		user.getTask().add(task);
		userrepo.save(user);
		return task;
	}

	@RequestMapping("changeStatus/{taskId}/{newStatus}")
	public boolean changeStatus(@PathVariable int taskId, @PathVariable int newStatus) {
		Task task = taskrepo.findById(taskId).get();
		task.setStatus(newStatus);
		taskrepo.save(task);
		return true;
	}

	@RequestMapping("deletetask/{userid}/{taskId}")
	public User deleterow(@PathVariable int userid,@PathVariable int taskId)
	{		
			userrepo.deletefromtable(taskId);
			taskrepo.deleteById(taskId);
			User user = userrepo.findById(userid).get();
			return user;
	}

}

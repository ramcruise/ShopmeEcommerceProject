package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
	
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateNewUserWithOneRole() {
		Role roleAdmin= entityManager.find(Role.class, 1);
		User userRam = new User("rambabu0216@gmail.com","Rambabu0216@","rambabu","yadav");
		userRam.addRole(roleAdmin);
		
		 User saveUser = repo.save(userRam);
		 
		 assertThat(saveUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateNewUserWithTwoRole() {
		//Role roleAdmin= entityManager.find(Role.class, 1);
		User userRavi = new User("ravi@gmail.com","Ravi2023","Ravi","Kumar");
		Role roleEditor = new Role(3);
		Role roleAssistant = new Role(5);
		
		userRavi.addRole(roleEditor);
		userRavi.addRole(roleAssistant);
		
		 User saveUser = repo.save(userRavi);
		 
		 assertThat(saveUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testListAllUsers() {
		Iterable<User> listusers = repo.findAll();
		listusers.forEach(user -> System.out.println(user));
		//assertThat(listusers);
	}
	
	@Test
	public void getUserById() {
		User userRam = repo.findById(1).get();
		System.out.println("User whose ID is 1 "+userRam);
		assertThat(userRam).isNotNull();
	}

	@Test
	public void testUserDetails() {
		User userRam = repo.findById(1).get();
		userRam.setEnabled(true);
		
		repo.save(userRam);
	}
	
	@Test
	public void testUpdateUserRoles() {
		User userRavi = repo.findById(2).get();
		Role roleEditor= new Role(3);
		Role roleSalesperson = new Role(2);
		
		userRavi.getRoles().remove(roleEditor);
		userRavi.addRole(roleSalesperson);
		
		repo.save(userRavi); 
	}
	
	@Test
	public void testDeleteuser() {
		Integer userId = 2;
		repo.deleteById(userId);
		
	}
	
	@Test
	public void testGetUserByEmail() {
		String email = "rambabu0216@gmail.com";
		User user = repo.getUserByEmail(email);
		
		assertThat(user).isNotNull();
	}
	
	@Test
	public void testCountById() {
		Integer id = 15;
		Long countById = repo.countById(id);
		
		assertThat(countById).isNotNull().isGreaterThan(0);
	}
	
	@Test
	public void testDisableUser() {
		Integer id = 5;
		repo.updateEnabledStatus(id, false);
		
	}
	
	@Test
	public void testEnableUser() {
		Integer id = 5;
		repo.updateEnabledStatus(id, true);
		
	}
	
	@Test
	public void testListFirstPage() {
		int pageNumber = 0;
		int pageSize = 4;
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<User> page = repo.findAll(pageable);
		
		 List<User> listUsers = page.getContent();
		
		listUsers.forEach(user -> System.out.println(user));
		
		assertThat(listUsers.size()).isEqualTo(pageSize);
		
		
	}
	
	@Test
	public void testSearchUsers() {
		String keyword = "bruce";
		
		int pageNumber = 0;
		int pageSize = 4;
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<User> page = repo.findAll(keyword,pageable);
		
		 List<User> listUsers = page.getContent();
		
		listUsers.forEach(user -> System.out.println(user));
		
		assertThat(listUsers.size()).isGreaterThan(0);
	}
	
	
}

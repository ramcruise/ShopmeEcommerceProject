package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;

import antlr.collections.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class RoleRepositoryTests {
	
	@Autowired
	private RoleRepository repo;

	@Test
	public void testCreateFirstRole() {
		Role roleAdmin = new Role("Admin","Manage every thing");
		Role saveRole = repo.save(roleAdmin);
		
		assertThat(saveRole.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateRestRole() {
	Role roleSalesPerson = new Role("Salesperson","manage product price, "
	+ "Customer, shipping, orders and sales report");
	
	Role roleEditor =  new Role("Editor", "manage categories, brands, "
			+ "product, articles and minus");
	
	Role shipper = new Role("Shipper","view products, view orders "
			+ "and update order status");
	
	Role roleAssistance = new Role("Assistant","manage questions and reviews");
	
	repo.saveAll(java.util.List.of(roleSalesPerson,roleEditor,shipper,roleAssistance));
	}
}

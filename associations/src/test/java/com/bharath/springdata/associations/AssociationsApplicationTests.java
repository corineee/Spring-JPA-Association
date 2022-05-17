package com.bharath.springdata.associations;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.bharath.springdata.associations.manytomany.entities.Programmer;
import com.bharath.springdata.associations.manytomany.entities.Project;
import com.bharath.springdata.associations.manytomany.repos.ProgrammerRepository;
import com.bharath.springdata.associations.onetomany.entities.Customer;
import com.bharath.springdata.associations.onetomany.entities.PhoneNumber;
import com.bharath.springdata.associations.onetomany.entities.repos.CustomerRepository;
import com.bharath.springdata.associations.onetoone.entites.License;
import com.bharath.springdata.associations.onetoone.entites.Person;
import com.bharath.springdata.associations.onetoone.repos.LicenseRepository;

@SpringBootTest
class AssociationsApplicationTests {

	@Autowired
	CustomerRepository repository;
	
	@Autowired
	ProgrammerRepository programmerRepository;
	
	@Autowired
	LicenseRepository licenseRepository;

	@Test
	void contextLoads() {
	}

	@Test
	public void testCreateCustomer() {
		Customer customer = new Customer();
		customer.setName("John");

		PhoneNumber ph1 = new PhoneNumber();
		ph1.setNumber("1234567890");
		ph1.setType("cell");

		PhoneNumber ph2 = new PhoneNumber();
		ph2.setNumber("0987654321");
		ph2.setType("home");

		customer.addPhoneNumber(ph1);
		customer.addPhoneNumber(ph2);

		repository.save(customer);
	}

	@Test
	public void testLoadCustomer() {
		Customer customer = repository.findById(4L).get();
		customer.setName("John Bush");

		Set<PhoneNumber> numbers = customer.getNumbers();
		numbers.forEach(number -> number.setType("cell"));
		
		repository.save(customer);
	}
	
	@Test
	public void testDelete() {
//		repository.delete(4L);
	}
	
	@Test
	public void testmtomCreateProgrammer() {
		Programmer programmer = new Programmer();
		programmer.setName("John");
		programmer.setSal(10000);
		HashSet<Project> projects = new HashSet<Project>();
		Project project = new Project();
		project.setName("Hibernate Project");
		projects.add(project);
		
		programmer.setProjects(projects);
		
		programmerRepository.save(programmer);
	}
	
	@Test
	@Transactional
	public void testmtomFindProgrammer() {
		Programmer programmer = programmerRepository.findById(1).get();
		System.out.println(programmer);
		System.out.println(programmer.getProjects());
	}

	@Test
	public void testOnetoOneCreateLicense() {
		License license = new License();
		license.setType("CAR");
		license.setValidFrom(new Date());
		license.setValidTo(new Date());
		
		Person person = new Person();
		person.setFirstName("John");
		person.setLastName("Clinton");
		person.setAge(35);
		license.setPreson(person);
		licenseRepository.save(license);
	}
	
}













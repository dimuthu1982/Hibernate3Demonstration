package home.hibernate.main;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Assert;

import home.hibernate.dto.Department;
import home.hibernate.dto.Dependents;
import home.hibernate.dto.Employee;
import home.hibernate.dto.Laptop;
import home.hibernate.dto.OfficeContactDetails;
import home.hibernate.dto.VehiclePass;

public class HibernateMain {

	public static void main(String[] args) {
		
		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session session = factory.openSession();
		
//		createDBStructure(session);
//		findDataByBindingVariables(session);
//		findDataByNamedQuery(session);
//		findAndAlterChachableObject(factory);
		findQueryCache(factory);
		
		session.close();
	}
	
	private static void findQueryCache(SessionFactory factory) {
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		
		Query query = session.createQuery("from Employee where salary > ?");
		query.setDouble(0, 30000);
		query.setCacheable(true);
		System.out.println(query.list().size());
		tx.commit();
		session.close();
		
		
		Session session1 = factory.openSession();
		session1.beginTransaction();
		
		query = session1.createQuery("from Employee where salary > ?");
		query.setDouble(0, 30000);
		query.setCacheable(true);
		System.out.println(query.list().size());
		session1.getTransaction().commit();
		session1.close();
		
		
	}

	private static void findAndAlterChachableObject(SessionFactory factory) {
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		
		Employee emp7 = (Employee)session.get(Employee.class, 7);
		Laptop laptop = emp7.getLaptopInformation();
		System.out.println(laptop.getLaptopId());
		laptop.setModelId("N/A");
		laptop.setName("N/A");
		tx.commit();
		session.close();
		
		session = factory.openSession();
		tx = session.beginTransaction();
		laptop = (Laptop)session.get(Laptop.class, 4);
		System.out.println(laptop.getModelId());
		Assert.assertEquals("N/A",laptop.getModelId());
		Assert.assertEquals("N/A",laptop.getName());
		
		laptop.setModelId("DEL-34460");
		laptop.setName("Dell-Laptop");
		tx.commit();
		session.close();
		
	}

	private static void findDataByNamedQuery(Session session) {
		Query query = session.getNamedQuery("Employee.ageRange");
		query.setParameter("ageFrom", 20);
		query.setParameter("ageTo", 25);
		
		List<Employee> empList = query.<Employee>list();
		
		Assert.assertEquals(1,empList.size());
		Assert.assertEquals("Peter", empList.get(0).getName());
	}

	private static void findDataByBindingVariables(Session session) {
		Query query = session.createQuery("from Employee where salary > :salary");
		query.setParameter("salary", new Double(23000));
		List<Employee> empList = query.<Employee>list();
		
		Assert.assertEquals(2,empList.size());
		Assert.assertEquals("Michel", empList.get(0).getName());
		
	}

	private static void createDBStructure(Session session){
		
		Transaction tx = session.beginTransaction();
		
		List savingData = getStructureForSave();
		for (Object dto : savingData) {
			session.save(dto);
		}
		tx.commit();
	}
	
	private static List getStructureForSave(){
		List savingDataList = new ArrayList<>();
		
		Laptop laptop1 = new Laptop("HP-300", "HP-Laptop");
		Laptop laptop2 = new Laptop("DEL-34460", "Dell-Laptop");
		Laptop laptop3 = new Laptop("ASU-30050", "Asuse-Laptop");
		
		OfficeContactDetails contactDetails1 = new OfficeContactDetails(1234, 4321);
		OfficeContactDetails contactDetails2 = new OfficeContactDetails(5678, 8765);
		OfficeContactDetails contactDetails3 = new OfficeContactDetails(9357, 7539);
		
		Employee emp1 = getEmployee("Peter", 23, new Date(2005, 04, 22), 23000,contactDetails1 ,
				laptop1,getVehicleInforSet(new VehiclePass("GD-Pet-1")));
		
		Dependents wife = new Dependents("Peter: Wife's Name","ABC");
		Dependents child = new Dependents("Peter: Childs Name","PQR");
		
		Department itDept = new Department(2000,"IT");
		Department accountingDept = new Department(5000,"Accounting");
		
		wife.setDependentOwner(emp1);
		child.setDependentOwner(emp1);
		
		emp1.getDependents().add(wife);
		emp1.getDependents().add(child);
		
		Employee emp2 = getEmployee("Michel", 33, new Date(2007, 8, 15), 29000, contactDetails2,
				laptop2,getVehicleInforSet(new VehiclePass("GD-Mic-1"),new VehiclePass("GD-Kas-2")));
		
		Employee emp3 = getEmployee("Talor", 37, new Date(2012, 04, 22), 45000, contactDetails3,
				laptop3,getVehicleInforSet());

		
		emp1.getDepartments().add(itDept);
		emp2.getDepartments().add(accountingDept);
		emp3.getDepartments().add(itDept);
		
		emp1.setContactDetails(contactDetails1);
		emp2.setContactDetails(contactDetails2);
		emp3.setContactDetails(contactDetails3);
		
		itDept.getWorkingEmployees().add(emp1);
		accountingDept.getWorkingEmployees().add(emp2);
		itDept.getWorkingEmployees().add(emp3);
		
		savingDataList.add(wife);
		savingDataList.add(child);
		
		savingDataList.add(itDept);
		savingDataList.add(accountingDept);
		
		savingDataList.add(laptop1);
		savingDataList.add(laptop2);
		savingDataList.add(laptop3);
		
		savingDataList.add(emp1);
		savingDataList.add(emp2);
		savingDataList.add(emp3);
		
		return savingDataList;
	}
	
	private static Set<VehiclePass> getVehicleInforSet(VehiclePass... vehiclePass) {
		Set<VehiclePass> hashSet = new HashSet<>(vehiclePass.length);
		for (int i = 0; i < vehiclePass.length; i++) {
			hashSet.add(vehiclePass[i]);
		}
		return hashSet;
	}

	private static Employee getEmployee(String name,int age, Date joinedDate, double salary,OfficeContactDetails contact,Laptop laptop, Set<VehiclePass> vehiclePasses) {
		Employee emp = new Employee(name, age, salary,joinedDate);
		emp.setName(name);
		emp.setJoinedDate(joinedDate);
		emp.setSalary(salary);
		emp.setVehiclePasses(vehiclePasses);
		emp.setLaptopInformation(laptop);
		emp.setContactDetails(contact);
		return emp;
	}

}

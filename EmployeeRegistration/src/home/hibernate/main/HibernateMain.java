package home.hibernate.main;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import home.hibernate.dto.Department;
import home.hibernate.dto.Dependents;
import home.hibernate.dto.Employee;
import home.hibernate.dto.Laptop;
import home.hibernate.dto.OfficeContactDetails;
import home.hibernate.dto.VehiclePass;

public class HibernateMain {

	public static void main(String[] args) {
		
//		createDBStructure();
		
		
	}
	
	private static void createDBStructure(){
		Session session = null;
		Transaction tx = null;
		
		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		
		session = factory.openSession();
		tx = session.beginTransaction();
		
		List savingData = getStructureForSave();
		for (Object dto : savingData) {
			session.save(dto);
		}
		tx.commit();
		session.close();
	}
	
	private static List getStructureForSave(){
		List savingDataList = new ArrayList<>();
		
		Employee emp1 = getEmployee("Peter", 23, new Date(2005, 04, 22), 23000, new OfficeContactDetails(1234, 4321),
				new Laptop("HP-300", "HP-Laptop"),getVehicleInforSet(new VehiclePass("GD-Pet-1")));
		
		Dependents wife = new Dependents("Peter: Wife's Name","ABC");
		Dependents child = new Dependents("Peter: Childs Name","PQR");
		
		Department itDept = new Department(2000,"IT");
		Department accountingDept = new Department(5000,"Accounting");
		
		wife.setDependentOwner(emp1);
		child.setDependentOwner(emp1);
		
		emp1.getDependents().add(wife);
		emp1.getDependents().add(child);
		
		Employee emp2 = getEmployee("Michel", 33, new Date(2007, 8, 15), 29000, new OfficeContactDetails(5678, 8765),
				new Laptop("DEL-34460", "Dell-Laptop"),getVehicleInforSet(new VehiclePass("GD-Mic-1"),new VehiclePass("GD-Kas-2")));
		
		Employee emp3 = getEmployee("Talor", 37, new Date(2012, 04, 22), 45000, new OfficeContactDetails(9357, 7539),
				new Laptop("ASU-30050", "Asuse-Laptop"),getVehicleInforSet());

		
		emp1.getDepartments().add(itDept);
		emp2.getDepartments().add(accountingDept);
		emp3.getDepartments().add(itDept);
		
		itDept.getWorkingEmployees().add(emp1);
		accountingDept.getWorkingEmployees().add(emp2);
		itDept.getWorkingEmployees().add(emp3);
		
		savingDataList.add(wife);
		savingDataList.add(child);
		
		savingDataList.add(itDept);
		savingDataList.add(accountingDept);
		
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
		return emp;
	}

}

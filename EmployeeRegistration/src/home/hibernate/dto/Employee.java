package home.hibernate.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "EMPLOYEE")
@NamedQuery(name="Employee.ageRange",query="from Employee where age between :ageFrom and :ageTo")
public class Employee {

	@Id
	@Column(name="EMPLOYEE_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int empId;

	@Column(name="NAME")
	private String name;

	@Column(name="AGE")
	private int age;

	@Column(name="JOINED_DATE")
	private Date joinedDate;

	@Column(name="SALARY")
	private double salary;

	public Employee(String name, int age, double salary,Date joinedDate) {
		this.name = name;
		this.age = age;
		this.salary = salary;
		this.joinedDate = joinedDate;
	}

	public Employee(){}


	/*
	 * Says that the class is an object which needs to be treated as embedded
	 */
	@Embedded
	private OfficeContactDetails contactDetails;
	
	/*
	 * Value type inside an Entity
	 * When the @ElementCollection added it is mandatory that the Dependents makes as @Embeddable to inform Hibernate its including in.
	 * With that only the query is s follows : Employee_vehiclePasses (Employee_EMPLOYEE_ID, VEHICLE_NUMBER)
	 * Note: table name and the primary Id is not listed  in present way. To alter them following should take place
	 * @JoinTable(name="EMPLOYEE_CAR_PASS", joinColumns = @JoinColumn(name="EMP_ID"))
	 * With the above line, the query will be formatted to as follows: EMPLOYEE_CAR_PASS (EMP_ID, VEHICLE_NUMBER)
	 */
	@ElementCollection
	@JoinTable(name="EMPLOYEE_CAR_PASS", joinColumns = @JoinColumn(name="EMP_ID"))
	private Set<VehiclePass> vehiclePasses = new HashSet<>();
	
	/*
	 * One to One Relationship
	 * The relation going to dictate that the primary key of the WORK_STATIONS table will be the foreign key to this table. So 
	 * by default its going to create a column in Employee table called "WORK_STATIONS_WORK_STATION_ID". Cascade is use to save the Laptop
	 * information along with the save of the Employee.
	 * To get a meaning full name @JoinColumn(name="WORK_STATION_ID") is used
	 *  
	 */
	@OneToOne
	@JoinColumn(name="WORK_STATION_ID")
	private Laptop laptopInformation;
	

	/*
	 * One to many Mapping without Ternary Table
	 * mappedBy referred to the property name phoneOwner
	 * 
	 * Result will be a new table referring to Employee by EMP_ID column: DEPENDENTS (EMP_ID, NAME, RELATIONSHIP, DEPENDENT_ID)
	 */
	@OneToMany(mappedBy="dependentOwner")
	private List<Dependents> dependents = new ArrayList<>();

	
	/*
	 * Mapping Many To Many
	 * This will create a 2 additional tables to hold the relationship, each table per relationship in both classes:
	 * 	departments -> EMPLOYEE_DEPARTMENT
	 * Department.workingEmployees -> DEPARTMENT_EMPLOYEE
	 * To avoid that one relationship has to be prominent. This is done my "mappedBy" saying that the relationship is mapped by the given attribute.
	 * On this occasion it will be defined in Department to make the  departments as prominent on the relationship.
	 * Doing so will create a table as follows: EMPLOYEE_DEPARTMENT (workingEmployees_EMPLOYEE_ID, departments_DEPARTMENT_ID)
	 * Note: the joining table contains names too large and they can be arranged as : EMPLOYEE_DEPARTMENT_MAPPING (EMP_ID, REGISTRATION_NO)
	 * Cascade:
	 * cascade type is set so that the Transportation doesn't needs to save specifically, by including Transportation in the Employee
	 * will propagate the changes to the DB
	 */
	@ManyToMany(cascade=CascadeType.PERSIST)
	@JoinTable(name="EMPLOYEE_DEPARTMENT_MAPPING",joinColumns=@JoinColumn(name="EMP_ID"), inverseJoinColumns=@JoinColumn(name="REGISTRATION_NO"))
	private List<Department> departments = new ArrayList<>();


	public int getEmpId() {
		return empId;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public Date getJoinedDate() {
		return joinedDate;
	}

	public double getSalary() {
		return salary;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setJoinedDate(Date joinedDate) {
		this.joinedDate = joinedDate;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}


	public Laptop getLaptopInformation() {
		return laptopInformation;
	}

	public void setLaptopInformation(Laptop laptopInformation) {
		this.laptopInformation = laptopInformation;
	}

	public Set<VehiclePass> getVehiclePasses() {
		return vehiclePasses;
	}

	public void setVehiclePasses(Set<VehiclePass> vehiclePasses) {
		this.vehiclePasses = vehiclePasses;
	}

	public OfficeContactDetails getContactDetails() {
		return contactDetails;
	}

	public void setContactDetails(OfficeContactDetails contactDetails) {
		this.contactDetails = contactDetails;
	}

	public List<Dependents> getDependents() {
		return dependents;
	}

	public void setDependents(List<Dependents> dependents) {
		this.dependents = dependents;
	}

	public List<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}
}

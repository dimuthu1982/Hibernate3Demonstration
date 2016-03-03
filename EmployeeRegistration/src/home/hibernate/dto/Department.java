package home.hibernate.dto;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name ="DEPARTMENT")
public class Department {
	@Id @Column(name="DEPARTMENT_ID")
	private int departmmentId;

	@Column(name="DEPRTMENT_NAME")
	private String deprtmentName;

	/*
	 * With mappedBy="departments", it indicates that the relationship is formed by the attribute id departments, 
	 * and Hibernate should not create a relationship table for this mapping.
	 */
	@ManyToMany(mappedBy="departments")
	private Set<Employee> workingEmployees = new HashSet<>();

	public Department() {
	}

	public Department(int departmmentId, String deprtmentName) {
		this.departmmentId = departmmentId;
		this.deprtmentName = deprtmentName;
	}

	public int getDepartmmentId() {
		return departmmentId;
	}

	public String getDeprtmentName() {
		return deprtmentName;
	}

	public Set<Employee> getWorkingEmployees() {
		return workingEmployees;
	}

	public void setDepartmmentId(int departmmentId) {
		this.departmmentId = departmmentId;
	}

	public void setDeprtmentName(String deprtmentName) {
		this.deprtmentName = deprtmentName;
	}

	public void setWorkingEmployees(Set<Employee> workingEmployees) {
		this.workingEmployees = workingEmployees;
	}
}
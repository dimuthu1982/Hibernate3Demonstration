package home.hibernate.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "DEPENDENTS")
public class Dependents {

	@Id @Column(name="DEPENDENT_ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int delendentId;
	
	@Column(name="RELATIONSHIP")
	private String relationship;
	
	@Column(name="NAME")
	private String name;

	@ManyToOne
	@JoinColumn(name="EMP_ID")
	private Employee dependentOwner;
	
	public Dependents() {
	}

	public Dependents(String relationship, String name) {
		this.relationship = relationship;
		this.name = name;
	}

	public String getRelationship() {
		return relationship;
	}

	public String getName() {
		return name;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getDelendentId() {
		return delendentId;
	}

	public Employee getDependentOwner() {
		return dependentOwner;
	}

	public void setDelendentId(int delendentId) {
		this.delendentId = delendentId;
	}

	public void setDependentOwner(Employee dependentOwner) {
		this.dependentOwner = dependentOwner;
	}
	
}

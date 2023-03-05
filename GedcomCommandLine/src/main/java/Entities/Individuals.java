package Entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Individuals")
public class Individuals {
	@Id
	@Column(name="individual_id")
	private String id;
	
	@Column(name="Name")
    private String name;
	
	@Column(name="sex")
    private char sex;
	
	@Column(name="Birth_date")
    private Date birthDate;
	
	@Column(name="Death_date")
    private Date deathDate;
	
	@Column(name="famC_ID")
	private String famCId;
	
	@Column(name="famS_ID")
	private String famSId;

	
	public Individuals() {
		
	}
	public Individuals(String id) {
		super();
		this.id = id;
	}

	public Individuals(String id, String name, char sex) {
		super();
		this.id = id;
		this.name = name;
		this.sex = sex;
	}

	public Individuals(String id, String name, char sex, Date birthDate, Date deathDate, String famCId, String famSId) {
		super();
		this.id = id;
		this.name = name;
		this.sex = sex;
		this.birthDate = birthDate;
		this.deathDate = deathDate;
		this.famCId = famCId;
		this.famSId = famSId;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public String getNameNoNull() {
		if(name!=null)
			return name;
		else
			return "-";
	}
	public void setName(String name) {
		this.name = name;
	}

	public char getSex() {
		return sex;
	}
	public char getSexNoNull() {
		if(sex=='M'||sex=='F')
			return sex;
		else
			return '-';
	}
	public void setSex(char sex) {
		this.sex = sex;
	}

	public Date getBirthDate() {
		return birthDate;
	}
	public String getBirthDateNoNull() {
		if(birthDate!=null)
			return birthDate.toString().substring(0,10);
		else
			return "-";
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Date getDeathDate() {
		return deathDate;
	}
	public String getDeathDateNoNull() {
		if(deathDate!=null)
			return deathDate.toString().substring(0,10);
		else
			return "-";
	
	}
	public void setDeathDate(Date deathDate) {
		this.deathDate = deathDate;
	}

	public String getFamCId() {
		return famCId;
	}
	public String getFamCIdNoNull() {
		if(famCId!=null)
			return famCId;
		else
			return "-";
	}

	public void setFamCId(String famCId) {
		this.famCId = famCId;
	}

	public String getFamSId() {
		return famSId;
	}
	public String getFamSIdNoNull() {
		if(famSId!=null)
			return famSId;
		else
			return "-";
	}
	public void setFamSId(String famSId) {
		this.famSId = famSId;
	}
	
 

}

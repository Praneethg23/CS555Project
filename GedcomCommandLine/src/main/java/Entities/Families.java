package Entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Families")
public class Families {

	@Id
	@Column(name="family_id")
	private String familiyId;
	
	@Column(name="Marraige_date")
	private Date marraigeDate;
	
	@Column(name="Divorce_date")
	private Date divorceDate;
	
	@Column(name="Husband_id")
	private String husbandId;
	
	@Column(name="Wife_id")
	private String wifeId;
	

	public Families() {
		
	}
	
	public Families(String familiyId, Date marraigeDate, Date divorceDate, String husbandId, String wifeId) {
		super();
		this.familiyId = familiyId;
		this.marraigeDate = marraigeDate;
		this.divorceDate = divorceDate;
		this.husbandId = husbandId;
		this.wifeId = wifeId;
	}

	public Families(String familiyId) {
		super();
		this.familiyId = familiyId;
	}

	public String getFamiliyId() {
		return familiyId;
	}

	public void setFamiliyId(String familiyId) {
		this.familiyId = familiyId;
	}

	public Date getMarraigeDate() {
		return marraigeDate;
	}
	public String getMarraigeDateNoNull() {
		if(marraigeDate!=null)
			return marraigeDate.toString().substring(0,10);
		else
			return "-";
	}

	public void setMarraigeDate(Date marraigeDate) {
		this.marraigeDate = marraigeDate;
	}

	public Date getDivorceDate() {
		return divorceDate;
	}
	public String getDivorceDateNoNull() {
		if(divorceDate!=null)
			return divorceDate.toString().substring(0,10);
		else
			return "-";
	}

	public void setDivorceDate(Date divorceDate) {
		this.divorceDate = divorceDate;
	}

	public String getHusbandId() {
		return husbandId;
	}

	public void setHusbandId(String husbandId) {
		this.husbandId = husbandId;
	}

	public String getWifeId() {
		return wifeId;
	}

	public void setWifeId(String wifeId) {
		this.wifeId = wifeId;
	}

	
	
}

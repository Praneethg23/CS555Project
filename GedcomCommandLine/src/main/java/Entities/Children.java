package Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Children")
public class Children {
	
    
     
    @Id @Column(name = "child_id")
    private String childId;
    
    @Column(name = "family_id")
    private String familyId;
	

	public Children() {
		
	}
	public Children(String childId) {
		this.childId = childId;
	}
	public Children(String familyId, String childId) {
		super();
		this.familyId = familyId;
		this.childId = childId;
	}





	public String getFamilyId() {
		return familyId;
	}



	public void setFamilyId(String familyId) {
		this.familyId = familyId;
	}



	public String getChildId() {
		return childId;
	}



	public void setChildId(String childId) {
		this.childId = childId;
	}




	

    
}

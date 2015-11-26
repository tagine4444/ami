package ami.domain.model.security.amiusers;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

import ami.domain.model.security.AmiAdminAuthority;
import ami.domain.model.security.AmiAuthtorities;
import ami.domain.model.security.AmiMasterAuthority;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown=true)
@Document
public class AmiUser {

	private String user;
	private String pwd;
	private String firstName;
	private String lastName;
	private String email;
	private String occupation;
	private String hospitalName;
	private String hospitalId;
	private  DateTime creationDate;
	private  String createdBy;
	private  DateTime deactivationDate;
	private  String deactivatedBy;
	private  String deactivationReason;
	private boolean masterUser;
	
	private List< GrantedAuthority> role;
	
	public AmiUser(){
		
	}
	
	public AmiUser(NewUser newUser, String hospitalName, String hospitalId ,List< GrantedAuthority> role){
		this(newUser.getUserName(), newUser.getPwd(), newUser.getFirstName(),
				newUser.getLastName(),newUser.getEmail(),newUser.getOccupation(),  hospitalName, hospitalId, new DateTime(), 
				role);
	}
	
	public AmiUser(String user,String pwd, String firstName, String lastName, String email,String occupation, String hospitalName, String hospitalId, DateTime creationDate, List< GrantedAuthority> role) {
		this.user = user;
		this.pwd = pwd;
		this.firstName =firstName;
		this.lastName = lastName;
		this.email = email;
		this.occupation = occupation;
		this.hospitalName = hospitalName;
		this.hospitalId = hospitalId;
		this.creationDate =creationDate;
		this.role = role;
		this.masterUser =false;
		
		if(this.role!=null && this.role.size()>0 ){
		
			for (GrantedAuthority grantedAuthority : this.role) {
				if( AmiAuthtorities.AMI_MASTER_USER.toString().equals( grantedAuthority.getAuthority() )){
					this.masterUser = true;
					break;
				}
			}
		}
	}
	
	
	public void initializeMasterUser(String hospitalId){
		this.hospitalId = hospitalId;
		this.masterUser = true;
		if(this.role ==null){
			this.role = new ArrayList<GrantedAuthority>();
		}
		this.role.add(new AmiMasterAuthority());
	}
	
	public String getHospitalName() {
		return hospitalName;
	}

	public List< GrantedAuthority> getRole() {
		return role;
	}

	public String getUser() {
		return user;
	}
	
	public String getPwd() {
		return pwd;
	}

	public void blurPassword(){
		this.pwd = "";
	}
	
	
	@Override
	public String toString() {
		return "AmiUser [  user=" + user +", role=" + role.get(0).getAuthority() +"]";
	}
	public String getHospitalId() {
		return hospitalId;
	}
	public DateTime getCreationDate() {
		return creationDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public DateTime getDeactivationDate() {
		return deactivationDate;
	}
	public String getDeactivatedBy() {
		return deactivatedBy;
	}
	public String getDeactivationReason() {
		return deactivationReason;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	
	public boolean isMasterUser() {
		return masterUser;
	}

	public String getEmail() {
		return email;
	}

	public String getOccupation() {
		return occupation;
	}
	
	
	
}

package ami.domain.amiusers;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

import ami.domain.security.AmiAuthtorities;

@Document
public class AmiUser {

	private String user;
	private String pwd;
	private String firstName;
	private String lastName;
	private String hospitalName;
	private String hospitalId;
	private  DateTime creationDate;
	private  String createdBy;
	private  DateTime deactivationDate;
	private  String deactivatedBy;
	private  String deactivationReason;
	private boolean masterUser;
	
	private List<? extends GrantedAuthority> role;
	
	public AmiUser(){
		
	}
	
	public AmiUser(NewUser newUser, String hospitalName, String hospitalId ,List<? extends GrantedAuthority> role){
		this(newUser.getUserName(), newUser.getPwd(), newUser.getFirstName(),
				newUser.getLastName(), hospitalName, hospitalId, new DateTime(), 
				role);
	}
	
	public AmiUser(String user,String pwd, String firstName, String lastName, String hospitalName, String hospitalId, DateTime creationDate, List<? extends GrantedAuthority> role) {
		this.user = user;
		this.pwd = pwd;
		this.firstName =firstName;
		this.lastName = lastName;
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
	
	public String getHospitalName() {
		return hospitalName;
	}

	public List<? extends GrantedAuthority> getRole() {
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
	
//	public boolean isMasterUser(){
//		
//		boolean foundMasterRole =false;
//		for (GrantedAuthority grantedAuthority : this.role) {
//			if( AmiAuthtorities.AMI_MASTER_USER.toString().equals( grantedAuthority.getAuthority() )){
//				foundMasterRole = true;
//				break;
//			}
//		}
//		
//		return foundMasterRole;
//	}
	
	
	
	
}

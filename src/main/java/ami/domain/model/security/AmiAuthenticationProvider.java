package ami.domain.model.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import ami.domain.model.security.amiusers.AmiUser;
import ami.domain.model.security.amiusers.AmiUserRepository;
import ami.infrastructure.database.model.AmiUserView;

@Service
public class AmiAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
//
//	@Autowired
//	private MongoTemplate mongo;
	
	@Autowired
	private AmiUserRepository amiUserService;

	@Override
	protected void additionalAuthenticationChecks(UserDetails arg0,
			UsernamePasswordAuthenticationToken arg1)
			throws AuthenticationException {
		// TODO Auto-generated method stub
		System.out.println("didnt expect to be here...");
		//throw new RuntimeException("didn't expect to be here");
		
	}

	@Override
	protected UserDetails retrieveUser(String arg0,
			UsernamePasswordAuthenticationToken arg1)
			throws AuthenticationException {
		
		UserDetails loadedUser;

        try 
        {
        	final String userName =  arg1.getName();
        	AmiUserView userView = amiUserService.findAmiUser(userName);
        	AmiUser amiUser = userView.getAmiUser();
            loadedUser = new User(amiUser.getUser(), amiUser.getPwd(), amiUser.getRole());
        } 
        catch (Exception repositoryProblem) 
        {
            throw new InternalAuthenticationServiceException(repositoryProblem.getMessage(), repositoryProblem);
        }

//        if (loadedUser == null) {
//            throw new InternalAuthenticationServiceException(
//                    "UserDetailsService returned null, which is an interface contract violation");
//        }
        return loadedUser;
	}

   

//    @Override
//    protected AmiUser retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) 
//    		throws AuthenticationException {
//    	AmiUser loadedUser;
//
//        try {
//        	loadedUser = mongo.findById(chuck.getId(), AmiUser.class);
//        } catch (Exception repositoryProblem) {
//            throw new InternalAuthenticationServiceException(repositoryProblem.getMessage(), repositoryProblem);
//        }
//
//        if (loadedUser == null) {
//            throw new InternalAuthenticationServiceException(
//                    "UserDetailsService returned null, which is an interface contract violation");
//        }
//        return loadedUser;
//    }
}
package ami;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class SpringWebMvcInitializer extends
AbstractAnnotationConfigDispatcherServletInitializer{

	@Override
	  protected Class<?>[] getRootConfigClasses() {
	    return new Class[] { Application.class };
	  }

	@Override
	protected Class<?>[] getServletConfigClasses() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String[] getServletMappings() {
		// TODO Auto-generated method stub
		return new String[] { "/" };
	}
}

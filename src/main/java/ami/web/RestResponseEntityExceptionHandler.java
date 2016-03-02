package ami.web;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
@ControllerAdvice
public class RestResponseEntityExceptionHandler   {
 
	@ResponseStatus(HttpStatus.CONFLICT)  // 409
    @ExceptionHandler(DataIntegrityViolationException.class)
    public void handleConflict() {
        // Nothing to do
    }
	
	@ResponseStatus(HttpStatus.NOT_FOUND)  // 409
	@ExceptionHandler(NoDataFoundException.class)
	public void handleNoDataFoundException() {
		// Nothing to do
	}
}

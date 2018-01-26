package vzap.exceptions;
import java.util.Date;
public class ExceptionTest extends Exception
{

	private String errorMessage = null;
	private Date date = null;
	
	public ExceptionTest (String errorMessage)
	{
		date = new Date();
		this.errorMessage = errorMessage + "\n" + date.toString();
	}
	
	public String getErrorMessage()
	{
		return errorMessage;
	}
}




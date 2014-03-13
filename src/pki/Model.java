package pki;

public class Model {
	/**
	 * Validate mail address
	 */
	public Boolean validateEmail( String email ) throws Exception {
	    if ( email != null && email.trim().length() != 0 ) {
	        if ( !email.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" ) ) {
	            throw new Exception( "You must provide a valid email address" );
	        }
	    } else {
	    	throw new Exception( "You must provide a valid email address" );
	    }
	    
	    return true;
	}

	/**
	 * Validate a password
	 */
	public Boolean validatePassword( String motDePasse, String confirmation ) throws Exception{
	    if (motDePasse != null && motDePasse.trim().length() != 0 && confirmation != null && confirmation.trim().length() != 0) {
	        if (!motDePasse.equals(confirmation)) {
	            throw new Exception("Passwords are not the same");
	        } else if (motDePasse.trim().length() < 3) {
	            throw new Exception("Password must be at least of 3 characters");
	        }
	    } else {
	        throw new Exception("You must confirm your password");
	    }
	    
	    return true;
	}

	/**
	 * Validate a name string
	 */
	public Boolean validateName( String nom ) throws Exception {
	    if ( nom != null && nom.trim().length() < 3 ) {
	        throw new Exception( "Name must be at least of 3 characters" );
	    }
	    
	    return true;
	}
}

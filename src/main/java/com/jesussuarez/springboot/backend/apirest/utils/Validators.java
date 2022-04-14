package com.jesussuarez.springboot.backend.apirest.utils;

import java.util.List;
import java.util.regex.Pattern;

public class Validators {
	
	public static boolean validateEmail(String email){
		  return Pattern.matches("[_a-zA-Z1-9]+(\\.[A-Za-z0-9]*)*@[A-Za-z0-9]+\\.[A-Za-z0-9]+(\\.[A-Za-z0-9]*)*", email);
	}
	
	public static boolean isValid(String password, List<String> errorList) {

	   
	    Pattern UpperCasePatten = Pattern.compile("[A-Z ]");
	    Pattern lowerCasePatten = Pattern.compile("[a-z ]");
	    Pattern digitCasePatten = Pattern.compile("[0-9 ]");
	    errorList.clear();

	    boolean flag=true;
	   
	    if (!UpperCasePatten.matcher(password).find()) {
	        errorList.add("Password must have atleast one uppercase character !!");
	        flag=false;
	    }
	    if (!lowerCasePatten.matcher(password).find()) {
	        errorList.add("Password must have atleast one lowercase character !!");
	        flag=false;
	    }
	    if (!digitCasePatten.matcher(password).find()) {
	        errorList.add("Password must have atleast one digit character !!");
	        flag=false;
	    }
	    return flag;
	}

}

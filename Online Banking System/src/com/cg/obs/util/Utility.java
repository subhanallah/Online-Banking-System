package com.cg.obs.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {

	public static boolean validateChoice(int choice) {
		if (choice == 1 || choice == 2 || choice == 3)
			return true;
		return false;
	}
	
	public static boolean validateNoOfPages(int noOfPages) {
		if (noOfPages == 50 || noOfPages == 100 || noOfPages == 200)
			return true;
		return false;
	}

	public static boolean validateMobileNumber(String mobileNumber) {
		// The given argument to compile() method
		// is regular expression. With the help of
		// regular expression we can validate mobile
		// number.
		// 1) Begins with 0 or 91
		// 2) Then contains 7 or 8 or 9.
		// 3) Then contains 9 digits
		Pattern pattern = Pattern.compile("(0/91)?[7-9][0-9]{9}");
		// Pattern class contains matcher() method
		// to find matching between given number
		// and regular expression
		Matcher patternMatcher = pattern.matcher(mobileNumber);
		return (patternMatcher.find() && patternMatcher.group().equals(mobileNumber));

	}

	public static boolean validateEmailId(String emailId) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
				+ "A-Z]{2,7}$";
		Pattern pat = Pattern.compile(emailRegex);
		if (emailId == null)
			return false;
		return pat.matcher(emailId).matches();

	}

	public static boolean validateAccountType(String accountType) {
		if(accountType.toLowerCase().equals("savings")|| accountType.toLowerCase().equals("current"))
			return true;
		else
			return false;
	}

	public static boolean validatePanNumber(String panNumber) {
		/*
		 * The first three letters are sequence of alphabets from AAA to zzz The fourth
		 * character informs about the type of holder of the Card. Each assesses is
		 * unique:` C — Company P — Person H — HUF(Hindu Undivided Family) F — Firm A —
		 * Association of Persons (AOP) T — AOP (Trust) B — Body of Individuals (BOI) L
		 * — Local Authority J — Artificial Judicial Person G — Government The fifth
		 * character of the PAN is the first character (a) of the surname / last name of
		 * the person, in the case of a "Personal" PAN card, where the fourth character
		 * is "P" or (b) of the name of the Entity/ Trust/ Society/ Organization in the
		 * case of Company/ HUF/ Firm/ AOP/ BOI/ Local Authority/ Artificial Jurdical
		 * Person/ Government, where the fourth character is
		 * "C","H","F","A","T","B","L","J","G". 4) The last character is a alphabetic
		 * check digit.
		 */
		String panNumberRegex = "/^[a-zA-Z]{3}[PCHFATBLJG]{1}[a-zA-Z]{1}[0-9]{4}[a-zA-Z]{1}$/";
		Pattern pat = Pattern.compile(panNumberRegex);
		if (panNumber == null)
			return false;
		return pat.matcher(panNumber).matches();
	}

}

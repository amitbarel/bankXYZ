package utils;

import java.util.ArrayList;
import java.util.Iterator;

public class Customer {
	
	private String fName;
	private String lName;
	private String postCode;
	private ArrayList<String> accounts;
	
	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public ArrayList<String> getAccounts() {
		return accounts;
	}

	public void setAccounts(ArrayList<String> accounts) {
		this.accounts = accounts;
	}
	
	public boolean isSubstringExists(String sub) {
		boolean nameExists, accountExists = false;
		nameExists = this.fName.contains(sub) || this.lName.equals(sub) || this.postCode.contains(sub);
		for(String account: this.accounts) {
			if (account.contains(sub)) {
				accountExists = true;
				break;
			}
		}
		return nameExists || accountExists;
		
	}
	
	
	
	
}

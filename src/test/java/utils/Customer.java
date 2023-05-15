package utils;

import java.util.List;

public class Customer {
	
	private String fName;
	private String lName;
	private String postCode;
	private List<String> accounts;
	
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

	public List<String> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<String> accounts) {
		this.accounts = accounts;
	}
	
	@Override
	public String toString() {
		return "Customer [fName=" + fName + ", lName=" + lName + ", postCode=" + postCode + ", accounts=" + accounts
				+ "]";
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

package com.berenberg.library.exceptions;

public class FileNotFoundException extends Exception {
    private static final long serialVersionUID = 6001756005128261221L;
	
	private String code;
	private String message;

	public FileNotFoundException(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}

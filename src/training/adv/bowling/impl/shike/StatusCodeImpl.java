package training.adv.bowling.impl.shike;

import training.adv.bowling.api.StatusCode;

public enum StatusCodeImpl implements StatusCode{
	
	SUCCESS("SUCCESS","1"),FAIL("FAIL","0");
	private String code;
	private String message;
	private StatusCodeImpl(String code, String message) {
		this.code = code;
		this.message = message;
	}
	@Override
	public String getCode() {
		// TODO Auto-generated method stub
		return code;
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return message;
	}

}

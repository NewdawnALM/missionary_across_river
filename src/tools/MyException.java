package tools;

/**
 * 自定义的异常类，错误码和错误信息的简单封装
 */
public class MyException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public int code;		// 错误码
	public String msg;		// 错误信息
	
	public MyException(int code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "MyException [code=" + code + ", msg=" + msg + "]";
	}
	
}

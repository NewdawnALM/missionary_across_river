package tools;

public class TimeValue {
	
	private long milliSecond;
	
	/**
	 * 初始化时获取当前系统时间(millisecond)
	 */
	public TimeValue() {
		super();
		this.milliSecond = System.currentTimeMillis();
	}

	public TimeValue(long milliSecond) {
		super();
		this.milliSecond = milliSecond;
	}
	
	/**
	 * 返回耗时，以毫秒为单位
	 */
	public long costTime() {
		long nowMilliSecond = System.currentTimeMillis();
		return nowMilliSecond - this.milliSecond;
	}
	
	/**
	 * 重置时间为当前时间
	 */
	public void reset() {
		this.milliSecond = System.currentTimeMillis();
	}

	@Override
	public String toString() {
		return "TimeValue [milliSecond=" + milliSecond + "]";
	}
	
}


package header.response;

import header.HeaderField;

public class ContentLengthField extends HeaderField {

	private long length;

	public ContentLengthField(String name, long length) {
		super(name);
		this.length = length;
	}

	public long getLength() {
		return length;
	}

	public void setLength(long length) {
		this.length = length;
	}

	@Override
	public String convertToString() {
		return this.getName() + ":" + Long.toString(this.getLength());
	}

	@Override
	public void convertToClass(String strClass) {
		String[] strAttributes = strClass.split(":");
		
		this.setName(strAttributes[0]);
		this.setLength(Long.parseLong(strAttributes[1]));
	}
}

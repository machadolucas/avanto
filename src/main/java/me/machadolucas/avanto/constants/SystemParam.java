package me.machadolucas.avanto.constants;

public enum SystemParam {
	POST_EDIT_AUTOSAVE_TIME("POST_EDIT_AUTOSAVE_TIME");

	private final String text;

	private SystemParam(String text) {
		this.text = text;
	}

	public String toString() {
		return text;
	}

	public static final boolean contains(String text) {
		for (SystemParam value : values()) {
			if (value.text.equals(text)) {
				return true;
			}
		}
		return false;
	}
}

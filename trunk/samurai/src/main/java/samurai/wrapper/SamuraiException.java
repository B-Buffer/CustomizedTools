package samurai.wrapper;

import com.customized.tools.common.ToolsException;

public class SamuraiException extends ToolsException {

	private static final long serialVersionUID = -2126983493520299110L;

	public SamuraiException( String msg) {
		super("CTS-SAM", msg);
	}

	public SamuraiException( String msg, Throwable t) {
		super("CTS-SAM", msg, t);
	}
}

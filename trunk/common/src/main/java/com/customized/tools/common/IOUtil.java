package com.customized.tools.common;

import java.io.Closeable;
import java.io.IOException;

public class IOUtil {

	public static void closeStream(Closeable stream) {
		try {
			stream.close();
		} catch (IOException e) {
			throw new IllegalArgumentException(stream + " can not close", e);
		}
	}
}

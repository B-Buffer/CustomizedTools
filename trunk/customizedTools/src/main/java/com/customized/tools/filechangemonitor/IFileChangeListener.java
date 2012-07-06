package com.customized.tools.filechangemonitor;

import java.io.File;

public interface IFileChangeListener {

	public abstract void addListener(IFileChangeHandler handler, File file);
}

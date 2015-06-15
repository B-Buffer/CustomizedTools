package com.customized.tools.filechangemonitor;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.List;

public interface IFileChangeHandler {

	public abstract void hander(List<FileChangeEntity> changeList,  PrintStream out, PrintWriter pw);
}

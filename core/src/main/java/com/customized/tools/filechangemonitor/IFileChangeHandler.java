package com.customized.tools.filechangemonitor;

import java.io.PrintWriter;
import java.util.List;

import com.customized.tools.cli.InputConsole;

public interface IFileChangeHandler {

	public abstract void hander(List<FileChangeEntity> changeList, InputConsole console, PrintWriter pw);
}

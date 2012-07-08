package com.customized.tools.filechangemonitor;

import java.io.PrintWriter;
import java.util.List;

import com.customized.tools.startup.ToolsConsole;
import com.customized.tools.startup.ToolsProperties;

public interface IFileChangeHandler {

	public abstract void hander(List<FileChangeEntity> changeList, ToolsConsole console, PrintWriter pw);
}

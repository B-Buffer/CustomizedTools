package com.kylin.tools.filechangemonitor;

import java.util.List;

import com.kylin.tools.startup.ToolsConsole;
import com.kylin.tools.startup.ToolsProperties;

public interface IFileChangeHandler {

	public abstract void hander(List<FileChangeEntity> changeList, ToolsConsole console);
}

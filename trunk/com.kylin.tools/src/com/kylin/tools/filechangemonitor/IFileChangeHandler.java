package com.kylin.tools.filechangemonitor;

import java.util.List;

import com.kylin.tools.startup.ToolConsole;
import com.kylin.tools.startup.ToolProperties;

public interface IFileChangeHandler {

	public abstract void hander(List<FileChangeEntity> changeList, ToolConsole console);
}

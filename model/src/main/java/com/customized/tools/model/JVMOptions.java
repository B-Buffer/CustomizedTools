package com.customized.tools.model;

public class JVMOptions extends Entity{

	private static final long serialVersionUID = -5248904448252690024L;
	
	private String vendor;
	
	private String collector;
	
	private Integer heapSize;
	
	private Boolean enableLargePage;
	
	private Boolean enableGCLogging;
	
	private Boolean enableAggressiveOpts;

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getCollector() {
		return collector;
	}

	public void setCollector(String collector) {
		this.collector = collector;
	}

	public Integer getHeapSize() {
		return heapSize;
	}

	public void setHeapSize(Integer heapSize) {
		this.heapSize = heapSize;
	}

	public Boolean getEnableLargePage() {
		return enableLargePage;
	}

	public void setEnableLargePage(Boolean enableLargePage) {
		this.enableLargePage = enableLargePage;
	}

	public Boolean getEnableGCLogging() {
		return enableGCLogging;
	}

	public void setEnableGCLogging(Boolean enableGCLogging) {
		this.enableGCLogging = enableGCLogging;
	}

	public Boolean getEnableAggressiveOpts() {
		return enableAggressiveOpts;
	}

	public void setEnableAggressiveOpts(Boolean enableAggressiveOpts) {
		this.enableAggressiveOpts = enableAggressiveOpts;
	}

	@Override
	public String toString() {
		
		StringBuffer sb = new StringBuffer();
		
		switch(vendor){
		case "Oracle":
			sb.append(collectorOptionOracle(collector));
			
			if(enableLargePage){
				sb.append(" ");
				sb.append("-XX:+UseLargePages");
			}
			
			if(heapSize != null && heapSize > 0) {
				sb.append(" ");
				sb.append("-Xmx" + heapSize + "M" + " " + "-Xms" + heapSize + "M");
			}
			
			if(enableGCLogging) {
				sb.append(" ");
				sb.append(ORACLE_GC);
			}
			
			if(enableAggressiveOpts){
				sb.append(" ");
				sb.append("-XX:+AggressiveOpts");
			}
			
			break;
		case "OpenJDK":
			sb.append(collectorOptionOpenJDK(collector));
			
			if(enableLargePage){
				sb.append(" ");
				sb.append("-XX:+UseLargePages");
			}
			
			if(heapSize != null && heapSize > 0) {
				sb.append(" ");
				sb.append("-Xmx" + heapSize + "M" + " " + "-Xms" + heapSize + "M");
			}
			
			if(enableGCLogging) {
				sb.append(" ");
				sb.append(OpenJDK_GC);
			}
			
			if(enableAggressiveOpts){
				sb.append(" ");
				sb.append("-XX:+AggressiveOpts");
			}
			
			break;
		case "IBM":
			sb.append(collectorOptionIBM(collector));
			
			if(enableLargePage){
				sb.append(" ");
				sb.append("-Xlp");
			}
			
			if(heapSize != null && heapSize > 0) {
				sb.append(" ");
				sb.append("-Xmx" + heapSize + "M" + " " + "-Xms" + heapSize + "M");
			}
			
			if(enableGCLogging) {
				sb.append(" ");
				sb.append(IBM_GC);
			}
			
			if(enableAggressiveOpts){
				sb.append(" ");
				sb.append("-XX:+AggressiveOpts");
			}
			
			break;
		}
		
		return sb.toString();
	}
	
	private final String ORACLE_GC = "-verbose:gc -Xloggc:gc.log.`date +%Y%m%d%H%M%S` -XX:+PrintGCDetails -XX:+PrintGCTimeStamps";
	
	private final String OpenJDK_GC = "-verbose:gc -Xloggc:gc.log.`date +%Y%m%d%H%M%S` -XX:+PrintGCDetails -XX:+PrintGCTimeStamps";
	
	private final String IBM_GC = "-verbose:gc -Xverbosegclog:gc.log.`date +%Y%m%d%H%M%S`";
	
	private final String ORACLE_CMS = "-server -XX:+DoEscapeAnalysis -XX:+UseCompressedOops -XX:+UseConcMarkSweepGC -XX:+CMSClassUnloadingEnabled -XX:+UseParNewGC -XX:+ExplicitGCInvokesConcurrent -XX:CMSInitiatingOccupancyFraction=80 -XX:CMSIncrementalSafetyFactor=20 -XX:+UseCMSInitiatingOccupancyOnly";
	
	private final String ORACLE_PARALLEL = "-server -XX:+DoEscapeAnalysis -XX:+UseCompressedOops -XX:+UseParallelGC -XX:+UseParallelOldGC";
	
	private final String ORACLE_G1 = "-server -XX:+DoEscapeAnalysis -XX:+UseCompressedOops -XX:+UseG1GC -XX:MaxGCPauseMillis=500";
	
	private final String OpenJDK_CMS = "-server -XX:+DoEscapeAnalysis -XX:+UseCompressedOops -XX:+UseConcMarkSweepGC -XX:+CMSClassUnloadingEnabled -XX:+UseParNewGC -XX:+ExplicitGCInvokesConcurrent -XX:CMSInitiatingOccupancyFraction=80 -XX:CMSIncrementalSafetyFactor=20 -XX:+UseCMSInitiatingOccupancyOnly";
	
	private final String OpenJDK_PARALLEL = "-server -XX:+DoEscapeAnalysis -XX:+UseCompressedOops -XX:+UseParallelGC -XX:+UseParallelOldGC";
	
	private final String OpenJDK_G1 = "-server -XX:+DoEscapeAnalysis -XX:+UseCompressedOops -XX:+UseG1GC -XX:MaxGCPauseMillis=500";
	
	private final String IBM_CMS = "-server -XX:+DoEscapeAnalysis -XX:+UseCompressedOops -XX:+UseConcMarkSweepGC -XX:+CMSClassUnloadingEnabled -XX:+UseParNewGC -XX:+ExplicitGCInvokesConcurrent -XX:CMSInitiatingOccupancyFraction=80 -XX:CMSIncrementalSafetyFactor=20 -XX:+UseCMSInitiatingOccupancyOnly";
	
	private final String IBM_PARALLEL = "-server -XX:+DoEscapeAnalysis -XX:+UseCompressedOops -XX:+UseParallelGC -XX:+UseParallelOldGC";
	
	private final String IBM_G1 = "-server -XX:+DoEscapeAnalysis -XX:+UseCompressedOops -XX:+UseG1GC -XX:MaxGCPauseMillis=500";
	
	private String collectorOptionOracle(String collector) {
		switch(collector){
		case "CMS" :
			return ORACLE_CMS;
		case "Parallel":
			return ORACLE_PARALLEL;
		case "G1":
			return ORACLE_G1;
		}
		return null;
	}
	
	private String collectorOptionOpenJDK(String collector) {
		switch(collector){
		case "CMS" :
			return OpenJDK_CMS;
		case "Parallel":
			return OpenJDK_PARALLEL;
		case "G1":
			return OpenJDK_G1;
		}
		return null;
	}
	
	private String collectorOptionIBM(String collector) {
		switch(collector){
		case "CMS" :
			return IBM_CMS;
		case "Parallel":
			return IBM_PARALLEL;
		case "G1":
			return IBM_G1;
		}
		return null;
	}

}

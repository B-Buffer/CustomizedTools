package com.customized.tools.dbtester.metdata;

import java.util.List;

public class Metdata {
	
	private List<Table> tables;
	
	private List<KeyRecord> pks;
	
	private List<KeyRecord> indexs;
	
	private List<ForeignKey> fks;
	
	private List<Procedure> procs;

	public List<Table> getTables() {
		return tables;
	}

	public void setTables(List<Table> tables) {
		this.tables = tables;
	}

	public List<KeyRecord> getPks() {
		return pks;
	}

	public void setPks(List<KeyRecord> pks) {
		this.pks = pks;
	}

	public List<KeyRecord> getIndexs() {
		return indexs;
	}

	public void setIndexs(List<KeyRecord> indexs) {
		this.indexs = indexs;
	}

	public List<ForeignKey> getFks() {
		return fks;
	}

	public void setFks(List<ForeignKey> fks) {
		this.fks = fks;
	}

	public List<Procedure> getProcs() {
		return procs;
	}

	public void setProcs(List<Procedure> procs) {
		this.procs = procs;
	}

}

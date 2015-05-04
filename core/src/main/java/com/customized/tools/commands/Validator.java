package com.customized.tools.commands;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.jboss.aesh.cl.validator.OptionValidator;
import org.jboss.aesh.cl.validator.OptionValidatorException;
import org.jboss.aesh.console.command.validator.ValidatorInvocation;


public class Validator {
	
	public static class BooleanValidator implements OptionValidator<ValidatorInvocation<Boolean,?>> {

		@Override
		public void validate(ValidatorInvocation<Boolean, ?> validatorInvocation)throws OptionValidatorException {			
			validatorInvocation.getValue();
		}
		
	}
	
	public static class IntegerValidator implements OptionValidator<ValidatorInvocation<Integer,?>> {

		@Override
		public void validate(ValidatorInvocation<Integer, ?> validatorInvocation)throws OptionValidatorException {			
			if(validatorInvocation.getValue() <= 0){
				throw new OptionValidatorException("Validation failed, " + validatorInvocation.getValue() + " not a larger 0 number.");
			}
		}
		
	}
	
	public static class JVMCollectorValidator implements OptionValidator<ValidatorInvocation<String,?>> {
		
		static Set<String> oppSet = new HashSet<>();
		
		static {
			oppSet.add("CMS");
			oppSet.add("Parallel");
			oppSet.add("G1");
		}

		@Override
		public void validate(ValidatorInvocation<String, ?> validatorInvocation) throws OptionValidatorException {
			if(!oppSet.contains(validatorInvocation.getValue())){
				throw new OptionValidatorException("Validation failed, " + validatorInvocation.getValue() + " not a valid Collector, accepted Collectors: " + oppSet);
			}
		}
		
	}
	
	public static class JVMVendorValidator implements OptionValidator<ValidatorInvocation<String,?>> {
		
		static Set<String> oppSet = new HashSet<>();
		
		static {
			oppSet.add("Oracle");
			oppSet.add("OpenJDK");
			oppSet.add("IBM");
		}

		@Override
		public void validate(ValidatorInvocation<String, ?> validatorInvocation) throws OptionValidatorException {
			if(!oppSet.contains(validatorInvocation.getValue())){
				throw new OptionValidatorException("Validation failed, " + validatorInvocation.getValue() + " not supported, supported Vendors: " + oppSet);
			}
		}
		
	}
	
	public static class DBTesterOptionValidator implements OptionValidator<ValidatorInvocation<String,?>> {
		
		static Set<String> oppSet = new HashSet<>();
		
		static {
			oppSet.add("DBConnectionTest");
			oppSet.add("DBMetadataTest");
			oppSet.add("SQLPlus");
		}

		@Override
		public void validate(ValidatorInvocation<String, ?> validatorInvocation)throws OptionValidatorException {
			String opption = validatorInvocation.getValue();
			if(!oppSet.contains(opption)){
				throw new OptionValidatorException("Validation failed, " + validatorInvocation.getValue() + " not a valid opption, accepted opptions: " + oppSet);
			}
		}
		
	}
	
	public static class DBURLValidator implements OptionValidator<ValidatorInvocation<String,?>> {
		
		static Set<String> urlPrefixSet = new HashSet<>();
		
		static {
			urlPrefixSet.add("jdbc:h2");
			urlPrefixSet.add("jdbc:mysql");
			urlPrefixSet.add("jdbc:postgresql");
			urlPrefixSet.add("jdbc:microsoft:sqlserver");
			urlPrefixSet.add("jdbc:oracle");
			
			urlPrefixSet.add("jdbc:jtds:sqlserver");
			urlPrefixSet.add("jdbc:ucanaccess");
			urlPrefixSet.add("jdbc:ingres");
			urlPrefixSet.add("jdbc:presto");
			urlPrefixSet.add("jdbc:phoenix");
			
			urlPrefixSet.add("jdbc:hive2");
			urlPrefixSet.add("jdbc:hive");
			urlPrefixSet.add("jdbc:derby");
			urlPrefixSet.add("jdbc:db2");
			urlPrefixSet.add("jdbc:teiid");
			
			urlPrefixSet.add("jdbc:odbc");
			urlPrefixSet.add("jdbc:Cache");
//			urlPrefixSet.add("");
//			urlPrefixSet.add("");
//			urlPrefixSet.add("");
		}
		

		@Override
		public void validate(ValidatorInvocation<String, ?> validatorInvocation) throws OptionValidatorException {
			String url = validatorInvocation.getValue();
			if(!validate(url)){
				throw new OptionValidatorException("Validation failed, " + validatorInvocation.getValue() + " not a recognized JDBC URL.");
			}
		}


		private boolean validate(String url) {
			
			for(String prefix : urlPrefixSet){
				if(url.startsWith(prefix)){
					return true;
				}	
			}
			return false;
		}
		
	}
	
	public static class DBDriverValidator implements OptionValidator<ValidatorInvocation<String,?>>{
		
		static Set<String> driverSet = new HashSet<>();
		
		static {
			driverSet.add("org.h2.Driver");
			driverSet.add("com.mysql.jdbc.Driver");
			driverSet.add("org.postgresql.Driver");
			driverSet.add("com.microsoft.jdbc.sqlserver.SQLServerDriver");
			driverSet.add("oracle.jdbc.OracleDriver");
			
			driverSet.add("net.sourceforge.jtds.jdbc.Driver");
			driverSet.add("net.ucanaccess.jdbc.UcanaccessDriver");
			driverSet.add("com.intersys.jdbc.CacheDriver");
			driverSet.add("sun.jdbc.odbc.JdbcOdbcDriver");
			driverSet.add("com.ibm.db2.jcc.DB2Driver");
			
			driverSet.add("org.apache.hadoop.hive.jdbc.HiveDriver");
			driverSet.add("org.apache.hive.jdbc.HiveDriver");
			driverSet.add("org.apache.derby.jdbc.ClientDriver");
			driverSet.add("com.facebook.presto.jdbc.PrestoDriver");
			driverSet.add("org.apache.phoenix.jdbc.PhoenixDriver");
			
			driverSet.add("org.teiid.jdbc.TeiidDriver");
			driverSet.add("com.sybase.jdbc2.jdbc.SybDriver");
			driverSet.add("com.sybase.jdbc.SybDriver");
//			driverSet.add("");
//			driverSet.add("");
			
		}

		public void validate(ValidatorInvocation<String, ?> validatorInvocation)throws OptionValidatorException {
			String driver = validatorInvocation.getValue();
			if(!driverSet.contains(driver)){
				throw new OptionValidatorException("Validation failed, " + validatorInvocation.getValue() + " not a recognized driver.");
			}
		}
		
	}
	
	public static class DirectoryValidator implements OptionValidator<ValidatorInvocation<String,?>> {
        @Override
        public void validate(ValidatorInvocation<String,?> validatorInvocation) throws OptionValidatorException {
            if(!new File(validatorInvocation.getValue()).isDirectory())
                throw new OptionValidatorException("Validation failed, " + validatorInvocation.getValue() + " not a exist directory.");
        }
    }
	
	public static class FileValidator implements OptionValidator<ValidatorInvocation<String,?>> {
        @Override
        public void validate(ValidatorInvocation<String,?> validatorInvocation) throws OptionValidatorException {
            if(!new File(validatorInvocation.getValue()).exists())
                throw new OptionValidatorException("Validation failed, " + validatorInvocation.getValue() + " not a exist file.");
        }
    }
	
	public static class InputNotNullValidator implements OptionValidator<ValidatorInvocation<String,?>> {
        @Override
        public void validate(ValidatorInvocation<String,?> validatorInvocation) throws OptionValidatorException {
            if(validatorInvocation.getValue().trim().equals(""))
                throw new OptionValidatorException("InputNotNull validation failed, input can not null");
        }
    }

}

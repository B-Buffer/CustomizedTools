package com.customized.tools.commands;

import java.io.File;

import org.jboss.aesh.cl.validator.OptionValidator;
import org.jboss.aesh.cl.validator.OptionValidatorException;
import org.jboss.aesh.console.AeshContext;
import org.jboss.aesh.console.command.Command;
import org.jboss.aesh.console.command.validator.ValidatorInvocation;


public class Validator {
	
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
	
	public static class FileValidatorInvocation implements ValidatorInvocation<File, Command<?>> {

        private final File file;
        private final Command<?> command;
        private final AeshContext aeshContext;

        public FileValidatorInvocation(File file, Command<?> command, AeshContext aeshContext) {
            this.file = file;
            this.command = command;
            this.aeshContext = aeshContext;
        }

        @Override
        public File getValue() {
            return file;
        }

        @Override
        public Command<?> getCommand() {
            return command;
        }

        @Override
        public AeshContext getAeshContext() {
            return aeshContext;
        }
    }
	
	public static class InputValidatorInvocation implements ValidatorInvocation<String, Command<?>> {
		
		private final String input;
        private final Command<?> command;
        private final AeshContext aeshContext;

        public InputValidatorInvocation(String input, Command<?> command, AeshContext aeshContext) {
            this.input = input;
            this.command = command;
            this.aeshContext = aeshContext;
        }

		@Override
		public String getValue() {
			return input;
		}

		@Override
		public Command<?> getCommand() {
			return command;
		}

		@Override
		public AeshContext getAeshContext() {
			return aeshContext;
		}
		
	}

}

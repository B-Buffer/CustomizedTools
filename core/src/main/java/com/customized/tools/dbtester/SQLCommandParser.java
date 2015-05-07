package com.customized.tools.dbtester;

import java.util.Stack;

/**
 * A simple parser that separates SQLStatements. 
 * 
 * A usage example:
 * <pre>
 * SQLCommandParser parser = new SQLCommandParser();
 * 
 * parser.append("select * from foo; select * from bar \n");
 * parser.append("select * from zoo;");
 * 
 * while(parser.hasNext()){
 *     String sql = parser.next();
 *     ...
 * }
 * </pre>
 * 
 * @author Kylin
 *
 */
public class SQLCommandParser {
	
	private static final byte NEW_STATEMENT   = 0;
    private static final byte START           = 1;  // statement == start
    private static final byte STATEMENT       = 1;
    private static final byte START_COMMENT   = 3;
    private static final byte COMMENT         = 4;
    private static final byte PRE_END_COMMENT = 5;
    private static final byte START_ANSI      = 6;
    private static final byte ENDLINE_COMMENT = 7;
    private static final byte STRING          = 8;
    private static final byte STRING_QUOTE    = 9;
    private static final byte SQLSTRING       = 10;
    private static final byte SQLSTRING_QUOTE = 11;
    private static final byte STATEMENT_QUOTE = 12;  // backslash in statement
    private static final byte FIRST_SEMICOLON_ON_LINE_SEEN  = 13;
    private static final byte POTENTIAL_END_FOUND = 14;
    
    private static class ParseState {
    	
    	private byte         _state;
    	private StringBuffer _inputBuffer;
    	private StringBuffer _commandBuffer;
    	
    	/*
    	 * instead of adding new states, we store the
    	 * fact, that the last 'potential_end_found' was
    	 * a newline here.
    	 */
    	private boolean      _eolineSeen;
    	
    	public ParseState(){
    		_eolineSeen = true; // we start with a new line.
    	    _state = NEW_STATEMENT;
    	    _inputBuffer = new StringBuffer();
    	    _commandBuffer = new StringBuffer();
    	}

		public byte getState() {
			return _state;
		}

		public void setState(byte _state) {
			this._state = _state;
		}

		public boolean hasNewlineSeen() {
			return _eolineSeen;
		}

		public void setNewlineSeen(boolean _eolineSeen) {
			this._eolineSeen = _eolineSeen;
		}

		public StringBuffer getInputBuffer() {
			return _inputBuffer;
		}

		public StringBuffer getCommandBuffer() {
			return _commandBuffer;
		}
    }
    
    private boolean _removeComments;
    private ParseState _currentState;
    private Stack<ParseState> _stateStack;
    
    public SQLCommandParser(){
    	_currentState = new ParseState();
    	_stateStack = new Stack<>();
    	_removeComments = true;
    }
    
    /**
     * push the current state and start with a clean one. Use to parse
     * other files (like includes), and continue then with the old
     * state.
     * like 
     * load foobar.sql ; select * from foobar
     */
    public void push() {
    	_stateStack.push(_currentState);
    	_currentState = new ParseState();
    }
    
    public void pop() {
    	_currentState = _stateStack.pop();
    }
    
    /**
     * add a new line including the '\n' to the input buffer.
     * 
     * @param s sql lines want to execute 
     */
    public void append(String s) {
    	_currentState.getInputBuffer().append(s);
    }
    
    /**
     * discard any input.
     */
    public void discard(){
    	_currentState.getInputBuffer().setLength(0);
    	_currentState.getCommandBuffer().setLength(0);
    	_currentState.setState(NEW_STATEMENT);
    }
    
    /**
     * after having called next(), call cont(), if you are not yet pleased with the result; 
     * the parser should read to the next possible end.
     * 
     */
    public void cont() {
    	_currentState.setState(START);
    }
    
    /**
     * after having called next() and you were pleased with the result call this method to state, 
     * that you consumed it.
     */
    public void consumed() {
    	_currentState.setState(NEW_STATEMENT);
    }
    
    /**
     * returns true, if the parser can find a complete command that either ends with newline or with ';'
     * @return
     * @throws IllegalStateException
     */
    public boolean hasNext() throws IllegalStateException {
    	
    	if (_currentState.getState() == POTENTIAL_END_FOUND) {
    		throw new IllegalStateException ("call cont() or consumed() before hasNext()");
    	}
    	
    	if (_currentState.getInputBuffer().length() == 0){
    		return false;
    	}
    	
    	parse();
    	
    	return (_currentState.getState() == POTENTIAL_END_FOUND);
    }
    
    /**
     *  Validating operations including:
     *   * consume the parser if the command is empty
     *   * trim semicolon(';') and whitespace(' ', '\n', '\t') at the end of command
     *   * trim the slash '/' at the end of command
     */
    private void validation() {
    	
    	for(int i = _currentState.getCommandBuffer().length()- 1 ; i >= 0 ; i--){
    		char c = _currentState.getCommandBuffer().charAt(i);
    		if (c == ';' || Character.isWhitespace(c) || c == '/'){
    			_currentState.getCommandBuffer().deleteCharAt(i);
			} else {
				break;
			}
    	}
    	
    	if(_currentState.getCommandBuffer().length() == 0){
    		consumed();
    		return;
    	}
    	
	}

	public String next() throws IllegalStateException {
    	
    	if (_currentState.getState() != POTENTIAL_END_FOUND) {
    		throw new IllegalStateException ("next() called without hasNext()");
    	}
    	
    	consumed();
    	
    	return _currentState.getCommandBuffer().toString();
    }

    /**
     * parse partial input and set state to POTENTIAL_END_FOUND if we either reached end-of-line or a semicolon.
     */
	private void parse() {
		
		int pos = 0;
		char current;
		byte oldstate = -1;
		
		byte state = _currentState.getState();
		boolean lastEoline = _currentState.hasNewlineSeen();
		
		final StringBuffer input  = _currentState.getInputBuffer();
		final StringBuffer parsed = _currentState.getCommandBuffer();
		
		//remove whitespace prefix
		if (state == NEW_STATEMENT) {
			parsed.setLength(0);
			while (pos < input.length() && Character.isWhitespace (input.charAt(pos))) {
				_currentState.setNewlineSeen(false);
				++pos;
			}
			input.delete(0, pos);
			pos = 0;
		}
		
		if (input.length() == 0) {
			state = POTENTIAL_END_FOUND;
		}
		
		while (state != POTENTIAL_END_FOUND && pos < input.length()) {
			boolean vetoAppend = false;
		    boolean reIterate;
		    current = input.charAt(pos);
		    if (current == '\r'){
		    	current = '\n'; // canonicalize.
		    }
		    if (current == '\n'){
		    	_currentState.setNewlineSeen(true);
		    }
		    
		    do{
		    	reIterate = false;
		    	switch (state){
		    	case NEW_STATEMENT:
		    	case STATEMENT :
		    		if (current == '\n') {
		    			state = POTENTIAL_END_FOUND;
		    			_currentState.setNewlineSeen(true);
		    		} else if (_removeComments && lastEoline && current== ';' ){
		    			state = FIRST_SEMICOLON_ON_LINE_SEEN;
		    		} else if (!lastEoline && current==';') {
		    			_currentState.setNewlineSeen(false);
		    			state = POTENTIAL_END_FOUND;
		    		} else if (_removeComments && current == '/') {
		    			state = START_COMMENT;
		    		} else if (_removeComments && lastEoline && current == '#') {
		    			state = ENDLINE_COMMENT;
		    		} else if (current == '"') {
		    			state = STRING;
		    		} else if (current == '\'') {
		    			state = SQLSTRING;
		    		} else if (current == '-') {
		    			state = START_ANSI;
		    		} else if (current == '\\') {
		    			state = STATEMENT_QUOTE;
		    		}
		    		break;
		    	case STATEMENT_QUOTE:
		    		state = STATEMENT;
		    		break;
		    	case FIRST_SEMICOLON_ON_LINE_SEEN:
		    		if (current == ';'){
		    			state = ENDLINE_COMMENT;
		    		} else {
		    			input.delete(0, pos);
		    			state = STATEMENT;
		    			pos --;
//		    			state = POTENTIAL_END_FOUND;
//		    			current = ';';
//		    			--pos;
		    		}
		    		break;
		    	case START_COMMENT:
		    		if (current == '*') {
		    			state = COMMENT;
		    		} else {
		    			parsed.append ('/'); 
		    			state = STATEMENT; 
		    			reIterate = true;
		    		}
		    		break;
		    	case COMMENT:
		    		if (current == '*') {
		    			state = PRE_END_COMMENT;
		    		}
		    		break;
		    	case PRE_END_COMMENT:
		    		if (current == '/') {
		    			state = STATEMENT;
		    		} else if (current == '*') {
		    			state = PRE_END_COMMENT;
		    		} else {
		    			state = COMMENT;
		    		}
		    		break;
		    	case START_ANSI:
		    		if (current == '-'){
		    			state = ENDLINE_COMMENT;
		    		} else {
		    			parsed.append('-'); 
		    			state = STATEMENT;
		    			reIterate = true;
		    		}
		    		break;
		    	case ENDLINE_COMMENT:
		    		if (current == '\n'){
		    			state = POTENTIAL_END_FOUND;
		    		}
				    break;
		    	case STRING: 
		    		if (current == '\\') {
		    			state = STRING_QUOTE;
		    		} else if (current == '"') {
		    			state = STATEMENT;
		    		}
				    break;
		    	case SQLSTRING:
				    if (current == '\\') {
				    	state = SQLSTRING_QUOTE;
				    }
				    if (current == '\'') {
				    	state = STATEMENT;
				    }
				    break;
		    	case STRING_QUOTE:
				    vetoAppend = (current == '\n'); // line continuation
		                    if (current == 'n') current = '\n';
		                    else if (current == 'r') current = '\r';
		                    else if (current == 't') current = '\t';
		                    else if (current == '\\') /* ignore quoted backslash */ ;
		                    else if (current != '\n' && current != '"') {
		                        // if we do not recognize the escape sequence,
		                        // pass it through.
		                        parsed.append("\\");
		                    }
				    state = STRING;
				    break;
		    	case SQLSTRING_QUOTE:
				    vetoAppend = (current == '\n'); // line continuation
		                    // convert a "\'" to a correct SQL-Quote "''"
				    if (current == '\'') parsed.append("'");
		                    else if (current == 'n') current = '\n';
		                    else if (current == 'r') current = '\r';
		                    else if (current == 't') current = '\t';
		                    else if (current == '\\') /* ignore quoted backslash */ ;
		                    else if (current != '\n') {
		                        // if we do not recognize the escape sequence,
		                        // pass it through.
		                        parsed.append("\\");
		                    }
				    state = SQLSTRING;
				    break;
		    	}
		    	
		    } while(reIterate);
		    
		    if (!vetoAppend && ((state == STATEMENT && oldstate != PRE_END_COMMENT) || state == NEW_STATEMENT || state == STATEMENT_QUOTE || state == STRING || state == SQLSTRING || state == POTENTIAL_END_FOUND)) {
		    	parsed.append(current);
		    }
		    
		    oldstate = state;
		    pos++;
		    
		    lastEoline &= Character.isWhitespace(current);
		}
		input.delete(0, pos);
		_currentState.setState(state);
		
		validation();
	}

}

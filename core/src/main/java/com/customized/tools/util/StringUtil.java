package com.customized.tools.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.regex.Pattern;

import com.customized.tools.CorePlugin;


/**
 * This is a common place to put String utility methods.
 */
public final class StringUtil {

    public interface Constants {
        char CARRIAGE_RETURN_CHAR = '\r';
        char LINE_FEED_CHAR       = '\n';
        char NEW_LINE_CHAR        = LINE_FEED_CHAR;
        char SPACE_CHAR           = ' ';
        char DOT_CHAR           = '.';
        char TAB_CHAR             = '\t';
        
        String CARRIAGE_RETURN = String.valueOf(CARRIAGE_RETURN_CHAR);
        String EMPTY_STRING    = ""; //$NON-NLS-1$
        String DBL_SPACE       = "  "; //$NON-NLS-1$
        String LINE_FEED       = String.valueOf(LINE_FEED_CHAR);
        String NEW_LINE        = String.valueOf(NEW_LINE_CHAR);
        String SPACE           = String.valueOf(SPACE_CHAR);
        String DOT             = String.valueOf(DOT_CHAR);
        String TAB             = String.valueOf(TAB_CHAR);

        String[] EMPTY_STRING_ARRAY = new String[0];

        // all patterns below copied from Eclipse's PatternConstructor class.
        final Pattern PATTERN_BACK_SLASH = Pattern.compile("\\\\"); //$NON-NLS-1$
        final Pattern PATTERN_QUESTION = Pattern.compile("\\?"); //$NON-NLS-1$
        final Pattern PATTERN_STAR = Pattern.compile("\\*"); //$NON-NLS-1$
    }

    /**
     * The String "'"
     */
    public static final String SINGLE_QUOTE = "'"; //$NON-NLS-1$

    /**
     * The name of the System property that specifies the string that should be used to separate
     * lines.  This property is a standard environment property that is usually set automatically.
     */
    public static final String LINE_SEPARATOR_PROPERTY_NAME = "line.separator"; //$NON-NLS-1$

    /**
     * The String that should be used to separate lines; defaults to
     * {@link #NEW_LINE}
     */
    public static final String LINE_SEPARATOR = System.getProperty(LINE_SEPARATOR_PROPERTY_NAME, Constants.NEW_LINE);

    public static final Comparator<String> CASE_INSENSITIVE_ORDER = String.CASE_INSENSITIVE_ORDER;
    
    public static final Comparator<String> NULL_SAFE_CASE_INSENSITIVE_ORDER = new Comparator<String>() {
		@Override
		public int compare(String o1, String o2) {
			if (o1 == null) {
				if (o2 == null) {
					return 0;
				}
				return -1;
			}
			if (o2 == null) {
				return 1;
			}
			return String.CASE_INSENSITIVE_ORDER.compare(o1, o2);
		}
	};

    public static final Comparator<String> CASE_SENSITIVE_ORDER = new Comparator<String>() {
        /** 
         * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
         * @since 4.2
         */
        public int compare(String o1, String o2) {
            if ( o1 == o2 ) {
                return 0;
            }
            return o1.compareTo(o2);
        }
    };
    
    public static String getLineSeparator() {
        return LINE_SEPARATOR;
    }

	/**
	 * Join string pieces and separate with a delimiter.  Similar to the perl function of
	 * the same name.  If strings or delimiter are null, null is returned.  Otherwise, at
	 * least an empty string will be returned.	 
	 * @see #split
	 *
	 * @param strings String pieces to join
	 * @param delimiter Delimiter to put between string pieces
	 * @return One merged string
	 */
	public static String join(Collection<String> strings, String delimiter) {
		if(strings == null || delimiter == null) {
			return null;
		}

		StringBuffer str = new StringBuffer();

		Iterator<String> iter = strings.iterator();
		while (iter.hasNext()) {            
			str.append(iter.next());
			if (iter.hasNext()) {
				str.append(delimiter);
			}
		}
		
		return str.toString();
	}
    
    /**
     * Return a stringified version of the array.
     * @param array the array
     * @param delim the delimiter to use between array components
     * @return the string form of the array
     */
    public static String toString( final Object[] array, final String delim ) {
    	return toString(array, delim, true);
    }
    
    /**
     * Return a stringified version of the array.
     * @param array the array
     * @param delim the delimiter to use between array components
     * @return the string form of the array
     */
    public static String toString( final Object[] array, final String delim, boolean includeBrackets) {
        if ( array == null ) {
            return ""; //$NON-NLS-1$
        }
        final StringBuffer sb = new StringBuffer();
        if (includeBrackets) {
        	sb.append('[');
        }
        for (int i = 0; i < array.length; ++i) {
            if ( i != 0 ) {
                sb.append(delim);
            }
            sb.append(array[i]);
        }
        if (includeBrackets) {
        	sb.append(']');
        }
        return sb.toString();
    }

    
    /**
     * Return a stringified version of the array, using a ',' as a delimiter
     * @param array the array
     * @return the string form of the array
     * @see #toString(Object[], String)
     */
    public static String toString( final Object[] array ) {
        return toString(array, ",", true); //$NON-NLS-1$
    }
    
	/**
	 * Split a string into pieces based on delimiters.  Similar to the perl function of
	 * the same name.  The delimiters are not included in the returned strings.
	 * @see #join
	 *
	 * @param str Full string
	 * @param splitter Characters to split on
	 * @return List of String pieces from full string
	 */
    public static List<String> split(String str, String splitter) {
        StringTokenizer tokens = new StringTokenizer(str, splitter);
        ArrayList<String> l = new ArrayList<String>(tokens.countTokens());
        while(tokens.hasMoreTokens()) {
            l.add(tokens.nextToken());
        }
        return l;
    }
    
	/*
	 * Replace a single occurrence of the search string with the replace string
	 * in the source string. If any of the strings is null or the search string
	 * is zero length, the source string is returned.
	 * @param source the source string whose contents will be altered
	 * @param search the string to search for in source
	 * @param replace the string to substitute for search if present
	 * @return source string with the *first* occurrence of the search string
	 * replaced with the replace string
	 */
	public static String replace(String source, String search, String replace) {
	    if (source != null && search != null && search.length() > 0 && replace != null) {
	        int start = source.indexOf(search);
            if (start > -1) {
                return new StringBuffer(source).replace(start, start + search.length(), replace).toString();
	        }
	    }
	    return source;    
	}

	/*
	 * Replace all occurrences of the search string with the replace string
	 * in the source string. If any of the strings is null or the search string
	 * is zero length, the source string is returned.
	 * @param source the source string whose contents will be altered
	 * @param search the string to search for in source
	 * @param replace the string to substitute for search if present
	 * @return source string with *all* occurrences of the search string
	 * replaced with the replace string
	 */
	public static String replaceAll(String source, String search, String replace) {
	    if (source == null || search == null || search.length() == 0 || replace == null) {
	    	return source;
	    }
        int start = source.indexOf(search);
        if (start > -1) {
	        StringBuffer newString = new StringBuffer(source);
	        while (start > -1) {
	            int end = start + search.length();
	            newString.replace(start, end, replace);
	            start = newString.indexOf(search, start + replace.length());
	        }
	        return newString.toString();
        }
	    return source;    
	}

    /**
     * Simple static method to tuncate Strings to given length.
     * @param in the string that may need tuncating.
     * @param len the lenght that the string should be truncated to.
     * @return a new String containing chars with length <= len or <code>null</code>
     * if input String is <code>null</code>.
     */
    public static String truncString(String in, int len) {
        String out = in;
        if ( in != null && len > 0 && in.length() > len ) {
            out = in.substring(0, len);
        }
        return out;
    }

    /**
     * Simple utility method to wrap a string by inserting line separators creating
     * multiple lines each with length no greater than the user specified maximum.
     * The method parses the given string into tokens using a space delimiter then
     * reassembling the tokens into the resulting string while inserting separators
     * when required.  If the number of characters in a single token is greater
     * than the specified maximum, the token will not be split but instead the
     * maximum will be exceeded.
     * @param str the string that may need tuncating.
     * @param maxCharPerLine the max number of characters per line
     * @return a new String containing line separators or the original string
     * if its length was less than the maximum.
     */
    public static String wrap(String str, int maxCharPerLine) {
        int strLength = str.length();
        if (strLength > maxCharPerLine) {
            StringBuffer sb = new StringBuffer(str.length()+(strLength/maxCharPerLine)+1);
            strLength = 0;
            List<String> tokens = StringUtil.split(str,Constants.SPACE);
            Iterator<String> itr = tokens.iterator();
            while (itr.hasNext()) {
                String token = itr.next();
                if ( strLength+token.length() > maxCharPerLine ) {
//                    sb.append(getLineSeparator());
                    sb.append(Constants.NEW_LINE);
                    strLength = 0;
                }
                sb.append(token);
                sb.append(Constants.SPACE);
                strLength += token.length()+1;
            }
            return sb.toString();
        }
        return str;
    }

	/**
	 * Return the tokens in a string in a list. This is particularly
	 * helpful if the tokens need to be processed in reverse order. In that case,
	 * a list iterator can be acquired from the list for reverse order traversal.
	 *
	 * @param str String to be tokenized
	 * @param delimiter Characters which are delimit tokens
	 * @return List of string tokens contained in the tokenized string
	 */
	public static List<String> getTokens(String str, String delimiter) {
		ArrayList<String> l = new ArrayList<String>();
		StringTokenizer tokens = new StringTokenizer(str, delimiter);
		while(tokens.hasMoreTokens()) {
			l.add(tokens.nextToken());
		}
		return l;
    }
    
	/**
	 * Return the last token in the string.
	 *
	 * @param str String to be tokenized
	 * @param delimiter Characters which are delimit tokens
	 * @return the last token contained in the tokenized string
	 */
	public static String getLastToken(String str, String delimiter) {
        if (str == null) {
            return Constants.EMPTY_STRING;
        }
        int beginIndex = 0;
        if (str.lastIndexOf(delimiter) > 0) {
            beginIndex = str.lastIndexOf(delimiter)+1;
        }
        return str.substring(beginIndex,str.length());
    }

	
	/**
	 * Return the first token in the string.
	 *
	 * @param str String to be tokenized
	 * @param delimiter Characters which are delimit tokens
	 * @return the first token contained in the tokenized string
	 */
	public static String getFirstToken(String str, String delimiter) {
        if (str == null) {
            return Constants.EMPTY_STRING;
        }
        int endIndex = str.indexOf(delimiter);
        if (endIndex < 0) {
        	endIndex = str.length();
        }
        return str.substring(0,endIndex);
    }
	
	/**
	 * Compute a displayable form of the specified string.  This algorithm
     * attempts to create a string that contains words that begin with uppercase
     * characters and that are separated by a single space.  For example,
     * the following are the outputs of some sample inputs:
     * <li>"aName" is converted to "A Name"</li>
     * <li>"Name" is converted to "Name"</li>
     * <li>"NAME" is converted to "NAME"</li>
     * <li>"theName" is converted to "The Name"</li>
     * <li>"theBIGName" is converted to "The BIG Name"</li>
     * <li>"the BIG Name" is converted to "The BIG Name"</li>
     * <li>"the big Name" is converted to "The Big Name"</li>
     * <li>"theBIG" is converted to "The BIG"</li>
     * <li>"SQLIndex" is converted to "SQL Index"</li>
     * <li>"SQLIndexT" is converted to "SQL Index T"</li>
     * <li>"SQLIndex T" is converted to "SQL Index T"</li>
     * <li>"SQLIndex t" is converted to "SQL Index T"</li>
     * <p>
     * An exception is "MetaMatrix", which is always treated as a single word
     * </p>
	 *
	 * @param str String to be converted; may be null
	 * @param defaultValue the default result if the input is either null or zero-length.
     * @return the displayable form of <code>str</code>, or the default value if
     * <code>str</code> is either null or zero-length.
	 */
	public static String computeDisplayableForm(String str, String defaultValue) {
        if ( str == null || str.length() == 0 ) {
            return defaultValue;
        }

        StringBuffer newName = new StringBuffer(str);
        boolean previousCharUppercase = false;

        // If the first character is lowercase, replace it with the uppercase ...
        char prevChar = newName.charAt(0);
        if ( Character.isLowerCase(prevChar) ) {
            newName.setCharAt(0, Character.toUpperCase(prevChar) );
            previousCharUppercase = true;
        }

        if ( newName.length() > 1 ) {
            char nextChar;
            char currentChar;
            boolean currentCharUppercase;
            boolean nextCharUppercase;
            for (int i=1; i!=newName.length(); ++i ) {
                prevChar = newName.charAt(i-1);
                currentChar = newName.charAt(i);
                previousCharUppercase = Character.isUpperCase(prevChar);
                currentCharUppercase = Character.isUpperCase(currentChar);
                // In the case where we're not at the end of the string ...
                if ( i!=newName.length()-1 ) {
                    nextChar = newName.charAt(i+1);
                    nextCharUppercase = Character.isUpperCase(nextChar);
                } else {
                    nextCharUppercase = false;
                    nextChar = ' ';
                }

                // If the previous character is a space, capitalize the current character
                if ( prevChar == ' ' ) {
                    newName.setCharAt(i, Character.toUpperCase(currentChar) );
                    // do nothing
                }
                // Otherwise, if the current character is already uppercase ...
                else if ( currentCharUppercase ) {
                    // ... and the previous character is not uppercase, then insert
                    if ( !previousCharUppercase ) {
                        // ... and this is not the 'M' of 'MetaMatrix' ...
                        if ( currentChar != 'M' || i < 4 || (!newName.substring(i-4).startsWith(CorePlugin.Util.getString("StringUtil.Displayable"))) ) { //$NON-NLS-1$
                            newName.insert(i, ' ' );
                            ++i;        // skip, since we just move the character back one position
                        }
                    }
                    // ... and the previous character is uppercase ...
                    else {
                        // ... but the next character neither uppercase or a space ...
                        if ( !nextCharUppercase && nextChar != ' ' ) {
                            newName.insert(i, ' ' );
                            ++i;        // skip, since we just move the character back one position
                        }
                    }
                }
            }
        }

        return newName.toString();
    }

    public static String computePluralForm(String str) {
        return computePluralForm(str, Constants.EMPTY_STRING);
    }

    public static String computePluralForm(String str, String defaultValue ) {
        if ( str == null || str.length() == 0 ) {
            return defaultValue;
        }
        String result = str;
        if ( result.endsWith("es") ) { //$NON-NLS-1$
        	// do nothing 
        } else if ( result.endsWith("ss") || //$NON-NLS-1$
             result.endsWith("x")  || //$NON-NLS-1$
             result.endsWith("ch") || //$NON-NLS-1$
             result.endsWith("sh") ) { //$NON-NLS-1$
            result = result + "es"; //$NON-NLS-1$
        } else if ( result.endsWith("y") && ! ( //$NON-NLS-1$
                    result.endsWith("ay") || //$NON-NLS-1$
                    result.endsWith("ey") || //$NON-NLS-1$
                    result.endsWith("iy") || //$NON-NLS-1$
                    result.endsWith("oy") || //$NON-NLS-1$
                    result.endsWith("uy") || //$NON-NLS-1$
                    result.equalsIgnoreCase("any") ) ) { //$NON-NLS-1$
            result = result.substring(0, result.length()-1) + "ies"; //$NON-NLS-1$
        } else {
            result += "s"; //$NON-NLS-1$
        }
        return result;
    }
    
    public static String getStackTrace( final Throwable t ) {
        final ByteArrayOutputStream bas = new ByteArrayOutputStream();
        final PrintWriter pw = new PrintWriter(bas);
        t.printStackTrace(pw);
        pw.close();
        return bas.toString();
    }

    /**
     * Returns whether the specified text represents a boolean value, i.e., whether it equals "true" or "false"
     * (case-insensitive).
	 * @since 4.0
	 */
	public static boolean isBoolean(final String text) {
        return (Boolean.TRUE.toString().equalsIgnoreCase(text)  ||  Boolean.FALSE.toString().equalsIgnoreCase(text));
	}
    
    /**<p>
     * Returns whether the specified text is either empty or null.
	 * </p>
     * @param text The text to check; may be null;
     * @return True if the specified text is either empty or null.
	 * @since 4.0
	 */
	public static boolean isEmpty(final String text) {
        return (text == null  ||  text.length() == 0);
	}
    
    /**
     * Returns the index within this string of the first occurrence of the
     * specified substring. The integer returned is the smallest value 
     * <i>k</i> such that:
     * <blockquote><pre>
     * this.startsWith(str, <i>k</i>)
     * </pre></blockquote>
     * is <code>true</code>.
     *
     * @param   text  any string.
     * @param   str   any string.
     * @return  if the str argument occurs as a substring within text,
     *          then the index of the first character of the first
     *          such substring is returned; if it does not occur as a
     *          substring, <code>-1</code> is returned.  If the text or 
     *          str argument is null or empty then <code>-1</code> is returned.
     */
    public static int indexOfIgnoreCase(final String text, final String str) {
        if (isEmpty(text)) {
            return -1;
        }
        if (isEmpty(str)) {
            return -1;
        }
        final String lowerText = text.toLowerCase();
        final String lowerStr  = str.toLowerCase();
        return lowerText.indexOf(lowerStr);
    }
    
    /**
     * Tests if the string starts with the specified prefix.
     *
     * @param   text     the string to test.
     * @param   prefix   the prefix.
     * @return  <code>true</code> if the character sequence represented by the
     *          argument is a prefix of the character sequence represented by
     *          this string; <code>false</code> otherwise.      
     *          Note also that <code>true</code> will be returned if the 
     *          prefix is an empty string or is equal to the text 
     *          <code>String</code> object as determined by the 
     *          {@link #equals(Object)} method. If the text or 
     *          prefix argument is null <code>false</code> is returned.
     * @since   JDK1. 0
     */
    public static boolean startsWithIgnoreCase(final String text, final String prefix) {
        if (text == null || prefix == null) {
            return false;
        }
        return text.regionMatches(true, 0, prefix, 0, prefix.length());
    }
    
    /**
     * Tests if the string ends with the specified suffix.
     *
     * @param   text     the string to test.
     * @param   suffix   the suffix.
     * @return  <code>true</code> if the character sequence represented by the
     *          argument is a suffix of the character sequence represented by
     *          this object; <code>false</code> otherwise. Note that the 
     *          result will be <code>true</code> if the suffix is the 
     *          empty string or is equal to this <code>String</code> object 
     *          as determined by the {@link #equals(Object)} method. If the text or 
     *          suffix argument is null <code>false</code> is returned.
     */
    public static boolean endsWithIgnoreCase(final String text, final String suffix) {
    	if (text == null || suffix == null) {
            return false;
        }
        return text.regionMatches(true, text.length() - suffix.length(), suffix, 0, suffix.length());
    }
 
    /**
     * Determine if the string passed in has all digits as its contents
     * @param str
     * @return true if digits; false otherwise
     */
    public static boolean isDigits(String str) {
        for(int i=0; i<str.length(); i++) {
            if(!StringUtil.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    //============================================================================================================================
    // Constructors
    
    /**<p>
     * Prevents instantiation.
     * </p>
     * @since 4.0
     */
    private StringUtil() {
    }

    /*
     * Converts user string to regular expres '*' and '?' to regEx variables.
     * copied from eclipse's PatternConstructor
     */
    static String asRegEx(String pattern) {
        // Replace \ with \\, * with .* and ? with .
        // Quote remaining characters
        String result1 = Constants.PATTERN_BACK_SLASH.matcher(pattern).replaceAll(
                "\\\\E\\\\\\\\\\\\Q"); //$NON-NLS-1$
        String result2 = Constants.PATTERN_STAR.matcher(result1).replaceAll(
                "\\\\E.*\\\\Q"); //$NON-NLS-1$
        String result3 = Constants.PATTERN_QUESTION.matcher(result2).replaceAll(
                "\\\\E.\\\\Q"); //$NON-NLS-1$
        return "\\Q" + result3 + "\\E"; //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * Creates a regular expression pattern from the pattern string (which is
     * our old 'StringMatcher' format).  Copied from Eclipse's PatternConstructor class.
     * 
     * @param pattern
     *            The search pattern
     * @param isCaseSensitive
     *            Set to <code>true</code> to create a case insensitve pattern
     * @return The created pattern
     */
    public static Pattern createPattern(String pattern, boolean isCaseSensitive) {
        if (isCaseSensitive)
            return Pattern.compile(asRegEx(pattern));
        return Pattern.compile(asRegEx(pattern), Pattern.CASE_INSENSITIVE
                | Pattern.UNICODE_CASE);
    }

    /**
     * If input == null OR input.length() < desiredLength, pad to desiredLength with spaces.  
     * If input.length() > desiredLength, chop at desiredLength.
     * @param input Input text
     * @param desiredLength Desired length
     * @return
     * @since 5.0
     */    
    public static String toFixedLength(String input, int desiredLength) {
        if(input == null) {
            input = ""; //$NON-NLS-1$
        }
        
        if(input.length() == desiredLength) {
            return input;
        }
        
        if(input.length() < desiredLength) {
            StringBuffer str = new StringBuffer(input);
            int needSpaces = desiredLength - input.length();
            for(int i=0; i<needSpaces; i++) {
                str.append(' ');
            }
            return str.toString();
        }
        
        // Else too long - chop
        return input.substring(0, desiredLength);
    }
    
    
    public static boolean isLetter(char c) {
        return isBasicLatinLetter(c) || Character.isLetter(c);
    }
    public static boolean isDigit(char c) {
        return isBasicLatinDigit(c) || Character.isDigit(c);
    }
    public static boolean isLetterOrDigit(char c) {
        return isBasicLatinLetter(c) || isBasicLatinDigit(c) || Character.isLetterOrDigit(c);
    }
    public static boolean isValid(String str) {
    	return (!(str == null || str.trim().length() == 0));
    }
    
    /**
     * Create a valid filename from the given String.
     * 
     * @param str The String to convert to a valid filename.
     * @param defaultName The default name to use if only special characters exist.
     * @return String A valid filename.
     */
    public static String createFileName(String str) {

      /** Replace some special chars */
      str = str.replaceAll(" \\| ", "_"); //$NON-NLS-1$ //$NON-NLS-2$
      str = str.replaceAll(">", "_"); //$NON-NLS-1$ //$NON-NLS-2$
      str = str.replaceAll(": ", "_"); //$NON-NLS-1$ //$NON-NLS-2$
      str = str.replaceAll(" ", "_"); //$NON-NLS-1$ //$NON-NLS-2$
      str = str.replaceAll("\\?", "_"); //$NON-NLS-1$ //$NON-NLS-2$
      str = str.replaceAll("/", "_"); //$NON-NLS-1$ //$NON-NLS-2$
      
      /** If filename only contains of special chars */
      if (str.matches("[_]+")) //$NON-NLS-1$
        str = "file"; //$NON-NLS-1$

      return str;
    }
    
    
    /**
     * Make the first letter uppercase 
     * @param str
     * @return The string with the first letter being changed to uppercase
     * @since 5.5
     */
    public static String firstLetterUppercase(String str) {
        if(str == null || str.length() == 0) {
            return null;
        }
        if(str.length() == 1) {
            return str.toUpperCase();
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
    
    private static boolean isBasicLatinLetter(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }
    private static boolean isBasicLatinDigit(char c) {
        return c >= '0' && c <= '9';
    }
    
    /**
     * Convert the given value to specified type. 
     * @param value
     * @param type
     * @return
     */
    @SuppressWarnings("unchecked")
	public static <T> T valueOf(String value, Class type){
    	if (value == null) {
    		return null;
    	}
    	if(type == String.class) {
    		return (T) value;
    	}
    	else if(type == Boolean.class || type == Boolean.TYPE) {
    		return (T) Boolean.valueOf(value);
    	}
    	else if (type == Integer.class || type == Integer.TYPE) {
    		return (T) Integer.decode(value);
    	}
    	else if (type == Float.class || type == Float.TYPE) {
    		return (T) Float.valueOf(value);
    	}
    	else if (type == Double.class || type == Double.TYPE) {
    		return (T) Double.valueOf(value);
    	}
    	else if (type == Long.class || type == Long.TYPE) {
    		return (T) Long.decode(value);
    	}
    	else if (type == Short.class || type == Short.TYPE) {
    		return (T) Short.decode(value);
    	}
    	else if (type.isAssignableFrom(List.class)) {
    		return (T)new ArrayList<String>(Arrays.asList(value.split(","))); //$NON-NLS-1$
    	}
    	else if (type.isAssignableFrom(Set.class)) {
    		return (T)new HashSet<String>(Arrays.asList(value.split(","))); //$NON-NLS-1$
    	}
    	else if (type.isArray()) {
    		String[] values = value.split(","); //$NON-NLS-1$
    		Object array = Array.newInstance(type.getComponentType(), values.length);
    		for (int i = 0; i < values.length; i++) {
				Array.set(array, i, valueOf(values[i], type.getComponentType()));
			}
    		return (T)array;
    	}
    	else if (type == Void.class) {
    		return null;
    	}
    	else if (type.isEnum()) {
    		return (T)Enum.valueOf(type, value);
    	}
    	else if (type == URL.class) {
    		try {
				return (T)new URL(value);
			} catch (MalformedURLException e) {
				// fall through and end up in error
			}
    	}
    	else if (type.isAssignableFrom(Map.class)) {
    		List<String> l = Arrays.asList(value.split(",")); //$NON-NLS-1$
    		Map m = new HashMap<String, String>();
    		for(String key: l) {
    			int index = key.indexOf('=');
    			if (index != -1) {
    				m.put(key.substring(0, index), key.substring(index+1));
    			}
    		}
    		return (T)m;
    	}

    	throw new IllegalArgumentException("Conversion from String to "+ type.getName() + " is not supported"); //$NON-NLS-1$ //$NON-NLS-2$
    }

	public static String[] getLines(final String value) {
	    StringReader stringReader = new StringReader(value);
	    BufferedReader reader = new BufferedReader(stringReader);
	    ArrayList<String> result = new ArrayList<String>();
	    try {
	        String line = reader.readLine();
	        while (line != null) {
	            result.add(line);
	            line = reader.readLine();
	        }
	    } catch (IOException e) {
	          throw new RuntimeException(e);
	    }
	    return result.toArray(new String[result.size()]);
	}
	
	public static boolean equalsIgnoreCase(String s1, String s2) {
		if (s1 != null) {
			return s1.equalsIgnoreCase(s2);
		} else if (s2 != null) {
			return false;
		}
		return true;
	}
	
	public static <T extends Enum<T>> T caseInsensitiveValueOf(Class<T> enumType, String name) {
		try {
			return Enum.valueOf(enumType, name);
		} catch (IllegalArgumentException e) {
			T[] vals = enumType.getEnumConstants();
			for (T t : vals) {
				if (name.equalsIgnoreCase(t.name())) {
					return t;
				}
			}
			throw e;
		}
	}
	
	public static List<String> tokenize(String str, char delim) {
		ArrayList<String> result = new ArrayList<String>();
		StringBuilder current = new StringBuilder();
		boolean escaped = false;
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c == delim) {
				if (escaped) {
					current.append(c);
					escaped = false;
				} else {
					escaped = true;
				}
			} else {
				if (escaped && current.length() > 0) {
					result.add(current.toString());
					current.setLength(0);
					escaped = false;
				}
				current.append(c);
			}
		}
		if (current.length()>0) {
			result.add(current.toString());
		}
		return result;
	}
	
	/**
	 * Unescape the given string
	 * @param string
	 * @param quoteChar
	 * @param useAsciiExcapes
	 * @param sb a scratch buffer to use
	 * @return
	 */
    public static String unescape(CharSequence string, int quoteChar, boolean useAsciiEscapes, StringBuilder sb) {
    	boolean escaped = false;
    	
    	for (int i = 0; i < string.length(); i++) {
    		char c = string.charAt(i);
    		if (escaped) {
	    		switch (c) {
	    		case 'b':
	    			sb.append('\b');
	    			break;
	    		case 't':
	    			sb.append('\t');
	    			break;
	    		case 'n':
	    			sb.append('\n');
	    			break;
	    		case 'f':
	    			sb.append('\f');
	    			break;
	    		case 'r':
	    			sb.append('\r');
	    			break;
	    		case 'u':
					i = parseNumericValue(string, sb, i, 0, 4, 4);
					//TODO: this should probably be strict about needing 4 digits
	    			break;
    			default:
    				if (c == quoteChar) {
    					sb.append(quoteChar);
    				} else if (useAsciiEscapes) {
	    				int value = Character.digit(c, 8);
						if (value == -1) {
							sb.append(c);
						} else {
							int possibleDigits = value < 3 ? 2:1;
							int radixExp = 3;
	    					i = parseNumericValue(string, sb, i, value, possibleDigits, radixExp);
	    				}
    				}
	    		}
	    		escaped = false;
    		} else {
    			if (c == '\\') {
    				escaped = true;
    			} else if (c == quoteChar) {
    				break;
    			} else {
					sb.append(c);
    			}
    		}
    	}
    	//TODO: should this be strict?
    	//if (escaped) {
    		//throw new FunctionExecutionException();
    	//}
    	return sb.toString();
    }

	private static int parseNumericValue(CharSequence string, StringBuilder sb,
			int i, int value, int possibleDigits, int radixExp) {
		for (int j = 0; j < possibleDigits; j++) {
			if (i + 1 == string.length()) {
				break;
			}
			char digit = string.charAt(i + 1);
			int val = Character.digit(digit, 1 << radixExp);
			if (val == -1) {
				break;
			}
			i++;
			value = (value << radixExp) + val;
		}
		sb.append((char)value);
		return i;
	}
	
	public static String hex(long val, int hexLength) {
		long hi = 1L << (Math.min(63, hexLength * 4));
		return Long.toHexString(hi | (val & (hi - 1))).substring(1);
    }
	
}

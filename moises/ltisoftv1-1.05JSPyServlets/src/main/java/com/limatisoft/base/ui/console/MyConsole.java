package com.limatisoft.base.ui.console;
import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Formatter;

public final class MyConsole {
	private Console console = System.console();
	BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	PrintStream out = System.out; 
	private Formatter formatter;
	private static MyConsole myConsole=null;
	private MyConsole() {		
		formatter = new Formatter(out);
	}
	public static MyConsole getInstance(){
		if(myConsole == null){
			myConsole = new MyConsole();
		}
		return myConsole;
	}
	public String readLine(String fmt, Object ... args) {
		if(console!=null){
			return console.readLine(fmt, args);
		}
		out.print(String.format(fmt, args));
		String input = null;
		try {
			input = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return input;
	}
	
	public String readLine() {
        return readLine("");
    }
	
	public char[] readPassword(String fmt, Object ... args) {
		if(console!=null){
			return console.readPassword(fmt, args);
		}
		String line = readLine(fmt, args); 
		return line.toCharArray();
	}
	public char[] readPassword() {
        return readPassword("");
    }
	
	public void format(String fmt, Object ...args) {
		if(console!=null){
			console.format(fmt, args);		
			return;
		}
        formatter.format(fmt, args).flush();
    }
	
	public void printf(String format, Object ... args) {
		format(format, args);
	}
	
	public void println(String format, Object... args) {
	    String newFormat = format + System.lineSeparator();
	    format(newFormat, args);
	}
	
	public void clear() {
	    try {
	        if (System.getProperty("os.name").contains("Windows")) {
	            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
	        } else {
	            new ProcessBuilder("clear").inheritIO().start().waitFor();
	        }
	    } catch (IOException | InterruptedException e) {
	        e.printStackTrace();
	    }
	}
}
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.*;


public class linter1 {
	public static void main(String args[]){
		File file = new File(args[0]);
		Scanner scan;
		try {
			scan = new Scanner(file);
			String line = scan.nextLine();
			int line_num = 1;
			semicolon(line, line_num);
			while(scan.hasNextLine()){
				line = scan.nextLine();
				line_num++;
				semicolon(line, line_num);
				whitespace(line, line_num);
				equality(line, line_num);
				apostrophe(line, line_num);
				multiple(line, line_num);
				length(line, line_num);
				if(!scan.hasNextLine()){
					newline(line, line_num);
				}
			}
			scan.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		}
	}
	public static boolean semicolon(String line, int num){
		boolean okay = false;
		Matcher match = Pattern.compile(".*"+Pattern.quote(";")+"$").matcher(line);
		if(match.matches()){
			okay = true;
		}
		else{
			okay = bracket(line, num);
		}
		return okay;
	}
	public static boolean bracket(String line, int num){
		boolean okay = false;
		if(line.length() == 1){
			Matcher match = Pattern.compile(".*" + Pattern.quote("}")+"$").matcher(line);
			if(match.matches()){
				okay = true;
			}
			else{
				System.out.printf("<%d>. An opening brace cannot stand alone\n", num);
			}
		}
		else if(line.matches("^\\t*"+Pattern.quote("}")+"$")){
				okay = true;
			}
		else if(line.matches(".*"+Pattern.quote("}")+"$")){
			System.out.printf("<%d>. A closing brace must stand alone\n", num);
		}
		else if(line.matches("^\\s*$"))
			okay = true;
		else{
			Matcher match = Pattern.compile(".*"+Pattern.quote("{")+"$").matcher(line);
			Matcher matchAgain = Pattern.compile("/s*" + Pattern.quote("}")+"$").matcher(line);
			if(match.matches()){
				okay = true;
			}
			else if(matchAgain.matches()){
				System.out.printf("<%d>. A closing brace must stand alone\n", num);
			}
			else{
				System.out.printf("<%d>. Should end with a semicolon\n", num);
			}
		}
		return okay;
	}
	public static void whitespace(String line, int num){
		if(line.matches("^.*\\s$")){
			System.out.printf("<%d>. Trailing whitespace\n", num);
		}
	}
	public static void newline(String line, int num){
		if(!line.matches("^$"))
			System.out.printf("<%d> Program should end with a new line", num);
	}
	public static void equality(String line, int num){
		if(line.matches(".*==.*")){
			if(!line.matches(".*===.*")){
				System.out.printf("<%d>. Should only use strict equality\n", num);
			}
		}
	}
	public static void apostrophe(String line, int num){
		if(line.matches(".*\".*\".*")){
			if(!line.matches(".*'.*")){
				System.out.printf("<%d>. Should use single quotes\n", num);
			}
		}
	}
	public static void multiple(String line, int num){
		if(line.matches(".*;.*;")){
			System.out.printf("<%d>. Use only one statement per line", num);
		}
	}
	public static void length(String line, int num){
		if(line.length() > 80){
			System.out.printf("<%d>. Lines should not be longer than 80 characters\n", num);
		}
	}
}

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.*;


public class linter1 {
	public static void main(String args[]){
		Scanner console = new Scanner(System.in);
		System.out.println("File: ");
		String input = console.next();
		File file = new File(input);
		Scanner scan;
		try {
			scan = new Scanner(file);
			String line = scan.nextLine();
			int line_num = 1;
			boolean okay = semicolon(line);
			if(okay == false){
				System.out.printf("<%d>. Should end with a semicolon", line_num);
			}
			while(scan.hasNextLine()){
				line = scan.nextLine();
				line_num++;
				okay = semicolon(line);
				if(okay == false){
					System.out.printf("<%d>. Should end with a semicolon", line_num);
				}
			}
			console.close();
			scan.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		}
	}
	public static boolean semicolon(String line){
		boolean okay = false;
		Matcher match = Pattern.compile(".*"+Pattern.quote(";")+"$").matcher(line);
		if(match.matches()){
			okay = true;
		}
		else{
			okay = bracket(line);
		}
		return okay;
	}
	public static boolean bracket(String line){
		boolean okay = false;
		if(line.length() == 1){
			Matcher match = Pattern.compile(Pattern.quote("}")+"$").matcher(line);
			if(match.matches()){
				okay = true;
			}
		}
		else{
			Matcher match = Pattern.compile(".*"+Pattern.quote("{")+"$").matcher(line);
			if(match.matches()){
				okay = true;
			}
		}
		return okay;
	}

}

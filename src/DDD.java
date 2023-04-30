import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DDD {
public static void main(String[] args) throws FileNotFoundException {
	File file=new File("data.txt");//the data file
	Scanner scan = new Scanner(file);
	String arr[]=new String [50];
	String line="";
	line=scan.nextLine();
	for(int i=0;i<50;i++) {
		
		line=scan.nextLine();//each line is country
		String[] strN = line.split(" ");
		arr[i]=strN[0];
		
	}
for(int i=0;i<150;) {
		int s=(int)(Math.random()*50);
		int k=(int)(Math.random()*50);
		if(s!=k) {
			System.out.println(arr[s]+" "+arr[k]);
			i++;
		}
		
		
}
		
	}
}

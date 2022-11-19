package testtwo;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.lang.*;

public class TestTwo {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner Scanscan = new Scanner(System.in);
        System.out.println("Input filenames for this test are \"input1.txt\", \"input2.txt\", \"input3.txt\", \"input4.txt\"");
        System.out.println("See README for more info");
        System.out.print("Including file extension, type the test file name: ");
        String filename = Scanscan.nextLine();
        File inputFile = new File(filename);
        Scanner reader = new Scanner(inputFile);

        //ArrayList for lexemes and HashTable for the Symbol Table
        ArrayList<String> lines = new ArrayList<>();
        Map<String, List<String>> symbolTable = new HashMap<String, List<String>>();
        
        //Reads every line and then splits everything into lexemes
        while (reader.hasNextLine()) {
            String str = reader.nextLine(); //Reads the lines
            if (!(str.length() == 0)) {
                String [] strSplit = str.trim().split("\\s+|\\s*,\\s*|\\;+|\\\"+|\\:+|\\[+|\\]+"); //lexemes regex
                
                List <String> list = Arrays.asList(strSplit);
                lines.addAll(list);
            }
        }
        List<String> keywords = new ArrayList<String>();        
        if (lines.contains("int") || lines.contains("if") || lines.contains("else")) {         
            if (lines.contains("int")) {
                int index = 0;
                index = lines.indexOf("int");   
                keywords.add(lines.get(index));
            }  
            if (lines.contains("if")) {
                int index = 0;
                index = lines.indexOf("if");  
                keywords.add(lines.get(index));
            }  
            if (lines.contains("else")) {
                int index = 0;
                index = lines.indexOf("else");   
                keywords.add(lines.get(index));
            }            
        }
        symbolTable.put("Keywords", keywords);
        List<String> operators = new ArrayList<String>();        
        if (lines.contains("=") || lines.contains("-") || lines.contains("+") || lines.contains("*")) {         
            if (lines.contains("=")) {
                int index = 0;
                index = lines.indexOf("=");   
                operators.add(lines.get(index));
            }  
            if (lines.contains("-")) {
                int index = 0;
                index = lines.indexOf("-");  
                operators.add(lines.get(index));
            }  
            if (lines.contains("+")) {
                int index = 0;
                index = lines.indexOf("+");  
                operators.add(lines.get(index));
            }  
            if (lines.contains("*")) {
                int index = 0;
                index = lines.indexOf("*");   
                operators.add(lines.get(index));
            }            
        }        
        symbolTable.put("Math Operators", operators); 
        
        List<String> logical = new ArrayList<String>();        
        if (lines.contains("<") || lines.contains(">") || lines.contains("<=") || lines.contains(">=")) {         
            if (lines.contains("<")) {
                int index = 0;
                index = lines.indexOf("<");   
                logical.add(lines.get(index));
            }  
            if (lines.contains(">")) {
                int index = 0;
                index = lines.indexOf(">");  
                logical.add(lines.get(index));
            }  
            if (lines.contains("<=")) {
                int index = 0;
                index = lines.indexOf("<=");  
                logical.add(lines.get(index));
            }  
            if (lines.contains(">=")) {
                int index = 0;
                index = lines.indexOf(">=");   
                logical.add(lines.get(index));
            }            
        }        
        
        symbolTable.put("Logical Operators", logical);          
        
        String [] linesArray = lines.toArray(new String [0]);
        
        List<String> digits = new ArrayList<String>();     
        for (int count = 0;  count < linesArray.length; count++) {
            if (linesArray[count].matches("\\d+|\\d+\\+\\d+")) {  //Use regex here for numbers                 
                digits.add(linesArray[count]);                       
            }        
        }

        symbolTable.put("nUMBERS", digits);

        List<String> identifiers = new ArrayList<String>();     
        for (int count = 0;  count < linesArray.length; count++) {
            if (linesArray[count].matches("\\w+") && !linesArray[count].matches("\\d+") && !linesArray[count].matches("int|float|if|else")) {  //Use regex here for variables  
                if (!identifiers.contains(linesArray[count])) {
                    identifiers.add(linesArray[count]);    
                }
            }        
        }
        
        symbolTable.put("Identifiers", identifiers);

        List<String> misc = new ArrayList<String>();     
        for (int count = 0;  count < linesArray.length; count++) {
            if (linesArray[count].matches("\\(|\\)|\\{|\\}")) {  //Use regex here for variables  
                if (!identifiers.contains(linesArray[count])) {
                    misc.add(linesArray[count]);
                }
            }        
        }
        symbolTable.put("Misc", misc);
        int lexcount = lines.size();
        System.out.println("\n# of lexemes: "+lexcount);
        System.out.print("Lexemes: ");
        System.out.println(lines+"\n");

        for (int i=0; i < lines.size(); i++) {
            String test = lines.get(i);
            String[] badKeys = new String[]{"while","if","int","short","long"}; //this array is used to ensure keywords cannot
                                                                                //use restriced words.  Not necessary as char limit is enforced
            if (test.equals("int")||test.equals("string")) {  //ensures token following int is an void of digits and 6-8 chars long
                
                boolean varnums = lines.get(i+1).matches("^[0-9]+$");
                boolean charlimit = lines.get(i+1).matches("[_a-zA-z]{6,8}");
                if (!varnums) {
                    System.out.println("INVALID VARIABLE ID: "+lines.get(i+1));
                    System.out.println("Variable name cannot contain digits\n");
                }
                if(!charlimit) {
                    System.out.println(lines.get(i+1)+" is NOT 6-8 chars long. Illegal declaration");
                }                
                try {
                    String semitest = lines.get(i+2);
                    if (!semitest.equals(";")) {
                        System.out.println("Declarations MUST end with a semicolon!");
                        System.out.println("CORRECTION "+lines.get(i)+" "+lines.get(i+1)+";\n");
                    }
                } catch (Exception e) {
                    System.out.println("ExceptionThrow: end of ducment");
                }
            }
            if (test.equals("string")) {  //ensures token following 'string' has is between 6-8 chars long
                boolean varnums = lines.get(i+1).matches("^[0-9]+$");
                boolean charlimit = lines.get(i+1).matches("[_a-zA-z]{6,8}");
                if (!varnums) {
                    System.out.println("INVALID VARIABLE ID: "+lines.get(i+1));
                    System.out.println("Variable name cannot contain digits\n");
                }
                if(!charlimit) {
                    System.out.println(lines.get(i+1)+" is NOT 6-8 chars long. Illegal declaration");
                }
            }
        }
        System.out.println("SYMBOL TABLE");
        
        for (Map.Entry<String, List<String>> entry : symbolTable.entrySet()) {
            String key = entry.getKey();
            List<String> values = entry.getValue();
            System.out.print(key + ": ");
            System.out.println(values);
        }

    }

}
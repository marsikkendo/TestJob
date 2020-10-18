import java.util.List;

public class Main {
    
    private final static String FIRST_XML_FILE = "1.xml";
    
    private final static String RESULT_XML_FILE = "result.xml";
    
    public static void main(String[] args) {
        long currentTime = System.currentTimeMillis();
        App app = new App();
        app.connectToDb();
        app.addFields(100); // First step: add fields to the db.
        List<Integer> fields = app.getAllTSTFields(); // Step two : select all fields from db.
        app.generateDocument(fields, FIRST_XML_FILE);// Step three : generate xml file with name 1.xml.
        app.transformFile(FIRST_XML_FILE, RESULT_XML_FILE);// Step four : transform xml file from 1.xml to result.xml.
        app.countSumFields(RESULT_XML_FILE);// Step five : count total sum fields from file result.xml.
        app.disconnectDb();
        System.out.println("Время выполнения программы: " + (System.currentTimeMillis() - currentTime) / 1000.0 + " секунд");
    }
    
    
}

import java.util.ArrayList;
import java.util.List;

/**
 * Main app.
 */
public class App {
    
    /** Repository TST. */
    private final TSTRepository TSTrepository = TSTRepository.getInstance();
    
    /** Document helper. */
    private final DocumentHelper documentHelper = new DocumentHelper();
    
    public void addFields(int number) {
        TSTrepository.addTSTFields(generateFieldsList(number));
    }
    
    /**
     * Get all TST fields.
     *
     * @return list with fields.
     */
    public List<Integer> getAllTSTFields() {
        return TSTrepository.getAllTSTFields();
    }
    
    /**
     * Generate document.
     *
     * @param list data list.
     * @param path target file path.
     */
    public void generateDocument(List<Integer> list, String path) {
        documentHelper.generateDocument(list, path);
    }
    
    /**
     * Generate list on inbound value.
     *
     * @param number inbound value.
     * @return data list.
     */
    private List<Integer> generateFieldsList(int number) {
        List<Integer> list = new ArrayList<>(number);
        for (int i = 1; i <= number; i++) {
            list.add(i);
        }
        return list;
    }
    
    /**
     * Count total sum from source file and print it value.
     *
     * @param sourcePath source file path.
     */
    public void countSumFields(String sourcePath) {
        System.out.println( "Сумма значений всех атрибутов field = "+documentHelper.countTotalSum(sourcePath));
    }
    
    /**
     * Transform source file into target file.
     *
     * @param sourcePath path for source file.
     * @param targetPath path for target file.
     */
    public void transformFile(String sourcePath, String targetPath) {
        documentHelper.transform(sourcePath, targetPath);
    }
    
    /**
     * Connect to data base.
     */
    public void connectToDb() {
        TSTrepository.connectToDb();
    }
    
    /**
     * Disconnect from base.
     */
    public void disconnectDb() {
        TSTrepository.disconnect();
    }
}

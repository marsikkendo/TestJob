import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Document helper.
 */
public class DocumentHelper {
    
    /** Tag field in xml. */
    private final static String TAG_FIELD = "field";
    
    /** Tag entry in xml. */
    private final static String TAG_ENTRY = "entry";
    
    /** Tag entries in xml. */
    private final static String TAG_ENTRIES = "entries";
    
    /**
     * Generate document.
     *
     * @param list data list.
     * @param path result file path.
     */
    void generateDocument(List<Integer> list, String path) {
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            Element entries = document.createElement(TAG_ENTRIES);
            document.appendChild(entries);
            list.stream().map(fieldValue -> createEntryElement(fieldValue, document)).forEach(entries::appendChild);
            writeDocument(document, path);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Create entry element.
     *
     * @param fieldValue value for tag field.
     * @param document document.
     * @return element entry with child elements.
     */
    private Element createEntryElement(int fieldValue, Document document) {
        Element entry = document.createElement(TAG_ENTRY);
        Element field = document.createElement(TAG_FIELD);
        Text textNode = document.createTextNode(String.valueOf(fieldValue));
        field.appendChild(textNode);
        entry.appendChild(field);
        return entry;
    }
    
    /**
     * Write document on file system.
     *
     * @param document source document.
     * @param path target file path for document.
     */
    private void writeDocument(Document document, String path) {
        try {
            Transformer tr = TransformerFactory.newInstance().newTransformer();
            DOMSource source = new DOMSource(document);
            FileOutputStream fos = new FileOutputStream(path);
            StreamResult result = new StreamResult(fos);
            tr.transform(source, result);
        } catch (TransformerException | IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Transform source file into target file.
     *
     * @param sourcePath path for source file.
     * @param targetPath path for target file.
     */
    public void transform(String sourcePath, String targetPath) {
        try {
            TransformerFactory factory = TransformerFactory.newInstance();
            Source xslt = new StreamSource(new File("stylesheet.xsl"));
            Transformer transformer = factory.newTransformer(xslt);
            Source xml = new StreamSource(new File(sourcePath));
            transformer.transform(xml, new StreamResult(new File(targetPath)));
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Count total sum from source file.
     *
     * @param sourcePath path to source file.
     * @return total sum.
     */
    public int countTotalSum(String sourcePath) {
        int sum = 0;
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(sourcePath);
            Node root = document.getDocumentElement();
            NodeList entries = root.getChildNodes();
            for (int i = 0; i < entries.getLength(); i++) {
                String fieldValue = entries.item(i).getAttributes().getNamedItem(TAG_FIELD).getNodeValue();
                sum += Integer.parseInt(fieldValue);
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return sum;
    }
}

package Tools.FileWorkers.FileImplementation;

import com.example.translateok.DebugActivity;
import com.example.translateok.MainActivity;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import Models.Structures.ConfigStructure;

/**
 * Created by java_monkey on 2/21/2016.
 */
public class FileWorkerXml extends FileAbstract implements IApplicationFile{

    private String pathToFile;

    private Document content;

    private String fileType;

    private final DocumentBuilderFactory documentBuilderFactory;

    private final DocumentBuilder documentBuilder;

    private final String EXTENSION = ".xml";

    /**
     *
     * @param pathToFile
     * @throws ParserConfigurationException
     */
    public FileWorkerXml(String pathToFile) throws Exception {
        this.documentBuilderFactory = DocumentBuilderFactory.newInstance();
        this.documentBuilder = documentBuilderFactory.newDocumentBuilder();

        if(!this.validateFilePath(pathToFile)){
            throw new Exception("Wrong path to file: " + pathToFile);
        }

        this.pathToFile = pathToFile + EXTENSION;
        this.fileType = Types.TEMPORARY.name();

        DebugActivity.logger.flushMessage(
                this.getClass().getName() + " loaded!");
    }

    /**
     *
     * @param type
     */
    @Override
    public void setFileType(IApplicationFile.Types type) {
        this.fileType = type.name();
    }

    /**
     *
     * @param pathToFile
     */
    public void setPathToFile(String pathToFile) throws Exception {
        if(!this.validateFilePath(pathToFile)){
            throw new Exception("Wrong path to file");
        }

        this.pathToFile = pathToFile + EXTENSION;
    }

    /**
     *
     * @param fields
     */
    private void updateXmlDocument(Map<String, String> fields){
        for (Map.Entry entry : fields.entrySet()){
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();

            DebugActivity.logger.flushMessage(key + " : " + value);
            this.changeDocument(this.content, key, value);
        }
    }

    @Override
    public boolean updateFile(Object obj) {
        Map<String, String> fields = (HashMap) obj;
        if(obj == null || fields.isEmpty()){

            return false;
        }

        this.updateXmlDocument(fields);

        try {
            this.saveConfig(this.content);
        } catch (TransformerException e) {
            e.printStackTrace();
            return false;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * Create file with default data
     *
     * @return
     */
    @Override
    public boolean createFile() {
        DOMImplementation impl = documentBuilder.getDOMImplementation();
        Document document = this.setupNewConfigFile(impl);
        try {
            this.saveConfig(document);
        } catch (TransformerException e) {
            DebugActivity.logger.flushMessage(" cant save "
                    + this.pathToFile + e.getMessage());

            return false;
        } catch (FileNotFoundException e){
            DebugActivity.logger.flushMessage(" not found "
                    + this.pathToFile + e.getMessage());

            return false;
        }

        this.content = document;

        return true;
    }

    /**
     *
     * @param impl
     * @return
     */
    private Document setupNewConfigFile(DOMImplementation impl){
        Document doc = impl.createDocument(null, null, null);
        Element root = doc.createElement("app_config");
        Element element = null;

        ConfigStructure defaultConfigs = new ConfigStructure();
        for(Map.Entry <String, String> entry: defaultConfigs.getConfigs().entrySet()){
            element = doc.createElement(entry.getKey());
            element.setAttribute("value", entry.getValue());

            root.appendChild(element);
        }

        Element statistic = doc.createElement("statistic");
        for(Map.Entry <String, Integer> entry: defaultConfigs.getStatistic().entrySet()){
            element = doc.createElement(entry.getKey());
            element.setAttribute("value", String.valueOf(entry.getValue()));

            statistic.appendChild(element);
        }

        root.appendChild(statistic);
        doc.appendChild(root);

        return doc;
    }

    @Override
    public boolean readFile() throws IOException {
        try {
            this.content = this.documentBuilder.parse(
                    MainActivity.context.openFileInput(this.getPathToFile()));
        } catch (SAXException e) {
            e.printStackTrace();

            return false;
        }

        //this.showDocument(this.content);

        return true;
    }

    /**
     * Test method
     * @param node
     */
    public void showDocument(Node node){
        NodeList list = node.getChildNodes();
        for (int ix = 0; ix < list.getLength(); ix++) {
            Node childNode = list.item(ix);
            this.displayElement(childNode);
            this.showDocument(childNode);
        }
    }


    /**
     * Test method
     * @param node
     */
    public void displayElement(Node node) {
        DebugActivity.logger.flushMessage(node.getNodeName());
        if (node instanceof Element){
            Element e = (Element) node;
            DebugActivity.logger.flushMessage("#" + e.getAttribute("value"));
        }
    }

    /**
     * Update xml document
     *
     * @param node
     * @param field
     * @param value
     */
    private void changeDocument(Node node, String field, String value){
        NodeList list = node.getChildNodes();
        for(int ix = 0; ix < list.getLength(); ++ix){
            Node childNode = list.item(ix);
            if(childNode.getNodeName().equals(field)) {
                if (childNode instanceof Element) {
                    Element e = (Element) childNode;
                    e.setAttribute("value", value);
                }
            }

            this.changeDocument(childNode, field, value);
        }
    }

    /**
     *
     * @param doc
     * @throws TransformerException
     * @throws FileNotFoundException
     */
    private void saveConfig(Document doc) throws TransformerException, FileNotFoundException {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(
                new DOMSource(doc),
                new StreamResult(this.getOutStream())
        );
    }

    @Override
    public boolean delete() throws Exception {
        if(fileType.equals(Types.PERMANENT)){
            throw new Exception("File is PERMANENT");
        }

        return MainActivity.context.deleteFile(this.pathToFile);
    }

    @Override
    public String getPathToFile() {
        return this.pathToFile;
    }

    /**
     *
     * @return
     */
    public Document getContent() {
        return this.content;
    }
}

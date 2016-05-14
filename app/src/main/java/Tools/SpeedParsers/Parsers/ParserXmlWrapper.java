package Tools.SpeedParsers.Parsers;

import com.example.translateok.DebugActivity;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import Tools.QueryBuilder.QueryTypes.IXmlCriteria;
import Tools.SpeedParsers.IParserQuery;
import Tools.SpeedParsers.ParserAdapter;

/**
 * Created by java_monkey on 2/28/2016.
 */
public class ParserXmlWrapper extends ParserAdapter implements IParserQuery {

    private XPathFactory xPathfactory = XPathFactory.newInstance();

    private XPath xpath;

    private Document document;

    public ParserXmlWrapper(Document document){
        this.init();
        this.document = document;
    }

    /**
     *
     */
    public ParserXmlWrapper(){
        this.init();
        this.nodeListSearch = new ArrayList<String>();
    }

    /**
     *
     * @param document
     */
    public void setContent(Document document){
        this.document = document;
    }

    @Override
    public Object[][] runQuery(IXmlCriteria criteria) {
        XPathExpression expr;
        NodeList node = null;

        try {
            expr = xpath.compile(criteria.getQuery());
            node = (NodeList) expr.evaluate(this.document, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

        Object[][] obj = this.nodesToArray(node, IXmlCriteria.ATTR_VALUE);

        DebugActivity.logger.flushMessage("XML QUERYED!!");
        this.clearQuery();

        return obj;
    }

    @Override
    public void init() {
        xpath = xPathfactory.newXPath();
    }

    /**
     * Need to test
     *
     * @param list
     * @return
     */
    private String[][] nodesToArray(NodeList list, String item){
        String arr[][] = new String[list.getLength()][2];
        for (int ix = 0; ix < list.getLength(); ++ix){
            arr[ix][0] = list.item(ix).getNodeName();
            arr[ix][1] = list.item(ix).getAttributes().getNamedItem(item).getNodeValue();
        }

        return arr;
    }
}

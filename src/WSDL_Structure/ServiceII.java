package WSDL_Structure;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.xml.namespace.QName;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMDocument;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.impl.builder.StAXOMBuilder;
import org.apache.axiom.om.impl.llom.factory.OMXMLBuilderFactory;
import org.apache.axis2.AxisFault;
import org.apache.axis2.databinding.utils.BeanUtil;
import org.apache.axis2.util.StreamWrapper;

public class ServiceII {
	public static void main(String[] args) throws AxisFault,
			FileNotFoundException, XMLStreamException,
			FactoryConfigurationError {
		/*
		 * WSDL_ParameterInfo man = new WSDL_ParameterInfo(); man.setName("a");
		 * man.setType("int"); man.setValue("20");
		 * javax.xml.stream.XMLStreamReader reader=BeanUtil.getPullParser(man);
		 * StreamWrapper parser=new StreamWrapper(reader); StAXOMBuilder
		 * stAXOMBuilder
		 * =OMXMLBuilderFactory.createStAXOMBuilder(OMAbstractFactory
		 * .getOMFactory(), parser); OMElement
		 * element=stAXOMBuilder.getDocumentElement();
		 * System.out.println(element.toString());
		 */

		XMLStreamReader parser = XMLInputFactory.newInstance()
				.createXMLStreamReader(new FileInputStream("C:\\5.xml"));
		StAXOMBuilder builder = new StAXOMBuilder(parser);
		// get the root element
		// OMElement documentElement = builder.getDocumentElement();
		OMDocument doc = builder.getDocument();

		OMElement cre = doc.getOMDocumentElement().getFirstChildWithName(
				new QName("fool"));
		System.out.println(cre);

		/*
		 * cre.serialize(System.out); // cache on
		 * cre.serializeAndConsume(System.out); // cache off
		 * 
		 * // will NOT build the OMTree in the memory. // So you are at your own
		 * risk of losing information. String creStr =
		 * cre.toStringWithConsume(); // call toString, will build the OMTree in
		 * the memory. System.out.println(cre);
		 */
	}
}

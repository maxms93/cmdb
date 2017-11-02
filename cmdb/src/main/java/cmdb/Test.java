package cmdb;

import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.query.DatasetAccessor;
import org.apache.jena.query.DatasetAccessorFactory;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;

public class Test {

	public static void main(String[] args) {

		Model m = ModelFactory.createDefaultModel();
    	
    	Resource r = m.createResource("http://max.sand.com/test/r");
    	Property p = m.createProperty("http://max.sand.com/test/p");
    	
    	r.addProperty(p, "Max" , XSDDatatype.XSDstring);
    	        
        m.write(System.out , "Turtle");
        
        String serviceURI = "http://localhost:3030/cmdb/data";
        DatasetAccessorFactory factory = null;
        DatasetAccessor accessor;
        accessor = factory.createHTTP(serviceURI);
        
        accessor.putModel(m);
        
	}

}

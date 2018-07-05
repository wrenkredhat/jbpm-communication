package stTest;


import java.util.List;
import java.util.Map;



import org.kie.api.runtime.process.NodeInstance;
import org.kie.api.runtime.process.ProcessContext;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;
import org.jbpm.marshalling.impl.JBPMMessages;
import org.jbpm.runtime.manager.impl.RuntimeEngineImpl;
import org.jbpm.runtime.manager.impl.SingletonRuntimeManager;
import org.kie.api.definition.process.Connection;
import org.kie.api.definition.process.Node;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeManager;

import org.jbpm.process.workitem.core.AbstractWorkItemHandler;

public class STWihKsAWih extends AbstractWorkItemHandler {
	
	STWihKsAWih( KieSession ks ) {
		super( ks );
	}
	
  public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {

	  Map<String, Object> pList = workItem.getParameters();
	  
	  for ( String s : pList.keySet() ) {
		  System.out.println( "Parameter:" + s + ':' + workItem.getParameter(s)  );		  
	  }
	  
	  Long id = workItem.getId();
	  
	  
	  System.out.println (  "wiID:" +  id );
	  
	  // manager.completeWorkItem(workItem.getId(), null);
	  
	  NodeInstance ni =  getNodeInstance(workItem);
	  
	  Node n = ni.getNode();
	  
	  Map metadata = n.getMetaData();
	  
	  for ( Object s : metadata.keySet() ) {
		  System.out.println( "MD:" + s + ':' + metadata.get(s) );		  
	  }
	  
	  System.out.println( "Node-Name:" + n.getName( ));
	  
	  Map<String, List<Connection>> ic = n.getIncomingConnections();
	  
	  for ( Object s : ic.keySet() ) {
		  List<Connection> cs = ic.get(s);
		  
		  for ( Connection c : cs ) {

			  for ( String cd : c.getMetaData().keySet() ) {
				  System.out.println( "icMD:" + s + ':' + metadata.get(cd) );		  
			  }
		  }
	  }
	  
	  Map<String, List<Connection>> oc = n.getIncomingConnections();
	  
	  for ( Object s : oc.keySet() ) {
		  List<Connection> ocs = oc.get(s);
		  
		  for ( Connection c : ocs ) {

			  for ( String cd : c.getMetaData().keySet() ) {
				  System.out.println( "ocMD:" + s + ':' + metadata.get(cd) );		  
			  }
		  }
	  }
	  	  
  }

  public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {

  }
}


package stTest;


import java.util.Map;



import org.kie.api.runtime.process.NodeInstance;
import org.kie.api.runtime.process.ProcessContext;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;
import org.kie.internal.runtime.StatefulKnowledgeSession;
import org.jbpm.marshalling.impl.JBPMMessages;
import org.jbpm.runtime.manager.impl.RuntimeEngineImpl;
import org.jbpm.runtime.manager.impl.SingletonRuntimeManager;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeManager;

public class STWihKs implements WorkItemHandler {
	
	KieSession ks;
	
	STWihKs( StatefulKnowledgeSession ks ) {
		this.ks = ks;
	}
	
  public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {

	  Map<String, Object> pList = workItem.getParameters();
	  
	  for ( String s : pList.keySet() ) {
		  System.out.println( "Parameter:" + s + ':' + workItem.getParameter(s)  );		  
	  }
	  
	  Long id = workItem.getId();
	  
	  
	  System.out.println (  "wiID:" +  id );
	  //System.out.println (  "re:" + re );
	  
  }

  public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {

  }
}


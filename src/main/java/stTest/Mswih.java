package stTest;


import java.util.Map;


import org.kie.api.runtime.process.NodeInstance;
import org.kie.api.runtime.process.ProcessContext;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;
import org.kie.api.runtime.KieSession;


public class Mswih implements WorkItemHandler {
	
  public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {

	  Map<String, Object> pList = workItem.getParameters();
	  
	  for ( String s : pList.keySet() ) {
		  System.out.println( "Parameter:" + s + ':' + workItem.getParameter(s)  );		  
	  }    
  }

  public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {

  }
}


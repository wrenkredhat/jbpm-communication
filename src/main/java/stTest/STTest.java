package stTest;

import java.util.Properties;

import org.drools.compiler.compiler.ProcessBuilderFactory;
import org.drools.core.impl.EnvironmentFactory;
import org.drools.core.impl.KnowledgeBaseFactory;
import org.drools.core.runtime.process.ProcessRuntimeFactory;
import org.jbpm.process.builder.ProcessBuilderFactoryServiceImpl;
import org.jbpm.process.instance.ProcessRuntimeFactoryServiceImpl;
import org.jbpm.services.task.identity.JBossUserGroupCallbackImpl;
import org.junit.*;
import org.kie.api.KieBase;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.KieSessionConfiguration;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeEnvironment;
import org.kie.api.runtime.manager.RuntimeEnvironmentBuilder;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.runtime.manager.RuntimeManagerFactory;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.runtime.manager.context.EmptyContext;
import org.kie.internal.task.api.UserGroupCallback;

public class STTest {
	
	private UserGroupCallback userGroupCallback;  
    private RuntimeManager manager;
	
    private static KieSession createSession(KieBase kbase) {
        Properties properties = new Properties();
        properties.put("drools.processInstanceManagerFactory",
                       "org.jbpm.process.instance.impl.DefaultProcessInstanceManagerFactory");
        properties.put("drools.processSignalManagerFactory",
                       "org.jbpm.process.instance.event.DefaultSignalManagerFactory");
        KieSessionConfiguration config = KnowledgeBaseFactory.newKnowledgeSessionConfiguration(properties);
        return kbase.newKieSession(config,
                                   EnvironmentFactory.newEnvironment());
    }
    
    private static KieBase readKnowledgeBase( String ... resources  ) throws Exception {
        ProcessBuilderFactory.setProcessBuilderFactoryService(new ProcessBuilderFactoryServiceImpl());
        ProcessRuntimeFactory.setProcessRuntimeFactoryService(new ProcessRuntimeFactoryServiceImpl());
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        
        
        for ( String resource : resources ) {
            kbuilder.add(ResourceFactory.newClassPathResource(resource),  ResourceType.BPMN2);
        	
        }
        return kbuilder.newKieBase();
    }
    
    RuntimeEnvironmentBuilder builder = null;
 
       
    public KieSession creationOfSessionInMemory( String ... resources ) {
    	
    	
    	builder = RuntimeEnvironmentBuilder.Factory.get().newDefaultInMemoryBuilder();
    	builder.entityManagerFactory(null).persistence(false);

    	
        for ( String resource : resources ) {
            builder.addAsset(ResourceFactory.newClassPathResource(resource),  ResourceType.BPMN2);
        	
        }
        
        Properties properties= new Properties();
        properties.setProperty("mary", "HR");
        properties.setProperty("john", "HR");
        userGroupCallback = new JBossUserGroupCallbackImpl(properties);
		
        RuntimeEnvironment environment = builder.userGroupCallback(userGroupCallback).get();
        
        manager = RuntimeManagerFactory.Factory.get().newSingletonRuntimeManager(environment);  
        // assertNotNull(manager);
        
        
        RuntimeEngine runtime = manager.getRuntimeEngine(EmptyContext.get());
        
        

        KieSession ksession = runtime.getKieSession();
        
        return ksession;
        
    }
    
    
    
    @Test
    public void testSendTask() throws Exception {
    	
    	KieSession ks = createSession(readKnowledgeBase("sendTask.bpmn2"));
    	
        ks.getWorkItemManager().registerWorkItemHandler("Send Task", new STWihKsAWih(ks));
        
        ks.startProcess("MortgageApplication.STTest");
        
    }
    
    
    @Test
    public void testMsg() throws Exception {
    	
    	// KieSession ks = creationOfSessionInMemory( "sendTask.bpmn2" );
    	
    	KieSession ks = createSession(readKnowledgeBase("sendMsg.bpmn2"));
    	
        ks.getWorkItemManager().registerWorkItemHandler("Send Task", new STWihKsAWih(ks));
        
        ks.startProcess("MortgageApplication.sendMsg");
        
    }
    
    @Test
    public void testSig() throws Exception {
    	
    	// KieSession ks = creationOfSessionInMemory( "sendTask.bpmn2" );
    	
    	KieSession ks = createSession(readKnowledgeBase("sendSig.bpmn2"));
    	
        // ks.getWorkItemManager().registerWorkItemHandler("Send Task", new STWihKsAWih(ks));
        
        ks.startProcess("MortgageApplication.sendSig");
        
    }
}
 
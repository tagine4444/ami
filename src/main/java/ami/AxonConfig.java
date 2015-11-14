package ami;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.annotation.AggregateAnnotationCommandHandler;
import org.axonframework.commandhandling.annotation.AnnotationCommandHandlerBeanPostProcessor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.SimpleEventBus;
import org.axonframework.eventhandling.annotation.AnnotationEventListenerBeanPostProcessor;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventstore.EventStore;
import org.axonframework.eventstore.mongo.DefaultMongoTemplate;
import org.axonframework.eventstore.mongo.MongoEventStore;
import org.axonframework.eventstore.mongo.MongoFactory;
import org.axonframework.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import ami.domain.SecurityAggregate;
import ami.domain.model.amicase.AmiCase;

import com.mongodb.Mongo;


@Configuration
public class AxonConfig {
	
	@Autowired
	private MongoTemplate mongoTemplate;

   @Bean
   public CommandGateway commandGateway() {
   		CommandGateway commandGateway = new DefaultCommandGateway(commandBus());
   		return commandGateway;
   }
   
   @Bean
   public CommandBus commandBus() {
   	
   	CommandBus commandBus = new SimpleCommandBus();
   	
   	
   	// Register the Command Handlers with the command bus by subscribing to the name of the command//       commandBus.subscribe(CreateToDoItemCommand.class.getName(),
//               new CreateToDoCommandHandler(repository()));
//       commandBus.subscribe(MarkCompletedCommand.class.getName(),
//               new MarkCompletedCommandHandler(repository()));
       
       
       return commandBus;
   }


   @Bean
   public EventStore eventStore() {
   	
	   
	   String databaseName ="ami"; 
	   String domainEventsCollectionName ="domainevents";
       String snapshotEventsCollectionName ="snapshotevents"; 
       String userName =""; 
       char[] password = {}; 
       
       
	   	MongoFactory mongoFactory = new MongoFactory();
	   	Mongo mongo = mongoFactory.createMongo();
	   

	   	DefaultMongoTemplate mongoTemplate = new DefaultMongoTemplate(
	   			mongo,  databaseName,  domainEventsCollectionName,
	   			 snapshotEventsCollectionName,  userName, password);
	   	
	   	
	    MongoEventStore eventStore = new MongoEventStore(mongoTemplate);
        return eventStore;
   }

   @Bean
   public EventBus eventBus() {
       return new SimpleEventBus();
   }
 
   
   @Bean
   AnnotationCommandHandlerBeanPostProcessor annotationCommandHandlerBeanPostProcessor() {
       AnnotationCommandHandlerBeanPostProcessor handler = new AnnotationCommandHandlerBeanPostProcessor();
       handler.setCommandBus(commandBus());
       return handler;
   }

   @Bean
   AnnotationEventListenerBeanPostProcessor annotationEventListenerBeanPostProcessor() {
       AnnotationEventListenerBeanPostProcessor listener = new AnnotationEventListenerBeanPostProcessor();
       listener.setEventBus(eventBus());
       return listener;
   }
   
   
  
   
   
//   @Bean
//   public AggregateAnnotationCommandHandler<ToDoItem> todoItemCommandHandler() {
//       return AggregateAnnotationCommandHandler.subscribe(ToDoItem.class, todoItemRepository(), commandBus());
//   }
   
   
   // ==================================== AmiRequestAggregate ====================================
   @SuppressWarnings("unchecked")
   @Bean
   public AggregateAnnotationCommandHandler<AmiCase> AmiRequestCommandHandler() {
	   AggregateAnnotationCommandHandler<AmiCase> commandHandler = AggregateAnnotationCommandHandler.subscribe(AmiCase.class, amiRequestAggregateRepository(), commandBus());
	   return commandHandler;
   }
   

   @Bean(name = "amiRequestAggregateRepository")
   public Repository<AmiCase> amiRequestAggregateRepository() {
		
       EventSourcingRepository<AmiCase> repository = new EventSourcingRepository<AmiCase>(AmiCase.class, eventStore());
       repository.setEventBus(eventBus());
       return repository;
   }
   
   
   // ==================================== SecurityAggregate ====================================
   @SuppressWarnings("unchecked")
   @Bean
   public AggregateAnnotationCommandHandler<SecurityAggregate> SecurityCommandHandler() {
	   AggregateAnnotationCommandHandler<SecurityAggregate> commandHandler = AggregateAnnotationCommandHandler.subscribe(SecurityAggregate.class, securityAggregateRepository(), commandBus());
	   return commandHandler;
   }
   
   @Bean(name = "securityAggregateRepository")
   public Repository<SecurityAggregate> securityAggregateRepository() {
	   
	   EventSourcingRepository<SecurityAggregate> repository = new EventSourcingRepository<SecurityAggregate>(SecurityAggregate.class, eventStore());
	   repository.setEventBus(eventBus());
	   return repository;
   }
   


}

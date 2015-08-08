package ami;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.annotation.AggregateAnnotationCommandHandler;
import org.axonframework.commandhandling.annotation.AnnotationCommandHandlerBeanPostProcessor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.SimpleEventBus;
import org.axonframework.eventhandling.annotation.AnnotationEventListenerAdapter;
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

import ami.axon.annotated.ToDoEventHandler;
import ami.axon.annotated.ToDoItem;
import ami.domain.AmiRequestAggregate;

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
       char[] password = {'a'}; 
       
       
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
   
   @SuppressWarnings("unchecked")
   @Bean
   public AggregateAnnotationCommandHandler<ToDoItem> groupCommandHandler() {
       AggregateAnnotationCommandHandler<ToDoItem> commandHandler = AggregateAnnotationCommandHandler.subscribe(ToDoItem.class, todoItemRepository(), commandBus());
       return commandHandler;
   }
   @SuppressWarnings("unchecked")
   @Bean
   public AggregateAnnotationCommandHandler<AmiRequestAggregate> AmiRequestCommandHandler() {
	   AggregateAnnotationCommandHandler<AmiRequestAggregate> commandHandler = AggregateAnnotationCommandHandler.subscribe(AmiRequestAggregate.class, amiRequestAggregateRepository(), commandBus());
	   return commandHandler;
   }

   @Bean(name = "amiRequestAggregateRepository")
   public Repository<AmiRequestAggregate> amiRequestAggregateRepository() {
		
       EventSourcingRepository<AmiRequestAggregate> repository = new EventSourcingRepository<AmiRequestAggregate>(AmiRequestAggregate.class, eventStore());
       repository.setEventBus(eventBus());
       return repository;
   }
   
   @Bean(name = "todoItemRepository")
   public Repository<ToDoItem> todoItemRepository() {
	   
	   EventSourcingRepository<ToDoItem> repository = new EventSourcingRepository<ToDoItem>(ToDoItem.class, eventStore());
	   repository.setEventBus(eventBus());
	   return repository;
   }
   
   
   @Bean
   public AggregateAnnotationCommandHandler<ToDoItem> todoItemCommandHandler() {
       return AggregateAnnotationCommandHandler.subscribe(ToDoItem.class, todoItemRepository(), commandBus());
   }


}

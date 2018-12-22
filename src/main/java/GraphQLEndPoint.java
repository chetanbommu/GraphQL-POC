import javax.servlet.annotation.WebServlet;

import com.coxautodev.graphql.tools.SchemaParser;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import graphql.schema.GraphQLSchema;
import graphql.servlet.SimpleGraphQLServlet;

@WebServlet(urlPatterns = "/graphql")
public class GraphQLEndPoint extends SimpleGraphQLServlet {

	private static final long serialVersionUID = 1L;
	private static final LinkRepository linkRepository;
	private static final UserRepository userRepository;
	
	static {
		MongoDatabase mongo = new MongoClient().getDatabase("hackernews");
		linkRepository = new LinkRepository(mongo.getCollection("links"));
		userRepository = new UserRepository(mongo.getCollection("users"));
	}
	
	public GraphQLEndPoint() {
		super(buildSchema());
	}
	
	private static GraphQLSchema buildSchema() {
		
		return SchemaParser.newParser().file("schema.graphqls")
				.resolvers(new Query(linkRepository), new Mutation(linkRepository, userRepository)) //registering the resolvers
				.build()
				.makeExecutableSchema();
	}
}
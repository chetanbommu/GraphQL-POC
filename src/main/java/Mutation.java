import com.coxautodev.graphql.tools.GraphQLRootResolver;

public class Mutation implements GraphQLRootResolver {
	
	private LinkRepository linkRepository;
	private UserRepository userRepository;
	
	public Mutation(LinkRepository linkRepository, UserRepository userRepository) {
		this.linkRepository = linkRepository;
		this.userRepository = userRepository;
	}
	
	public Link createLink(String url, String description) {
		Link newLink = new Link(url, description);
		linkRepository.saveLink(newLink);
		return newLink;
	}
	
	public User createUser(String name, AuthData authentication) {
		User newUser = new User(name, authentication.getEmail(), authentication.getPassword());
		return userRepository.saveUser(newUser);
	}
}

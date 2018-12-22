import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoCollection;

public class UserRepository {
	private final MongoCollection<Document> users;
	
	public UserRepository(MongoCollection<Document> users) {
		this.users = users;
	}
	
	public User findById(String id) {
		Document document = users.find(eq("_id", new ObjectId(id))).first();
		return user(document);
	}
	
	public User findByEmail(String email) {
		Document document = users.find(eq("email", email)).first();
		return user(document);
	}
	
	public User saveUser(User user) {
		Document document = new Document();
		document.append("name", user.getName());
		document.append("email", user.getEmail());
		document.append("password", user.getPassword());
		users.insertOne(document);
		return new User(document.get("_id").toString(),
				user.getName(),
				user.getEmail(),
				user.getPassword());
	}
	
	private User user(Document document) {
		if(document == null) {
			return null;
		}
		return  new User(
				document.getString("name"),
				document.getString("email"),
				document.getString("password"));
	}
}

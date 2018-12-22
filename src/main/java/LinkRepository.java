import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoCollection;
/**
 * Manages Link Persistence
 * @author cbommu
 *
 */
public class LinkRepository {
	
	private final MongoCollection<Document> links;

	public LinkRepository(MongoCollection<Document> links) {
		this.links = links;
	}

	public Link findById(String id) {
		Document document = links.find(eq("_id", new ObjectId(id))).first();
		return link(document);
	}
	
	public List<Link> getAllLinks() {
		List<Link> allLinks = new ArrayList<>();
		for(Document doc : links.find()) {
			allLinks.add(link(doc));
		}
		return allLinks;
	}
	
	private Link link(Document document) {
		return new Link(
					document.get("_id").toString(),
					document.getString("url"),
					document.getString("description"));
	}
	
	public void saveLink(Link link) {
		Document document = new Document();
		document.append("url", link.getUrl());
		document.append("description", link.getDescription());
		links.insertOne(document);
	}
}

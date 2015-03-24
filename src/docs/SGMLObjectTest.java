package docs;

import java.util.List;

public class SGMLObjectTest {
	public static void main(String[] args) {
		SGMLObject obj = SGMLReaderUtil.readFromPath("res/common/Nationalities.xml").getChild("nationalities");
		if(obj.hasChildren("group")) {
			List<SGMLObject> groups = obj.getChildren("group");
			for(SGMLObject object: groups) {
				System.out.println(object.toString());
			}
		} else System.out.println("This is shit.");
	}
}

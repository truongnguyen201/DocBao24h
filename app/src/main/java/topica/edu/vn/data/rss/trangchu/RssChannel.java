package topica.edu.vn.data.rss.trangchu;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.List;

@Root(name = "channel",strict = false)
public class RssChannel implements Serializable {

    @Element(required = false,name ="title")
    private String title;

    @ElementList(inline = true,required = false)
    public List<RssItem> items;

    public RssChannel(String title, List<RssItem> items) {
        this.title = title;
        this.items = items;
    }

    public RssChannel() {
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<RssItem> getItems() {
        return items;
    }

    public void setItems(List<RssItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "RssChannel{" +
                "title='" + title + '\'' +
                ", items=" + items +
                '}';
    }


}

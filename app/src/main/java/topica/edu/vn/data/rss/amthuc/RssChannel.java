package topica.edu.vn.data.rss.amthuc;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.List;

@Root(name ="channel", strict = false)
public class RssChannel implements Serializable {

    @Element(name = "title",required = false)
    private String title;
    @ElementList(inline = true,required = false)
    public List<RssItem> item;

    public RssChannel() {
    }

    public RssChannel(String title, List<RssItem> item) {
        this.title = title;
        this.item = item;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<RssItem> getItem() {
        return item;
    }

    public void setItem(List<RssItem> item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "RssChannel{" +
                "title='" + title + '\'' +
                ", item=" + item +
                '}';
    }
}

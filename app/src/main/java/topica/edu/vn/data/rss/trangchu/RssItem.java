package topica.edu.vn.data.rss.trangchu;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.List;

@Root(name = "item",strict = false)
public class RssItem implements Serializable {
    @Element(name ="title")
    private String title;
    @Element(name ="link" )
    private String link;
    @Element(name ="pubDate" )
    private String time;
    @ElementList(inline = true,required = false )
    private List<Rssdescription> mdescriptionList;
   // private Rssdescription description;

    public RssItem(String title, String link, String time, List<Rssdescription> mdescriptionList) {
        this.title = title;
        this.link = link;
        this.time = time;
        this.mdescriptionList = mdescriptionList;
    }

    public RssItem() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<Rssdescription> getMdescriptionList() {
        return mdescriptionList;
    }

    public void setMdescriptionList(List<Rssdescription> mdescriptionList) {
        this.mdescriptionList = mdescriptionList;
    }

    @Override
    public String toString() {
        return "RssItem{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", time='" + time + '\'' +
                ", mdescriptionList=" + mdescriptionList +
                '}';
    }
}

package topica.edu.vn.data.rss.thethao;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.List;

@Root(name = "description",strict = false)
public class Rssdescription implements Serializable {
    @ElementList(inline = true,required = false)
    public List<Rssimg> listimg;
    //@Element (name = "img")


    public Rssdescription(List<Rssimg> listimg) {
        this.listimg = listimg;
    }

    public Rssdescription() {
    }

    public List<Rssimg> getListimg() {
        return listimg;
    }

    public void setListimg(List<Rssimg> listimg) {
        this.listimg = listimg;
    }

    @Override
    public String toString() {
        return "Rssdescription{" +
                "listimg=" + listimg +
                '}';
    }
}

package topica.edu.vn.data.rss.congnghe;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.List;

@Root(name = "description ",strict = false)
public class Rssdescription implements Serializable {
    @ElementList(inline = true,required = false)
    public List<Rssimg> lisimg;

    public Rssdescription(List<Rssimg> lisimg) {
        this.lisimg = lisimg;
    }

    public List<Rssimg> getLisimg() {
        return lisimg;
    }

    public void setLisimg(List<Rssimg> lisimg) {
        this.lisimg = lisimg;
    }

    public Rssdescription() {
    }

    @Override
    public String toString() {
        return "Rssdescription{" +
                "lisimg=" + lisimg +
                '}';
    }
}

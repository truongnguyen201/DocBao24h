package topica.edu.vn.data.rss.thethao;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name ="img",strict = false )
public class Rssimg {
    @Attribute(name = "src")
    private String src;

    public Rssimg(String src) {
        this.src = src;
    }

    public Rssimg() {
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getSrc() {
        return src;
    }

    @Override
    public String toString() {
        return "Rssimg{" +
                "src='" + src + '\'' +
                '}';
    }
}

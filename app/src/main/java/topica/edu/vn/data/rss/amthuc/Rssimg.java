package topica.edu.vn.data.rss.amthuc;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name ="img",strict = false)
public class Rssimg {
    @Attribute(name = "src")
    private String src;

    public Rssimg(String src) {
        this.src = src;
    }

    public Rssimg() {
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    @Override
    public String toString() {
        return "Rssimg{" +
                "src='" + src + '\'' +
                '}';
    }
}

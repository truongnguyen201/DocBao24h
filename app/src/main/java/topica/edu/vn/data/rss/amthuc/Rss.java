package topica.edu.vn.data.rss.amthuc;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "rss",strict = false)
public class Rss {
    @Element
    public RssChannel channel;

    public Rss(RssChannel channel) {
        this.channel = channel;
    }

    public Rss() {
    }

    public RssChannel getChannel() {
        return channel;
    }

    public void setChannel(RssChannel channel) {
        this.channel = channel;
    }

    @Override
    public String toString() {
        return "Rss{" +
                "channel=" + channel +
                '}';
    }
}

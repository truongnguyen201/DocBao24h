package topica.edu.vn.data.model;

public class TrangChu {
    public String title;
    public String img;
    public String link;
    public String time;

    public TrangChu(String title, String img, String link, String time) {
        this.title = title;
        this.img = img;
        this.link = link;
        this.time = time;
    }

    public TrangChu() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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
}

package notify.fcm.com.myapplication.imagedata;

/**
 * Created by 1405473 on 23-12-2016.
 */

public class ImageData {
    private int Id;
    private String ImageTitle;
    private String ImageLink;
    private String ImageDesc;

    public ImageData(){};

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getImageTitle() {
        return ImageTitle;
    }

    public void setImageTitle(String imageTitle) {
        ImageTitle = imageTitle;
    }

    public String getImageLink() {
        return ImageLink;
    }

    public void setImageLink(String imageLink) {
        ImageLink = imageLink;
    }

    public String getImageDesc() {
        return ImageDesc;
    }

    public void setImageDesc(String imageDesc) {
        ImageDesc = imageDesc;
    }
}

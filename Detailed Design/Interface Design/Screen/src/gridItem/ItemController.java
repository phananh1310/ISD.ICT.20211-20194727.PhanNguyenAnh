package gridItem;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class ItemController {

    @FXML
    private Text price;

    @FXML
    private Text name;

    @FXML
    private Text available;

    @FXML
    private ImageView imageView;

    private Item item;
    
    public void setData(Item item)
    {
    	this.item = item;
    	name.setText(item.getName());
    	price.setText(item.getPrice()+" $");
    	Image img = new Image(getClass().getResourceAsStream(item.getImgSrc()));
    	
    	imageView.setImage(img);
    }
}

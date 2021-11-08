package cartItem;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class CartItemController {

    @FXML
    private Text price;

    @FXML
    private Button name;

    @FXML
    private ImageView imageView;
    
    private CartItem cartItem;
    
    public void setData(CartItem cartItem)
    {
    	this.cartItem = cartItem;
    	name.setText(cartItem.getName());
    	price.setText(cartItem.getPrice()+" $");
    	Image img = new Image(getClass().getResourceAsStream(cartItem.getImgSrc()));
    	
    	imageView.setImage(img);
    }

}

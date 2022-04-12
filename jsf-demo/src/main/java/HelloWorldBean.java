import java.io.Serializable;

import javax.inject.Named;
import javax.faces.view.ViewScoped;

@Named("helloworld")
@ViewScoped
public class HelloWorldBean implements Serializable {

   public String getMessage() {
      return "Hello World from Fuertefentura";
   }

}
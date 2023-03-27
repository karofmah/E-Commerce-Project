/**

 This class contains the main method for starting the Spring Boot application.
 */
package no.ntnu.ecomback;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EcomBackApplication {

  /**

   The main method for starting the Spring Boot application.
   @param args the command line arguments to the application.
   */
  public static void main(String[] args) {
    SpringApplication.run(EcomBackApplication.class, args);
  }
}
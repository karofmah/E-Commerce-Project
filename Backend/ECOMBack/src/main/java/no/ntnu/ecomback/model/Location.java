/**
 * The Location class is used to represent the exact location of an Item
 */

package no.ntnu.ecomback.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Embeddable;


@Embeddable
@Schema(description = "The location of an item")
public class Location {

    @Schema(description = "The latitude of the location")
    private double latitude;
    @Schema(description = "The latitude of the location")
    private double longitude;



    /**
     * Gets latitude.
     * @return the latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Sets latitude.
     * @param latitude the latitude
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * Gets longitude.
     * @return the longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Sets longitude.
     * @param longitude the longitude
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * string override formats output of object
     * @return string
     */
    @Override
    public String toString() {
        return "(" + latitude + ", " + longitude + ")";
    }
}

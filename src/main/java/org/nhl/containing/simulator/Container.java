package org.nhl.containing.simulator;

import java.util.Date;

/**
 * Data model for a container.
 *
 * @author Ruben Bakker
 */
public class Container {

    private Date arrivalDate;
    private String arrivalTransportType; // Method of transport through which the container was delivered.
    private String arrivalCompany;
    private String owner;
    private Date departureDate;
    private String departureTransportType; // Method of transport through which the container must be dispatched.
    private String departureCompany;
    private float weight; // Weight in kilogrammes.
    private String contents; // What's in the container?

    public Container(Date arrivalDate, String arrivalTransportType, String arrivalCompany, String owner,
            Date departureDate, String departureTransportType, String departureCompany, float weight, String contents) {
        this.arrivalDate = arrivalDate;
        this.arrivalTransportType = arrivalTransportType;
        this.arrivalCompany = arrivalCompany;
        this.owner = owner;
        this.departureDate = departureDate;
        this.departureTransportType = departureTransportType;
        this.departureCompany = departureCompany;
        this.weight = weight;
        this.contents = contents;
    }
}

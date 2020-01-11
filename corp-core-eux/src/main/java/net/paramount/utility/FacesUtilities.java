package net.paramount.utility;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;

import net.paramount.domain.dummy.Car;

public interface FacesUtilities extends Serializable {
	List<Car> getCars();
	
	void init();

	void addDetailMessage(String message);

	void addDetailMessage(String message, FacesMessage.Severity severity);

	boolean isUserInRole(String role);
}

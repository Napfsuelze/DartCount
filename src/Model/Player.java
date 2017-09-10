package Model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class Player {

	private String name;
	private double average;
	private BooleanProperty isActive;
	
	public Player(String name) {
		this.name = name;
		this.average = 0;
		this.isActive = new SimpleBooleanProperty();
		this.onProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				setActive(arg2);
			}
			
		});
	}
	
	public String getName() {
		return name;
	}
	
	public void setActive(boolean bool) {
		isActive.set(bool);
	}
	
	public boolean isActive() {
		return isActive.getValue();
	}
	
	public BooleanProperty onProperty() {
		return isActive;
	}
	
	public String toString() {
		return name;
	}
	
	@Override
	public boolean equals(Object o) {
		Player compare = (Player) o;
		if (this.getName() == compare.getName())
			return true;
		return false;
	}
}

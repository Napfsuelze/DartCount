package model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * Repräsentiert einen Spieler
 */
public class Player {

	private String name;
	private double average;
	private BooleanProperty isActive;
	private int order;

    /**
     * Konstruktor
     * @param name
     *          Name des Spielers
     */
	public Player(String name) {
		this.name = name;
		this.average = 0;
		this.isActive = new SimpleBooleanProperty();
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
	    if (o == null)
		{
			return false;
		}

	    Player compare = (Player) o;
		if (this.getName() == compare.getName())
		{
			return true;
		}

		return false;
	}

	public void setOrder(int order)
	{
		this.order = order;
	}

	public int getOrder()
	{
		return this.order;
	}
}

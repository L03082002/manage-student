package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Class {
	private StringProperty id = new SimpleStringProperty();
	private StringProperty name = new SimpleStringProperty();
	private Faculty faculty;
	
	public Class(String id, String name, Faculty faculty) {
		setId(id);
		setName(name);
		this.faculty = faculty;
	}
	
	@Override
	public String toString() {
		return getName();
	}

	public final StringProperty idProperty() {
		return this.id;
	}
	
	public final String getId() {
		return this.idProperty().get();
	}
	
	public final void setId(final String id) {
		this.idProperty().set(id);
	}
	
	public final StringProperty nameProperty() {
		return this.name;
	}
	
	public final String getName() {
		return this.nameProperty().get();
	}
	
	public final void setName(final String name) {
		this.nameProperty().set(name);
	}

	public final Faculty getFaculty() {
		return faculty;
	}

	public final void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}
	
}

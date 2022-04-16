package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Faculty {
	private StringProperty id = new SimpleStringProperty();
	private StringProperty name = new SimpleStringProperty();
	private ObservableList<Class> classList = FXCollections.observableArrayList();
	
	public Faculty() {
		// TODO Auto-generated constructor stub
	}
	
	public Faculty(String id, String name) {
		setId(id);
		setName(name);
	}
	
	public void addClass(Class newClass) {
		classList.add(newClass);
	}
	
	public void addAllClass(Class... newClass) {
		classList.addAll(newClass);
	}
	
	@Override
	public String toString() {
		return getName();
	}

	public static void main(String[] args) {
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

	public final ObservableList<Class> getClassList() {
		return classList;
	}
	
	
}

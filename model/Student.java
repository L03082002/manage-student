package model;

import java.time.LocalDate;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Student {
	private StringProperty id = new SimpleStringProperty();
	private StringProperty firstName = new SimpleStringProperty();
	private StringProperty lastName = new SimpleStringProperty();
	private SimpleObjectProperty<LocalDate> dateOfBirth = new SimpleObjectProperty<>();
	private SimpleObjectProperty<Faculty> faculty = new SimpleObjectProperty<>();
	private SimpleObjectProperty<Class> stdClass = new SimpleObjectProperty<>();
	private StringProperty gender = new SimpleStringProperty();
	private StringProperty email = new SimpleStringProperty();
	
	
	public Student(String id, String firstName, String lastName, Faculty faculty, Class studyClass, String gender) {
		setId(id);
		setFirstName(firstName);
		setLastName(lastName);
		setFaculty(faculty);
		setStdClass(studyClass);
		setGender(gender);
	}
	
	public Student(String id, String firstName, String lastName, Faculty faculty, Class studyClass, LocalDate birthDate, String gender) {
		this(id, firstName, lastName, faculty, studyClass, gender);
		setDateOfBirth(birthDate);
	}
	
	public Student(String id, String firstName, String lastName, Faculty faculty, Class studyClass, LocalDate birthDate, String gender, String email ) {
		this(id, firstName, lastName, faculty, studyClass, birthDate, gender);
		setEmail(email);
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
	
	public final StringProperty firstNameProperty() {
		return this.firstName;
	}
	
	public final String getFirstName() {
		return this.firstNameProperty().get();
	}
	
	public final void setFirstName(final String firstName) {
		this.firstNameProperty().set(firstName);
	}
	
	public final StringProperty lastNameProperty() {
		return this.lastName;
	}
	
	public final String getLastName() {
		return this.lastNameProperty().get();
	}
	
	public final void setLastName(final String lastName) {
		this.lastNameProperty().set(lastName);
	}
	
	public final SimpleObjectProperty<LocalDate> dateOfBirthProperty() {
		return this.dateOfBirth;
	}
	
	public final LocalDate getDateOfBirth() {
		return this.dateOfBirthProperty().get();
	}
	
	public final void setDateOfBirth(final LocalDate dateOfBirth) {
		this.dateOfBirthProperty().set(dateOfBirth);
	}
	
	public final SimpleObjectProperty<Faculty> facultyProperty() {
		return this.faculty;
	}
	
	public final Faculty getFaculty() {
		return this.facultyProperty().get();
	}
	
	public final void setFaculty(final Faculty faculty) {
		this.facultyProperty().set(faculty);
	}
	
	public final SimpleObjectProperty<Class> stdClassProperty() {
		return this.stdClass;
	}
	
	public final Class getStdClass() {
		return this.stdClassProperty().get();
	}
	
	public final void setStdClass(final Class stdClass) {
		this.stdClassProperty().set(stdClass);
	}
	
	public final StringProperty genderProperty() {
		return this.gender;
	}
	
	public final String getGender() {
		return this.genderProperty().get();
	}
	
	public final void setGender(final String gender) {
		this.genderProperty().set(gender);
	}
	
	public final StringProperty emailProperty() {
		return this.email;
	}
	
	public final String getEmail() {
		return this.emailProperty().get();
	}
	
	public final void setEmail(final String email) {
		this.emailProperty().set(email);
	}
	
}

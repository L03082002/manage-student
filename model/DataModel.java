package model;

import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

public class DataModel {
	private ObservableList<Student> studentList = FXCollections.observableArrayList();
	private FilteredList<Student> filteredStudentList;
	private ObservableList<Faculty> facultyList = FXCollections.observableArrayList();
	private Student selectedStd;
	
	public DataModel() {
		this.addSampleData();
		filteredStudentList = new FilteredList<>(this.studentList);
	}
	
	/**
	 * Thêm dữ liệu mẫu
	 */
	private void addSampleData() {
		// 3 khoa
		Faculty it = new Faculty("01", "IT");
		Faculty eco = new Faculty("02", "Economy");
		Faculty law = new Faculty("03", "Law");
		
		// Lớp
		Class it01 = new Class("it01", "21SCMT", it);
		Class it02 = new Class("it02", "20CNTT1B", it);
		Class it03 = new Class("it03", "20CNTT1A", it);
		Class eco1 = new Class("eco01", "20KT1", eco);
		Class eco2 = new Class("eco02", "20KT2", eco);
		Class law1 = new Class("law1", "19LUAT01", law);
		
		it.addAllClass(it01, it02, it03);
		eco.addAllClass(eco1, eco2);
		law.addClass(law1);
		
		studentList.add(new Student("01", "Lê Quang Tường", "Trần", it, it01, LocalDate.of(2001, 5, 3),"Male"));
		studentList.add(new Student("02", "Gia Huy", "Trần", it, it01, LocalDate.of(2001, 4, 29),"Male"));
		studentList.add(new Student("03", "Thị Kim Ánh", "Nguyễn", law, law1, LocalDate.of(2000, 10, 8), "Female"));
		
		facultyList.addAll(it, eco, law);
		
	}

	public final ObservableList<Student> getStudentList() {
		return studentList;
	}

	public final void setStudentList(ObservableList<Student> studentList) {
		this.studentList = studentList;
	}

	public final Student getSelectedStd() {
		return selectedStd;
	}

	public final void setSelectedStd(Student selectedStd) {
		this.selectedStd = selectedStd;
	}

	public final ObservableList<Faculty> getFacultyList() {
		return facultyList;
	}

	public final FilteredList<Student> getFilteredStudentList() {
		return filteredStudentList;
	}
	
	

}

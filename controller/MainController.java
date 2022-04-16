package controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.function.Predicate;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import model.*;
import model.Class;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class MainController {
	
	private ToggleGroup radioBtnGroup;
	private DataModel model;
	
	@FXML private Button insertBtn;
	@FXML private Button deleteBtn;
	@FXML private TextField idField;
	@FXML private TextField emailField;
	@FXML private TextField lastNameField;
	@FXML private TextField firstNameField;
	@FXML private ChoiceBox<Faculty> facultyCb;
	@FXML private ChoiceBox<Class> classCb;
	@FXML private DatePicker birthDatePicker;
	@FXML private RadioButton male;
	@FXML private RadioButton female;
	@FXML private TableView<Student> studentTable;
	
	@FXML private TableColumn<Student, String> idCol;
	@FXML private TableColumn<Student, String> lastNameCol;
	@FXML private TableColumn<Student, String> firstNameCol;
	@FXML private TableColumn<Student, Faculty> facultyCol;
	@FXML private TableColumn<Student, Class> classCol;
	@FXML private TableColumn<Student, LocalDate> dateOfBirthCol;
	@FXML private TableColumn<Student, String> genderCol;
	@FXML private TableColumn<Student, String> emailCol;
	
	public MainController() {
		model = new DataModel();
		radioBtnGroup = new ToggleGroup();
	}
	
	@FXML
	private void initialize() {
		// Nhóm nút RadioButton
		male.setToggleGroup(radioBtnGroup);
		female.setToggleGroup(radioBtnGroup);
		
		
		studentTable.setItems(model.getFilteredStudentList());
		studentTable.getSelectionModel().selectedItemProperty().addListener((obs, old, newItem) -> {
			if (newItem != null) {
				idField.setDisable(true);
				deleteBtn.setDisable(false);
				model.setSelectedStd(newItem);
				this.showInfo();
			} else {
				model.setSelectedStd(null);
				deleteBtn.setDisable(true);
			}
		});
		
		facultyCb.setItems(model.getFacultyList());
		
		facultyCb.getSelectionModel().selectFirst();
		classCb.setItems(facultyCb.getSelectionModel().getSelectedItem().getClassList());
		classCb.getSelectionModel().selectFirst();

		
		// ChangeListener thay đổi danh sách lớp khi chọn khoa mới.
		facultyCb.getSelectionModel().selectedItemProperty().addListener((obs, old, newFaculty) -> {
			if (newFaculty != null) {
				classCb.setItems(newFaculty.getClassList());
				classCb.getSelectionModel().selectFirst();
				if (model.getSelectedStd() == null) {
					model.getFilteredStudentList().setPredicate(createPredicate(newFaculty.getName()));
				}
			} else {
				classCb.getSelectionModel().clearSelection();
				model.getFilteredStudentList().setPredicate(createPredicate(null));
			}
		});
		
		// ChangeListener thay đổi dan sách học sinh khi đổi lớp
		classCb.getSelectionModel().selectedItemProperty().addListener((obs, old, newClass) -> {
			if (newClass != null) {
				if (model.getSelectedStd() == null) {
					model.getFilteredStudentList().setPredicate(createPredicate(newClass.getName()));
				}
			}
		});
		
		idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		facultyCol.setCellValueFactory(new PropertyValueFactory<>("faculty"));
		classCol.setCellValueFactory(new PropertyValueFactory<>("stdClass"));
		dateOfBirthCol.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
		genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
		emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
		
		// 	setCellFactory để chỉnh sửa trong ô.
		firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
		firstNameCol.setOnEditCommit(e -> {
			e.getRowValue().setFirstName(e.getNewValue());
		});
		
		lastNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
		lastNameCol.setOnEditCommit(e -> {
			e.getRowValue().setLastName(e.getNewValue());
		});
		
		facultyCol.setCellFactory(ChoiceBoxTableCell.forTableColumn(model.getFacultyList()));
		facultyCol.setOnEditCommit(e -> {
			e.getRowValue().setFaculty(e.getNewValue());
			// Tạo list phù hợp với Khoa mới được chọn & setCellFactory cho classCol
			ObservableList<Class> specificList = model.getSelectedStd().getFaculty().getClassList();
			classCol.setCellFactory(ChoiceBoxTableCell.forTableColumn(specificList));
			// Tự động chọn Lớp mới nếu đổi Khoa khác
			e.getRowValue().setStdClass(specificList.get(0));
		});
		
		classCol.setOnEditCommit(e -> {
			e.getRowValue().setStdClass(e.getNewValue());
		});
		
		genderCol.setCellFactory(ChoiceBoxTableCell.forTableColumn("Male", "Female"));
		genderCol.setOnEditCommit(e -> {
			e.getRowValue().setGender(e.getNewValue());
			if (e.getNewValue().equals("Male")) {
				male.setSelected(true);
			} else {
				female.setSelected(true);
			}
		});
		
		emailCol.setCellFactory(TextFieldTableCell.forTableColumn());
		emailCol.setOnEditCommit(e -> {
			e.getRowValue().setEmail(e.getNewValue());
		});
		
	}
	
	private boolean searchStudent(Student s, String text) {
		if (s.getFaculty().getName().equals(text) || s.getStdClass().getName().equals(text)) {
			return true;
		}
		return false;
	}
	
	private Predicate<Student> createPredicate(String text) {
		return student -> {
			if (text == null || text.isEmpty()) return true;
			return searchStudent(student, text);
		};
	}

	/**
	 * Hiện cửa sổ thông tin app
	 */
	@FXML
	private void showAboutUs() {
		Alert about = this.createAlert(AlertType.INFORMATION, "Phần mềm quản lý thông tin sinh viên", "", "Phần mềm quản lý sinh viên", ButtonType.CLOSE);
		about.show();
	}
	
	/**
	 * Hiện cửa sổ hỏi xác nhận thoát chương trình
	 */
	@FXML
	private void askConfirm() {
		Alert confirm = this.createAlert(AlertType.CONFIRMATION, "Thoát chương trình?", "", "Thoát");
		confirm.showAndWait().ifPresent(response -> {
			if (response == ButtonType.OK) {
				Platform.exit();
			}
		});
	}
	
	/**
	 * Thêm hoặc cập nhật thông tin sinh viên
	 */
	@FXML
	public void insertStudent() {
		// Kiểm tra dữ liệu trước khi thêm sinh viên
		boolean valid = checkInput();
		if (!valid) {
			Alert warn = this.createAlert(AlertType.WARNING, "Chưa điền đủ thông tin", "", "Điền thông tin", ButtonType.CLOSE);
			warn.show();
			return;
		}
		
		String id = this.idField.getText();
		String lastName = this.lastNameField.getText();
		String firstName = this.firstNameField.getText();
		LocalDate birthDate = birthDatePicker.getValue();
		Faculty selectedFaculty = this.facultyCb.getValue();
		Class selectedClass = this.classCb.getValue();
		RadioButton choosedBtn = (RadioButton) radioBtnGroup.getSelectedToggle();
		String gender = choosedBtn.getText();
		
		if (model.getSelectedStd() == null) {
			Student newStd = new Student(id, firstName, lastName, selectedFaculty, selectedClass, birthDate, gender);
			model.getStudentList().add(newStd);
			this.clearFields();
		} else {
			Student selectedStd = model.getSelectedStd();
			System.out.println(selectedStd == studentTable.getSelectionModel().getSelectedItem());
			selectedStd.setLastName(lastName);
			selectedStd.setFirstName(firstName);
			selectedStd.setDateOfBirth(birthDate);
			selectedStd.setFaculty(selectedFaculty);
			selectedStd.setStdClass(selectedClass);
			selectedStd.setGender(gender);
			studentTable.refresh();
		}
	}
	
	/**
	 * Xóa sinh viên khỏi TableView
	 */
	@FXML
	private void deleteStudent() {
		Student selected = studentTable.getSelectionModel().getSelectedItem();
		studentTable.getItems().remove(selected);
	}
	
	/**
	 * Gỡ ràng buộc, xóa text của field, và bỏ chọn row
	 */
	@FXML
	private void clearFields() {
		studentTable.getSelectionModel().clearSelection();
		idField.setDisable(false);
		idField.setText("");
		emailField.setText("");
		lastNameField.setText("");
		firstNameField.clear();
		birthDatePicker.setValue(null);
		facultyCb.getSelectionModel().clearSelection();
		male.setSelected(false);
		female.setSelected(false);
	}
	
	/**
	 * Ràng buộc thuộc tính của sinh viên đang chọn cho field, choicebox.
	 */
	@FXML
	private void showInfo() {
		Student std = model.getSelectedStd();

		idField.setText(std.getId());
		emailField.setText(std.getEmail());
		lastNameField.setText(std.getLastName());
		firstNameField.setText(std.getFirstName());
		birthDatePicker.setValue(std.getDateOfBirth());
		facultyCb.setValue(std.getFaculty());
		classCb.setValue(std.getStdClass());
		
		if (std.getGender().equals("Male")) {
			male.setSelected(true);
		} else {
			female.setSelected(true);
		}
	}
	
	/**
	 * Kiểm tra input
	 * @return true nếu đầy đủ, false nễu thiếu.
	 */
	private boolean checkInput() {
		boolean isValid = true;		
		if (idField.getText().isEmpty() || lastNameField.getText().isEmpty() || firstNameField.getText().isEmpty()) {
			isValid = false;
		}
		if (facultyCb.getValue() == null || classCb.getValue() == null) {
			isValid = false;
		}
		if (birthDatePicker.getValue() == null) {
			isValid = false;
		}
		if (!male.isSelected() && !female.isSelected()) {
			isValid = false;
		}
		return isValid;
	}
	
	private Alert createAlert(AlertType type, String content, String header, String title, ButtonType... buttonTypes) {
		Alert alert = this.createAlert(type, content, header, title);
		alert.getButtonTypes().addAll(buttonTypes);
		return alert;
	}
	
	private Alert createAlert(AlertType type, String content, String header, String title) {
		Alert alert = new Alert(type, content);
		alert.setHeaderText(header);
		alert.setTitle(title);
		return alert;
	}

}

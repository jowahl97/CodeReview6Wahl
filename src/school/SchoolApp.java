package school;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;
    public class SchoolApp
            extends Application {

        private ListView<Teacher> listView = new ListView<>();
        private ObservableList<Teacher> data;
        private  TextField teacherID = new TextField();
        private  TextField firstName = new TextField();
        private  TextField lastName = new TextField();
        private  TextField email = new TextField();
        private  SchoolAccess dbaccess;

        public  static void main(String [] args) {

            Application.launch(args);
        }

        @Override
        public  void init() {

            try  {
                dbaccess = new SchoolAccess();
            }
            catch (Exception e) {

                displayException(e);
            }
        }

        @Override
        public  void stop() {

            try {
                dbaccess.closeDb();
            }
            catch (Exception e) {

                displayException(e);
            }
        }

        @Override
        public void  start(Stage primaryStage) {

            primaryStage.setTitle( "School Statistics of Teachers");

            // gridPane layout

            BorderPane borderPane = new BorderPane();
            GridPane pane = new GridPane();
            pane.setHgap(5);
            pane.setVgap(5);
            pane.setPadding(new Insets(25,25,25,25));

            // list view, listener and list data

            listView = new  ListView<>();
            listView.getSelectionModel().selectedIndexProperty().addListener(
                    new  ListSelectChangeListener());
            data = getDbData();
            listView.setItems(data);

            ListView<Teacher> teacherListView = new ListView<Teacher>(data);
            teacherListView.setCellFactory(param -> new ListCell<Teacher>() {
                @Override
                protected void updateItem(Teacher teacher, boolean empty) {
                    super.updateItem(teacher, empty);

                    if (empty || teacher == null || teacher.getLastName() == null) {
                        setText(null);
                    } else {
                        setText(teacher.getFirstName() + ", " + teacher.getLastName());
                    }
                }
            });

            teacherListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    teacherID.setText(String.valueOf(teacherListView.getSelectionModel().getSelectedItem().getTeacherID()));
                    firstName.setText(teacherListView.getSelectionModel().getSelectedItem().getFirstName());
                    lastName.setText(teacherListView.getSelectionModel().getSelectedItem().getLastName());
                    email.setText(teacherListView.getSelectionModel().getSelectedItem().getEmail());
                }
            });

            HBox teacherList = new HBox(teacherListView);

            Label idLabel = new Label("ID");
            Label firstNameLabel = new Label("First  name");
            Label lastNameLabel = new Label("Last name");
            Label emailLabel = new Label("Email");
            Label teacherLabel = new Label("Teachers");

            pane.add(idLabel, 0, 1);
            pane.add(teacherID, 1,1);
            pane.add(firstNameLabel, 0,2);
            pane.add(firstName, 1, 2);
            pane.add(lastNameLabel, 0, 3);
            pane.add(lastName, 1, 3);
            pane.add(emailLabel, 0, 4);
            pane.add(email, 1, 4);

            borderPane.setLeft(teacherList);
            borderPane.setCenter(pane);


            // scene

            Scene scene = new  Scene(borderPane, 750 , 400 ); // width = 750, height = 400
            primaryStage.setScene(scene);
            primaryStage.show();

            // initial selection
            listView.getSelectionModel().selectFirst(); // does nothing if no data

        } // start()

        private   class  ListSelectChangeListener implements  ChangeListener<Number> {

            @Override
            public   void  changed(ObservableValue<? extends Number> ov,
                                   Number old_val, Number new_val) {

                if  ((new_val.intValue() < 0 ) || (new_val.intValue() >= data.size())) {

                    return ; // invalid data
                }

                // set name and desc fields for the selected teacher
                Teacher teacher = data.get(new_val.intValue());
                teacherID.setText(String.valueOf(teacher.getTeacherID()));
                firstName.setText(teacher.getFirstName());
                lastName.setText(teacher.getLastName());
                email.setText(teacher.getEmail());
            }
        }

        private  ObservableList<Teacher> getDbData() {

            List<Teacher> list = null ;

            try  {
                list = dbaccess.getAllRows();
            }
            catch  (Exception e) {

                displayException(e);
            }

            ObservableList<Teacher> dbData = FXCollections.observableList(list);
            return  dbData;
        }


        private   void  displayException(Exception e) {

            System.out.println( "###### Exception ######" );
            e.printStackTrace();
            System.exit( 0 );
        }
    }

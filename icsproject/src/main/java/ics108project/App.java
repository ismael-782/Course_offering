package ics108project;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
 
public class App extends Application implements Serializable {

    // constants and fixed dimensions with creating the pan and scense and their settings
    private final int STAGE_WIDTH = 1400;
    private final int STAGE_HEIGHT = 800;
    private final String BUTTON_FONT = "Verdana";
    private final double BUTTON_FONT_SIZE = 12;
    private TableView<Section> table = new TableView<Section>();
    GridPane root = new GridPane();
    GridPane root2 = new GridPane();
    Scene Scene1 = new Scene(root, STAGE_WIDTH, STAGE_HEIGHT, Color.TRANSPARENT);
    Scene Scene2 = new Scene(root2, STAGE_WIDTH, STAGE_HEIGHT, Color.TRANSPARENT);
    //----------------------------------------------------------------------------------------

    /********************** buttons ******************/
    Button addsc1 = new Button("Add course");
    Button removesc1 = new Button("remove course");
    Button nextsc1 = new Button("Next Page");
    Button loadPrevious = new Button("Start with saved schedule");
    //scene 2 buttons//
    Button removec2 = new Button("remove course");
    Button addsc2 = new Button("Show section");
    Button saveing  = new Button("Save schedule");
    Button back = new Button("go back to course offering");
    /*******************************************************/
    Label titLabel = new Label("COURSES AVAILABLE");
    Label info1Label = new Label("MAJOR: swe");
    Label info2Label = new Label("TERM: 222");
    Text note = new Text("THE COURSE WILL NOT BE RIGESTERD IN THESE CASES:  1- IF THERE IS TIME CONFLICT  2 - IF THERE IS EXCISTING SECTION");

    //-------- schedule scene2 ---------------//
    ListView<GridPane> listView = new ListView<>();
    GridPane schedule = new GridPane(); //putting the grid pane inside the listview
    ListView <Section> basketListView = new ListView<Section>(); // listview to hold the regestered sections
    ObservableList<Section> sectionsBasket = FXCollections.observableArrayList();

    Label[] weekDays = { new Label("Sunday"), new Label("Monday"),new Label("Tuesday"),
            new Label("Wednesday"),new Label("Thursday")};
    Label[] times = {new Label("7:00"), new Label("8:00"), new Label("9:00"), new Label("10:00"),new Label("11:00"),
                new Label("12:00"),new Label("1:00"), new Label("2:00"), new Label("3:00"), new Label("4:00"), new Label("5:00"), new Label("6:00")};
    //--------------------------------------//

    /**************************TableView variables and columns********************************/
    final Label label = new Label("Course Offering");
    TableColumn Course = new TableColumn("Course Name");
    TableColumn Section = new TableColumn("Sections");
    TableColumn Activity = new TableColumn("Activity");
    TableColumn CRN = new TableColumn("CRN");
    TableColumn Instructor = new TableColumn("Instructor");
    TableColumn Day = new TableColumn("Day");
    TableColumn Time = new TableColumn("Time");
    TableColumn Location = new TableColumn("Location");
    TableColumn Status = new TableColumn("Status");
    TableColumn Waitlist = new TableColumn("Waitlist");
    TableColumn<Section,HBox> statusColumn = new TableColumn<Section,HBox>("Regesterd");
    ObservableList<Section> allAvailableSections = FXCollections.observableArrayList();
    /******************************************************************************************/

    //================================================================================================================================
    File courseoffering = new File("C:\\Users\\Lenovo\\Desktop\\icsproject\\Project ICS108\\ProjectSampleFiles\\CourseOffering.csv");
    File degreeplan = new File("C:\\Users\\Lenovo\\Desktop\\icsproject\\Project ICS108\\ProjectSampleFiles\\DegreePlan2.csv");
    File finishedcourses = new File("C:\\Users\\Lenovo\\Desktop\\icsproject\\Project ICS108\\ProjectSampleFiles\\FinishedC2.csv");
    //================================================================================================================================

    //================== Arrays ========================================================//
    ArrayList<Course> DPlist= new ArrayList<>();
    ArrayList<Section> COlist= new ArrayList<>();
    ArrayList<Course> FClist = new ArrayList<>();
    ArrayList<Section> Displaysection = new ArrayList<>();
    ArrayList<Section> Sectionadded = new ArrayList<>();
    //==================================================================================//
    



    public void start(Stage stage) { 

        statusColumn.setResizable(false); //activating the tableview

        /**************************functionality for buttons************************/
        nextsc1.setOnAction(e -> {
            stage.setScene(Scene2);
        });            


        back.setOnAction(e -> {
            stage.setScene(Scene1);
        });

        addsc1.setOnAction(add);
        addsc2.setOnAction(add1);;

        removesc1.setOnAction(remove);
        removec2.setOnAction(remove1);

        saveing.setOnAction(e -> {
            try {
                FileOutputStream outputStream = new FileOutputStream("SavedSchedule.dat");
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                Section[] sectionsArr = Sectionadded.toArray(new Section[Sectionadded.size()]);
                objectOutputStream.writeObject(sectionsArr);
                outputStream.close();
                savedSchudele.displaysaved();
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        });

        loadPrevious.setOnAction(e -> {
            try {
                FileInputStream fileIn = new FileInputStream("SavedSchedule.dat");
                ObjectInputStream objectIn = new ObjectInputStream(fileIn);
                Object object = objectIn.readObject();

                if (object instanceof Section[]) {
                    System.out.println(Arrays.toString((Section[]) object));
                    Sectionadded.addAll(new ArrayList<Section>(Arrays.asList((Section[]) object)));
                }
                objectIn.close();
                basketListView.getItems().addAll(Sectionadded);
                stage.setScene(Scene2);
            } catch (Exception k) {
                k.printStackTrace();
            }
            
        });
        /****************************************************************************************************/



        //======================================== Table of courses =====================================//
        listView.getItems().add(schedule);
        listView.setPadding(new Insets(15));

        schedule.setPadding(new Insets(15));
        schedule.setGridLinesVisible(true);

        for(int i = 0; i < weekDays.length; i++)
        {
            //Filling Columns
            weekDays[i].setFont(Font.font("Calibri", FontWeight.BOLD, FontPosture.REGULAR, 20));
            schedule.add(weekDays[i], i+1, 0);
        }

        //Filling Rows
        for (int i = 0; i < times.length; i++) {
            times[i].setAlignment(Pos.CENTER);
            schedule.add(times[i], 0, i + 1);
            schedule.setMouseTransparent(true);
        }

        ColumnConstraints[] columns = new ColumnConstraints[weekDays.length + 1]; //Plus 1 for the first Empty Column
        RowConstraints[] rows = new RowConstraints[times.length + 1]; //Plus 1 for the first Empty Row

        //First Empty Column
        columns[0] = new ColumnConstraints();
        columns[0].setPercentWidth(3);
        schedule.getColumnConstraints().add(columns[0]);

        for (int i = 0; i < weekDays.length ; i++)
        {
            columns[i+1] = new ColumnConstraints();
            columns[i+1].setPercentWidth(20);
            columns[i+1].setHalignment(HPos.CENTER);
            schedule.getColumnConstraints().add(columns[i+1]);
        }

        //First Empty Row
        rows[0] = new RowConstraints();
        rows[0].setPrefHeight(20);
        schedule.getRowConstraints().add(rows[0]);

        for (int i = 0; i < times.length ; i++)
        {
            rows[i+1] = new RowConstraints();
            rows[i+1].setPrefHeight(50);
            rows[i+1].setValignment(VPos.CENTER);
            rows[i+1].setFillHeight(true);
            schedule.getRowConstraints().add(rows[i+1]);
        }
        //----------------------------------------------------------------------------------------------------------//
        applyLayout();
        applyStyle();
        setTableColFactory();

        table.getItems().addAll(Displaying());
        table.setEditable(true);
        Scene2.setRoot(root2);
        Scene1.setRoot(root);
        stage.setScene(Scene1);
        stage.setWidth(STAGE_WIDTH);
        stage.setHeight(STAGE_HEIGHT);
        stage.setTitle("Course offering_KFUPM");
        stage.show();
    }

    /**********************************************************/
    public static void main(String[] args) {
        launch();
    }
    /**********************************************************/

    /*
     * now every thing below is just helping methods
     */

     //===========First methos that set the Gridpane====================//
    private void applyLayout(){
        RowConstraints topRow1 = new RowConstraints();
        RowConstraints midRow1 = new RowConstraints();
        RowConstraints buttomRow1 = new RowConstraints();
        ColumnConstraints leftCol1 = new ColumnConstraints();
        ColumnConstraints midCol1 = new ColumnConstraints();
        ColumnConstraints rightCol1 = new ColumnConstraints();

        topRow1.setPercentHeight(30);
        midRow1.setPercentHeight(60);
        buttomRow1.setPercentHeight(10);
        //--------------
        leftCol1.setPercentWidth(1);
        midCol1.setPercentWidth(98);
        rightCol1.setPercentWidth(1);

        root.getRowConstraints().addAll(topRow1, midRow1,buttomRow1);
        root.getColumnConstraints().addAll(leftCol1,midCol1 ,rightCol1);

        //-----------topRow--------//
        VBox infBox = new VBox(25);
        infBox.getChildren().addAll(titLabel,info1Label,info2Label,note);
        infBox.setAlignment(Pos.CENTER_LEFT);

        VBox addBox = new VBox();
        addBox.getChildren().addAll(loadPrevious);
        addBox.setAlignment(Pos.BOTTOM_RIGHT); 
        
        HBox topB = new HBox(10);
        topB.getChildren().addAll(infBox,addBox);

        //-------buttomRow-----------//
        HBox reges = new HBox(10);
        reges.getChildren().addAll(addsc1, removesc1);
        reges.setAlignment(Pos.BOTTOM_RIGHT); 


        HBox down = new HBox(900);
        down.getChildren().addAll(nextsc1,reges);
        down.setAlignment(Pos.BOTTOM_LEFT);

        //-----------tableview---------------//
        VBox vbox = new VBox();
        table.getColumns().addAll(Course, Section ,Activity, CRN,Instructor,Day,Time,Location,Status,Waitlist,statusColumn);
        vbox.setSpacing(10);
        vbox.getChildren().addAll(label, table);

        root.add(vbox,1 , 1);
        root.add(topB,1,0);
        root.add(down,1,2);
        root.setPadding(new Insets(10,10,10,10));

        /***********************************second scene edits*********************************************/

        RowConstraints topRow2 = new RowConstraints();
        RowConstraints buttomRow2 = new RowConstraints();
        ColumnConstraints leftCol2 = new ColumnConstraints();
        ColumnConstraints rightCol2 = new ColumnConstraints();

        topRow2.setPercentHeight(90);
        buttomRow2.setPercentHeight(10);

        leftCol2.setPercentWidth(70);
        rightCol2.setPercentWidth(30);

        root2.getRowConstraints().addAll(topRow2,buttomRow2);
        root2.getColumnConstraints().addAll(leftCol2,rightCol2);

        HBox show = new HBox(15);
        show.getChildren().addAll(addsc2,removec2);
        show.setAlignment(Pos.CENTER);
 
        HBox saveBox = new HBox(15);
        saveBox.getChildren().addAll(saveing, back);
        saveBox.setAlignment(Pos.CENTER_LEFT);


        Label label55 = new Label("Basket: ");
        Font font = Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 12);
        label55.setFont(font);

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(5, 5, 5, 5));
        layout.getChildren().addAll(label55,basketListView );
        layout.setStyle("-fx-background-color: BEIGE");


        root2.setHgap(10);
        root2.setVgap(10);
        root2.add(saveBox,0,1);
        root2.add(listView,0,0);
        root2.add(layout,1,0);
        root2.add(show,1,1);
        root2.setPadding(new Insets(10,10 ,10 ,10 ));
    }
    /************************************************************************************/

    //================== Second method to apply the style on the nodes ====================//
    private void applyStyle() {

        addsc1.setMinSize(130, 50);
        addsc1.setTextFill(Color.WHITE);
        addsc1.setFont(Font.font(BUTTON_FONT, FontWeight.BOLD, BUTTON_FONT_SIZE));
        addsc1.setStyle("-fx-background-radius: 50; -fx-background-color: rgb(50, 50, 50);");

        removesc1.setMinSize(130, 50);
        removesc1.setTextFill(Color.WHITE);
        removesc1.setFont(Font.font(BUTTON_FONT, FontWeight.BOLD, BUTTON_FONT_SIZE));
        removesc1.setStyle("-fx-background-radius: 50; -fx-background-color: rgb(50, 50, 50);");

        nextsc1.setMinSize(130, 50);
        nextsc1.setTextFill(Color.WHITE);
        nextsc1.setFont(Font.font(BUTTON_FONT, FontWeight.BOLD, BUTTON_FONT_SIZE));
        nextsc1.setStyle("-fx-background-radius: 50; -fx-background-color: rgb(50, 50, 50);");

        loadPrevious.setMinSize(180, 80);
        loadPrevious.setTextFill(Color.WHITE);
        loadPrevious.setFont(Font.font(BUTTON_FONT, FontWeight.BOLD, BUTTON_FONT_SIZE));
        loadPrevious.setStyle("-fx-background-radius: 50; -fx-background-color: rgb(50, 50, 50);");

        saveing.setMinSize(130, 50);
        saveing.setTextFill(Color.WHITE);
        saveing.setFont(Font.font(BUTTON_FONT, FontWeight.BOLD, BUTTON_FONT_SIZE));
        saveing.setStyle("-fx-background-radius: 50; -fx-background-color: rgb(50, 50, 50);");

        back.setMinSize(130, 50);
        back.setTextFill(Color.WHITE);
        back.setFont(Font.font(BUTTON_FONT, FontWeight.BOLD, BUTTON_FONT_SIZE));
        back.setStyle("-fx-background-radius: 50; -fx-background-color: rgb(50, 50, 50);");

        removec2.setMinSize(130, 50);
        removec2.setTextFill(Color.WHITE);
        removec2.setFont(Font.font(BUTTON_FONT, FontWeight.BOLD, BUTTON_FONT_SIZE));
        removec2.setStyle("-fx-background-radius: 50; -fx-background-color: rgb(50, 50, 50);");

        addsc2.setMinSize(130, 50);
        addsc2.setTextFill(Color.WHITE);
        addsc2.setFont(Font.font(BUTTON_FONT, FontWeight.BOLD, BUTTON_FONT_SIZE));
        addsc2.setStyle("-fx-background-radius: 50; -fx-background-color: rgb(50, 50, 50);");


        label.setTextFill(Color.WHITE);
        label.setFont(Font.font(BUTTON_FONT, FontWeight.BOLD, 15));
        label.setFont(new Font("Arial", 20));

        titLabel.setTextFill(Color.WHITE);
        titLabel.setFont(Font.font(BUTTON_FONT, FontWeight.BOLD, 30));

        info1Label.setFont(Font.font(BUTTON_FONT, FontWeight.BOLD, 15));
        info1Label.setTextFill(Color.WHITE);

        info2Label.setTextFill(Color.WHITE);
        info2Label.setFont(Font.font(BUTTON_FONT, FontWeight.BOLD, 15));

        note.setFill(Color.YELLOW);
        note.setFont(Font.font(BUTTON_FONT, FontWeight.BOLD, 15));
        
        
        root.setStyle("-fx-background-color: #155555;");
        root2.setStyle("-fx-background-color: #155555;");

    }    
    /************************************************************************************************/

    //====================== creating the TableView' columns ==========================================//
    private void setTableColFactory(){
        Course.setCellValueFactory(new PropertyValueFactory<Section, String>("name"));
        Section.setCellValueFactory(new PropertyValueFactory<Section, String>("section"));
        CRN.setCellValueFactory(new PropertyValueFactory<Section, String>("crn"));
        Activity.setCellValueFactory(new PropertyValueFactory<Section, String>("activity"));
        Instructor.setCellValueFactory(new PropertyValueFactory<Section, String>("instructor"));
        Day.setCellValueFactory(new PropertyValueFactory<Section, String>("day"));
        Time.setCellValueFactory(new PropertyValueFactory<Section, String>("time"));
        Location.setCellValueFactory(new PropertyValueFactory<Section, String>("location"));
        Status.setCellValueFactory(new PropertyValueFactory<Section, String>("status"));
        Waitlist.setCellValueFactory(new PropertyValueFactory<Section, String>("waitlist"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<Section, HBox>("regestered"));
    }        
    //=================================================================================================//

    //=========================== displaying the avaliable courses on the TablrView ==============================//
    public ArrayList<Section> Displaying(){
        try {
            Scanner get1 = new Scanner(degreeplan);
            Scanner get2 = new Scanner(courseoffering);
            Scanner get3 = new Scanner(finishedcourses);
            
            while(get1.hasNext()){
                String courseA = get1.nextLine();
                String[] splitcourse = courseA.split(",");
                Course course = new Course(splitcourse[0],Integer.parseInt(splitcourse[1]),splitcourse[2],splitcourse[3]);
                DPlist.add(course);
            }
            while(get2.hasNext()){
               String courseofile = get2.nextLine();
               String[] splitCO = courseofile.split(",");
               String[] splitCS = splitCO[0].split("-");

               for(int i = 0;i<DPlist.size();i++){
                  if(DPlist.get(i).getName().equals(splitCS[0])){
                    Section sectionCO = new Section(DPlist.get(i),splitCS[1],splitCO[1],splitCO[2],splitCO[4],splitCO[5],splitCO[6],splitCO[7],splitCO[8],splitCO[9]);
                    COlist.add(sectionCO);
                  }
               }
            }
            while(get3.hasNext()){
                String fcfile = get3.nextLine();
                String[] fcsplit = fcfile.split(",");
                for(int i = 0;i<DPlist.size();i++){
                    if(DPlist.get(i).getName().equals(fcsplit[0])){
                        FClist.add(DPlist.get(i));
                    }
                 }
            }
            ArrayList<Course> display = DPlist;
            
            for(int i = 0;i<FClist.size();i++){
                display.remove(FClist.get(i));
            }

            for(int i = 0;i<display.size();i++){
                String preCs=display.get(i).getPrerequisite();
                for(int j = 0;j<DPlist.size();j++){
                    if(DPlist.get(j).getName().equals(preCs)){
                        Course preC = DPlist.get(j);
                        for(int k = 0; k<FClist.size();k++){
                            if(preC.getName().equals(FClist.get(k))){
                            }
                            else{
                                display.remove(i);
                            }
                        }
                    }
                 }
            }
            for(int i = 0;i<display.size();i++){
                for(int j = 0;j<COlist.size();j++){
                    if(display.get(i).equals(COlist.get(j).getCourse())){
                        Displaysection.add(COlist.get(j));
                    }
                }
            }
            
        } catch (Exception e) {
            System.out.println(e);
        }
        return Displaysection;
    }
    //=====================================================================================//

    //================================ functionality for add course button ==============================//
    EventHandler<ActionEvent> add = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e)
        {
            ArrayList<Boolean> flag = new ArrayList<>();
            Section selectedsec = table.getSelectionModel().getSelectedItem();
            if (Sectionadded.size()>=1){
                for(int i = 0;i<Sectionadded.size();i++){
                    if(selectedsec.getActivity().equals(Sectionadded.get(i).getActivity())){
                        if(Sectionadded.get(i).getCourse()!=selectedsec.getCourse()){
                            String day1= Sectionadded.get(i).getDay();
                            String day2 = selectedsec.getDay();
                            String[] day1s=day1.split("");
                            String[] day2s=day2.split("");
                            String[] time1s= Sectionadded.get(i).getTime().split("-");
                            String[] time2s= selectedsec.getTime().split("-");
                            if(day1.equals(day2)){
                                if (time1s[0].equals(time2s[0])||time1s[1].equals(time2s[1])){
                                    flag.add(false);
                                }
                                else{
                                    flag.add(true);
                                }
                            }
                            else if(day1.contains(day2)){
                                if(time1s[0].equals(time2s[0])||time1s[1].equals(time2s[1])){
                                    flag.add(false);
                                }
                                else{
                                    flag.add(true);

                                }
                            }  
                        }
                        else{
                            flag.add(false);
                        }
                    }
                    else{
                        String day1= Sectionadded.get(i).getDay();
                        String day2 = selectedsec.getDay();
                        String[] day1s=day1.split("");
                        String[] day2s=day2.split("");
                        String[] time1s= Sectionadded.get(i).getTime().split("-");
                        String[] time2s= selectedsec.getTime().split("-");
                        if(day1.equals(day2)){
                                if (time1s[0].equals(time2s[0])||time1s[1].equals(time2s[1])){
                                    flag.add(false);
                                }
                                else{
                                    flag.add(true);

                                }
                        }
                        else if(day1.contains(day2)){
                                if(time1s[0].equals(time2s[0])||time1s[1].equals(time2s[1])){
                                    flag.add(false);
                                }
                                else{
                                    flag.add(true);

                                }
                        }
                        else{
                            flag.add(true);

                        }  
                    }
                }  
            }
            else{
                flag.add(true);  

            }
            if(flag.contains(false)){
                CourseNotAdded.display();
            }
            else{
                Sectionadded.add(selectedsec);
                selectedsec.getRegestered().setStyle("-fx-background-color: #00FF00;");
                basketListView.getItems().add(selectedsec);
            }
            flag.clear();
            System.out.println(Sectionadded);
    
        }
    };
    //============================================================================================//

    //================================ functionality for show course button ==============================//
    EventHandler<ActionEvent> add1 = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e)
        {
            Section selectedsec = basketListView.getSelectionModel().getSelectedItem();
            if (selectedsec !=null){
                String day = selectedsec.getDay();
                String[] days =selectedsec.getDay().split("");
                String coursename  = selectedsec.getCourseName();
                String activity = selectedsec.getActivity();
                String timed = selectedsec.getTime();
                String[] time = (selectedsec.getTime().split("-"));
                double start = Double.parseDouble(time[0])/100;
                double end = Double.parseDouble(time[1])/100;
                int row = (int) (start-6);
                int diff = (int) (end-start);
                Random obj = new Random();
                int rand_num = obj.nextInt(0xffffff + 1);
                // format it as hexadecimal string and print
                String colorCode = String.format("#%06x", rand_num);
                for(int i =0;i<days.length;i++){
                    if (days[i].equals("U")){
                        for(int k = 0;k<diff+1;k++){
                            Rectangle rectangle = new Rectangle(); 
                            Text text = new Text(coursename+"\n"+activity+"\n"+timed);         
                            rectangle.setWidth(200);
                            rectangle.setHeight(50);
                            rectangle.setStroke(Color.TRANSPARENT);
                            rectangle.setFill(Color.valueOf(colorCode));
                            schedule.setColumnIndex(rectangle, 1);
                            schedule.setRowIndex(rectangle, row+k);
                            schedule.setColumnIndex(text, 1);
                            schedule.setRowIndex(text, row+k);
                            schedule.getChildren().addAll(rectangle,text);
                        }
                    }
                    else if (days[i].equals("M")){
                        for(int k = 0;k<diff+1;k++){
                        Rectangle rectangle = new Rectangle(); 
                        Text text = new Text(coursename+"\n"+activity+"\n"+timed);  
                      
                        rectangle.setWidth(200);
                        rectangle.setHeight(50);
                        rectangle.setStroke(Color.TRANSPARENT);
                        rectangle.setFill(Color.valueOf(colorCode));
                        schedule.setColumnIndex(rectangle, 2);
                        schedule.setRowIndex(rectangle, row+k);
                        schedule.setColumnIndex(text, 2);
                        schedule.setRowIndex(text, row+k);
                        schedule.getChildren().addAll(rectangle,text);
                        }
                    }
                    else if (days[i].equals("T")){
                        for(int k = 0;k<diff+1;k++){
                        Rectangle rectangle = new Rectangle(); 
                        Text text = new Text(coursename+"\n"+activity+"\n"+timed);        
                        rectangle.setWidth(200);
                        rectangle.setHeight(50);
                        rectangle.setStroke(Color.TRANSPARENT);
                        rectangle.setFill(Color.valueOf(colorCode));
                        schedule.setColumnIndex(rectangle, 3);
                        schedule.setRowIndex(rectangle, row+k);
                        schedule.setColumnIndex(text, 3);
                        schedule.setRowIndex(text, row+k);
                        schedule.getChildren().addAll(rectangle,text);
                        }
                    }
                    else if (days[i].equals("W")){
                        for(int k = 0;k<diff+1;k++){
                        Rectangle rectangle = new Rectangle(); 
                        Text text = new Text(coursename+"\n"+activity+"\n"+timed);          
                        rectangle.setWidth(200);
                        rectangle.setHeight(50);
                        rectangle.setStroke(Color.TRANSPARENT);
                        rectangle.setFill(Color.valueOf(colorCode));
                        schedule.setColumnIndex(rectangle, 4);
                        schedule.setRowIndex(rectangle, row+k);
                        schedule.setColumnIndex(text, 4);
                        schedule.setRowIndex(text, row+k);
                        schedule.getChildren().addAll(rectangle,text);
                        }
                    }
                    else if (days[i].equals("R")){
                        for(int k = 0;k<diff+1;k++){
                        Rectangle rectangle = new Rectangle(); 
                        Text text = new Text(coursename+"\n"+activity+"\n"+timed);         
                        rectangle.setWidth(200);
                        rectangle.setHeight(50);
                        rectangle.setStroke(Color.TRANSPARENT);
                        rectangle.setFill(Color.valueOf(colorCode));
                        schedule.setColumnIndex(rectangle, 5);
                        schedule.setRowIndex(rectangle, row+k);
                        schedule.setColumnIndex(text, 5);
                        schedule.setRowIndex(text, row+k);
                        schedule.getChildren().addAll(rectangle,text);
                        }
                    }
                }
                
            }
    }
    };
    //================================================================================================//

    //================================ functionality for remove course button in first page ==============================//
    EventHandler<ActionEvent> remove = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e)
        {
            Section selectedsec = table.getSelectionModel().getSelectedItem();

            if(Sectionadded.contains(selectedsec)){
                Sectionadded.remove(Sectionadded.indexOf(selectedsec));
                basketListView.getItems().remove(selectedsec);
                selectedsec.getRegestered().setStyle("-fx-background-color: #E6E6FA;");
            }
            else{
                CourseNotRemoved.display();
            }
            System.out.println(Sectionadded);
        }
    };
    //====================================================================================================================//

    //================================ functionality for remove course button in second page ==============================//
    EventHandler<ActionEvent> remove1 = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e)
        {   
            Section selectedsec = basketListView.getSelectionModel().getSelectedItem();
    
                if(Sectionadded.contains(selectedsec)){
                    Sectionadded.remove(Sectionadded.indexOf(selectedsec));
                    basketListView.getItems().remove(selectedsec);
                    selectedsec.getRegestered().setStyle("-fx-background-color: #E6E6FA;");
                    reset(selectedsec);
                }
                else{
                    CourseNotRemoved.display();
                }
        }
    }; 
    //=====================================================================================================================//

    //zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz//
    // this method help with delete the course displayed in the basket (schudale)
    public void reset(Section selectedsec){
        String day = selectedsec.getDay();
        String[] days =selectedsec.getDay().split("");
        String coursename  = selectedsec.getCourseName();
        String[] time = (selectedsec.getTime().split("-"));
        double start = Double.parseDouble(time[0])/100;
        double end = Double.parseDouble(time[1])/100;
        int row = (int) (start-6);
        int diff = (int) (end-start);

        for(int i =0;i<days.length;i++){
            if (days[i].equals("U")){
                for(int k = 0;k<diff+1;k++){
                    Rectangle rectangle = new Rectangle(); 
                    Text text = new Text(coursename);  
                    text.setFill(Color.WHITE);
                    rectangle.setWidth(200);
                    rectangle.setHeight(50);
                    rectangle.setStroke(Color.WHITE);
                    rectangle.setFill(Color.valueOf("#FFFAF0"));
                    schedule.setColumnIndex(rectangle, 1);
                    schedule.setRowIndex(rectangle, row+k);
                    schedule.setColumnIndex(text, 1);
                    schedule.setRowIndex(text, row+k);
                    schedule.getChildren().addAll(rectangle,text);
                }
            }
            else if (days[i].equals("M")){
                for(int k = 0;k<diff+1;k++){
                Rectangle rectangle = new Rectangle(); 
                Text text = new Text(coursename);
                text.setFill(Color.WHITE);       
                rectangle.setWidth(200);
                rectangle.setHeight(50);
                rectangle.setStroke(Color.WHITE);
                rectangle.setFill(Color.valueOf("#FFFAF0"));
                schedule.setColumnIndex(rectangle, 2);
                schedule.setRowIndex(rectangle, row+k);
                schedule.setColumnIndex(text, 2);
                schedule.setRowIndex(text, row+k);
                schedule.getChildren().addAll(rectangle,text);
                }
            }
            else if (days[i].equals("T")){
                for(int k = 0;k<diff+1;k++){
                Rectangle rectangle = new Rectangle(); 
                Text text = new Text(coursename);
                text.setFill(Color.WHITE);   
                rectangle.setWidth(200);
                rectangle.setHeight(50);
                rectangle.setStroke(Color.WHITE);
                rectangle.setFill(Color.valueOf("#FFFAF0"));
                schedule.setColumnIndex(rectangle, 3);
                schedule.setRowIndex(rectangle, row+k);
                schedule.setColumnIndex(text, 3);
                schedule.setRowIndex(text, row+k);
                schedule.getChildren().addAll(rectangle,text);
                }
            }
            else if (days[i].equals("W")){
                for(int k = 0;k<diff+1;k++){
                Rectangle rectangle = new Rectangle(); 
                Text text = new Text(coursename);
                text.setFill(Color.WHITE);      
                rectangle.setWidth(200);
                rectangle.setHeight(50);
                rectangle.setStroke(Color.WHITE);
                rectangle.setFill(Color.valueOf("#FFFAF0"));
                schedule.setColumnIndex(rectangle, 4);
                schedule.setRowIndex(rectangle, row+k);
                schedule.setColumnIndex(text, 4);
                schedule.setRowIndex(text, row+k);
                schedule.getChildren().addAll(rectangle,text);
                }
            }
            else if (days[i].equals("R")){
                for(int k = 0;k<diff+1;k++){
                Rectangle rectangle = new Rectangle(); 
                Text text = new Text(coursename);
                text.setFill(Color.WHITE);      
                rectangle.setWidth(200);
                rectangle.setHeight(50);
                rectangle.setStroke(Color.WHITE);
                rectangle.setFill(Color.valueOf("#FFFAF0"));
                schedule.setColumnIndex(rectangle, 5);
                schedule.setRowIndex(rectangle, row+k);
                schedule.setColumnIndex(text, 5);
                schedule.setRowIndex(text, row+k);
                schedule.getChildren().addAll(rectangle,text);
                }
            }
        }    
    }
}
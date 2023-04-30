import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {
	ChoiceBox<String> source=new ChoiceBox<String>();//create combo box of Strings
	ChoiceBox<String> dest=new ChoiceBox<String>();//create combo box of Strings 
	static ArrayList<Country> Countrys=new  ArrayList<Country>();//array list of all countries
	Country sourceC ;//to store Source country
	Country destination ;//to store destination country
	Group lines=new Group();//a group of lines to draw it in the map (i make it as a group to delete them all without looping)
	static float mapWidth=1200;//the map Width
	static float mapHeight=715;//the map height
	
	public static void main(String[] args) throws FileNotFoundException {
		
		try {
			Countrys=Dijkstra.readFile();//read the file and store all counties in the array list
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		launch(args);
		
		

	}
	Pane root = new Pane();// crate pane
	@Override
	public void start(Stage arg0) throws Exception {
		Stage primaryStage = new Stage();
		
		Scene scene = new Scene(root, 1550, 800);// crate scene
		primaryStage.setTitle("the main menu");// set stage title
		root.setStyle("-fx-background-color: 			#778899	;\r\n");// set the pane color using css
		initialize();
		
		TextField t1 = new TextField();//for total distance
		t1.setTranslateX(1220);
		t1.setTranslateY(650);
		t1.setPrefSize(280, 50);
		t1.setFont(new Font("Arial", 25));
		t1.setStyle("-fx-background-color: #eeffff;\r\n" + "        -fx-background-radius:100;\r\n");
		
		sortArrayList(Countrys);//sort the countries in the list
		source.getItems().add("source");
		source.setValue("source");
		dest.getItems().add("destination");
		dest.setValue("destination");
		for(int i=0;i<Countrys.size();i++) {//add all countries in the Choose boxes
			source.getItems().add(Countrys.get(i).name);
			dest.getItems().add(Countrys.get(i).name);
		}
		source.setOnAction(e->{
			if(source.getValue().compareTo("source")!=0) {//the default value is not a country
			sourceC=Dijkstra.allNodes.get(source.getValue());//set the source country as the country chosed from the choice box using the hash map by its name
			sourceC.button.setStyle("-fx-background-color: #000000;\r\n" + "        -fx-background-radius:100;\r\n");
			}
		});
		dest.setOnAction(e->{
			if(dest.getValue().compareTo("destination")!=0) {//the default value is not a country
				destination=Dijkstra.allNodes.get(dest.getValue());//set the destination country as the country chosed from the choice box using the hash map by its name
			destination.button.setStyle("-fx-background-color: #000000;\r\n" + "        -fx-background-radius:100;\r\n");
			}
		});
		source.setTranslateX(1220);
		source.setTranslateY(50);
		source.setPrefSize(150, 50);
		dest.setTranslateX(1220);
		dest.setTranslateY(150);
		dest.setPrefSize(150, 50);
		
		 TextArea pathArea = new TextArea();//to show the path on it 
		 pathArea.setFont(new Font("Arial", 20));
		 pathArea.setTranslateX(1220);
		 pathArea.setTranslateY(350);
		 pathArea.setMinSize(310, 270);
		 pathArea.setMaxSize(310, 270);
		 pathArea.setEditable(false);
		
		Image myimage4 = new Image(new FileInputStream("error.png"));// add the image
		ImageView myview4 = new ImageView(myimage4);// show
		// set the size
		myview4.setFitHeight(50);
		myview4.setFitWidth(50);
		Label sameCountryError=new Label("the source country and distination country are the same!!",myview4);
		sameCountryError.setFont(new Font("Arial", 25));
		sameCountryError.setTextFill(Color.RED);
		sameCountryError.setTranslateX(100);
		sameCountryError.setTranslateY(720);
		sameCountryError.setVisible(false);
		
		Label lb = new Label("Path--->");
		lb.setFont(new Font("Arial", 25));
		lb.setTextFill(Color.DARKBLUE);
		lb.setTranslateX(1220);
		lb.setTranslateY(320);
		root.getChildren().add(lb);
		
		Label lb1 = new Label("total distance--->");
		lb1.setFont(new Font("Arial", 25));
		lb1.setTextFill(Color.DARKBLUE);
		lb1.setTranslateX(1220);
		lb1.setTranslateY(620);
		root.getChildren().add(lb1);

		Label nullCountryError=new Label("please enter source and destination country press reset button and reEnter them",myview4);
		nullCountryError.setFont(new Font("Arial", 25));
		nullCountryError.setTextFill(Color.RED);
		nullCountryError.setTranslateX(100);
		nullCountryError.setTranslateY(720);
		nullCountryError.setVisible(false);
		
		Image myimage2 = new Image(new FileInputStream("path.png"));// add the image
		ImageView myview2 = new ImageView(myimage2);// show
		// set the size
		myview2.setFitHeight(50);
		myview2.setFitWidth(50);
		Button run = new Button("Run   ", myview2);// create a button and add the image
		// set size and position
		run.setTranslateX(1220);
		run.setTranslateY(220);
		run.setMinWidth(200);
		run.setMinHeight(80);
		run.setMaxWidth(200);
		run.setMaxHeight(80);
		// set the button color and radius using css
		run.setStyle("-fx-background-color: #FFFFFF;\r\n" + "        -fx-background-radius:100;\r\n");
		run.setTextFill(Color.DARKBLUE);// set text color
		run.setFont(new Font("Arial", 20));// set font type
		run.setOnAction(e->{
			
			root.getChildren().remove(lines);//if there is lines in the map will remove the group of lines from the map
			if(sourceC!=destination&&sourceC!=null&&destination!=null) {//to check that the source country and destination country not the same country and both are not null
				sameCountryError.setVisible(false);
				nullCountryError.setVisible(false);				
			Country [] path;
			int s=0,d=0;
			Dijkstra main;
			for (int i = 0; i < Countrys.size(); i++) {
				if(Countrys.get(i).name.compareTo(sourceC.name)==0) {//search for the source country in the list and get it's index
					s=i;
				}
				if(Countrys.get(i).name.compareTo(destination.name)==0) {//search for the destination country in the list and get it's index
					d=i;
				}
			}
			main=new Dijkstra( Countrys, Countrys.get(s), Countrys.get(d));
			main.generateDijkstra();//do the Dijkstra 
			path=main.pathTo( Countrys.get(d));//store the counties that the path is across them in the array of countries
			pathArea.setText(main.getPathString());
			t1.setText(main.distanceString+" KM");
			for(int i=0;i<path.length-1;i++) {//to draw the lines (number of lines =number of counties in the path -1)
				Line line=new Line();
				if(i!=0)//this to make all buttons in the path colored by blue expect first and last one
					path[i].button.setStyle("-fx-background-color: #0000FF;\r\n" + "        -fx-background-radius:100;\r\n");
				line.setStartX(findFinalX(path[i].x)+5);
				line.setStartY(findFinalY(path[i].y)+5);
				line.setEndX(findFinalX(path[i+1].x)+5);
				line.setEndY(findFinalY(path[i+1].y)+5);
				line.setFill(Color.BLACK);
				line.setStroke(Color.RED);
				line.setStrokeWidth(2);
				lines.getChildren().add(line);
				
			}
			lines.setVisible(true);
			root.getChildren().add(lines);
			}
			
			else {
				if(sourceC==destination&&sourceC!=null) {
				sameCountryError.setVisible(true);
				nullCountryError.setVisible(false);
				}
				else if(sourceC==null||destination==null) {
					nullCountryError.setVisible(true);
					sameCountryError.setVisible(false);
				}
			}
		});
		
		Image myimage3 = new Image(new FileInputStream("reset.png"));// add the image
		ImageView myview3 = new ImageView(myimage3);// show
		// set the size
		myview3.setFitHeight(50);
		myview3.setFitWidth(50);
		Button reset = new Button("reset   ", myview3);// create a button and add the image
		// set size and position
		reset.setTranslateX(1220);
		reset.setTranslateY(720);
		reset.setMinWidth(150);
		reset.setMinHeight(60);
		reset.setMaxWidth(150);
		reset.setMaxHeight(60);
		// set the button color and radius using css
		reset.setStyle("-fx-background-color: #FFFFFF;\r\n" + "        -fx-background-radius:100;\r\n");
		reset.setTextFill(Color.DARKBLUE);// set text color
		reset.setFont(new Font("Arial", 20));// set font type
		reset.setOnAction(e->{
			sourceC=null;
			destination=null;
			lines.setVisible(false);
			lines.getChildren().clear();
			root.getChildren().remove(lines);
			source.setValue("source");
			dest.setValue("destination");
			pathArea.setText("");
			nullCountryError.setVisible(false);
			sameCountryError.setVisible(false);
			t1.setText("");
			//dest.setValue("destination");
			for (int i = 0; i < Countrys.size(); i++) {
				Countrys.get(i).button.setStyle("-fx-background-color: #FF0000;\r\n" + "        -fx-background-radius:100;\r\n");
			}
		});
		
		 
			
			

		
		
		root.getChildren().addAll(source,dest,run,pathArea,t1,reset,sameCountryError,nullCountryError);// add the controls to the pane
		primaryStage.setScene(scene);// set the scene
		primaryStage.show();
		
	}
	

	
	
	public void initialize() {//this will be run one time at the bagging of the program
		Image image1 = new Image("tttt.png");
		ImageView imageView1 = new ImageView(image1);
		imageView1.setFitHeight(mapHeight);
		imageView1.setFitWidth(mapWidth);
		imageView1.setVisible(true);
		root.getChildren().add(imageView1);
		for(int i=0;i<Countrys.size();i++) {//for all countries
			Button b=new Button();
			
			b.setTranslateX(findFinalX(Countrys.get(i).x));
			b.setTranslateY(findFinalY(Countrys.get(i).y));
			b.setMinWidth(10);
			b.setMinHeight(10);
			b.setMaxWidth(10);
			b.setMaxHeight(10);
			// set the button color and radius using css
			b.setStyle("-fx-background-color: #FF0000;\r\n" + "        -fx-background-radius:100;\r\n");
			Countrys.get(i).setButton(b);
			b.setUserData(Countrys.get(i));
			b.setOnAction(event -> {
				b.setStyle("-fx-background-color: #000000;\r\n" + "        -fx-background-radius:100;\r\n");//if pressed color  it by black
				if(sourceC==null) {
				
					sourceC=(Country)b.getUserData();
					source.setValue(sourceC.name);
					
					
				}
				else if(sourceC!=null&&destination==null) {
					destination=(Country)b.getUserData();
					dest.setValue(destination.name);
				}
			});
			
			Label lb = new Label(Countrys.get(i).name);
			lb.setFont(new Font("Arial", 10));
			lb.setTextFill(Color.DARKBLUE);
			lb.setTranslateX(findFinalX(Countrys.get(i).x));
			lb.setTranslateY(findFinalY(Countrys.get(i).y)-10);
			
			root.getChildren().add(b);
			root.getChildren().add(lb);
		}


}
	public static float findFinalX(float countryX) {//to make the country point fit for all sizes of the map
		float widthRatio;
		widthRatio=mapWidth/1200;
		//start from the middle of map (-45 because there where error in x )
		return mapWidth/2+((3.3334f*countryX)-45)*widthRatio;
		
	}
	
	public static float findFinalY(float countryY) {//to make the country point fit for all sizes of the map
		float heightRatio;
		heightRatio=mapHeight/715;
		//start from the middle of map (+22.5 because there where error in y )
		return mapHeight/2-((+3.97222f*countryY)+22.5f)*heightRatio;
		
	}
	public static void sortArrayList(ArrayList<Country> list) {
	    int n = list.size();
	    for (int i = 0; i < n - 1; i++) {
	        for (int j = 0; j < n - i - 1; j++) {
	            if (list.get(j).name.compareTo(list.get(j + 1).name) > 0) {
	            	Country temp = list.get(j);
	                list.set(j, list.get(j + 1));
	                list.set(j + 1, temp);
	            }
	        }
	    }
	}

}
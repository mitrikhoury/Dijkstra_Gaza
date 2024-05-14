package algotest11;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ControlsClass implements Initializable {

	double MxMin = 34.174756;
	double MyMin = 31.608502;
	double MxMax = 34.574889;
	double MyMax = 31.194222;
	double YMax = 542.7843627929688;
	double XMax = 460;
	double ymin = 28;
	double xmin = 21;
	//Label name;Polygon
	ArrayList<Line> lines = new ArrayList<>();
	ArrayList<Polygon> Arrow = new ArrayList<>();
	@FXML
	private Button runbt;

	@FXML
	void clearaction(ActionEvent event) {
		comboSourceBox.setValue(null);
		comboBoxTarget.setValue(null);
		distanceLabel.setText("");
		TextArea.clear();
		for (Line line : lines) {
			Pane.getChildren().remove(line);
		}
		
		for(Polygon dgd : Arrow) {
			Pane.getChildren().remove(dgd);
		}

		try {
			for (Map.Entry<String, Vertix> entry : vertixList.entrySet()) {
				Vertix ver = entry.getValue();
				System.out.println(ver.getCity().getCityName());
				ver.setDistance(Double.MAX_VALUE);
				ver.setKnown(false);
				ver.setPrev(null);

			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}

	}

	@FXML
	void runaction(ActionEvent event) {

		if (comboSourceBox.getValue().equals(comboBoxTarget.getValue())) {
			System.out.println("error");
			Alert alert = new Alert(AlertType.ERROR);
		    alert.setTitle("Eror");
		    alert.setHeaderText(null); 
			alert.setContentText("erorr" );
		    alert.showAndWait();
		} else {

			StringBuilder pathsc = new StringBuilder();
			FindShortPath();

			Vertix begin = vertixList.get(comboSourceBox.getValue());
			Vertix target = vertixList.get(comboBoxTarget.getValue());
			path(target, pathsc);
			TextArea.appendText(pathsc.toString());
			double distance = fromLongAndLat(begin.getCity().getLongitude(), begin.getCity().getLatitude(),
					target.getCity().getLongitude(), target.getCity().getLatitude());
			
			distanceLabel.setText(String.valueOf(distance) + " KM");
			distanceLabel.setTextFill(Color.RED);
			Drawline(target);
		

		}

	}

	@FXML
	private Label distanceLabel;

	@FXML
	private Button ClearButtun;

	@FXML
	public Pane Pane;

	@FXML
	private TextArea TextArea;
    
	HashMap<String, Vertix> vertixList = new HashMap<>();
	
	@FXML
	private ComboBox<String> comboBoxTarget;

	@FXML
	private ComboBox<String> comboSourceBox;
	
	@FXML
    private ImageView Photo ;


	private void path(Vertix x, StringBuilder sc) {

		if (x.getPrev() instanceof Vertix) {
			path(x.getPrev(), sc);
			sc.append(" to " + "\n");

		}
		sc.append(x.getCity().getCityName());
		
	}
	

	private void Drawline(Vertix x) {

		if (x.getPrev() == null) {
			
			return;
		}

		if (x.getPrev() instanceof Vertix) {
			Line line = new Line();
			line.setStrokeWidth(2);
			line.setStroke(Color.BLUE);
			line.setStartX(x.getCity().getX()); // setting starting X point of Line
			line.setStartY(x.getCity().getY());
			line.setEndX(x.getPrev().getCity().getX()); // setting ending X point of Line
			line.setEndY(x.getPrev().getCity().getY());
			addArrowHead(line, x.getCity().getX(), x.getCity().getY(), x.getPrev().getCity().getX(), x.getPrev().getCity().getY());
            
			Pane.getChildren().add(line);
			lines.add(line);
		}

		Drawline(x.getPrev());
		
	}
 
	private void FindShortPath() {
		tminHeap pq = new tminHeap();
		String source = comboSourceBox.getValue();
		Vertix sorcever = vertixList.get(source); // get the vertex of the source
		sorcever.setDistance(0.0); //to start from it
		pq.add(sorcever); 
		Vertix v, w;
		for (;;) {  // infinty loop
			v = pq.poll();  //smalest unkown distance (log n)
			if (!(v instanceof Vertix)) {  //will break if the condition true
				break;                     //all known
			}

			v.setKnown(true);  // set v true set it to known
			if (v.getCity().getCityName().equals(comboBoxTarget.getValue()) && v.isKnown()) { // when we get the city
																								// wont will quit
				break;
			}
			for (int i = 0; i < v.getAdjacents().size(); i++) { // for loop in adjisant city
				w = v.getAdjacents().get(i);
				if (w.isKnown() == false) { // not known //if it known impossible to get it shortes
					double longV = v.getCity().getLongitude(); // x
					double lattV = v.getCity().getLatitude(); // y
					double longw = w.getCity().getLongitude(); // x
					double lattw = w.getCity().getLatitude(); // y
					double distance = fromLongAndLat(longV, lattV, longw, lattw); // get the distance

					pq.add(w);  // set it in the queue until تا تيجي دورها بلفور لوب الانفنتي و تكون معلومة المسافة
					if (v.getDistance() + distance < w.getDistance()) { // if we get distance short
						w.setDistance(v.getDistance() + distance);
						w.setPrev(v);                              // update the distance              

					}
				}
			}

		}
	}
	//Haversine formula
	public static double fromLongAndLat(double long1, double lat1, double long2, double lat2) { 
		double dlat = deg2rad(lat2 - lat1);
		double dlong = deg2rad(long2 - long1);
		double d = Math.sin(dlat / 2) * Math.sin(dlat / 2)
				+ Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.sin(dlong / 2) * Math.sin(dlong / 2);
		double d2 = 2 * Math.atan2(Math.sqrt(d), Math.sqrt(1 - d));
		double distance = 6371 * d2;

		distance = (double) (((int) (distance * 100.0)) / 100.0);
		return distance;
	}

	private static double deg2rad(double v) { //to convert degrees to radians
		return (Math.PI / 180) * v;
	}

	
	
	private void addArrowHead(Line line, double startX, double startY, double endX, double endY) {
	    double arrowSize = 5.0;

	    double angle = Math.atan2((endY - startY), (endX - startX));
	    double sin = Math.sin(angle);
	    double cos = Math.cos(angle);

	    
	    double x1 = (startX + arrowSize * cos + arrowSize * sin);
	    double y1 = (startY + arrowSize * sin - arrowSize * cos);
	    double x2 = startX;
	    double y2 = startY;
	    double x3 = (startX + arrowSize * cos - arrowSize * sin);
	    double y3 = (startY + arrowSize * sin + arrowSize * cos);

	  
	    Polygon arrowhead = new Polygon(x1, y1, x2, y2, x3, y3);
	    arrowhead.setFill(Color.BLACK);
        
	    Pane.getChildren().add(arrowhead);
	    Arrow.add(arrowhead);
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		Stage c = new Stage();
		try {
			
			FileChooser filechooser = new FileChooser();
			File file = filechooser.showOpenDialog(c); // file chooser
			Scanner scan = new Scanner(file);// read the file
			Circle circle, circle2;
			String line = scan.nextLine();
			String[] arrline = line.split(",");
			String[] lineinformation;
			int index = 0;
			
			for (int i = 0; i < Integer.valueOf(arrline[0]); i++) {
				line = scan.nextLine();
				lineinformation = line.split(",");

				
				String cityName = lineinformation[0];
				String xVal = lineinformation[1];
				String yval = lineinformation[2];
				String flagg = lineinformation[3];
				double Mx = Double.valueOf(xVal) - 0.01;
				double My = Double.valueOf(yval) + 0.005;
				double XValue = ((XMax * Mx - XMax * MxMin) - (xmin * Mx - xmin * MxMax)
						+ (xmin * MxMax - xmin * MxMin)) / (MxMax - MxMin);
				double YValue = ((YMax * My - YMax * MyMin) - (ymin * My - ymin * MyMax)
						+ (ymin * MyMax - ymin * MyMin)) / (MyMax - MyMin);
				if (flagg.equals("c")) {  // city
					City cityy = new City(cityName, Double.valueOf(xVal), Double.valueOf(yval), XValue, YValue);
					Vertix ver = new Vertix(cityy);
					vertixList.put(cityName, ver);
					comboSourceBox.getItems().add(cityName);
					comboBoxTarget.getItems().add(cityName);
					circle = new Circle(4, Color.RED);
					Label name = new Label(cityName);
					circle.setLayoutX(XValue);
					circle.setLayoutY(YValue);
					circle.setId(name.getText());
					Pane.getChildren().add(circle);
					
					//PRIMARY: Usually corresponds to the left mouse button.
					//SECONDARY: Usually corresponds to the right mouse button.
					circle.setOnMouseClicked(e ->{
						if(e.getButton() == MouseButton.PRIMARY) { // left 
							Circle hg	=  (Circle)e.getSource();
							System.out.println( hg.getId() +"left combo source");
							comboSourceBox.setValue(hg.getId());		
						
						}  
						if(e.getButton() == MouseButton.SECONDARY) { // right
							Circle tt	=  (Circle)e.getSource();
							comboBoxTarget.setValue(tt.getId());
							System.out.println( tt.getId() + "rigth combo target " );
						}
						
					});
					name.setLayoutX(XValue);
					name.setLayoutY(YValue);
					name.setMaxHeight(5);
					
					Pane.getChildren().add(name);
				} else {  //street

					City cityy = new City(cityName, Double.valueOf(xVal), Double.valueOf(yval), XValue, YValue);
					Vertix ver = new Vertix(cityy);
					vertixList.put(cityName, ver);
					circle2 = new Circle(2.5, Color.TRANSPARENT);
					circle2.setLayoutX(XValue);
					circle2.setLayoutY(YValue);

					Pane.getChildren().add(circle2);
				}

			}

			for (int i = 0; i < Integer.valueOf(arrline[1]); i++) {  // read the adjesint
				line = scan.nextLine();
				String[] g = line.split(" ");
				Vertix x = vertixList.get(g[0]);
				Vertix y = vertixList.get(g[1]);
				x.addAdjacent(y);
				
			}

		} catch (Exception e) {
			System.out.println(e);
		}

	}

}
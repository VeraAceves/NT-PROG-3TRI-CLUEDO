module CluedoFantasy {

	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;
	requires java.sql;
	requires java.desktop;
	requires org.mongodb.driver.sync.client;
	requires org.mongodb.bson;

	opens aplicacion to javafx.fxml, javafx.graphics;
	opens controlador to javafx.fxml;

}
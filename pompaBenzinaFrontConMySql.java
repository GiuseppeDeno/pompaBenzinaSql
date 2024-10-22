
package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class pompaBenzinaFrontConMySql extends Application {
    TextField logIn; // Campo per l'inserimento del PIN
    Button bEntra; // Pulsante per il log in 
    Button BtnEr10; // Pulsante per erogare
    Button BtnEr20; // Pulsante per erogare
    Button BtnEr50; // Pulsante per erogare
    Button BtnInfo; // Pulsante per visualizzare informazioni sul saldo
    Button BtnRic; // Pulsante per ricaricare 
    
    Button BtnEsci; // Pulsante per uscire e rifare il login
    Label message; // Label per i messaggi
    TextField importo; // Campo per inserire l'importo

    // Importante per collegarsi al back-end di bancomat. Istanzio un oggetto pompaBenzinaLogica
    pompaBenzinaLogica scheda = new pompaBenzinaLogica();
    
    
    ///CONNESSIONE A MySQL -- la tabella si chiama  schedebenzina  pin (varchar ) e saldo (double)
    String url = "jdbc:mysql://localhost:3306/world";
    String username = "root";
    String password = "1234";
    Connection connection = null;


    
    //necessario il main come accesso  per far partire il programma
    public static void main(String[] args) {
        launch(args); // Avvia l'applicazione JavaFX
    }

    @Override
    public void start(Stage st1) throws Exception {
    	
    	//accedo a tutte  le schede di benzina tramite il select * 
    	
   	  connection = DriverManager.getConnection(url, username, password);
         String selectQuery = "SELECT * FROM schedebenzina";
         Statement stmt2 = connection.createStatement();
          ResultSet rs = stmt2.executeQuery(selectQuery);
          
          System.out.println("connesso a mySql  schedabenzina (world.schedebenzina) ");
      
        while (rs.next()) {
            String pin = rs.getString("pin");
             double saldo = rs.getDouble("saldo");
             //vedo in console come è popolata la tabella  schedabenzina
             System.out.println( "Pin: " + pin + ", Saldo: " + saldo);
             scheda.addScheda(new schedaBenzina(pin, saldo));
       }
        stmt2.close();
 	
 	
 	
    	
    	//>>>>>>>>tolgo l"istanziamento di una scheda  perche uso il db
        //in questo modo Istanzio una schedaBenzina con credito 
        //  scheda.addScheda(new schedaBenzina("1234", 100));
        
        GridPane gridPane = new GridPane();
        
        // Imposto l'allineamento del contenuto al centro
        gridPane.setAlignment(Pos.CENTER);

        // Imposto margini tra gli elementi
        gridPane.setHgap(10); 
        gridPane.setVgap(10); 
        
        
        
        // Creiamo dei widget per la pagina
        logIn = new TextField();
        logIn.setPromptText("Inserisci pin");
        logIn.setPrefWidth(200); // Imposta la larghezza preferita
        bEntra = new Button("Entra");
        
        message = new Label(); // Label per messaggi
        importo = new TextField();  ///dove inserisco l'importo di ricarica. pulirò il campo dopo aver inserito un importo 
        importo.setPromptText("Inserisci importo ");
        BtnEr10 = new Button("eroga 10 euro");
        BtnEr20 = new Button("eroga 20 euro");
        BtnEr50 = new Button("eroga 50 euro");
        BtnRic = new Button("ricarica");
        BtnInfo = new Button("info saldo");
        BtnEsci = new Button("Esci");
        
        // Azioni per i pulsanti
        bEntra.setOnAction(e -> entra());
        
        ///per i vari eroga e ricarica  ECLIPSe mi ha chiesto di implementare i try catch. perche?
        //forse perche accedono al database e lo modificano?
        BtnEr10.setOnAction(e -> {
			try {
				eroga10();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
        BtnEr20.setOnAction(e -> {
			try {
				eroga20();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
        BtnEr50.setOnAction(e -> {
			try {
				eroga50();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
       BtnRic.setOnAction(e -> {
		try {
			caricaCredito();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	});
       BtnInfo.setOnAction(e -> StampaInfo());
       
       // Azioni per il pulsante di uscita
       BtnEsci.setOnAction(e -> rifaiLogin());
       BtnEsci.setVisible(false);

        // Nascondi tutto tranne entra e textinputLogin; pulsanti di versamento e prelievo 
        BtnEr10.setVisible(false);
        BtnEr20.setVisible(false);
        BtnEr50.setVisible(false);
        BtnRic.setVisible(false);
        BtnInfo.setVisible(false);
        importo.setVisible(false);
        message.setVisible(false); //dove inserisco il valore da ricaricare 
        
        
     // setto gli id
        logIn.setId("inputPin"); 
        bEntra.setId("btnEntra"); 
        BtnEr10.setId("btnEroga10"); 
        BtnEr20.setId("btnEroga20"); 
        BtnEr50.setId("btnEroga50"); 
        BtnRic.setId("btnRicarica");
        BtnInfo.setId("btnInfo"); 
        message.setId("labelMessage"); 
        importo.setId("inputImporto"); 

     // inserisco immagine sotto i campi precedenti con 'ImageView
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("pompa.png"))); 
        imageView.setFitWidth(200); // largezza predefinita e ratio
        imageView.setPreserveRatio(true); 
        gridPane.add(imageView, 1, 5, 4, 1); // immagine nel  GridPane (colonna 1, riga 5, occupa 4 colonne)
      
      

        
        // Imposto l'allineamento del contenuto al centro
        gridPane.setAlignment(Pos.CENTER);

        // Imposto margini tra gli elementi
        gridPane.setHgap(10); // gap orizzontale
        gridPane.setVgap(10); // gap verticale
        
        // Aggiungo i widget al gridPane (colonna, riga)
        gridPane.add(logIn, 0, 0);
        gridPane.add(bEntra, 1, 0);
        gridPane.add(BtnInfo, 0, 1);
        gridPane.add(BtnEr10, 1, 1);
        gridPane.add(BtnEr20, 2, 1);
        gridPane.add(BtnEr50, 3, 1);
        gridPane.add(message, 0, 3);
        gridPane.add(importo, 0, 4);
        gridPane.add(BtnRic, 1, 4);
        gridPane.add(BtnEsci, 0, 5);
        
        // Scena
        Scene scena = new Scene(gridPane, 900, 600);
        
        // stili  finestra
        scena.getStylesheets().add(getClass().getResource("pompa.css").toExternalForm());
        
        // Aggiungo icona
        Image icon = new Image("iconBenzina.jpeg");
        st1.getIcons().add(icon);
      
        st1.setTitle("PompaBenzina");
        st1.setScene(scena);
        st1.show();
    } // FINE START 
    
    
    
    //>>>>>>>>>>>>>metodi per entrare erogare e ricaricare 
    
    
    public void entra() {
        String codice = logIn.getText();
        
        if (scheda.getAccesso(codice)) {
            logIn.setVisible(false);
            BtnEr10.setVisible(true);
            BtnEr20.setVisible(true);
            BtnEr50.setVisible(true);
           BtnRic.setVisible(true);
            BtnInfo.setVisible(true);
            importo.setVisible(true);
            
            BtnEsci.setVisible(true); // Mostra il pulsante di uscita
            
            message.setText("Login riuscito");
        } else {
            mostraAlert();
            //ho messo in mostraAlert tutta quella pappardella di codici allerta e visibilita
    }
    }
    
    
    
    
 // Metodo per ripristinare lo stato di login
    public void rifaiLogin() {
        // Rendi visibili i campi per il login
        logIn.setVisible(true);
        bEntra.setVisible(true);
        
        // Nascondi i pulsanti di erogazione e ricarica
        BtnEr10.setVisible(false);
        BtnEr20.setVisible(false);
        BtnEr50.setVisible(false);
        BtnRic.setVisible(false);
        BtnInfo.setVisible(false);
        importo.setVisible(false);
        
        //  pulisci il campo PIN  e nascondo i messaggi 
        message.setVisible(false);
        logIn.clear();
        BtnEsci.setVisible(false); // Nascondi il pulsante di uscita
    }
    
    
    //er0ga 10 euro benzina
    
    public void eroga10() throws SQLException {
        String codice = logIn.getText();
        
        if (scheda.eroga10(codice)) {
        	//calcolo i litro
        	double litri= 10/1.78;
        	double risultato = Math.floor(litri * 10) / 10; // arrotonda a 1 cifra decimale
        	//MYSQL 
        	 Connection connection = DriverManager.getConnection(url, username, password);
             String BuyQuery = "UPDATE schedebenzina SET saldo = saldo - 10 WHERE pin = ?"; //eroga 10 - saldo-10
             PreparedStatement stmt3 = connection.prepareStatement(BuyQuery);
            // stmt3.setDouble(1, 10.0); //impprto è 10
             stmt3.setString(1, codice);
             stmt3.executeUpdate();
        	
        	
        	message.setVisible(true); // Rendi visibile il messaggio
        	 message.setText("benzina in erogazione, 10 euro\nHai messo " + risultato + " litri di benzina\nCredito residuo: " + scheda.getSaldo(codice));
        } else {
            mostraAlert();
        }
        }
    public void eroga20() throws SQLException {
        String codice = logIn.getText();
        
        if (scheda.eroga20(codice)) {
        	double litri= 20/1.78;
        	double risultato = Math.floor(litri * 10) / 10; // arrotonda a 1 cifra decimale
        	
        	//connessione a MSQL
        	
        	 Connection connection = DriverManager.getConnection(url, username, password);
             String BuyQuery = "UPDATE schedebenzina SET saldo = saldo - 20 WHERE pin = ?";
             PreparedStatement stmt3 = connection.prepareStatement(BuyQuery);
           // stmt3.setDouble(1, 20);//importo1 20
             stmt3.setString(1, codice);
             stmt3.executeUpdate();
             
        	message.setVisible(true); // Rendi visibile il messaggio
       	 message.setText("benzina in erogazione, 20 euro\nHai messo " + risultato + " litri di benzina\nCredito residuo: " + scheda.getSaldo(codice));
        } else {
            mostraAlert();
        }
        }
    public void eroga50() throws SQLException {
        String codice = logIn.getText();
        
        if (scheda.eroga50(codice)) {
        	double litri= 50/1.78;
        	double risultato = Math.floor(litri * 10) / 10; // arrotonda a 1 cifra decimale
        	 Connection connection = DriverManager.getConnection(url, username, password);
             String BuyQuery = "UPDATE schedebenzina SET saldo = saldo - 50 WHERE pin = ?";
             PreparedStatement stmt3 = connection.prepareStatement(BuyQuery);
          //   stmt3.setDouble(1, importo1);// importo1 50
             stmt3.setString(1, codice);
             stmt3.executeUpdate();
        	
        	message.setVisible(true); // Rendi visibile il messaggio
       	 message.setText("benzina in erogazione, 50 euro\nHai messo " + risultato + " litri di benzina\nCredito residuo: " + scheda.getSaldo(codice));
        } else {
           mostraAlert();
        }
        }
    
    
    ///////////////IMP come gestisco ricariche maggiori di 100
    
    public void caricaCredito() throws SQLException {
        String codice = logIn.getText();
        int importoINSERITO;

        try {

            importoINSERITO = Integer.parseInt(importo.getText());

            // Controllo se l'importo supera 100 euro
            if (importoINSERITO > 100) {
                message.setVisible(true);
                message.setText("Impossibile caricare più di 100 euro."); 
                return; //ESCE  dal metodo senza ricaricare
            }

            //  metodo per ricaricare il credito la scheda. è un metodo di pompaBenzinaLogica
            scheda.ricarica(codice, importoINSERITO); 

            // Rendi visibile il messaggio e imposta il testo
            message.setVisible(true);
            
            //// connessione a mySql
            Connection connection = DriverManager.getConnection(url, username, password);
            String BuyQuery = "UPDATE schedebenzina SET saldo = saldo + ? WHERE pin = ?";
            PreparedStatement stmt3 = connection.prepareStatement(BuyQuery);
            stmt3.setDouble(1, importoINSERITO);
            stmt3.setString(2, codice);
            stmt3.executeUpdate();
            
            
            message.setVisible(true); // Rendi visibile il messaggio
            message.setText("Credito caricato con successo! Credito attuale: " + scheda.getSaldo(codice));

            // Pulisci il campo di input dopo il caricamento
            importo.clear();
         } catch (NumberFormatException e) {  ///ecezione se inserisco lettere che non possono essere gestite come numeri 
            message.setVisible(true);
            message.setText("Impossibile caricare. Inserisci un importo valido."); // Messaggio specifico per errore di formato
        }
    }

    
    public void StampaInfo() {
        String codice = logIn.getText(); //  PIN dall'input
        double saldo = scheda.getSaldo(codice); // Ottieni il saldo
        message.setVisible(true); 
        // Controlla se il saldo è maggiore di zero
        if (saldo >= 0) {
            message.setText("Il tuo saldo è: " + saldo); // Mostra il saldo
        } else {
            message.setText("PIN non riconosciuto.");
        }
    }

    //metodo per non riscrivere sempre tutta questa pappardella
    
    public void mostraAlert () {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Messaggio");
        alert.setContentText("Credito non sufficiente");
        alert.showAndWait();
        logIn.setVisible(true);
        BtnEr10.setVisible(false);
        BtnEr20.setVisible(false);
        BtnEr50.setVisible(false);
       BtnRic.setVisible(false);
        BtnInfo.setVisible(false);
        importo.setVisible(false);
        message.setText("Login fallito");
    }
    
    
    
    
    }
    
    
    
    
    
//https://pastebin.com/e90rzQgE  metodo eroga nel back/ andra in pompaBenzinaLogica di antonio della ragione 
//un solo eroga per ogni metodo 

//public boolean eroga(String pin, String item) {
//    boolean ok = false;
//    for (Scheda s1 : listaC) {
//        if (s1.pin.equalsIgnoreCase(pin)) {
//            if (item.equals("Eroga10") && s1.saldo >= 10) {
//                s1.saldo -= 10;
//                ok = true;
//            } else if (item.equals("Eroga20") && s1.saldo >= 20) {
//                s1.saldo -= 20;
//                ok = true;
//            } else if (item.equals("Eroga50") && s1.saldo >= 50) {
//                s1.saldo -= 50;
//                ok = true;
//            }
//        }
//    }
//    return ok;
//}
//

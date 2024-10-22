package application;
//Creare una finestra che rappresenta una pompa di benzina
//La pompa di benzina funziona con una scheda che ha un pin e un credito.
//Creare un oggetto scheda con un pin e un credito.
//Creare un oggetto ListaSchede che va a instanziare un arraylist di schede e consente di effetuare operazioni per:
//10 euro di benzina
//20 euro di benzina
//50 euro di benzina
//
//è inoltre possibile ricaricare la scheda con un importo a scelta (max 100 euro)
//quindi implmentare il metodo versa(int importo)
//
//Nella finestra una volta inserito il pin di una scheda se il pin viene riconosciuto tra quelli registrati è possibile:
//ricaricare la scheda
//effettuare 10, 20, 50 euro di benzina
//controllare il saldo.
//
//Opzionale (controllare a quanto ammonta oggi un litro di benzina e il programma stampa quanti litri abbiamo erogato)
//
//double prezzoB = 1.780;
//es:
//>> 50 euro 
//>> 50/1.70
//
//Creare un label che indica quanti listri sono stati erogati
//>> Hai messo 28 litri di benzina

///                                   la pompaBenzinaLogica prende in ingresso come variabili di istanza un arraylist di schedeBenzina 
import java.util.ArrayList;

public class pompaBenzinaLogica {
	  // Lista di conti correnti come variabile di istanza. La metto a public per comodità e non usare i getter e setter
    public ArrayList<schedaBenzina> listaSc;
    
    // Costruttore IMPORTANTE COME SI SCRIVE QUANDO ISTANZIA UN ARRAY LIST 
    public pompaBenzinaLogica(){
        listaSc = new ArrayList<>();
    }

    


//metodi di ricarica e metodi di erogazione 10 20 50 euro

//metodo eroga10

// Metodo per prelevare denaro
public boolean eroga10(String pin) {
    boolean ok = false;
    int importo=10; 

    for (schedaBenzina s1 : listaSc) {
        if (s1.getPin().equalsIgnoreCase(pin)) {
        	
            // Controllo se c'è abbastanza saldo
            if (s1.saldo >= importo) { //che l'ArrayList
                // Aggiorno il saldo togliendo dal saldo l importo
                s1.saldo -= importo;
                ok = true;
            }
        }
    }
    return ok; // Ritorna true se il prelievo è avvenuto con successo
    
    
}

//eroga 20
public boolean eroga20(String pin) {
    boolean ok = false;
    int importo=20; 

    for (schedaBenzina s1 : listaSc) {
        if (s1.getPin().equalsIgnoreCase(pin)) {
        	
            // Controllo se c'è abbastanza saldo
            if (s1.saldo >= importo) { //che l'ArrayList
                // Aggiorno il saldo togliendo dal saldo l importo
                s1.saldo -= importo;
                ok = true;
            }
        }
    }
    return ok; // Ritorna true se il prelievo è avvenuto con successo
    
    
}


//eroga 50
public boolean eroga50(String pin) {
  boolean ok = false;
  int importo=50; 

  for (schedaBenzina s1 : listaSc) {
      if (s1.getPin().equalsIgnoreCase(pin)) {
      	
          // Controllo se c'è abbastanza saldo
          if (s1.saldo >= importo) { //che l'ArrayList
              // Aggiorno il saldo togliendo dal saldo l importo
              s1.saldo -= importo;
              ok = true;
          }
      }
  }
  return ok; // Ritorna true se il prelievo è avvenuto con successo
  
  
}


// Metodo per versare denaro nella scheda 
public boolean ricarica(String pin, double importo) {
    boolean ok = false;
   double impMax= 100;  //ricarica massima di 100- quindi senza il controllo nel front. ricaricherbbe di 100

    for (schedaBenzina s1 : listaSc) {
        if (s1.pin.equalsIgnoreCase(pin)) {
        	if(importo<=impMax) {
        		 // Aggiungo al saldo
                s1.saldo += importo;
                ok = true;
        	}
        	// l' else non serve poiché non serve fare nulla in caso di ricarica non valida.
        	//bisogna restituire un codice di errore o di gestire la segnalazione 
        	//all'utente direttamente nel metodo chiamante 
        	//(ad esempio, nel metodo caricaCredito del frontend), per restituire l'errore 
        	}
        	
           
        }return ok; // Ritorna true se il versamento è avvenuto con successo
    }
    


// Metodo per ottenere il saldo se il PIN è corretto
public double getSaldo(String pin) {
    double saldo = 0;
    for (schedaBenzina s1 : listaSc) {
        if (s1.pin.equalsIgnoreCase(pin)) { // Posso fare c1.pin.equalsIgnoreCase(pin) perché ho messo public 
            saldo =s1.saldo; // Restituisco il saldo se il PIN è corretto
        }
    }
    return saldo; // Ritorna il saldo
}


// Metodo per accedere e verificare se il PIN corrisponde ai PIN della lista di conti 
public boolean getAccesso(String pin) {
    boolean ok = false;
    for (schedaBenzina s1 : listaSc) { 
        if (s1.pin.equalsIgnoreCase(pin)) { // Posso fare c1.pin.equalsIgnoreCase(pin) perché ho messo public 
            ok = true; // Ritorna true se il PIN è trovato
        }
    }
    return ok; // Ritorna false se il PIN non è trovato
}


// Aggiungi un nuovo conto alla lista
public void addScheda(schedaBenzina s1) {
    listaSc.add(s1);
}

}







//https://pastebin.com/e90rzQgE  metodo eroga nel back/ andra in pompaBenzinaLogica di antonio della ragione 
//un solo eroga per ogni metodo 

//public boolean eroga(String pin, String item) {
//  boolean ok = false;
//  for (Scheda s1 : listaC) {
//      if (s1.pin.equalsIgnoreCase(pin)) {
//          if (item.equals("Eroga10") && s1.saldo >= 10) {
//              s1.saldo -= 10;
//              ok = true;
//          } else if (item.equals("Eroga20") && s1.saldo >= 20) {
//              s1.saldo -= 20;
//              ok = true;
//          } else if (item.equals("Eroga50") && s1.saldo >= 50) {
//              s1.saldo -= 50;
//              ok = true;
//          }
//      }
//  }
//  return ok;
//}
//


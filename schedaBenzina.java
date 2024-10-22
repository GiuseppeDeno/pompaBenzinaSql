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


public class schedaBenzina {
	String pin;
	double saldo;
	public schedaBenzina(String pin, double saldo) {
		super();
		this.pin = pin;
		this.saldo = saldo;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	public double getSaldo() {
		return saldo;
	}
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	@Override
	public String toString() {
		return "schedaBenzina [pin=" + pin + ", saldo=" + saldo + "]";
	}
	

}

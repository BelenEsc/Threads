package usothread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Banco {

	public static void main(String[] args) {

		Banco1 test = new Banco1();

		for (int i = 0; i < 100; i++) {
			EjecucionTrans correr = new EjecucionTrans(test, i, 2000);
			Thread thread = new Thread(correr);
			thread.start();

		}
	}

}

class Banco1 {
	private final double[] cuentas = new double[100];
	private Lock cierreBanco = new ReentrantLock();

	public Banco1() {
		for (int i = 0; i < cuentas.length; i++) {
			cuentas[i] = 2000;

		}
	}

	public void transferir(int origen, double monto, int destino) {
		cierreBanco.lock();
		try {
			if (cuentas[origen] < monto) {
				return;
			}

			System.out.println(Thread.currentThread());

			cuentas[origen] -= monto;
			cuentas[destino] += monto;
			System.out.printf("la cantidad que se pasó fue %10.2f de %d a %d", monto, origen, destino);
			System.out.println("saldo total: " + saldoTotal());
		} finally {
			cierreBanco.unlock();
		}
	}

	public double saldoTotal() {
		double total = 0;
		for (double a : cuentas) {

			total += a;

		}
		return total;
	}
}

class EjecucionTrans implements Runnable {
	private Banco1 b;
	private int origen;
	private double cantidad;

	public EjecucionTrans(Banco1 b, int origen, double cantidadMax) {
		this.b = b;
		this.origen = origen;
		this.cantidad = cantidadMax;
	}

	@Override
	public void run() {
		while (true) {
			int destinataria = (int) (Math.random() * 100);

			double cantidadTransferir = cantidad * Math.random();

			b.transferir(origen, cantidadTransferir, destinataria);
			try {
				Thread.sleep((int) Math.random() * 10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}

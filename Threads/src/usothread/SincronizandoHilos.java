package usothread;

public class SincronizandoHilos {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		HilosVarios hilo1 = new HilosVarios();
		HilosVarios2 hilo2 = new HilosVarios2(hilo1);
		hilo1.start();

		hilo2.start();

		System.out.println("hola");
	}

}

class HilosVarios extends Thread {

	public void run() {

		for (int i = 0; i < 15; i++) {
			try {
				Thread.sleep(400);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println("Hilo" + getName());
		}

	}
}

class HilosVarios2 extends Thread {
	private Thread hilo;

	public HilosVarios2(Thread t) {
		this.hilo = t;

	}

	public void run() {

		try {
			hilo.join();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		for (int i = 0; i < 15; i++) {

			System.out.println("Hilo" + getName());
			try {
				Thread.sleep(400);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
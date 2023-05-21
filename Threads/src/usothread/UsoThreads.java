package usothread;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class UsoThreads {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		JFrame marco = new MarcoRebote();

		marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		marco.setVisible(true);

	}

}

class PelotaHilos implements Runnable {
	private Pelota pelota;
	private Component c;

	public PelotaHilos(Pelota pelota, Component c) {
		this.pelota = pelota;
		this.c = c;
	}

	@Override
	public void run() {

		System.out.println("comenzar " + Thread.currentThread().isInterrupted());
		while (!Thread.currentThread().isInterrupted()) {

			pelota.mueve_pelota(c.getBounds());

			c.paint(c.getGraphics());

			try {
				Thread.sleep(4);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				Thread.currentThread().interrupt();
			}
		}

		System.out.println("salir " + Thread.currentThread().isInterrupted());
	}

}

//Movimiento de la pelota-----------------------------------------------------------------------------------------

class Pelota {

	// Mueve la pelota invirtiendo posición si choca con límites

	public void mueve_pelota(Rectangle2D limites) {

		x += dx;

		y += dy;

		if (x < limites.getMinX()) {

			x = limites.getMinX();

			dx = -dx;
		}

		if (x + TAMX >= limites.getMaxX()) {

			x = limites.getMaxX() - TAMX;

			dx = -dx;
		}

		if (y < limites.getMinY()) {

			y = limites.getMinY();

			dy = -dy;
		}

		if (y + TAMY >= limites.getMaxY()) {

			y = limites.getMaxY() - TAMY;

			dy = -dy;

		}

	}

	// Forma de la pelota en su posición inicial

	public Ellipse2D getShape() {

		return new Ellipse2D.Double(x, y, TAMX, TAMY);

	}

	private static final int TAMX = 15;

	private static final int TAMY = 15;

	private double x = 0;

	private double y = 0;

	private double dx = 1;

	private double dy = 1;

}

// Lámina que dibuja las pelotas----------------------------------------------------------------------

class LaminaPelota extends JPanel {

	// Añadimos pelota a la lámina

	public void add(Pelota b) {

		pelotas.add(b);
	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;

		for (Pelota b : pelotas) {

			g2.fill(b.getShape());
		}

	}

	private ArrayList<Pelota> pelotas = new ArrayList<Pelota>();
}

//Marco con lámina y botones------------------------------------------------------------------------------

class MarcoRebote extends JFrame {

	private LaminaPelota lamina;
	JButton comienza1, comienza2, comienza3, detener1, detener2, detener3;

	public MarcoRebote() {

		setBounds(600, 300, 400, 350);

		setTitle("Rebotes");

		lamina = new LaminaPelota();

		add(lamina, BorderLayout.CENTER);

		JPanel laminaBotones = new JPanel();
		comienza1 = new JButton("comienza 1");
		comienza2 = new JButton("comienza 2");
		comienza3 = new JButton("comienza 3");
		detener1 = new JButton("detener 1");
		detener2 = new JButton("detener 2");
		detener3 = new JButton("detener 3");

		comienza1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evento) {

				comienza_el_juego(evento);
			}

		});
		comienza2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evento) {

				comienza_el_juego(evento);
			}

		});
		comienza3.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evento) {

				comienza_el_juego(evento);
			}

		});

		ponerBoton(laminaBotones, "Salir", new ActionListener() {

			public void actionPerformed(ActionEvent evento) {

				System.exit(0);

			}

		});

		detener1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evento) {
				detenJuego(evento);
			}

		});
		detener2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evento) {
				detenJuego(evento);
			}

		});
		detener3.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evento) {
				detenJuego(evento);
			}

		});

		laminaBotones.add(comienza1);
		laminaBotones.add(comienza2);
		laminaBotones.add(comienza3);
		laminaBotones.add(detener1);
		laminaBotones.add(detener2);
		laminaBotones.add(detener3);
		add(laminaBotones, BorderLayout.SOUTH);

	}

	// Ponemos botones

	public void ponerBoton(Container c, String titulo, ActionListener oyente) {

		JButton boton = new JButton(titulo);

		c.add(boton);

		boton.addActionListener(oyente);

	}

	Runnable e;
	Thread t1, t2, t3;
	// Añade pelota y la bota 1000 veces

	public void comienza_el_juego(ActionEvent evento) {

		Pelota pelota = new Pelota();

		lamina.add(pelota);

		e = new PelotaHilos(pelota, lamina);
		if (evento.getSource().equals(comienza1)) {
			t1 = new Thread(e);
			t1.start();
		} else if (evento.getSource().equals(comienza2)) {
			t2 = new Thread(e);
			t2.start();
		} else {
			t3 = new Thread(e);
			t3.start();
		}
	}

	public void detenJuego(ActionEvent evento) {

		if (evento.getSource().equals(detener1)) {
			t1.interrupt();
		} else if (evento.getSource().equals(detener2)) {
			t2.interrupt();
		} else {
			t3.interrupt();
		}

	}

}

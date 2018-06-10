package io.spring2go.jmm.volatilekeyword;

import javax.swing.*;
import java.awt.*;

public class ReadWriteSharedDataSwing {
	
	public static void main(String[] args) {
		JFrame frame = createFrame();
		frame.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 5));

		final ProgressUi writerProgressUi = new ProgressUi("Writer Thread");
		final ProgressUi readerProgressUi = new ProgressUi("Reader Thread");

		frame.add(writerProgressUi.getProgressComponent());
		frame.add(readerProgressUi.getProgressComponent());

		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		SharedObject sharedObject = new SharedObject();

		Thread thread1 = new Thread(() -> {
			int temp = 0;
			while (true) {
				if (temp != sharedObject.value) {
					temp = sharedObject.value;
					readerProgressUi.update(temp);
				}
			}
		});

		Thread thread2 = new Thread(() -> {
			for (int i = 1; i <= 100; i++) {
				sharedObject.value++;
				writerProgressUi.update(sharedObject.value);
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				if (i == 100) {
					i = 1;
					sharedObject.value = 0;
				}
			}
		});

		thread1.start();
		thread2.start();
	}

	private static class ProgressUi {
		private JProgressBar progressBar = new JProgressBar();

		ProgressUi(String name) {
			progressBar.setString(name);
			progressBar.setStringPainted(true);
		}

		JComponent getProgressComponent() {
			return progressBar;
		}

		void update(int v) {
			progressBar.setValue(v);
		}
	}

	private static JFrame createFrame() {
		JFrame frame = new JFrame("Visibility Demo with Volatile");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(400, 80));
		return frame;
	}
}
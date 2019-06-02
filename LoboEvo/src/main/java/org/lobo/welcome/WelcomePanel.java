package org.lobo.welcome;

import java.awt.Color;

import javax.swing.JPanel;

import org.lobo.component.BrowserPanel;

public class WelcomePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private final LoginButton button;

	private final TextFieldUsername text;

	public WelcomePanel(BrowserPanel panel) {
		this.text = new TextFieldUsername();
		this.button = new LoginButton(panel, this.text);
		setBackground(new Color(37, 51, 61));
		setLayout(null);
		add(this.text);
		add(this.button);
	}

	@Override
	public void repaint() {
		if (this.text != null) {
			final int x = (int) (getSize().getWidth() * 0.07);
			final int x1 = (int) (getSize().getWidth() * 0.27);
			final int y = 109;
			final int width = (int) (getSize().getWidth() * 0.80);
			final int height = 44;
			this.text.setBounds(x, y, width, height);
			this.button.setBounds(x1, 170, width / 2, height);
		}
	}
}
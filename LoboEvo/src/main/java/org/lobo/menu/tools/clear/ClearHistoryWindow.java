package org.lobo.menu.tools.clear;

import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import org.lobo.component.BrowserFrame;
import org.lobo.gui.CheckBoxPanel;
import org.lobo.gui.FormPanel;

public class ClearHistoryWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	/** The bookmark panel. */
	private CheckBoxPanel bookmarkPanel;

	/** The cache anel. */
	private CheckBoxPanel cachePanel;

	/** The cookie panel. */
	private CheckBoxPanel cookiePanel;

	/** The history button. */
	private JButton historyButton;

	/** The history panel. */
	private FormPanel historyPanel;

	/** The navigation panel. */
	private CheckBoxPanel navigationPanel;

	public ClearHistoryWindow(BrowserFrame frame) {
		createAndShowGUI(frame);
	}

	private void createAndShowGUI(BrowserFrame frame) {
		this.historyPanel = new FormPanel();
		this.historyPanel.setBorder(new EmptyBorder(1, 8, 8, 0));
		this.cachePanel = new CheckBoxPanel("Cache", this.historyPanel);
		this.cookiePanel = new CheckBoxPanel("Cookies", this.historyPanel);
		this.navigationPanel = new CheckBoxPanel("Navigation", this.historyPanel);
		this.bookmarkPanel = new CheckBoxPanel("Bookmarks", this.historyPanel);

		final JButton historyButton = new JButton();
		historyButton.setAction(
				new ClearDataAction(this.cachePanel, this.cookiePanel, this.navigationPanel, this.bookmarkPanel));
		historyButton.setText("Delete Now");
		this.historyButton = historyButton;

		add(getHistoryBox());

	}

	/**
	 * @return the bookmarkPanel
	 */
	public CheckBoxPanel getBookmarkPanel() {
		return this.bookmarkPanel;
	}

	/**
	 * @return the cachePanel
	 */
	public CheckBoxPanel getCachePanel() {
		return this.cachePanel;
	}

	/**
	 * @return the cookiePanel
	 */
	public CheckBoxPanel getCookiePanel() {
		return this.cookiePanel;
	}

	/**
	 * Gets the history box.
	 *
	 * @return the history box
	 */
	private Component getHistoryBox() {
		final JPanel groupBox = new JPanel();
		groupBox.setLayout(new BoxLayout(groupBox, BoxLayout.Y_AXIS));
		groupBox.setBorder(new TitledBorder(new EtchedBorder(), "Clear History"));
		groupBox.add(getCachePanel());
		groupBox.add(getCookiePanel());
		groupBox.add(getNavigationPanel());
		groupBox.add(getBookmarkPanel());
		groupBox.add(getHistoryButton());
		return groupBox;
	}

	/**
	 * @return the historyButton
	 */
	public JButton getHistoryButton() {
		return this.historyButton;
	}

	/**
	 * @return the historyPanel
	 */
	public FormPanel getHistoryPanel() {
		return this.historyPanel;
	}

	/**
	 * @return the navigationPanel
	 */
	public CheckBoxPanel getNavigationPanel() {
		return this.navigationPanel;
	}

	/**
	 * @param bookmarkPanel the bookmarkPanel to set
	 */
	public void setBookmarkPanel(CheckBoxPanel bookmarkPanel) {
		this.bookmarkPanel = bookmarkPanel;
	}

	/**
	 * @param cachePanel the cachePanel to set
	 */
	public void setCachePanel(CheckBoxPanel cachePanel) {
		this.cachePanel = cachePanel;
	}

	/**
	 * @param cookiePanel the cookiePanel to set
	 */
	public void setCookiePanel(CheckBoxPanel cookiePanel) {
		this.cookiePanel = cookiePanel;
	}

	/**
	 * @param historyButton the historyButton to set
	 */
	public void setHistoryButton(JButton historyButton) {
		this.historyButton = historyButton;
	}

	/**
	 * @param historyPanel the historyPanel to set
	 */
	public void setHistoryPanel(FormPanel historyPanel) {
		this.historyPanel = historyPanel;
	}

	/**
	 * @param navigationPanel the navigationPanel to set
	 */
	public void setNavigationPanel(CheckBoxPanel navigationPanel) {
		this.navigationPanel = navigationPanel;
	}
}
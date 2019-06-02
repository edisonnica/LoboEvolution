package org.lobo.gui;

/**
 * A factory for creating ItemEditor objects.
 *
 * @param <T> the generic type
 */
public interface ItemEditorFactory<T> {

	/**
	 * Creates a new ItemEditor object.
	 *
	 * @return the abstract item editor
	 */
	AbstractItemEditor<T> createItemEditor();
}

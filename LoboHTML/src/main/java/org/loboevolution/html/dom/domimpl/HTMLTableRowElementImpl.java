/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */
/*
 * Created on Dec 4, 2005
 */
package org.loboevolution.html.dom.domimpl;

import com.gargoylesoftware.css.dom.DOMException;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.dom.HTMLElement;
import org.loboevolution.html.dom.HTMLTableCellElement;
import org.loboevolution.html.dom.HTMLTableRowElement;
import org.loboevolution.html.dom.nodeimpl.NodeListImpl;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.html.renderstate.TableRowRenderState;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * <p>HTMLTableRowElementImpl class.</p>
 */
public class HTMLTableRowElementImpl extends HTMLElementImpl implements HTMLTableRowElement {
	
	private int index = -1;
	
	/**
	 * <p>Constructor for HTMLTableRowElementImpl.</p>
	 */
	public HTMLTableRowElementImpl() {
		super("TR");
	}
	
    /** {@inheritDoc} */
    @Override
    protected RenderState createRenderState(RenderState prevRenderState) {
        return new TableRowRenderState(prevRenderState, this);
    }

	/**
	 * <p>Constructor for HTMLTableRowElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public HTMLTableRowElementImpl(final String name) {
		super(name);
	}

	/** {@inheritDoc} */
	@Override
	public void deleteCell(int index) {	
		int trcount = 0;
		if (index == -1) index = this.nodeList.size() -1;
		for (Node node : nodeList) {
			if ("TD".equalsIgnoreCase(node.getNodeName())) {
				if (trcount == index) {
					removeChildAt(nodeList.indexOf(node));
					return;
				}
				trcount++;
			}
		}
		
		if (this.nodeList.size() < index) {
            throw new DOMException(DOMException.INDEX_SIZE_ERR, "The index is minor than the number of cells in the table ");
		}
		
		if (this.nodeList.size() > index) {
            throw new DOMException(DOMException.INDEX_SIZE_ERR, "The index is greater than the number of cells in the table ");
		}
	}

	/** {@inheritDoc} */
	@Override
	public String getAlign() {
		return getAttribute("align");
	}

	/** {@inheritDoc} */
	@Override
	public String getBgColor() {
		return getAttribute("bgcolor");
	}

	/** {@inheritDoc} */
	@Override
	public HTMLCollection getCells() {
		NodeListImpl list = this.nodeList;	
		if (getParentNode() != null && list.size() == 0) {
			NodeListImpl childNodes = (NodeListImpl) getParentNode().getChildNodes();
			childNodes.forEach(node -> {
				if (node instanceof HTMLTableCellElementImpl) {
					list.add(node);
				}
			});
			return new HTMLCollectionImpl(this, list);
		}

		return new HTMLCollectionImpl(this, list.stream().filter(node -> "TD".equalsIgnoreCase(node.getNodeName())).collect(Collectors.toList()));
	}

	/** {@inheritDoc} */
	@Override
	public String getCh() {
		return getAttribute("ch");
	}

	/** {@inheritDoc} */
	@Override
	public String getChOff() {
		return getAttribute("choff");
	}

	/** {@inheritDoc} */
	@Override
	public int getRowIndex() {
		if (index >= 0) {
			return index;
		} else {
			AtomicInteger index = new AtomicInteger(-1);
			if (getParentNode() != null) {
				NodeListImpl childNodes = (NodeListImpl) getParentNode().getChildNodes();
				childNodes.forEach(node -> {
					if (node instanceof HTMLTableRowElement) {
						index.incrementAndGet();
					}
				});
			}
			return index.get();
		}
	}

	/** {@inheritDoc} */
	@Override
	public int getSectionRowIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

	/** {@inheritDoc} */
	@Override
	public String getvAlign() {
		return getAttribute("valign");
	}
	
	/** {@inheritDoc} */
	@Override
	public HTMLTableCellElement insertCell() {
		final Document doc = this.document;
		if (doc == null) {
			throw new DOMException(DOMException.WRONG_DOCUMENT_ERR, "Orphan element");
		}
		HTMLTableCellElementImpl cellElement = (HTMLTableCellElementImpl) doc.createElement("TD");
		appendChild(cellElement);
		return cellElement;
	}

	/** {@inheritDoc} */
	@Override
	public HTMLTableCellElementImpl insertCell(Object index) {
		return this.insertCell(index, "TD");
	}

	private HTMLTableCellElementImpl insertCell(Object objIndex, String tagName) {
		final Document doc = this.document;
		if (doc == null) {
			throw new DOMException(DOMException.WRONG_DOCUMENT_ERR, "Orphan element");
		}
		HTMLTableCellElementImpl cellElement = (HTMLTableCellElementImpl) doc.createElement(tagName);
		
		int index = -1;		
		if (objIndex instanceof Double) {
			index = ((Double) objIndex).intValue();
		} else {
			if (objIndex == null || "".equals(objIndex)) {
				index = 0;
			} else {
				index = Integer.parseInt(objIndex.toString());
			}
		}
		
		if (index  == 0 || index  == - 1) {
			appendChild(cellElement);
			AtomicInteger cellIndex = new AtomicInteger(-1);
			if (index == -1) {
				NodeListImpl childNodes = (NodeListImpl) getParentNode().getChildNodes();
				childNodes.forEach(node -> {
					if (node instanceof HTMLTableCellElementImpl) {
						cellIndex.incrementAndGet();
					}
				});
			}
			cellElement.setIndex(index == -1 ? cellIndex.get() : 0);
			return cellElement;
		}

		AtomicInteger trcount = new AtomicInteger();
		nodeList.forEach(node -> {
			if (node instanceof HTMLTableCellElement) {
				trcount.incrementAndGet();
			}
		});
		
		if (trcount.get() < index) {
            throw new DOMException(DOMException.INDEX_SIZE_ERR, "The index is greater than the number of cells in the table ");
		} else {
			cellElement.setIndex(index);
			insertAt(cellElement, index);
		}

		return cellElement;
	}

	/**
	 * Inserts a TH element at the specified index.
	 * <p>
	 * Note: This method is non-standard.
	 *
	 * @param index The cell index to insert at.
	 * @return The element that was inserted.
	 * @throws java.lang.Exception if any.
	 * @throws org.w3c.dom.DOMException When the index is out of range.
	 */
	public HTMLElement insertHeader(int index) throws Exception {
		return this.insertCell(index, "TH");
	}
	
	/**
	 * <p>Setter for the field <code>index</code>.</p>
	 *
	 * @param index a int.
	 */
	protected void setIndex(int index) {
		this.index = index;
	}

	/** {@inheritDoc} */
	@Override
	public void setAlign(String align) {
		setAttribute("align", align);
	}

	/** {@inheritDoc} */
	@Override
	public void setBgColor(String bgColor) {
		setAttribute("bgcolor", bgColor);
	}

	/** {@inheritDoc} */
	@Override
	public void setCh(String ch) {
		setAttribute("ch", ch);
	}

	/** {@inheritDoc} */
	@Override
	public void setChOff(String chOff) {
		setAttribute("choff", chOff);
	}

	/** {@inheritDoc} */
	@Override
	public void setvAlign(String vAlign) {
		setAttribute("valign", vAlign);
	}

	
	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "[object HTMLTableRowElement]";
	}
}

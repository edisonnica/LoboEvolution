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

package org.loboevolution.html.dom.domimpl;

import com.gargoylesoftware.css.dom.DOMException;
import com.gargoylesoftware.css.parser.selector.Selector;
import com.gargoylesoftware.css.parser.selector.SelectorList;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.dom.filter.ClassNameFilter;
import org.loboevolution.html.dom.filter.ElementFilter;
import org.loboevolution.html.dom.filter.TagNameFilter;
import org.loboevolution.html.dom.nodeimpl.NodeImpl;
import org.loboevolution.html.dom.nodeimpl.NodeListImpl;
import org.loboevolution.html.js.Executor;
import org.loboevolution.html.node.*;
import org.loboevolution.html.node.events.Event;
import org.loboevolution.html.node.events.EventTarget;
import org.loboevolution.html.style.CSSUtilities;
import org.loboevolution.html.style.StyleSheetAggregator;
import org.mozilla.javascript.Function;
import org.w3c.dom.EntityReference;
import org.w3c.dom.TypeInfo;
import org.w3c.dom.events.EventException;

import java.util.*;
import java.util.stream.Stream;

/**
 * <p>EventTargetImpl class.</p>
 */
public class EventTargetImpl extends NodeImpl implements EventTarget {
	
	private final Map<NodeImpl, Map<String, List<Function>>> onEventHandlers = new HashMap<>();
	
	private final List<Node> clicked = new ArrayList<>();
	
	/**
	 * <p>getElementsByTagName.</p>
	 *
	 * @param tagname a {@link java.lang.String} object.
	 * @return a {@link org.loboevolution.html.node.NodeList} object.
	 */
	public HTMLCollection getElementsByTagName(String tagname) {
		if ("*".equals(tagname)) {
			return new HTMLCollectionImpl(this, Arrays.asList(this.getNodeList(new ElementFilter(null)).toArray()));
		} else {
			return new HTMLCollectionImpl(this, Arrays.asList(this.getNodeList(new TagNameFilter(tagname)).toArray()));
		}
	}
	
	/**
	 * <p>getElementsByClassName.</p>
	 *
	 * @param classNames a {@link java.lang.String} object.
	 * @return a {@link org.loboevolution.html.node.NodeList} object.
	 */
	public HTMLCollection getElementsByClassName(String classNames) {
		return new HTMLCollectionImpl(this, Arrays.asList(this.getNodeList(new ClassNameFilter(classNames)).toArray()));

	}
	
		
    /**
     * <p>querySelector.</p>
     *
     * @param selectors a {@link java.lang.String} object.
     * @return a {@link org.loboevolution.html.node.Element} object.
     */
    public Element querySelector(String selectors) {
    	SelectorList selectorList = CSSUtilities.getSelectorList(selectors);
    	List<Element> elem = new ArrayList<>();
    	if (selectorList != null) {
    		NodeListImpl childNodes = (NodeListImpl) getDescendents(new ElementFilter(null), true);
    		childNodes.forEach(child -> {
    			for (Selector selector : selectorList) {                	
                	if (child instanceof Element && StyleSheetAggregator.selects(selector, child, null)) {
                		elem.add((Element)child);
                    }
                }
    		});    		
        }
        return elem.size() > 0  ? elem.get(0) : null;
    }
    
    /**
     * <p>querySelectorAll.</p>
     *
     * @param selector a {@link java.lang.String} object.
     * @return a {@link org.loboevolution.html.node.NodeList} object.
     */
    public NodeList querySelectorAll(String selector) {

		final ArrayList<Node> al = new ArrayList<>();

		if(selector == null) {
			return new NodeListImpl(al);
		}

    	if(selector.isEmpty()){
			throw new DOMException(DOMException.NOT_FOUND_ERR, "The provided selector is empty.");
		}

		if(selector.trim().isEmpty()){
			throw new DOMException(DOMException.NOT_FOUND_ERR, "is not a valid selector.");
		}

    	SelectorList selectorList = CSSUtilities.getSelectorList(selector);
    	if (selectorList != null) {
    		NodeListImpl childNodes = (NodeListImpl) getDescendents(new ElementFilter(null), true);
    		childNodes.forEach(child -> {
                for (Selector select : selectorList) {
                	if (child instanceof Element && StyleSheetAggregator.selects(select, child, null)) {
                        al.add(child);
                    }
                }
    		});
        }
        return new NodeListImpl(al);
    }
		
	/**
	 * {@inheritDoc}
	 *
	 * <p>addEventListener.</p>
	 */
	@Override
	public void addEventListener(final String type, final Function listener) {
	    addEventListener(type, listener, false);
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>addEventListener.</p>
	 */
	@Override
	public void addEventListener(String type, Function listener, boolean useCapture) {
		if ("load".equals(type) || "DOMContentLoaded".equals(type)) {
			onloadEvent(listener);
		} else {
			List<Function> handlerList = new ArrayList<>();
			handlerList.add(listener);
			final Map<String, List<Function>> onEventListeners = new HashMap<>();
			onEventListeners.put(type, handlerList);
			this.onEventHandlers.put(this, onEventListeners);
		}
	}
	
	private void onloadEvent(Function onloadHandler) {
		Executor.executeFunction(this, onloadHandler, null, new Object[0]);
	}
		
	/**
	 * {@inheritDoc}
	 *
	 * <p>removeEventListener.</p>
	 */
	@Override
	public void removeEventListener(String script, Function function) {
		removeEventListener(script, function, true);
	}
	
	/**
	 * {@inheritDoc}
	 *
	 * <p>removeEventListener.</p>
	 */
	@Override
	public void removeEventListener(String type, Function listener, boolean useCapture) {
		Set<NodeImpl> keySet = onEventHandlers.keySet();
		for (NodeImpl htmlElementImpl : keySet) {
			Map<String, List<Function>> map = this.onEventHandlers.get(htmlElementImpl);
			if (map != null) {
				map.get(type).remove(listener);
			}
		}
	}
	
	/**
	 * {@inheritDoc}
	 *
	 * <p>dispatchEvent.</p>
	 */
	@Override
	public boolean dispatchEvent(Node htmlElementImpl, Event evt) {
		Map<String, List<Function>> map = this.onEventHandlers.get(htmlElementImpl);
		if (map != null) {
			List<Function> handlers = map.get(evt.getType());
			if (handlers != null) {
				for (final Function h : handlers) {
					if (!clicked.contains(htmlElementImpl)) {
						Executor.executeFunction(this, h, evt, new Object[0]);
						clicked.add(htmlElementImpl);
					} else {
						clicked.clear();
					}
				}
			}
		}
		return false;
	}

	@Override
	public boolean dispatchEvent(Event evt) throws EventException {
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public String getNodeName() {
		return "#document";
	}

	/** {@inheritDoc} */
	@Override
	public NodeType getNodeType() {
		return NodeType.DOCUMENT_NODE;
	}

	/** {@inheritDoc} */
	@Override
	public String getLocalName() {
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public String getNodeValue() {
		return null;
	}
	
	/** {@inheritDoc} */
	@Override
	public void setNodeValue(String nodeValue) throws DOMException {
		throw new DOMException(DOMException.INVALID_MODIFICATION_ERR, "Cannot set node value of document");
	}
	
	/**
	 * <p>getElementsByTagNameNS.</p>
	 *
	 * @param namespaceURI a {@link java.lang.String} object.
	 * @param localName a {@link java.lang.String} object.
	 * @return a {@link org.loboevolution.html.node.NodeList} object.
	 */
	public HTMLCollection getElementsByTagNameNS(String namespaceURI, String localName) {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Namespaces not supported");
	}
	
	/**
	 * <p>getAttributeNodeNS.</p>
	 *
	 * @param namespaceURI a {@link java.lang.String} object.
	 * @param localName a {@link java.lang.String} object.
	 * @return a {@link org.loboevolution.html.node.Attr} object.
	 * @throws com.gargoylesoftware.css.dom.DOMException if any.
	 */
	public Attr getAttributeNodeNS(String namespaceURI, String localName) throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Namespaces not supported");
	}

	/**
	 * <p>getAttributeNS.</p>
	 *
	 * @param namespaceURI a {@link java.lang.String} object.
	 * @param localName a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 * @throws com.gargoylesoftware.css.dom.DOMException if any.
	 */
	public String getAttributeNS(String namespaceURI, String localName) throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Namespaces not supported");
	}
	
	/**
	 * <p>renameNode.</p>
	 *
	 * @param n a {@link org.loboevolution.html.node.Node} object.
	 * @param namespaceURI a {@link java.lang.String} object.
	 * @param qualifiedName a {@link java.lang.String} object.
	 * @return a {@link org.loboevolution.html.node.Node} object.
	 * @throws com.gargoylesoftware.css.dom.DOMException if any.
	 */
	public Node renameNode(Node n, String namespaceURI, String qualifiedName) throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "No renaming");
	}
	
	/**
	 * <p>createAttributeNS.</p>
	 *
	 * @param namespaceURI a {@link java.lang.String} object.
	 * @param qualifiedName a {@link java.lang.String} object.
	 * @return a {@link org.loboevolution.html.node.Attr} object.
	 * @throws com.gargoylesoftware.css.dom.DOMException if any.
	 */
	public Attr createAttributeNS(String namespaceURI, String qualifiedName) throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "HTML document");
	}
	
	/**
	 * <p>removeAttributeNS.</p>
	 *
	 * @param namespaceURI a {@link java.lang.String} object.
	 * @param localName a {@link java.lang.String} object.
	 * @throws com.gargoylesoftware.css.dom.DOMException if any.
	 */
	public void removeAttributeNS(String namespaceURI, String localName) throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Namespaces not supported");
	}
	
	/**
	 * <p>hasAttributeNS.</p>
	 *
	 * @param namespaceURI a {@link java.lang.String} object.
	 * @param localName a {@link java.lang.String} object.
	 * @return a boolean.
	 * @throws com.gargoylesoftware.css.dom.DOMException if any.
	 */
	public boolean hasAttributeNS(String namespaceURI, String localName) throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Namespaces not supported");
	}
	
	/**
	 * <p>getSchemaTypeInfo.</p>
	 *
	 * @return a {@link org.w3c.dom.TypeInfo} object.
	 */
	public TypeInfo getSchemaTypeInfo() {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Namespaces not supported");
	}
	
	/**
	 * <p>setIdAttributeNS.</p>
	 *
	 * @param namespaceURI a {@link java.lang.String} object.
	 * @param localName a {@link java.lang.String} object.
	 * @param isId a boolean.
	 * @throws com.gargoylesoftware.css.dom.DOMException if any.
	 */
	public void setIdAttributeNS(String namespaceURI, String localName, boolean isId) throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Namespaces not supported");
	}
	
	/**
	 * <p>setAttributeNodeNS.</p>
	 *
	 * @param newAttr a {@link org.loboevolution.html.node.Attr} object.
	 * @return a {@link org.loboevolution.html.node.Attr} object.
	 * @throws com.gargoylesoftware.css.dom.DOMException if any.
	 */
	public Attr setAttributeNodeNS(Attr newAttr) throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Namespaces not supported");
	}

	/**
	 * <p>setAttributeNS.</p>
	 *
	 * @param namespaceURI a {@link java.lang.String} object.
	 * @param qualifiedName a {@link java.lang.String} object.
	 * @param value a {@link java.lang.String} object.
	 * @throws com.gargoylesoftware.css.dom.DOMException if any.
	 */
	public void setAttributeNS(String namespaceURI, String qualifiedName, String value) throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Namespaces not supported");
	}
	
	/**
	 * <p>createElementNS.</p>
	 *
	 * @param namespaceURI a {@link java.lang.String} object.
	 * @param qualifiedName a {@link java.lang.String} object.
	 * @return a {@link org.loboevolution.html.node.Element} object.
	 * @throws com.gargoylesoftware.css.dom.DOMException if any.
	 */
	public Element createElementNS(String namespaceURI, String qualifiedName) throws DOMException {
		throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Namespaces not supported");
	}

	/**
	 * <p>getFirstElementChild.</p>
	 *
	 * @return a {@link org.w3c.dom.Element} object.
	 */
	public Element getFirstElementChild() {
		return (Element) nodeList.stream().filter(n -> n instanceof Element).findFirst().orElse(null);
	}

	/**
	 * <p>getLastElementChild.</p>
	 *
	 * @return a {@link org.w3c.dom.Element} object.
	 */
	public Element getLastElementChild() {
		long count = nodeList.stream().filter(n -> n instanceof Element).count();
		Stream<Node> stream = nodeList.stream();
		return (Element) stream.filter(n -> n instanceof Element).skip(count - 1).findFirst().orElse(null);
	}

	/**
	 * <p>getChildElementCount.</p>
	 *
	 * @return a int.
	 */
	public int getChildElementCount() {
		return (int) nodeList.stream().filter(n -> n instanceof Element).count();
	}
}

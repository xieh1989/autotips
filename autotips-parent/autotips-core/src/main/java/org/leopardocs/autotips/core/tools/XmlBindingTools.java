package org.leopardocs.autotips.core.tools;

import java.io.Reader;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.ValidationEventLocator;

import org.w3c.dom.Node;

public class XmlBindingTools {
	public static <E> E parseXML(Reader reader, Class<E> rootElementClass) throws JAXBException {
		if (rootElementClass == null)
			throw new IllegalArgumentException("rootElementClass is null");
		if (reader == null)
			throw new IllegalArgumentException("reader is null");
		JAXBContext context = JAXBContext.newInstance(new Class[] { rootElementClass });
		Unmarshaller unmarshaller = context.createUnmarshaller();
		CollectingValidationEventHandler handler = new CollectingValidationEventHandler();
		unmarshaller.setEventHandler(handler);
		Object object = unmarshaller.unmarshal(reader);
		if (!handler.getMessages().isEmpty()) {
			String errorMessage = "XML parse errors:";
			for (String message : handler.getMessages()) {
				errorMessage = errorMessage + "\n" + message;
			}
			throw new JAXBException(errorMessage);
		}
		return (E)object;
	}

	public static void generateXML(Object rootElement, Writer writer) throws JAXBException {
		if (rootElement == null)
			throw new IllegalArgumentException("rootElement is null");
		if (writer == null)
			throw new IllegalArgumentException("writer is null");
		JAXBContext context = JAXBContext.newInstance(new Class[] { rootElement.getClass() });
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty("jaxb.formatted.output", Boolean.TRUE);
		marshaller.marshal(rootElement, writer);
	}

	private static class CollectingValidationEventHandler implements ValidationEventHandler {
		private List<String> messages = new ArrayList();

		public List<String> getMessages() {
			return this.messages;
		}

		public boolean handleEvent(ValidationEvent event) {
			if (event == null) {
				throw new IllegalArgumentException("event is null");
			}
			String severity = null;
			boolean continueParsing = false;
			switch (event.getSeverity()) {
			case 0:
				severity = "Warning";
				continueParsing = true;
				break;
			case 1:
				severity = "Error";
				continueParsing = true;
				break;
			case 2:
				severity = "Fatal error";
				continueParsing = false;
				break;
			default:
				break;
			}
			String location = getLocationDescription(event);
			String message = severity + " parsing " + location + " due to " + event.getMessage();
			this.messages.add(message);
			return continueParsing;
		}

		private String getLocationDescription(ValidationEvent event) {
			ValidationEventLocator locator = event.getLocator();
			if (locator == null) {
				return "XML with location unavailable";
			}
			StringBuffer msg = new StringBuffer();
			URL url = locator.getURL();
			Object obj = locator.getObject();
			Node node = locator.getNode();
			int line = locator.getLineNumber();
			if ((url != null) || (line != -1)) {
				msg.append("line " + line);
				if (url != null)
					msg.append(" of " + url);
			} else if (obj != null) {
				msg.append(" obj: " + obj.toString());
			} else if (node != null) {
				msg.append(" node: " + node.toString());
			}
			return msg.toString();
		}
	}
}
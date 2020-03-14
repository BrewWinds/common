package refactor.ch6creation.adapter.after;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ElementAdapter implements XMLNode{

    Element element;
    Document document;

    public ElementAdapter(Element element, Document document){
        this.element = element;
        this.document = document;
    }

    public Element getElement() {
        return element;
    }

    @Override
    public void add(XMLNode chilNode) {

    }

    public void addAttribute(String name, String value){
        getElement().setAttribute(name, value);
    }


    public void add(ElementAdapter child){
        getElement().appendChild(child.getElement());
    }

    public void addValue(String value){
        getElement().appendChild(document.createTextNode(value));
    }

}

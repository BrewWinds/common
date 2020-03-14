package refactor.ch6creation.adapter.after;

import org.w3c.dom.Document;

public class DomBuilderNew {

    private Document document;
    private ElementAdapter rootNode;
    private ElementAdapter parentNode;
    private ElementAdapter currentNode;

    public void addAttribute(String name, String value){
//        currentNode.getElement().setAttribute(name, value);
        addAttribute(currentNode, name, value);
    }

    public void addAttribute(ElementAdapter currnet, String name, String value){
        currnet.getElement().setAttribute(name, value);
    }
}

package refactor.ch6creation;

public class NodeFactory2 {

    private boolean decodestringnodes;

    public boolean isDecodestringnodes() {
        return decodestringnodes;
    }

    public void setDecodestringnodes(boolean decodestringnodes) {
        this.decodestringnodes = decodestringnodes;
    }

    public Node createStringNode(StringBuffer textBuffer,
                                 int textBegin, int textEnd){
        if(decodestringnodes){
            return new DecodingStringNode(new StringNode(textBuffer,
                    textBegin, textEnd));
        }
        return new StringNode(textBuffer, textBegin, textEnd);
    }
}

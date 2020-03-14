package refactor.ch6creation;

public class StringParser extends Parser {

    public Node find(StringBuffer textBuffer, int textBegin,
                     int textEnd, Parser parser){

        NodeFactory nodeFactory = new NodeFactory();

        return nodeFactory.createStringNode(
                textBuffer, textBegin, textEnd,
                parser.getNodeFactory2().isDecodestringnodes()
        );
    }
}

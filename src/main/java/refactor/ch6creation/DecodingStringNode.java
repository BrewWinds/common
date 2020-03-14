package refactor.ch6creation;

public class DecodingStringNode implements Node{

    StringNode node;

    DecodingStringNode(StringNode node){
        this.node = node;
    }
}

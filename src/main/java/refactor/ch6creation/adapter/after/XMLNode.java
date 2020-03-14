package refactor.ch6creation.adapter.after;

public interface XMLNode {
    public abstract void add(XMLNode chilNode);
    public abstract void addAttribute(String attribute, String value);
    public abstract void addValue(String value);
}

package sample.model;

public class TypeCards {
    private Integer prefix;
    private Integer len;
    private String name;

    public TypeCards(Integer prefix, Integer len, String name) {
        this.prefix = prefix;
        this.len = len;
        this.name = name;
    }

    public Integer getPrefix() {
        return prefix;
    }

    public void setPrefix(Integer prefix) {
        this.prefix = prefix;
    }

    public Integer getLen() {
        return len;
    }

    public void setLen(Integer len) {
        this.len = len;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return  name;
    }
}

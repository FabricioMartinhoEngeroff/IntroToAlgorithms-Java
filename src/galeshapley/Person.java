package galeshapley;

import java.util.List;

public abstract class Person {
    private String name;
    private List<String> preferences;
    private int nextProposalIndex = 0;
    private Person partner = null;


    public Person(String name, List<String> preferences){
        this.name = name;
        this.preferences = preferences;
    }

    public String getName() { return name; }

    public Person getPartner() {return partner;}

    public Person setPartner(Person p) { this.partner = p;
        return p;
    }


    public boolean isFree() { return partner == null; }

    public String getNextPreferred(){
        if (nextProposalIndex >= preferences.size()) return null;
        return preferences.get(nextProposalIndex++);
    }

    public boolean prefers(String newPersonName){
        if (partner == null) return true;
        return preferences.indexOf(newPersonName) < preferences.indexOf(partner.getName());
    }

}

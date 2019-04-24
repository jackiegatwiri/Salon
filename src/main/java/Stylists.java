import java.util.List;

import org.sql2o.*;

public class Stylists {

    private String name;
    private String number;
    private int id;

    public Stylists(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }
    public String getNumber() {
        return number;
    }
    public int getId() {
        return id;
    }

    public static List<Stylists> all() { //returns all the stylists from stylists table
        String sql = "SELECT id, name, number FROM stylists";
        try(Connection con = DB.sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Stylists.class);
        }
    }

    @Override
    public boolean equals(Object otherStylist) { //overrides the equal() method
        if (!(otherStylist instanceof Stylists)) {
            return false;
        } else {
            Stylists newStylist = (Stylists) otherStylist;
            return this.getName().equals(newStylist.getName()) && this.getNumber().equals(newStylist.getNumber()) && this.getId() == newStylist.getId() ; //checks each stylist then equates it to another stylist
        }
    }
    public void save() { // use save to save the the info to the database then assign the object the same id as its sata in the database
        try(Connection con = DB.sql2o.open()) {
            String sql = "INSERT INTO stylists (name, number) VALUES (:name, :number)"; //inserting new stylist to my database
            this.id = (int)con.createQuery(sql, true) //pass the argument true. This tells Sql2o to add the id, saved as the key, to Query.
                    .addParameter("name", this.name) //passes this values to the database
                    .addParameter("number", this.number)
                    .executeUpdate().getKey(); //We add getKey(). This is saved as an Object with a numerical value. We can use type casting to save this object as an int, which we then save to our instance variable with this.id = (int)
        }
    }

    public static Stylists find(int id) { //finding each stylist in our database
        try(Connection con = DB.sql2o.open()) {
            String sql = "SELECT * FROM stylists where id=:id";
            Stylists stylist = con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Stylists.class); //This will return the first item in the collection returned by our database, cast as a Category object
            return stylist;
        }
    }
    public List<Clients> getClients() { //retrieves all the clients form the stylists db. that is all the clients from a certain stylist
        try(Connection con = DB.sql2o.open()) {
            String sql = "SELECT * FROM clients where stylistid=:id";
            return con.createQuery(sql)
                    .addParameter("id", this.id)
                    .executeAndFetch(Clients.class);
        }
    }

    public void update(String name, String number) {
        try(Connection con = DB.sql2o.open()) {
            String sql = "UPDATE stylists SET name = :name, number = :number WHERE id = :id";
            con.createQuery(sql)
                    .addParameter("name", name)
                    .addParameter("number", number)
                    .addParameter("id", id)
                    .executeUpdate();
        }
    }

    public void delete() {
        try(Connection con = DB.sql2o.open()) {
            String sql = "DELETE FROM stylists WHERE id = :id;";
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        }
    }
}

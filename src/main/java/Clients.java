import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class Clients {
    private String name;
    private String number;
    private String style;
    private int id;
    private int stylistid;

//    public Clients(String name, String number, String style, int stylistid){
//        this.name = name;
//        this.number = number;
//        this.style = style;
//        this.stylistid = stylistid;
//    }

    @Override
    public boolean equals(Object otherClient) {
        if (!(otherClient instanceof Clients)) {
            return false;
        } else {
            Clients newClient = (Clients) otherClient;
            return this.getName().equals(newClient.getName()) &&
                    this.getnumber().equals(newClient.getnumber()) &&
                    this.getStyle().equals(newClient.getStyle()) &&
                    this.getId() == newClient.getId() &&
                    this.getStylistid() == newClient.getStylistid() ;
        }
    }

    public String getName() {
        return name;
    }

    public String getnumber() {
        return number;
    }
    public String getStyle() {
        return style;
    }
    public int getId() {
        return id;
    }

    public int getStylistid() {
        return stylistid;
    }

    public static List<Clients> all() { //retrieve all clients from database
        String sql = "SELECT id, name, number, style, stylistid FROM clients";
        try(Connection con = DB.sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Clients.class);
        }
    }

    public void save() {
        try(Connection con = DB.sql2o.open()) {
            String sql = "INSERT INTO clients(name, number, style, stylistid) VALUES (:name, :number, :style, :stylistid)";//saving this into my clients table
            this.id = (int) con.createQuery(sql, true)
                    .addParameter("name", this.name)
                    .addParameter("number", this.number)
                    .addParameter("style", this.style)
                    .addParameter("stylistid", this.stylistid)
                    .executeUpdate()
                    .getKey();
        }
    }

    public static Clients find(int id) {
        try(Connection con = DB.sql2o.open()) {
            String sql = "SELECT * FROM clients where id=:id";
            Clients client = con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Clients.class);
            return client;
        }
    }
    public void update(String name, String number, String style) {
        try(Connection con = DB.sql2o.open()) {
            String sql = "UPDATE clients SET name = :name, number = :number, style = :style  WHERE id = :id";
            con.createQuery(sql)
                    .addParameter("name", name)
                    .addParameter("number", number)
                    .addParameter("style", style)
                    .addParameter("id", id)
                    .executeUpdate();
        }
    }

    public void delete() {
        try(Connection con = DB.sql2o.open()) {
            String sql = "DELETE FROM clients WHERE id = :id;";
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        }
    }


}

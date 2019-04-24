import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.Arrays;

import static org.junit.Assert.*;

public class ClientsTest {

    @Before
    public void setUp() {
        DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/hair_salon_test", "jackline", "jacky");
    }
    @After
    public void tearDown(){
        try(Connection con = DB.sql2o.open()){
            String deleteClientstsQuery = "DELETE FROM clients *;";
            String deleteStylistsQuery = "DELETE FROM stylists *;";
            con.createQuery(deleteClientstsQuery).executeUpdate();
            con.createQuery(deleteStylistsQuery).executeUpdate();
        }
    }
//    @Test
//    public void Clients_createAnInstanceOfClients_true(){
//        Clients newClient = new Clients("jackie", "0700042852", "braids" );
//        assertTrue(newClient instanceof Clients);
//    }
//    @Test
//    public void Clients_InstantiateWithNameGetter_String(){
//        Clients newClient = new Clients("jackie", "0700042852", "braids" );
//        assertEquals("jackie", newClient.getName());
//    }
//    @Test
//    public void Clients_InstantiateWithTelNoGetter_String(){
//        Clients newClient = new Clients("jackie", "0700042852", "braids" );
//        assertEquals("0700042852", newClient.gettelNo());
//    }
//    @Test
//    public void Clients_InstantiateWithStyleGetter_String(){
//        Clients newClient = new Clients("jackie", "0700042852", "braids" );
//        assertEquals("braids", newClient.getStyle());
//    }
//    @Test
//    public void all_returnsAllTheInstancesOfClients_true(){
//        Clients firstClient = new Clients("kesh", "07999876", "wig");
//        Clients secondClient = new Clients("tar", "0798654", "boxBraids");
//        assertEquals(true, Clients.all().contains(firstClient));
//        assertEquals(true, Clients.all().contains(secondClient));
//    }
//
//    @Test
//    public void getId_tasksInstantiateWithAnID_1() {
//        Clients newClient = new Clients("jackie", "0700042852", "braids" );
//        assertEquals(1, newClient.getId());
//    }
//    @Test
//    public void find_returnsClientWithSameId_secondClient(){
//        Clients firstClient = new Clients("kesh", "07999876", "wig");
//        Clients secondClient = new Clients("tar", "0798654", "boxBraids");
//        assertEquals(Clients.find(secondClient.getId()), secondClient);
//    }

//    @Test
//    public void equals_returnsTrueIfPropertiesAretheSame() {
//        Clients firstClient = new Clients("rft", "765543", "ytf", 1);
//        Clients secondClient = new Clients("Mdrt", "3647", "ufyrf", 1);
//        assertTrue(firstClient.equals(secondClient));
//    }

    @Test
    public void save_returnsTrueIfPropertiesAretheSame() { //saving all clients in the database
        Clients myClient = new Clients("Merf", "235799", "ffve", 1);
        myClient.save();
        assertTrue(Clients.all().get(0).equals(myClient)); //Then, we retrieve all Task objects with all(), look at the first one, and use .equals() to confirm the Task returned from our database is the same as the Taskwe created locally
    }
    @Test
    public void all_returnsAllInstancesOfClients_true() {
        Clients firstClient = new Clients("jhgy", "65427864", "fggb", 1);
        firstClient.save();
        Clients secondClient = new Clients("scd", "76243", "vdngv", 1);
        secondClient.save();
        assertEquals(true, Clients.all().get(0).equals(firstClient));
        assertEquals(true, Clients.all().get(1).equals(secondClient));
    }
    @Test
    public void save_assignsIdToObject() {
        Clients myClient = new Clients("bhvg", "65643", "yftc", 1);
        myClient.save();
        Clients savedClient = Clients.all().get(0);
        assertEquals(myClient.getId(), savedClient.getId());
    }

    @Test
    public void getId_clientsInstantiateWithAnID() {
        Clients myClient = new Clients("dfvdfv", "234556", "efvghc", 1);
        myClient.save();
        assertTrue(myClient.getId() > 0);
    }

    @Test
    public void find_returnsClientWithSameId_secondClient() {
        Clients firstClient = new Clients("gfdcy", "645562", "gvy", 1);
        firstClient.save();
        Clients secondClient = new Clients("Bugvf", "73526489", "hcdghg", 1);
        secondClient.save();
        assertEquals(Clients.find(secondClient.getId()), secondClient);
    }

    @Test
    public void save_savesStylistsIdIntoDB_true() {
        Stylists myStylist = new Stylists("kiik", "766443");
        myStylist.save();
        Clients myClient = new Clients("Mow","98765", "loop", myStylist.getId());
        myClient.save();
        Clients savedClient = Clients.find(myClient.getId());
        assertEquals(savedClient.getStylistid(), myStylist.getId());
    }

    @Test
    public void getClients_retrievesAllClientsFromDatabase_clientsList() {
        Stylists myStylist = new Stylists("pin", "877554");
        myStylist.save();
        Clients firstClient = new Clients("ken", "665432", "noy", myStylist.getId());
        firstClient.save();
        Clients secondClient = new Clients("pin", "67564", "hgft", myStylist.getId());
        secondClient.save();
        Clients[] clients = new Clients[] { firstClient, secondClient };
        assertTrue(myStylist.getClients().containsAll(Arrays.asList(clients)));
    }
    @Test
    public void update_updatesClient_true() {
        Clients myClient = new Clients("Mo", "76556", "box", 4);
        myClient.save();
        myClient.update("miy", "6542", "noi");
        assertEquals("miy", Clients.find(myClient.getId()).getName());
    }

    @Test
    public void delete_deletesClient_true() {
        Clients myClient = new Clients("Mow the lawn", "76556", "box", 4);
        myClient.save();
        int myClientId = myClient.getId();
        myClient.delete();
        assertEquals(null, Clients.find(myClientId));
    }

}

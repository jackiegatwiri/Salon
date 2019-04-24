import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StylistsTest {
    @Before
    public void setUp(){
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

//    @After
//    public void tearDown() {
//        Stylists.clear();
//    }
//    @Test
//    public void Stylists_createAnInstanceOfStylists_true(){
//        Stylists newStylist = new Stylists("jackie", "0700034");
//        assertTrue(newStylist instanceof Stylists);
//    }
//
//    @Test
//    public void getName_stylistInstantiatesWithName_Home() {
//        Stylists testStylist = new Stylists("kesh", "093654");
//        assertEquals("kesh", testStylist.getName());
//    }
//    @Test
//    public void all_returnsAllInstancesOfStylists_true() {
//        Stylists firstStylist = new Stylists("kut", "7545");
//        Stylists secondStylist= new Stylists("gfgc", "634563");
//        assertEquals(true, Stylists.all().contains(firstStylist));
//        assertEquals(true, Stylists.all().contains(secondStylist));
//    }
//
//    @Test
//    public void getId_stylistInstantiateWithAnId_1() {
//        Stylists teststylist = new Stylists("jack", "87765");
//        assertEquals(1, teststylist.getId());
//    }
//    @Test
//    public void find_returnsStylistWithSameId_secondStylist() {
//        Stylists firstStylist = new Stylists("jackie", "3467");
//        Stylists secondStylist = new Stylists("gatz", "7376456");
//        assertEquals(Stylists.find(secondStylist.getId()), secondStylist);
//    }
//    @Test
//    public void getClients_initiallyReturnsEmptyList_ArrayList() {
//
//        Stylists testStylist = new Stylists("kili", "3642543");
//        assertEquals(0, testStylist.getClients().size());
//    }
//    @Test
//    public void addClient_addsClientToList_true() {
//        Stylists testStylist = new Stylists("bit", "7536534");
//        Clients testClient = new Clients("jack", "7655343", "ftyd");
//        testStylist.addClient(testClient);
//        assertTrue(testStylist.getClients().contains(testClient));
//    }
//@Test
//public void equals_returnsTrueIfNamesAretheSame() {
//    Stylists firstStylist = new Stylists("jack", "0976");
//    Stylists secondStylist = new Stylists("yity", "0987544");
//    assertTrue(firstStylist.equals(secondStylist));
//}

    @Test
    public void save_savesIntoDatabase_true() {
        Stylists myStylist = new Stylists("yui", "098775");
        myStylist.save();
        assertTrue(Stylists.all().get(0).equals(myStylist));
    }

    @Test
    public void all_returnsAllInstancesOfStylists_true() {
        Stylists firstStylist = new Stylists("pink", "09876");
        firstStylist.save();
        Stylists secondStylist = new Stylists("Wpoli", "087654");
        secondStylist.save();
        assertEquals(true, Stylists.all().get(0).equals(firstStylist));
        assertEquals(true, Stylists.all().get(1).equals(secondStylist));
    }


    @Test
    public void save_assignsIdToObject() {
        Stylists myStylist = new Stylists("Hous", "874653");
        myStylist.save();
        Stylists  savedStylist = Stylists.all().get(0);
        assertEquals(myStylist.getId(), savedStylist.getId());
    }

    @Test
    public void getId_stylistsInstantiateWithAnId_1() {
        Stylists testStylist = new Stylists("kim", "67554534");
        testStylist.save();
        assertTrue(testStylist.getId() > 0);
    }

    @Test
    public void find_returnsStylistWithSameId_secondStylist() {
        Stylists firstStylist = new Stylists("lim", "0987");
        firstStylist.save();
        Stylists secondStylist = new Stylists("lio", "765432");
        secondStylist.save();
        assertEquals(Stylists.find(secondStylist.getId()), secondStylist);
    }

    @Test
    public void update_updatesStylist_true() {
        Stylists myStylist = new Stylists("Mo", "76556");
        myStylist.save();
        myStylist.update("miy", "6542");
        assertEquals("miy", Stylists.find(myStylist.getId()).getName());
    }

}

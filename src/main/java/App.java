import java.io.Console;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
    public static void main(String[] args) {


        staticFileLocation("/public");
        String layout = "templates/layout.vtl";


        ProcessBuilder process = new ProcessBuilder();
        Integer port;
        if (process.environment().get("PORT") != null) {
            port = Integer.parseInt(process.environment().get("PORT"));
        } else {
            port = 4568;
        }

        port(port);




//        get("/", (request, response) -> { //root page
//            Map<String, Object>model = new HashMap<String, Object>();
//            model.put("template", "templates/index.vtl");
//            return modelAndView(model,layout);
//        }, new VelocityTemplateEngine());

        get("clients/new", (request, response) -> { //creating a new client
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("template", "templates/client-form.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        get("/clients", (request, response) -> { //getting all the clients created in the database and displaying it in clients.vtl
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("clients", Clients.all());
            model.put("template", "templates/clients.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        post("/clients", (request, response) -> { //posting new client in the clients.vtl, but first create a success page
            Map<String, Object> model = new HashMap<String, Object>();

            Stylists stylist = Stylists.find(Integer.parseInt(request.queryParams("stylistid"))); //creating a new stylist object and am saving this inside my stylist and passing in an id

            String name = request.queryParams("name");
            String number = request.queryParams("number");
            String style = request.queryParams("style");

            Clients newClient = new Clients(name, number, style, stylist.getId());
            System.out.println("Client is "+newClient);
            newClient.save(); // am saving the new client created to my database
            model.put("stylist", stylist);
            model.put("template", "templates/stylist-clients-success.vtl");

            return new ModelAndView(model, layout);
        },new VelocityTemplateEngine());

        get("/clients/:id", (request, response) -> {  //assigns a client an id
            HashMap<String, Object> model = new HashMap<String, Object>();
            Clients client = Clients.find(Integer.parseInt(request.params(":id")));
            model.put("client", client);
            model.put("template", "templates/client.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        get("/stylists/new", (request, response) -> { //creating a new stylist
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("template", "templates/stylist-form.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        post("/stylists", (request, response) -> { //adding the new stylist to the stylists.vtl
            Map<String, Object> model = new HashMap<String, Object>();
            String name = request.queryParams("name");
            String number = request.queryParams("number");
            Stylists newStylist = new Stylists(name, number);
            newStylist.save();
            model.put("template", "templates/stylist-success.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        get("/stylists", (request, response) -> { //getting all the stylists created
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("stylists", Stylists.all());
            model.put("template", "templates/stylists.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        get("/stylists/:id", (request, response) -> { //accessing individual stylist with their id
            Map<String, Object> model = new HashMap<String, Object>();
            Stylists stylist = Stylists.find(Integer.parseInt(request.params(":id")));
            model.put("stylist", stylist);
            model.put("template", "templates/stylist.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        get("stylists/:id/clients/new", (request, response) -> { //accessing individual stylist and adding a client to it
            Map<String, Object> model = new HashMap<String, Object>();
            Stylists stylist = Stylists.find(Integer.parseInt(request.params(":id")));
            model.put("stylist", stylist);
            model.put("template", "templates/stylist-clients-form.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        get("/stylists/:stylist_id/clients/:id", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            Stylists stylist = Stylists.find(Integer.parseInt(request.params(":stylist_id")));
            Clients client = Clients.find(Integer.parseInt(request.params(":id")));
            model.put("stylist", stylist);
            model.put("client", client);
            model.put("template", "templates/client.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        post("/stylists/:stylist_id/clients/:id", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            Clients client = Clients.find(Integer.parseInt(request.params("id")));
            String name = request.queryParams("name");
            String number = request.queryParams("number");
            String style = request.queryParams("style");

            Stylists stylist = Stylists.find(client.getStylistid());
            client.update(name, number, style);
            String url = String.format("/stylists/%d/clients/%d", stylist.getId(), client.getId());
            response.redirect(url);
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        post("/stylists/:stylist_id/clients/:id/delete", (request, response) -> {
            HashMap<String, Object> model = new HashMap<String, Object>();
            Clients client = Clients.find(Integer.parseInt(request.params("id")));
            Stylists stylist = Stylists.find(client.getStylistid());
            client.delete();
            model.put("stylist", stylist);
            model.put("template", "templates/stylist.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());



        get("/stylists/:stylist_id", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            Stylists stylist = Stylists.find(Integer.parseInt(request.params(":stylist_id")));
            model.put("stylist", stylist);
            model.put("template", "templates/stylist.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        post("/stylists/:id/delete", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            Stylists stylist = Stylists.find(Integer.parseInt(request.params(":id")));
            stylist.delete();
            response.redirect("/stylists");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());




    }
}
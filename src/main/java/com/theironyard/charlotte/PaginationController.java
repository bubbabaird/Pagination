package com.theironyard.charlotte;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

    @Controller
    public class PaginationController {

    @Autowired
    AddressesRepo addresses;

        @PostConstruct
        // for every line in the file
        // convert that line into an address
        // save all addresses in the repository
        public void Init() {
            //read in data from CSV file
            Scanner addressScanner = null;
            try {
                addressScanner = new Scanner(new File("src/main/resources/addresses.csv"));

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Scanner pageScanner = null;
            try {
                pageScanner = new Scanner(new File("src/main/resources/addresses.csv"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            pageScanner.nextLine();
            if (addresses.findOne(1) == null) {
                while (addressScanner.hasNext()) {
                    String line = addressScanner.nextLine();
                    int columnLine = 1;
                    String[] columns = line.split(",");
                    Addresses newAddress = new Addresses(columns[columnLine++], columns[columnLine++], columns[columnLine++], Integer.valueOf(columns[columnLine++]));
                    addresses.save(newAddress);
                }
            }
        }
    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(Model model, HttpSession session, String state, Integer page) {

        // if page is null, meaning when we
        // made this request, we did not specify a page
        // then we set page to 0. otherwise we basically
        // set page = page, which changes nothing.
        page = (page == null) ? 0 : page;

        // we want "page" (either 0 or what we have
        // in the query string), and 10 results.
        PageRequest pr = new PageRequest(page, 10);

        // this will store the results of our jpa query
        // into a "Page" object.
        Page<Addresses> p;

        if (state != null) {
            // get the page of results from the category
            p = addresses.findByState(pr, state);
        } else {
            p = addresses.findAll(pr);
        }
        // passing our page of data into our template
        model.addAttribute("addresses", p);

        // we're going to use this value to generate
        // a "next page" link
        model.addAttribute("nextPage", page + 1);

        // we're going to use THIS to decide whether or
        // not we need a "next page" link.
        model.addAttribute("showNext", p.hasNext());

        // the category we're in. we need to know
        // the category, if it exists, if we want
        // to render the appropriate "next" link in
        // the template.
        model.addAttribute("state", state);
        return "index";
    }
}
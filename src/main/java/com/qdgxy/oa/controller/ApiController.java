package com.qdgxy.oa.controller;

import com.qdgxy.oa.meta.Customer;
import com.qdgxy.oa.meta.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liyongjun on 17/2/1.
 */
@Controller
public class ApiController {

    @RequestMapping("/test")
    public
    @ResponseBody
    Map<String, Object> test() {
        List<Customer> customers = new ArrayList<Customer>();
        Map<String, Object> Rows = new HashMap<String, Object>();
        Customer customer = new Customer();
        customer.setCustomerID("remeo");
        customer.setCompanyName("remeo");
        customer.setContactName("remeo");
        Customer customer1 = new Customer();
        customer1.setCustomerID("jenius");
        customer1.setCompanyName("jenius");
        customer1.setContactName("jenius");
        customers.add(customer);
        customers.add(customer1);
        Rows.put("Rows", customers);
        Rows.put("Total",2);
        return Rows;
    }

}

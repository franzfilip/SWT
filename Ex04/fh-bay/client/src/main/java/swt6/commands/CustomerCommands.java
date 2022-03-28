package swt6.commands;

import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import swt6.services.CustomerService;

@ShellComponent
@ShellCommandGroup("Customer Commands")
public class CustomerCommands {
    private CustomerService customerService;

    public CustomerCommands(CustomerService customerService) {
        this.customerService = customerService;
    }

    @ShellMethod("get all Customers")
    public void getAllCustomers(){
        customerService.findAll().forEach(System.out::println);
    }

    @ShellMethod("get Customer by ID")
    public void getCustomerById(@ShellOption Long id){
        System.out.println(customerService.findById(id).get());
    }
}


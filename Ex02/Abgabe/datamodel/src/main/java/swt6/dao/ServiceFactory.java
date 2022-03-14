package swt6.dao;

import swt6.util.JPAUtil;

public class ServiceFactory {
    private static CustomerService customerService;

    public static CustomerService getCustomerService(){
        if(customerService == null){
            customerService = new  swt6.dao.impl.CustomerService(JPAUtil.getEntityManager());
        }
        return customerService;
    }
}

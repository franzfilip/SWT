package swt6.dao;

import swt6.util.JPAUtil;

public class ServiceFactory {
    private static CustomerService customerService;
    private static ArticleService articleService;
    private static BidService bidService;

    public static CustomerService getCustomerService(){
        if(customerService == null){
            customerService = new  swt6.dao.impl.CustomerService(JPAUtil.getEntityManager());
        }
        return customerService;
    }

    public static ArticleService getArticleService(){
        if(articleService == null){
            articleService = new  swt6.dao.impl.ArticleService(JPAUtil.getEntityManager());
        }
        return articleService;
    }

    public static BidService getBidService(){
        if(bidService == null){
            bidService = new  swt6.dao.impl.BidService(JPAUtil.getEntityManager());
        }
        return bidService;
    }
}

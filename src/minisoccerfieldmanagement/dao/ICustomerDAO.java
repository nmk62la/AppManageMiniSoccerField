package minisoccerfieldmanagement.dao;

import java.util.List;
import minisoccerfieldmanagement.model.Customer;

public interface ICustomerDAO {

    Boolean add(Customer model);

    Customer findById(int id);

    List<Customer> findByMemberShip(int memberShipId);

    Customer findByPhoneNumber(String phoneNumber);

    Boolean softDelete(int id);

    Boolean update(Customer model);
    
    List<Customer> findAll();
    
    Boolean checkPhoneNumberExist(String phoneNumber);
    
    Boolean checkPhoneNumberExistExceptCurrent(int id, String phoneNumber);
    
    List<Customer> findAllAndFilter(String content, int membershipId, int displayType);
    
}

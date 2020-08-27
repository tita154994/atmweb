package th.rd.go.atm.data;

import th.rd.go.atm.model.Customer;

import java.util.List;

public interface JdbcRepository {
    List<Customer> findAll();
    Customer findById(int id);
    void save(Customer customer);
    void update(int id, Customer customer);
    void deleteById(int id);

}

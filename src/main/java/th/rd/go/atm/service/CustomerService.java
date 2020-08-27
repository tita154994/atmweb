package th.rd.go.atm.service;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import th.rd.go.atm.data.CustomerRepository;
import th.rd.go.atm.model.Customer;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;



@Service
public class CustomerService {

    //private List<Customer> customerList;
    //private ArrayList<Customer> customers = new ArrayList<>();
    private CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }



//    @PostConstruct
//    public void postConstruct() {
//        this.customerList = new ArrayList<>();
//    }


    public void createCustomer(Customer customer) {
        String hashPin = hash(customer.getPin());
        customer.setPin(hashPin);
        //customers.add(customer);
        repository.save(customer);

    }

    public Customer findCustomer(int id) {
        return repository.findById(id);
    }

    public List<Customer> getCustomers() {
        return repository.findAll();
    }


//    public List<Customer> getCustomers() {
//        return new ArrayList<>(customers);
//    }


    private String hash(String pin) {
        String salt = BCrypt.gensalt(12);
        return BCrypt.hashpw(pin, salt);
    }


    public Customer checkPin(Customer inputCustomer) {
        // 1. หา customer ที่มี id ตรงกับพารามิเตอร์
        Customer matchingCustomer = null;
        for (Customer c : repository.findAll()) {
            if (inputCustomer.getId() == c.getId())
                matchingCustomer = c;
        }
        // 2. ถ้ามี id ตรง ให้เช็ค pin ว่าตรงกันไหม โดยใช้ฟังก์ชันเกี่ยวกับ hash
        if (matchingCustomer != null) {
            if (BCrypt.checkpw(inputCustomer.getPin(),
                    matchingCustomer.getPin()))
                return matchingCustomer;
        }
        // 3. ถ้าไม่ตรง ต้องคืนค่า null
        return null;
    }



}

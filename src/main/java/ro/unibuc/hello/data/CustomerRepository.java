package ro.unibuc.hello.data;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CustomerRepository extends MongoRepository<CustomerEntity, String> {
    CustomerEntity findByName(String Id);
}

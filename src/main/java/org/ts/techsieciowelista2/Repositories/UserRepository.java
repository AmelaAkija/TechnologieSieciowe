package org.ts.techsieciowelista2.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.ts.techsieciowelista2.User;

@Repository
public interface UserRepository extends CrudRepository<User,Integer> {
}

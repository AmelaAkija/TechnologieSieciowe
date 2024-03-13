package org.ts.techsieciowelista2.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.ts.techsieciowelista2.Detail;




@Repository
public interface DetailRepository extends CrudRepository<Detail,Integer> {

}
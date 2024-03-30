package com.school.commentmanager.repo;

import com.school.commentmanager.model.Category;
import com.school.commentmanager.model.Picture;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryRepository extends CrudRepository<Category, UUID> {

}

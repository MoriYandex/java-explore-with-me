package ru.main.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.main.category.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
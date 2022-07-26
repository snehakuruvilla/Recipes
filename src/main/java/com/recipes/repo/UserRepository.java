package com.recipes.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recipes.bean.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}

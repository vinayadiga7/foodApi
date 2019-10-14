package com.FoodCourt.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.FoodCourt.Entities.UserAddressEntity;

public interface UserAddressEntityRepository extends JpaRepository<UserAddressEntity, Integer>{

	public List<UserAddressEntity> findByUserUserId(Integer userId);
}

package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.ImageEntity;

public interface ImageRepository extends JpaRepository<ImageEntity, Integer> {



	ImageEntity findByProdutoId(Integer id);

}
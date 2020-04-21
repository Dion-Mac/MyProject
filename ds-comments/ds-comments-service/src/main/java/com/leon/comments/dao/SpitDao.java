package com.leon.comments.dao;


import com.leon.comments.pojo.Spit;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author LeonMac
 * @description
 */
public interface SpitDao extends MongoRepository<Spit,String> {
}

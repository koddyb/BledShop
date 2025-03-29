package com.soft.MarketPlace.serviceImplement;

import com.soft.MarketPlace.entity.LignePaniers;
import com.soft.MarketPlace.repository.LignepaniersRepository;
import com.soft.MarketPlace.service.LignePanierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LignepaniersServiceImplement implements LignePanierService {

    @Autowired
    private LignepaniersRepository lignepaniersRepository;

    public LignepaniersServiceImplement(LignepaniersRepository lignepaniersRepository) {
        this.lignepaniersRepository = lignepaniersRepository;
    }

    @Override
    public List<LignePaniers> getAllLignePaniers() {
        return lignepaniersRepository.findAll();
    }

    @Override
    public LignePaniers getLignePaniersById(int id) {
        return lignepaniersRepository.findById(id).get();
    }

    @Override
    public LignePaniers addLignePaniers(LignePaniers lignePaniers) {
        return lignepaniersRepository.save(lignePaniers);
    }

    @Override
    public LignePaniers updateLignePaniers(LignePaniers lignePaniers) {
        return lignepaniersRepository.save(lignePaniers);
    }

    @Override
    public void deleteLignePaniers(int id) {
        lignepaniersRepository.deleteById(id);
    }
}

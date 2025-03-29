package com.soft.MarketPlace.serviceImplement;

import com.soft.MarketPlace.entity.ImageCategorie;
import com.soft.MarketPlace.repository.ImagescategorieRepository;
import com.soft.MarketPlace.service.ImageCategorieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImagescategorieServiceImplement implements ImageCategorieService {

    @Autowired
    private ImagescategorieRepository imagescategorieRepository;

    public ImagescategorieServiceImplement(ImagescategorieRepository imagescategorieRepository) {
        this.imagescategorieRepository = imagescategorieRepository;
    }


    @Override
    public List<ImageCategorie> getAllImageCategorie() {
        return imagescategorieRepository.findAll();
    }

    @Override
    public ImageCategorie getImageCategorieById(int id) {
        return imagescategorieRepository.findById(id).get();
    }

    @Override
    public List<ImageCategorie> getImageCategorieByNom(String nom) {
        return imagescategorieRepository.findByNom(nom);
    }

    @Override
    public ImageCategorie addImageCategorie(ImageCategorie imageCategorie) {
        return imagescategorieRepository.save(imageCategorie);
    }

    @Override
    public ImageCategorie updateImageCategorie(ImageCategorie imageCategorie) {
        return imagescategorieRepository.save(imageCategorie);
    }

    @Override
    public void deleteImageCategorie(int id) {
        imagescategorieRepository.deleteById(id);
    }
}

package com.soft.MarketPlace.serviceImplement;

import com.soft.MarketPlace.entity.Image;
import com.soft.MarketPlace.repository.ImagesRepository;
import com.soft.MarketPlace.service.ImageService;
import org.springframework.stereotype.Service;

import javax.imageio.ImageReader;
import java.util.List;

@Service
public class ImageServiceImplement implements ImageService {

    private ImagesRepository imagesRepository;

    public ImageServiceImplement(ImagesRepository imagesRepository) {
        this.imagesRepository = imagesRepository;
    }

    @Override
    public List<Image> getAllImage() {
        return imagesRepository.findAll();
    }

    @Override
    public Image getImageById(int id) {
        return imagesRepository.findById(id).get();
    }

    @Override
    public List<Image> getImageByNom(String nom) {
        return imagesRepository.findByNom(nom);
    }

    @Override
    public Image addImage(Image image) {
        return imagesRepository.save(image);
    }

    @Override
    public Image updateImage(Image image) {
        return imagesRepository.save(image);
    }

    @Override
    public void deleteImage(int id) {
        imagesRepository.deleteById(id);
    }
}

package com.soft.MarketPlace.service;

import com.soft.MarketPlace.entity.Client;
import com.soft.MarketPlace.entity.Image;

import java.util.List;

public interface ImageService {
    //toutes les methodes qui concernent l'image

    public List<Image> getAllImage();
    public Image getImageById(int id);
    public List<Image> getImageByNom(String nom);
    public Image addImage(Image image);
    public Image updateImage(Image image);
    public void deleteImage(int id);
}

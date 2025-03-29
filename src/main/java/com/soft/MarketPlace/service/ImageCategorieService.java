package com.soft.MarketPlace.service;

import com.soft.MarketPlace.entity.Image;
import com.soft.MarketPlace.entity.ImageCategorie;

import java.util.List;

public interface ImageCategorieService {

    //toutes les methodes qui concernent l'imageCategorie

    public List<ImageCategorie> getAllImageCategorie();
    public ImageCategorie getImageCategorieById(int id);
    public List<ImageCategorie> getImageCategorieByNom(String nom);
    public ImageCategorie addImageCategorie(ImageCategorie imageCategorie);
    public ImageCategorie updateImageCategorie(ImageCategorie imageCategorie);
    public void deleteImageCategorie(int id);
}

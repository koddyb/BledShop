package com.soft.MarketPlace.serviceImplement;

import com.soft.MarketPlace.entity.TypeBoutique;
import com.soft.MarketPlace.repository.TypeboutiqueRepository;
import com.soft.MarketPlace.service.TypeBoutiqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeboutiqueServiceImplement implements TypeBoutiqueService {

    @Autowired
    private TypeboutiqueRepository typeboutiqueRepository;

    public TypeboutiqueServiceImplement(TypeboutiqueRepository typeboutiqueRepository) {
        this.typeboutiqueRepository = typeboutiqueRepository;
    }

    @Override
    public List<TypeBoutique> getAllTypeBoutique() {
        return typeboutiqueRepository.findAll();
    }

    @Override
    public TypeBoutique getTypeBoutiqueById(int id) {
        return typeboutiqueRepository.findById(id).get();
    }

    @Override
    public TypeBoutique getTypeBoutiqueByLibelle(String libelle) {
        return typeboutiqueRepository.findByLibelle(libelle);
    }

    @Override
    public TypeBoutique addTypeBoutique(TypeBoutique typeBoutique) {
        return typeboutiqueRepository.save(typeBoutique);
    }

    @Override
    public TypeBoutique updateTypeBoutique(TypeBoutique typeBoutique) {
        return typeboutiqueRepository.save(typeBoutique);
    }

    @Override
    public void deleteTypeBoutique(int id) {
        typeboutiqueRepository.deleteById(id);
    }
}

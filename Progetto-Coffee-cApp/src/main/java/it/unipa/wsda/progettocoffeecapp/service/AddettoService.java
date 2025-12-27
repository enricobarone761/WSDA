package it.unipa.wsda.progettocoffeecapp.service;

import it.unipa.wsda.progettocoffeecapp.model.Addetto;
import it.unipa.wsda.progettocoffeecapp.repository.AddettoRepository;
import org.springframework.stereotype.Service;

@Service
public class AddettoService {

    private final AddettoRepository addettoRepository;

    public AddettoService(AddettoRepository addettoRepository) {
        this.addettoRepository = addettoRepository;
    }

    public Iterable<Addetto> getAllAddetti(){
        return addettoRepository.findAll();
    }

}

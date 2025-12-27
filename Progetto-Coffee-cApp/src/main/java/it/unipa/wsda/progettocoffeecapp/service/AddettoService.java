package it.unipa.wsda.progettocoffeecapp.service;

import it.unipa.wsda.progettocoffeecapp.model.Addetto;
import it.unipa.wsda.progettocoffeecapp.repository.AddettoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddettoService {

    private final AddettoRepository addettoRepository;
    public AddettoService(AddettoRepository addettoRepository) {
        this.addettoRepository = addettoRepository;
    }


    public Iterable<Addetto> getAllAddetti(){
        return addettoRepository.findAll();
    }

    public void saveAddetto(Addetto addetto) {
        addettoRepository.save(addetto);
    }

    public void deleteAddettoByEmail(String email) {
        Optional<Addetto> addetto = addettoRepository.getAddettoByEmail(email);
        addetto.ifPresent(addettoRepository::delete);
    }

}

package it.unipa.wsda.progettocoffeecapp.service;

import it.unipa.wsda.progettocoffeecapp.model.Bevanda;
import it.unipa.wsda.progettocoffeecapp.repository.BevandaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BevandaService {

    private final BevandaRepository bevandaRepository;

    public BevandaService(BevandaRepository bevandaRepository) {
        this.bevandaRepository = bevandaRepository;
    }

    public Optional<Bevanda> getBevanda(Integer idBevanda) {
        return bevandaRepository.findById(idBevanda);
    }
}

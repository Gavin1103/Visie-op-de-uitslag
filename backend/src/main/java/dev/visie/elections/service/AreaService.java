package dev.visie.elections.service;

import dev.visie.elections.dto.AreaDTO;
import dev.visie.elections.model.election.Constituency;
import dev.visie.elections.model.election.Municipality;
import dev.visie.elections.repository.ConstituencyRepository;
import dev.visie.elections.repository.MunicipalityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaService {
    private final MunicipalityRepository municipalityRepository;
    private final ConstituencyRepository constituencyRepository;

    public AreaService(MunicipalityRepository municipalityRepository, ConstituencyRepository constituencyRepository) {
        this.municipalityRepository = municipalityRepository;
        this.constituencyRepository = constituencyRepository;
    }

    public AreaDTO getAllAreas(){
        List<Municipality> municipalities = municipalityRepository.findAll();
        List<Constituency> constituencies = constituencyRepository.findAll();
        return new AreaDTO(municipalities, constituencies);

    }
}

package com.cronometro.servicio.servicecube.services.imp;


import com.cronometro.servicio.servicecube.exceptions.AuthJwtException;
import com.cronometro.servicio.servicecube.exceptions.SolvesBadRequestException;
import com.cronometro.servicio.servicecube.models.dtos.GraphDto;
import com.cronometro.servicio.servicecube.models.dtos.ListPreSolvesDto;
import com.cronometro.servicio.servicecube.models.dtos.PreSolveDto;
import com.cronometro.servicio.servicecube.models.enitties.FinalResutls;
import com.cronometro.servicio.servicecube.models.enitties.Solves;
import com.cronometro.servicio.servicecube.models.enitties.Users;
import com.cronometro.servicio.servicecube.repositories.FinalResultRepository;
import com.cronometro.servicio.servicecube.repositories.SolveRepository;
import com.cronometro.servicio.servicecube.repositories.UserRepository;
import com.cronometro.servicio.servicecube.services.CronoService;
import com.cronometro.servicio.servicecube.services.utils.RubikAlgorithmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AppServiceImp implements CronoService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SolveRepository solveRepository;
    @Autowired
    private FinalResultRepository finalResultRepository;
    @Autowired
    private RubikAlgorithmService rubikAlgorithmService;

    private Users getAuthentication() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new AuthJwtException("el usuario authenticado no se encuentra, cierre e inicie secion de nuevo"));

    }

    @Override
    @Transactional
    public List<FinalResutls> findByDate(Pageable pageable) {
        Users user = getAuthentication();
        return finalResultRepository.findAllByOrderByCreatedAtAndUsername(user.getUsername(), pageable);
    }

    @Override
    @Transactional
    public List<FinalResutls> findByBestAVG5(Pageable pageable) {
        Users user = getAuthentication();
        return finalResultRepository.findAllBuAvgBestAndUsername(user.getUsername(), pageable);
    }

    @Override
    @Transactional
    public List<FinalResutls> findByWorstAvg5(Pageable pageable) {
        Users user = getAuthentication();
        return finalResultRepository.findAllBuAvgWorstAndUsername(user.getUsername(), pageable);
    }

    @Override
    @Transactional
    public List<GraphDto> findGraphData() {
        Users user = getAuthentication();
        return finalResultRepository.findAllForGraph(user.getUsername());
    }

    @Override
    @Transactional
    public void deleteById(UUID id, String username) {
        finalResultRepository.findByIdAndUsername(id, username).ifPresent(p -> {
            finalResultRepository.deleteById(p.getId());
        });
    }

    @Override
    @Transactional
    public FinalResutls saveResult(ListPreSolvesDto listPreSolvesDto) {
        Users user = getAuthentication();
        List<PreSolveDto> presolves = listPreSolvesDto.getPresolves();
        if(presolves.size() != 5)
            throw new SolvesBadRequestException();
        List<Long> viewlist = presolves.stream()
                .map(PreSolveDto::getMiliseconds).toList();
        var prefinalResultDto = rubikAlgorithmService.calculateTimes(viewlist);
        FinalResutls finalResutls = FinalResutls.builder()
                .min(prefinalResultDto.getMin()).max(prefinalResultDto.getMax())
                .avg5(prefinalResultDto.getAvg5()).media(prefinalResultDto.getMedia())
                .user(user).build();
        FinalResutls finalResutlssave = finalResultRepository.save(finalResutls);

        List<Solves> solves = presolves.stream()
                .map(p->
                        Solves.builder().algoritm(p.getAlgoritm()).miliseconds(p.getMiliseconds())
                                .finalResutls(finalResutlssave).build()
                ).toList();

        var res = (List<Solves>) solveRepository.saveAll(solves);
        finalResutlssave.setSolves(res);
        return finalResultRepository.findById(finalResutlssave.getId()).orElseThrow();
    }
}

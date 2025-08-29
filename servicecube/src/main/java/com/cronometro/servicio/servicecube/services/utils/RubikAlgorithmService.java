package com.cronometro.servicio.servicecube.services.utils;

import com.cronometro.servicio.servicecube.exceptions.SolvesBadRequestException;
import com.cronometro.servicio.servicecube.models.dtos.AlgoritmDto;
import com.cronometro.servicio.servicecube.models.dtos.PreFinalResultDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
public class RubikAlgorithmService {

    private double calculateMedia(List<Long> timessolve) {
        double res = 0.0;
        if (timessolve.isEmpty())
            return res;
        for (long n : timessolve) {
            res += n;
        }

        return res == 0 ? res : res / timessolve.size();
    }

    private String pickTypeMove(String letter) {
        Random random = new Random();
        int num = random.nextInt(3);
        if (num == 2) return letter;
        if (num == 1) return letter + "'";
        return "2" + letter;
    }

    public PreFinalResultDto calculateTimes(List<Long> timessolves) throws SolvesBadRequestException {
        if (timessolves.isEmpty())
            throw new SolvesBadRequestException();

        List<Long> ordertime = new ArrayList<>(timessolves);
        Collections.sort(ordertime);
        double media = calculateMedia(ordertime);
        double min = ordertime.removeFirst();
        double max = ordertime.removeLast();
        double avg5 = calculateMedia(ordertime);

        return PreFinalResultDto.builder()
                .media(media).max(max).avg5(avg5).min(min)
                .build();
    }


    public AlgoritmDto getAlgoritm() {
        String[] letters = {"R", "U", "D", "B", "F", "L"};
        Random rando = new Random();
        List<String> alg = new ArrayList<>();
        int limit = 21;
        String ant = "";
        while (alg.size() < limit) {
            int index = rando.nextInt(6);
            String letter = letters[index];
            if (!letter.equals(ant)) {
                ant = letter;
                alg.add(pickTypeMove(letter));
            }
        }
        String fullalg = String.join(" ", alg);
        return new AlgoritmDto(fullalg);
    }

}

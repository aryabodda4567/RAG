package org.project.ai.queryai.service;

import org.project.ai.queryai.dto.DataDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DTOWrapper {

    public static DataDTO wrapDTO(List<Map<String, Object>> result) {

        DataDTO dataDTO = new DataDTO();
        for (String header : result.get(0).keySet()) {
            dataDTO.addHeading(header);
        }
        for (Map<String, Object> stringObjectMap : result) {
            dataDTO.addResult(stringObjectMap.values());
        }

        System.out.println(result.get(0).values());
        dataDTO.setStatus(1);
        dataDTO.setMessage("Data fetched");

        return dataDTO;

    }


    public static DataDTO wrapDTO(String message) {

        DataDTO dataDTO = new DataDTO();
        dataDTO.setMessage(message);
        dataDTO.setStatus(0);
        return dataDTO;
    }
}

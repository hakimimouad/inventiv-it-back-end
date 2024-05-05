package com.inventivit.dreamapp.service;

import com.inventivit.dreamapp.entity.Case;
import com.inventivit.dreamapp.repository.CaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class CaseService {

    @Autowired
    private CaseRepository caseRepository;

    public Case getCaseById(Long caseId) {
        return caseRepository.findById(caseId).orElse(null);
    }

    public Case createCase(Case newCase) {
    	newCase.setCreationDate(new Date());
        newCase.setLastUpdateDate(new Date());
        return caseRepository.save(newCase);
    }

    public Case updateCase(Long caseId, Case updatedCase) {
        Case existingCase = caseRepository.findById(caseId).orElse(null);
        if (existingCase != null) {
            // Mise à jour des informations du cas existant avec les données fournies
        	existingCase.setLastUpdateDate(new Date());
            existingCase.setTitle(updatedCase.getTitle());
            existingCase.setDescription(updatedCase.getDescription());
            return caseRepository.save(existingCase);
        }
        return null;
    }

    public void deleteCase(Long caseId) {
        caseRepository.deleteById(caseId);
    }
}

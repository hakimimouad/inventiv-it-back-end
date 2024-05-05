package com.inventivit.dreamapp.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.inventivit.dreamapp.entity.Case;
import com.inventivit.dreamapp.service.CaseService;

@RestController
@RequestMapping("/cases")
public class CaseController {

    @Autowired
    private CaseService caseService;

    @GetMapping("/{caseId}")
    public Case getCaseById(@PathVariable Long caseId) {
        return caseService.getCaseById(caseId);
    }

    @PostMapping("/")
    public Case createCase(@RequestBody Case newCase) {
        return caseService.createCase(newCase);
    }

    @PutMapping("/{caseId}")
    public Case updateCase(@PathVariable Long caseId, @RequestBody Case updatedCase) {
        return caseService.updateCase(caseId, updatedCase);
    }

    @DeleteMapping("/{caseId}")
    public void deleteCase(@PathVariable Long caseId) {
        caseService.deleteCase(caseId);
    }
}

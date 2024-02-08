package edu.ucsb.cs156.example.controllers;

import edu.ucsb.cs156.example.entities.UCSBOrganizations;
import edu.ucsb.cs156.example.errors.EntityNotFoundException;
import edu.ucsb.cs156.example.repositories.UCSBOrganizationsRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.time.LocalDateTime;

@Tag(name = "UCSBOrganizations")
@RequestMapping("/api/ucsborganizations")
@RestController
@Slf4j
public class UCSBOrganizationsController extends ApiController {

    @Autowired
    UCSBOrganizationsRepository ucsbOrganizationsRepository;

    @Operation(summary= "List all ucsb organzations")
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/all")
    public Iterable<UCSBOrganizations> allUCSBOrganizations() {
        Iterable<UCSBOrganizations> organizations = ucsbOrganizationsRepository.findAll();
        return organizations;
    }

    @Operation(summary= "Create a new organization")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/post")
    public UCSBOrganizations postUCSBOrganizations(
            @Parameter(name="orgCode") @RequestParam String orgCode,
            @Parameter(name="orgTranslationShort") @RequestParam String orgTranslationShort,
            @Parameter(name="orgTranslation") @RequestParam String orgTranslation,
            @Parameter(name="inactive") @RequestParam boolean inactive
    )
            throws JsonProcessingException {

        // For an explanation of @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        // See: https://www.baeldung.com/spring-date-parameters

        UCSBOrganizations orgs = new UCSBOrganizations();
        commons.setorgCode(orgCode);
        commons.setorgTranslationShort(orgTranslationShort);
        commons.setorgTranslation(orgTranslation);
        commons.setinactive(inactive);

        UCSBOrganizations savedOrgs = ucsbOrganizationsRepository.save(orgs);

        return savedOrgs;
    }

    @Operation(summary= "Get a single organization")
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("")
    public UCSBOrganizations getById(
            @Parameter(name="orgCode") @RequestParam String orgCode) {
        UCSBOrganizations ucsborganizations = ucsbOrganizationRepository.findById(orgCode)
                .orElseThrow(() -> new EntityNotFoundException(UCSBOrganizations.class, orgCode));

        return ucsbOrganizations;
    }

    @Operation(summary= "Delete a UCSBOrganizations")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("")
    public Object deleteUCSBOrganizations(
            @Parameter(name="orgCode") @RequestParam String orgCode) {
        UCSBOrganizations ucsbOrganizations = ucsbOrganizationsRepository.findById(orgCode)
                .orElseThrow(() -> new EntityNotFoundException(UCSBOrganizations.class, id));

        ucsbOrganizationsRepository.delete(ucsbOrganizations);
        return genericMessage("UCSBOrganizations with orgCode %s deleted".formatted(orgCode));
    }

    @Operation(summary= "Update a single organization")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("")
    public UCSBOrganizations updateUCSBOrganizations(
            @Parameter(name="orgCode") @RequestParam String orgCode,
            @RequestBody @Valid UCSBOrganizations incoming) {

        UCSBOrganizations ucsbOrganizations = ucsbOrganizationsRepository.findById(orgCode)
                .orElseThrow(() -> new EntityNotFoundException(UCSBOrganizations.class, orgCode));

        ucsbOrganizations.setorgTranslationShort(incoming.getorgTranslationShort());
        ucsbOrganizations.setorgTranslation(incoming.getorgTranslation());
        ucsbOrganizations.setinactive(incoming.getinactive());

        ucsbOrganizationsRepository.save(ucsbOrganizations);

        return ucsbOrganizations;
    }
}

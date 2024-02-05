package edu.ucsb.cs156.example.controllers;

import edu.ucsb.cs156.example.entities.UCSBDiningMenu;
import edu.ucsb.cs156.example.errors.EntityNotFoundException;
import edu.ucsb.cs156.example.repositories.UCSBDiningMenuRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
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

@Tag(name = "UCSBDiningCommonsMenuItem")
@RequestMapping("/api/ucsbdiningmenu")
@RestController
@Slf4j
public class UCSBDiningMenuController extends ApiController {

    @Autowired
    UCSBDiningMenuRepository ucsbMenuRepository;

    @Operation(summary= "Lists the menus at ucsb dining commons")
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/all")
    public Iterable<UCSBDiningMenu> allMenus() {
        Iterable<UCSBDiningMenu> menus = ucsbMenuRepository.findAll();
        return menus;
    }

    @Operation(summary= "Create a new meal")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/post")
    public UCSBDiningMenu postMeal(
        @Parameter(name="diningCommonsCode") @RequestParam String diningCommonsCode,
        @Parameter(name="name") @RequestParam String name,
        @Parameter(name="station") @RequestParam String station,
        @Parameter(name="id") @RequestParam long id
        )
        {

        UCSBDiningMenu menus = new UCSBDiningMenu();
        menus.setDiningCommonsCode(diningCommonsCode);
        menus.setName(name);
        menus.setStation(station);
        menus.setId(id);

        UCSBDiningMenu savedMenus = ucsbMenuRepository.save(menus);

        return savedMenus;
    }

    @Operation(summary= "Get a single menu item")
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("")
    public UCSBDiningMenu getById(
            @Parameter(name="id") @RequestParam long id) {
        UCSBDiningMenu menus = ucsbMenuRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(UCSBDiningMenu.class, id));

        return menus;
    }

    @Operation(summary= "Delete a UCSBDiningCommons")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("")
    public Object deleteCommons(
            @Parameter(name="id") @RequestParam long id) {
        UCSBDiningMenu menus = ucsbMenuRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(UCSBDiningMenu.class, id));

        ucsbMenuRepository.delete(menus);
        return genericMessage("UCSBDiningMenuItem with id %s deleted".formatted(id));
    }

    @Operation(summary= "Update a single menu item")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("")
    public UCSBDiningMenu updateMenus(
            @Parameter(name="id") @RequestParam long id,
            @RequestBody @Valid UCSBDiningMenu incoming) {

        UCSBDiningMenu menus = ucsbMenuRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(UCSBDiningMenu.class, id));


        menus.setDiningCommonsCode(incoming.getDiningCommonsCode());
        menus.setName(incoming.getName());
        menus.setStation(incoming.getStation());

        ucsbMenuRepository.save(menus);

        return menus;
    }
}

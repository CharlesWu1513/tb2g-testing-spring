package org.springframework.samples.petclinic.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.samples.petclinic.service.ClinicServiceImpl;
import org.springframework.validation.BindingResult;

import java.util.Map;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;


@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {
    private static final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "owners/createOrUpdateOwnerForm";
    @Mock
    Map<String, Object> model;

    @Mock
    BindingResult result;

    @Mock
    ClinicServiceImpl service;

    @InjectMocks
    OwnerController controller;

    Owner owner = new Owner();

    @BeforeEach
    void setUp() {

    }

    @Test
    void initCreationForm() {
        String view = controller.initCreationForm(model);
        then(model).should().put(anyString(), any());
        assertThat(view).isEqualToIgnoringCase(VIEWS_OWNER_CREATE_OR_UPDATE_FORM);
    }

    @Test
    void processCreationFormNoError() {
        System.out.println("owner's id in first method a : " + owner.getId());
        owner.setId(0);
        System.out.println("owner's id after first method a : " + owner.getId());
        given(result.hasErrors()).willReturn(true);
        String view = controller.processCreationForm(owner, result);
        assertThat(view).isEqualToIgnoringCase(VIEWS_OWNER_CREATE_OR_UPDATE_FORM);
    }

    @Test
    void processCreationFormWithError() {
        System.out.println("owner's id before second method b : " + owner.getId());
        owner.setId(1);
        System.out.println("owner's id after second method b : " + owner.getId());

        given(result.hasErrors()).willReturn(false);
        String view = controller.processCreationForm(owner, result);
        then(service).should().saveOwner(owner);
        assertThat(view).isEqualToIgnoringCase("redirect:/owners/" + owner.getId());
    }

    @Test
    void initFindForm() {
    }

    @Test
    void processFindForm() {
    }

    @Test
    void initUpdateOwnerForm() {
    }

    @Test
    void processUpdateOwnerForm() {
    }

    @Test
    void showOwner() {
    }
}
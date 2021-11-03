package org.springframework.samples.petclinic.web;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.reset;
import static  org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@SpringJUnitWebConfig(locations = {"classpath:spring/mvc-test-config.xml","classpath:spring/mvc-core-config.xml"})
class OwnerControllerWebConfigTest {
    @Autowired
    OwnerController ownerController;

    @Autowired
    ClinicService clinicService;

    MockMvc mockMvc;

    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;

    /*
    * Add unit test for ownercontroller
    * test method initCreationForm
    * use HttpGet
    * verify Http status
    * verify owner property
    * verify view name returned
    * */

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();
    }

    //org.mockito.exceptions.verification.TooManyActualInvocations:
    //clinicService.findOwnerByLastName(
    //    <any string>
    //);
    //Wanted 1 time:
    //-> at org.springframework.samples.petclinic.web.OwnerControllerWebConfigTest.testFindOwnerOneResult(OwnerControllerWebConfigTest.java:79)
    //But was 2 times:
    //-> at org.springframework.samples.petclinic.web.OwnerController.processFindForm(OwnerController.java:91)
    //-> at org.springframework.samples.petclinic.web.OwnerController.processFindForm(OwnerController.java:91)


    @AfterEach
    void tearDown() {
        reset(clinicService);
    }

    @Test
    void testUpdateOwnerPostValid() throws Exception{
        mockMvc.perform(post("/owners/{ownerId}/edit", 1)
                .param("firstName", "Charles")
                .param("lastName", "Wu")
                .param("Address", "160 Pleasant st")
                .param("city","Boston")
                .param("telephone","8572059899"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/{ownerId}"));
    }

    @Test
    void testUpdateOwnerPostNotValid() throws Exception{
        mockMvc.perform(post("/owners/{ownerId}/edit", 1)
                .param("firstName", "Charles")
                .param("lastName", "Wu")
                .param("Address", "160 Pleasant st"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/createOrUpdateOwnerForm"));
    }

    //@Valid test
    @Test
    void testNewOwnerPostValid() throws Exception{
        mockMvc.perform(post("/owners/new")
                .param("firstName", "Charles")
                .param("lastName", "Wu")
                .param("Address", "160 Pleasant st")
                .param("city","Boston")
                .param("telephone","8572059899"))
                .andExpect(status().is3xxRedirection());

    }

    @Test
    void testNewOwnerPostNotValid() throws Exception{
        mockMvc.perform(post("/owners/new")
                .param("firstName", "Charles")
                .param("lastName", "Wu"))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasErrors("owner"))
                .andExpect(model().attributeHasFieldErrors("owner","address"))
                .andExpect(model().attributeHasFieldErrors("owner","telephone"))
                .andExpect(view().name("owners/createOrUpdateOwnerForm"));

    }
    @Test
    void testReturnListOfOwners() throws Exception{
        given(clinicService.findOwnerByLastName("")).willReturn(Lists.newArrayList(new Owner(), new Owner()));
        mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownersList"));

        then(clinicService).should().findOwnerByLastName(stringArgumentCaptor.capture());
        assertThat(stringArgumentCaptor.getValue()).isEqualToIgnoringCase("");
    }

    @Test
    void testFindOwnerOneResult() throws Exception{
        Owner one = new Owner();
        one.setId(1);
        final String findJustOne = "FindJustOne";
        one.setLastName(findJustOne);
        given(clinicService.findOwnerByLastName(findJustOne)).willReturn(Lists.newArrayList(one));
        mockMvc.perform(get("/owners").param("lastName",findJustOne))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"));

        then(clinicService).should().findOwnerByLastName(anyString());

    }

    @Test
    void testFindByNameNotFound() throws Exception{
        mockMvc.perform(get("/owners")
                .param("lastName", "Dont find me."))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/findOwners"));

    }

    @Test
    void initCreationFormTest() throws Exception{
        mockMvc.perform(get("/owners/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("owner"))
                .andExpect(view().name("owners/createOrUpdateOwnerForm"));
    }


}
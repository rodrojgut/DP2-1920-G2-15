package org.springframework.samples.petclinic.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyList;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Opinion;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.repository.VetRepository;
import org.springframework.stereotype.Service;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class OpinionServiceTest {

    @Autowired
    protected OpinionService opinionService;

    @Autowired
    protected UserService userService;
    
    @Autowired
    protected VetRepository vetRepo;

    private Opinion opinionTest;

  
    @BeforeEach
    void init(){
        User userTest = new User();
        userTest.setEnabled(true);
        userTest.setUsername("userTest");
        userTest.setPassword("1234");
        this.userService.saveUser(userTest);

        Vet vetTest = this.vetRepo.findById(1);

        this.opinionTest = new Opinion();
        opinionTest.setComentary("Comentario de prueba");
        opinionTest.setDate(LocalDateTime.of(2020, 02, 12, 12, 30, 58));
        opinionTest.setVet(vetTest);
        opinionTest.setUser(userTest);
        opinionTest.setPuntuation(5);
        opinionTest.setId(1);
        this.opinionService.saveOpinion(opinionTest);
    }

    @Test
    void findAllMineTest() throws IllegalArgumentException{

        Opinion opinion = this.opinionTest;
        Iterable<Opinion> opinionsMine = this.opinionService.findAllMine("userTest");
        assertEquals(opinionsMine.iterator().next(), opinion); //Comprueba que el usuario de pruba contenga SU opinion
        opinionsMine =this.opinionService.findAllMine("NotUser");
        Iterable<Opinion> vacio=anyList(); 
        assertEquals(opinionsMine,vacio );
    }

    @Test
    void findAllTest(){
        Iterable<Opinion> list = this.opinionService.findAll();
        assertNotNull(list.iterator().next());
    }

    @Test
    void deleteTest(){
        this.opinionService.deleteOpinion(this.opinionTest);
        assert(!this.opinionService.findById(1).isPresent());
    }
}
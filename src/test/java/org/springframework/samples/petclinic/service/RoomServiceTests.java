
package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import javax.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.samples.petclinic.model.Room;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class RoomServiceTests {

    public Validator createValidator() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.afterPropertiesSet();
        return localValidatorFactoryBean;
    }

    @Autowired
    protected RoomService roomService;

    // FindbyId positive
    @Test
    void shouldFindRoomById() {
        Room room = this.roomService.findRoomById(1);
        assertThat(room.getId()).isEqualTo(1);

    }

    // FindbyId negative
    @ParameterizedTest
    @ValueSource(ints = { -10, -40 })
    void shouldFindRoomByIdNegative(int argument) {
        Assertions.assertThrows(NullPointerException.class, () -> {
            this.roomService.findRoomById(argument).getClass();
        });
    }

    @Test
    void shouldFindRoomAll() {

        Collection<Room> rooms = this.roomService.findAll();

        Room room1 = EntityUtils.getById(rooms, Room.class, 1);
        assertThat(room1.getName()).isEqualTo("Quirofano1");
        Room room4 = EntityUtils.getById(rooms, Room.class, 4);
        assertThat(room4.getFloor()).isEqualTo(2);

    }

    @Test
    void shouldFindRoomNegativeAll() {

        Collection<Room> rooms = this.roomService.findAll();
        Room room1 = EntityUtils.getById(rooms, Room.class, 1);
        Room room2 = EntityUtils.getById(rooms, Room.class, 2);
        rooms.add(room1);
        rooms.add(room2);
        Assertions.assertThrows(NullPointerException.class, () -> {
            this.roomService.findRoomById(9).getName().isEmpty();
        });

    }

    // Positive Insert
    @Test
    void shouldInsertRoom() {
        Room rooms = this.roomService.findRoomById(1);
        Collection<Room> found = this.roomService.findAll();
        int count = found.size();

        final Room room = new Room();
        Collection<String> medicalTeam = new ArrayList<>();
        medicalTeam.add("bisturi");

        room.setName("Prueba");
        room.setFloor(1);
        room.setMedicalTeam(medicalTeam);

        this.roomService.saveRoom(room);
        assertThat(room.getId().longValue()).isNotEqualTo(0);

        rooms = this.roomService.findRoomById(1);
        assertThat(rooms.getId().longValue()).isNotEqualTo(count + 1);
    }

    // Negative Insert
    @Test
    public void shouldInsertNegativeRoom() {

        Room room = new Room();
        Collection<String> medicalTeam = new ArrayList<>();
        medicalTeam.add("bisturi");

        room.setName("aaaaaa");
        room.setFloor(1);
        room.setMedicalTeam(medicalTeam);
        Assertions.assertThrows(Exception.class, () ->{room.setName("");
            this.roomService.saveRoom(room);});

        // Validator validator = createValidator();

        
       /*  Set<ConstraintViolation<Room>> constraintViolations = validator.validate(room);
        assertThat(constraintViolations.size()).isEqualTo(0);
        ConstraintViolation<Room> violation = constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("floor");
        //assertThat(violation.getMessage()).isEqualTo("must match "); */

    }

    // Update positive
    @Test
    @Transactional
    void shouldUpdateRoom() {
        Room room = this.roomService.findRoomById(1);
        String oldName = room.getName();
        String newName = oldName + "XXXXXXXX";

        room.setName(newName);
        this.roomService.saveRoom(room);
        room = this.roomService.findRoomById(1);
        assertThat(room.getName()).isEqualTo(newName);

    }

    @ParameterizedTest
    @ValueSource(ints = { 1 })
    void shoulThrowExceptionUpdateRoom(int id) {
        Room room = this.roomService.findRoomById(id);
        String newName = null;
        room.setName(newName);
        this.roomService.saveRoom(room);
        Assertions.assertThrows(NullPointerException.class, () -> {
            this.roomService.findRoomById(id).getName().isEmpty();
        });

    }

    @Test
    void shouldDeletetRoom() {
        Collection<Room> found1 = this.roomService.findAll();
        int count1 = found1.size();
        Room room1 = this.roomService.findRoomById(1);
        this.roomService.delete(room1);
        Collection<Room> found2 = this.roomService.findAll();
        int count2 = found2.size();
        assertTrue(count2 < count1);

    }

    @ParameterizedTest
    @ValueSource(ints = { -16, -30 })
    void shouldThrowExceptionDeleteRoom(int id) {
        Room room1 = this.roomService.findRoomById(id);
        Assertions.assertThrows(InvalidDataAccessApiUsageException.class, () -> {
            this.roomService.delete(room1);
        });
    }
}

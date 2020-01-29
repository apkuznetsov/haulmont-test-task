package com.haulmont.testtask.PharmacyDb.DaoableEntities;

import com.haulmont.testtask.PharmacyDb.Dtos.Doctor;

import java.sql.SQLException;
import java.util.List;

public interface DaoableDoctor {
    String ID = "id";
    String FORENAME = "forename";
    String PATRONYMIC = "patronymic";
    String SURNAME = "surname";
    String SPECIALIZATION_ID = "specialization_id";

    Doctor findDoctor(long id) throws SQLException, ClassNotFoundException;

    long insertDoctor(Doctor doctor);

    boolean updateDoctor(Doctor doctor);

    boolean deleteDoctor(long id);

    List<Doctor> getAllDoctors();
}

package lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.bo;

import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.bo.custom.impl.*;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dao.DAOFactory;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dao.SuperDAO;
import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dao.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory() {}

    public static BOFactory getInstance() {
        if (boFactory == null) {
            boFactory = new BOFactory();
        }
        return boFactory;
    }

    public enum boType {
        USER,THERAPIST,THERAPYSESSION,PATIENT,THERAPYPROGRAMME,PAYEMENT,REGISTRATION
    }

    public SuperBO getBo(boType type) {
        switch (type){
            case USER:
                return new UserBOImpl();
            case THERAPIST:
                return new TherapistBOImpl();
            case THERAPYSESSION:
                return new TherapySessionBOImpl();
            case PATIENT:
                return new PatientBOImpl();
            case THERAPYPROGRAMME:
                return new TherapyProgramBOImpl();
            case PAYEMENT:
                return new PaymentBOImpl();
            case REGISTRATION:
                return new RegistrationBoImpl();

        }
        return null;
    }
}

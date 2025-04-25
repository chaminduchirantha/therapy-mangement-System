package lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dao;

import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {}

    public static DAOFactory getInstance() {
        if (daoFactory == null) {
            daoFactory = new DAOFactory();
        }
        return daoFactory;
    }

    public enum daoType {
        USER,THERAPIST,THERAPYSESSION,PATIENT,THERAPYPROGRAMME,PAYEMENT,REGISTRATION
    }

    public SuperDAO getDao(daoType type) {
        switch (type){
            case USER:
                return new UserDAOImpl();
            case THERAPIST:
                return new TherapistDAOImpl();
            case THERAPYSESSION:
                return new TherapySessionDAOImpl();
            case PATIENT:
                return new PatientDAOImpl();
            case THERAPYPROGRAMME:
                return new TherapyProgramDAOImpl();
            case PAYEMENT:
                return new PaymentDAOImpl();
            case REGISTRATION:
                return new RegistrationDAOImpl();

        }
        return null;
    }}

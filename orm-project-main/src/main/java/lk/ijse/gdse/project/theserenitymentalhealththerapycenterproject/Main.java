package lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject;

import lk.ijse.gdse.project.theserenitymentalhealththerapycenterproject.config.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class Main {
    public static void main(String[] args) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.close();
    }
}

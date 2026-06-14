package repository;

import model.Medicine;
import model.PrescriptionType;

import java.time.LocalDate;

public class DataInitializer {

    public static void initMedicineRepository(Repository<Medicine, String> repo) {
        repo.add(new Medicine("Парацетамол", PrescriptionType.WITHOUTPRESCRIPTION,
                LocalDate.now().plusDays(30), 50));
        repo.add(new Medicine("Амоксициллин", PrescriptionType.PRESCRIPTION,
                LocalDate.now().plusDays(20), 20));
        repo.add(new Medicine("Витаминки", PrescriptionType.WITHOUTPRESCRIPTION,
                LocalDate.now().minusDays(5), 10));
    }
}
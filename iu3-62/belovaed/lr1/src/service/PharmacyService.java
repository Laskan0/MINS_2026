package service;

import exception.*;
import model.Medicine;
import model.Sale;
import repository.Repository;
import java.util.List;

public class PharmacyService implements PharmacyServiceInterface {
    private Repository<Medicine, String> medicineRepo;
    private Repository<Sale, String> saleRepo;

    public PharmacyService(Repository<Medicine, String> medicineRepo,
                           Repository<Sale, String> saleRepo) {
        this.medicineRepo = medicineRepo;
        this.saleRepo = saleRepo;
    }

    public void addMedicine(Medicine medicine) {
        if (medicine.isExpired()) throw new ExpiredMedicineException("Препарат просрочен");
        medicineRepo.add(medicine);
    }

    public void deleteMedicine(String id) {
        if (medicineRepo.findById(id) == null) throw new MedicineNotFoundException("Лекарство не найдено");
        medicineRepo.deleteById(id);
    }

    public void sellMedicine(String id, int quantity, boolean hasPrescription) {
        Medicine med = medicineRepo.findById(id);
        if (med == null) throw new MedicineNotFoundException("Лекарство не найдено");
        if (med.isExpired()) throw new ExpiredMedicineException("Препарат просрочен");
        if (med.isPrescriptionRequired() && !hasPrescription)
            throw new PrescriptionRequiredException("Нужен рецепт");

        med.reduceQuantity(quantity);
        saleRepo.add(new Sale(med, quantity));
    }

    public List<Medicine> getAllMedicines() { return medicineRepo.findAll(); }
    public List<Sale> getSales() { return saleRepo.findAll(); }
}
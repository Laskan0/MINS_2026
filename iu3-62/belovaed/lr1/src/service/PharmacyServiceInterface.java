package service;

import model.Medicine;
import model.Sale;

import java.util.List;

public interface PharmacyServiceInterface {
    void addMedicine(Medicine medicine);
    void deleteMedicine(String id);
    void sellMedicine(String id, int quantity, boolean hasPrescription);
    List<Medicine> getAllMedicines();
    List<Sale> getSales();
}
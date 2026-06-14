package service;

import exception.*;
import model.Medicine;
import model.Sale;
import observer.EventType;
import observer.Observer;
import report.Report;
import report.ReportFactory;
import repository.MedicineRepository;
import repository.Repository;
import repository.SaleRepository;
import service.strategy.PricingStrategy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PharmacyService implements PharmacyServiceInterface {
    private Repository<Medicine, String> medicineRepo;
    private Repository<Sale, String> saleRepo;
    private List<Observer> observers = new ArrayList<>();

    public PharmacyService(Repository<Medicine, String> medicineRepo,
                           Repository<Sale, String> saleRepo) {
        this.medicineRepo = medicineRepo;
        this.saleRepo = saleRepo;
    }


    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    private void notifyObservers(Medicine medicine, EventType eventType) {
        for (Observer observer : observers) {
            observer.update(medicine, eventType);
        }
    }

    public void addMedicine(Medicine medicine) {
        if (medicine.isExpired()) {
            notifyObservers(medicine, EventType.EXPIRED);
            throw new ExpiredMedicineException("Препарат просрочен");
        }
        medicineRepo.add(medicine);
        notifyObservers(medicine, EventType.ADDED);
    }

    public void deleteMedicine(String id) {
        Medicine medicine = medicineRepo.findById(id);
        if (medicineRepo.findById(id) == null) throw new MedicineNotFoundException("Лекарство не найдено");
        medicineRepo.deleteById(id);
        notifyObservers(medicine, EventType.REMOVED);
    }

    public void sellMedicine(String id, int quantity, boolean hasPrescription, PricingStrategy strategy) {
        Medicine med = medicineRepo.findById(id);
        if (med == null) throw new MedicineNotFoundException("Лекарство не найдено");
        if (med.isExpired()) {
            notifyObservers(med, EventType.EXPIRED);
            throw new ExpiredMedicineException("Препарат просрочен");
        }
        if (med.isPrescriptionRequired() && !hasPrescription)
            throw new PrescriptionRequiredException("Нужен рецепт");

        med.reduceQuantity(quantity);
        double[] prices = strategy.calculatePrice(med, quantity);
        double unitPrice = prices[0];
        double totalPrice = prices[1];

        Sale sale = new Sale(med, quantity, unitPrice, totalPrice);
        saleRepo.add(sale);
        notifyObservers(med, EventType.SOLD);
    }

    public List<Medicine> getAllMedicines() { return medicineRepo.findAll(); }
    public List<Sale> getSales() { return saleRepo.findAll(); }
}
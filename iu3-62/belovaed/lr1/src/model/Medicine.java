package model;

import exception.InsufficientQuantityException;
import exception.InvalidInputException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Medicine {
    private static int counter = 1;
    private String id;
    private String name;
    private PrescriptionType prescriptionType;
    private LocalDate expirationDate;
    private int quantity;

    public Medicine(String name, PrescriptionType prescriptionType, LocalDate expirationDate, int quantity) {
        if (quantity <= 0) throw new InvalidInputException("Количество должно быть положительным");
        this.id = String.valueOf(counter++);
        this.name = name;
        this.prescriptionType = prescriptionType;
        this.expirationDate = expirationDate;
        this.quantity = quantity;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public boolean isPrescriptionRequired() {
        return prescriptionType.isPrescriptionRequired();
    }

    public boolean isExpired() { return expirationDate.isBefore(LocalDate.now()); }

    public void reduceQuantity(int amount) {
        if (amount <= 0) throw new InvalidInputException("Количество должно быть больше 0");
        if (amount > quantity) throw new InsufficientQuantityException("Недостаточно товара");
        quantity -= amount;
    }

    @Override
    public String toString() {
        String expired = isExpired() ? " (просрочено)" : "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return "ID: " + id +
                " | " + name +
                " | Кол-во: " + quantity +
                " | Годен до: " + expirationDate.format(formatter) + expired +
                " | " + prescriptionType.getDisplayName();
    }
}
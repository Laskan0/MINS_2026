package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Sale {
    private static int counter = 1;
    private String id;
    private Medicine medicine;
    private int quantity;
    private LocalDateTime date;

    public Sale(Medicine medicine, int quantity) {
        this.id = String.valueOf(counter++);
        this.medicine = medicine;
        this.quantity = quantity;
        this.date = LocalDateTime.now();
    }

    public String getId() { return id; }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy");
        return "ID: " + id +
                " | " + date.format(formatter) +
                " | " + medicine.getName() + " x" + quantity;
    }
}
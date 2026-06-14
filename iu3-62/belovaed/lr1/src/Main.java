import model.Medicine;
import model.Sale;
import repository.DataInitializer;
import repository.MedicineRepository;
import repository.Repository;
import repository.SaleRepository;
import service.PharmacyService;
import service.PharmacyServiceInterface;
import ui.ConsoleUI;


public class Main {
    public static void main(String[] args) {

        Repository<Medicine, String> medicineRepo = new MedicineRepository();
        Repository<Sale, String> saleRepo = new SaleRepository();

        DataInitializer.initMedicineRepository(medicineRepo);

        PharmacyServiceInterface service = new PharmacyService(medicineRepo, saleRepo);
        ConsoleUI ui = new ConsoleUI(service);
        ui.start();
    }
}
// в репозитории init где тестовые данные - туда вынести - done
// интерфейс фармаси => его в консоль (у каждого сервиса свой интерфейс) (без этого зависит от конкретики, а не абстракции) - done
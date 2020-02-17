package View;

import Controller.DAO.DAOCar;
import Controller.DAO.DAOClient;
import Controller.DAO.DAORequest;
import Model.Entities.Car;
import Model.Entities.Client;
import Model.Entities.Request;
import Model.Exception.DAOException;
import Model.Exception.ViewException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Scanner;

/**
 * Class to work with view
 */
public class Main {
    static final Logger rootLogger = LogManager.getRootLogger();
    static final Logger mainLogger = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws ViewException {

        System.out.println("------------Menu for Administrator------------");
        System.out.println("1 <model> <rental_time> <id_passport> - Leave request");
        System.out.println("2 <model> - Get list of requests for car of this model");
        System.out.println("3 <model> - Get amount  of available cars of this model");
        System.out.println("4 <car_id> - Get state  of car with this id");
        System.out.println("5 <request_id> <car_id> - Process request № id, client must pay the bill");
        System.out.println("6 <car_id> - Return car № id");
        System.out.println("7 <model> - Get all available cars of this model");
        System.out.println("8 - Get all cars");
        System.out.println("9 - Get all requests");
        System.out.println("10 - Finish work");

        DAOCar daoCar = new DAOCar();
        DAOClient daoClient = new DAOClient();
        DAORequest daoRequest = new DAORequest();

        boolean flag = true;
        while (flag)
        {
            System.out.println(">> ");
            Scanner in = new Scanner(System.in);
            String tokens[] = in.nextLine().split(" ");
            int type = Integer.parseInt(tokens[0]);
            String model = "";
            String idPassport = "";
            int rentalTime = 0;
            int id = 0;
            int requestId = 0;
            switch (type) {
                case (1):
                    model = tokens[1];
                    rentalTime = Integer.parseInt(tokens[2]);
                    idPassport = tokens[3];
                    try {
                        if (!daoClient.getClientById(idPassport)) {
                            System.out.println("Put this client in database.");
                            System.out.println("Enter client's data: <name> <residence_address>, <birth_date>");
                            Scanner inClient = new Scanner(System.in);
                            String tokensClient[] = inClient.nextLine().split(" ");
                            String name = tokensClient[0];
                            String residenceAddress = tokensClient[1];
                            String birthDate = tokensClient[2];

                            Client client = new Client();
                            client.setIdPassport(idPassport);
                            client.setName(name);
                            client.setResidenceAddress(residenceAddress);
                            client.setBirthDate(birthDate);

                            daoClient.insertClient(client);
                        }

                        Request request = new Request();
                        request.setId(0);
                        request.setModel(model);
                        request.setRentalTime(rentalTime);
                        request.setIdPassport(idPassport);

                        daoRequest.insertRequest(request);

                    } catch (DAOException e) {
                        mainLogger.error("Leave request exception. " + e.getMessage());
                    }
                    break;
                case (2):
                    model = tokens[1];
                    try {
                        List<Request> requests = daoRequest.getAllRequestsByModel(model);
                        System.out.println("Requests: ");
                        for (Request req : requests) {
                            System.out.println(req.getId() + " " + req.getModel() + " " + req.getRentalTime() + " " + req.getIdPassport());
                        }
                    } catch (DAOException e) {
                        mainLogger.error("Leave request exception. " + e.getMessage());
                    }
                    break;
                case (3):
                    model = tokens[1];
                    try {
                        int amount = daoCar.getAmountByCarModel(model);
                        System.out.println("Amount: ");
                        System.out.println(amount);
                    } catch (DAOException e) {
                        mainLogger.error("Get amount  of available cars of this model exception. " + e.getMessage());
                    }
                    break;
                case (4):
                    id = Integer.parseInt(tokens[1]);
                    try {
                        boolean state = daoCar.getCarStateByCarId(id);
                        System.out.println("State: ");
                        if (state)
                            System.out.println("Available.");
                        else
                            System.out.println("Not available.");
                    } catch (DAOException e) {
                        mainLogger.error("Get state  of car with this id exception. " + e.getMessage());
                    }
                    break;
                case (5):
                    requestId = Integer.parseInt(tokens[1]);
                    id = Integer.parseInt(tokens[2]);
                    System.out.println("After client payed the bill, delete his request and update car's state.");
                    try {
                        daoCar.updateCarOnState(id, false);
                        System.out.println("Car is not available");
                        daoRequest.deleteRequest(requestId);
                    } catch (DAOException e) {
                        mainLogger.error("Process request № id, client must pay the bill exception. " + e.getMessage());
                    }
                    break;
                case (6):
                    id = Integer.parseInt(tokens[1]);
                    try {
                        daoCar.updateCarOnState(id, true);
                    } catch (DAOException e) {
                        mainLogger.error("Return car № id exception. " + e.getMessage());
                    }
                    break;
                case (7):
                    model = tokens[1];
                    try {
                        List<Car> cars = daoCar.getCarByCarStateAndModel(true, model);
                        System.out.println("Available cars: ");
                        for (Car cr : cars) {
                            System.out.println(cr.getCarId() + " " + cr.getCarModel());
                        }
                    } catch (DAOException e) {
                        mainLogger.error("Get all available cars of this model exception. " + e.getMessage());
                    }
                    break;
                case (8):
                    try {
                        List<Car> cars = daoCar.getAllCars();
                        System.out.println("All cars: ");
                        for (Car cr : cars) {
                            System.out.println(cr.getCarId() + " " + cr.getCarModel() + " " + cr.isCarState());
                        }
                    } catch (DAOException e) {
                        mainLogger.error("Get all available cars of this model exception. " + e.getMessage());
                    }
                    break;
                case (9):
                    try {
                        List<Request> requests = daoRequest.getAllRequests();
                        System.out.println("All requests: ");
                        for (Request req : requests) {
                            System.out.println(req.getId() + " " + req.getModel() + " " + req.getRentalTime() + " " + req.getIdPassport());
                        }
                    } catch (DAOException e) {
                        mainLogger.error("Get all requests exception. " + e.getMessage());
                    }
                    break;
                case (10):
                    flag = false;
                    break;
                default:
                    System.out.println("Wrong enter.");
                    break;
            }
        }

    }
}

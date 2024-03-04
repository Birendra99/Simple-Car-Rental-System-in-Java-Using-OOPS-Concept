import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;
class Car{
    private String cardId;
    private String model;
    private String brand;
    private boolean isAvailable;
    private double basePricePerDay;
    public Car(String cardId, String model,String brand,double basePricePerDay){
        this.cardId=cardId;
        this.model=model;
        this.brand=brand;
        this.basePricePerDay=basePricePerDay;
        isAvailable=true;
    }
    public String getCardId(){
        return cardId;
    }
    public String getModel(){
        return model;
    }
    public String getBrand(){
        return brand;
    }
    public double calculatePrice(int rentalDays){
        return basePricePerDay*rentalDays;
    }
    public boolean isAvailable(){
        return isAvailable;
    }
    public void rent(){
        isAvailable=false;
    }
    public void returnCar(){
        isAvailable=true;
    }




}
class Customer{
    private String customerId;
    private String name;
    public Customer(String customerId, String name){
        this.customerId=customerId;
        this.name=name;
    }
    public String getCustomerId(){
        return customerId;
    }
    public String getName(){
        return name;
    }


}
class Rental{
    private Car car;
    private Customer customer;
    private int days; //for how many days
    public Rental(Car car, Customer customer,int days){
        this.car=car;
        this.customer=customer;
        this.days=days;
    }
    public Car getCar(){
        return car;
    }
    public Customer getCustomer(){
        return customer;
    }
    public int getDays(){
        return days;
    }




}

class CarRentalSystem{
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;
    public CarRentalSystem(){
        cars=new ArrayList<>();
        customers=new ArrayList<>();
        rentals=new ArrayList<>();
    }
    public void addCar(Car car){
        cars.add(car);
    }
    public void addCustomer(Customer customer){
        customers.add(customer);
    }
    public void rentCar(Car car,Customer customer,int days){
        // User come to rent the car
        if(car.isAvailable()){
            car.rent();
            rentals.add(new Rental(car,customer,days));
        }
        else{
            System.out.println("Car is not available for rent.");
        }
    }
    // If user comes to return the car.
    public void returnCar(Car car){
        car.returnCar();
        Rental rentaltoRemove=null;
        for(Rental rental:rentals){
            if(rental.getCar()==car){
                rentaltoRemove=rental;
                break;
            }
        }
        if(rentaltoRemove!=null){
            rentals.remove(rentaltoRemove);
//            System.out.println("Car Returned Successfully.");
        }
        else{
            System.out.println("Car was not rented.");
        }

    }
    public void menu(){
        Scanner scanner=new Scanner(System.in);
        while (true){
            System.out.println("======Car Rental System=======");
            System.out.println("1. Rent a Car");
            System.out.println("2. Return a Car");
            System.out.println("3. Exit");
            System.out.println("Enter Your Choice:");
            int choice=scanner.nextInt();
            scanner.nextLine(); // for next line
            if(choice==1){
                System.out.println("\n==Rent a Car==\n");
                System.out.print("Enter Your Name:");
                String customerName=scanner.nextLine();
                System.out.println("\nAvailable Cars:");
                for(Car car:cars){
                    if(car.isAvailable()){
                        System.out.println(car.getCardId()+ " - "+car.getBrand()+" "+car.getModel());
                    }
                }
                System.out.print("\nEnter the car Id you want to rent: ");
                String carId=scanner.nextLine();
                System.out.print("Enter the number of days for rent ");
                int rentalDays=scanner.nextInt();
                scanner.nextLine();
                Customer newCustomer=new Customer("CUS"+(customers.size()+1),customerName);
                addCustomer(newCustomer);
                Car selectedCar=null;
                for(Car car:cars){
                    if(car.getCardId().equals(carId)&&car.isAvailable()){
                        selectedCar=car;
                        break;
                    }
                }
                if(selectedCar!=null){
                    double totalPrice=selectedCar.calculatePrice(rentalDays);
                    System.out.println("\n==Rental Information==\n");
                    System.out.println("Customer ID: "+newCustomer.getCustomerId());
                    System.out.println("Customer Name: "+newCustomer.getName());
                    System.out.println("Car: "+selectedCar.getBrand()+" "+selectedCar.getModel());
                    System.out.println("Rental Days: "+rentalDays);
                    System.out.printf("Total Price= $%.2f%n",totalPrice);

                    System.out.print("\nConfirm rental. (Y/N): ");
                    String confirm=scanner.nextLine();
                    if(confirm.equalsIgnoreCase("Y")){
                        rentCar(selectedCar,newCustomer,rentalDays);
                        System.out.println("\n Car Rented Successfully.");
                    }
                    else {
                        System.out.println("\nRental Cancelled.");
                    }


                }
                else
                {
                    System.out.println("\n Invalid Car selection or Car not available for rent now.");
                }
            }
            else if(choice==2){
                System.out.println("\n== Return a Car ==\n");
                System.out.println("Enter the car Id you want to return: ");
                String cardId=scanner.nextLine();
                Car carToReturn=null;
                for(Car car:cars){
                    if(car.getCardId().equals(cardId)&& !car.isAvailable()){
                        carToReturn=car;
                        break;
                    }
                }
                if(carToReturn!=null){
                    Customer customer=null;
                    for(Rental rental:rentals){
                        if(rental.getCar()==carToReturn){
                            customer=rental.getCustomer();
                            break;
                        }
                    }
                 if(customer!=null){
                     returnCar(carToReturn);
                     System.out.println("Car Returned Successfully by "+customer.getName());
                 }
                 else {
                     System.out.println("Car was not rented or rental information is missing.");
                 }

                }
                else {
                    System.out.println("Invalid car Id or Car is not rented.");
                }


            }
            else if(choice==3){
                break;

            }
            else{
                System.out.println("Invalid Choice. Please enter a valid option.");
            }
        }
        System.out.println("\n Thank You for using the Car Rental System.");

    }


}
public class Main{
    public static void main(String[] args) {
        CarRentalSystem rentalSystem=new CarRentalSystem();
        Car car1=new Car("COO1","Maruti","Canry",80);
        Car car2=new Car("C002","Honda","Accord",90);
        Car car3=new Car("C003","Mahindra","Thar",160);
        rentalSystem.addCar(car1);
        rentalSystem.addCar(car2);
        rentalSystem.addCar(car3);
        rentalSystem.menu();


    }
}
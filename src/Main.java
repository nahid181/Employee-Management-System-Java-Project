import java.util.ArrayList;
import java.util.List;

// Observer pattern
interface EmployeeObserver {
    void update(Employee employee);
}

// Command pattern
interface Command {
    void execute();
}

// State pattern
abstract class EmployeeState {
    public abstract void promote(Employee employee);
}

// Concrete State - Satisfactory
class SatisfactoryState extends EmployeeState {
    @Override
    public void promote(Employee employee) {
        employee.setSalary(employee.getSalary() * 1.05); // Increase salary by 5%
        employee.setState(new ExemplaryState()); // Move to exemplary state after promotion
    }
}

// Concrete State - Exemplary
class ExemplaryState extends EmployeeState {
    @Override
    public void promote(Employee employee) {
        employee.setSalary(employee.getSalary() * 1.1); // Increase salary by 10%
    }
}

// Context class for State pattern
class Employee {
    private String name;
    private double salary;
    private EmployeeState currentState;
    private List<EmployeeObserver> observers = new ArrayList<>();

    public Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
        this.currentState = new SatisfactoryState(); // Initial state
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void addObserver(EmployeeObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(EmployeeObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (EmployeeObserver observer : observers) {
            observer.update(this);
        }
    }

    // Simulate a promotion action
    public void promote() {
        currentState.promote(this);
        notifyObservers();
    }

    public void setState(EmployeeState state) {
        this.currentState = state;
    }
}

// Concrete Observer
class EmployeeObserverImpl implements EmployeeObserver {
    @Override
    public void update(Employee employee) {
        System.out.println("Employee " + employee.getName() +
                " has been promoted. New salary: $" + employee.getSalary());
    }
}

// Concrete Command
class PromotionCommand implements Command {
    private Employee employee;

    public PromotionCommand(Employee employee) {
        this.employee = employee;
    }

    @Override
    public void execute() {
        employee.promote();
    }
}

// Command Processor
class EmployeeCommandProcessor {
    public void processCommand(Command command) {
        command.execute();
    }
}

// Main Application
class EmployeeManagementApp {
    public static void main(String[] args) {
        // Create an employee
        Employee employee = new Employee("John Doe", 50000.0);

        // Create observer
        EmployeeObserver observer = new EmployeeObserverImpl();

        // Add observer to the employee
        employee.addObserver(observer);

        // Create a promotion command
        Command promotionCommand = new PromotionCommand(employee);

        // Process the promotion command
        EmployeeCommandProcessor commandProcessor = new EmployeeCommandProcessor();
        commandProcessor.processCommand(promotionCommand);
    }
}

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 * Aaron Van Curen
 * CS160 Fall 2022
 * Project 1: CAFE Compliance Analyzer
 *
 * Description. <Summarize the purpose of the program here.>
 *
 */

public class CafeComplianceAnalyzer
{
    public static final double MAX_FUEL_ECONOMY = 30.42;
    public static final double MIN_FUEL_ECONOMY = 21.79;
    public static final double MIDWAY_FOOTPRINT = 47.74;
    public static final double RATE = 4.65;

    public static void main(String[] args)
    {
        Scanner inputReader = new Scanner(System.in);
        System.out.println("Welcome to the 2016 CAFE Compliance Analyzer for CS Motors");

        System.out.print("Enter vehicle footprint: ");
        double vehicleFootprint = inputReader.nextDouble();
        double targetFuelEconomy = TargetFuelEconomy(vehicleFootprint);

        List<Vehicle> vehicleList = new ArrayList<>();
        for (int i = 1; i <= 3; i++)
        {
            System.out.print("Enter model name, production amount and mpg rating for model " + i + ": ");
            String model = inputReader.next();
            int production = inputReader.nextInt();
            double mpg = inputReader.nextDouble();
            vehicleList.add(new Vehicle(model, mpg, production));
        }

        double fleetFuelEconomy = FleetFuelEconomy(vehicleList);
        GenerateReport(vehicleList, vehicleFootprint, targetFuelEconomy, fleetFuelEconomy);
    }

    public static double TargetFuelEconomy(double vehicleFootprint)
    {
        double calculatedVehicleFootprint = Math.exp(((vehicleFootprint - MIDWAY_FOOTPRINT) / RATE));
        return 1 / (((1 / MAX_FUEL_ECONOMY) + ((1 / MIN_FUEL_ECONOMY) - (1 / MAX_FUEL_ECONOMY)) * calculatedVehicleFootprint) / (1 + calculatedVehicleFootprint));
    }

    public static double FleetFuelEconomy(List<Vehicle> vehicleList)
    {
        double totalProduction = 0;
        double sumOfVehicleFuelEconomy = 0;
        for (Vehicle vehicle : vehicleList)
        {
            totalProduction += vehicle.GetProduction();
            sumOfVehicleFuelEconomy += vehicle.GetProduction() / vehicle.GetMpg();
        }
        return totalProduction / sumOfVehicleFuelEconomy;
    }

    public static void GenerateReport(List<Vehicle> vehicleList, double vehicleFootprint, double targetFuelEconomy,
                                      double fleetFuelEconomy)
    {
        String reportFormat = "%1$-59s%n";
        System.out.printf(reportFormat, "Welcome to the 2016 CAFE Compliance Analyzer for CS Motors.");
        System.out.printf(reportFormat, "-----------------------------------------------------------");
        System.out.printf(reportFormat, "Vehicle footprint: " + vehicleFootprint + " sq. ft.");
        System.out.printf(reportFormat, "Target fuel economy: " + targetFuelEconomy + " mpg.");

        String tableFormat = "%1$5s %2$10s %3$3s%n";
        System.out.printf(tableFormat, "\nModel", "Production", "MPG");
        System.out.printf(tableFormat, "-----", "----------", "---");
        for (Vehicle vehicle : vehicleList)
        {
            System.out.printf("%1$5s %2$10d %3$3.2f%n", vehicle.GetModel(), vehicle.GetProduction(), vehicle.GetMpg());
        }

        System.out.printf(reportFormat, "\nFleet fuel economy: " + fleetFuelEconomy + " mpg.");
    }

    public static class Vehicle
    {
        private String _model;

        public String GetModel()
        {
            return _model;
        }

        public void SetModel(String model)
        {
            this._model = model;
        }

        private double _mpg;

        public double GetMpg()
        {
            return _mpg;
        }

        public void SetMpg(double mpg)
        {
            this._mpg = mpg;
        }

        private int _production;

        public int GetProduction()
        {
            return _production;
        }

        public void SetProduction(int production)
        {
            this._production = production;
        }

        public Vehicle(String model, double mpg, int production)
        {
            SetModel(model);
            SetMpg(mpg);
            SetProduction(production);
        }
    }
}

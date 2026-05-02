import java.util.*;
import java.util.function.Function;
import java.util.stream.IntStream;

public class PlotterView{
    double minX, maxX, minY, maxY;
    Plotter pl;
    PlotterHistory history; //Historik
    List<String> functions = new ArrayList<>();
    String currentFunction;
    
    PlotterView(double minX,double maxX, double minY, double maxY){
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
        pl = new Plotter(minX, maxX, minY, maxY);

        this.history = new PlotterHistory();
            
    }
        //
    PlotterView(){
        this(-10, 10, -10, 10);//default constructor with standardranges
        
    }

    PlotterView(LiveView view, double minX,double maxX, double minY, double maxY){
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;

        pl = new Plotter(view, minX, maxX, minY, maxY);

        this.history = new PlotterHistory();
    }

    PlotterView(LiveView view){
        this(view, -10, 10, -10, 10);// Constructor with LiveView and default ranges
        pl = new Plotter(minX, maxX, minY, maxY);
    }

    void setFunction(String function){
        this.currentFunction = function;
    }

    void plotFunction(double minX, double maxX, double minY, double maxy) {
        if(minX >= maxX){
            System.out.println("Invalid range(minX must be less than maxX).");
            return;
        }

        if(minY >= maxY){
            System.out.println("Invalid range(minY must be less than maxY).");
            return;
        }

        //check if a function has actuallx been entered
        if(currentFunction == null || currentFunction.isEmpty()){
            throw new IllegalArgumentException("Go and set a a function MY FRIENNDD");
        }

        plotFunction();
        functions.add(this.currentFunction);
    }
        


    
    
    void plotFunction(){
        int steps = 100; //No of points in domain
        IntStream.rangeClosed(0, steps).forEach(i -> {
        double x = minX + (maxX - minX) * i / steps;
        double y = evaluateFunction(currentFunction, x);
    
            //Only show points of the y axis, i.e in the range
            if(y >= minY && y <= maxY){
                System.out.printf("Point: (x = %.2f, y = %.2f)%n", x, y);
                //Historik aktualisieren
                history.addEntry(currentFunction, x, y);
            }
                
            if(x == minX){
                pl.moveTo(x, y);
            }else{
                pl.lineTo(x, y);
            }
        });

        functions.add(this.currentFunction);
        //Historie danach anzeigen
        //history.printHistory();
    }


    //Verschiebung
    void pan(double dx, double dy){
        this.minX += dx;
        this.maxX += dx;
        this.minY += dy;
        this.maxY += dy;

        //Neuzeichnen
        pl.clearCanvas();
        pl.axes(this.minX, this.maxX, this.minY, this.maxY);
        
        for(String function : functions){
            plotFunction();
        }
        
    }

    //Zoomen
    void zoom(double factor) {
        //Bereich vergrößern oder verkleinern
        double centerX = (this.minX + this.maxX) / 2;
        double centerY = (this.minY + this.maxY) / 2;


        //Neue x und y Bereiche berechenen
        double newXRange = (this.maxX - this.minX) / factor;
        double newYRange = (this.maxY - this.minY) / factor;

        //Setze neuer Bereich ums Zentrum
        this.minX = centerX - newXRange / 2;
        this.maxX = centerX + newXRange / 2;
        this.minY = centerY - newYRange / 2;
        this.maxY = centerY + newYRange / 2;

        
        pl.clearCanvas();
        pl.axes(this.minX, this.maxX, this.minY, this.maxY);
        for(String function : functions){
            plotFunction();
        }
    }
    

    Map<String, Function<Double, Double>> mathFunctions = new HashMap<>();
        {
            //trigo functions
            mathFunctions.put("sin", Math::sin);
            mathFunctions.put("cos", Math::cos);
            mathFunctions.put("tan", Math::tan);
            
    
    
            //log functions
            mathFunctions.put("log", Math::log);
            mathFunctions.put("log10", Math::log10);
            mathFunctions.put("exp", Math::exp);
    
        
            //square root
            mathFunctions.put("sqrt", Math::sqrt);
    
            mathFunctions.put("abs", Math::abs);
    
            
        }
        double evaluateFunction(String function, double x){
        try {
                //if(function.contains(function))
                String[] parts = function.split("\\+");
    
                double result = 0;
                for(String part: parts){
                    part = part.trim();
    
    
                    for(Map.Entry<String, Function<Double, Double>> entry : mathFunctions.entrySet()){
                    if(part.startsWith(entry.getKey())){
                        String argument = part.substring(part.indexOf('(') + 1, part.indexOf(')'));

                        double value = Double.parseDouble(argument.replace("x", Double.toString(x)));

                        result = result + entry.getValue().apply(value);
                        break;
                    }
                }

                if(part.matches("x\\*\\d+(\\.\\d+)?"))
                {
                    String[] mulParts = part.split("\\*");
                    result = result + Double.parseDouble(mulParts[1]) * x;
                }

                else if(part.equals("x")){
                    result += x;
                
                }

                else if(part.matches("\\d+(\\.\\d+)?")){
                    result += Double.parseDouble(part);
                }

                else if (part.matches("(x-\\d+(\\.\\d+)?|\\d+(\\.\\d+)?-x)")) {
                    String[] subParts = part.split("-");
                    if (part.startsWith("x")) {
                        result += x - Double.parseDouble(subParts[1]);
                    } else {
                        result += Double.parseDouble(subParts[0]) - x;
                    }
                }

                else if (part.matches("(x/\\d+(\\.\\d+)?|\\d+(\\.\\d+)?/x)")) {
                    String[] divParts = part.split("/");
                    if (part.startsWith("x")) {
                        if (Double.parseDouble(divParts[1]) != 0) {
                            result += x / Double.parseDouble(divParts[1]);
                        }
                    } else {
                        if (x != 0) {
                            result += Double.parseDouble(divParts[0]) / x;
                        }
                    }
                }
            }
            
            return result;
        }catch(Exception e){
            System.err.println("Error occurred during function's calculation: " + e.getMessage());

            return Double.NaN;
        }
        

        
    }
    
        
        
}

    




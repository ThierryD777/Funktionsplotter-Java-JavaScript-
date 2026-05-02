import java.util.ArrayList;
import java.util.List;


public class PlotterHistory {
    //Record zur Speicherung
    public record HistoryEntry(String function, double input, double result){

    }

    //Liste zur Speicherung der Historie
    final List<HistoryEntry> history;

    
    PlotterHistory() {
        this.history = new ArrayList<>();
    }
    

    

    //Neuer Eintrag hinzufügen
    void addEntry(String function, double input, double result){
        history.add(new HistoryEntry(function, input, result));
    }

    //Gesamte Historie anzeigen
    void printHistory(){
        if(history.isEmpty()) {
            System.out.println("Noch keine ausgeführte Funktion!");
            return;
        }

        System.out.println("Historie der ausgeführten Funktion(en): ");
        for(HistoryEntry entry : history) {
            System.out.printf("Funktion: %s, Input: %.2f, Ergebnis: %.2f%n", entry.function(), entry.input(), entry.result());
        }
    }

    //Historie leeren
    void clearHistory(){
        history.clear();
        System.out.println("Historie entleert!");
    }

    //Zugriff auf Historie#
    List<HistoryEntry> getHistory(){
        return new ArrayList<>(history);
    }
}
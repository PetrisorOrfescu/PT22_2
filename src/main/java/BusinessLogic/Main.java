package BusinessLogic;

import View.SimulationView;

public class Main {
    public synchronized static void main(String[] args) {
        SimulationView view = new SimulationView();
        view.setVisible(true);
    }
}

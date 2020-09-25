import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
    private View view;

    public Controller(View view) {
        this.view = view;
        setViewListener();
    }

    private void setViewListener() {
        view.getAlgo1RdBtn().addActionListener(selectAlgorithmListener);
        view.getAlgo2RdBtn().addActionListener(selectAlgorithmListener);
        view.getAlgo3RdBtn().addActionListener(selectAlgorithmListener);

        view.getResetButton().addActionListener(resetButtonListener);
    }

    ActionListener selectAlgorithmListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String algo = view.getBtnGroup().getSelection().getActionCommand();
            switch(algo) {
                case "RR":
                    System.out.println("1");
                    view.getPriorityTQPanel().setText("Time Quantum :");
                    break;
                case "NPSJF":
                    System.out.println("2");
                    view.getPriorityTQPanel().setText("Priority :");
                    break;
                case "PR":
                    System.out.println("3");
                    view.getPriorityTQPanel().setText("Priority :");
                    break;
            }
        }
    };

    ActionListener resetButtonListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.getArrivalTxtField().setText("");
            view.getBurstTxtField().setText("");
            view.getPriorityTQTxtField().setText("");

            // need to clear panel too

            view.getAvgTATTxtField().setText("");
            view.getTotalTATTxtField().setText("");
            view.getAvgWTTxtField().setText("");
            view.getTotalWTTxtField().setText("");
        }
    };
}
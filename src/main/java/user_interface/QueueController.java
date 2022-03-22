package user_interface;

import data_models.QueueModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QueueController {

    private QueueModel m_model;
    private QueueView m_view;

    public QueueController(QueueView view, QueueModel model){
        m_view = view;
        m_model = model;

        m_view.addListener(new startListener());
    }

    class startListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            int noClients;
            int noQueues;
            int simulationInterval;
            int [] arrInterval;
            int [] serInterval;
            try{
                noClients = m_view.getNoClients();
                noQueues = m_view.getNoQueues();
                simulationInterval = m_view.getSimulationInterval();
                arrInterval = m_view.getArrivalInterval();
                serInterval = m_view.getServiceInterval();

                m_model.setInformation(simulationInterval, arrInterval, serInterval, noQueues, noClients);
            }
            catch(NumberFormatException nfex){
                m_view.showError("Enter valid data!");
            }
        }
    }
}

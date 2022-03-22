package user_interface;

import data_models.QueueModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class QueueView extends JFrame{

    private JLabel m_title = new JLabel("Queues Simulator");
    private JLabel m_clients = new JLabel("NO. of Clients: ");
    private JLabel m_que = new JLabel("NO. of Queues: ");
    private JLabel m_sim = new JLabel("Simulation Interval: ");
    private JLabel m_arrTime = new JLabel("  Arrival Interval: ");
    private JLabel m_serTime = new JLabel("Service Interval:");

    private JTextField m_noClients = new JTextField(10);
    private JTextField m_noQueues = new JTextField(10);
    private JTextField m_simTime = new JTextField(13);
    private JTextField m_minArrival = new JTextField(10);
    private JTextField m_maxArrival = new JTextField(10);
    private JTextField m_minService = new JTextField(10);
    private JTextField m_maxService = new JTextField(10);

    private JButton m_button = new JButton("Start");

    private QueueModel m_model;

    private JPanel content = new JPanel();
    private JPanel content1 = new JPanel();
    private JPanel content2 = new JPanel();
    private JPanel content3 = new JPanel();
    private JPanel content4 = new JPanel();


    public QueueView(QueueModel model){
        this.m_model = model;

        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        content1.setLayout(new FlowLayout(FlowLayout.CENTER));
        content1.add(m_title);

        ///////////////////////////////////////////
        JPanel firstPL = new JPanel();
        firstPL.setLayout(new FlowLayout(FlowLayout.CENTER));
        JPanel secondPL = new JPanel();
        secondPL.setLayout(new FlowLayout(FlowLayout.CENTER));

        firstPL.add(m_clients);
        secondPL.add(m_que);

        JPanel firstPT = new JPanel();
        firstPT.setLayout(new FlowLayout(FlowLayout.CENTER));
        JPanel secondPT = new JPanel();
        secondPT.setLayout(new FlowLayout(FlowLayout.CENTER));

        firstPT.add(m_noClients);
        secondPT.add(m_noQueues);

        JPanel firstP = new JPanel();
        JPanel secondP = new JPanel();

        firstP.add(firstPL);
        firstP.add(firstPT);

        secondP.add(secondPL);
        secondP.add(secondPT);

        content2.add(firstP);
        content2.add(secondP);

        /////////////////////////////
        content3.setLayout(new BoxLayout(content3, BoxLayout.Y_AXIS));

        JPanel simPanel1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel simPanel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        simPanel1.add(m_sim);
        simPanel2.add(m_simTime);

        content3.add(simPanel1);
        content3.add(simPanel2);

        /////////////////////////////////////////
        JPanel intervalPanelLeft = new JPanel();
        intervalPanelLeft.setLayout(new BoxLayout(intervalPanelLeft, BoxLayout.Y_AXIS));
        JPanel intervalPanelRight = new JPanel();
        intervalPanelRight.setLayout(new BoxLayout(intervalPanelRight, BoxLayout.Y_AXIS));

        m_arrTime.setLayout(new FlowLayout(FlowLayout.CENTER));
        m_serTime.setLayout(new FlowLayout(FlowLayout.CENTER));

        m_minArrival.setLayout(new FlowLayout(FlowLayout.CENTER));
        m_maxArrival.setLayout(new FlowLayout(FlowLayout.CENTER));
        m_minService.setLayout(new FlowLayout(FlowLayout.CENTER));
        m_maxService.setLayout(new FlowLayout(FlowLayout.CENTER));

        JPanel top = new JPanel();
        JPanel bottom = new JPanel();

        top.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottom.setLayout(new FlowLayout(FlowLayout.CENTER));
        top.add(m_minArrival);
        top.add(m_maxArrival);
        bottom.add(m_minService);
        bottom.add(m_maxService);
        intervalPanelRight.add(top);
        intervalPanelRight.add(bottom);

        intervalPanelLeft.add(m_arrTime);
        intervalPanelLeft.add(m_serTime);

        content4.add(intervalPanelLeft);
        content4.add(intervalPanelRight);

        ///////////////////////////////////
        content.add(content1);
        content.add(content2);
        content.add(content3);
        content.add(content4);
        m_button.setAlignmentX(Component.CENTER_ALIGNMENT);
        content.add(m_button);

        this.setContentPane(content);
        this.pack();

        this.setTitle("Queues Simulator");
        this.setSize(500, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void addListener(ActionListener start){
        this.m_button.addActionListener(start);
    }

    public int getNoClients(){
        return Integer.parseInt(this.m_noClients.getText());
    }
    public int getNoQueues(){
        return Integer.parseInt(this.m_noQueues.getText());
    }
    public int getSimulationInterval(){
        return Integer.parseInt(this.m_simTime.getText());
    }
    public int[] getArrivalInterval(){
        int[]arrival = new int[2];
        arrival[0] = Integer.parseInt(this.m_minArrival.getText());
        arrival[1] = Integer.parseInt(this.m_maxArrival.getText());
        return arrival;
    }
    public int[] getServiceInterval(){
        int[]service = new int[2];
        service[0] = Integer.parseInt(this.m_minService.getText());
        service[1] = Integer.parseInt(this.m_maxService.getText());
        return service;
    }
    public void showError(String errMessage) {
        JOptionPane.showMessageDialog(this, errMessage);
    }

}

package main;

import data_models.QueueModel;
import user_interface.QueueController;
import user_interface.QueueView;

public class QueueMCV {
    public static void main (String[] args){
        QueueModel model = new QueueModel();
        QueueView view = new QueueView(model);
        QueueController controller = new QueueController(view, model);
        view.setVisible(true);
    }
}

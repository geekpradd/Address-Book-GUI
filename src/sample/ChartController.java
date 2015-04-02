package sample;

import java.text.DateFormatSymbols;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;

/**
 * Created by Pradipta on 02-04-2015.
 */
public class ChartController {
    @FXML
    private BarChart<String, Integer> chart;
    @FXML
    private CategoryAxis xAxis;

    private ObservableList<String> months = FXCollections.observableArrayList();

    @FXML
    private void initialize(){
        String[] temp = DateFormatSymbols.getInstance(Locale.ENGLISH).getMonths();
        months.addAll(Arrays.asList(temp));

        xAxis.setCategories(months);
    }
    public void setChartData(List<Model> persons){
        int[] hits = new int[12];
        for (Model p: persons){
            int month = p.getBirthday().getMonthValue() - 1;
            hits[month]++;
        }
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        for (int i=0;i<12;++i){
            series.getData().add(new XYChart.Data<>(months.get(i),hits[i]));
        }
        chart.getData().add(series);
    }
}

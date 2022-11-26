package server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QueryStringHandler {
    String operation;
    List<Float> qStringValuesArray = new ArrayList<>();
    String queryString;

    public QueryStringHandler(String qString){
        this.queryString = qString;
    }

    public boolean isValid(){
        boolean valid = true;
        try {
            List<String> qStringArray = new ArrayList<>(Arrays.asList(queryString.split("&")));
            operation = qStringArray.get(0).split("=")[1];
            qStringArray.remove(0);

            for (String var: qStringArray){
                qStringValuesArray.add(Float.parseFloat(var.split("=")[1]));
            }
        }catch (Exception e){
            valid = false;
        }

        if (!Arrays.asList(new String[]{"soma", "subtracao", "divisao", "multiplicacao"}).contains(operation)){
            valid = false;
        }

        if (qStringValuesArray.size() < 2){
            valid = false;
        }

        return valid;
    }

    public Float getResult(){
        float result = 0;

        switch (operation){
            case "soma":
                result = (float) qStringValuesArray.stream().mapToDouble(i -> i).sum();
                break;
            case "subtracao":
                for (Float number : qStringValuesArray){
                    result -= number;
                }
                break;
            case "divisao":
                result = (float) qStringValuesArray.get(0) / qStringValuesArray.get(1);
                qStringValuesArray.remove(0);
                qStringValuesArray.remove(0);
                for (Float number : qStringValuesArray){
                    result /= number;
                }
                break;
            case "multiplicacao":
                result = qStringValuesArray.get(0) * qStringValuesArray.get(1);
                qStringValuesArray.remove(0);
                qStringValuesArray.remove(0);
                for (Float number : qStringValuesArray){
                    result *= number;
                }
        }

        return result;
    }
}
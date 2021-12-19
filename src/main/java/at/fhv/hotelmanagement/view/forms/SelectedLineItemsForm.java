package at.fhv.hotelmanagement.view.forms;

import java.util.HashMap;
import java.util.Map;

public class SelectedLineItemsForm {
    private Map<String, Integer> selectedLineItemsCount;

    SelectedLineItemsForm() { this.selectedLineItemsCount = new HashMap<>(); }

    public Map<String, Integer> getSelectedLineItemsCount() {
        return this.selectedLineItemsCount;
    }

    public void setSelectedLineItemsCount(Map<String, Integer> selectedLineItemsCount) {
        this.selectedLineItemsCount = selectedLineItemsCount;
    }
}

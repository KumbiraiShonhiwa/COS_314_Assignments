import java.util.ArrayList;

public class Knapsack {
    public int totalWeight;
    public int totalValue;
    private int weightLimit;
    public int capacity;
    public int numItems;
    public int[] genes = new int[10];
    private int index = 0;
    private ArrayList<Item> items; // List of items in the knapsack

    public Knapsack(int weightLimit) {
        this.weightLimit = weightLimit;
        this.totalWeight = 0;
        this.items = new ArrayList<Item>();
        numItems = 0;
    }

    public Knapsack(int weightLimit, ArrayList<Item> items) {
        this.weightLimit = weightLimit;
        this.items = items;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i) != null) {
                totalWeight += items.get(i).getWeight();
                totalValue += items.get(i).getValue();
            }
        }
    }

    public boolean hasSameItem(Item item) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getWeight() == item.getWeight() && items.get(i).getValue() == item.getValue()) {
                return true;
            }
        }
        return false;
    }

    public boolean sameItems(Knapsack knapsack) {
        if (knapsack.items.size() != items.size()) {
            return false;
        }
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getWeight() != knapsack.items.get(i).getWeight()
                    || items.get(i).getValue() != knapsack.items.get(i).getValue()) {
                return false;
            }
        }
        return true;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public boolean contains(Item item) {
        for (Item i : items) {
            if (i.equals(item)) {
                return true;
            }
        }
        return false;
    }

    public Knapsack(Knapsack other) {
        this.weightLimit = other.weightLimit;
        this.totalWeight = other.totalWeight;
        this.items = new ArrayList<Item>();
        for (Item item : other.items) {
            this.items.add(new Item(item));
        }
        this.numItems = other.numItems;
    }

    public int getWeightLimit() {
        return weightLimit;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public void addItem(Item item) {
        if (item != null) {
            if (totalWeight + item.getWeight() <= weightLimit) {
                items.add(item);
                totalWeight += item.getWeight();
                totalValue += item.getValue();
                index++;
            }
        }
    }

    public void removeItem(Item item) {
        if (item != null) {
            if (contains(item)) {
                items.remove(item);
                totalWeight -= item.getWeight();
                capacity = totalWeight;
                numItems--;
            }
        }
    }

    public Item getItemAtIndex(int index) {
        if (index >= 0 && index < items.size()) {
            return items.get(index);
        }
        return null; // Return null if the index is out of bounds
    }

    public void setItemAtIndex(int index, Item item) {
        if (index >= 0 && index < items.size()) {
            items.set(index, item);
        }
    }

    public void removeItemAtIndex(int index) {
        if (index >= 0 && index < items.size()) {
            items.remove(index);
        }
    }

    public boolean isFull(int totalWeight) {
        if (weightLimit < totalWeight) {
            return false;
        }
        return true;
    }

    public void print() {
        for (int i = 0; i < items.size(); i++) {
            if (getItemAtIndex(i) != null)
                System.out.println("ITEM: " + i + " Weight: " + getItemAtIndex(i).getWeight() + " Value: "
                        + getItemAtIndex(i).getValue());
        }
    }

    public double calculateFitness() {
        return (double) totalValue / (double) totalWeight;
    }

    public void orderByFitness() {
        for (int i = 0; i < items.size(); i++) {
            for (int j = 0; j < items.size(); j++) {
                if (items.get(i).calulateFitness() > items.get(j).calulateFitness()) {
                    Item temp = items.get(i);
                    items.set(i, items.get(j));
                    items.set(j, temp);
                }
            }
        }
    }

}
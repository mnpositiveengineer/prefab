package com.prefab.sales.production;
import com.prefab.sales.production.interfaces.CalculateElement;
import com.prefab.sales.utils.CollectionModification;
import com.prefab.sales.utils.Validation;
import com.prefab.sales.utils.enums.ConsoleOrCutoutType;
import com.prefab.sales.utils.exceptions.ObjectAlreadyAddedException;
import com.prefab.sales.utils.exceptions.ObjectNotInCollectionException;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public class ConsoleElement extends StandardElement {

    private Set<ConsoleCreator> consolesAndCutouts = new HashSet<>();

    public ConsoleElement(String name, int amount, String typeOfElement) {
        super(name, (short)amount, typeOfElement);
    }

    public ConsoleCreator createConsole(ConsoleOrCutoutType type, int amount, float height, float width, float length) {
        return new ConsoleCreator(type, amount, height, width, length);
    }

    public void addConsoleOrCutout(ConsoleCreator...selectedConsoleCreator) {
        for (ConsoleCreator consoleCreator : selectedConsoleCreator) {
            try {
                CollectionModification.addToCollection(consolesAndCutouts, consoleCreator);
            } catch (ObjectAlreadyAddedException e) {
                System.out.println("Console/Cutout already added.");
            }
        }
    }

    public void removeConsoleOrCutout(ConsoleCreator...selectedConsoleCreator) {
        for (ConsoleCreator consoleCreator : selectedConsoleCreator) {
            try {
                CollectionModification.removeFromCollection(consolesAndCutouts, consoleCreator);
            } catch (ObjectNotInCollectionException e) {
                System.out.println("Console/Cutout does not exist.");
            }
        }
    }

    public Set<ConsoleCreator> getConsolesAndCutouts() {
        return consolesAndCutouts;
    }

    @Override
    public CalculateElement calculate() {
        return new ConsoleCalculator(this);
    }

    public class ConsoleCalculator extends Calculator {

        private ConsoleElement element;

        public ConsoleCalculator(ConsoleElement element) {
            super(element);
            this.element = element;
        }

        @Override
        public float calculateVolume() {
            return super.calculateVolume() + calculateVolumeOfConsolesAndCutouts();
        }

        @Override
        public float calculateFrameworkArea() {
            return super.calculateFrameworkArea() + calculateFrameworkAreaOfConsolesAndCutouts();
        }

        private float calculateVolumeOfConsolesAndCutouts() {
            Predicate<ConsoleCreator> console = m -> m.getType().equals(ConsoleOrCutoutType.console);
            Predicate<ConsoleCreator> cutout = m -> m.getType().equals(ConsoleOrCutoutType.cutout);
            return (float)consolesAndCutouts.stream().filter(console)
                    .mapToDouble(ConsoleCreator::calculateVolume)
                    .reduce(0, Double::sum)
                    - (float)consolesAndCutouts.stream().filter(cutout)
                    .mapToDouble(ConsoleCreator::calculateVolume)
                    .reduce(0, Double::sum);
        }

        private float calculateFrameworkAreaOfConsolesAndCutouts() {
            return (float)consolesAndCutouts
                    .stream()
                    .mapToDouble(ConsoleCreator::calculateFrameworkArea)
                    .reduce(0, Double::sum);
        }
    }

    public class ConsoleCreator {

        private ConsoleOrCutoutType type;
        private short amount;
        private float height;
        private float width;
        private float length;

    public ConsoleCreator(ConsoleOrCutoutType type, int amount, float height, float width, float length) {
        this.type = type;
        setAmount((short)amount);
        setHeight(height);
        setWidth(width);
        setLength(length);
    }

    public float calculateVolume() {
        return amount * height * width * length;
    }

    public float calculateFrameworkArea() {
        return amount * length * 2 * (height + width);
    }

    private void setAmount(short amount) {
        Validation.greaterOrEqualZero(amount);
        this.amount = amount;
    }

    private void setHeight(float height) {
        Validation.greaterOrEqualZero(height);
        this.height = height;
    }

    private void setWidth(float width) {
        Validation.greaterOrEqualZero(width);
        this.width = width;
    }

    private void setLength(float length) {
        Validation.greaterOrEqualZero(length);
        this.length = length;
    }

    public ConsoleOrCutoutType getType() {
        return type;
    }

        public short getAmount() {
            return amount;
        }
    }

}

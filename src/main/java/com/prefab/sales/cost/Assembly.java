package com.prefab.sales.cost;

import com.prefab.sales.client.Project;
import com.prefab.sales.cost.interfaces.CalculateAssemblyCost;
import com.prefab.sales.utils.exceptions.IncorrectDateException;

import java.time.LocalDate;

public class Assembly extends CostGroupsCreator {

    private short assemblyCost;
    private LocalDate assemblyStart;
    private LocalDate assemblyEnd;

    public Assembly(Project project, int assemblyCost) {
        super(project, project.getAssemblyGroups());
        this.assemblyCost = (short)assemblyCost;
    }

    public void setAssemblyCost(int assemblyCost) {
        this.assemblyCost = (short)assemblyCost;
    }

    public void setAssemblyStart(LocalDate assemblyStart) throws IncorrectDateException {
        if (assemblyEnd == null
                || this.assemblyEnd.isAfter(assemblyStart)
                || this.assemblyEnd.isEqual(assemblyStart)) {
            this.assemblyStart = assemblyStart;
            return;
        }
        throw new IncorrectDateException("Assembly start cannot be after Assembly end.");
    }

    public void setAssemblyEnd(LocalDate assemblyEnd) throws IncorrectDateException {
        if (assemblyStart == null
                || this.assemblyStart.isBefore(assemblyEnd)
                || this.assemblyStart.isEqual(assemblyEnd)) {
            this.assemblyEnd = assemblyEnd;
            return;
        }
        throw new IncorrectDateException("Assembly end cannot be before Assembly start.");
    }


    @Override
    public CalculateAssemblyCost calculate() {
        return new AssemblyCalculator(this);
    }

    public class AssemblyCalculator implements CalculateAssemblyCost {

        private Assembly assembly;

        public AssemblyCalculator(Assembly assembly) {
            this.assembly = assembly;
        }

        public int calculateCostOfAssemblyInGroup() {
            if (this.assembly.getElementsInGroup().isEmpty())
                return 0;
            return assembly.getElementsInGroup().stream().mapToInt(m->m.getAmount()).sum() * assemblyCost;
        }
    }
}

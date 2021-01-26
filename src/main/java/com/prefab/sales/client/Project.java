package com.prefab.sales.client;

import com.prefab.sales.cost.ProjectCostCalculator;
import com.prefab.sales.cost.interfaces.CreateCostGroups;
import com.prefab.sales.production.interfaces.CreateElement;
import com.prefab.sales.utils.*;
import com.prefab.sales.utils.enums.*;
import com.prefab.sales.utils.exceptions.ObjectAlreadyAddedException;
import com.prefab.sales.utils.exceptions.ObjectNotInCollectionException;

import java.util.*;

public class Project {

    private String name;
    private Prospect prospect;
    private Address address;
    private ConstructionType constructionType;
    private ProjectType projectType;
    private ClientType clientType;
    private boolean withDesign;
    private Status status;
    private Set<CreateElement> elements = new HashSet<>();
    private Map<CreateCostGroups, Set<CreateElement>> transportGroups = new HashMap<>();
    private Map<CreateCostGroups, Set<CreateElement>> productionGroups = new HashMap<>();
    private Map<CreateCostGroups, Set<CreateElement>> assemblyGroups = new HashMap<>();

    public Project(String name, Prospect prospect) {
        setName(name);
        setProspect(prospect);
    }

    public void addElement(CreateElement...selectedElementCreator) {
        for (CreateElement elementCreator : selectedElementCreator) {
            try {
                CollectionModification.addToCollection(elements, elementCreator);
            } catch (ObjectAlreadyAddedException e) {
                System.out.println("Element " + elementCreator.getName() + " already exists.");
            }
        }
    }

    public void removeElement(CreateElement...selectedElementCreator) {
        for (CreateElement elementCreator : selectedElementCreator) {
            try {
                CollectionModification.removeFromCollection(elements, elementCreator);
            } catch (ObjectNotInCollectionException e) {
                System.out.println("Element " + elementCreator.getName() + " is not on the list.");
            }
        }
    }

    public void setName(String name) {
        Validation.alphanumericValue(name);
        this.name = name.trim();
    }

    public void setProspect(Prospect prospect) {
        this.prospect = prospect;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setConstructionType(ConstructionType constructionType) {
        this.constructionType = constructionType;
    }

    public void setProjectType(ProjectType projectType) {
        this.projectType = projectType;
    }

    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
    }

    public void setWithDesign(boolean withDesign) {
        this.withDesign = withDesign;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Set<CreateElement> getElements() {
        return elements;
    }

    public Map<CreateCostGroups, Set<CreateElement>> getTransportGroups() {
        return transportGroups;
    }

    public Map<CreateCostGroups, Set<CreateElement>> getProductionGroups() {
        return productionGroups;
    }

    public Map<CreateCostGroups, Set<CreateElement>> getAssemblyGroups() {
        return assemblyGroups;
    }

    public ProjectCostCalculator calculate() {
        return new ProjectCostCalculator(this);
    }

    public String getName() {
        return name;
    }

    public Prospect getProspect() {
        return prospect;
    }

    public Address getAddress() {
        return address;
    }

    public ConstructionType getConstructionType() {
        return constructionType;
    }

    public ProjectType getProjectType() {
        return projectType;
    }

    public ClientType getClientType() {
        return clientType;
    }

    public boolean isWithDesign() {
        return withDesign;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return name.equals(project.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

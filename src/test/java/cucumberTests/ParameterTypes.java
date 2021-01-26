package cucumberTests;

import com.prefab.sales.production.ConsoleElement;
import com.prefab.sales.utils.enums.*;
import io.cucumber.java.ParameterType;

public class ParameterTypes {

    @ParameterType("Warehouse|Infrastructure|House|Factory|Bridge|Stadium|Tunnel")
    public ConstructionType constructionType(String type) {
        if (type.equals("Warehouse"))
            return ConstructionType.warehouse;
        else if (type.equals("Infrastructure"))
            return ConstructionType.infrastructure;
        else if (type.equals("House"))
            return ConstructionType.house;
        else if (type.equals("Factory"))
            return ConstructionType.factory;
        else if (type.equals("Bridge"))
            return ConstructionType.bridge;
        else if (type.equals("Stadium"))
            return ConstructionType.stadium;
        else if (type.equals("Tunnel"))
            return ConstructionType.tunnel;
        else
            return null;
    }

    @ParameterType("Assembly|Delivery")
    public ProjectType projectType(String type) {
        if (type.equals("Assembly"))
            return ProjectType.assembly;
        else if (type.equals("Delivery"))
            return ProjectType.delivery;
        else
            return null;
    }

    @ParameterType("Investor|General|Subcontractor")
    public ClientType clientType(String type) {
        if (type.equals("Investor"))
            return ClientType.investor;
        else if (type.equals("General"))
            return ClientType.general;
        else if (type.equals("Subcontractor"))
            return ClientType.subcontractor;
        else
            return null;
    }

    @ParameterType("is included|is not included")
    public Boolean designIncluded(String type) {
        if (type.equals("is included"))
            return true;
        else if (type.equals("is not included"))
            return false;
        else
            return null;
    }

    @ParameterType("Received|In_progress|Offer_sent|Discarded|Approved")
    public Status projectStatus(String status) {
        if (status.equals("Received"))
            return Status.received;
        else if (status.equals("In_progress"))
            return Status.in_progress;
        else if (status.equals("Offer_sent"))
            return Status.in_progress;
        else if (status.equals("Discarded"))
            return Status.discarded;
        else if (status.equals("Approved"))
            return Status.approved;
        else
            return null;
    }

    @ParameterType("m|m2|m3|kg|ts|pcs")
    public Unit unit(String unit) {
        if (unit.equals("m"))
            return Unit.m;
        else if (unit.equals("m2"))
            return Unit.m2;
        else if (unit.equals("m3"))
            return Unit.m3;
        else if (unit.equals("kg"))
            return Unit.kg;
        else if (unit.equals("ts"))
            return Unit.ts;
        else if (unit.equals("pcs"))
            return Unit.pcs;
        else
            return null;
    }

    @ParameterType(("Console|Consoles|Cutout|Cutouts"))
    public ConsoleOrCutoutType consoleOrCutout(String type) {
        if (type.equals("Console") || type.equals("Consoles"))
            return ConsoleOrCutoutType.console;
        else if (type.equals("Cutout") || type.equals("Cutouts"))
            return ConsoleOrCutoutType.cutout;
        else
            return null;
    }



}

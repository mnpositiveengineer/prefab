package cucumberTests;

import com.prefab.sales.client.Address;
import com.prefab.sales.client.PersonOfContact;
import com.prefab.sales.client.Project;
import com.prefab.sales.client.Prospect;
import com.prefab.sales.client.repositories.AddressRepository;
import com.prefab.sales.cost.Assembly;
import com.prefab.sales.cost.Production;
import com.prefab.sales.cost.ProjectCostCalculator;
import com.prefab.sales.cost.Transport;
import com.prefab.sales.production.Accessory;
import com.prefab.sales.production.ConsoleElement;
import com.prefab.sales.production.CustomizedElement;
import com.prefab.sales.production.StandardElement;
import com.prefab.sales.utils.enums.*;
import io.cucumber.java.Transpose;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class StepDefinition {

    private Set<Prospect> prospects = new HashSet<>();
    private Set<PersonOfContact> personOfContacts = new HashSet<>();

    @Autowired
    private AddressRepository addressRepository;

    private Set<Address> addresses = new HashSet<>();
    private Set<Project> projects = new HashSet<>();
    private Set<StandardElement> elements = new HashSet<>();
    private Set<Accessory> accessories = new HashSet<>();
    private Map<String, Production> productionCosts = new HashMap<>();
    private Map<String, Transport> transportCosts = new HashMap<>();
    private Map<String, Assembly> assemblyCosts = new HashMap<>();

    private Prospect getProspect(String prospect) {
        return prospects.stream().filter(m -> m.getName().equals(prospect)).findAny()
                .get();
    }

    private PersonOfContact getPerson(String person) {
        return personOfContacts.stream().filter(m -> m.getFirstName().equals(person)).findAny()
                .get();
    }

    private Address getAddress(String address) {
        return addresses.stream().filter(m -> m.getAddress().equals(address)).findAny()
                .get();
    }

    private Project getProject(String project) {
        return projects.stream().filter(m -> m.getName().equals(project)).findAny()
                .get();
    }

    private StandardElement getElement(String name) {
        return elements.stream().filter(m -> m.getName().equals(name)).findAny()
                .get();
    }

    private Accessory getAccessory(String name) {
        return accessories.stream().filter(m -> m.getName().equals(name)).findAny()
                .get();
    }

    private String getStringFromDataTable(Map<String, String> map, String columnName) {
        return map.get(columnName);
    }

    private Float getFloatFromDataTable(Map<String, String> map, String columnName) {
        return Float.parseFloat(map.get(columnName));
    }

    private Short getShortFromDataTable(Map<String, String> map, String columnName) {
        return Short.parseShort(map.get(columnName));
    }

    private Integer getIntegerFromDataTable(Map<String, String> map, String columnName) {
        return Integer.parseInt(map.get(columnName));
    }


    @Given("Prospect {string} is created")
    public void prospect_is_created(String name) {
        prospects.add(new Prospect(name));
    }

    @Given("Person Of Contact {string} is created")
    public void person_of_contact_is_created(String name) {
        personOfContacts.add(new PersonOfContact(name,
                name,
                name.replace(" ", ".") + "@prefab.com",
                "111"));
    }

    @Given("Address {string} is created")
    public void address_is_created(String address) {
        addresses.add(new Address(address, address, address, address));
    }

    @Given("Project {string} is created for Prospect {string}")
    public void project_is_created(String project, String prospect) {
        projects.add(new Project(project, getProspect(prospect)));
    }

    @Given("Standard Element of name {string} and amount {short} is created")
    public void standard_element_is_created(String name, short amount) {
        elements.add(new StandardElement(name, amount, name));
    }

    @Given("Following {string} Elements are created")
    public void elements_are_created(String type, io.cucumber.datatable.DataTable dataTable) {
        for (Map<String, String> map : dataTable.asMaps()) {
            String name = getStringFromDataTable(map, "name");
            int amount = getIntegerFromDataTable(map, "amount");
            float height = getFloatFromDataTable(map, "height");
            float width = getFloatFromDataTable(map, "width");
            float length = getFloatFromDataTable(map, "length");
            int steel = getIntegerFromDataTable(map, "steel");
            int tension = getIntegerFromDataTable(map, "tension");

            if (type.equals("Standard"))
                elements.add(new StandardElement(name, amount, name));
            else if (type.equals("Console"))
                elements.add(new ConsoleElement(name, amount, name));
            else if (type.equals("Customized"))
                elements.add(new CustomizedElement(name, amount, name));

            getElement(name).setHeight(height);
            getElement(name).setWidth(width);
            getElement(name).setLength(length);
            getElement(name).setSteelSaturation(steel);
            getElement(name).setTensionSaturation(tension);
        }
    }

    @Given("Creating Customized Element(s) of attributes")
    public void customized_elements_are_created(io.cucumber.datatable.DataTable dataTable) {

        for (Map<String, String> map : dataTable.asMaps()) {
            String name = getStringFromDataTable(map, "name");
            int amount = getIntegerFromDataTable(map, "amount");
            float volume = getFloatFromDataTable(map, "volume");
            float area = getFloatFromDataTable(map, "area");
            float weight = getFloatFromDataTable(map, "weight");
            float frameworkArea = getFloatFromDataTable(map, "framework");
            float steelWeight = getFloatFromDataTable(map, "steel");
            float tensionWeight = getFloatFromDataTable(map, "tension");

            CustomizedElement element = new CustomizedElement(name, amount, name);
            element.setVolume(volume);
            element.setArea(area);
            element.setWeight(weight);
            element.setTensionWeight(tensionWeight);
            element.setSteelWeight(steelWeight);
            element.setFrameworkArea(frameworkArea);

            elements.add(element);
        }
    }

    @Given("Console Element of name {string} and amount {short} is created")
    public void console_element_is_created(String name, short amount) {
        elements.add(new ConsoleElement(name, amount, name));
    }

    @Given("Customized Element of name {string} and amount {short} is created")
    public void customized_element_is_created(String name, short amount) {
        elements.add(new CustomizedElement(name, amount, name));
    }

    @Given("PCG(s) of following cost(s) is/are created for Project {string}")
    public void setting_pcg(String project, io.cucumber.datatable.DataTable dataTable) {
        for (Map<String, String> map : dataTable.asMaps()) {
            String pcgName = getStringFromDataTable(map, "name_of_pcg");
            int concreteCost = getIntegerFromDataTable(map, "concrete_cost");
            float steelCost = getFloatFromDataTable(map, "steel_cost");
            float tensionCost = getFloatFromDataTable(map, "tension_cost");
            int frameworkCost = getIntegerFromDataTable(map, "framework_cost");
            int manHourCost = getIntegerFromDataTable(map, "man_hour_cost");
            int energyCost = getIntegerFromDataTable(map, "energy_cost");
            int facultyCost = getIntegerFromDataTable(map, "faculty_cost");
            productionCosts.put(pcgName,
                    new Production(getProject(project), concreteCost, steelCost, tensionCost,
                            frameworkCost, manHourCost, energyCost, facultyCost));
        }
    }

    @Given("TCG(s) of following cost(s) is/are created for Project {string}")
    public void setting_tcg(String project, io.cucumber.datatable.DataTable dataTable) {
        for (Map<String, String> map : dataTable.asMaps()) {
            String tcgName = getStringFromDataTable(map, "name_of_tcg");
            int transportCost = getIntegerFromDataTable(map, "cost_of_one_transport");
            transportCosts.put(tcgName,
                    new Transport(getProject(project), transportCost));
        }
    }

    @Given("ACG(s) of following cost(s) is/are created for Project {string}")
    public void setting_acg(String project, io.cucumber.datatable.DataTable dataTable) {
        for (Map<String, String> map : dataTable.asMaps()) {
            String acgName = getStringFromDataTable(map, "name_of_acg");
            int assemblyCost = getIntegerFromDataTable(map, "assembly_cost_one_element");
            assemblyCosts.put(acgName,
                    new Assembly(getProject(project), assemblyCost));
        }
    }

    @When("Adding Person Of Contact {string} to Prospect {string}")
    public void adding_person_of_contact_to_prospect(String person, String prospect) {
        getProspect(prospect).addPersonOfContactToProspect(getPerson(person));
    }

    @When("Adding Address {string} to Prospect {string}")
    public void adding_address_to_prospect(String address, String prospect) {
        getProspect(prospect).addAddressToProspect(getAddress(address));
    }

    @Then("Prospect {string} has {short} Person(s) Of Contact")
    public void prospect_has_person_of_contact(String prospect, short amount) {
        Assertions.assertEquals(amount, getProspect(prospect).getPersonOfContacts().size());
    }

    @Then("Prospect {string} has {short} Address(es)")
    public void prospect_has_address(String prospect, short amount) {
        Assertions.assertEquals(amount, getProspect(prospect).getAddresses().size());
    }

    @When("Removing Person Of Contact {string} from Prospect {string}")
    public void removing_person_of_contact_from_prospect(String person, String prospect) {
        getProspect(prospect).removePersonOfContactFromProspect(getPerson(person));
    }

    @When("Removing Address {string} from Prospect {string}")
    public void removing_address_from_prospect(String address, String prospect) {
        getProspect(prospect).removeAddressFromProspect(getAddress(address));
    }


    @When("Setting Principal Activity of Prospect {string} to {string}")
    public void setting_principal_activity(String prospect, String activity) {
            getProspect(prospect).setPrincipalActivity(activity);
    }

    @Then("Principal Activity of Prospect {string} is set to {string}")
    public void principal_activity_is_set_up(String prospect, String activity) {
        Assertions.assertEquals(activity, getProspect(prospect).getPrincipalActivity());
    }

    @When("Setting Tax ID of Prospect {string} to {string}")
    public void setting_tax_id(String prospect, String tax) {
        getProspect(prospect).setTax(tax);
    }

    @Then("Tax ID of Prospect {string} is set to {string}")
    public void setting_name_of_prospect(String prospect, String tax) {
        Assertions.assertEquals(tax, getProspect(prospect).getTax());
    }

    @When("Changing Name of Prospect {string} to {string}")
    public void prospect_s_name_is_changed(String oldName, String newName) {
        getProspect(oldName).setName(newName);
    }

    @Then("Name of Prospect {string} is {string}")
    public void name_of_prospect(String prospect, String name) {
        Assertions.assertEquals(name, getProspect(prospect).getName());
    }

    @When("Adding {string} to Project {string}")
    public void setting_address_of_project(String address, String project) {
        getProject(project).setAddress(getAddress(address));
    }

    @Then("Project {string} has Address {string}")
    public void address_is_set_up_in_project(String project, String address) {
        Assertions.assertEquals(address, getProject(project).getAddress().getAddress());
    }

    @When("Setting Construction Type as {constructionType} in Project {string}")
    public void setting_construction_type_in_project(ConstructionType constructionType, String project) {
        getProject(project).setConstructionType(constructionType);
    }

    @Then("Construction Type of Project {string} is {constructionType}")
    public void construction_type_is_set_up(String project, ConstructionType constructionType) {
        Assertions.assertEquals(constructionType, getProject(project).getConstructionType());
    }

    @When("Setting Project Type as {projectType} of Project {string}")
    public void setting_project_type_in_project(ProjectType projectType, String project) {
        getProject(project).setProjectType(projectType);
    }

    @Then("Project Type of Project {string} is {projectType}")
    public void project_type_is_set_up(String project, ProjectType projectType) {
        Assertions.assertEquals(projectType, getProject(project).getProjectType());
    }

    @When("Setting Client Type as {clientType} in Project {string}")
    public void setting_client_type_in_project(ClientType clientType, String project) {
        getProject(project).setClientType(clientType);
    }

    @Then("Client Type of Project {string} is {clientType}")
    public void client_type_is_set_up(String project, ClientType clientType) {
        Assertions.assertEquals(clientType, getProject(project).getClientType());
    }

    @When("Setting that Design {designIncluded} in Project {string}")
    public void setting_design(boolean designIncluded, String project) {
        getProject(project).setWithDesign(designIncluded);
    }

    @Then("Design {designIncluded} in Project {string}")
    public void design_is_included_in_project(boolean designIncluded, String project) {
        Assertions.assertEquals(designIncluded, getProject(project).isWithDesign());
    }

    @When("Setting Status of Project {string} to {projectStatus}")
    public void setting_status_of_project(String project, Status status) {
        getProject(project).setStatus(status);
    }

    @Then("Status of Project {string} is {projectStatus}")
    public void status_is_set_up_in_project(String project, Status status) {
        Assertions.assertEquals(status, getProject(project).getStatus());
    }

    @When("Changing Name of Project {string} to {string}")
    public void is_changing_name_of_project(String project, String name) {
        getProject(project).setName(name);
    }

    @Then("Name of Project {string} is {string}")
    public void project_name_is_changed(String project, String name) {
        Assertions.assertEquals(name, getProject(project).getName());
    }

    @When("Setting amount of Element {string} to {short}")
    public void setting_amount_of_standard_element_to(String name, short amount) {
        getElement(name).setAmount(amount);
    }

    @When("Setting height of Element {string} to {float}")
    public void setting_height_of_standard_element_to(String name, float height) {
        StandardElement standardElement = (StandardElement) getElement(name);
        standardElement.setHeight(height);
    }

    @When("Setting width of Element {string} to {float}")
    public void setting_width_of_standard_element_to(String name, float width) {
        StandardElement standardElement = (StandardElement) getElement(name);
        standardElement.setWidth(width);
    }

    @When("Setting length of Element {string} to {float}")
    public void setting_length_of_standard_element_to(String name, float length) {
        getElement(name).setLength(length);
    }

    @When("Setting steel of Element {string} to {short}")
    public void setting_steel_of_standard_element_to(String name, short steel) {
        getElement(name).setSteelSaturation(steel);
    }

    @When("Setting tension of Element {string} to {short}")
    public void setting_tension_of_standard_element_to(String name, short tension) {
        getElement(name).setTensionSaturation(tension);
    }

    @Then("Total Volume of Element {string} is {float}")
    public void total_volume_of_standard_element_is(String name, float volume) {
        Assertions.assertEquals(volume, getElement(name).calculate().calculateVolumeTotal(), 0.001);
    }

    @Then("Total Area of Element {string} is {float}")
    public void total_area_of_standard_element_is(String name, float area) {
        Assertions.assertEquals(area, getElement(name).calculate().calculateAreaTotal(), 0.001);
    }

    @Then("Total Weight of Element {string} is {float}")
    public void total_weight_of_standard_element_is(String name, float weight) {
        Assertions.assertEquals(weight, getElement(name).calculate().calculateWeightTotal(), 0.001);
    }

    @Then("Total Steel of Element {string} is {int}")
    public void total_steel_of_standard_element_is(String name, int steel) {
        Assertions.assertEquals(steel, getElement(name).calculate().calculateSteelWeightTotal(), 0.001);
    }

    @Then("Total Tension of Element {string} is {float}")
    public void total_tension_of_standard_element_is(String name, float tension) {
        Assertions.assertEquals(tension, getElement(name).calculate().calculateTensionWeightTotal(), 0.001);
    }

    @Then("Total Framework Area of Element {string} is {float}")
    public void total_framework_of_standard_element_is(String name, float framework) {
        Assertions.assertEquals(framework, getElement(name).calculate().calculateFrameworkAreaTotal(), 0.001);
    }

    @Given("Accessory {string} of unit {unit} is created")
    public void accessory_is_created(String name, Unit unit) {
        accessories.add(new Accessory(name, unit));
    }

    @When("Setting Unit Price of Accessory {string} to {short}")
    public void setting_unit_price_of_accessory(String name, float price){
        getAccessory(name).setUnitPrice(price);
    }

    @Then("Accessory {string} has Unit Price of value {short}")
    public void unit_price_of_accessory(String name, float price){
        Assertions.assertEquals(price, getAccessory(name).getUnitPrice());
    }

    @When("Adding Accessory {string} of amount {float} to Standard Element {string}")
    public void adding_accessory_to_standard_element(String accessory, float amount, String element){
        getElement(element).addAccessory(getAccessory(accessory), amount);
    }

    @Then("Standard Element {string} has {short} Accessories")
    public void element_has_accessories(String element, short amount) {
        Assertions.assertEquals(amount, getElement(element).getAccessories().size());
    }

    @When("{short} {consoleOrCutout} of height {float}, width {float}, length {float} are/is added to Element " +
            "of name {string}")
    public void console_element_is_created(short amount, ConsoleOrCutoutType type,
                                           float height, float width, float length, String element) {
        ConsoleElement consoleElement = (ConsoleElement)getElement(element);
        consoleElement.addConsoleOrCutout(consoleElement.createConsole(type, amount, height, width, length));
    }

    @Then("Console Element of name {string} has {short} {consoleOrCutout}")
    public void element_has_consoles_and_cutouts(String element, short amount, ConsoleOrCutoutType type) {
        ConsoleElement consoleElement = (ConsoleElement)getElement(element);
        int actualResult = consoleElement.getConsolesAndCutouts().stream().filter(m->m.getType().equals(type))
                .mapToInt(m->m.getAmount()).reduce(0, Integer::sum);
        Assertions.assertEquals(amount, actualResult);
    }

    @When("All {consoleOrCutout} are removed from Element of name {string}")
    public void remove_consoles_or_cutout(ConsoleOrCutoutType type, String element) {
        ConsoleElement consoleElement = (ConsoleElement)getElement(element);
        consoleElement.getConsolesAndCutouts().removeIf(m->m.getType().equals(type));
    }

    @When("Setting Volume of Customized Element {string} to {float}")
    public void setting_volume_of_customized_element(String element, float volume) {
        CustomizedElement customizedElement = (CustomizedElement)getElement(element);
        customizedElement.setVolume(volume);
    }

    @When("Setting Area of Customized Element {string} to {float}")
    public void setting_area_of_customized_element(String element, float area) {
        CustomizedElement customizedElement = (CustomizedElement)getElement(element);
        customizedElement.setArea(area);
    }

    @When("Setting Weight of Customized Element {string} to {float}")
    public void setting_weight_of_customized_element(String element, float weight) {
        CustomizedElement customizedElement = (CustomizedElement)getElement(element);
        customizedElement.setWeight(weight);
    }

    @When("Setting Steel of Customized Element {string} to {float}")
    public void setting_steel_of_customized_element(String element, float steel) {
        CustomizedElement consoleElement = (CustomizedElement)getElement(element);
        consoleElement.setSteelWeight(steel);
    }

    @When("Setting Tension of Customized Element {string} to {float}")
    public void setting_tension_of_customized_element(String element, float tension) {
        CustomizedElement customizedElement = (CustomizedElement)getElement(element);
        customizedElement.setTensionWeight(tension);
    }

    @When("Setting Framework Area of Customized Element {string} to {float}")
    public void setting_framework_of_customized_element(String element, float framework) {
        CustomizedElement customizedElement = (CustomizedElement)getElement(element);
        customizedElement.setFrameworkArea(framework);
    }

    @Then("Project {string} has {short} PCG(s)")
    public void project_has_pcg(String project, short amount) {
        Assertions.assertEquals(amount, getProject(project).getProductionGroups().size());
    }

    @Then("PCG {string} has {short} Element(s)")
    public void pcg_has_elements(String pcg, short amount) {
        Assertions.assertEquals(amount, productionCosts.get(pcg).getElementsInGroup().size());
    }

    @When("Element {string} is assigned to Project {string}")
    public void assigning_element_to_project(String element, String project) {
        getProject(project).addElement(getElement(element));
    }

    @When("Element {string} is removed from Project {string}")
    public void removing_element_from_project(String element, String project) {
        getProject(project).removeElement(getElement(element));
    }

    @Then("Project {string} has {short} Element(s)")
    public void project_has_elements(String project, short amount) {
        Assertions.assertEquals(amount, getProject(project).getElements().size());
    }

    @When("Element {string} is assigned to PCG {string}")
    public void assigning_element_to_pcg(String element, String pcg) {
        productionCosts.get(pcg).addSelectedElementsToGroup(getElement(element));
    }

    @Then("Based on PCG {string} Concrete cost of 1 piece of Element {string} is {int}")
    public void concrete_cost_of_one_piece(String pcg, String element, int amount) {
        Assertions.assertEquals(amount, productionCosts.get(pcg).calculate()
                .calculateConcreteCostOfOnePiece(getElement(element)));
    }

    @Then("Based on PCG {string} Concrete cost of all pieces of Element {string} is {int}")
    public void concrete_cost_of_all_pieces(String pcg, String element, int amount) {
        Assertions.assertEquals(amount, productionCosts.get(pcg).calculate()
                .calculateConcreteCostOfAllPieces(getElement(element)));
    }

    @Then("Based on PCG {string} Steel cost of 1 piece of Element {string} is {int}")
    public void steel_cost_of_one_pieces(String pcg, String element, int amount) {
        Assertions.assertEquals(amount, productionCosts.get(pcg).calculate()
                .calculateSteelCostOfOnePiece(getElement(element)));
    }

    @Then("Based on PCG {string} Steel cost of all pieces of Element {string} is {int}")
    public void steel_cost_of_all_piecess(String pcg, String element, int amount) {
        Assertions.assertEquals(amount, productionCosts.get(pcg).calculate()
                .calculateSteelCostOfAllPieces(getElement(element)));
    }

    @Then("Based on PCG {string} Tension cost of 1 piece of Element {string} is {int}")
    public void tension_cost_of_one_piece(String pcg, String element, int amount) {
        Assertions.assertEquals(amount, productionCosts.get(pcg).calculate()
                .calculateTensionCostOfOnePiece(getElement(element)));
    }

    @Then("Based on PCG {string} Tension cost of all pieces of Element {string} is {int}")
    public void tension_cost_of_all_pieces(String pcg, String element, int amount) {
        Assertions.assertEquals(amount, productionCosts.get(pcg).calculate()
                .calculateTensionCostOfOAllPieces(getElement(element)));
    }

    @Then("Based on PCG {string} Framework cost of 1 piece of Element {string} is {int}")
    public void framework_cost_of_one_piece(String pcg, String element, int amount) {
        Assertions.assertEquals(amount, productionCosts.get(pcg).calculate()
                .calculateFrameworkCostOfOnePiece(getElement(element)));
    }

    @Then("Based on PCG {string} Framework cost of all pieces of Element {string} is {int}")
    public void framework_cost_of_all_pieces(String pcg, String element, int amount) {
        Assertions.assertEquals(amount, productionCosts.get(pcg).calculate()
                .calculateFrameworkCostOfAllPieces(getElement(element)));
    }

    @Then("Based on PCG {string} Labour and Energy cost of 1 piece of Element {string} is {int}")
    public void labour_energy_cost_of_one_piece(String pcg, String element, int amount) {
        Assertions.assertEquals(amount, productionCosts.get(pcg).calculate()
                .calculateEnergyAndLabourCostsOfOnePiece(getElement(element)));
    }

    @Then("Based on PCG {string} Labour and Energy cost of all pieces of Element {string} is {int}")
    public void labour_energy_cost_of_all_pieces(String pcg, String element, int amount) {
        Assertions.assertEquals(amount, productionCosts.get(pcg).calculate()
                .calculateEnergyAndLabourCostsOfAllPieces(getElement(element)));
    }

    @When("Following Elements are assigned to Project {string}")
    public void following_elements_are_assigned_to_project(String project,
                                                                    io.cucumber.datatable.DataTable dataTable) {
        for (String name : dataTable.asList())
        getProject(project).addElement(getElement(name));
    }

    @When("All Elements from Project are assigned to PCG {string}")
    public void all_elements_from_project_are_assigned_to_pcg(String pcg) {
        productionCosts.get(pcg).addAllElementsToGroup();
    }

    @When("Following Element(s) are/is assigned to PCG {string}")
    public void following_elements_are_assigned_to_pcg(String pcg,
                                                           @Transpose io.cucumber.datatable.DataTable dataTable) {
        for (String name : dataTable.asList())
            productionCosts.get(pcg).addSelectedElementsToGroup(getElement(name));
    }

    @When("Following Element(s) are/is removed from PCG {string}")
    public void removing_elements_from_pcg(String pcg, @Transpose io.cucumber.datatable.DataTable dataTable) {
        for (String name : dataTable.asList())
            productionCosts.get(pcg).removeSelectedElementsFromGroup(getElement(name));
    }

    @When("All Elements from Project are assigned to TCG {string}")
    public void all_elements_from_project_are_assigned_to_tcg(String tcg) {
        transportCosts.get(tcg).addAllElementsToGroup();
    }

    @When("All Elements from Project are assigned to ACG {string}")
    public void all_elements_from_project_are_assigned_to_acg(String acg) {
        assemblyCosts.get(acg).addAllElementsToGroup();
    }

    @Then("Project {string} has {short} TCGs")
    public void project_has_tcg(String project, short amount) {
        Assertions.assertEquals(amount, getProject(project).getTransportGroups().size());
    }

    @Then("TCG {string} has {short} Element(s)")
    public void tcg_has_elements(String tcg, short amount) {
        Assertions.assertEquals(amount, transportCosts.get(tcg).getElementsInGroup().size());
    }

    @Then("Project {string} has {short} ACGs")
    public void project_has_acg(String project, short amount) {
        Assertions.assertEquals(amount, getProject(project).getAssemblyGroups().size());
    }

    @Then("ACG {string} has {short} Element(s)")
    public void acg_has_elements(String tcg, short amount) {
        Assertions.assertEquals(amount, assemblyCosts.get(tcg).getElementsInGroup().size());
    }

    @Then("Total weight of elements in TCG {string} is {int}")
    public void total_weight_of_elements_in_tcg(String tcg, int amount) {
        Assertions.assertEquals(amount, transportCosts.get(tcg).calculate().calculateTotalWeightOfElementsInGroup());
    }

    @Then("Number of transports for TCG {string} is {short}")
    public void number_of_transports_in_tcg(String tcg, short amount) {
        Assertions.assertEquals(amount, transportCosts.get(tcg).calculate().calculateNumberOfCTransportsInGroup());
    }

    @Then("Cost of transport of elements in TCG {string} is {int}")
    public void cost_of_transport_of_elements_in_tcg(String tcg, int amount) {
        Assertions.assertEquals(amount, transportCosts.get(tcg).calculate().calculateCostOfTransportInGroup());
    }

    @Then("Assembly cost of elements in ACG {string} is {int}")
    public void cost_of_assembly_of_elements_in_acg(String tcg, int amount) {
        Assertions.assertEquals(amount, assemblyCosts.get(tcg).calculate().calculateCostOfAssemblyInGroup());
    }

    @When("Setting maximum load of one transport in TCG {string} to {short}")
    public void all_elements_from_project_are_assigned_to_tcg(String tcg, short load) {
        transportCosts.get(tcg).setMaxCarLoadInTones(load);
    }

    @When("Following Element(s) are/is assigned to TCG {string}")
    public void following_elements_are_assigned_to_tcg(String tcg,
                                                       @Transpose io.cucumber.datatable.DataTable dataTable) {
        for (String name : dataTable.asList())
            transportCosts.get(tcg).addSelectedElementsToGroup(getElement(name));
    }

    @When("Following Element(s) are/is removed from TCG {string}")
    public void removing_elements_from_tcg(String tcg, @Transpose io.cucumber.datatable.DataTable dataTable) {
        for (String name : dataTable.asList())
            transportCosts.get(tcg).removeSelectedElementsFromGroup(getElement(name));
    }

    @When("Following Element(s) are/is assigned to ACG {string}")
    public void following_elements_are_assigned_to_acg(String acg,
                                                       @Transpose io.cucumber.datatable.DataTable dataTable) {
        for (String name : dataTable.asList())
            assemblyCosts.get(acg).addSelectedElementsToGroup(getElement(name));
    }

    @When("Following Element(s) are/is removed from ACG {string}")
    public void removing_elements_from_acg(String acg, @Transpose io.cucumber.datatable.DataTable dataTable) {
        for (String name : dataTable.asList())
            assemblyCosts.get(acg).removeSelectedElementsFromGroup(getElement(name));
    }

    @When("All Elements are removed from ACG {string}")
    public void removing_all_elements_from_acg(String acg) {
            assemblyCosts.get(acg).removeAllElementsFromGroup();
    }

    @Then("Production cost of Project {string} is {int}")
    public void production_cost_of_project(String project, int cost) {
        ProjectCostCalculator calculator = new ProjectCostCalculator(getProject(project));
        Assertions.assertEquals(cost, calculator.calculateAllProductionCostsOfProject());
    }

    @Then("Transport cost of Project {string} is {int}")
    public void transport_cost_of_project(String project, int cost) {
        ProjectCostCalculator calculator = new ProjectCostCalculator(getProject(project));
        Assertions.assertEquals(cost, calculator.calculateAllTransportCostsOfProject());
    }

    @Then("Assembly cost of Project {string} is {int}")
    public void assembly_cost_of_project(String project, int cost) {
        ProjectCostCalculator calculator = new ProjectCostCalculator(getProject(project));
        Assertions.assertEquals(cost, calculator.calculateAllAssemblyCostsOfProject());
    }

    @Given("All addresses are removed from database")
    public void all_addresses_are_removed_from_database() {
        addressRepository.deleteAll();
    }

    @When("User clicks on SAVE")
    public void user_clicks_on_save() {
        for (Address address : addresses)
        addressRepository.save(address);
    }




}

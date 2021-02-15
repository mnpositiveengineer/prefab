package com.prefab.sales.client;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ProspectsAddresses")
public class ProspectAddress {

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "id", unique = true)
    private int id;
    @ManyToOne
    @JoinColumn(name = "prospect_id")
    private Prospect prospect;
    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    public ProspectAddress() {
    }
}

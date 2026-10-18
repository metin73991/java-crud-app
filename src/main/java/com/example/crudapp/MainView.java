package com.example.crudapp;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("")
public class MainView extends VerticalLayout {
    private CustomerRepository repository;
    private Grid<Customer> grid = new Grid<>(Customer.class);
    private TextField firstName = new TextField("First name");
    private TextField lastName = new TextField("Last name");
    private Button save = new Button("Save");
    private Button delete = new Button("Remove Customer");

    public MainView(CustomerRepository repository) {
        this.repository = repository;

        // Set up the grid
        grid.setColumns("firstName", "lastName");

        // Add components to the screen
        HorizontalLayout form = new HorizontalLayout(firstName, lastName, save, delete);
        add(form, grid);

        updateList();

        // Handle saving
        save.addClickListener(e -> {
            Customer c = new Customer(firstName.getValue(), lastName.getValue());
            repository.save(c);
            firstName.clear();
            lastName.clear();
            updateList();
        });

        // Handle deleting
        delete.addClickListener(e -> {
            Customer c = grid.asSingleSelect().getValue();
            if (c != null) {
                repository.delete(c);
                updateList();
            }
        });
    }

    private void updateList() {
        grid.setItems(repository.findAll());
    }
}
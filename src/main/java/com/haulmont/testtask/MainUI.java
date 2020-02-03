package com.haulmont.testtask;

import com.haulmont.testtask.PharmacyDb.Daos.MedicalPrescriptionDao;
import com.haulmont.testtask.PharmacyDb.Dtos.MedicalPrescription;
import com.haulmont.testtask.PharmacyDb.HsqldbDaos.HsqldbPharmacyDbDao;
import com.haulmont.testtask.PharmacyUi.PatientForm;
import com.vaadin.annotations.Theme;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.sql.SQLException;
import java.util.List;

@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {
    public static final String DB_URL = "jdbc:hsqldb:file:testdb";
    public static final String USER = "SA";
    public static final String PASSWORD = "";
    private HsqldbPharmacyDbDao hsqldbPharmacyDbDao = new HsqldbPharmacyDbDao(DB_URL, USER, PASSWORD);

    private PatientForm patientForm = new PatientForm(this);

    private MedicalPrescriptionDao prescriptionDao = hsqldbPharmacyDbDao.getMedicalPrescriptionDao();

    private Grid<MedicalPrescription> gridPrescriptions = new Grid<>(MedicalPrescription.class);
    private TextField tfFilterText = new TextField();

    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout layout = new VerticalLayout();

        tfFilterText.setPlaceholder("отфильтровать по описанию ...");
        tfFilterText.addValueChangeListener(ev -> showAllMedicalPrescriptions());
        tfFilterText.setValueChangeMode(ValueChangeMode.LAZY);

        Button btnClearTfFilterText = new Button(VaadinIcons.CLOSE);
        btnClearTfFilterText.setDescription("Очистить фильтр");
        btnClearTfFilterText.addClickListener(ev -> tfFilterText.clear());

        CssLayout filtering = new CssLayout();
        filtering.addComponents(tfFilterText, btnClearTfFilterText);
        filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        //gridPrescriptions.setColumns("surname", "forename", "patronymic", "phone", "id");

        HorizontalLayout mainLayout = new HorizontalLayout(gridPrescriptions, patientForm);
        mainLayout.setSizeFull();
        gridPrescriptions.setSizeFull();
        mainLayout.setExpandRatio(gridPrescriptions, 1);

        layout.addComponents(filtering, mainLayout);

        showAllMedicalPrescriptions();

        setContent(layout);
    }

    public void showAllMedicalPrescriptions() {
        try {
            List<MedicalPrescription> prescriptions;

            String filterDescription = tfFilterText.getValue();
            if (filterDescription.equals(""))
                prescriptions = prescriptionDao.getAllMedicalPrescriptions();
            else
                prescriptions = prescriptionDao.getMedicalPrescriptionsByDescription(filterDescription);

            gridPrescriptions.setItems(prescriptions);
        } catch (SQLException | ClassNotFoundException exc) {
            exc.printStackTrace();
        }
    }
}
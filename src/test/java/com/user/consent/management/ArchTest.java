package com.user.consent.management;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.user.consent.management");

        noClasses()
            .that()
            .resideInAnyPackage("com.user.consent.management.service..")
            .or()
            .resideInAnyPackage("com.user.consent.management.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..com.user.consent.management.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}

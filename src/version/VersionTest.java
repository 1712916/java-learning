package version;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import util.Validate;

class VersionTest {
    @Test
    void versionCompareShouldReturnZeroWhenVersionsAreEqual() {
        Version version1 = new Version.VersionBuilder().setVersionString("1.2.3").build();
        Version version2 = new Version.VersionBuilder().setVersionString("1.2.3").build();
        Assertions.assertEquals(0, version1.compare(version2));
    }

    @Test
    void versionCompareShouldReturnPositiveWhenVersion1IsGreater() {
        Version version1 = new Version.VersionBuilder().setVersionString("1.3.0").build();
        Version version2 = new Version.VersionBuilder().setVersionString("1.2.3").build();
        Assertions.assertTrue(version1.compare(version2) > 0);
    }

    @Test
    void versionCompareShouldReturnNegativeWhenVersion1IsLesser() {
        Version version1 = new Version.VersionBuilder().setVersionString("1.1.3").build();
        Version version2 = new Version.VersionBuilder().setVersionString("1.2.3").build();
        Assertions.assertTrue(version1.compare(version2) < 0);
    }

    @Test
    void versionCompareShouldHandleDifferentLengths() {
        Version version1 = new Version.VersionBuilder().setVersionString("1.2").build();
        Version version2 = new Version.VersionBuilder().setVersionString("1.2.3").build();
        Assertions.assertTrue(version1.compare(version2) < 0);
    }

    @Test
    void versionBuilderShouldThrowExceptionWhenVersionStringIsNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Version.VersionBuilder().setVersionString(null).build();
        });
    }

    @Test
    void versionBuilderShouldThrowExceptionWhenVersionStringIsInvalid() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Version.VersionBuilder().setVersionString("1.2.a").build();
        });
    }

    @Test
    void getVersionStringShouldReturnOptionalWithValueWhenVersionStringIsNotNull() {
        Version version = new Version.VersionBuilder().setVersionString("1.2.3").build();
        Assertions.assertTrue(version.getVersionString().isPresent());
        Assertions.assertEquals("1.2.3", version.getVersionString().get());
    }

    @Test
    void setValidatorShouldAcceptValidValidator() {
        Version.VersionBuilder versionBuilder = new Version.VersionBuilder();
        Validate<String>       validator      = new VersionValidator();
        versionBuilder.setValidator(validator);
        Assertions.assertEquals(validator, versionBuilder.getValidator());
    }

    @Test
    void setValidatorShouldThrowExceptionWhenValidatorIsNull() {
        Version.VersionBuilder versionBuilder = new Version.VersionBuilder();
        Assertions.assertThrows(IllegalArgumentException.class, () -> versionBuilder.setValidator(null));
    }

    @Test
    void getValidatorShouldReturnNonNullWhenValidatorIsSet() {
        Version.VersionBuilder versionBuilder = new Version.VersionBuilder();
        Validate<String> validator = new VersionValidator();
        versionBuilder.setValidator(validator);
        Assertions.assertNotNull(versionBuilder.getValidator());
    }

    @Test
    void getValidatorShouldReturnDefaultValidatorWhenNotExplicitlySet() {
        Version.VersionBuilder versionBuilder = new Version.VersionBuilder();
        Assertions.assertInstanceOf(VersionValidator.class, versionBuilder.getValidator());
    }
}
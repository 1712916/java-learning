package version;

import org.jetbrains.annotations.NotNull;
import util.Validate;

import java.util.Arrays;
import java.util.Optional;


public class Version {
    static final String separator = "\\.";

    private final String versionString;

    private Version(@NotNull String versionString) {
        this.versionString = versionString;
    }

    public Optional<String> getVersionString() {
        return Optional.ofNullable(versionString);
    }

    public int compare(@NotNull Version other) {
        String[] thisSegments  = versionString.split(Version.separator);
        String[] otherSegments = other.versionString.split(Version.separator);

        for (int i = 0; i < Math.min(thisSegments.length, otherSegments.length); i++) {
            int comparison = Long.compare(Long.parseLong(thisSegments[i]), Long.parseLong(otherSegments[i]));
            if (comparison != 0) {
                return comparison;
            }
        }

        return Integer.compare(thisSegments.length, otherSegments.length);
    }

    static class VersionBuilder {
        private Validate<String> versionValidator = new VersionValidator();
        private String versionString;

        public void setValidator(@NotNull Validate<String> versionValidator) {
            this.versionValidator = versionValidator;
        }

        public Validate<String> getValidator() {
            return versionValidator;
        }

        public VersionBuilder setVersionString(String versionString) {
            this.versionString = versionString;
            return this;
        }

        public Version build() {
            if (versionString == null) {
                throw new IllegalArgumentException("Version string is null");
            }

            if (versionValidator.isValid(versionString)) {
                return new Version(versionString);
            } else {
                throw new IllegalArgumentException(versionValidator.getInvalidMessage());
            }
        }
    }
}

class VersionValidator implements Validate<String> {

    @Override public boolean isValid(String value) {
        try {
            return Arrays.stream(value.split(Version.separator)).mapToLong(Long::parseLong).allMatch(i -> i >= 0);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override public String getInvalidMessage() {
        return "Invalid version string";
    }
}

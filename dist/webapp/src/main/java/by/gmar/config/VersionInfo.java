package by.gmar.config;

public final class VersionInfo {

    private String version;
    private String buildDate;
    private String releaseVersion;

    public VersionInfo(String version, String buildDate, String releaseVersion) {
        this.version = version;
        this.buildDate = buildDate;
        this.releaseVersion = releaseVersion;
    }

    public String getVersion() {
        return version;
    }

    public String getBuildDate() {
        return buildDate;
    }

    public String getReleaseVersion() {
        return releaseVersion;
    }
}

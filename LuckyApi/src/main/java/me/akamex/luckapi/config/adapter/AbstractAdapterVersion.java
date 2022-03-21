package me.akamex.luckapi.config.adapter;

public abstract class AbstractAdapterVersion implements AdapterVersion {

    protected final double version;

    protected AbstractAdapterVersion(double version) {
        this.version = version;
    }

    @Override
    public double getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return "AbstractAdapterVersion{" +
                "version=" + version +
                '}';
    }
}

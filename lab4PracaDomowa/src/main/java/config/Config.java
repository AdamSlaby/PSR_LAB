package config;

import oracle.kv.KVStoreConfig;

public class Config {
    private final static String STORE = "kvstore";
    private final static String HOST = "localhost";
    private final static String PORT = "5000";

    public static KVStoreConfig getConfig() {
        return new KVStoreConfig(STORE, HOST + ":" + PORT);
    }
}

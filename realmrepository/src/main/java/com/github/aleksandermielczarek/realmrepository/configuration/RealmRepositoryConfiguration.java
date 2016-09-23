package com.github.aleksandermielczarek.realmrepository.configuration;

/**
 * Created by Aleksander Mielczarek on 22.09.2016.
 */

public class RealmRepositoryConfiguration {

    private final RealmProvider realmProvider;

    private RealmRepositoryConfiguration(Builder builder) {
        realmProvider = builder.realmProvider;
    }

    public static RealmRepositoryConfiguration getDefault() {
        return builder().build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public RealmProvider getRealmProvider() {
        return realmProvider;
    }

    public static class Builder {

        private RealmProvider realmProvider;

        private Builder() {

        }

        public Builder withRealmProvider(RealmProvider realmProvider) {
            this.realmProvider = realmProvider;
            return this;
        }

        public RealmRepositoryConfiguration build() {
            if (realmProvider == null) {
                realmProvider = new DefaultRealmProvider();
            }
            return new RealmRepositoryConfiguration(this);
        }
    }

}

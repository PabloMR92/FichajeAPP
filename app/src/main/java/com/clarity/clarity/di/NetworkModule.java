package com.clarity.clarity.di;

import com.clarity.clarity.network.NetworkManager;

import dagger.Module;
import dagger.Provides;

@Module
public class NetworkModule {

    @Provides
    NetworkManager providesNetworkManager() {
        return new NetworkManager();
    }
}


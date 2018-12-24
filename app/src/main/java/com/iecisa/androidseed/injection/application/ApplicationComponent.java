package com.iecisa.androidseed.injection.application;

import com.iecisa.androidseed.injection.presentation.PresentationComponent;
import com.iecisa.androidseed.injection.presentation.PresentationModule;
import com.iecisa.androidseed.injection.service.ServiceComponent;
import com.iecisa.androidseed.injection.service.ServiceModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, NetworkingModule.class})
public interface ApplicationComponent {
    public PresentationComponent newPresentationComponent(PresentationModule presentationModule);
    public ServiceComponent newServiceComponent(ServiceModule serviceModule);
}

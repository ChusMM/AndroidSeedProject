package com.iecisa.androidseed.injection.application;

import com.iecisa.androidseed.injection.presentation.PresentationComponent;
import com.iecisa.androidseed.injection.presentation.PresentationModule;
import com.iecisa.androidseed.injection.service.ServiceComponent;
import com.iecisa.androidseed.injection.service.ServiceModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, UseCaseModule.class})
public interface ApplicationComponent {
    PresentationComponent newPresentationComponent(PresentationModule presentationModule);
    ServiceComponent newServiceComponent(ServiceModule serviceModule);
}

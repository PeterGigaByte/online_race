package fei.stuba.bp.rigo.preteky.service.implementation;

import fei.stuba.bp.rigo.preteky.repository.SettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class Settings implements fei.stuba.bp.rigo.preteky.service.service.Settings {
    @Autowired
    private SettingsRepository settingsRepository;

    public  Settings(SettingsRepository settingsRepository){
        super();
        this.settingsRepository = settingsRepository;
    }
}

package com.homo.core.common.module;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class ModuleMgrImpl implements ModuleMgr{
    @Autowired(required = false)
    RootModule rootModule;
    @Autowired(required = false)
    Map<String,Module> moduleMap;

    @PostConstruct
    public void init(){
        try {
            rootModule.init();
            initModules();
            afterInitModules();
        }catch (Exception e){
            log.error("ModuleMgr init error !",e);
        }
    }


    @Override
    public Set<Module> getModules() {
        return moduleMap.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toSet());
    }

    @Override
    public Module getModule(String moduleName) {
        return moduleMap.get(moduleName);
    }

    @Override
    public void initModules() {
        moduleMap.values().forEach(item->{item.init(rootModule);init();});
    }

    @Override
    public void afterInitModules() {
        moduleMap.values().forEach(Module::afterAllModuleInit);
    }

    @Override
    public String getPodName(){
        return rootModule.getPodName();
    }

}

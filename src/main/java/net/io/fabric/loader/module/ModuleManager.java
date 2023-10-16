package net.io.fabric.loader.module;

import net.io.fabric.loader.module.modules.client.*;
import net.io.fabric.loader.module.modules.combate.*;
import net.io.fabric.loader.module.modules.render.*;
import net.io.fabric.loader.module.modules.misc.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

public class ModuleManager
{
	private final HashMap<Class<? extends Module>, Module> modulesByClass = new HashMap<>();
	private final HashMap<String, Module> modulesByName = new HashMap<>();
	private final HashSet<Module> modules = new HashSet<>();

	public ModuleManager()
	{
		addModules();
	}

	public ArrayList<Module> getModules()
	{
		ArrayList<Module> arrayList = new ArrayList<>(modules);
		arrayList.sort(Comparator.comparing(Module::getName));
		return arrayList;
	}

	public int getSizeOfModulesByCategory(Category category) {
		ArrayList<Module> arrayList = getModules();
		int size = 0;

		for (int i = 0; i < arrayList.size(); i++) {
			if (arrayList.get(i).getCategory() == category)
				size++;
		}
		return size;
	}

	public int getSizeOfModulesByCategory(String categoryName) {
		return getSizeOfModulesByCategory(getCategoryByName(categoryName));
	}

	public Category getCategoryByName(String categoryName) {

		Category[] categories = {Category.Client, Category.Combat, Category.Render, Category.Misc};

		for (Category category : categories) {
			if (category.toString().equals(categoryName)) {
				return category;
			}
		}

		return null;

	}

	public Module getModule(Class<? extends Module> clazz)
	{
		return modulesByClass.get(clazz);
	}

	public Module getModuleByName(String name)
	{
		return modulesByName.get(name);
	}

	private void addModules()
	{
		addModule(AGH.class);
		addModule(ADH.class);
		addModule(AHC.class);
		addModule(AIT.class);
		addModule(AXP.class);
		addModule(AT.class);
		addModule(BC.class);
		addModule(CGS.class);
		addModule(FP.class);;
		addModule(NF.class);
		addModule(NHC.class);
		addModule(NI.class);
		addModule(NP.class);
		addModule(SP.class);
		addModule(SA.class);
		addModule(NTGS.class);
		addModule(PP.class);
		addModule(TB.class);
		addModule(Watermark.class);
		addModule(vydralogo.class);
		addModule(MarlowAnchor.class);
		addModule(AutoWTap.class);
		addModule(AutoDtap.class);
		addModule(MiddleClickPing.class);
		addModule(UpsideDownPlayers.class);
		addModule(ViewModel.class);
		addModule(AutoInvTotemLegit.class);
	}


	private void addModule(Class<? extends Module> clazz) {
		try {
			Module module = clazz.getConstructor().newInstance();
			modulesByClass.put(clazz, module);
			modulesByName.put(module.getName(), module);
			modules.add(module);
		} catch (NoSuchMethodException | IllegalAccessException | InstantiationException |
				 InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	public void removeModules() {
		for (Module module : this.getModules()) {
			removeModule(module.getClass());
		}
	}

	private void removeModule(Class<? extends Module> clazz) {
		modulesByClass.remove(clazz);
		modulesByName.remove(clazz);
		modules.remove(clazz);
	}



	public int getNumberOfCategory(Category cat){
		int count=0;
		for(Module module : getModules()){
			if(module.getCategory()==cat){
				count++;
			}
		}
		return count;
	}
}




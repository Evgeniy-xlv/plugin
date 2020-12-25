package ru.xlv.plugin;

public class TestPlugin1 extends Plugin {

    private final SomeTestClass someTestClass = new SomeTestClass();
    private final SomeTestClass1 someTestClass1 = new SomeTestClass1();

    public TestPlugin1() {
        System.out.println("<init> of " + getClass().getName());
    }

    @Override
    protected void onEnable() {
        someTestClass.test();
        someTestClass1.test();
        String className = "ru.xlv.plugin.TestPlugin";
        try {
            Class<?> aClass = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        for (Plugin plugin : getPluginManager().getPlugins()) {
            if(plugin.getClass().getName().equals(className)) {
                System.out.println(((TestPlugin) plugin).testVal);
            }
        }
    }
}

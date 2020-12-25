package ru.xlv.plugin;

public class TestPlugin extends Plugin {

    private final SomeTestClass someTestClass = new SomeTestClass();

    public final String testVal = "SOME TEXT";

    public TestPlugin() {
        System.out.println("<init> of " + getClass().getName());
    }

    @Override
    protected void onEnable() {
        someTestClass.test();
    }
}

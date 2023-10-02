package ru.otus.gradle.codegen

import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory

interface CodeGenPluginExtension {
    @Input
    Property<String> getClassName()

    @Input
    Property<String> getPackageName()

    @InputDirectory
    Property<String> getSourceDir()

    @Input
    Property<String> getFieldName()

    @Input
    Property<String> getFields()
}
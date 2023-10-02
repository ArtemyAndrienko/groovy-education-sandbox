package ru.otus.gradle.codegen

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.invocation.Gradle
import org.gradle.api.plugins.ExtensionAware

import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeSpec
import com.squareup.javapoet.JavaFile

import javax.lang.model.element.Modifier
import java.nio.file.Files


class CodeGenPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        def extension = project.extensions.create('codegen', CodeGenPluginExtension)

        extension.className.convention("Main")
        extension.packageName.convention(project.group)
        extension.fieldName.convention("field1")
        extension.sourceDir.convention("${project.buildDir}/tmp")
        extension.fields.convention('[first:[one:1.1], second:2]')


        Gradle gradle = project.getRootProject().getGradle()
        println("1>${gradle}")
        ExtensionAware gradleExtensions = (ExtensionAware) gradle

        project.task('simpleCodeGen') {

            def directory = project.file("src/codegen")

            doLast {
                println ">>${extension.className.get()}"
                println ">>${extension.fieldName.get()}"
                println ">>${extension.packageName.get()}"
                println ">>${extension.sourceDir.get()}"

                def map = Eval.me(extension.fields.get())
                println map


                def clas = getCustomClass("${extension.className.get()}", "${extension.fieldName.get()}")
                Files.createDirectories(directory.toPath())
                writeToOutputFile(extension.sourceDir.get(), extension.packageName.get(), clas)

            }


        }

    }


    static TypeSpec getCustomClass(String className, String fieldName) {
        return TypeSpec
                .classBuilder(className)
                .addModifiers(Modifier.PUBLIC)
                .addField(FieldSpec
                        .builder(String.class, fieldName)
                        .addModifiers(Modifier.PRIVATE)
                        .build())
                .addMethod(MethodSpec
                        .methodBuilder("get${fieldName.capitalize()}")
                        .addModifiers(Modifier.PUBLIC)
                        .returns(String.class)
                        .addStatement("return this.${fieldName}")
                        .build())
                .addMethod(MethodSpec
                        .methodBuilder("set${fieldName.capitalize()}")
                        .addParameter(String.class, fieldName)
                        .addModifiers(Modifier.PUBLIC)
                        .addStatement("this.${fieldName} = ${fieldName}")
                        .build())
                .addMethod(MethodSpec
                        .methodBuilder("toString")
                        .addModifiers(Modifier.PUBLIC)
                        .returns(String.class)
                        .addStatement("return \"${className}{${fieldName} = \" + this.${fieldName} + \"}\"")
                        .build())
                .build()

    }


    private void writeToOutputFile(String dirName, String packageName, TypeSpec typeSpec) throws IOException {
        String FOUR_WHITESPACES = "    "
        JavaFile javaFile = JavaFile
                .builder(packageName, typeSpec)
                .indent(FOUR_WHITESPACES)
                .build();
        println javaFile.toString()
        javaFile.writeTo(new File(dirName))
    }

}
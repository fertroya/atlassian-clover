package com.atlassian.clover.registry.entities;

import com.atlassian.clover.api.registry.BlockMetrics;
import com.atlassian.clover.api.registry.ClassInfo;
import com.atlassian.clover.api.registry.ContextSet;
import com.atlassian.clover.api.registry.EntityContainer;
import com.atlassian.clover.api.registry.EntityVisitor;
import com.atlassian.clover.api.registry.FileInfo;
import com.atlassian.clover.api.registry.MethodInfo;
import com.atlassian.clover.api.registry.ModifiersInfo;
import com.atlassian.clover.api.registry.PackageInfo;
import com.atlassian.clover.api.registry.SourceInfo;
import com.atlassian.clover.api.registry.StatementInfo;
import com.atlassian.clover.registry.FixedFileRegion;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BaseClassInfo extends FixedFileRegion implements ClassInfo {
    /** Base class name */
    protected String name;
    /** Fully qualified class name */
    protected String qualifiedName;
    /** Class modifiers (like public, static) with class annotations */
    protected Modifiers modifiers;

    /** Whether this object is an interface */
    protected boolean typeInterface;
    /** Whether this object is an enum */
    protected boolean typeEnum;
    /** Whether this object is an annotation */
    protected boolean typeAnnotation;
    /** Whether this object is a test class (according to custom or default test detector */
    protected boolean testClass;

    protected transient BasePackageInfo packageInfo;
    protected transient BlockMetrics rawMetrics;
    protected transient BlockMetrics metrics;
    protected transient ContextSet contextFilter;

    public BaseClassInfo(BasePackageInfo packageInfo, BaseFileInfo containingFile,
                         String name, SourceInfo region, Modifiers modifiers,
                         boolean typeInterface, boolean typeEnum, boolean typeAnnotation) {

        super(containingFile, region);
        this.name = name;
        this.packageInfo = packageInfo;
        this.typeInterface = typeInterface;
        this.typeEnum = typeEnum;
        this.typeAnnotation = typeAnnotation;
        this.qualifiedName = calcQualifiedName();
        this.modifiers = modifiers;
    }

    protected BaseClassInfo(String name, String qualifiedName, SourceInfo region, Modifiers modifiers,
                            boolean typeInterface, boolean typeEnum, boolean typeAnnotation, boolean testClass) {
        super(null, region);
        this.name = name;
        this.qualifiedName = qualifiedName;
        this.typeInterface = typeInterface;
        this.typeEnum = typeEnum;
        this.typeAnnotation = typeAnnotation;
        this.testClass = testClass;
        this.modifiers = modifiers;
    }

    private String calcQualifiedName() {
        return
            packageInfo == null
                ? null
                : packageInfo.isDefault() ? getName() : packageInfo.getName() + "." + getName();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getQualifiedName() {
        if (qualifiedName == null && packageInfo != null) {
            qualifiedName = calcQualifiedName();
        }
        return qualifiedName;
    }

    @Override
    public ModifiersInfo getModifiers() {
        return modifiers;
    }

    @Override
    @Nullable
    public ClassInfo getContainingClass() {
        throw new UnsupportedOperationException("Use FullClassInfo");
    }

    @Override
    @Nullable
    public MethodInfo getContainingMethod() {
        throw new UnsupportedOperationException("Use FullClassInfo");
    }

    @Override
    @Nullable
    public FileInfo getContainingFile() {
        return containingFile;
    }

    @Override
    public EntityContainer getParent() {
        throw new UnsupportedOperationException("Use FullClassInfo");
    }

    @Override
    public PackageInfo getPackage() {
        return packageInfo;
    }

    void setPackage(BasePackageInfo packageInfo) {
        this.packageInfo = packageInfo;
    }

    public boolean isInterface() {
        return typeInterface;
    }

    public boolean isEnum() {
        return typeEnum;
    }

    public boolean isAnnotationType() {
        return typeAnnotation;
    }

    @Override
    public BlockMetrics getRawMetrics() {
        return rawMetrics;
    }    

    @Override
    public BlockMetrics getMetrics() {
        return metrics;
    }

    @Override
    public void setMetrics(BlockMetrics metrics) {
        this.metrics = metrics;
    }

    @Override
    public boolean isTestClass() {
        return testClass;
    }

    @Override
    @NotNull
    public List<? extends ClassInfo> getClasses() {
        throw new UnsupportedOperationException("Use FullClassInfo");
    }

    @Override
    @NotNull
    public List<? extends ClassInfo> getAllClasses() {
        throw new UnsupportedOperationException("Use FullClassInfo");
    }

    @Override
    @NotNull
    public List<? extends MethodInfo> getMethods() {
        throw new UnsupportedOperationException("Use FullClassInfo");
    }

    @Override
    @NotNull
    public List<? extends MethodInfo> getAllMethods() {
        throw new UnsupportedOperationException("Use FullClassInfo");
    }

    @Override
    @NotNull
    public List<? extends StatementInfo> getStatements() {
        throw new UnsupportedOperationException("Use FullClassInfo");
    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException("Use FullClassInfo");
    }

    @Override
    public ContextSet getContextFilter() {
        return getContainingFile().getContextFilter();
    }
    
    public String toString() {
        return "["+ getQualifiedName() + ",test="+isTestClass()+",if="+isInterface()+",enum="+isEnum()+",anno="+isAnnotationType()+"]";
    }

    @Override
    public int getAggregatedComplexity() {
        throw new UnsupportedOperationException("Use FullClassInfo");
    }

    @Override
    public void setAggregatedComplexity(int aggregatedComplexity) {
        throw new UnsupportedOperationException("Use FullClassInfo");
    }

    @Override
    public int getAggregatedStatementCount() {
        throw new UnsupportedOperationException("Use FullClassInfo");
    }

    @Override
    public void setAggregatedStatementCount(int aggregatedStatements) {
        throw new UnsupportedOperationException("Use FullClassInfo");
    }

    /**
     * Visit yourself
     *
     * @param entityVisitor callback
     */
    @Override
    public void visit(EntityVisitor entityVisitor) {
        entityVisitor.visitClass(this);
    }

}

package com.atlassian.clover.reporters.html

import clover.org.apache.velocity.VelocityContext
import com.atlassian.clover.TestUtils
import com.atlassian.clover.registry.metrics.BlockMetrics
import com.atlassian.clover.registry.metrics.HasMetricsTestFixture
import com.atlassian.clover.registry.entities.FullProjectInfo
import com.atlassian.clover.reporters.Column
import com.atlassian.clover.reporters.Current
import com.atlassian.clover.reporters.util.CloverChartFactory
import junit.framework.TestCase

class RenderActionTest extends TestCase {

    VelocityContext context
    Current config
    HasMetricsTestFixture fixture
    File basePath
    
    void setUp() throws IOException {
        context = new VelocityContext()
        basePath = TestUtils.createEmptyDirFor(getClass(), getName())
        String initStr = basePath.getAbsolutePath() + "/clover.db"

        config = HtmlReporter.processArgs([
                "-i", initStr, "-o", basePath.getAbsolutePath(), "--style", "adg" ] as String[])
        fixture = new HasMetricsTestFixture("Render File Action Test")

    }

    void testInsertDashboardProperties() throws Exception {

        CloverChartFactory.ChartInfo chartInfo = new CloverChartFactory.ChartInfo(null, null, 0, null, null)
        RenderDashboardAction action =
                new RenderDashboardAction(context, basePath, fixture.getProject(), fixture.getProject(), chartInfo, chartInfo, config)
        action.insertDashboardProperties()

        assertTrue(context.get("tpcColumn") instanceof Column)
        assertTrue(context.get("hasResults") instanceof Boolean)
        assertTrue(context.get("projectInfo") instanceof FullProjectInfo)
        assertTrue(context.get("headerMetrics") instanceof BlockMetrics)
        assertTrue(context.get("headerMetricsRaw") instanceof BlockMetrics)
        assertTrue(context.get("complexPkgs") instanceof List)
        assertTrue(context.get("complexClasses") instanceof List)
        assertTrue(context.get("leastTestedMethods") instanceof List)
        assertTrue(context.get("currentPageURL") instanceof String)
        assertTrue(context.get("topRisks") instanceof List)
        assertTrue(context.get("axisColor") instanceof StatisticsClassInfoVisitor)
        assertTrue(context.get("axisSize") instanceof StatisticsClassInfoVisitor)
        assertTrue(context.get("chartInfoHistogram") instanceof CloverChartFactory.ChartInfo)
        assertTrue(context.get("chartInfoScatter") instanceof CloverChartFactory.ChartInfo)
    }

    void testInsertCoverageCloudProperties() throws Exception {
        fixture.newClass("TestClass", 2)
        RenderProjectCoverageCloudsAction action = new RenderProjectCoverageCloudsAction(
                context, config,
                basePath,
                new HtmlReporter.TreeInfo("", "AppCloud"),
                fixture.getProject())
        action.call()

        assertTrue(context.get("currentPageURL") instanceof String)
        assertTrue(context.get("tabs") instanceof List)
        assertEquals(2, ((List)context.get("tabs")).size())
        assertTrue(((List)context.get("tabs")).get(0) instanceof TabInfo)
        assertTrue(((List)context.get("tabs")).get(1) instanceof TabInfo)
        assertTrue(context.get("axis1") instanceof StatisticsClassInfoVisitor)
        assertTrue(context.get("axis2") instanceof StatisticsClassInfoVisitor)
        assertTrue(context.get("title") instanceof String)
    }

}

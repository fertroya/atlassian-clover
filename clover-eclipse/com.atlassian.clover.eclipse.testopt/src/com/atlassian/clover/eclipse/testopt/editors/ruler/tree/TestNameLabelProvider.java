package com.atlassian.clover.eclipse.testopt.editors.ruler.tree;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.graphics.Image;

import com.atlassian.clover.eclipse.core.CloverPlugin;
import com.atlassian.clover.eclipse.core.ui.CloverPluginIcons;
import com.atlassian.clover.eclipse.core.views.nodes.Nodes;
import com.atlassian.clover.registry.entities.TestCaseInfo;

public class TestNameLabelProvider extends ColumnLabelProvider {

    @Override
    public String getText(Object element) {
        return element instanceof TestCaseInfo ? ((TestCaseInfo)element).getTestName() : element.toString();
    }
    
    @Override
    public Image getImage(Object element) {
        if (element instanceof TestCaseInfo) {
            final TestCaseInfo tci = (TestCaseInfo) element;
            return Nodes.iconFor(tci);
        } else {
            return CloverPlugin.getImage(CloverPluginIcons.TEST_CLASS_ICON);
        }
    }
}

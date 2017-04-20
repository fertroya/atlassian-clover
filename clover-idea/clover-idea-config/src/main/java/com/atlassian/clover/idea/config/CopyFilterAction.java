package com.atlassian.clover.idea.config;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.project.Project;

public class CopyFilterAction extends AnAction {


    @Override
    public void actionPerformed(AnActionEvent event) {

        // add new context to the model.
        Project project = DataKeys.PROJECT.getData(event.getDataContext());
        if (project != null) {
            IdeaRegexpConfigPanel panel = IdeaRegexpConfigPanel.getInstance(project);
            if (panel != null) {
                panel.doCopy();
            }
        }
    }

    @Override
    public void update(AnActionEvent event) {
        Project project = DataKeys.PROJECT.getData(event.getDataContext());
        if (project != null) {
            IdeaRegexpConfigPanel panel = IdeaRegexpConfigPanel.getInstance(project);
            event.getPresentation().setEnabled(panel != null && panel.isEnabled() && panel.getModel().getSelected() != null);
        } else {
            event.getPresentation().setEnabled(false);
        }
    }


}
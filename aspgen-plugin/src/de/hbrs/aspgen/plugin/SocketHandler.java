package de.hbrs.aspgen.plugin;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;

public class SocketHandler extends AbstractHandler {
    private DirectoryRefresher directoryRefresher = null;

    @Override
    public Object execute(final ExecutionEvent event) throws ExecutionException {
        final Shell shell = HandlerUtil.getActiveShell(event);
        final ISelection sel = HandlerUtil.getActiveMenuSelection(event);
        final IStructuredSelection selection = (IStructuredSelection) sel;
        final Set<IProject> projects = new HashSet<IProject>();
        for (final Object element : selection.toList()) {
            if (element instanceof IJavaProject) {
                final IJavaProject javaProject = (IJavaProject) element;
                projects.add(javaProject.getProject());
            }
            ResourcesPlugin.getWorkspace().getRoot().getProjects();
        }
        final InputDialog dialog = new InputDialog(shell,"Conntect to aspgen",
                "Enter port of aspgen notification bundle (default 9255):" , "9255", null);

        if( dialog.open()== IStatus.OK){
            connectToServer(shell, projects, dialog);

        } else {
            showErrorMessage(shell);
        }
        return null;
    }

    private void connectToServer(final Shell shell,
            final Set<IProject> projects, final InputDialog dialog) {
        final String value = dialog.getValue();
        final int port = Integer.valueOf(value);
        Socket connection = null;
        try {
            connection = new Socket("localhost", port);
        } catch (final UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
        if (connection != null) {
            if (directoryRefresher == null) {
                directoryRefresher = new DirectoryRefresher(connection, projects);
                directoryRefresher.startListening();
            } else {
                if (directoryRefresher.isRunning()) {
//                    directoryRefresher.refreshConnection(connection);
                    directoryRefresher.addProjects(projects);
                } else {
                    directoryRefresher = new DirectoryRefresher(connection, projects);
                    directoryRefresher.startListening();
                }
            }
            final MessageBox box = new MessageBox(shell, SWT.ICON_INFORMATION);
            box.setMessage("Success");
            box.open();
        } else {
            showErrorMessage(shell);
        }
    }

    private void showErrorMessage(final Shell shell) {
        final MessageBox box = new MessageBox(shell, SWT.ICON_ERROR);
        box.setMessage("Connection failed");
        box.open();
    }

}

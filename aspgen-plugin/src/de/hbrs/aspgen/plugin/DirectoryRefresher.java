package de.hbrs.aspgen.plugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

public class DirectoryRefresher extends Thread {
    private Socket connection;
    private final Set<IProject> projects;
    private volatile boolean running = false;

    public DirectoryRefresher(final Socket connection, final Set<IProject> projects) {
        this.connection = connection;
        this.projects = projects;
    }

    public void startListening() {
        running = true;
        this.start();
    }

    @Override
    public void run() {
        while(running) {
            try{
                receiveMessage();
            } catch (final IOException e) {
                handleDisconnect();
            }
        }
    }

    private void receiveMessage() throws IOException {
        final String updateDir;
        BufferedReader in;
        in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        updateDir = in.readLine().replace("\\", "/");
        for (final IProject project : projects) {
            final String projectDir = project.getLocation().toPortableString();

            if (updateDir.startsWith(projectDir)) {
                updateProjectFolder(updateDir, project, projectDir);
            }

        }
    }

    private void updateProjectFolder(final String updateDir,
            final IProject project, final String projectDir) {
        boolean couldUpdateFolder = false;
        String folderToUpdate;
        folderToUpdate = updateDir.replace(projectDir, "");
        folderToUpdate.replaceFirst("/*", "");
        if (project.getFolder(folderToUpdate).exists()) {
            try {
                project.getFolder(folderToUpdate).refreshLocal(IResource.DEPTH_ONE, null);
                couldUpdateFolder = true;
            } catch (final CoreException e) {
                couldUpdateFolder = false;
            }
        }
        if (!couldUpdateFolder) {
            try {
                project.refreshLocal(IResource.DEPTH_INFINITE, null);
            } catch (final CoreException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleDisconnect() {
        running = false;
        try {
            connection.close();
        } catch (final IOException e) {
            e.printStackTrace();
        }
        PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
            @Override
            public void run() {
                final Shell activeShell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
                final MessageBox box = new MessageBox(activeShell, SWT.ICON_WARNING);
                box.setMessage("Connection was closed");
                box.open();
            }
        });
    }

    public void addProjects(final Set<IProject> projects) {
        this.projects.addAll(projects);
    }

    public void refreshConnection(final Socket newConnection) {
        if (connection.isClosed()) {
            connection = newConnection;
        }
    }

    public boolean isRunning() {
        return running;
    }
}

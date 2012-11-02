package de.hbrs.aspgen.notification.server;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

import org.osgi.service.log.LogService;

import de.hbrs.aspgen.api.notification.PluginNotifierService;

public class NotificationServer extends Thread implements PluginNotifierService {
    private static final int DEFAULT_PORT = 9255;
    private final Set<Socket> sockets = new HashSet<>();
    private boolean isListening = false;
    private final LogService logService;

    public NotificationServer(final LogService logService) {
        this.logService = logService;
    }

    public void startServer() {
        isListening = true;
        this.start();
    }

    public void sendSocketsMsg(final String msg) {
        final Set<Socket> closedSockets = new HashSet<>();
        for (final Socket socket : sockets) {
            if (!socket.isClosed()) {
                sendMsgToSocket(msg, socket);
            } else {
                closedSockets.add(socket);
            }
        }
        removeClosedSockets(closedSockets);
    }

    private void removeClosedSockets(final Set<Socket> closedSockets) {
        for (final Socket closedSocket : closedSockets) {
            sockets.remove(closedSocket);
        }
    }

    private void sendMsgToSocket(final String msg, final Socket socket) {
        PrintWriter out = null;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println(msg);
            logService.log(LogService.LOG_DEBUG, "notify plugin " + socket + " to refresh folder " + msg);
        } catch (final IOException e) {
            logService.log(LogService.LOG_INFO, "Error while sending message" + e);
            sockets.remove(socket);
        }
    }

    @Override
    public void run() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(DEFAULT_PORT);
        } catch (final IOException e) {
            throw new RuntimeException("Error while create Server Socket");
        }
        while(isListening) {
            try {
                final Socket connection = serverSocket.accept();
                sockets.add(connection);
                logService.log(LogService.LOG_DEBUG, "add plugin " + connection.toString());
            } catch (final IOException e) {
                logService.log(LogService.LOG_INFO, "Error while plugin trying to connect" + e);
            }
        }
        try {
            serverSocket.close();
        } catch (final IOException e) {
            logService.log(LogService.LOG_INFO, "Error while stopping server" + e);
        }
    }

    @Override
    public void updateFolder(final String path) {
        sendSocketsMsg(path);
    }

    @Override
    public void updateFolder(final File path) {
        updateFolder(path.getAbsolutePath());
    }
}

package com.tiknil.boilerplate.model.events;

/**
 * Evento che viene lanciato quando cambia la connessione a internet
 *
 * @TiKnil
 */
public class InternetConnectionChangeEvent {

    private boolean connected = false;

    public InternetConnectionChangeEvent(boolean connected) {
        this.connected = connected;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }
}

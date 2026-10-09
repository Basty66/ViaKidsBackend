package com.viakids.backend.notification;

public class PresetNotificationRequest {
    private String presetKey;
    private String presetLabel;
    private String mensaje;
    private String tipo;
    private String ruta;
    private String senderName;
    private String senderRole;
    private String targetRoles;

    public String getPresetKey() { return presetKey; }
    public void setPresetKey(String presetKey) { this.presetKey = presetKey; }
    public String getPresetLabel() { return presetLabel; }
    public void setPresetLabel(String presetLabel) { this.presetLabel = presetLabel; }
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getRuta() { return ruta; }
    public void setRuta(String ruta) { this.ruta = ruta; }
    public String getSenderName() { return senderName; }
    public void setSenderName(String senderName) { this.senderName = senderName; }
    public String getSenderRole() { return senderRole; }
    public void setSenderRole(String senderRole) { this.senderRole = senderRole; }
    public String getTargetRoles() { return targetRoles; }
    public void setTargetRoles(String targetRoles) { this.targetRoles = targetRoles; }

    public Notification.NotificationType getTipoEnum() {
        if (tipo == null) return Notification.NotificationType.INFO;
        try { return Notification.NotificationType.valueOf(tipo.toUpperCase()); }
        catch (IllegalArgumentException e) { return Notification.NotificationType.INFO; }
    }
}

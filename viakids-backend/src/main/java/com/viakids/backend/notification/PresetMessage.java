package com.viakids.backend.notification;

import java.util.List;

public class PresetMessage {

    private String key;
    private String label;
    private String mensaje;
    private Notification.NotificationType tipo;
    private String targetRoles;
    private String icon;

    public PresetMessage(String key, String label, String mensaje,
                          Notification.NotificationType tipo,
                          String targetRoles, String icon) {
        this.key = key;
        this.label = label;
        this.mensaje = mensaje;
        this.tipo = tipo;
        this.targetRoles = targetRoles;
        this.icon = icon;
    }

    public String getKey() { return key; }
    public String getLabel() { return label; }
    public String getMensaje() { return mensaje; }
    public Notification.NotificationType getTipo() { return tipo; }
    public String getTargetRoles() { return targetRoles; }
    public String getIcon() { return icon; }

    public static final List<PresetMessage> DEFAULT_PRESETS = List.of(
        new PresetMessage("ruta_iniciada", "Ruta Iniciada",
            "La ruta ha comenzado. Todos los estudiantes a bordo.",
            Notification.NotificationType.INFO, "PARENT,ADMIN", "Play"),

        new PresetMessage("llegando_colegio", "Llegando al Colegio",
            "Estamos llegando al colegio. Todo en orden.",
            Notification.NotificationType.INFO, "PARENT,ADMIN", "MapPin"),

        new PresetMessage("ruta_completada", "Ruta Completada",
            "Ruta completada exitosamente. Todos los estudiantes dejados en sus destinos.",
            Notification.NotificationType.INFO, "PARENT,ADMIN", "CheckCircle2"),

        new PresetMessage("retraso_5", "Retraso 5 min",
            "Estimados apoderados, presentaremos un retraso aproximado de 5 minutos debido al tráfico.",
            Notification.NotificationType.ALERTA, "PARENT,ADMIN", "Clock"),

        new PresetMessage("retraso_10", "Retraso 10 min",
            "Estimados apoderados, presentaremos un retraso aproximado de 10 minutos debido al tráfico.",
            Notification.NotificationType.ALERTA, "PARENT,ADMIN", "Clock"),

        new PresetMessage("retraso_15", "Retraso 15+ min",
            "Estimados apoderados, presentaremos un retraso significativo. Estaremos informando.",
            Notification.NotificationType.URGENTE, "PARENT,ADMIN", "AlertTriangle"),

        new PresetMessage("emergencia", "Emergencia",
            "⚠️ Emergencia en la ruta. Contactando a emergencias. Manténganse atentos.",
            Notification.NotificationType.URGENTE, "ADMIN", "AlertCircle"),

        new PresetMessage("clima_adverso", "Clima Adverso",
            "Condiciones climáticas adversas. Conduciendo con precaución. Posibles retrasos.",
            Notification.NotificationType.ALERTA, "PARENT,ADMIN", "Cloud"),

        new PresetMessage("averia_menor", "Avería Menor",
            "Avería menor detectada, siendo resuelta. No hay retraso significativo.",
            Notification.NotificationType.ALERTA, "ADMIN", "Wrench"),

        new PresetMessage("todo_ok", "Todo en Orden",
            "Todo transcurriendo con normalidad. Sin novedades.",
            Notification.NotificationType.INFO, "PARENT,ADMIN", "ThumbsUp")
    );
}

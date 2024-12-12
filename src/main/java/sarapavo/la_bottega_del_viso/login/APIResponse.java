package sarapavo.la_bottega_del_viso.login;

public record APIResponse<T>(
        Enum status,
        T data,
        String message
) {
}

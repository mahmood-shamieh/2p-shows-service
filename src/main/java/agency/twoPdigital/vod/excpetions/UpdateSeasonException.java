package agency.twoPdigital.vod.excpetions;

public class UpdateSeasonException extends MyException {
    public UpdateSeasonException() {
        super("Error Updating Season");
    }

    public UpdateSeasonException(Object data) {
        super(data, "Error Updating Season");
    }
}


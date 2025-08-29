package agency.twoPdigital.vod.excpetions;

public class CreateSeasonException extends MyException {
    public CreateSeasonException() {
        super("Error Create Season");
    }

    public CreateSeasonException(Object data) {
        super(data, "Error Create Season");
    }
}


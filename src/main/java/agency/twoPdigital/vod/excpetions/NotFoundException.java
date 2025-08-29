package agency.twoPdigital.vod.excpetions;

public class NotFoundException extends MyException {
    public NotFoundException() {
        super("Element Not Found");
    }

    public NotFoundException(Object data) {
        super(data, "Element Not Found");
    }
}


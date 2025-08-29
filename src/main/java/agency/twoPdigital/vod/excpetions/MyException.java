package agency.twoPdigital.vod.excpetions;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class MyException extends Exception{
    private Object data ;
    public MyException(Object data) {
        super();
        this.data = data;
    }
    public MyException(Object data,String message) {
        super(message);
        this.data = data;
    }
    public MyException() {
        super();
    }
    public MyException(String message) {
        super(message);
    }

}

package local.vda.votingsystem.util.exception;

import org.springframework.http.HttpStatus;

public class VoteTimeException extends ApplicationException {
    public static final String EXCEPTION_TIME_END_VOTING = "exception.common.timeEndVoting";

    public VoteTimeException() {
        super(ErrorType.APP_ERROR, EXCEPTION_TIME_END_VOTING, HttpStatus.NOT_ACCEPTABLE);
    }
}

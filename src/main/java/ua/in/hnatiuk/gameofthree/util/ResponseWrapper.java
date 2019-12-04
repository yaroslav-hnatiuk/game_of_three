package ua.in.hnatiuk.gameofthree.util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Builder
@Getter
public class ResponseWrapper {
    @JsonProperty("message")
    private String message;
    @JsonProperty("initial_value")
    private long initialValue;
    @JsonProperty("current_value")
    private long currentValue;
    @JsonProperty("winner")
    private UserType winner;
    @JsonIgnore
    private int status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseWrapper that = (ResponseWrapper) o;
        return initialValue == that.initialValue &&
                currentValue == that.currentValue &&
                status == that.status &&
                message.equals(that.message) &&
                winner == that.winner;
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, initialValue, currentValue, winner, status);
    }
}

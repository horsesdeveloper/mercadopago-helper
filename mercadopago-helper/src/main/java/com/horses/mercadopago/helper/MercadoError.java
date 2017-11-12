package com.horses.mercadopago.helper;

import java.io.Serializable;
import java.util.List;

/**
 * @author @briansalvattore on 9/11/2017.
 */

@SuppressWarnings("unused")
public class MercadoError implements Serializable {

    private String message;
    private String error;
    private int status;
    private List<CauseError> cause;

    public class CauseError {
        private String code;
        private String description;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        @Override
        public String toString() {
            return "CauseError{" +
                    "code='" + code + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<CauseError> getCause() {
        return cause;
    }

    public void setCause(List<CauseError> cause) {
        this.cause = cause;
    }

    @Override
    public String toString() {
        return "MercadoError{" +
                "message='" + message + '\'' +
                ", error='" + error + '\'' +
                ", status=" + status +
                ", cause=" + cause.toString() +
                '}';
    }
}

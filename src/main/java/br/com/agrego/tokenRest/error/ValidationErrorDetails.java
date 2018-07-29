package br.com.agrego.tokenRest.error;

public class ValidationErrorDetails extends ErrorDetails {
    private String field;
    private String fieldMessage;

    public String getField() {
        return field;
    }

    public String getFieldMessage() {
        return fieldMessage;
    }
    
    public static final class Builder extends ErrorDetails.Builder {

        private String field;
        private String fieldMessage;

        public static Builder newBuilder() {
            return new Builder();
        }
        public Builder title(String title) {
            super.title(title);
            return this;
        }
        public Builder status(int status) {
            super.status(status);
            return this;
        }
        public Builder detail(String detail) {
            super.detail(detail);
            return this;
        }
        public Builder timestamp(long timestamp) {
            super.timestamp(timestamp);
            return this;
        }
        public Builder exception(String exception) {
            super.exception(exception);
            return this;
        }
        public Builder path(String path) {
            super.path(path);
            return this;
        }
        public Builder method(String method) {
            super.method(method);
            return this;
        }
        
        public Builder field(String field) {
            this.field = field;
            return this;
        }
        public Builder fieldMessage(String fieldMessage) {
            this.fieldMessage = fieldMessage;
            return this;
        }
        @Override
        public ValidationErrorDetails build() {
            ValidationErrorDetails validationErrorDetails = new ValidationErrorDetails();
            validationErrorDetails.setTitle(super.title);
            validationErrorDetails.setStatus(super.status);
            validationErrorDetails.setDetail(super.detail);
            validationErrorDetails.setTimestamp(super.timestamp);
            validationErrorDetails.setException(super.exception);
            validationErrorDetails.setPath(super.path);
            validationErrorDetails.setMethod(super.method);
            validationErrorDetails.fieldMessage = fieldMessage;
            validationErrorDetails.field = field;
            return validationErrorDetails;
        }
    }
}

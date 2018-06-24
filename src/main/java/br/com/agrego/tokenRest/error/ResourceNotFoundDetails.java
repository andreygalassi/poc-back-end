package br.com.agrego.tokenRest.error;

public class ResourceNotFoundDetails extends ErrorDetails {

	public static final class Builder extends ErrorDetails.Builder {
        private Builder() {
        }

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
        
        public ResourceNotFoundDetails build() {
            ResourceNotFoundDetails resourceNotFoundDetails = new ResourceNotFoundDetails();
            return resourceNotFoundDetails;
        }
    }
}

package br.com.agrego.tokenRest.error;

import br.com.agrego.tokenRest.error.ResourceNotFoundDetails.Builder;

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
		public Builder method(String method) {
            super.method(method);
            return this;
		}
        
        public ResourceNotFoundDetails build() {
            ResourceNotFoundDetails resourceNotFoundDetails = new ResourceNotFoundDetails();
            
            resourceNotFoundDetails.setTitle(super.title);
            resourceNotFoundDetails.setStatus(super.status);
            resourceNotFoundDetails.setDetail(super.detail);
            resourceNotFoundDetails.setTimestamp(super.timestamp);
            resourceNotFoundDetails.setException(super.exception);
            resourceNotFoundDetails.setPath(super.path);
            resourceNotFoundDetails.setMethod(super.method);
            
            return resourceNotFoundDetails;
        }
    }
}

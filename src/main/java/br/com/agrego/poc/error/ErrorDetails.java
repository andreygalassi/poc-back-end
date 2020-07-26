package br.com.agrego.poc.error;

// Padronizando os detalhes das exceptions
public class ErrorDetails {
	
    private String title;
    private int status;
    private String detail;
    private long timestamp;
    private String data;
    private String classe;
    private String exception;
    private String path;
    private String method;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    
    public static class Builder {
        protected String title;
        protected int status;
        protected String detail;
        protected long timestamp;
        protected String exception;
        protected String path;
        protected String method;
        
        protected Builder() {
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder status(int status) {
            this.status = status;
            return this;
        }

        public Builder detail(String detail) {
            this.detail = detail;
            return this;
        }

        public Builder timestamp(long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder exception(String exception) {
            this.exception = exception;
            return this;
        }

        public Builder path(String path) {
            this.path = path;
            return this;
        }
        public Builder method(String method) {
            this.method = method;
            return this;
        }

        public ErrorDetails build() {
            ErrorDetails errorDetails = new ErrorDetails();
            errorDetails.setTitle(title);
            errorDetails.setStatus(status);
            errorDetails.setDetail(detail);
            errorDetails.setTimestamp(timestamp);
            errorDetails.setException(exception);
            errorDetails.setPath(path);
            errorDetails.setMethod(method);
            return errorDetails;
        }
    }

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
}

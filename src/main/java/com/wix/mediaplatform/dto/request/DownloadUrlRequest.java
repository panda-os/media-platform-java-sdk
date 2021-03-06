package com.wix.mediaplatform.dto.request;

public class DownloadUrlRequest {

    private Integer ttl;

    private Attachment attachment;

    private String onExpireRedirectTo;

    public DownloadUrlRequest() {
    }

    public Integer getTtl() {
        return ttl;
    }

    public DownloadUrlRequest setTtl(Integer ttl) {
        this.ttl = ttl;
        return this;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public DownloadUrlRequest setAttachment(Attachment attachment) {
        this.attachment = attachment;
        return this;
    }

    public String getOnExpireRedirectTo() {
        return onExpireRedirectTo;
    }

    public DownloadUrlRequest setOnExpireRedirectTo(String onExpireRedirectTo) {
        this.onExpireRedirectTo = onExpireRedirectTo;
        return this;
    }

}

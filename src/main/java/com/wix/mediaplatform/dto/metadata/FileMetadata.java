package com.wix.mediaplatform.dto.metadata;

import com.wix.mediaplatform.dto.metadata.basic.BasicMetadata;
import com.wix.mediaplatform.dto.metadata.features.Features;

public class FileMetadata {

    private FileDescriptor fileDescriptor;

    private BasicMetadata basic;

    private Features features;

    public FileMetadata() {
    }

    public FileDescriptor getFileDescriptor() {
        return fileDescriptor;
    }

    public BasicMetadata getBasic() {
        return basic;
    }

    public Features getFeatures() {
        return features;
    }
}
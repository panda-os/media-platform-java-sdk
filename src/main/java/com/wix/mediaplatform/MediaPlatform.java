package com.wix.mediaplatform;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wix.mediaplatform.authentication.Authenticator;
import com.wix.mediaplatform.configuration.Configuration;
import com.wix.mediaplatform.dto.job.ExtractArchiveJob;
import com.wix.mediaplatform.dto.job.FileImportJob;
import com.wix.mediaplatform.dto.job.Job;
import com.wix.mediaplatform.dto.job.TranscodeJob;
import com.wix.mediaplatform.dto.metadata.FileMetadata;
import com.wix.mediaplatform.gson.FileMetadataJsonDeserializer;
import com.wix.mediaplatform.gson.RuntimeTypeAdapterFactory;
import com.wix.mediaplatform.http.AuthenticatedHTTPClient;
import com.wix.mediaplatform.management.*;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

public class MediaPlatform {

    private final FileDownloader fileDownloader;
    private final FileManager fileManager;
    private final JobManager jobManager;
    private final ArchiveManager archiveManager;

    public MediaPlatform(String domain, String appId, String sharedSecret, HttpClient httpClient) {
        Configuration configuration = new Configuration(domain, appId, sharedSecret);
        Gson gson = getGson(false);

        Authenticator authenticator = new Authenticator(configuration);
        AuthenticatedHTTPClient authenticatedHTTPClient = new AuthenticatedHTTPClient(authenticator, httpClient, gson);

        FileUploader fileUploader = new FileUploader(configuration, authenticatedHTTPClient);
        this.fileDownloader = new FileDownloader(configuration, authenticator);
        this.fileManager = new FileManager(configuration, authenticatedHTTPClient, fileUploader);
        this.jobManager = new JobManager(configuration, authenticatedHTTPClient);
        this.archiveManager = new ArchiveManager(configuration, authenticatedHTTPClient);
    }

    public MediaPlatform(String domain, String appId, String sharedSecret) {
        this(domain, appId, sharedSecret, getHttpClient());
    }

    public FileDownloader fileDownloader() {
        return fileDownloader;
    }

    public FileManager fileManager() {
        return fileManager;
    }

    public JobManager jobManager() {
        return jobManager;
    }

    public ArchiveManager archiveManager() {
        return archiveManager;
    }

    public static Gson getGson(boolean pretty) {
        GsonBuilder builder = new GsonBuilder()
                .registerTypeAdapter(FileMetadata.class, new FileMetadataJsonDeserializer())
                .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(Job.class, "type")
                        .registerSubtype(FileImportJob.class, FileImportJob.job_type)
                        .registerSubtype(ExtractArchiveJob.class, ExtractArchiveJob.job_type)
                        .registerSubtype(TranscodeJob.class, TranscodeJob.job_type));

        if (pretty) {
            builder.setPrettyPrinting();
        }

        return builder.create();
    }

    protected static HttpClient getHttpClient() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(100);
        connectionManager.setDefaultMaxPerRoute(50);
        return HttpClients.createMinimal(connectionManager);
    }
}

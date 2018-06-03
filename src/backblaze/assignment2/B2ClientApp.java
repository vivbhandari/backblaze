package backblaze.assignment2;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import com.backblaze.b2.client.B2Sdk;
import com.backblaze.b2.client.B2StorageClient;
import com.backblaze.b2.client.contentHandlers.B2ContentFileWriter;
import com.backblaze.b2.client.exceptions.B2Exception;
import com.backblaze.b2.client.structures.B2Bucket;
import com.backblaze.b2.client.structures.B2DownloadByNameRequest;
import com.backblaze.b2.client.structures.B2FileVersion;
import com.backblaze.b2.client.webApiHttpClient.B2StorageHttpClientBuilder;

public class B2ClientApp {

	private static String ACCOUNT_ID = "1914076c668c";
	private static String APPLICATION_KEY = "002d0222e9fcf64d255681f8e9207d423b45ac0a46";

	public static void main(String[] args) throws B2Exception {
		PrintWriter writer = new PrintWriter(System.out, true);

		try (final B2StorageClient client = B2StorageHttpClientBuilder
				.builder(ACCOUNT_ID, APPLICATION_KEY, "Viv").build()) {
			process(writer, client);
		} finally {
		}
	}

	private static void process(PrintWriter writer, B2StorageClient client)
			throws B2Exception {
		writer.println("Running with " + B2Sdk.getName() + " version "
				+ B2Sdk.getVersion());

		// list all buckets
		bigHeader(writer, "List Buckets");
		for (B2Bucket scan : client.buckets()) {
			writer.println(" " + scan);
		}

		String bucketId = client.buckets().get(0).getBucketId();
		// list all file names in first bucket
		bigHeader(writer, "list file names");
		for (B2FileVersion version : client.fileNames(bucketId)) {
			writer.println("fileName: " + version);
		}

		bigHeader(writer, "download all b2 files in first bucket to disk");
		for (B2FileVersion version : client.fileNames(bucketId)) {
			final B2DownloadByNameRequest request = B2DownloadByNameRequest
					.builder("vivBucket1", version.getFileName()).build();

			// create target file in project directory
			File outputFile = new File("downloaded-" + version.getFileName());
			try {
				outputFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}

			final B2ContentFileWriter handler = B2ContentFileWriter
					.builder(outputFile)
					.setVerifySha1ByRereadingFromDestination(true).build();
			client.downloadByName(request, handler);
			writer.println("headers: " + handler.getHeadersOrNull());
		}

	}

	private static void bigHeader(PrintWriter writer, String title) {
		writer.println(
				"########################################################################");
		writer.println("#");
		writer.println("# " + title);
		writer.println("#");
		writer.println(
				"########################################################################");
	}
}

package vn.com.viettel.vds.gitmanagement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import java.io.IOException;

public class CreateRemoteRepository {
    public static void main(String[] args) throws IOException {
        String gitLabApiUrl = "http://gitlab.orc.com/api/v4/projects";
        String gitLabAccessToken = "glpat-d2BBx28oX7tDE3KQyjsx";
        String repositoryName = "new-repository";

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(gitLabApiUrl);

        // Set headers
        httpPost.addHeader("Content-Type", "application/json");
        httpPost.addHeader("PRIVATE-TOKEN", gitLabAccessToken);

        // Set request body
        String requestBody = "{\"name\": \"" + repositoryName + "\"}";
        httpPost.setEntity(new StringEntity(requestBody));

        // Execute the request
        HttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            System.out.println("Repository created successfully!");
        }
        httpClient.close();
    }
}

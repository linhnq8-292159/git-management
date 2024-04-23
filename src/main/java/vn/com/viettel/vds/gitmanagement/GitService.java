package vn.com.viettel.vds.gitmanagement;

import org.gitlab4j.api.models.Project;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class GitService{

    public static void main(String[] args) {

        String baseUrl = "http://gitlab.orc.com/api/v4/";
        String token = "glpat-d2BBx28oX7tDE3KQyjsx";
        String projectName = "test3";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GitApi service = retrofit.create(GitApi.class);
        Call<Project> call = service.createProject(token, new Project().withName(projectName));

        try {
            Response<Project> response = call.execute();
            if (response.isSuccessful()) {
                Project project = response.body();
                assert project != null;
                System.out.println("Project ID: " + project.getHttpUrlToRepo());
            } else {
                System.out.println("Error: " + response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

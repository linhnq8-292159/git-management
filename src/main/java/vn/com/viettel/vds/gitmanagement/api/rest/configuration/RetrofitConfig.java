package vn.com.viettel.vds.gitmanagement.api.rest.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vn.com.viettel.vds.gitmanagement.application.service.GitLabApiService;

@Configuration
public class RetrofitConfig {

    @Value("${gitlab.api.url}")
    private String gitLabApiUrl;

    @Bean
    public Retrofit retrofit() {
        return new Retrofit.Builder()
                .baseUrl(gitLabApiUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Bean
    public GitLabApiService gitLabApiService(Retrofit retrofit) {
        return retrofit.create(GitLabApiService.class);
    }
}

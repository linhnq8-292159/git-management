package vn.com.viettel.vds.gitmanagement.api.rest.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Configuration
public class RetrofitConfig {

    @Bean
    public Retrofit retrofit() {
        return new Retrofit.Builder()
                .baseUrl("http://gitlab.orc.vn/api/v4/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}

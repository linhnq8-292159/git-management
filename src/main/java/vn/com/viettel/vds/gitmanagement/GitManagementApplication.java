package vn.com.viettel.vds.gitmanagement;

import org.eclipse.jgit.api.errors.GitAPIException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;


@SpringBootApplication
@EnableAsync
public class GitManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(GitManagementApplication.class, args);

    }

}

package com.mx.devops;

import com.mx.devops.init.InitStack;
import com.mx.devops.taskdef.TaskDefStack;
import software.amazon.awscdk.App;
import software.amazon.awscdk.Environment;
import software.amazon.awscdk.StackProps;

public class StackApp {
    public static void main(final String[] args) {
        App app = new App();

        new InitStack(app, "MXINITSTACK", StackProps.builder()
                .stackName("MXINITSTACK")
                .env(Environment.builder()
                        .account(System.getenv("AWS_DEFAULT_ACCOUNT"))
                        .region(System.getenv("AWS_DEFAULT_REGION"))
                        .build()
                )
                .build()
        );

        new TaskDefStack(app, "MXTASKDEFSTACK", StackProps.builder()
                .stackName("MXTASKDEFSTACK")
                .env(Environment.builder()
                        .account(System.getenv("AWS_DEFAULT_ACCOUNT"))
                        .region(System.getenv("AWS_DEFAULT_REGION"))
                        .build()
                )
                .build()
        );

        app.synth();
    }
}


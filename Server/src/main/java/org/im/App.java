package org.im;

import org.im.context.SpringApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class App
{
    public static void main( String[] args ) throws InterruptedException {
        System.out.println( "Hello World!" );
        SpringApplication.run(App.class,args);

        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                SpringApplicationContext.destoryContainer();
            }
        });
    }
}

package web.common.core.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ServerTypeUtils {
    public static String serverType;

    @Value("${spring.profiles.active}")
    public void setServerType(String active){
        serverType = active;
    }

    public static Boolean isProd(){
        return serverType.equals("prod");
    }

    public static Boolean isLocal(){
        return serverType.equals("local");
    }

    public static Boolean isDev(){
        return serverType.equals("dev");
    }

    public static Boolean isTest(){
        return serverType.equals("test");
    }
}

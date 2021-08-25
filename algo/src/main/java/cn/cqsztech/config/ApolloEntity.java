package cn.cqsztech.config;

import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * ccmars
 * 2021/8/25
 **/
@Component
public class ApolloEntity {
    @Value("${times:100}")
    private int times;

    public int getTimes() {
        return times;
    }
}

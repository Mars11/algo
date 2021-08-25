package apollo;

import cn.cqsztech.config.ApolloConfig;
import cn.cqsztech.config.ApolloEntity;
import com.ctrip.framework.apollo.Config;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * ccmars
 * 2021/8/25
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:/application*.xml" })
public class ApolloConfigTest {
    @Resource
    ApolloConfig apolloConfig;
    @Test
    public void testConfig(){
        Config config = apolloConfig.getConfig();
        while (true){
            Integer intProperty = config.getIntProperty("times", 1221);
            System.out.println(intProperty);
            if (intProperty != 1221 )
                break;
        }

    }

    @Resource
    ApolloEntity apolloEntity;
    @Test
    public void testDirectUse(){
        System.out.println(apolloEntity.getTimes());
    }
}

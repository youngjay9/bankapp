package sample.spring.bankapp.aop;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan("sample.spring.bankapp")
@EnableAspectJAutoProxy // 開啟AspectJ功能
public class AopConfig {

}

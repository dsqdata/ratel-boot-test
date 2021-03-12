package org.ratel.config;

import org.jasig.cas.client.proxy.ProxyGrantingTicketStorageImpl;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.validation.Cas20ServiceTicketValidator;
import org.ratel.framework.annotation.AnonymousAccess;
import org.ratel.modules.security.config.RatelAuthenticationProvider;
import org.ratel.modules.security.config.RatelTokenConfigurer;
import org.ratel.modules.security.handler.JwtAccessDeniedHandler;
import org.ratel.modules.security.handler.JwtAuthenticationEntryPoint;
import org.ratel.modules.security.service.TokenProviderService;
import org.ratel.modules.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.authentication.CasAuthenticationProvider;
import org.springframework.security.cas.web.CasAuthenticationEntryPoint;
import org.springframework.security.cas.web.CasAuthenticationFilter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class RatelCasBootSecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenProviderService tokenProviderService;
    private final CorsFilter corsFilter;
    private final JwtAuthenticationEntryPoint authenticationErrorHandler;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final ApplicationContext applicationContext;

    /**
     * 应用登陆地址
     */
    @Value("${ratel.modules.security.cas-client.cas-client-security}")
    private String casClientSecurity;

    @Value("${ratel.modules.security.cas-client.cas-authentication-key}")
    private String casAuthenticationKey;

    @Value("${ratel.modules.security.cas-client.cas-default-target-enabled}")
    private boolean casDefaultTargetEnabled;

    /**
     * Cas认证成功后应用跳转地址
     */
    @Value("${ratel.modules.security.cas-client.cas-default-target-url}")
    private String casDefaultTargetUrl;

    /**
     * 是否启动Cas单点集成配置
     */
    @Value("${ratel.modules.security.cas-client.cas-enabled}")
    private boolean casEnabled;

    /**
     * Cas服务地址
     */
    @Value("${ratel.modules.security.cas-client.cas-server}")
    private String casServer;

    /**
     * Cas服务登陆地址
     */
    @Value("${ratel.modules.security.cas-client.cas-server-login}")
    private String casServerLogin;

    /**
     * Cas服务登出地址
     */
    @Value("${ratel.modules.security.cas-client.cas-server-logout}")
    private String casServerLogout;


    /**
     * 登录成功返回的 JSON 格式数据给前端（否则为 html）
     */
    @Autowired
    RatelAuthenticationSuccessHandler authenticationSuccessHandler;


    public RatelCasBootSecurityConfig(TokenProviderService tokenProviderService,
                                      CorsFilter corsFilter,
                                      JwtAuthenticationEntryPoint authenticationErrorHandler,
                                      JwtAccessDeniedHandler jwtAccessDeniedHandler,
                                      ApplicationContext applicationContext) {
        this.tokenProviderService = tokenProviderService;
        this.corsFilter = corsFilter;
        this.authenticationErrorHandler = authenticationErrorHandler;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
        this.applicationContext = applicationContext;

    }


    @Bean
    GrantedAuthorityDefaults grantedAuthorityDefaults() {
        // 去除 ROLE_ 前缀
        return new GrantedAuthorityDefaults("");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 密码加密方式
        return new BCryptPasswordEncoder();
        //ldap加密方式
//        return  new SSHAPasswordEncoder();
    }

    @Bean
    public ServiceProperties serviceProperties() {
        ServiceProperties serviceProperties = new ServiceProperties();
        serviceProperties.setService(casClientSecurity);
        serviceProperties.setSendRenew(false);
        return serviceProperties;
    }

    @Bean
    public CasAuthenticationEntryPoint casAuthenticationEntryPoint() {
        CasAuthenticationEntryPoint casAuthenticationEntryPoint = new CasAuthenticationEntryPoint();
        casAuthenticationEntryPoint.setLoginUrl(casServerLogin);
        casAuthenticationEntryPoint.setServiceProperties(serviceProperties());
        return casAuthenticationEntryPoint;
    }


    @Bean
    public ProxyGrantingTicketStorageImpl pgtStorage() {
        return new ProxyGrantingTicketStorageImpl();
    }

    @Bean
    public CasAuthenticationFilter casAuthenticationFilter() throws Exception {
        CasAuthenticationFilter casAuthenticationFilter = new CasAuthenticationFilter();
        casAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
        casAuthenticationFilter.setProxyGrantingTicketStorage(pgtStorage());
        if (casDefaultTargetEnabled) {
            casAuthenticationFilter.setAuthenticationSuccessHandler(new SimpleUrlAuthenticationSuccessHandler(casDefaultTargetUrl));
        } else {
            casAuthenticationFilter.setAuthenticationSuccessHandler(this.authenticationSuccessHandler);
        }
        return casAuthenticationFilter;
    }

    @Bean
    public LogoutFilter logoutFilter() {
        LogoutFilter lougoutFilter = new LogoutFilter(casServerLogout, lougoutHandler());
        lougoutFilter.setFilterProcessesUrl("/logout/cas");
        return lougoutFilter;
    }

    @Bean
    public SingleSignOutFilter singleSignOutFilter() {
        SingleSignOutFilter singleSignOutFilter = new SingleSignOutFilter();
        singleSignOutFilter.setLogoutCallbackPath(this.casServerLogout);
        singleSignOutFilter.setIgnoreInitConfiguration(true);
        return singleSignOutFilter;
    }

    @Bean
    public SecurityContextLogoutHandler lougoutHandler() {
        return new SecurityContextLogoutHandler();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new RatelAuthenticationProvider();
    }

    @Bean
    public Cas20ServiceTicketValidator cas20ServiceTicketValidator() {
        return new Cas20ServiceTicketValidator(casServer);
    }

    @Bean
    public CasAuthenticationProvider casAuthenticationProvider() {
        CasAuthenticationProvider casAuthenticationProvider = new CasAuthenticationProvider();
        casAuthenticationProvider.setUserDetailsService(applicationContext.getBean(SysUserService.class));
        casAuthenticationProvider.setServiceProperties(serviceProperties());
        casAuthenticationProvider.setTicketValidator(cas20ServiceTicketValidator());
        casAuthenticationProvider.setKey(casAuthenticationKey);
        return casAuthenticationProvider;
    }


    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        if (this.casEnabled) {// Cas登陆认证
            authenticationManagerBuilder.authenticationProvider(casAuthenticationProvider());
        } else {
            authenticationManagerBuilder.authenticationProvider(authenticationProvider());
        }
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // 搜寻匿名标记 url： @AnonymousAccess
        Map<RequestMappingInfo, HandlerMethod> handlerMethodMap = applicationContext.getBean("requestMappingHandlerMapping", RequestMappingHandlerMapping.class).getHandlerMethods();
        Set<String> anonymousUrls = new HashSet<>();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> infoEntry : handlerMethodMap.entrySet()) {
            HandlerMethod handlerMethod = infoEntry.getValue();
            AnonymousAccess anonymousAccess = handlerMethod.getMethodAnnotation(AnonymousAccess.class);
            if (null != anonymousAccess) {
                anonymousUrls.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
            }
        }

        httpSecurity
                // 禁用 CSRF
                .csrf().disable();
        if (this.casEnabled) {
            httpSecurity
                    .exceptionHandling()
                    .authenticationEntryPoint(casAuthenticationEntryPoint())
                    .and()
                    .addFilter(casAuthenticationFilter())
                    .addFilterBefore(singleSignOutFilter(), CasAuthenticationFilter.class)
                    .addFilterBefore(logoutFilter(), LogoutFilter.class);
        } else {
            httpSecurity
                    // 授权异常
                    .exceptionHandling()
                    .authenticationEntryPoint(authenticationErrorHandler)
                    .accessDeniedHandler(jwtAccessDeniedHandler).and();
        }
        httpSecurity.addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class);
        // 防止iframe 造成跨域
        httpSecurity
                .headers()
                .frameOptions()
                .disable()

                // 不创建会话
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()
                .antMatchers("/index.html").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // 静态资源等等
                .antMatchers(
                        HttpMethod.GET,
                        "/*.html",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/**/*.json",
                        "/component/**",
                        "/src/**",
                        "/webSocket/**"
                ).permitAll()
                // swagger 文档
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/*/api-docs").permitAll()
                .antMatchers("/auth/login").permitAll()
                //
                .antMatchers("/oauth/enterprise/**").permitAll()
                .antMatchers("/oauth/enterpriseUser/**").permitAll()
                //ldap同步用户部门
                .antMatchers("/api/ladp/**").permitAll()
                //qywx同步用户部门
                .antMatchers("/api/qywx/**").permitAll()

                .antMatchers("/api/tool/coupon/*").permitAll()

                .antMatchers("/api/tool/source/*").permitAll()
                .antMatchers("/api/tool/file/*").permitAll()
                .antMatchers("/auth/login").permitAll()
                .antMatchers("/vx/login").permitAll()
                .antMatchers("/oauth/token").permitAll()
                .antMatchers("/oauth/authorize").permitAll()
                .antMatchers("/webSocket/*").permitAll()
                .antMatchers("/api/wx/auth").permitAll()
                .antMatchers("/api/wx/auth/toutiao").permitAll()
                .antMatchers("/api/wx/auth/common").permitAll()
                .antMatchers("/api/wx/auth/qq").permitAll()
                .antMatchers("/api/wx/userSetting/getUsers").permitAll()
                // 文件
                .antMatchers("/static/**").permitAll()
                .antMatchers("/avatar/**").permitAll()
                .antMatchers("/file/**").permitAll()
                // 阿里巴巴 druid
                .antMatchers("/druid/**").permitAll()
                // 放行OPTIONS请求
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // 自定义匿名访问所有url放行 ： 允许匿名和带权限以及登录用户访问
                .antMatchers(anonymousUrls.toArray(new String[0])).permitAll()
                // 所有请求都需要认证
                .anyRequest().authenticated()
                .and().apply(securityConfigurerAdapter());
    }

    private RatelTokenConfigurer securityConfigurerAdapter() {
        return new RatelTokenConfigurer(tokenProviderService);
    }

}

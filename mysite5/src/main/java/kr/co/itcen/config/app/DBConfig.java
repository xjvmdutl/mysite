package kr.co.itcen.config.app;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement//DB설정 같은 경우 이런 설정이 있어야한다,xml에서는 mybatis에서 해준다//aop를 해주기 위해서는 반드시 필요, 없을경우 transactionManger을 받아 쿼리에다 써주어야 한다.
							//transaction은 반드시 service단에다가 적어주어야한다.왜냐하면. 여러개의 쿼리를 실행해주는 트랜젝션이 실패할경우 rollback을 해주어야 하기 떄문에
@PropertySource("classpath:kr/co/itcen/config/app/properties/jdbc.properties")//프라퍼티를 읽어서 이름값이름값으로 해싱
public class DBConfig {
	
	@Autowired
	private Environment env;//프로퍼티로 읽어온 값
	
	@Bean
	public DataSource dataSource() {
		BasicDataSource basicDataSource = new BasicDataSource();
		basicDataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
		basicDataSource.setUrl(env.getProperty("jdbc.url"));
		basicDataSource.setUsername(env.getProperty("jdbc.username"));
		basicDataSource.setPassword(env.getProperty("jdbc.password"));
		basicDataSource.setInitialSize(env.getProperty("jdbc.initialSize",Integer.class));//처음 연결을 몇개를 해줄것이냐
		basicDataSource.setMaxActive(env.getProperty("jdbc.maxActive",Integer.class));//101개가 동시에 들어오면 에러가 난다.
		return basicDataSource;
	}
	@Bean
	public PlatformTransactionManager transactionManager(DataSource dataSource) {//Thread = Transaction = Connection, 같은 Connection이기 때문에 어느곳에서 @Transactional을 써도 되게끔 해준다.
		return new DataSourceTransactionManager(dataSource);
	}
}

import cn.jian.config.SpringMybatisConfig;
import cn.jian.entity.Article;
import cn.jian.entity.User;
import cn.jian.mapper.ArticleMapper;
import cn.jian.service.ArticleService;
import cn.jian.service.ConferenceService;
import cn.jian.service.UserService;
import cn.jian.service.UserfocusService;
import com.alibaba.druid.pool.DruidDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringMybatisConfig.class)
public class TestDao {

    @Autowired
    DruidDataSource dataSource;


    @Autowired
    private ArticleService articleService;

    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    UserfocusService userfocusService;

    @Autowired
    UserService userService;

    @Autowired
    ConferenceService conferenceService;

    @Test
    public void getDataSource() throws SQLException {
        System.out.println(dataSource.getConnection());
    }


    @Test
    public void tt1(){
        List<Article> articles = articleService.selectAll();
        for (Article article : articles) {
            System.out.println(article);
        }
    }


    @Test
    public void tt2(){
        List<Article> articles = articleMapper.selectAll();
        for (Article article : articles) {
            System.out.println(article);
        }
    }

    @Test
    public void tt3(){
        int i = userfocusService.deleteByUidAndFid(2, 1);
        System.out.println(i);
    }


    @Test
    public void tt4(){
        List<Map<String,Object>> users = conferenceService.selectUsersByCId(20);
        for (Map<String, Object> user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void tt6(){
        HashMap<String, Object> map = new HashMap<>();
//        map.put("userId",12);
//        map.put("type","1");
//        map.put("typeSort",1);
//        map.put("sort",1);
        List<Map<String, Object>> maps = articleMapper.selectArticleByCondition(map);
        for (Map<String, Object> stringObjectMap : maps) {
            System.out.println(stringObjectMap);
        }
    }

    @Test
    public void tt5(){
        Integer integer = userfocusService.selectFocusCount(2);
        System.out.println(integer);
    }
    @Test
    public void tt7(){
        User user=new User();
        user.setEmail("cbl_qhc@163.com");
        User user1 = userService.selectOne(user);
        System.out.println(user1);
    }

}
